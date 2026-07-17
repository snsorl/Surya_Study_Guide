# Lab: Exploiting and Fixing Server-Side Request Forgery (SSRF)

## Objectives
- Identify SSRF vulnerabilities in Python Flask backend endpoints.
- Exploit the vulnerability to read local metadata APIs from a simulated server.
- Implement security boundaries (IP blocks, domain allowlisting, disabling redirects) to fix the vulnerability.

---

## Starter Code
Create a file named `ssrf_lab.py` and paste the following vulnerable code:

```python
from flask import Flask, request, jsonify
import requests

app = Flask(__name__)

# Simulated Internal Admin Panel (Blocked from the public internet)
INTERNAL_PANEL = {
    "system_secret_key": "FLAG{SSRF_EXPLOIT_SUCCESSFUL_99}",
    "authorized_users": ["admin@internal", "ops@internal"]
}

@app.route("/internal/system-info", methods=["GET"])
def internal_system_info():
    """Private Endpoint. Should only be visible internally."""
    return jsonify(INTERNAL_PANEL), 200

# Public Endpoint vulnerable to SSRF
@app.route("/preview", methods=["GET"])
def fetch_preview():
    # User-controlled input URL
    target_url = request.args.get("url")
    if not target_url:
        return jsonify({"error": "Missing 'url' parameter"}), 400
        
    try:
        # Vulnerable requests query with redirects enabled
        response = requests.get(target_url, allow_redirects=True, timeout=5)
        return response.text, 200
    except requests.exceptions.RequestException as e:
        return str(e), 500

if __name__ == "__main__":
    app.run(host="127.0.0.1", port=5000)
```

---

## Instructions

### Part 1: Exploit the Endpoint
1. Start the Flask application: `python ssrf_lab.py`
2. Open a browser or terminal window. Use curl or your browser to query the public `/preview` endpoint.
3. Craft a query parameter `url` that tricks the backend server into fetching the private database data from `http://127.0.0.1:5000/internal/system-info`.
4. Capture the JSON output containing the `system_secret_key` flag.

---

### Part 2: Secure the Endpoint
Refactor `ssrf_lab.py` to mitigate the vulnerability:
- **Url Scheme Check:** Only allow `https` URL patterns.
- **Domain Allowlisting:** Create a set of allowed domains: `ALLOWED_DOMAINS = {"news.ycombinator.com", "api.github.com"}`. Reject any requests whose parsed domain is not in this set.
- **Loopback and Private Subnet Blocking:** Resolve the host domain using `socket.gethostbyname()`. Block any IP starting with `127.`, `10.`, `192.168.`, or the AWS metadata endpoint `169.254.`.
- **Disable Redirects:** Pass `allow_redirects=False` inside the `requests.get()` function to prevent redirect-based validation bypasses.

---

## Definition of Done
Your SSRF lab is complete when:
- You have successfully retrieved the `FLAG{...}` string by querying the vulnerable `/preview` route.
- You have refactored the `/preview` route to implement domain check boundaries, resolved IP validation, and disabled redirects.
- Querying `/preview?url=http://127.0.0.1:5000/internal/system-info` returns a `400 Bad Request` or `403 Forbidden` response, verifying that the exploit is blocked.
