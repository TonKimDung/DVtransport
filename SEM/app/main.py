from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.routes.sem_routes import router

app = FastAPI()

# =========================
# CORS
# =========================

origins = [
    "http://localhost:5173",
    "http://127.0.0.1:5173",
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# =========================
# ROUTES
# =========================

app.include_router(
    router,
    prefix="/sem",
    tags=["SEM"]
)

# =========================
# HEALTH CHECK
# =========================

@app.get("/")
def home():
    return {
        "message": "SEM Service Running"
    }