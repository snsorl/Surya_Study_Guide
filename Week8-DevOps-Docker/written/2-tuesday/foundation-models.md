# Foundation Models: Core Concepts, Parameters, and Inference

## Learning Objectives
- Define what Foundation Models (FMs) are and describe their training process.
- Contrast base inference, retrieval-augmented generation, and model fine-tuning.
- Explain the significance of the Context Window in model interactions.
- Describe the operation of LLM hyper-parameters (temperature, top-p, top-k).
- Evaluate and select appropriate models for different software workloads.

---

## Why This Matters
Integrating AI models into an application involves more than sending a text string to an endpoint. You must understand the parameters that govern the model's behavior.

If your backend sends queries without adjusting hyper-parameters like `temperature` or `top-p`, you risk unpredictable outputs, API timeouts, or high token billing. A clear understanding of context windows, token consumption, and model selection is critical to building cost-effective, reliable AI features.

---

## The Concept

### 1. What is a Foundation Model?
A **Foundation Model (FM)** is a large-scale neural network trained on vast amounts of unstructured data (often using self-supervised learning). They are called "foundation" models because they serve as a base starting point for many downstream tasks:

```
                  +--------------------------------+
                  |        FOUNDATION MODEL        |
                  | (Pre-trained on Web-Scale Data)|
                  +----------------t---------------+
                                   |
           +-----------------------+-----------------------+
           |                       |                       |
           v                       v                       v
+--------------------+   +--------------------+   +--------------------+
|  Chat & Assistants |   | Sentiment Analysis |   |  Code Generation   |
+--------------------+   +--------------------+   +--------------------+
```

These models use transformer architectures to predict the next word or token in a sequence.

---

### 2. Adaptation Strategies

#### Base Inference
Querying the pre-trained model directly without making any updates to its weights. Responses rely on the patterns the model learned during its initial training.

#### Fine-Tuning
Updating the internal weights of the model by training it on a specific, labeled dataset. This adapts the model to use specific styles, vocabulary, or niche corporate domains.

#### Retrieval-Augmented Generation (RAG)
Instead of retraining the model, you query a database (often a vector database) for relevant documents, paste those documents into the prompt, and ask the model to answer based on that context. This is highly popular because it prevents hallucinations and does not require costly training.

---

### 3. Key Concepts: Context Windows and Tokens
- **Tokens**: The basic units of text processed by an LLM (typically a word fragment, where 100 tokens roughly equals 75 words).
- **Context Window**: The maximum number of tokens a model can process in a single request (including both your prompt and the generated response). If your prompt exceeds this limit, the API returns an error or truncates older messages.
  - E.g., Anthropic Claude 3.5 Sonnet supports a large **200,000 token** context window.
  - E.g., Llama 3 supports an **8,192 token** context window.

---

### 4. Hyper-Parameters (Controlling Output Variability)

When calling a foundation model, you pass parameters to configure its output style:

#### Temperature
Controls the randomness of the token prediction.
- **Low Temperature (0.0 to 0.2)**: Output is highly deterministic and consistent. Best for coding, mathematical logic, and factual database queries.
- **High Temperature (0.7 to 1.0)**: Output is creative and varied. Best for brainstorming, creative writing, and human-like chat dialogue.

#### Top-P (Nucleus Sampling)
Limits token selection to a cumulative probability percentage. E.g., a Top-P of `0.9` means the model only selects from the pool of words representing the top 90% of logical choices, ignoring highly unlikely words.

#### Top-K
Limits token selection to the $K$ most likely next words. E.g., a Top-K of `50` limits the model's choices to the top 50 predicted words, preventing it from selecting unusual phrasing.

---

## Code Examples and Walkthroughs

### 1. Conceptual Hyper-Parameter Payload (JSON Schema)
This JSON structure demonstrates how hyper-parameters are configured for a model request:

```json
{
  "modelId": "anthropic.claude-3-sonnet-20240229-v1:0",
  "contentType": "application/json",
  "accept": "application/json",
  "body": {
    "anthropic_version": "bedrock-2023-05-31",
    "max_tokens": 1000,
    "temperature": 0.2,
    "top_p": 0.9,
    "top_k": 250,
    "messages": [
      {
        "role": "user",
        "content": "Parse the following customer order logs and output as valid JSON..."
      }
    ]
  }
}
```

---

## Summary
- **Foundation Models** are large pre-trained neural networks that form the base starting point for downstream tasks.
- **Inference** queries base models, **Fine-Tuning** updates model weights, and **RAG** injects external context dynamically.
- **Context Windows** restrict the volume of tokens processed in a single interaction.
- **Temperature**, **Top-P**, and **Top-K** are configuration parameters that control the creativity and predictability of the model's output.

---

## Additional Resources
- [Understanding Transformers and Attention Mechanisms](https://arxiv.org/abs/1706.03762)
- [Amazon Bedrock Foundation Model Parameter Directory](https://docs.aws.amazon.com/bedrock/latest/userguide/model-parameters.html)
- [Best Practices for Token Management and Context Optimization](https://help.openai.com/en/articles/4936856-what-are-tokens-and-how-to-count-them)
