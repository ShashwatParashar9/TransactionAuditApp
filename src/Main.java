
import java.util.Arrays;

/**
 * UC5: Account ID Lookup in Transaction Logs
 * Focus: Comparing Linear Search (O(n)) vs. Binary Search (O(log n)).
 */
class Log {
    String accountId;
    String transactionId;

    public Log(String accountId, String transactionId) {
        this.accountId = accountId;
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return accountId + " (" + transactionId + ")";
    }
}

public class Main {

    public static void main(String[] args) {
        // Initial dataset (Unsorted)
        Log[] logs = {
                new Log("accB", "TX-01"),
                new Log("accA", "TX-02"),
                new Log("accB", "TX-03"),
                new Log("accC", "TX-04"),
                new Log("accD", "TX-05"),
                new Log("accB", "TX-06")
        };

        String targetId = "accB";

        System.out.println("=== UC5: TRANSACTION LOG LOOKUP SYSTEM ===");

        // 1. Linear Search (Works on unsorted data)
        System.out.println("\n--- Step 1: Linear Search (First/Last Occurrence) ---");
        linearSearch(logs, targetId);

        // 2. Sorting (Pre-requisite for Binary Search)
        System.out.println("\n--- Step 2: Sorting Logs for Optimization ---");
        // We sort by accountId to enable Binary Search
        Arrays.sort(logs, (a, b) -> a.accountId.compareTo(b.accountId));
        for (Log l : logs) System.out.print(l.accountId + " ");
        System.out.println();

        // 3. Binary Search + Occurrence Count
        System.out.println("\n--- Step 3: Binary Search (Optimized Lookup) ---");
        binarySearchWithCount(logs, targetId);
    }

    /**
     * Linear Search: Sequential check.
     * Complexity: O(n)
     */
    public static void linearSearch(Log[] logs, String target) {
        int firstIndex = -1;
        int lastIndex = -1;
        int comparisons = 0;

        for (int i = 0; i < logs.length; i++) {
            comparisons++;
            if (logs[i].accountId.equals(target)) {
                if (firstIndex == -1) firstIndex = i;
                lastIndex = i;
            }
        }

        System.out.println("Target: " + target);
        System.out.println("First Occurrence: Index " + firstIndex);
        System.out.println("Last Occurrence: Index " + lastIndex);
        System.out.println("Total Comparisons: " + comparisons + " (Complexity: O(n))");
    }

    /**
     * Binary Search: Divide and Conquer.
     * Complexity: O(log n)
     */
    public static void binarySearchWithCount(Log[] logs, String target) {
        int low = 0;
        int high = logs.length - 1;
        int comparisons = 0;
        int foundIndex = -1;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            int res = target.compareTo(logs[mid].accountId);

            if (res == 0) {
                foundIndex = mid;
                break; // Found one instance
            } else if (res > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (foundIndex != -1) {
            // Counting occurrences by scanning left and right of foundIndex
            int count = 1;
            int left = foundIndex - 1;
            while (left >= 0 && logs[left].accountId.equals(target)) {
                count++;
                left--;
            }
            int right = foundIndex + 1;
            while (right < logs.length && logs[right].accountId.equals(target)) {
                count++;
                right++;
            }

            System.out.println("Target: " + target);
            System.out.println("Match found at mid-index: " + foundIndex);
            System.out.println("Total Occurrences: " + count);
            System.out.println("Search Comparisons: " + comparisons + " (Complexity: O(log n))");
        } else {
            System.out.println("Account ID not found.");
        }
    }
}