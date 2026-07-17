# Introduction to the Tech Stack

## Learning Objectives
- Define the roles of the Presentation, Logic, and Data tiers in modern software architectures.
- Describe the step-by-step lifecycle of an HTTP Request-Response transaction.
- Explain why Java is chosen as an industry standard for backend development.
- Write a clean Java simulation of a client-server API exchange.

---

## Why This Matters
Until now, you have written Java code that runs locally on your computer inside a single terminal console. But in the real world, Java is rarely run this way. 

Java is the muscle behind some of the largest systems in the world—including banking portals, retail reservation engines, and search interfaces. In these systems, Java does not talk directly to a monitor; it sits behind the scenes on a server, receiving network messages from web browsers (like Chrome or Safari), checking databases for records, and sending formatted replies back. To build full-stack applications, you must understand exactly where Java fits in the three-tier web architecture and how it communicates across a network.

---

## The Concept

### The Three-Tier Architecture
Modern enterprise applications are divided into three independent layers to ensure scalability and ease of maintenance:

```
[ PRESENTATION TIER ]        [ LOGIC TIER (BACKEND) ]        [ DATA TIER ]
 (User Browser/Client)          (Application Server)        (Database Server)
   HTML / CSS / JS   =========> Java (Spring Boot) ==========> PostgreSQL
   (Visual Layouts)  <========= (Processes Rules)  <========== (Stores Data)
```

#### 1. The Presentation Tier (Frontend)
This is what the user sees, clicks, and interacts with. It runs inside the client's web browser or mobile app.
*   **Technologies**: HTML, CSS, JavaScript, or frameworks like React, Angular, and Vue.
*   **Role**: Gather user inputs and render visual layouts. It does *not* calculate business logic (e.g., checking if a user has enough credit to make a purchase is never done on the frontend, as a user could easily bypass browser JavaScript checks).

#### 2. The Logic Tier (Backend)
This is the core engine of the application where **Java** resides. It runs on a remote application server.
*   **Technologies**: Java (typically using frameworks like Spring Boot or Jakarta EE), Node.js, C#, or Python.
*   **Role**: Execute business algorithms, enforce access controls, process payment gateways, and coordinate database updates. It acts as the gatekeeper between the user and your raw data storage.

#### 3. The Data Tier (Database)
This is where the application's records are permanently saved.
*   **Technologies**: Relational databases (e.g., PostgreSQL, MySQL, Oracle) or NoSQL databases (e.g., MongoDB, DynamoDB).
*   **Role**: Securely store, index, and retrieve data when queried by the Logic Tier.

---

### The HTTP Request-Response Lifecycle
The presentation tier and the logic tier communicate over a network using the **Hypertext Transfer Protocol (HTTP)**. This is a strict request-response protocol: the client *must* initiate the request, and the server *must* return a response.

Here is the step-by-step flow of a client-server transaction:

```
1. Browser Sends HTTP GET /api/user ===> 2. Web Server routes to Java Controller
                                               │
                                         3. Java validates session & queries DB
                                               │
4. Browser renders JSON result      <=== 5. Java returns HTTP 200 OK with JSON
```

1.  **Request Initiation**: The user clicks a button on the browser. The frontend packages this action into an **HTTP Request** (containing headers, a URL path like `/api/products`, and an action method like `GET` or `POST`) and transmits it over the internet.
2.  **Processing**: The backend server receives the request. Java reads the input values, runs business calculations, checks permissions, and queries the database for records if needed.
3.  **Formatting**: Java converts raw database records into a standard data-interchange format, typically **JSON (JavaScript Object Notation)**.
4.  **Response**: The server wraps the JSON data inside an **HTTP Response** (containing an status code like `200 OK` or `404 Not Found`) and transmits it back.
5.  **Rendering**: The frontend receives the response, parses the JSON data, and updates the visual interface for the user.

---

## Code Example: Backend Simulator
The following Java program simulates the entire three-tier architecture in a single compilable file. It contains a mock client (frontend), a mock controller (backend Java logic), and a mock database (data storage):

```java
import java.util.HashMap;
import java.util.Map;

// ==================== DATA TIER (MOCK DATABASE) ====================
class InventoryDatabase {
    private static final Map<String, Double> products = new HashMap<>();

    static {
        // Populating database records
        products.put("LAPTOP-909", 1200.00);
        products.put("PHONE-101", 699.99);
        products.put("TABLET-55", 350.50);
    }

    public static Double queryPrice(String productId) {
        System.out.println("[DB] Querying price for: " + productId);
        return products.get(productId);
    }
}

// ==================== LOGIC TIER (MOCK BACKEND CONTROLLER) ====================
class ProductController {
    
    /**
     * Simulates receiving an HTTP GET Request and returning a formatted Response.
     * 
     * @param requestPath The simulated URL path (e.g. "/products?id=LAPTOP-909")
     * @return Simulated JSON Response String
     */
    public String handleGetProductRequest(String requestPath) {
        System.out.println("[BACKEND] Received HTTP request on path: " + requestPath);

        // 1. Parse query parameter out of URL path
        if (requestPath == null || !requestPath.contains("?id=")) {
            return "{ \"status\": \"400\", \"error\": \"Bad Request: Missing ID parameter\" }";
        }

        String productId = requestPath.split("\\?id=")[1];

        // 2. Business Logic Guard: Validate product code structure
        if (productId.isEmpty()) {
            return "{ \"status\": \"400\", \"error\": \"Bad Request: Empty Product ID\" }";
        }

        // 3. Query the Data Tier
        Double price = InventoryDatabase.queryPrice(productId);

        // 4. Determine response status and format simulated JSON payload
        if (price == null) {
            return "{ \"status\": \"404\", \"error\": \"Product not found in database\" }";
        } else {
            return "{ \"status\": \"200 OK\", \"data\": { \"id\": \"" + productId + "\", \"price\": " + price + " } }";
        }
    }
}

// ==================== PRESENTATION TIER (SIMULATED CLIENT) ====================
public class StackDemo {
    public static void main(String[] args) {
        ProductController apiServer = new ProductController();

        System.out.println("=== Transaction 1: Fetching valid product ===");
        // Client initiates simulated HTTP GET Request
        String clientRequest1 = "/products?id=LAPTOP-909";
        String serverResponse1 = apiServer.handleGetProductRequest(clientRequest1);
        System.out.println("[CLIENT] Browser received HTTP response:\n" + serverResponse1);

        System.out.println("\n=== Transaction 2: Fetching missing product ===");
        String clientRequest2 = "/products?id=CAMERA-88";
        String serverResponse2 = apiServer.handleGetProductRequest(clientRequest2);
        System.out.println("[CLIENT] Browser received HTTP response:\n" + serverResponse2);
    }
}
```

---

## Summary
- Modern systems use a **three-tier architecture**: Presentation (Visual), Logic (Business Rules), and Data (Storage).
- **Java** acts as theLogic Tier, processing rules and managing access to databases.
- Communication between tiers is managed via **HTTP Request-Response** loops.
- Java dominates backend development because of its concurrency handling, compile-time type safety, and large corporate support framework.

---

## Additional Resources
- [What is a Three-Tier Application? - IBM Cloud](https://www.ibm.com/topics/three-tier-architecture)
- [How HTTP Works - MDN Web Docs](https://developer.mozilla.org/en-US/docs/Web/HTTP/Overview)
- [Java Backend Roadmap - roadmap.sh](https://roadmap.sh/java)