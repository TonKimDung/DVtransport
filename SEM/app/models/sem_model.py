from semopy import Model
import pandas as pd
import numpy as np
import random

np.random.seed(42)
random.seed(42)
SEM_DESCRIPTION = """

# operational efficiency
empty_miles ~ total_capacity + new_vehicle_ratio + incident_rate

# customer growth
retention_rate ~ empty_miles + incident_rate

# financial performance
avg_revenue ~ retention_rate + new_contracts + empty_miles

"""

def train_sem_model(df):

    # remove id
    if "trip_id" in df.columns:
        df = df.drop(columns=["trip_id"])

    # numeric only
    df = df.select_dtypes(include=[np.number])

    # clean
    df = df.replace([np.inf, -np.inf], np.nan)
    df = df.fillna(df.mean())

    # drop constant cols
    nunique = df.nunique()
    constant_cols = nunique[nunique <= 1].index

    if len(constant_cols) > 0:
        print("Dropped constant columns:", constant_cols.tolist())
        df = df.drop(columns=constant_cols)

    print("Final shape:", df.shape)
    print("Columns:", df.columns.tolist())

    # dataset nhỏ -> bootstrap demo
    if len(df) < 30:

        print(f"WARNING: Low sample size: {len(df)}")

        original_df = df.copy()

        while len(df) < 30:

            noisy = original_df.copy()

            for col in noisy.columns:
                noisy[col] += np.random.normal(
                    0,
                    0.01,
                    len(noisy)
                )

            df = pd.concat([df, noisy], ignore_index=True)

        print("Bootstrapped shape:", df.shape)

    model = Model(SEM_DESCRIPTION)

    model.fit(df)

    estimates = model.inspect()

    return {
        "estimates": estimates.to_dict(orient="records"),
        "summary": generate_business_summary(estimates)
    }

def generate_business_summary(estimates_df):

    insights = []

    label_map = {
        "total_capacity":
            "tổng công suất vận chuyển",

        "empty_miles":
            "quãng đường chạy rỗng",

        "new_vehicle_ratio":
            "tỷ lệ xe mới",

        "incident_rate":
            "tỷ lệ sự cố",

        "retention_rate":
            "tỷ lệ giữ chân khách hàng",

        "avg_revenue":
            "doanh thu trung bình",

        "new_contracts":
            "số hợp đồng mới",
    }

    for _, row in estimates_df.iterrows():

        if row["op"] != "~":
            continue

        source = row["rval"]
        target = row["lval"]

        coef = float(row["Estimate"])

        p_value = row.get(
            "p-value",
            1
        )

        # bỏ quan hệ yếu
        if abs(coef) < 0.1:
            continue

        # không có ý nghĩa thống kê
        if p_value > 0.05:
            continue

        priority = abs(coef)

        source_name = label_map.get(
            source,
            source
        )

        target_name = label_map.get(
            target,
            target
        )

        # ========================
        # COST OPTIMIZATION
        # ========================
        if (
            source ==
            "new_vehicle_ratio"
            and
            target ==
            "empty_miles"
            and
            coef < 0
        ):
            insights.append({
                "type":
                    "cost",

                "priority":
                    priority,

                "title":
                    "Giảm chi phí vận hành",

                "description":
                    "Tăng tỷ lệ xe mới có thể giúp giảm quãng đường chạy rỗng, từ đó tối ưu nhiên liệu và giảm chi phí logistics.",

                "impact":
                    round(coef, 3)
            })

        # ========================
        # CUSTOMER RETENTION
        # ========================
        elif (
            target ==
            "retention_rate"
            and
            coef < 0
        ):
            insights.append({
                "type":
                    "customer",

                "priority":
                    priority,

                "title":
                    "Cải thiện giữ chân khách hàng",

                "description":
                    f"Giảm {source_name} có thể giúp tăng tỷ lệ giữ chân khách hàng.",

                "impact":
                    round(coef, 3)
            })

        # ========================
        # REVENUE
        # ========================
        elif (
            target ==
            "avg_revenue"
            and
            coef > 0
        ):
            insights.append({
                "type":
                    "revenue",

                "priority":
                    priority,

                "title":
                    "Tăng doanh thu",

                "description":
                    f"Tăng {source_name} có thể giúp cải thiện doanh thu trung bình.",

                "impact":
                    round(coef, 3)
            })

        # ========================
        # RISK MANAGEMENT
        # ========================
        elif (
            source ==
            "incident_rate"
            and
            coef < 0
        ):
            insights.append({
                "type":
                    "risk",

                "priority":
                    priority,

                "title":
                    "Giảm rủi ro vận hành",

                "description":
                    "Giảm tỷ lệ sự cố có thể cải thiện hiệu suất vận hành và trải nghiệm khách hàng.",

                "impact":
                    round(coef, 3)
            })

        # ========================
        # GENERIC INSIGHT
        # ========================
        else:

            direction = (
                "tăng"
                if coef > 0
                else "giảm"
            )

            insights.append({
                "type":
                    "general",

                "priority":
                    priority,

                "title":
                    f"Tối ưu {target_name}",

                "description":
                    f"Thay đổi {source_name} có thể giúp {direction} {target_name}.",

                "impact":
                    round(coef, 3)
            })

    # sort theo độ ảnh hưởng mạnh nhất
    insights.sort(
        key=lambda x:
        x["priority"],
        reverse=True
    )

    # lấy top 5
    return insights[:5]