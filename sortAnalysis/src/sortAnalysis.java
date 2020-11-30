/* CSCI 112 - 900
Prof. Herbert
Program to test runtimes of various sorting methods
Last edited by Pat Doyle 4/26/20
 */

import java.util.*;
import java.util.Scanner;

public class sortAnalysis {
    public static void main(String[] args) {
        //create count variable to represent the arrays size
        int count = 100000000;

        //create large array
        int[] array = new int[count];

        //asks user what sorting method to use
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter 1 for selection sort, 2 for insertion sort");
        System.out.println("3 for quick sort, 4 for merge sort");
        int method = scnr.nextInt();

        for(int i = 0; i < 100; i++){

            //for loop iterates array and assigns each element a random number
            for(int j = 0; j < array.length; j++){

                array[j] = randomFill();
            }//end inner for

            //Each branch takes a start time before method is called and an end time when it completes
            //Each branch utilizes a different sort method depending on the input
            //the difference is the total time for each method to sort
            if (method == 4){
                long startTime = System.nanoTime();
                int[] temp = new int [array.length];
                mergeSort(array, temp, 0, array.length - 1);
                long endTime = System.nanoTime();
                long timeElapsed = endTime - startTime;

                System.out.println(timeElapsed);
            }
            else if (method == 3){
                long startTime = System.nanoTime();
                quickSort(array, 0, array.length - 1);
                long endTime = System.nanoTime();
                long timeElapsed = endTime - startTime;

                System.out.println(timeElapsed);
            }
            else if (method == 2){
                long startTime = System.nanoTime();
                insertionSort(array, count);
                long endTime = System.nanoTime();
                long timeElapsed = endTime - startTime;

                System.out.println(timeElapsed);
            }
            else if (method == 1){
                long startTime = System.nanoTime();
                selectionSort(array, count);
                long endTime = System.nanoTime();
                long timeElapsed = endTime - startTime;

                System.out.println(timeElapsed);
            }
            else {
                System.out.println("Invalid input");
            }
        }//end out for loop

    }//end main

    public static void selectionSort(int[] a, int count) {
        int spot; //location in array to insert minimum from remainder of list
        int minimum; //location of minimum value in remainder of list
        for (spot = 0; spot < count; spot++) {
            //minimum should be the first spot in the remainder of the list
            minimum = spot;

            for (int i = spot + 1; i < count; i++) //compares strings at [i] and [minimum]
            {
                if (a[i] < a[minimum]) {
                    minimum = i;
                }//end if
            }//end nested for loop

            //swap a[spot] and a[minimum]
            int temp = a[spot];
            a[spot] = a[minimum];
            a[minimum] = temp;
        }//end outer loop
    }// end selection sort

    public static void insertionSort(int a[], int count)
    {
        int i; //pointer to item in the unsorted list
        int j; //pointer to item in the sorted list

        for (i = 1; i < count; i++) //iterate entire array
        {
            int element = a[i]; //next value of unsorted list to be inserted into the sorted list

            for(j = i - 1; j >= 0 && element <= a[j]; j--) //go backwards through array
            {
                a[j + 1] = a[j]; //moves current item to the right
            }//end nested for loop
            a[j + 1] = element;//insert value in correct spot
        }//end for
    }//end insertionSort()

    public static void quickSort(int[] a, int startIndex, int endIndex) {
        int pivotIndex;      // the index of pivot returned by the quicksort partition

        // if the set has more than one element, then partition
        if (startIndex < endIndex) {
            // partition and return the pivotIndex
            pivotIndex = partition(a, startIndex, endIndex);
            // quicksort the low set
            quickSort(a, startIndex, pivotIndex - 1);
            // quiclsort the high set
            quickSort(a, pivotIndex + 1, endIndex);
        } // end if
    } // end quickSort()
    //************************************************************************

