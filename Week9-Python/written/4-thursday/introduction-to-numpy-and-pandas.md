# Introduction to NumPy and Pandas

## Learning Objectives
- Define the architectural purpose of NumPy and Pandas in data engineering.
- Implement multi-dimensional array operations using NumPy's `ndarray`.
- Leverage vectorized calculations and broadcasting to avoid standard loop latency.
- Construct and parse tabular data structures using Pandas `Series` and `DataFrame`.
- Read and write structured CSV/JSON data using Pandas parser methods.
- Filter, aggregate, and slice data frames using `.loc`, `.iloc`, and `.groupby()`.

---

## Why This Matters
As a full-stack developer, your backend pipelines often compile raw transaction logs, user activities, or metrics. In Java, performing database-style manipulations (like group-by groupings, sorting, and aggregations) on custom collections requires verbose Streams API calls, custom map definitions, or heavy dependencies like Spark.

In Python, the combination of **NumPy** and **Pandas** acts as a powerful in-memory database and matrix engine. NumPy executes operations in compiled C, bypasses Python interpreter loops, and enables lightning-fast calculations. Pandas wraps this power in a clean, table-like API called a **DataFrame**. Learning the basics of these libraries allows you to build data analysis steps directly into your full-stack applications.

---

## The Concept

### 1. NumPy: High-Performance Vectorization
NumPy (Numerical Python) provides the **`ndarray`** (N-dimensional array) structure.
*   **Vectorization:** Applying an operation to a whole array simultaneously without explicit `for` loops. It runs at compiled C speeds.
*   **Broadcasting:** The ability of NumPy to perform arithmetic operations on arrays of different shapes during arithmetic operations.

### 2. Pandas: Structured Data Analysis
Pandas builds on top of NumPy, introducing two key data structures:
*   **`Series`:** A 1D labeled array (like a single column in an Excel sheet).
*   **`DataFrame`:** A 2D labeled data structure (like a SQL table or Excel sheet).

### 3. Basic DataFrame Inspection
When you load data into a DataFrame (`df`), use these functions to inspect its structure:
*   `df.head(n)`: Returns the first `n` rows.
*   `df.info()`: Prints column names, data types, and non-null counts.
*   `df.describe()`: Computes summary statistics (mean, std, min, max, quartiles) for numeric columns.

### 4. Selection and Slicing in Pandas
*   **`.loc[row_label, col_label]`:** Selects data using explicit index labels.
*   **`.iloc[row_position, col_position]`:** Selects data using integer-based positions.

---

## Code Examples

### 1. Vectorization vs. Loop Performance (NumPy)
Let's see how vectorization replaces manual loops:

```python
import numpy as np
import time

# Create a list and array of 1,000,000 integers
size = 1000000
python_list = list(range(size))
numpy_array = np.arange(size)

# Calculate sum using a Python loop
start = time.time()
python_list_result = [x + 2 for x in python_list]
print(f"Python list elapsed: {time.time() - start:.5f} seconds")

# Calculate sum using NumPy vectorization (No loop!)
start = time.time()
numpy_array_result = numpy_array + 2
print(f"NumPy array elapsed: {time.time() - start:.5f} seconds")
```

**Output (Approximate):**
```text
Python list elapsed: 0.08210 seconds
NumPy array elapsed: 0.00120 seconds  # Over 60x faster!
```

---

### 2. Data Filtering and Aggregation (Pandas)
Let's simulate analyzing product transaction data:

```python
import pandas as pd

# 1. Load mock transaction data (representing entries from our Java db)
data = {
    "transaction_id": [1001, 1002, 1003, 1004, 1005],
    "category": ["Electronics", "Books", "Electronics", "Clothing", "Books"],
    "price": [800.0, 15.5, 1200.0, 45.0, 22.0],
    "quantity": [1, 2, 1, 3, 1]
}

df = pd.DataFrame(data)

# 2. Add a calculated column
df["total_spent"] = df["price"] * df["quantity"]

# 3. Filter transactions where total spent is greater than 30 USD
expensive_transactions = df[df["total_spent"] > 30.0]
print("Expensive Transactions:")
print(expensive_transactions)

# 4. Group by category and compute total sales sum
sales_by_category = df.groupby("category")["total_spent"].sum().reset_index()
print("\nSales Summary by Category:")
print(sales_by_category)
```

**Output:**
```text
Expensive Transactions:
   transaction_id     category   price  quantity  total_spent
0            1001  Electronics   800.0         1        800.0
2            1003  Electronics  1200.0         1       1200.0
3            1004     Clothing    45.0         3        135.0

Sales Summary by Category:
      category  total_spent
0        Books         53.0
1     Clothing        135.0
2  Electronics       2000.0
```

---

## Summary
- **NumPy** uses the `ndarray` to run vectorized mathematical operations at C speeds, bypassing slow Python loops.
- **Pandas** introduces the `DataFrame` for tabular data structures and database-style manipulation.
- Inspect data frames using **`head()`**, **`info()`**, and **`describe()`**.
- Index data using **`.loc`** (label-based) and **`.iloc`** (index position-based).
- Group data using **`.groupby()`** to perform sum, mean, and count aggregations.

---

## Additional Resources
- [NumPy Quickstart Guide](https://numpy.org/doc/stable/user/quickstart.html)
- [Pandas Getting Started Tutorials](https://pandas.pydata.org/docs/getting_started/intro_tutorials/index.html)
- [Real Python: Pandas DataFrame: Working With Tabular Data](https://realpython.com/pandas-dataframe/)
