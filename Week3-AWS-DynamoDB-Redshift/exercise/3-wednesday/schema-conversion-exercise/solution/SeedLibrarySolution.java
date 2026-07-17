// ============================================================================
// SOLUTION: Library System — DynamoDB Seed Script
// Lab: Schema Conversion Exercise — Part C
// FOR INSTRUCTOR USE ONLY — Do not distribute to trainees.
// ============================================================================

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeedLibrarySolution {

    private static final String TABLE_NAME = "library-system";

    private static final DynamoDbClient ddb = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .endpointOverride(URI.create("http://localhost:8000")) // Remove for AWS Cloud
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();

    // --- Key Helpers (Single-Table Design) ---
    private static String memberPk(String memberId) { return "MEMBER#" + memberId; }
    private static String memberSk()                { return "METADATA"; }
    private static String bookPk(String isbn)       { return "BOOK#" + isbn; }
    private static String bookSk()                  { return "DETAILS"; }
    private static String loanSk(String loanId)     { return "LOAN#" + loanId; }

    private static AttributeValue str(String v) {
        return AttributeValue.builder().s(v).build();
    }

    public static void createLibraryTable() {
        System.out.println("[CREATE TABLE] Creating " + TABLE_NAME + "...");
        try {
            CreateTableRequest request = CreateTableRequest.builder()
                    .tableName(TABLE_NAME)
                    .attributeDefinitions(
                            AttributeDefinition.builder().attributeName("PK").attributeType(ScalarAttributeType.S).build(),
                            AttributeDefinition.builder().attributeName("SK").attributeType(ScalarAttributeType.S).build()
                    )
                    .keySchema(
                            KeySchemaElement.builder().attributeName("PK").keyType(KeyType.HASH).build(),
                            KeySchemaElement.builder().attributeName("SK").keyType(KeyType.RANGE).build()
                    )
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build();

            ddb.createTable(request);

            boolean active = false;
            while (!active) {
                DescribeTableResponse desc = ddb.describeTable(
                        DescribeTableRequest.builder().tableName(TABLE_NAME).build());
                active = desc.table().tableStatus() == TableStatus.ACTIVE;
                if (!active) Thread.sleep(500);
            }
            System.out.println("[CREATE TABLE] Table is ACTIVE.");

        } catch (ResourceInUseException e) {
            System.out.println("[CREATE TABLE] Table already exists. Proceeding.");
        } catch (DynamoDbException | InterruptedException e) {
            System.err.println("[CREATE TABLE] Error: " + e.getMessage());
        }
    }

    public static void seedAll() {

        // --- Seed Members ---
        System.out.println("\n[SEED] Inserting members...");
        Object[][] members = {
            {"M-001", "Alice Nguyen",  "alice@library.org", "PREMIUM"},
            {"M-002", "Ben Carter",    "ben@library.org",   "STANDARD"},
            {"M-003", "Clara Diaz",    "clara@library.org", "PREMIUM"},
        };
        for (Object[] m : members) {
            Map<String, AttributeValue> item = new HashMap<>();
            item.put("PK",              str(memberPk((String) m[0])));
            item.put("SK",              str(memberSk()));
            item.put("entity_type",     str("MEMBER"));
            item.put("member_id",       str((String) m[0]));
            item.put("full_name",       str((String) m[1]));
            item.put("email",           str((String) m[2]));
            item.put("membership_tier", str((String) m[3]));
            ddb.putItem(PutItemRequest.builder().tableName(TABLE_NAME).item(item).build());
            System.out.println("  Inserted member " + m[0]);
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
            Map<String, AttributeValue> item = new HashMap<>();
            item.put("PK",          str(bookPk((String) b[0])));
            item.put("SK",          str(bookSk()));
            item.put("entity_type", str("BOOK"));
            item.put("isbn",        str((String) b[0]));
            item.put("title",       str((String) b[1]));
            item.put("author",      str((String) b[2]));
            item.put("genre",       str((String) b[3]));
            ddb.putItem(PutItemRequest.builder().tableName(TABLE_NAME).item(item).build());
            System.out.println("  Inserted book " + b[0]);
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
            String returnDate = (String) l[5]; // null = active

            Map<String, AttributeValue> item = new HashMap<>();
            item.put("PK",          str(memberPk(memberId))); // Nested under member partition
            item.put("SK",          str(loanSk(loanId)));
            item.put("entity_type", str("LOAN"));
            item.put("loan_id",     str(loanId));
            item.put("member_id",   str(memberId));
            item.put("isbn",        str(isbn));
            item.put("loan_date",   str(loanDate));
            item.put("due_date",    str(dueDate));
            // Active loans: omit return_date attribute entirely
            // Returned loans: include return_date as an S attribute
            if (returnDate != null) {
                item.put("return_date", str(returnDate));
            }
            ddb.putItem(PutItemRequest.builder().tableName(TABLE_NAME).item(item).build());
            System.out.println("  Inserted loan " + loanId + " for member " + memberId);
        }

        System.out.println("[SEED] All 12 records inserted.");
    }

    public static void verifyMemberLoans(String memberId) {
        System.out.println("\n[VERIFY] Active loans for member " + memberId + ":");

        Map<String, AttributeValue> exprValues = new HashMap<>();
        exprValues.put(":pk",     str(memberPk(memberId)));
        exprValues.put(":prefix", str("LOAN#"));

        QueryRequest request = QueryRequest.builder()
                .tableName(TABLE_NAME)
                // begins_with() is a DynamoDB reserved function; use it in key condition expressions
                .keyConditionExpression("PK = :pk AND begins_with(SK, :prefix)")
                // Filter out returned loans (those that have a return_date attribute)
                .filterExpression("attribute_not_exists(return_date)")
                .expressionAttributeValues(exprValues)
                .build();

        QueryResponse response = ddb.query(request);
        List<Map<String, AttributeValue>> items = response.items();

        if (items.isEmpty()) {
            System.out.println("  No active loans found.");
        }
        for (Map<String, AttributeValue> item : items) {
            String loanId  = item.get("loan_id").s();
            String isbn    = item.get("isbn").s();
            String dueDate = item.get("due_date").s();
            System.out.println("  - " + loanId + " | ISBN: " + isbn + " | Due: " + dueDate);
        }
        System.out.println("  [Total active: " + items.size() + "]");
    }

    public static void main(String[] args) {
        createLibraryTable();
        seedAll();
        verifyMemberLoans("M-001"); // Alice — 1 active loan (LN-1001)
        verifyMemberLoans("M-002"); // Ben   — 1 active loan (LN-1003)
        verifyMemberLoans("M-003"); // Clara — 1 active loan (LN-1004)
    }
}
