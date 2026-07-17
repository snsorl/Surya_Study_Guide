# Form Elements and Attributes

## Learning Objectives
- Construct valid HTML forms using the `<form>` element.
- Identify and implement common form controls: `<input>`, `<label>`, `<select>`, `<textarea>`, `<button>`.
- Use input type attributes (e.g., `text`, `number`, `email`, `password`, `submit`).
- Utilize configuration attributes (`required`, `placeholder`, `name`, `value`).
- Associate inputs with labels using the `for` and `id` pattern to support accessibility.

---

## Why This Matters
Forms are the primary interactive channel on the web. They are how users input registration details, write search strings, and submit credit card numbers. If you do not construct your frontend forms correctly, you will fail to send accurate parameters to your backend Javalin API. Furthermore, if you do not map labels to input elements correctly, users tapping on a mobile screen will struggle to focus fields, and screen readers will have no idea what each text box represents.

---

## The Concept

### The `<form>` Element
A form is a container element that groups together interactive inputs. When a form is submitted, the browser collects the input values and packages them into an HTTP request.
-   *Syntax*: `<form action="/submit-endpoint" method="POST">`
    -   `action`: The URL endpoint where form data is sent.
    -   `method`: The HTTP method used (usually GET or POST).

### Form Controls

#### 1. `<label>`
Labels describe what an input is for.
-   **Critical Accessibility Pattern**: You must link a `<label>` to an `<input>` by matching the label's `for` attribute with the input's `id` attribute.
    -   *Example*:
        ```html
        <label for="user-email">Email Address:</label>
        <input type="email" id="user-email">
        ```
    -   *Why it matters*: Visual users can click the label text to automatically focus the linked input box. Screen readers will read the label text when a user navigates to the input.

#### 2. `<input>`
The most versatile form control. Its behavior changes completely based on the `type` attribute:
-   `type="text"`: Single-line text input.
-   `type="password"`: Obfuscates input characters.
-   `type="email"`: Enforces basic email format checks at the browser level.
-   `type="number"`: Enforces integer/float values only, displaying selector buttons.
-   `type="checkbox"`: Binary check toggles.
-   `type="radio"`: Mutually exclusive selections from a group (grouped by sharing a `name` attribute).
-   `type="submit"`: Renders a button that triggers form submission.

#### 3. `<select>` and `<option>`
Renders a dropdown selection list.
-   *Example*:
    ```html
    <select name="country">
        <option value="us">United States</option>
        <option value="ca">Canada</option>
    </select>
    ```

#### 4. `<textarea>`
Renders a multi-line text input field (useful for comments, reviews, or messages).

#### 5. `<button>`
Creates a clickable button. If placed inside a form, its default behavior is to submit the form (`type="submit"`), unless configured otherwise (e.g., `type="button"`).

### Crucial Configuration Attributes
-   **`name`**: **Mandatory for backend integration**. When the form is submitted, the browser packages values as key-value pairs matching `name=value`. If an input lacks a `name` attribute, its value is **never** sent to the server.
-   **`value`**: Sets the default, pre-filled value of the input.
-   **`placeholder`**: Displays temporary helper text inside the input box before the user types. It should not replace a label.
-   **`required`**: A boolean attribute that blocks form submission if the field is empty.

---

## Code Example

Here is a complete, accessible registration form layout utilizing standard input elements and configuration attributes.

```html
<form action="http://localhost:8080/api/register" method="POST">
    
    <!-- Text Input with Placeholder -->
    <div class="form-group">
        <label for="username">Choose a Username:</label>
        <input type="text" id="username" name="username" placeholder="e.g., devcoder" required>
    </div>

    <!-- Password Input -->
    <div class="form-group">
        <label for="password">Create Password:</label>
        <input type="password" id="password" name="password" required>
    </div>

    <!-- Select Dropdown -->
    <div class="form-group">
        <label for="role">Select Role:</label>
        <select id="role" name="role">
            <option value="developer">Developer</option>
            <option value="designer">Designer</option>
            <option value="manager">Project Manager</option>
        </select>
    </div>

    <!-- Checkbox input -->
    <div class="form-group checkbox-group">
        <input type="checkbox" id="terms" name="agreeToTerms" value="true" required>
        <label for="terms">I accept the terms and conditions</label>
    </div>

    <!-- Submit Button -->
    <div class="form-actions">
        <button type="submit">Submit Registration</button>
    </div>

</form>
```

---

## Summary
-   The **`<form>`** tag wraps input components, defining the endpoint **`action`** and HTTP **`method`**.
-   Always connect a **`<label>`** to an **`<input>`** by matching the label's **`for`** attribute with the input's **`id`** attribute.
-   The **`name`** attribute is required to send input parameters to the server; without it, the field data is ignored.
-   Configure fields using helper attributes like **`required`** for validation and **`placeholder`** for prompt text.

---

## Additional Resources
-   [MDN: HTML Forms Guide](https://developer.mozilla.org/en-US/docs/Learn/Forms/Your_first_form)
-   [W3Schools: HTML Form Elements](https://www.w3schools.com/html/html_form_elements.asp)
-   [WebAIM: Creating Accessible Forms](https://webaim.org/techniques/forms/)
