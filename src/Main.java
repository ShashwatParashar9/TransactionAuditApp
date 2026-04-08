import java.util.Arrays;
import java.util.Random;

/**
 * UC4: Portfolio Return Sorting
 * Focus: Advanced Quick Sort (Pivot selection) and Dual-criteria Sorting.
 */
class Asset {
    String ticker;
    double returnRate;
    double volatility;

    public Asset(String ticker, double returnRate, double volatility) {
        this.ticker = ticker;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return String.format("[%s | Return: %.1f%% | Vol: %.1f%%]", ticker, returnRate, volatility);
    }
}

public class Main {

    public static void main(String[] args) {
        Asset[] portfolio = {
                new Asset("TSLA", 8.0, 35.0),
                new Asset("AAPL", 12.0, 20.0),
                new Asset("GOOG", 15.0, 18.0),
                new Asset("MSFT", 12.0, 15.0) // Tie with AAPL on return, lower volatility
        };

        System.out.println("=== UC4: PORTFOLIO RETURN SORTING SYSTEM ===");

        // 1. Merge Sort (Preserve original order for ties - Stability)
        System.out.println("\n--- Step 1: Merge Sort (Ascending Return) ---");
        Asset[] mergeSorted = portfolio.clone();
        mergeSort(mergeSorted, 0, mergeSorted.length - 1);
        printArray(mergeSorted);

        // 2. Quick Sort (Descending Return + Ascending Volatility)
        // Uses Median-of-Three for pivot selection to avoid O(n^2) worst case.
        System.out.println("\n--- Step 2: Quick Sort (Desc Return + Asc Volatility) ---");
        Asset[] quickSorted = portfolio.clone();
        quickSort(quickSorted, 0, quickSorted.length - 1);
        printArray(quickSorted);

        System.out.println("\n" + "=".repeat(45));
        System.out.println("Optimization: Median-of-Three pivot used for Quick Sort.");
    }

    // --- MERGE SORT (Stability check) ---
    public static void mergeSort(Asset[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(Asset[] arr, int left, int mid, int right) {
        Asset[] L = Arrays.copyOfRange(arr, left, mid + 1);
        Asset[] R = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < L.length && j < R.length) {
            if (L[i].returnRate <= R[j].returnRate) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < L.length) arr[k++] = L[i++];
        while (j < R.length) arr[k++] = R[j++];
    }

    // --- QUICK SORT (Median-of-Three & Dual Criteria) ---
    public static void quickSort(Asset[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(Asset[] arr, int low, int high) {
        // Median-of-Three Pivot Selection
        int mid = low + (high - low) / 2;
        if (arr[mid].returnRate > arr[low].returnRate) swap(arr, mid, low);
        if (arr[high].returnRate > arr[low].returnRate) swap(arr, high, low);
        if (arr[mid].returnRate > arr[high].returnRate) swap(arr, mid, high);

        // Pivot is now at arr[high]
        Asset pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            // Logic: Descending Return RATE. If tied, Ascending VOLATILITY.
            if (arr[j].returnRate > pivot.returnRate ||
                    (arr[j].returnRate == pivot.returnRate && arr[j].volatility < pivot.volatility)) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(Asset[] arr, int i, int j) {
        Asset temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void printArray(Asset[] arr) {
        for (Asset a : arr) System.out.println(" > " + a);
    }
}