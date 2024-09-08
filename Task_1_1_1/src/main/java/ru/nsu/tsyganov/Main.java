package ru.nsu.tsyganov;

public class Main {
    public static int[] heapsort(int[] arr){
        int N = arr.length;

        for(int i = N / 2 - 1; i>= 0; i--)
            heapify(arr, N, i);
        return arr;
    }

    static void heapify(int arr[], int N, int i){
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if(l < N && arr[l] > arr[largest])
            largest = l;
        if(r < N && arr[r] > arr[largest])
            largest = r;
        if(largest != i){
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, N, largest);
        }
    }

    static void printArray(int arr[]){
        int n = arr.length;
        for(int i=0; i<n; i++)
            System.out.print(arr[i]+" ");
        System.out.println();
    }
    public static void main(String[] args) {
        int arr[] = {12, 11, 13, 5, 6, 7};
        int n = arr.length;

        Main ob = new Main();
        ob.heapsort(arr);

        printArray(arr);
    }
}