from app.services.data_loader import load_sem_dataset
from app.services.preprocessing import preprocess_data
from app.models.sem_model import train_sem_model
from app.utils.chart_generator import generate_chart

def run_sem_analysis():

    raw_df = load_sem_dataset()

    processed_df = preprocess_data(raw_df)

    result = train_sem_model(processed_df)

    chart = generate_chart(
        result["estimates"]
    )

    return {
        "rows": len(raw_df),
        "estimates": result["estimates"],
        "insights": result["summary"],
        "chart": chart
    }