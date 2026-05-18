from fastapi import FastAPI

from opentelemetry import trace
from opentelemetry.exporter.otlp.proto.http.trace_exporter import OTLPSpanExporter
from opentelemetry.instrumentation.fastapi import FastAPIInstrumentor
from opentelemetry.sdk.resources import Resource
from opentelemetry.sdk.trace import TracerProvider
from opentelemetry.sdk.trace.export import BatchSpanProcessor

from app.api.ai_controller import router as ai_router


app = FastAPI(
    title="AI Expense Intelligence Service",
    version="1.0.0",
    description="FastAPI microservice for expense categorization, anomaly detection, and spending insights."
)

resource = Resource.create({
    "service.name": "ai-expense-ai-service"
})

trace_provider = TracerProvider(resource=resource)

trace_provider.add_span_processor(
    BatchSpanProcessor(
        OTLPSpanExporter(
            endpoint="http://otel-collector:4318/v1/traces"
        )
    )
)

trace.set_tracer_provider(trace_provider)

FastAPIInstrumentor.instrument_app(app)


@app.get("/health")
def health():
    return {
        "status": "UP",
        "service": "AI Expense Intelligence Service"
    }


app.include_router(ai_router)