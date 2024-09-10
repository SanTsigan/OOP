package ru.nsu.tsyganov;

/**
 * Класс Main
 */
public class Main {
    /**
     * Основная функция пирамидальной сортировки
     * Сначала создаёт кучу затем достаём элементы и перемещаем в конец по одному и на каждом
     * шаге строим максимальную кучу (max-heap) из оставшихся элементов
     * @param arr
     */
    public static void heapsort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    static void heapify(int[] arr, int N, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < N && arr[l] > arr[largest]) {
            largest = l;
        }
        if (r < N && arr[r] > arr[largest]) {
            largest = r;
        }
        if (largest != i){
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, N, largest);
        }
    }

    static void printArray(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int[] arr = {5, 3, 4, 1, 2};

        Main ob = new Main();
        ob.heapsort(arr);

        printArray(arr);
    }
}