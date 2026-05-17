import requests

from app.schemas.llm_schema import LlmAdviceRequest, LlmAdviceResponse


class LlmService:

    def __init__(self):
        self.ollama_url = "http://localhost:11434/api/generate"
        self.model = "llama3.2"

    def generate_advice(self, request: LlmAdviceRequest) -> LlmAdviceResponse:
        prompt = f"""
You are an AI financial expense advisor.

User question:
{request.question}

Expense summary:
- Total amount: {request.total_amount}
- Total expenses: {request.total_expenses}
- Highest spending category: {request.highest_category}

Category breakdown:
{request.category_breakdown}

Give practical, clear, and concise advice.
"""

        payload = {
            "model": self.model,
            "prompt": prompt,
            "stream": False
        }

        response = requests.post(
            self.ollama_url,
            json=payload,
            timeout=120
        )
        response.raise_for_status()

        data = response.json()

        return LlmAdviceResponse(
            advice=data.get("response", "No advice generated."),
            provider="Ollama",
            model=self.model
        )