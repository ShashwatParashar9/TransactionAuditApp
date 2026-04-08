import java.util.Arrays;

/**
 * UC3: Historical Trade Volume Analysis
 * Focus: High-performance sorting (O(n log n)) and merging datasets.
 */
class Trade {
    String id;
    int volume;

    public Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

public class Main {

    public static void main(String[] args) {
        // Initial dataset for Session A
        Trade[] sessionA = {
                new Trade("T3", 500),
                new Trade("T1", 100),
                new Trade("T2", 300)
        };

        System.out.println("=== UC3: HISTORICAL TRADE VOLUME ANALYSIS ===");

        // 1. Merge Sort (Stable, Ascending)
        System.out.println("\n--- Step 1: Merge Sort (Ascending Volume) ---");
        mergeSort(sessionA, 0, sessionA.length - 1);
        System.out.println("Sorted Session A: " + Arrays.toString(sessionA));

        // 2. Quick Sort (In-place, Descending)
        Trade[] sessionB = {
                new Trade("T6", 200),
                new Trade("T4", 600),
                new Trade("T5", 400)
        };
        System.out.println("\n--- Step 2: Quick Sort (Descending Volume) ---");
        quickSort(sessionB, 0, sessionB.length - 1);
        System.out.println("Sorted Session B: " + Arrays.toString(sessionB));

        // 3. Merge Two Sorted Lists (Ascending)
        // Note: For merging, both lists must be sorted in the same direction.
        // We'll quickly sort Session B ascending first to demonstrate the merge logic.
        mergeSort(sessionB, 0, sessionB.length - 1);

        System.out.println("\n--- Step 3: Merging Morning & Afternoon Sessions ---");
        Trade[] combinedReport = mergeSessions(sessionA, sessionB);
        System.out.println("Combined Report: " + Arrays.toString(combinedReport));

        // 4. Compute Total Volume
        int totalVolume = 0;
        for (Trade t : combinedReport) totalVolume += t.volume;
        System.out.println("\nTOTAL TRADE VOLUME: " + totalVolume);
    }

    // --- MERGE SORT IMPLEMENTATION (O(n log n)) ---
    public static void mergeSort(Trade[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(Trade[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Trade[] L = new Trade[n1];
        Trade[] R = new Trade[n2];

        for (int i = 0; i < n1; ++i) L[i] = arr[left + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].volume <= R[j].volume) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // --- QUICK SORT IMPLEMENTATION (Average O(n log n)) ---
    public static void quickSort(Trade[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(Trade[] arr, int low, int high) {
        int pivot = arr[high].volume;
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            // Logic for Descending Order
            if (arr[j].volume > pivot) {
                i++;
                Trade temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Trade temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // --- MERGE TWO SESSIONS LOGIC ---
    public static Trade[] mergeSessions(Trade[] s1, Trade[] s2) {
        Trade[] combined = new Trade[s1.length + s2.length];
        int i = 0, j = 0, k = 0;
        while (i < s1.length && j < s2.length) {
            if (s1[i].volume <= s2[j].volume) combined[k++] = s1[i++];
            else combined[k++] = s2[j++];
        }
        while (i < s1.length) combined[k++] = s1[i++];
        while (j < s2.length) combined[k++] = s2[j++];
        return combined;
    }
}