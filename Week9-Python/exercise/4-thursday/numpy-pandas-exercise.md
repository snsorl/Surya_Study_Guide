# Exercise: Data Analysis with NumPy and Pandas

## Objectives
- Load and parse tabular CSV datasets using **Pandas**.
- Perform high-performance vector calculations using **NumPy** math functions.
- Filter DataFrames using conditional masks.
- Group and aggregate data fields using `.groupby()`.
- Export analytical summaries to new CSV files.

---

## Starter Dataset
1. Create a file named `products.csv` in your workspace folder.
2. Populate the file with the following raw data:
   ```csv
   product_id,name,category,price,quantity
   P100,Laptop,Electronics,1200.00,5
   P101,Mouse,Electronics,25.00,20
   P102,Keyboard,Electronics,85.00,12
   P103,Java Book,Books,45.00,15
   P104,Python Book,Books,50.00,8
   P105,T-Shirt,Clothing,18.00,30
   P106,Jeans,Clothing,45.00,25
   ```

---

## Instructions

1. Create a script named `data_analysis.py`.
2. Load the library modules: `import pandas as pd` and `import numpy as np`.
3. Read the `products.csv` file into a Pandas DataFrame: `df = pd.read_csv("products.csv")`.
4. Perform the following calculations:
   - **Step 1: Calculate Total Inventory Value:** Add a calculated column `total_value` representing `price` multiplied by `quantity`.
   - **Step 2: Basic Stats with NumPy:** 
     - Convert the `price` column to a NumPy array.
     - Calculate the **average (mean)** product price, the **maximum** price, and the **minimum** price using NumPy math functions. Print these values to the console.
   - **Step 3: Filter Low Cost Items:** Filter the DataFrame to display products whose price is **below the calculated average price**.
   - **Step 4: GroupBy Category:** 
     - Group the data by `category`.
     - Aggregate the groups to calculate the **sum of quantities** and the **sum of total_value** per category.
     - Save this summary DataFrame.
5. Export the aggregated category summary back to a new file named `category_summary.csv`.

---

## Definition of Done
Your lab is complete when:
- Running `python data_analysis.py` executes successfully.
- The console displays:
  - Calculated mean, max, and min product prices.
  - The filtered list of products priced below the average.
- A new file named `category_summary.csv` is created containing the aggregated sums for Books, Clothing, and Electronics categories:
  ```csv
  category,quantity,total_value
  Books,23,1075.0
  Clothing,55,1665.0
  Electronics,37,7520.0
  ```
