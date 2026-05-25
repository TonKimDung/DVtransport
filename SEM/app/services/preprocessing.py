from sklearn.preprocessing import StandardScaler
import pandas as pd

def preprocess_data(df):

    df = df.fillna(0)

    if "analysis_date" in df.columns:
        df = df.drop(columns=["analysis_date"])

    scaler = StandardScaler()

    scaled = scaler.fit_transform(df)

    scaled_df = pd.DataFrame(
        scaled,
        columns=df.columns
    )

    return scaled_df