from datetime import date
from decimal import Decimal

from pydantic import BaseModel, Field


class ExpenseInsightRequest(BaseModel):
    title: str
    description: str | None = None
    amount: Decimal
    expense_date: date = Field(alias="expenseDate")

    model_config = {
        "populate_by_name": True
    }


class CategorizationResponse(BaseModel):
    predicted_category: str
    confidence: float
    reason: str


class AnomalyResponse(BaseModel):
    anomaly: bool
    risk_score: float
    reason: str

class SpendingInsightRequest(BaseModel):
    total_amount: Decimal = Field(alias="totalAmount")
    total_expenses: int = Field(alias="totalExpenses")
    highest_category: str = Field(alias="highestCategory")

    model_config = {
        "populate_by_name": True
    }


class SpendingInsightResponse(BaseModel):
    summary: str
    highest_category: str
    risk_level: str
    recommendation: str