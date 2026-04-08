import java.util.ArrayList;
import java.util.List;

/**
 * TransactionAuditApp (Renamed to Main to match Main.java)
 * This app sorts banking fees and flags high-cost outliers.
 */
class Transaction {
    String id;
    double fee;
    String timestamp;

    public Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return id + ": $" + fee + " [" + timestamp + "]";
    }
}

public class Main { // CLASS NAME MATCHES Main.java

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("TX101", 10.5, "10:00"));
        transactions.add(new Transaction("TX102", 25.0, "09:30"));
        transactions.add(new Transaction("TX103", 5.0, "10:15"));
        transactions.add(new Transaction("TX104", 65.0, "11:00")); // Outlier

        System.out.println("=== Banking Transaction Audit Tool ===");
        System.out.println("Original Batch: " + transactions);

        // Logic: Choose sorting method based on batch size
        if (transactions.size() <= 100) {
            System.out.println("\nExecuting Bubble Sort (Small Batch Optimized)...");
            bubbleSort(transactions);
        } else {
            System.out.println("\nExecuting Insertion Sort (Medium Batch Optimized)...");
            insertionSort(transactions);
        }

        System.out.println("Sorted Batch: " + transactions);

        // Compliance check
        flagOutliers(transactions, 50.0);
    }

    /**
     * Bubble Sort: Optimized with early termination flag.
     */
    public static void bubbleSort(List<Transaction> list) {
        int n = list.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    /**
     * Insertion Sort: Highly stable and efficient for medium lists.
     */
    public static void insertionSort(List<Transaction> list) {
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).fee > key.fee) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    public static void flagOutliers(List<Transaction> list, double threshold) {
        System.out.println("\n--- Audit Compliance Check ---");
        boolean found = false;
        for (Transaction t : list) {
            if (t.fee > threshold) {
                System.out.println("[ALERT] High-fee outlier detected: " + t.id + " ($" + t.fee + ")");
                found = true;
            }
        }
        if (!found) System.out.println("No high-fee outliers found. Compliance status: Green.");
    }
}