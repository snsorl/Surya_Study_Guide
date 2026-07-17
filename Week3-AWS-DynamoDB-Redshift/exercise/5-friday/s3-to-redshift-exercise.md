# Exercise: Loading Data from S3 into Redshift via COPY

**Exercise Mode:** Mode A: Implementation (Code Lab)
**Target Engine:** Amazon Redshift & Amazon S3

---

## Scenario
In enterprise data pipelines, loading millions of rows into a data warehouse using traditional `INSERT` statements is too slow. The industry standard pattern is to export transactional data as flat files (such as CSV or JSON) into Amazon S3, and then use the Redshift **`COPY`** command. The COPY command triggers compute nodes to read files from S3 in parallel, optimizing ingestion speeds.

Your task is to:
1. Create a table in Redshift.
2. Upload a sample sales CSV file to your S3 bucket.
3. Construct and run the `COPY` command in DBeaver using the correct IAM role credentials.
4. Verify the ingested records.

---

## Part A: Redshift Setup
Create a target table in your Redshift cluster named `s3_sales_load`:

```sql
DROP TABLE IF EXISTS s3_sales_load;

CREATE TABLE s3_sales_load (
    sale_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    quantity INT,
    price DECIMAL(10, 2),
    sale_date DATE
);
```

---

## Part B: Upload Data to S3

1. Create a local file named `sample_sales.csv` and paste the following content:
   ```csv
   sale_id,product_name,quantity,price,sale_date
   1001,Wireless Mouse,5,25.00,2026-07-01
   1002,Mechanical Keyboard,2,75.00,2026-07-02
   1003,Ergonomic Chair,1,150.00,2026-07-02
   1004,USB-C Cable,10,12.50,2026-07-03
   1005,Gaming Monitor,1,349.99,2026-07-04
   ```
2. Upload `sample_sales.csv` to the S3 bucket you created during Tuesday's exercise.
3. Find the S3 URI for the uploaded file (e.g. `s3://techmart-exports-.../sample_sales.csv`).

---

## Part C: Execute the COPY Command

In DBeaver, construct and execute a `COPY` statement to load the data from S3. 

> **Important:** To execute the COPY command, Redshift needs permissions to read from S3. In AWS Academy, a pre-configured IAM Role named `LabRole` is created for you. You will need its Amazon Resource Name (ARN) which usually follows this format:
> `arn:aws:iam::123456789012:role/LabRole` (replace `123456789012` with your AWS Account ID).

```sql
COPY s3_sales_load
FROM 's3://[YOUR_S3_BUCKET_NAME]/sample_sales.csv'
IAM_ROLE 'arn:aws:iam::[YOUR_AWS_ACCOUNT_ID]:role/LabRole'
FORMAT AS CSV
IGNOREHEADER 1
DELIMITER ',';
```

---

## Part D: Verification Queries
Once the COPY completes:
1. Verify the total row count (should be `5`):
   ```sql
   SELECT COUNT(*) FROM s3_sales_load;
   ```
2. Write an analytical query to calculate the total sales revenue generated:
   ```sql
   SELECT SUM(quantity * price) AS total_revenue FROM s3_sales_load;
   ```

---

## Deliverables
A file named `redshift_copy_queries.sql` containing:
1. The table creation SQL.
2. The exact `COPY` statement used (with bucket names and IAM role ARNs populated).
3. The validation queries and their output values.
