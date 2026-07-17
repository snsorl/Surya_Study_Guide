# Loading Data into Redshift: The COPY Command

## Learning Objectives
- Execute bulk data load operations using the Amazon Redshift `COPY` command.
- Configure IAM roles to authorize Redshift clusters to access S3 data files.
- Adapt `COPY` options to load CSV, JSON, and Parquet file formats.
- Troubleshoot load failures using the `STL_LOAD_ERRORS` system log table.

## Why This Matters
Data warehouses (like Amazon Redshift) are designed to store massive datasets for analysis. When setting up a warehouse, you need to populate it with data from operational databases, web logs, or transactional archives. 

Inserting records row by row using SQL `INSERT` statements is inefficient. Inserting millions of rows this way can take hours and cause cluster performance bottlenecks. To load large datasets, Redshift uses the **`COPY`** command. The `COPY` command reads files (stored in S3) in parallel using the cluster's compute nodes and writes the data directly to disk. Understanding how to use the `COPY` command, configure S3 access, and handle data formatting errors is essential for building efficient data pipelines.

## The Concept

### The COPY Command Architecture
The `COPY` command leverages Redshift’s Massively Parallel Processing (MPP) architecture to load data in parallel:

```
+-------------------------------------------------------------+
|                     AMAZON S3 BUCKET                        |
|  - Multipart source data files (e.g., sales-part-1.csv, ...) |
+-------------------------------------------------------------+
                              ||
        (Parallel download    || using IAM Role Auth)
        across slices)        v
+-------------------------------------------------------------+
|                  AMAZON REDSHIFT CLUSTER                    |
|  - Compute Node 1 (Downloads and writes partition 1)        |
|  - Compute Node 2 (Downloads and writes partition 2)        |
+-------------------------------------------------------------+
```

When you execute a `COPY` command, the leader node coordinates the download. Each slice on the compute nodes downloads a portion of the files from S3 simultaneously and writes the data to its local columnar storage. This parallel design makes loading data fast and efficient.
*Best Practice*: To maximize parallel throughput, split your source data into multiple small files of equal size (e.g., matching the number of slices in your cluster) rather than loading a single large file.

---

### Authentication and IAM Roles
To load files from S3, the Redshift cluster must be authorized to access the target bucket. 
- **IAM Role**: The recommended authentication method. You attach an IAM role containing the `AmazonS3ReadOnlyAccess` policy to your Redshift cluster.
- **Syntax**: In the `COPY` command, you provide the Amazon Resource Name (ARN) of the attached role. The database cluster assumes the role to read the files, eliminating the need to save credentials in database scripts.

---

### File Formats and Copy Options
The `COPY` command supports several data formats:

- **CSV**: Text files with delimited fields. You can specify custom delimiters (e.g., commas or tabs) and options like `IGNOREHEADER 1` to skip column header rows.
- **JSON**: Structured document formats. You must specify a `jsonpaths` mapping file if the JSON keys do not match the target table columns exactly.
- **Parquet**: An efficient columnar storage format. Parquet files are compressed, and Redshift loads them quickly because the file structure maps directly to Redshift's columnar layout.

---

### Error Handling and STL_LOAD_ERRORS
If a row in a source file contains invalid data (such as a string in a numeric column or a malformed date), the `COPY` command terminates the load by default to prevent data corruption.

#### Error Management Options:
- **MAXERROR**: Allows you to specify a threshold of acceptable row errors before the command fails (e.g., `MAXERROR 10`).
- **NOLOAD**: Runs the command as a dry run to check for data formatting errors without writing data to the table.
- **Troubleshooting**: If a load fails, Redshift logs the error details in a system table named **`STL_LOAD_ERRORS`**. You query this table to identify the exact line number and column where the formatting error occurred.

## Code Examples

### Scenario: Table Setup
We want to load sales records into a target table named `sales_load_target`.

```sql
CREATE TABLE sales_load_target (
    sale_id INT PRIMARY KEY,
    product_sku VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    sale_amount DECIMAL(10, 2) NOT NULL,
    sale_date TIMESTAMP NOT NULL
);
```

### Example 1: Loading CSV Data from S3
This command loads sales data from a CSV file stored in an S3 bucket. We skip the first header line:

```sql
COPY sales_load_target
FROM 's3://enterprise-data-warehouse-2026/sales_data.csv'
IAM_ROLE 'arn:aws:iam::123456789012:role/RedshiftS3ReadRole'
FORMAT AS CSV
DELIMITER ','
IGNOREHEADER 1
DATEFORMAT 'auto'
TIMEFORMAT 'auto';
```

### Example 2: Loading JSON Data with JsonPaths
If loading a JSON file where the keys do not match the column names exactly, provide a JSONPaths file to map the attributes:

```sql
COPY sales_load_target
FROM 's3://enterprise-data-warehouse-2026/sales_data.json'
IAM_ROLE 'arn:aws:iam::123456789012:role/RedshiftS3ReadRole'
FORMAT AS JSON 's3://enterprise-data-warehouse-2026/sales_paths.json';
```

### Example 3: Running a Validation Dry Run
Run a dry run with error tolerance to check for data anomalies:

```sql
COPY sales_load_target
FROM 's3://enterprise-data-warehouse-2026/sales_corrupt.csv'
IAM_ROLE 'arn:aws:iam::123456789012:role/RedshiftS3ReadRole'
FORMAT AS CSV
IGNOREHEADER 1
MAXERROR 5
NOLOAD; -- Validates syntax and formats without saving data
```

### Example 4: Querying Load Errors
If the load fails, query the `STL_LOAD_ERRORS` table to identify the issue:

```sql
SELECT 
    query, 
    starttime, 
    filename, 
    line_number, 
    colname, 
    err_reason 
FROM stl_load_errors 
ORDER BY starttime DESC 
LIMIT 1;
```

This query returns the file path, the exact line number, and the reason for the failure (e.g., "Invalid digit: A"), allowing you to fix the data and run the load again.

## Summary
- The **COPY** command loads data from S3 into Redshift tables in parallel, optimizing performance for large datasets.
- Authorize the load operation by passing an **IAM Role ARN** attached to the Redshift cluster.
- The `COPY` command supports **CSV**, **JSON**, and **Parquet** formats, using configuration parameters to manage formatting options.
- If a load fails, query the **`STL_LOAD_ERRORS`** system log table to inspect formatting issues.

## Additional Resources
- [Amazon Redshift Database Developer Guide - COPY Command Reference](https://docs.aws.amazon.com/redshift/latest/dg/r_COPY.html)
- [AWS Best Practices for Loading Data in Amazon Redshift](https://docs.aws.amazon.com/redshift/latest/dg/t_Loading_data.html)
- [Querying STL_LOAD_ERRORS to Troubleshoot Data Loading](https://docs.aws.amazon.com/redshift/latest/dg/r_STL_LOAD_ERRORS.html)
