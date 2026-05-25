import pandas as pd
from app.database import engine

def load_sem_dataset():

    query = """

    SELECT

        t.id as trip_id,

        -- Fleet Capacity
        v.capacity as total_capacity,

        CASE
            WHEN EXTRACT(YEAR FROM CURRENT_DATE)
                - v.manufacture_year < 5
            THEN 1 ELSE 0
        END as new_vehicle_ratio,

        -- Operational Efficiency
        CASE
            WHEN t.arrival_time IS NOT NULL
            AND t.arrival_time <=
                t.departure_time +
                (r.estimated_hours || ' hours')::interval
            THEN 1 ELSE 0
        END as ontime_rate,

        CASE
            WHEN t.status = 'COMPLETED'
            THEN 1 ELSE 0
        END as completed_trip_rate,

        COALESCE(
            ft.quantity_liters /
            NULLIF(r.distance_km, 0),
            0
        ) as fuel_efficiency,

        -- Safety
        CASE
            WHEN i.id IS NOT NULL
            THEN 1 ELSE 0
        END as incident_rate,

        COALESCE(
            te.amount,
            0
        ) as maintenance_cost,

        -- Business / Financial
        COALESCE(o.total_amount, 0)
            as avg_revenue,

        COALESCE(
            t.total_revenue - t.total_cost,
            0
        ) as net_profit,

        COALESCE(
            t.total_revenue,
            0
        ) as revenue_growth,

        CASE
            WHEN to2.allocated_weight IS NULL
            THEN 1 ELSE 0
        END as empty_miles,

        CASE
            WHEN c.id IS NOT NULL
            THEN 1 ELSE 0
        END as new_contracts,

        CASE
            WHEN o.customer_id IS NOT NULL
            THEN 1 ELSE 0
        END as retention_rate

    FROM trips t

    LEFT JOIN vehicles v
        ON t.vehicle_id = v.id

    LEFT JOIN routes r
        ON t.route_id = r.id

    LEFT JOIN incidents i
        ON i.trip_id = t.id

    LEFT JOIN fuel_transactions ft
        ON ft.trip_id = t.id

    LEFT JOIN trip_expenses te
        ON te.trip_id = t.id
        AND te.expense_type = 'MAINTENANCE'

    LEFT JOIN trip_orders to2
        ON to2.trip_id = t.id

    LEFT JOIN orders o
        ON to2.order_id = o.id

    LEFT JOIN contracts c
        ON o.contract_id = c.id

    WHERE t.created_at IS NOT NULL

    """
    df = pd.read_sql(query, engine)

    return df