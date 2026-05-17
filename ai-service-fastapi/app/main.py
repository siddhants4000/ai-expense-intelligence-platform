from fastapi import FastAPI

from app.api.ai_controller import router as ai_router

app = FastAPI(
    title="AI Expense Intelligence Service",
    version="1.0.0",
    description="FastAPI microservice for expense categorization, anomaly detection, and spending insights."
)


@app.get("/health")
def health():
    return {
        "status": "UP",
        "service": "AI Expense Intelligence Service"
    }


app.include_router(ai_router)