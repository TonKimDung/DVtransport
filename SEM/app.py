from fastapi import FastAPI
from sqlalchemy import create_engine
import pandas as pd
from semopy import Model
from sklearn.preprocessing import StandardScaler

app = FastAPI()

DB_URL = "postgresql://neondb_owner:npg_s67gtnjcUXZy@ep-snowy-dawn-a1id9twt-pooler.ap-southeast-1.aws.neon.tech/neondb?sslmode=require&channel_binding=require"

@app.get("/sem")
def run_sem():
    try:
        engine = create_engine(DB_URL)

        query = """
        SELECT
            DATE_TRUNC('month', t.created_at) AS month,
            COUNT(v.id) AS total_vehicles,
            SUM(v.capacity) AS total_capacity,
            AVG(2026 - v.manufacture_year) AS avg_vehicle_age,
            AVG(CASE WHEN t.arrival_time IS NOT NULL THEN 1 ELSE 0 END) AS on_time_rate,
            COUNT(i.id)::float / NULLIF(COUNT(t.id),0) AS incident_rate,
            SUM(f.total_amount) / NULLIF(SUM(t.total_cost),0) AS fuel_ratio,
            AVG(te.amount) AS avg_expense,
            COUNT(DISTINCT c.id) AS contract_count,
            COUNT(DISTINCT o.customer_id) AS customer_count,
            AVG(o.total_amount) AS avg_order_value,
            AVG(t.total_revenue - t.total_cost) AS avg_profit,
            SUM(t.total_revenue) AS revenue,
            SUM(t.total_cost) AS cost
        FROM trips t
        LEFT JOIN vehicles v ON t.vehicle_id = v.id
        LEFT JOIN incidents i ON t.id = i.trip_id
        LEFT JOIN fuel_transactions f ON t.id = f.trip_id
        LEFT JOIN trip_expenses te ON t.id = te.trip_id
        LEFT JOIN trip_orders tro ON tro.trip_id = t.id
        LEFT JOIN orders o ON tro.order_id = o.id
        LEFT JOIN contracts c ON o.contract_id = c.id
        GROUP BY DATE_TRUNC('month', t.created_at)
        ORDER BY month
        """

        df = pd.read_sql(query, engine)

        if df.empty:
            return {"error": "No data in DB"}

        df = df.fillna(0).infer_objects(copy=False)

        # 👉 fallback nếu ít data
        if len(df) < 5:
            return {
                "status": "warning",
                "message": "Not enough data",
                "rows": len(df),
                "data": df.to_dict(orient="records")
            }

        df = df.drop(columns=['month'])

        if df.nunique().min() <= 1:
            return {"error": "Data has no variation"}

        scaler = StandardScaler()
        df_scaled = pd.DataFrame(
            scaler.fit_transform(df),
            columns=df.columns
        )

        model_desc = """
        FleetCapacity =~ total_vehicles + total_capacity + avg_vehicle_age
        DriverQuality =~ on_time_rate + incident_rate
        CostEfficiency =~ fuel_ratio + avg_expense
        MarketStrength =~ contract_count + customer_count + avg_order_value
        TransportPerformance =~ avg_profit + revenue + cost
        TransportPerformance ~ FleetCapacity + DriverQuality + CostEfficiency + MarketStrength
        """

        model = Model(model_desc)
        model.fit(df_scaled)

        result = model.inspect()

        return {
            "status": "success",
            "rows": len(df),
            "result": result.to_dict(orient="records")
        }

    except Exception as e:
        return {"error": str(e)}