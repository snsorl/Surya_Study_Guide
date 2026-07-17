# Lab Exercise: Integrating AWS Bedrock Claude API in Java with Temperature Tuning

## Learning Objectives
- Write a Java program using the AWS SDK v2 to call AWS Bedrock Runtime Client.
- Build payload request configurations for Anthropic Claude 3 messages.
- Programmatically invoke generative AI models and parse JSON text responses.
- Test and analyze the impact of the `temperature` parameter on response randomness.

---

## The Scenario
Your team wants to add automated product descriptions to **Project 3** using generative AI. You must write a Java class that calls AWS Bedrock Claude 3 APIs, sends a structured generation prompt, and parses the text response. Additionally, you will test how modifying the `temperature` parameter changes the randomness of the generated description.

---

## Starter Code
Save this starter code block as `BedrockExercise.java` in your project folder. Complete the missing sections marked with `// TODO`:

```java
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;
import software.amazon.awssdk.core.SdkBytes;

import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.charset.StandardCharsets;

public class BedrockExercise {

    public static String generateDescription(double temperatureValue) {
        String modelId = "anthropic.claude-3-haiku-20240307-v1:0";
        Region region = Region.US_EAST_1;
        String prompt = "Write a catchy 2-sentence description for a smart reusable water bottle named 'HydraPure'.";

        try (BedrockRuntimeClient client = BedrockRuntimeClient.builder()
                .region(region)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {

            // 1. Build the Claude 3 JSON message structure
            JSONObject messageContent = new JSONObject();
            messageContent.put("type", "text");
            messageContent.put("text", prompt);

            JSONObject messageObject = new JSONObject();
            messageObject.put("role", "user");
            messageObject.put("content", new JSONArray().put(messageContent));

            // 2. Prepare payload including temperatureValue
            JSONObject payload = new JSONObject();
            payload.put("anthropic_version", "bedrock-2023-05-31");
            payload.put("max_tokens", 150);
            payload.put("temperature", temperatureValue); // Set custom temperature here
            payload.put("messages", new JSONArray().put(messageObject));

            // TODO: Convert JSON payload to SdkBytes
            SdkBytes bodyBytes = SdkBytes.fromUtf8String(payload.toString());

            // TODO: Build the InvokeModelRequest
            InvokeModelRequest request = InvokeModelRequest.builder()
                    .modelId(modelId)
                    .contentType("application/json")
                    .accept("application/json")
                    .body(bodyBytes)
                    .build();

            // TODO: Invoke the model and parse the response text
            InvokeModelResponse response = client.invokeModel(request);
            String responseJsonString = response.body().asString(StandardCharsets.UTF_8);
            JSONObject responseJson = new JSONObject(responseJsonString);

            // TODO: Extract text response from Claude messages output format
            JSONArray contentArray = responseJson.getJSONArray("content");
            return contentArray.getJSONObject(0).getString("text").trim();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        // Run tests with different temperature values
        System.out.println("Running with low temperature (0.1) - Expected: Highly deterministic");
        for (int i = 1; i <= 3; i++) {
            System.out.println("Run " + i + ": " + generateDescription(0.1));
        }

        System.out.println("\nRunning with high temperature (1.0) - Expected: Creative and variable");
        for (int i = 1; i <= 3; i++) {
            System.out.println("Run " + i + ": " + generateDescription(1.0));
        }
    }
}
```

---

## Tasks

### Task 1: Complete the Java Service
1.  Add the `software.amazon.awssdk:bedrockruntime` and `org.json:json` dependencies to your project dependencies.
2.  Complete the missing parts (`// TODO`) in the starter code:
    *   Convert the payload string to `SdkBytes`.
    *   Build the `InvokeModelRequest`.
    *   Invoke the Bedrock model using the runtime client.
    *   Extract the generated text from Claude's response JSON.

---

### Task 2: Analyze Temperature Impact
1.  Run the completed `BedrockExercise` program.
2.  Observe the generated outputs:
    *   Compare the three runs at temperature `0.1`. Note how similar (or identical) the sentences are.
    *   Compare the three runs at temperature `1.0`. Note how the word choices, styles, and phrasing vary.
3.  Write a brief (3-4 sentence) summary comparing the impact of low vs. high temperature on generative model responses, and save it as `temperature_analysis.txt` in the exercise folder.

---

## Definition of Done
- `BedrockExercise.java` compiles and runs successfully.
- The console output prints results for both temperature tests (three runs each).
- The file `temperature_analysis.txt` is created, explaining the impact of temperature on model outputs.
