import java.util.ArrayList;
import java.util.List;

/**
 * UC2: Client Risk Score Ranking
 * This module handles risk prioritization using in-place sorting.
 */
class Client {
    String name;
    int riskScore;
    double accountBalance;

    public Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return String.format("%s [Risk: %d | Bal: $%.2f]", name, riskScore, accountBalance);
    }
}

public class Main {

    public static void main(String[] args) {
        // --- DATA INITIALIZATION ---
        Client[] clients = {
                new Client("Client C", 80, 5000),
                new Client("Client A", 20, 15000),
                new Client("Client B", 50, 2000),
                new Client("Client D", 80, 12000),
                new Client("Client E", 10, 3000)
        };

        System.out.println("=== UC2: CLIENT RISK RANKING SYSTEM ===");

        // --- TASK 1: BUBBLE SORT (Ascending for Demo) ---
        System.out.println("\nRunning Bubble Sort (Visualization Mode):");
        bubbleSortRiskAsc(clients);

        // --- TASK 2: INSERTION SORT (Descending Risk + Balance) ---
        // This is the "Priority Sort" for the review team
        System.out.println("\nRunning Insertion Sort (Priority Mode):");
        insertionSortPriorityDesc(clients);

        // Display Results
        for (Client c : clients) {
            System.out.println(" > " + c);
        }

        // --- TASK 3: TOP 3 IDENTIFICATION ---
        System.out.println("\n--- TOP 3 HIGH-PRIORITY REVIEWS ---");
        for (int i = 0; i < 3 && i < clients.length; i++) {
            System.out.println("RANK " + (i + 1) + ": " + clients[i]);
        }
    }

    /**
     * BUBBLE SORT logic
     * Focus: Adjacent swaps and O(1) space.
     */
    public static void bubbleSortRiskAsc(Client[] arr) {
        int n = arr.length;
        int swaps = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    // Manual Swap
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                }
            }
        }
        System.out.println("Bubble Sort finished with " + swaps + " swaps.");
    }

    /**
     * INSERTION SORT logic
     * Focus: Shifting elements and handling multiple criteria (Risk + Balance).
     */
    public static void insertionSortPriorityDesc(Client[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Client key = arr[i];
            int j = i - 1;

            // Descending logic: Shift if previous is smaller than current
            // Criteria 1: Risk Score
            // Criteria 2: Account Balance (Tie-breaker)
            while (j >= 0 && (arr[j].riskScore < key.riskScore ||
                    (arr[j].riskScore == key.riskScore && arr[j].accountBalance < key.accountBalance))) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}