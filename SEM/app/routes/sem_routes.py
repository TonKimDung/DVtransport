from fastapi import APIRouter
from app.services.sem_service import run_sem_analysis

router = APIRouter()

@router.get("/analyze")
def analyze():

    return run_sem_analysis()