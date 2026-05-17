from fastapi import APIRouter

from app.schemas.ai_schema import (
    AnomalyResponse,
    CategorizationResponse,
    ExpenseInsightRequest,
    SpendingInsightRequest,
    SpendingInsightResponse,
)
from app.services.ai_service import AiService

router = APIRouter(prefix="/api/v1/ai", tags=["AI Expense Intelligence"])

ai_service = AiService()


@router.post("/categorize", response_model=CategorizationResponse)
def categorize_expense(request: ExpenseInsightRequest):
    return ai_service.categorize_expense(request)


@router.post("/anomaly-detection", response_model=AnomalyResponse)
def detect_anomaly(request: ExpenseInsightRequest):
    return ai_service.detect_anomaly(request)


@router.post("/spending-insights", response_model=SpendingInsightResponse)
def generate_spending_insights(request: SpendingInsightRequest):
    return ai_service.generate_spending_insights(request)