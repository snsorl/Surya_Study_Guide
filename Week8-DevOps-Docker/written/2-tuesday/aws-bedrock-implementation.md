# Calling AWS Bedrock from Java: SDK Configuration and API Integration

## Learning Objectives
- Configure the AWS SDK for Java v2 to access Amazon Bedrock.
- Construct InvokeModel request payloads for Anthropic Claude models.
- Parse JSON API responses to extract generated text results.
- Implement robust exception handling and API retry strategies.

---

## Why This Matters
Learning about foundation models is only the first step. To use them, you must be able to call them programmatically from your backend application. For a Java Spring Boot backend, this means configuring the AWS SDK, constructing API payloads, and parsing the JSON responses.

Integrating AI calls into your backend also introduces new failure modes. AI requests can experience network latency, timeout errors, or rate-limiting blocks. Implementing robust exception handling and retry mechanisms is critical to keeping your application stable.

---

## The Concept

### 1. The AWS Java SDK v2 architecture
AWS provides a dedicated client dependency for Bedrock runtime interactions: **`software.amazon.awssdk:bedrockruntime`**.
- **BedrockClient (Sync)**: Blocking client where thread execution waits until the model completes response generation.
- **BedrockAsyncClient**: Non-blocking client designed for reactive pipelines and streaming token outputs.

In this guide, we focus on the synchronous `BedrockRuntimeClient` to show baseline connection patterns.

---

### 2. Request and Response Structures
Because Amazon Bedrock hosts models from multiple providers, the payload schemas are not standardized. The base API request accepts a raw byte buffer containing provider-specific JSON parameters:
- You pass the input payload as a binary blob (`SdkBytes`) to the `InvokeModelRequest`.
- API Gateway/Bedrock processes the request, queries the model, and returns a binary response payload.
- You convert the binary payload back into a string and parse it as JSON to extract the text output.

```
       Spring Boot Server (Java App)                 Amazon Bedrock Engine
+------------------------------------------+       +-------------------------+
|  1. Construct JSON string for model      |       |                         |
|  2. Wrap as SdkBytes payload blob        |======>|                         |
|  3. Call bedrockClient.invokeModel()     |       |  Executes foundation    |
|                                          |       |  model inference        |
|  4. Parse returned binary response blob  |<======|                         |
|  5. Convert to Java Object               |       |                         |
+------------------------------------------+       +-------------------------+
```

---

## Code Examples and Walkthroughs

### 1. Maven Dependency Configuration
Add the required AWS SDK BOM and Bedrock Runtime dependency to your `pom.xml` file:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>software.amazon.awssdk</groupId>
            <artifactId>bom</artifactId>
            <version>2.20.100</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <!-- AWS Bedrock Runtime Client -->
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>bedrockruntime</artifactId>
    </dependency>
</dependencies>
```

---

### 2. Java Service Implementation for Anthropic Claude
Here is a complete Java service class that authenticates with AWS, formats the payload for Claude 3, invokes the model, and handles exceptions:

```java
package com.example.project3.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;
import software.amazon.awssdk.services.bedrockruntime.model.AccessDeniedException;
import software.amazon.awssdk.services.bedrockruntime.model.ThrottlingException;

import java.nio.charset.StandardCharsets;

@Service
public class BedrockService {

    private final BedrockRuntimeClient bedrockClient;

    public BedrockService() {
        // Initialize the Bedrock Runtime client targeting the us-east-1 region.
        // It automatically resolves credentials from system environment variables
        // (AWS_ACCESS_KEY_ID & AWS_SECRET_ACCESS_KEY) or the local ~/.aws config profile.
        this.bedrockClient = BedrockRuntimeClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }

    public String generateSummary(String textToSummarize) {
        String modelId = "anthropic.claude-3-haiku-20240307-v1:0";

        // 1. Construct the payload matching the Anthropic Claude Messages API
        JSONObject payload = new JSONObject();
        payload.put("anthropic_version", "bedrock-2023-05-31");
        payload.put("max_tokens", 500);
        payload.put("temperature", 0.3);

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", "Provide a 3-sentence summary of the following text:\n\n" + textToSummarize);

        JSONArray messages = new JSONArray();
        messages.put(userMessage);
        payload.put("messages", messages);

        try {
            // 2. Wrap the JSON payload string as SdkBytes
            SdkBytes body = SdkBytes.fromUtf8String(payload.toString());

            // 3. Build the request
            InvokeModelRequest request = InvokeModelRequest.builder()
                    .modelId(modelId)
                    .contentType("application/json")
                    .accept("application/json")
                    .body(body)
                    .build();

            // 4. Invoke the model synchronously
            InvokeModelResponse response = bedrockClient.invokeModel(request);

            // 5. Parse the binary response payload
            String responseBody = response.body().asString(StandardCharsets.UTF_8);
            JSONObject jsonResponse = new JSONObject(responseBody);
            
            // Extract response text from Claude's message payload structure
            JSONArray contentArray = jsonResponse.getJSONArray("content");
            return contentArray.getJSONObject(0).getString("text");

        } catch (AccessDeniedException e) {
            // Raised if model access is not enabled in the AWS console or IAM permissions are invalid
            return "ERROR: Access Denied. Verify model access is enabled in the AWS Management Console.";
        } catch (ThrottlingException e) {
            // Raised if you exceed the rate limits configured for your account
            return "ERROR: Rate limit exceeded. Retrying request...";
        } catch (Exception e) {
            // Catch-all for network timeouts or client connection failures
            return "ERROR: System failure occurred: " + e.getMessage();
        }
    }
}
```

---

## Summary
- The **`bedrockruntime`** client dependency in the AWS Java SDK manages model API connections.
- Because model payloads are provider-specific, you must construct and parse the request and response payloads as raw bytes wrapping JSON strings.
- **`AccessDeniedException`** indicates missing IAM permissions or disabled console access; **`ThrottlingException`** indicates you have exceeded your rate limits.

---

## Additional Resources
- [AWS SDK for Java v2 Developer Guide](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/home.html)
- [Anthropic Claude Messages API Schema Reference](https://docs.aws.amazon.com/bedrock/latest/userguide/model-parameters-claude.html)
- [AWS Java SDK Bedrock Runtime API Index](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/bedrockruntime/package-summary.html)
