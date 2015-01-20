import java.io.IOException;
import java.util.Scanner;

/**
 * This is a program that visits all permutations of n (not necessarily
 * distinct) objects.
 * @author Tejashri Naikar
 *
 */

public class Permutation2 {

	static int count;
	static int choice;
	
	/**
	 * This method depending on the verbose option either prints all the permutations 
	 * or just counts the permutation
	 * @param A = Array of elements 
	 * @param v = verbose
	 */
	public static void visit(int[] A, int v) {
		if (v > 0) {
			for (int i = 0; i < A.length; i++) {
				if (A[i] != 0) {
					System.out.print(A[i]+" ");
				}
			}
			System.out.println("");
		}
		count++;
	}
	
	/**
	 * This method generates all the possible permutations. This uses the lexicographic approach
	 * to permute the elements. It takes as an input the sorted array, the from the last element 
	 * starts comparing the elements till it finds two adjacent ascending numbers. then it finds 
	 * a maximum length then swaps the elements. Then reverses all the elements from here. 
	 * @param A = Array of elements 
	 * @param n = number of elements
	 * @param v = verbose
	 */
	public static void permute(int[] A, int n, int v) {
		int j = 0, l = 0, k = 0;
		while (true) {
			visit(A, v);
			j = n - 2;
			while (true) {
				if (A[j] < A[j + 1]) {
					break;
				}
				if (A[j] >= A[j + 1]) {
					j--;
				}
				if (j == -1) {
					return;
				}

			}

			for (l = n - 1; l >= 0; l--) {
				if (A[j] < A[l]) {
					swap(A, j, l);

					break;
				}
			}

			k = j + 1;
			l = n - 1;
			while (k < l) {
				swap(A, k, l);
				k = k + 1;
				l = l - 1;
			}

		}
	}
	
	/**
	 * This method swaps the elements of array which are at m and n positions;    
	 * @param A = Array of elements
	 * @param m = position in array
	 * @param n = position in array
	 */
	public static void swap(int[] A, int m, int n) {
		int temp = 0;
		temp = A[m];
		A[m] = A[n];
		A[n] = temp;
	}

	/**
	 * This method calls itself recursively and divides array into smallest
	 * possible array.
	 * 
	 * @param A
	 *            = array containing numbers
	 * @param p
	 *            = start of the array
	 * @param r
	 *            = end of the array
	 * @return
	 * @throws Exception
	 */
	static void MergeSort(int[] A, int p, int r) {
		if (p < r) {
			int q = (p + r) / 2;
			MergeSort(A, p, q);
			MergeSort(A, q + 1, r);
			Merge(A, p, q, r);
		}
	}

	/**
	 * This method compares the values of A, and the elements under comparison
	 * are sorted and put into the destination array. This uses an auxiliary
	 * array B.
	 * 
	 * @param A
	 *            = array containing numbers
	 * @param p
	 *            = start of the array
	 * @param q
	 *            = mid point of the array
	 * @param r
	 *            = end of the array
	 */
	static void Merge(int[] A, int p, int q, int r) {
		int ls = q - p + 1;
		int rs = r - q;
		int[] B = new int[ls + rs];
		for (int i = p; i <= r; i++) {
			B[i - p] = A[i];
		}
		int i = 0;
		int j = ls;
		for (int k = p; k <= r; k++) {
			if ((j >= (ls + rs)) || ((i < ls) && (B[i] <= B[j])))
				A[k] = B[i++];
			else
				A[k] = B[j++];
		}
		return;
	}

	/**
	 * This is the main method which will be called on execution. This method
	 * takes first input from the console which will 
	 * n = number of elements 
	 * v = verbose
	 * Then in the second input from the console it takes the elements of array
	 * A = elements of Array
	 * This method first calls the merge sort to sort the input array and then calls 
	 * permute method.
	 * @param args = inline arguments if any
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out
				.println("Enter the number of elements n and the verbose v followed by single space ");
		String[] inputs = sc.nextLine().split(" ");
		int n = Integer.parseInt(inputs[0]);
		int v = Integer.parseInt(inputs[1]);
		System.out.println("Enter the " + n
				+ " numbers with only single space after each element ");
		String secondInput = sc.nextLine();
		String[] numbers = secondInput.split(" ");
		int[] A = new int[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			A[i] = Integer.parseInt(numbers[i]);
		}

		long beg = System.currentTimeMillis();
		MergeSort(A, 0, A.length - 1);	

		permute(A, A.length, v);
		long end = System.currentTimeMillis();
		System.out.println(count + " " + (end - beg));
	}

}
