# Introduction to REST

## Learning Objectives
- Explain the history and purpose of Representational State Transfer (REST).
- Identify and describe the five core architectural constraints of REST.
- Explain the Richardson Maturity Model and its levels (0 to 3).
- Contrast REST with alternative API architectural patterns.

---

## Why This Matters
"RESTful" is one of the most widely used—and widely misunderstood—terms in software engineering. Many developers refer to any API that outputs JSON as a "REST API," even if it violates every core architectural principle of the web. Understanding the formal constraints of REST and the Richardson Maturity Model will lift your design skills from simple endpoint script writing to building professional, enterprise-grade APIs. This knowledge is crucial as you build scalable, high-performance distributed systems.

---

## The Concept

### What is REST?
REST stands for **Representational State Transfer**. It was introduced in 2000 by Roy Fielding in his doctoral dissertation. REST is not a framework, library, or protocol. It is an **architectural style** for building distributed hypermedia systems. 

An API is only truly "RESTful" if it conforms to a set of core design constraints.

### Core Constraints of REST

1.  **Client-Server Architecture**: Separates the user interface concerns (the client) from the data storage concerns (the server). This allows clients and servers to evolve independently (e.g., you can swap your Java backend for a Go backend without modifying your JavaScript frontend).
2.  **Statelessness**: Every request from client to server must contain all the information necessary to understand and complete the request. The server must not store any session context about the client.
3.  **Cacheability**: Responses must define themselves as cacheable or non-cacheable. This allows clients or intermediate proxies to reuse cached responses, reducing latency and server load.
4.  **Layered System**: A client cannot tell whether it is connected directly to the end server or to an intermediate proxy, load balancer, or security gateway. This simplifies scaling and security management.
5.  **Uniform Interface**: The interface between client and server must be standardized. This is achieved via:
    -   *Identification of resources* (using URLs).
    -   *Manipulation of resources through representations* (e.g., JSON/XML payloads).
    -   *Self-descriptive messages* (each message contains headers explaining how to parse it).
    -   *HATEOAS* (Hypermedia As The Engine Of Application State - the client navigates the API purely using links returned in the responses).

### The Richardson Maturity Model
Developed by Leonard Richardson, this model rates an API's adherence to REST principles on a scale from Level 0 to Level 3:

```
[ Level 3: Hypermedia Controls (HATEOAS) ]
                    ^
[ Level 2: HTTP Verbs (GET, POST, PUT, DELETE) ]
                    ^
[ Level 1: Resources (Individual URLs for things) ]
                    ^
[ Level 0: The Swamp of POX (Single endpoint, RPC) ]
```

#### Level 0: The Swamp of POX (Plain Old XML)
The API has a single entry point (usually a POST method to a single URL like `/api/service`) and uses custom request payloads to invoke different actions. 
-   *Example*: SOAP services or basic RPC endpoints.

#### Level 1: Resources
The API introduces multiple endpoints representing distinct resources, but still uses a single HTTP method (usually POST) for all interactions.
-   *Example*: `/api/books` and `/api/books/42`, but all requests are POST.

#### Level 2: HTTP Verbs
The API uses distinct resources AND correct HTTP verbs with standard status codes. Most commercial APIs target Level 2.
-   *Example*: `GET /api/books/42` to read, `DELETE /api/books/42` to delete.

#### Level 3: Hypermedia Controls (HATEOAS)
The pinnacle of REST. The server response not only contains data but also returns clickable links indicating what the client can do next. The client does not need to hardcode API URLs; they discover them dynamically.
-   *Example*: A response for a bank account resource includes a link to `deposit` and a link to `withdraw`.

---

## Code Example

Here is a conceptual comparison of a Level 0 API response versus a Level 3 REST API response.

### Level 0 (Swamp of POX / RPC Style)
```http
POST /api/service HTTP/1.1
Content-Type: application/json

{
  "action": "getAccountDetails",
  "accountId": 1042
}

---
Response:
{
  "status": "success",
  "balance": 1500.0,
  "owner": "Jane Doe"
}
```

### Level 3 (HATEOAS / True REST Style)
```http
GET /api/v1/accounts/1042 HTTP/1.1
Accept: application/json

---
Response:
{
  "accountId": 1042,
  "balance": 1500.0,
  "owner": "Jane Doe",
  "_links": {
    "self": { "href": "/api/v1/accounts/1042" },
    "deposit": { "href": "/api/v1/accounts/1042/deposits" },
    "withdraw": { "href": "/api/v1/accounts/1042/withdrawals" }
  }
}
```

---

## Summary
-   **REST** is an architectural design style for distributed systems, created by Roy Fielding in 2000.
-   The core constraints of REST include **client-server decoupling**, **statelessness**, **cacheability**, **layered system design**, and a **uniform interface**.
-   The **Richardson Maturity Model** measures API maturity: Level 0 (RPC), Level 1 (individual URLs), Level 2 (HTTP verbs), and Level 3 (HATEOAS).
-   Most modern API systems operate at **Level 2**, matching resource URLs with relevant HTTP actions and status codes.

---

## Additional Resources
-   [Fielding's Dissertation: Architectural Styles and the Design of Network-based Software Architectures](https://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm)
-   [Martin Fowler: Richardson Maturity Model](https://martinfowler.com/articles/richardsonMaturityModel.html)
-   [Rest API Tutorial: What is REST?](https://restfulapi.net/)
