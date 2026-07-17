# Structured AI Input/Output in Software Engineering

## Learning Objectives
- Formulate prompts that force AI models to return outputs in structured formats.
- Design JSON schemas for AI instruction formatting.
- Implement reliable JSON parsing routines to process AI-generated outputs.
- Build defensive error-handling fallback logic for malformed AI responses.

---

## Why This Matters
As software engineers, we use AI assistants not only for interactive chat, but also programmatically (via APIs) to classify data, translate languages, or generate mock configurations. However, LLMs naturally generate unstructured conversational text (e.g. *"Here is your JSON:..."*). If your code parses this output dynamically, raw text breaks your parsing engines. Structuring AI prompts and validating outputs is essential for building reliable AI-assisted workflows.

---

## The Concept

### 1. JSON Schema Prompting
To force an AI to generate structured data without conversational wrappers, you must:
1. **Provide a Schema**: Give the AI a strict JSON schema template to populate.
2. **Inject Formatting Instructions**: Add explicit instructions forbidding conversational wrappers (like *"Here is the output"* or markdown code blocks).
3. **Use API System Configurations**: If using APIs directly, use built-in options like **JSON Mode** or **Structured Outputs** which enforce JSON schemas at the system level.

### 2. Output Formatting Instructions
A robust system prompt includes instructions like:
- *"Output MUST be valid, minified JSON matching the schema provided."*
- *"Do NOT wrap the output in markdown code fences (\`\`\`json).\`"*
- *"Do NOT include any introduction, comments, or explanations."*

### 3. Parsing AI-generated Outputs Safely
Even with strict instructions, AI models can occasionally output markdown wraps or malformed syntax. A reliable parsing routine must:
- Extract JSON substrings from conversational text blocks.
- Wrap JSON parses in `try/catch` error handlers.
- Fall back to default configurations or trigger automated retries if parsing fails.

---

## Code Examples

### Prompt Template for Structured Output
```text
[Role]
You are a mock data generator API.

[Task]
Generate a mock customer record in JSON format.

[JSON Schema]
{
  "id": number,
  "name": string,
  "roles": array of strings
}

[Constraints]
Return ONLY raw JSON matching the schema above. 
Do NOT wrap the JSON in markdown code blocks.
Do NOT include any conversational text.
```

### Defensive JSON Parsing in JavaScript
The function below safely extracts and parses a JSON payload even if the AI wraps it in markdown code blocks or conversational text:

```javascript
function parseAiJson(rawResponse) {
    let cleanResponse = rawResponse.trim();
    
    // 1. Regular expression to extract JSON from inside markdown ```json blocks
    const jsonMatch = cleanResponse.match(/```json\s*([\s\S]*?)\s*```/) 
                   || cleanResponse.match(/```\s*([\s\S]*?)\s*```/);
    
    if (jsonMatch) {
        cleanResponse = jsonMatch[1];
    }
    
    try {
        const data = JSON.parse(cleanResponse);
        return data;
    } catch (error) {
        console.error("Failed to parse AI JSON. Raw output was:", rawResponse);
        // 2. Return fallback defaults
        return {
            id: 0,
            name: "Unknown",
            roles: ["User"]
        };
    }
}

// Verification with wrapped response
const mockResponse = "Here is the data:\n```json\n{\n  \"id\": 1,\n  \"name\": \"Alice\",\n  \"roles\": [\"Admin\"]\n}\n```\nHope this helps!";
console.log(parseAiJson(mockResponse)); 
// Output: { id: 1, name: 'Alice', roles: [ 'Admin' ] }
```

---

## Summary
- LLMs are conversational by default; programmatically, we must enforce **structured outputs**.
- Use strict schemas and output constraints inside system prompts to enforce formatting.
- Always assume AI outputs might be malformed; use **defensive parsing** with regular expression extraction and `try/catch` handlers.
- Build default fallback configurations in your code to handle parsing failures cleanly.

---

## Additional Resources
- [Google Cloud: Designing structured prompts](https://cloud.google.com/vertex-ai/docs/generative-ai/multimodal/design-structured-prompts)
- [OpenAI: Structured Outputs guide](https://platform.openai.com/docs/guides/structured-outputs)
