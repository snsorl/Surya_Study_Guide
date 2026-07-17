# Overview of HTML

## Learning Objectives
- Describe the primary purpose of HTML in web browsers.
- Explain how web browsers parse HTML markup text.
- Contrast the distinct roles of HTML, CSS, and JavaScript in building front-end user interfaces.

---

## Why This Matters
Every web page you visit is structured using HTML. Whether you write clean, plain static documents or compile complex single-page apps using modern frameworks, the browser ultimately receives and parses HTML. As a full-stack developer, you cannot build effective backend API integrations without understanding how the frontend document structure works. Understanding the browser's relationship with HTML is the vital first step toward connecting user clicks, forms, and views to your backend REST endpoints.

---

## The Concept

### What is HTML?
HTML stands for **Hypertext Markup Language**. It is the standard markup language used to structure and display content on the World Wide Web.
-   **Markup Language**: Unlike programming languages (like Java or JavaScript), HTML does not contain execution logic, loops, conditional checks, or calculations. Instead, it uses **tags** to annotate (or "mark up") plain text so the browser knows how to structure it (e.g., distinguishing a header from a paragraph or a list item).
-   **Hypertext**: Links that connect web pages to one another, either within the same site or across different servers globally.

### How Browsers Parse HTML
When you navigate to a URL or load a local file, the browser performs a step-by-step process to display the page:

1.  **Bytes to Characters**: The browser reads raw bytes of data from the network or hard drive and translates them into text characters based on the file encoding (usually UTF-8).
2.  **Tokenization**: The browser processes these characters to identify start tags, end tags, attributes, and content strings.
3.  **Lexing**: The tokens are converted into objects that define their properties and rules.
4.  **Tree Construction**: The browser links these objects into a hierarchical tree structure called the **Document Object Model (DOM)**. The DOM represents the live structural blueprint of the page.
5.  **Rendering**: Once the DOM is established, the browser applies visual styling (CSS) and script logic (JavaScript) to draw the pixels onto the user's screen.

### The Front-End Web Triad
A modern web page is built using three core technologies that work together in harmony:

-   **HTML (Structure)**: Defines the semantic blueprint of the page. It determines what elements exist (e.g., text inputs, buttons, containers, tables, paragraphs).
-   **CSS (Presentation / Style)**: Controls the visual appearance of the HTML structure. It defines colors, fonts, margins, padding, positioning, layouts (like flexbox), and animations.
-   **JavaScript (Behavior / Interactivity)**: Adds programming logic and interactivity. It responds to click events, validates forms before submission, and fetches data asynchronously from backend REST APIs to update the page without refreshing.

```
       +---------------------------------------------+
       |                  Web Page                   |
       +---------------------------------------------+
       |   HTML (Structure)     ->  A button         |
       |   CSS (Style)          ->  Colored blue     |
       |   JavaScript (Logic)   ->  Sends API call   |
       +---------------------------------------------+
```

---

## Code Example
Here is a basic skeleton representing the relationship between HTML structure, CSS styling, and JavaScript behavioral logic in a single file container.

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Simple Frontend Triad Example</title>
    
    <!-- CSS Style Block -->
    <style>
        .styled-button {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .styled-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <!-- HTML Structure Block -->
    <h1>Interactive Button Demo</h1>
    <p>Click the button below to execute frontend logic.</p>
    
    <button id="myButton" class="styled-button">Click Me</button>

    <!-- JavaScript Interactive Logic Block -->
    <script>
        const button = document.getElementById('myButton');
        button.addEventListener('click', () => {
            alert('Button clicked! This is JavaScript logic in action.');
        });
    </script>

</body>
</html>
```

---

## Summary
-   **HTML** is a markup language used to outline the structure of documents displayed in web browsers.
-   Browsers parse raw HTML characters into a structured parent-child blueprint tree called the **Document Object Model (DOM)**.
-   Building modern frontends relies on a triad: **HTML** defines structure, **CSS** controls styling, and **JavaScript** enables interactive logic and API communication.

---

## Additional Resources
-   [MDN Web Docs: Getting started with the Web](https://developer.mozilla.org/en-US/docs/Learn/Getting_started_with_the_web)
-   [W3Schools: HTML Introduction](https://www.w3schools.com/html/html_intro.asp)
-   [HTML Living Standard (Official Specification)](https://html.spec.whatwg.org/)
