from app.schemas.ai_schema import (
    AnomalyResponse,
    CategorizationResponse,
    ExpenseInsightRequest,
    SpendingInsightRequest,
    SpendingInsightResponse,
)


class AiService:

    def categorize_expense(
        self,
        request: ExpenseInsightRequest
    ) -> CategorizationResponse:
        text = f"{request.title} {request.description or ''}".lower()

        category = "OTHER"

        if any(word in text for word in ["food", "restaurant", "dinner", "lunch", "coffee"]):
            category = "FOOD"
        elif any(word in text for word in ["uber", "taxi", "train", "bus", "transport", "ride"]):
            category = "TRANSPORT"
        elif any(word in text for word in ["rent", "apartment", "housing"]):
            category = "RENT"
        elif any(word in text for word in ["shopping", "amazon", "clothes"]):
            category = "SHOPPING"
        elif any(word in text for word in ["doctor", "medicine", "health"]):
            category = "HEALTH"

        return CategorizationResponse(
            predicted_category=category,
            confidence=0.85,
            reason="Rule-based AI baseline categorization"
        )

    def detect_anomaly(
        self,
        request: ExpenseInsightRequest
    ) -> AnomalyResponse:
        is_anomaly = request.amount > 1000

        reason = (
            "Expense amount is unusually high"
            if is_anomaly
            else "Expense amount is within normal range"
        )

        return AnomalyResponse(
            anomaly=is_anomaly,
            risk_score=0.9 if is_anomaly else 0.1,
            reason=reason
        )

    def generate_spending_insights(
        self,
        request: SpendingInsightRequest
    ) -> SpendingInsightResponse:

        recommendation = "Spending looks balanced."

        if request.total_amount > 2000:
            recommendation = (
                "Your total spending is high. "
                "Consider reviewing large expenses."
            )

        elif request.highest_category in [
            "FOOD",
            "SHOPPING",
            "ENTERTAINMENT"
        ]:
            recommendation = (
                f"Most spending is in {request.highest_category}. "
                "Consider setting a monthly budget for this category."
            )

        risk_level = "LOW"

        if request.total_amount > 3000:
            risk_level = "HIGH"

        elif request.total_amount > 1500:
            risk_level = "MEDIUM"

        return SpendingInsightResponse(
            summary=(
                f"You made {request.total_expenses} expenses "
                f"with total spending of {request.total_amount}."
            ),
            highest_category=request.highest_category,
            risk_level=risk_level,
            recommendation=recommendation
        )