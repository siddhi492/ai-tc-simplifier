from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

class InputText(BaseModel):
    text: str

def classify(text: str):
    t = text.lower()
    if "data" in t:
        return "data_sharing"
    elif "payment" in t:
        return "payments"
    elif "terminate" in t:
        return "termination"
    elif "subscription" in t:
        return "auto_renewal"
    else:
        return "other"

def risk(text: str):
    if "without notice" in text.lower():
        return "HIGH"
    elif "may" in text.lower():
        return "MEDIUM"
    return "LOW"

@app.post("/process")
def process(input_data: InputText):
    sentences = input_data.text.split(".")
    result = []

    for s in sentences:
        s = s.strip()
        if len(s) < 10:
            continue

        result.append({
            "clause": s,
            "category": classify(s),
            "risk": risk(s),
            "summary": s[:100]
        })

    return {"results": result}