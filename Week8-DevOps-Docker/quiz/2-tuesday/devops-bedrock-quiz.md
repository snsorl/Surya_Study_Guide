# Knowledge Check: DevOps, AWS Bedrock, and Code Quality Audits

This quiz evaluates your understanding of DevOps principles, continuous integration workflows, AWS Bedrock runtime APIs, Sonar static analysis, and authentication vulnerability mitigations.

---

## Questions

### 1. Which DevOps DORA metric measures the average time it takes for a code commit to successfully deploy to production?
- [ ] A) Deployment Frequency (DF)
- [ ] B) Lead Time for Changes (LTC)
- [ ] C) Mean Time to Recover (MTTR)
- [ ] D) Change Failure Rate (CFR)

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Lead Time for Changes (LTC)

**Explanation:** Lead Time for Changes measures the duration between a developer's code commit and its successful release to production, helping track pipeline efficiency.
- **Why others are wrong:**
  - A) Deployment Frequency measures how often code releases occur.
  - C) MTTR measures the time required to resolve a system outage.
  - D) CFR measures the percentage of deployments that cause production issues.
</details>

---

### 2. In AWS Bedrock, what is the difference between Prompting, RAG (Retrieval-Augmented Generation), and Fine-Tuning?
- [ ] A) Prompting changes the model weights; Fine-Tuning injects external database contexts.
- [ ] B) Prompting is run-time input design; RAG queries external databases to enrich inputs; Fine-Tuning modifies model weights using customized datasets.
- [ ] C) RAG trains the model weights; Fine-Tuning designs template instructions.
- [ ] D) They are identical model invocation techniques.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Prompting is run-time input design; RAG queries external databases to enrich inputs; Fine-Tuning modifies model weights using customized datasets.

**Explanation:** Prompting requires no training; RAG queries databases to enrich prompts dynamically; Fine-Tuning updates the model's weights using custom datasets.
- **Why others are wrong:**
  - A), C), and D) describe incorrect details for these techniques.
</details>

---

### 3. True or False: To prevent Session Fixation attacks, you should generate a new session ID after a user authenticates.
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) True

**Explanation:** Regenerating session IDs on login prevents Session Fixation attacks, where an attacker pre-defines a session token to hijack a user's session.
- **Why others are wrong:**
  - B) This is incorrect because regenerating session IDs is a standard security practice.
</details>

---

### 4. What type of static code check tool runs analysis directly in the developer's IDE, providing real-time feedback before code is committed?
- [ ] A) SonarCloud
- [ ] B) SonarLint
- [ ] C) Jenkins
- [ ] D) GitLab Runner

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) SonarLint

**Explanation:** SonarLint is an IDE plugin that flags bugs and code smells directly in the editor as you write code.
- **Why others are wrong:**
  - A) SonarCloud runs analysis in the cloud during CI builds.
  - C) and D) are pipeline automation runtimes, not static code analyzer tools.
</details>

---

### 5. What parameter in Bedrock model requests controls the randomness of responses, ranging from deterministic (0.0) to creative (1.0)?
- [ ] A) `max_tokens`
- [ ] B) `top_p`
- [ ] C) `temperature`
- [ ] D) `anthropic_version`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `temperature`

**Explanation:** The `temperature` parameter controls the randomness of generative outputs. Lower values produce deterministic text; higher values generate creative responses.
- **Why others are wrong:**
  - A) `max_tokens` limits the response length.
  - B) `top_p` controls cumulative probability token selections.
  - D) `anthropic_version` specifies the API runtime format.
</details>

---

### 6. What continuous deployment strategy deploys new code to a small, isolated subset of users (e.g., 5%) before rolling it out to the entire production environment?
- [ ] A) Blue-Green Deployment
- [ ] B) Recreate Deployment
- [ ] C) Canary Deployment
- [ ] D) Rolling Update

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Canary Deployment

**Explanation:** Canary deployments route a small fraction of traffic to the new version to check for errors before proceeding with a full release.
- **Why others are wrong:**
  - A) Blue-Green runs two identical environments, switching all traffic at once.
  - B) Recreate shuts down the old version completely before starting the new one, causing downtime.
  - D) Rolling updates replace instances sequentially, but do not target a specific subset of user traffic first.
</details>

---

### 7. What type of vulnerability occurs when an attacker manipulates prompt inputs to bypass safety guardrails and force the AI model to execute unauthorized actions?
- [ ] A) Model Poisoning
- [ ] B) Prompt Injection
- [ ] C) Model Drift
- [ ] D) Insecure Direct Object Reference

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Prompt Injection

**Explanation:** Prompt injection occurs when user inputs override the model's system instructions, causing the model to generate unsafe outputs or access unauthorized data.
- **Why others are wrong:**
  - A) Model Poisoning involves tampering with the model's training data.
  - C) Model Drift describes changes in prediction accuracy over time.
  - D) is a web application authorization flaw, not an AI prompt vulnerability.
</details>

---

### 8. What is a "Quality Gate" in static analysis tools?
- [ ] A) A firewall rule that blocks port traffic.
- [ ] B) A defined set of criteria (e.g., code coverage, vulnerabilities) that code must meet to pass build pipelines.
- [ ] C) An automated dependency update tool.
- [ ] D) An IDE key verification page.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) A defined set of criteria (e.g., code coverage, vulnerabilities) that code must meet to pass build pipelines.

**Explanation:** Quality Gates enforce coding standards in CI/CD pipelines, failing builds automatically if code quality thresholds (like code smells or security metrics) are violated.
- **Why others are wrong:**
  - A), C), and D) describe other network or configuration features.
</details>

---

### 9. True or False: Continuous Delivery (CD) automatically deploys every passing build to production without human intervention.
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) False

**Explanation:** Continuous Delivery automates building and testing, but requires manual approval before releasing to production. Continuous Deployment automates this final release step.
- **Why others are wrong:**
  - A) This describes Continuous Deployment.
</details>

---

### 10. When calling AWS Bedrock from Java, which class handles loading AWS credentials automatically from environment variables or local folders?
- [ ] A) `BedrockRuntimeClient`
- [ ] B) `InvokeModelRequest`
- [ ] C) `DefaultCredentialsProvider`
- [ ] D) `SdkBytes`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `DefaultCredentialsProvider`

**Explanation:** `DefaultCredentialsProvider` checks standard locations (environment variables, system properties, `~/.aws/credentials`) to authenticate the client automatically.
- **Why others are wrong:**
  - A) `BedrockRuntimeClient` is the client interface class.
  - B) `InvokeModelRequest` defines the API request body.
  - D) `SdkBytes` handles raw payload binary arrays.
</details>
