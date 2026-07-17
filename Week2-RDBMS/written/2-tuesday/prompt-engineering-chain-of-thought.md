# Prompt Engineering: Chain-of-Thought (CoT)

## Learning Objectives
- Define the Chain-of-Thought (CoT) prompting technique.
- Explain the cognitive mechanics of how step-by-step reasoning prompts improve Large Language Model (LLM) accuracy.
- Construct CoT prompts to generate complex SQL queries.
- Analyze how structuring step-by-step query logic prevents common relational errors.

---

## Why This Matters
As you start writing more complex database queries—linking multiple tables together, filtering records, and calculating averages—you will find that coding errors become harder to spot. If your SQL query compiles and runs without crashing, it is very easy to assume it is correct. However, if your join condition is slightly off, the database might return incorrect aggregations, leading to reporting bugs or application logic failures.

Using AI assistants to write SQL queries is common, but asking simple questions like *"Give me a SQL query to find active users with orders over $50"* often yields incorrect results when your schema is complex.

**Chain-of-Thought (CoT) prompting** solves this. By instructing the AI (and yourself) to break down the query generation process into a sequence of logical steps (e.g., identifying tables, determining key linkages, drafting filters, and then writing the SQL), you dramatically increase the likelihood of generating accurate, bug-free SQL queries.

---

## The Concept

### 1. What is Chain-of-Thought Prompting?
Chain-of-Thought prompting is a technique that encourages Large Language Models to output a sequence of intermediate reasoning steps before generating the final answer. 

By default, if an LLM is asked a complex query, it attempts to generate the code block immediately. Because LLMs generate text token-by-token based on probability, generating the output directly can cause the model to make logical errors or skip validation checks.

When you force the model to write out its reasoning first (e.g., *"Let's think step by step: First, let's identify the tables we need..."*), the model uses its own reasoning text as active context. This "thinking space" allows the model to align its parameters and produce a significantly more accurate final code block.

### 2. Using CoT for SQL Query Generation
When prompting an AI to write SQL, a structured CoT framework should force the model to walk through these four steps:
1.  **Identify Target Attributes:** What columns do we need in the final output?
2.  **Locate Data Sources:** Which tables contain these columns?
3.  **Determine Relational Links:** How are these tables connected (what are the primary and foreign keys)?
4.  **Formulate Filters and Aggregations:** What are the conditional limits (`WHERE` or `HAVING`)?

---

## Prompting Examples

Let's look at the difference between a standard query prompt and a Chain-of-Thought prompt using our e-commerce schema (Customers, Orders, Products, and OrderItems).

### Standard Prompt (Poor Results)
```
Prompt:
Write a SQL query that returns the names of customers who have bought products costing more than $100.
```
*Risk:* The AI might generate a query that performs a direct join between `customers` and `products`, ignoring the `orders` and `order_items` junction tables, resulting in invalid SQL.

---

### Chain-of-Thought Prompt (Highly Accurate Results)
```
Prompt:
Write a SQL query to return the first_name and email of customers who have purchased any product priced above 100.00.

Please follow this Chain-of-Thought structure in your response:
1. Identify the final columns and their source tables.
2. Step-by-step, trace the path of relationships (foreign keys) needed to join these tables.
3. Write down the filters that must be applied.
4. Finally, generate the complete, standardized SQL query.

Do not use any emojis in your response.
```

#### The AI Response using CoT:
```
1. Target Columns & Sources:
   - customer_name (first_name) from 'customers' table.
   - email from 'customers' table.
   - price from 'products' table (needed for the filter).

2. Relational Linkages:
   - To connect 'customers' to 'products', we must traverse the full schema path:
     - 'customers' connects to 'orders' via customers.customer_id = orders.customer_id
     - 'orders' connects to 'order_items' via orders.order_id = order_items.order_id
     - 'order_items' connects to 'products' via order_items.product_id = products.product_id

3. Filters:
   - Filter condition: products.price > 100.00.
   - We must also apply DISTINCT to avoid duplicate customer rows if they bought multiple qualifying items.

4. SQL Query:
   SELECT DISTINCT c.first_name, c.email
   FROM customers c
   JOIN orders o ON c.customer_id = o.customer_id
   JOIN order_items oi ON o.order_id = oi.order_id
   JOIN products p ON oi.product_id = p.product_id
   WHERE p.price > 100.00;
```

By requesting this breakdown, you can verify the logic of the join traversal before running the query against your database.

---

## Summary
-   **Chain-of-Thought (CoT)** prompting requires LLMs to generate intermediate reasoning steps before delivering the final answer.
-   Writing out reasoning provides "thinking space" in the model's context window, reducing logical errors.
-   For **SQL generation**, CoT prompts should explicitly separate target columns, relational joins, filter conditions, and the final SQL code.
-   Evaluating the AI's reasoning steps allows developers to spot logical bugs in queries.

---

## Additional Resources
-   [Chain-of-Thought Prompting Elicits Reasoning in Large Language Models](https://arxiv.org/abs/2201.11903)
-   [Prompt Engineering Guide: Chain-of-Thought Prompting](https://www.promptingguide.ai/techniques/cot)
-   [LearnSQL: How to Structure SQL Queries](https://learnsql.com/blog/how-to-write-a-complex-sql-query/)
