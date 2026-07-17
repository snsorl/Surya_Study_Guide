# Exercise: Semantic HTML Recipe Website

## Objective
Build a structurally sound, semantically rich HTML document representing a recipe page. Use modern HTML5 layout tags and integrate a user submission form with appropriate attributes.

---

## Scenario
You are developing the frontend layout for "GourmetShare," a collaborative cooking platform. Before writing any CSS presentation styles or JS dynamic scripts, you must build the semantic backbone of the recipe viewport.

---

## Core Tasks

### 1. Document Skeleton
- Create an HTML file named `recipe.html`.
- Set up a valid HTML5 structure including `<!DOCTYPE html>`, `<html>`, `<head>`, and `<body>`.
- Configure the metadata: character encoding, viewport scaling rules, and a descriptive page title.
- Reference: [html-document-structure-and-dom.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/3-wednesday/html-document-structure-and-dom.md)

### 2. Semantic Elements layout
- Structure your body using these elements:
  - `<header>`: Containing the logo and main navigation links (`<nav>`).
  - `<main>`: Containing the primary recipe article.
  - `<article>`: Wrapping the recipe content.
  - `<section>`: Separating the recipe description, ingredients list (`<ul>`), and cooking instructions (`<ol>`).
  - `<aside>`: Showing author information and links to related recipes.
  - `<footer>`: Containing copyright notices and copyright dates.
- Reference: [common-tags.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/3-wednesday/common-tags.md)

### 3. Add Recipe Submission Form
- Add a new section containing a `<form>` element.
- The form should collect:
  1. Recipe Title (`type="text"` input).
  2. Cooking Time (`type="number"` input).
  3. Recipe Ingredients (`<textarea>`).
- Associate all inputs with a `<label>` using the `for`/`id` mapping pattern.
- Mark all input fields as `required`. Add appropriate `name` attributes to all fields.
- Reference: [form-elements-and-attributes.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/3-wednesday/form-elements-and-attributes.md)

---

## Definition of Done
- A `recipe.html` file exists in your workspace directory.
- The HTML validates without syntax errors (correct nesting, closed tags).
- No inline styles are used.
- All form inputs carry `name` fields and are correctly mapped to visible `<label>` tags.
- The page structure is strictly semantic, using layout landmarks instead of nested generic `<div>` tags.
