# Exercise: AWS Console Exploration and S3 Bucket Setup

**Exercise Mode:** Mode B: Conceptual / Walkthrough (Design/Analysis)
**Platform:** AWS Management Console (via AWS Academy)

---

## Scenario
As a cloud database administrator, your first day on the job requires orienting yourself with the AWS Management Console, checking active database services, and provisioning storage resources for incoming system files.

---

## Part A: AWS Console Orientation

1. Log into your **AWS Academy Learner Lab** console.
2. In the AWS Console Search bar, locate and open each of these service landing pages:
   - **EC2** (Elastic Compute Cloud)
   - **RDS** (Relational Database Service)
   - **DynamoDB** (NoSQL Key-Value Database)
   - **S3** (Simple Storage Service)
3. Answer the following questions based on your navigation:
   - **Q1:** What is the primary AWS Region shown in the top-right corner of your console menu?
   - **Q2:** Navigate to the **RDS Console**. Under "Databases", are there any active databases running in your account currently?
   - **Q3:** In the **DynamoDB Console**, what are the names of the menu options listed under the "Data catalog" or "Tables" sections on the left-side navigation pane?
   - **Q4:** What are the three service categories that RDS is classified under (e.g. databases, compute, network)?

---

## Part B: Create and Configure an S3 Bucket

S3 is object storage. You will create a bucket to store application exports and configuration files.

1. Navigate to the **S3 Console** and click **Create bucket**.
2. **Bucket name:** Enter a globally unique name using the format: `techmart-exports-[yourname]-[date]`.
3. **Region:** Match the region of your primary sandbox (e.g., `us-east-1`).
4. **Object Ownership:** Keep *ACLs disabled* (recommended best practice).
5. **Block Public Access settings:** Keep *Block all public access* checked to prevent unauthorized access.
6. Click **Create bucket**.
7. Create a plain text file named `aws_notes.txt` on your computer. Write one sentence in it: *"Successfully completed S3 Console exploration on AWS Academy."*
8. Open your new S3 bucket in the console, click **Upload**, add `aws_notes.txt`, and complete the upload.
9. Verify that the file appears in your bucket object listing.

---

## Deliverables
Provide a text file or markdown document named `aws_orientation_answers.txt` containing:
1. Your answers to the 4 orientation questions in Part A.
2. A screenshot or copy of the S3 Object URL of the uploaded `aws_notes.txt` file (e.g. `s3://techmart-exports-.../aws_notes.txt`).
