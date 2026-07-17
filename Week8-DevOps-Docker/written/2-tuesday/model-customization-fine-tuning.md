# Model Customization: Comparing Prompt Engineering, RAG, and Fine-Tuning

## Learning Objectives
- Detail the differences between Prompt Engineering, Retrieval-Augmented Generation (RAG), and Fine-Tuning.
- Analyze the cost, complexity, and performance trade-offs of each customization strategy.
- Differentiate between continued pre-training (domain adaptation) and task-specific fine-tuning.
- Outline the steps of the Amazon Bedrock model customization workflow.
- Select the appropriate adaptation strategy based on project requirements.

---

## Why This Matters
When building AI features, developers often assume they need to fine-tune a model to teach it about their company's data. However, fine-tuning is complex, requires large labeled datasets, and incurs high compute costs.

In many cases, techniques like **Prompt Engineering** or **Retrieval-Augmented Generation (RAG)** can achieve the same results in less time and at a fraction of the cost. Understanding the trade-offs of each approach is key to designing efficient, cost-effective AI architectures.

---

## The Concept

### 1. Customization Spectrum: Prompting vs. RAG vs. Fine-Tuning
Each adaptation strategy sits on a spectrum of cost, complexity, and specialized knowledge:

```
                  CUSTOMIZATION SPECTRUM
 Low Cost/Complexity <=============================> High Cost/Complexity
 
 +--------------------+    +--------------------+    +--------------------+
 | PROMPT ENGINEERING |    |        RAG         |    |    FINE-TUNING     |
 | - Modify inputs    |    | - Dynamic query    |    | - Update weights   |
 | - Simple templates |    | - Vector database  |    | - Labeled data     |
 +--------------------+    +--------------------+    +--------------------+
```

#### Prompt Engineering
- **Concept**: Optimizing prompt text, instructions, and examples (few-shot prompting) to guide the base model's output.
- **Data Requirement**: None.
- **Compute Cost**: Low (normal inference costs).
- **Best For**: General text tasks, classification, formatting outputs (e.g., forcing JSON outputs), and quick prototyping.

#### Retrieval-Augmented Generation (RAG)
- **Concept**: Injecting relevant context from external databases (e.g., a PostgreSQL instance or vector database) into the prompt dynamically before sending it to the model.
- **Data Requirement**: Access to documents or database files, updated dynamically.
- **Compute Cost**: Low to Moderate (cost of running database query + extra context tokens).
- **Best For**: Applications requiring access to dynamic data, proprietary knowledge bases (e.g., HR manuals, product inventory), and workflows where you must prevent hallucinations.

#### Fine-Tuning
- **Concept**: Training the base model on a specialized dataset to permanently update its internal weights.
- **Data Requirement**: Large, high-quality, labeled training datasets (e.g., thousands of prompt-response pairs).
- **Compute Cost**: High (requires dedicated GPU time to train and run the custom model).
- **Best For**: Teaching the model highly specialized jargon, adapting output formats (e.g., voice or tone), or optimizing tasks for smaller, faster models.

---

### 2. Continued Pre-Training vs. Fine-Tuning
- **Continued Pre-Training (Unsupervised)**: Feeding raw text documents (e.g., medical journals or legal code) into the model so it learns the vocabulary, grammar, and relationship structures of a specific domain. The model is not trained on how to answer questions; it simply learns the domain's language patterns.
- **Fine-Tuning (Supervised)**: Training the model on specific input-output patterns (e.g., "Question: [SQL Error] -> Answer: [Remediation Step]"). This teaches the model how to perform a specific task.

---

### 3. Amazon Bedrock Customization Workflow
Amazon Bedrock provides a managed pipeline to customize models securely:
1.  **Prepare Training Data**: Format your dataset as JSON Lines (JSONL), where each line contains a prompt and response pair. Store the dataset in an Amazon S3 bucket.
2.  **Create Customization Job**: Configure a training run in Bedrock, specifying the base model (e.g., Titan or Llama), the training S3 path, and hyper-parameters (epochs, batch size, learning rate).
3.  **Validate and Evaluate**: Bedrock trains the model and outputs training logs to Amazon CloudWatch, along with model performance metrics.
4.  **Provision Throughput**: To run your custom model, you must provision dedicated hosting capacity (Provisioned Throughput) measured in Model Units.

---

## Code Examples and Walkthroughs

### 1. Training Data Format (JSON Lines Schema)
To customize models in Amazon Bedrock, training data must be uploaded to an S3 bucket in the JSONL format. Each line must be a self-contained JSON object:

```json
{"prompt": "Generate a standard Spring Boot controller for: User profiles.", "completion": "package com.example.project3.controller;\n\nimport org.springframework.web.bind.annotation.*;\n\n@RestController\n@RequestMapping(\"/api/users\")\npublic class UserController {\n    @GetMapping\n    public String getUser() { return \"User Profile\"; }\n}"}
{"prompt": "Generate a standard Spring Boot controller for: Tasks.", "completion": "package com.example.project3.controller;\n\nimport org.springframework.web.bind.annotation.*;\n\n@RestController\n@RequestMapping(\"/api/tasks\")\npublic class TaskController {\n    @GetMapping\n    public String getTasks() { return \"Task List\"; }\n}"}
```

---

## Summary
- **Prompt Engineering** guides output styling; **RAG** injects external data dynamically; **Fine-Tuning** updates model weights permanently.
- Use **RAG** when your model needs access to real-time, dynamic database resources or documents.
- Use **Fine-Tuning** to teach a model highly specialized language structures, domain styles, or task formats.
- **Amazon Bedrock Customization** reads JSONL datasets from S3 and trains custom models in a serverless, managed pipeline.

---

## Additional Resources
- [AWS Guide: Customizing Models in Amazon Bedrock](https://docs.aws.amazon.com/bedrock/latest/userguide/custom-models.html)
- [Retrieval-Augmented Generation (RAG) System Architecture Basics](https://aws.amazon.com/what-is/retrieval-augmented-generation/)
- [Best Practices for Formatting Labeled AI Training Datasets](https://docs.aws.amazon.com/bedrock/latest/userguide/model-customization-prepare.html)
