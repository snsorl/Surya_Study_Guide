# Building REST APIs with Flask

## Learning Objectives
- Define Flask as a Python micro-web framework and explain its architecture.
- Utilize the `@app.route()` decorator to bind endpoints to handler functions.
- Inspect request payloads (parameters, JSON data, headers) using the global `request` proxy.
- Format responses as JSON using `jsonify()` and configure standard HTTP status codes.
- Map HTTP request methods (GET, POST, PUT, DELETE) to distinct handlers.
- Execute the Flask local development server.
- Contrast the design patterns of micro-frameworks (Flask) with full-stack frameworks (Django, Spring Boot).

---

## Why This Matters
Throughout this program, you have built REST APIs in Java using enterprise frameworks like Spring Boot or micro-frameworks like Javalin. Building a REST API in Spring Boot requires declaring controllers, mapping dependencies via constructor injection, and creating data transfer objects (DTOs).

In Python, **Flask** is the classic micro-framework for building web services. Flask handles routing using Python **decorators** (annotations starting with `@`) and resolves request variables using thread-local proxies. Because Flask is a micro-framework, it has no built-in database layer, structure restrictions, or form-validation templates—allowing you to start building functional APIs with as few as ten lines of code.

---

## The Concept

### 1. Flask as a Micro-Framework
Flask provides the bare essentials for web applications: routing, request handling, and template rendering (via Jinja2). For database integration (ORM), form validation, or authentication, developers select and install separate modules (like `Flask-SQLAlchemy` or `Flask-JWT-Extended`). This is the opposite of **Django** (Python's "batteries-included" monolithic framework) and is similar in simplicity to Java's **Javalin**.

### 2. Basic Routing and Decorators
Routes are defined by decorating handler functions with `@app.route()`.
```python
from flask import Flask
app = Flask(__name__)

@app.route("/")
def home():
    return "Hello from Flask!"
```

### 3. Handling HTTP Methods
By default, Flask routes only respond to `GET` requests. To support other methods (like `POST` or `DELETE`), pass a list of methods in the decorator arguments:
```python
@app.route("/items", methods=["POST"])
def add_item():
    ...
```

### 4. Reading Requests: The `request` Proxy
Flask provides a global-looking `request` import. This is actually a thread-local proxy that safely points to the active thread's current HTTP request details:
*   **`request.args`:** Query string parameters (e.g., `?limit=10`).
*   **`request.json`:** Decoded JSON payload (equivalent to a parsed Python dictionary).
*   **`request.headers`:** HTTP request headers.
*   **`request.form`:** Key-value pairs from standard HTML form submissions.

### 5. Writing Responses: `jsonify()` and Status Codes
To return a JSON payload, wrap your data structure (usually a dictionary or list) in Flask's **`jsonify()`** helper. It serializes the data to a JSON string and automatically sets the HTTP header `Content-Type: application/json`.
- *Syntax:* `return jsonify(data), 200` (returns JSON data along with HTTP status code 200).

### 6. Running the Development Server
During development, you can start the built-in development server in debug mode:
*   **Debug Mode (`debug=True`):** Enables auto-reload (restarts the server when code files change) and displays an interactive traceback debugging console in the browser if a runtime error occurs.

---

## Code Examples

### 1. Complete Flask CRUD API Script
Here is a complete, executable Python script representing a REST API for managing a tasks dictionary:

```python
from flask import Flask, jsonify, request

app = Flask(__name__)

# Temporary in-memory database
tasks = [
    {"id": 1, "title": "Learn Python", "completed": False},
    {"id": 2, "title": "Build a Flask API", "completed": False}
]

# 1. GET: Fetch all items with optional query filter
@app.route("/tasks", methods=["GET"])
def get_tasks():
    # Reading query parameters (e.g., /tasks?completed=true)
    completed_param = request.args.get("completed")
    
    if completed_param is not None:
        is_completed = completed_param.lower() == "true"
        filtered = [t for t in tasks if t["completed"] == is_completed]
        return jsonify(filtered), 200
        
    return jsonify(tasks), 200

# 2. POST: Create a new task
@app.route("/tasks", methods=["POST"])
def create_task():
    # Verify client sent JSON payload
    if not request.is_json:
        return jsonify({"error": "Content-type must be application/json"}), 400
        
    data = request.json
    if "title" not in data:
        return jsonify({"error": "Title is required"}), 400
        
    new_task = {
        "id": len(tasks) + 1,
        "title": data["title"],
        "completed": False
    }
    tasks.append(new_task)
    return jsonify(new_task), 201

# 3. GET Single Task using path variables
@app.route("/tasks/<int:task_id>", methods=["GET"])
def get_task(task_id):
    task = next((t for t in tasks if t["id"] == task_id), None)
    if task is None:
        return jsonify({"error": "Task not found"}), 404
    return jsonify(task), 200

# Execution Entrypoint
if __name__ == "__main__":
    # Start the server on localhost, port 5000, with debugging enabled
    app.run(host="127.0.0.1", port=5000, debug=True)
```

### 2. Testing the Server using curl
With the server running, developers can hit endpoints using a terminal client:

```bash
# GET all tasks
curl http://127.0.0.1:5000/tasks

# POST a new task
curl -X POST -H "Content-Type: application/json" -d '{"title":"Refactor code"}' http://127.0.0.1:5000/tasks
```

---

## Summary
- **Flask** is a micro-framework that focuses on handling routing and request management simply, leaving other library integrations to the developer.
- Routes are mapped using the **`@app.route()`** decorator.
- Read request data using the thread-local **`request`** object (such as `request.json` and `request.args`).
- Use **`jsonify()`** to serialize response data structures and specify appropriate HTTP status codes (e.g., `201 Created` or `404 Not Found`).
- Run the server in **debug mode** (`debug=True`) to enable automatic reload and error debugging tracebacks.

---

## Additional Resources
- [Flask Official Documentation: Quickstart](https://flask.palletsprojects.com/en/stable/quickstart/)
- [Real Python: Python Web Applications With Flask](https://realpython.com/flask-by-example-part-1-project-setup/)
- [Real Python: API Development With Flask](https://realpython.com/api-integration-in-python/)
