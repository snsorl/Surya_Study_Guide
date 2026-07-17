// ============================================================================
// Starter Code: Library System — DynamoDB Seed Script
// Lab: Schema Conversion Exercise — Part C
//
// Instructions:
//   1. Complete your key design in templates/ BEFORE editing this file.
//   2. Update TABLE_NAME and PK/SK helper methods to match your design.
//   3. Implement the seedAll() method to insert all Members, Books, Loans.
//   4. Implement verifyMemberLoans() to test access pattern #2.
//
// Build: mvn compile
// Run:   mvn exec:java -Dexec.mainClass="SeedLibrary"
// ============================================================================

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeedLibrary {

    // --- Configuration ---
    // Update TABLE_NAME to match your table name from key-design-worksheet.md
    private static final String TABLE_NAME = "library-system"; // TODO: Update if you chose a different name

    private static final DynamoDbClient ddb = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .endpointOverride(URI.create("http://localhost:8000")) // Remove for AWS Cloud
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();

    // =========================================================================
    // Helper Methods: Build PK / SK values
    // Update these methods to use YOUR prefix conventions from the worksheet.
    // =========================================================================

    /** Returns the PK value for a Member item. Example: "MEMBER#M-001" */
    private static String memberPk(String memberId) {
        // TODO: Return the correct PK string for a member
        return "MEMBER#" + memberId;
    }

    /** Returns the SK value for a Member metadata item. */
    private static String memberSk() {
        // TODO: Return the correct SK string for a member metadata item
        return "METADATA";
    }

    /** Returns the PK value for a Book item. */
    private static String bookPk(String isbn) {
        // TODO: Return the correct PK string for a book
        return "BOOK#" + isbn;
    }

    /** Returns the SK value for a Book item. */
    private static String bookSk() {
        // TODO: Return the correct SK string for a book
        return "DETAILS";
    }

    /** Returns the SK value for a Loan item nested under a member partition. */
    private static String loanSk(String loanId) {
        // TODO: Return the correct SK string for a loan
        return "LOAN#" + loanId;
    }

    // =========================================================================
    // Task: Create Table
    // =========================================================================

    /** Creates the DynamoDB table with the composite key schema from your worksheet. */
    public static void createLibraryTable() {
        System.out.println("[CREATE TABLE] Creating " + TABLE_NAME + "...");
        // TODO: Implement table creation using your PK and SK attribute names.
        // 1. Build a CreateTableRequest with AttributeDefinitions for PK (S) and SK (S),
        //    KeySchema (PK = HASH, SK = RANGE), and BillingMode = PAY_PER_REQUEST.
        // 2. Handle ResourceInUseException (table already exists).
        // 3. Poll describeTable() until tableStatus() == ACTIVE.
        // 4. Print "[CREATE TABLE] Table is ACTIVE."
    }

    // =========================================================================
    // Task: Seed All Data
    // =========================================================================

    /** Seeds Members, Books, and Loans into the DynamoDB table. */
    public static void seedAll() {

        // --- Seed Members ---
        System.out.println("\n[SEED] Inserting members...");

        Object[][] members = {
            {"M-001", "Alice Nguyen",  "alice@library.org", "PREMIUM"},
            {"M-002", "Ben Carter",    "ben@library.org",   "STANDARD"},
            {"M-003", "Clara Diaz",    "clara@library.org", "PREMIUM"},
        };

        for (Object[] m : members) {
            String memberId        = (String) m[0];
            String fullName        = (String) m[1];
            String email           = (String) m[2];
            String membershipTier  = (String) m[3];

            // TODO: Call ddb.putItem() to insert each member.
            // Build an Item Map<String, AttributeValue> with:
            //   "PK"              -> memberPk(memberId)
            //   "SK"              -> memberSk()
            //   "entity_type"     -> "MEMBER"
            //   "member_id"       -> memberId
            //   "full_name"       -> fullName
            //   "email"           -> email
            //   "membership_tier" -> membershipTier
        }

        // --- Seed Books ---
        System.out.println("[SEED] Inserting books...");

        Object[][] books = {
            {"978-0-06-112008-4", "To Kill a Mockingbird", "Harper Lee",          "FICTION"},
            {"978-0-7432-7356-5", "The Great Gatsby",       "F. Scott Fitzgerald", "FICTION"},
            {"978-0-14-028329-7", "The Clean Coder",         "Robert C. Martin",   "TECHNICAL"},
            {"978-0-201-63361-0", "Design Patterns",         "Gang of Four",       "TECHNICAL"},
        };

        for (Object[] b : books) {
            String isbn   = (String) b[0];
            String title  = (String) b[1];
            String author = (String) b[2];
            String genre  = (String) b[3];

            // TODO: Call ddb.putItem() to insert each book.
            // Build an Item Map with:
            //   "PK"          -> bookPk(isbn)
            //   "SK"          -> bookSk()
            //   "entity_type" -> "BOOK"
            //   "isbn"        -> isbn
            //   "title"       -> title
            //   "author"      -> author
            //   "genre"       -> genre
        }

        // --- Seed Loans ---
        System.out.println("[SEED] Inserting loans...");

        // Format: loan_id, member_id, isbn, loan_date, due_date, return_date (null = active)
        Object[][] loans = {
            {"LN-1001", "M-001", "978-0-06-112008-4", "2026-07-01", "2026-07-15", null},
            {"LN-1002", "M-001", "978-0-14-028329-7", "2026-06-15", "2026-06-29", "2026-06-28"},
            {"LN-1003", "M-002", "978-0-7432-7356-5", "2026-07-05", "2026-07-19", null},
            {"LN-1004", "M-003", "978-0-201-63361-0", "2026-07-10", "2026-07-24", null},
            {"LN-1005", "M-002", "978-0-06-112008-4", "2026-05-01", "2026-05-15", "2026-05-14"},
        };

        for (Object[] l : loans) {
            String loanId     = (String) l[0];
            String memberId   = (String) l[1];
            String isbn       = (String) l[2];
            String loanDate   = (String) l[3];
            String dueDate    = (String) l[4];
            String returnDate = (String) l[5]; // null means the loan is still active

            // TODO: Call ddb.putItem() to insert each loan.
            // Key design:
            //   PK = memberPk(memberId)  — Loans are nested UNDER their member partition
            //   SK = loanSk(loanId)
            // Include isbn, loan_date, due_date as attributes.
            // For return_date: if null, OMIT the attribute entirely (active loans).
            //   If not null, include it as an S attribute.
        }

        System.out.println("[SEED] All records inserted.");
    }

    // =========================================================================
    // Task: Verify Access Pattern #2
    // "List all active loans for a member" — using QUERY, not Scan
    // =========================================================================

    /**
     * Lists all ACTIVE loans for a given member using a Query operation.
     * Active loans are those where return_date attribute does not exist.
     * Prints loan_id, isbn, and due_date for each active loan found.
     */
    public static void verifyMemberLoans(String memberId) {
        System.out.println("\n[VERIFY] Active loans for member " + memberId + ":");
        // TODO: Implement Query.
        // 1. Build a QueryRequest with:
        //    - keyConditionExpression("PK = :pk AND begins_with(SK, :prefix)")
        //    - filterExpression("attribute_not_exists(return_date)")
        //    - expressionAttributeValues: {":pk": memberPk(memberId), ":prefix": "LOAN#"}
        // 2. Call ddb.query(request).
        // 3. Print each result: "  - {loan_id} | ISBN: {isbn} | Due: {due_date}"
    }

    // =========================================================================
    // Main
    // =========================================================================
    public static void main(String[] args) {
        createLibraryTable();
        seedAll();

        // Verify access pattern #2 for each member
        verifyMemberLoans("M-001"); // Alice — should show 1 active loan (LN-1001)
        verifyMemberLoans("M-002"); // Ben   — should show 1 active loan (LN-1003)
    }
}
