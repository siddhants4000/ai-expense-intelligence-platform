from pydantic import BaseModel


class LlmAdviceRequest(BaseModel):
    question: str
    total_amount: float
    total_expenses: int
    highest_category: str
    category_breakdown: list[dict]


class LlmAdviceResponse(BaseModel):
    advice: str
    provider: str
    model: str