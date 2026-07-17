# Exercise: Integrating Public APIs

## Core Tasks
Build a "Post Dashboard" that fetches mockup article data from a public API, renders dynamic cards on the page, and filters them in real-time as the user types.

1. Create a directory `starters/` containing `index.html` and `app.js`.
2. Target API Endpoint: `https://jsonplaceholder.typicode.com/posts?_limit=10`
3. **Core Tasks**:
   - Write a function `fetchPosts()` using `async`/`await` to query the API.
   - Inspect `response.ok` and catch DNS/status errors inside a `try/catch` block, showing a user-facing warning if the API is offline.
   - Loop and render each post as a card showing its title and body text securely using `textContent`.
   - Bind an event listener to a text `<input>` field using the `"input"` event. Filter the displayed cards in real-time based on whether the post title contains the input text.

---

## Technical Guidelines
- Maintain an in-memory copy of the fetched posts array so you do not query the API repeatedly while filtering.
- Use CSS grid or flexbox layout to render posts.
- Use `event.target.value` to capture inputs.

---

## Definition of Done
- Opening the HTML page fetches 10 mock articles dynamically.
- Deactivating internet connection or misspelling the URL displays an explicit error status container.
- Typing into the search bar updates the visible list immediately without page refreshes.
- Element parameters are set using `textContent`, mitigating XSS concerns.