    // This method performs quicksort partitioning on a set of elements in an array.
    public static int partition(int[] a, int startIndex, int endIndex) {

        int pivotIndex;             // the index of the chosen pivot element
        int pivot;                  // the value of the chosen pivot
        int midIndex = startIndex;  // boundary element between high and low sets

        // select the center element in the set as the pivot by integer averaging
        pivotIndex = (startIndex + endIndex) / 2;
        pivot = a[pivotIndex];

        // put the pivot at the end of the set so it is out of the way
        swap(a, pivotIndex, endIndex);

        // iterate the set, up to but not including last element
        for (int i = startIndex; i < endIndex; i++) {
            // if a[i] is less than the pivot
            if (a[i] < pivot) {

                // put a[i] in the low half and increment current Index
                swap(a, i, midIndex);
                midIndex = midIndex + 1;
            } // end if
        } // end for

        // partitioning complete -- move pivot from end to middle
        swap(a, midIndex, endIndex);

        // return index of pivot
        return midIndex;

    } // end partition

    // This method swaps two elements in an integer array
    public static void swap(int[] a, int first, int second) {

        int c;  // a catalyst variable used for the swap

        c = a[first];
        a[first] = a[second];
        a[second] = c;

    } // end Swap()

    public static void mergeSort(int[] a, int[] temp, int low, int high) {
        //  low is the low index of the part of the array to be sorted
        //  high is the high index of the part of the array to be sorted

        int mid;  // the middle of the array – last item in low half

        // if high > low then there is more than one item in the list to be sorted
        if (high > low) {

            // split into two halves and mergeSort each part

            // find middle (last element in low half)
            mid = (low+high)/2;
            mergeSort(a, temp, low, mid );
            mergeSort(a, temp, mid+1, high);

            // merge the two halves back together, sorting while merging
            merge(a, temp, low, mid, high);
        } // end if

        return;
    }// end mergerSort()
    //********************************************************************


    /* This method merges the two halves of the set being sorted back together.
     * the low half goes from a[low] to a[mid]
     * the high half goes from a[mid+1] to a[high]
     * (High and low only refer to index numbers, not the values in the array.)
     *
     * The work of sorting occurs as the two halves are merged back into one
     * sorted set.
     *
     * This version of mergesort copies the set to be sorted into the same
     * locations in a temporary array, then sorts them as it puts them back.
     * Some versions of mergesort sort into the temporary array then copy it back.
     */
    public static void merge(int[] a, int[] temp, int low, int mid, int high) {
        //  low is the low index of the part of the array to be sorted
        //  high is the high index of the part of the array to be sorted
        //  mid is the middle of the array – last item in low half

        // copy the two sets from a[] to the same locations in the temporary array
        for (int i = low; i <= high; i++) {
            temp[i] = a[i];
        }

        //set up necessary pointers
        int lowP = low;         // pointer to current item in low half
        int highP = mid + 1;    // pointer to current item in high half
        int aP = low;           // pointer to where each item will be put back in a[]

        // while the pointers have not yet reached the end of either half
        while ((lowP <= mid) && (highP <= high)) {

            // if current item in low half <= current item in high half
            if (temp[lowP] <= temp[highP]) {
                // move item at lowP back to array a and increment low pointer
                a[aP] = temp[lowP];
                lowP++;
            } else {
                // move item at highP back to array a and increment high pointer
                a[aP] = temp[highP];
                highP++;
            } // end if..else

            // increment pointer for location in original array
            aP++;
        } // end while

        /* When the while loop is done, either the low half or the high half is done.
         * We now simply move back everything in the half not yet done.
         * Remember, each half is already in order itself.
         */

        // if lowP has reached end of low half, then low half is done, move rest of high half
        if (lowP > mid)
            for (int i = highP; i <= high; i++) {
                a[aP] = temp[i];
                aP++;
            } // end for
        else // high half is done, move rest of low half

            for (int i = lowP; i <= mid; i++) {
                a[aP] = temp[i];
                aP++;
            }// end for

        return;
    } // end merge()


    public static int randomFill() {

        Random rand = new Random();
        int randomNum = rand.nextInt(100001);
        return randomNum;

    }//end randomFill
}//end class