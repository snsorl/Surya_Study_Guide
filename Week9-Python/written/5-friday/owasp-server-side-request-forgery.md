# OWASP A10: Server-Side Request Forgery (SSRF)

## Learning Objectives
- Define Server-Side Request Forgery (SSRF) and outline its execution mechanism.
- Analyze how attackers leverage SSRF to query cloud metadata endpoints and internal APIs.
- Identify SSRF vulnerabilities in Python code using the `requests` library.
- Implement security mitigations against SSRF including URL allowlisting, disabling redirects, and internal network IP blocks.
- Detail the security enhancements of AWS IMDSv2 over IMDSv1.

---

## Why This Matters
Modern full-stack applications often interact with external third-party APIs (e.g., retrieving payment updates, scraping link previews, or loading images from user-submitted URLs).

If your application takes a URL from a user and makes a backend HTTP request to that URL without validation, it is vulnerable to **Server-Side Request Forgery (SSRF)**. Because the backend server executes the request, it acts as a proxy for the attacker. The server can bypass firewall blocks and query internal-only services, private databases, or highly sensitive cloud instance metadata endpoints (which contain cloud credentials). Protecting against SSRF is a critical skill for cloud-native backend engineers.

---

## The Concept

### 1. What is SSRF?
Server-Side Request Forgery (SSRF) occurs when a web application fetches a remote resource from a user-supplied URL without validating the destination.

```
+----------+      User URL: http://169.254.169.254/      +------------+
| Attacker | -----------------------------------------> | Vulnerable |
+----------+                                            | Web Server |
                                                        +------------+
                                                               |
                                     (Bypasses firewall        | Fetches URL
                                      to reach local network)  v
                                                        +------------+
                                                        | AWS IMDS   | (Returns Cloud
                                                        | Metadata   |  Credentials!)
                                                        +------------+
```

### 2. Targeting Internal Infrastructure
Attackers exploit SSRF to reach resources that are not exposed to the public internet:
*   **Loopback Addresses (`127.0.0.1`, `localhost`):** Reaching administration consoles or debugging endpoints running locally on the server.
*   **Internal Private Subnets (`10.x.x.x`, `192.168.x.x`):** Scanning database endpoints and private microservices.
*   **Cloud Instance Metadata Services (IMDS):** AWS, GCP, and Azure expose metadata services at the link-local IP address **`169.254.169.254`**. If an attacker tricks a cloud server into querying this endpoint, they can retrieve temporary IAM role security credentials and hijack the cloud environment.

### 3. SSRF Mitigations
To protect your python backend services from executing rogue requests:
1.  **Strict URL Allowlisting:** Only permit requests to a pre-defined list of trusted domain names. Reject raw IP addresses.
2.  **Network-Level Restraints:** Configure firewalls or network route tables to block the application runtime from sending requests to internal private IP ranges or the link-local metadata address (`169.254.169.254`).
3.  **Disable HTTP Redirects:** Attackers can submit a URL pointing to a public server they control (e.g., `http://public-server.com`) and return an HTTP `302 Redirect` pointing to `http://127.0.0.1`. Ensure your HTTP client does not follow redirects automatically.
4.  **AWS IMDSv2 Migration:** AWS IMDSv2 mitigates SSRF by requiring a session-oriented token exchange (POST request to obtain a token, which is then passed in the GET header). Since SSRF attacks typically use simple GET requests, IMDSv2 blocks them from reading metadata.

---

## Code Examples

### 1. A Vulnerable Python SSRF Endpoint
Here is an endpoint that takes a user-provided URL and fetches its contents using the Python `requests` library:

```python
# --- VULNERABLE CODE ---
import requests
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route("/fetch-preview", methods=["GET"])
def fetch_preview():
    # User controls this URL parameter completely
    target_url = request.args.get("url")
    
    try:
        # DANGEROUS: Fetches user-provided URL with no validation and redirects enabled
        response = requests.get(target_url, allow_redirects=True, timeout=5)
        return jsonify({"content": response.text}), 200
    except requests.exceptions.RequestException as e:
        return jsonify({"error": str(e)}), 400
```
*Attack Path:* Attacker calls `/fetch-preview?url=http://169.254.169.254/latest/meta-data/` to compromise the cloud credentials.

---

### 2. A Secured SSRF Mitigation Implementation
To secure the preview service, we validate that the domain belongs to an allowlist, disable redirects, and prevent parsing internal loopback/private IP addresses:

```python
# --- SECURED CODE ---
from urllib.parse import urlparse
import socket
import requests
from flask import Flask, request, jsonify, abort

app = Flask(__name__)

# Only allow these trusted domains
ALLOWED_DOMAINS = {"api.github.com", "api.weather.com", "news.ycombinator.com"}

def is_safe_url(url):
    parsed = urlparse(url)
    
    # 1. Enforce HTTPS only
    if parsed.scheme != "https":
        return False
        
    # 2. Verify domain is in the allowlist
    if parsed.netloc not in ALLOWED_DOMAINS:
        return False
        
    try:
        # 3. Resolve the domain to its IP address
        resolved_ip = socket.gethostbyname(parsed.netloc)
        
        # 4. Block local, loopback, and private address spaces
        # (This prevents DNS rebinding attacks pointing to localhost)
        if resolved_ip.startswith(("127.", "10.", "192.168.", "169.254.", "172.16.")):
            return False
    except socket.gaierror:
        return False
        
    return True

@app.route("/fetch-preview", methods=["GET"])
def fetch_preview_secure():
    target_url = request.args.get("url")
    if not target_url or not is_safe_url(target_url):
        return jsonify({"error": "Invalid or unauthorized URL"}), 400

    try:
        # Secure request parameters: No redirects, strict 3-second timeout
        response = requests.get(target_url, allow_redirects=False, timeout=3)
        return jsonify({"content": response.text}), 200
    except requests.exceptions.RequestException as e:
        return jsonify({"error": "Failed to retrieve preview content"}), 400
```

---

## Summary
- **SSRF** allows an attacker to force a server to make internal network requests on their behalf.
- Attackers use SSRF to target private networks and query the **`169.254.169.254`** cloud metadata server to steal server credentials.
- Secure Python applications by **disabling redirects**, **allowlisting target domains**, and verifying that resolved IPs are not private or loopback addresses.
- Migrate AWS instances to **IMDSv2** to enforce session token validation on metadata checks.

---

## Additional Resources
- [OWASP Top 10: Server-Side Request Forgery (SSRF)](https://owasp.org/Top10/A10_2021-Server-Side_Request_Forgery_SSRF/)
- [PortSwigger Web Security Academy: SSRF Guide and Labs](https://portswigger.net/web-security/ssrf)
- [AWS Documentation: Transitioning to Using Instance Metadata Service v2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html)
