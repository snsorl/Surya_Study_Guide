# Exercise: Parsing CSV Data with String Methods

## Objectives
- Manipulate text blocks using built-in string functions (`.strip()`, `.split()`, `.join()`).
- Format tabular logs dynamically using aligned **f-strings**.
- Parse raw multi-line strings cleanly, discarding leading and trailing whitespaces.

---

## The Scenario
Your Spring Boot backend exported a log file containing comma-separated transaction records. The data is messy, contains random spaces, and must be formatted into a clean console report for managers.

---

## Instructions

1. Create a script named `csv_parser.py`.
2. Define a multi-line string variable containing the raw data:
   ```python
   raw_csv = """
   transaction_id, customer_email, product_sku, total_usd
     tx_1001,  alice@gmail.com,   LAP-ASUS-99,   1200.50  
   tx_1002, bob@yahoo.com, MOUSE-LOGI-12, 25.00 
     tx_1003,charlie@gmail.com ,  KEY-CHERRY-5, 89.90  
   """
   ```
3. Parse this string:
   - Split the multi-line string into individual lines (hint: split by newline `\n` or use `.splitlines()`).
   - Filter out empty lines.
   - For the first line (headers): strip whitespace and split by `,` to extract column headers.
   - For the remaining data lines:
     - Split each line by `,`.
     - Strip all leading and trailing whitespace from each field.
     - Convert the `total_usd` string field into a floating-point number.
     - Convert the `transaction_id` field to uppercase.
4. Output the results in an aligned table using f-strings:
   - Header column widths should be: `ID` (10 spaces), `Email` (20 spaces), `Product` (15 spaces), `Total` (10 spaces).
   - Decimal alignment: The total cost must show 2 decimal places with a dollar sign prefix (e.g. `$1200.50`).

---

## Definition of Done
Your lab is complete when:
- Running `python csv_parser.py` prints the formatted table:
  ```text
  -------------------------------------------------------------
  ID         | Email                | Product         | Total     
  -------------------------------------------------------------
  TX_1001    | alice@gmail.com      | LAP-ASUS-99     | $1200.50  
  TX_1002    | bob@yahoo.com        | MOUSE-LOGI-12   | $25.00    
  TX_1003    | charlie@gmail.com    | KEY-CHERRY-5    | $89.90    
  -------------------------------------------------------------
  ```
- The output contains no leading/trailing blank margins or misaligned divider borders.
