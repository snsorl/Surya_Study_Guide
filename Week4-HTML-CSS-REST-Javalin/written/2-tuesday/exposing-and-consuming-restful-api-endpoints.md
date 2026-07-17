# Exposing and Consuming RESTful APIs

## Learning Objectives
- Differentiate between producing (server-side) and consuming (client-side) an API.
- Explain JSON serialization and deserialization processes.
- Implement Javalin endpoints that expose database models as JSON.
- Consume external API endpoints from a client application (using browser `fetch` or Java `HttpClient`).

---

## Why This Matters
Modern web architectures are divided into separate client and server applications. The server exposes a REST API, and the client consumes it. As a full-stack developer, you must understand both sides of this exchange. You need to write backend code that formats and serializes database data into standardized JSON, and you must write client-side code (in JavaScript or separate backend integrations) that requests, parses, and displays that data. Mastering this pipeline is key to building responsive, integrated web applications.

---

## The Concept

### Server-Side: Exposing Endpoints
When exposing an endpoint, the server's job is to listen for requests, retrieve the requested model data (usually from a database), and format it into a text exchange format like **JSON** (JavaScript Object Notation).
In Java, we do not write raw JSON strings manually. Instead, we use libraries like **Jackson** to convert Java Objects into JSON strings. This process is called **Serialization**.

```java
// Javalin automatically serializes Java objects to JSON using Jackson under the hood
app.get("/api/users", ctx -> {
    List<User> users = userService.getAllUsers();
    ctx.json(users); // Serializes 'users' list to a JSON array string
});
```

### Client-Side: Consuming Endpoints
Consuming an API means sending an HTTP request to an external server, waiting for the response, and parsing the body into a format your local application code can interact with. In Java, this process is called **Deserialization** (converting JSON text back into Java Objects).

#### 1. Consuming from JS (Browser Client)
Browsers use the built-in **Fetch API** to request and consume backend data asynchronously:

```javascript
fetch("http://localhost:8080/api/users")
  .then(response => response.json()) // Parse JSON response body
  .then(data => {
      console.log("Users:", data); // Use JSON data in JS logic
  });
```

#### 2. Consuming from Java (Server-to-Server Client)
Java applications can consume APIs using the native `java.net.http.HttpClient` class:

```java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/api/users"))
        .GET()
        .build();

HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
String jsonString = response.body(); // JSON response text
```

### Serialization vs. Deserialization
-   **Serialization**: Java Object -> JSON String (sending data out).
-   **Deserialization**: JSON String -> Java Object (reading data in).

```
  [ Java Object ]   ====== (Serialization) ======>   [ JSON String (Text) ]
  [ Java Object ]   <==== (Deserialization) ======   [ JSON String (Text) ]
```

---

## Code Example

Here is a complete example of a Java service class that consumes an external REST API and deserializes the JSON response into a local Java object using Jackson's `ObjectMapper`.

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiConsumer {

    // Representation of data structure returned by external API
    static class Todo {
        public int userId;
        public int id;
        public String title;
        public boolean completed;
    }

    public static void main(String[] args) {
        try {
            // 1. Initialize HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // 2. Build HTTP GET Request to JSONPlaceholder (a free mock API service)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            // 3. Send request and capture response text
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            System.out.println("HTTP Status Code: " + response.statusCode());
            String responseBodyText = response.body();
            System.out.println("Raw JSON Text: " + responseBodyText);

            // 4. Deserialize JSON text into Java Object using Jackson
            ObjectMapper mapper = new ObjectMapper();
            Todo todo = mapper.readValue(responseBodyText, Todo.class);

            // 5. Use the java object
            System.out.println("Parsed Todo Title: " + todo.title);
            System.out.println("Is Completed: " + todo.completed);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

## Summary
-   **Exposing APIs** involves setting up server endpoints to listen for requests and outputting resource data.
-   **Consuming APIs** involves requesting external endpoint resources using clients like JS `fetch` or Java `HttpClient`.
-   **Serialization** transforms Java objects into JSON data; **Deserialization** parses JSON data back into usable Java objects.
-   The Jackson **`ObjectMapper`** class is the primary utility used for reading and writing JSON data within Java programs.

---

## Additional Resources
-   [Jackson ObjectMapper Tutorial (Baeldung)](https://www.baeldung.com/jackson-objectmapper-tutorial)
-   [MDN: Using the Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch)
-   [Java 11 HttpClient Guide](https://openjdk.org/groups/net/httpclient/intro.html)
