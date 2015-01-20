


package com.utd.ds.algo;

/**
 * Implementation of Even Better Merge Sort using Exernal sort, Divide and Conquer and
 * recursion method. 
 * This has a better run time compared to Better Merge Sort of
 * Merge Sort. 
 * This implementation divides the elements into parts in recursive
 * manner till it splits the numbers to single terms. Then start merging these
 * small units along with sorting to get the final Sorted array.
 * 
 * @author Tejashri Naikar
 * 
 */

public class ExternalMergeSort {
	
	
	/**
	 * This method calls itself recursively and divides array into smallest
	 * possible array.
	 * @param A = array containing numbers
	 * @param B = auxiliary array
	 * @param p = start of the array
	 * @param r = end of the array
	 * @return
	 * @throws Exception
	 */
	static int MergeSort(int[] A, int[] B, int p, int r) throws Exception {
		int h2, h3;
		if (p < r) {
			int q = (p + r) / 2;
			h2 = MergeSort(A, B, p, q);
			h3 = MergeSort(A, B, q + 1, r);
			if (h2 != h3) {
				throw new Exception("not power of 2");
			} else {
				if (h2 % 2 == 0) {
					Merge(A, B, p, q, r);
				} else {
					Merge(B, A, p, q, r);
				}
			}
			return h2 + 1;
		} else {
			return 0;
		}

	}

	/**
	 * This method compares the values of A, and the elements under comparison are 
	 * sorted and put into the destination array.   
	 * @param A = array containing numbers
	 * @param B = auxiliary array
	 * @param p = start of the array
	 * @param q = mid point of the array
	 * @param r = end of the array
	 */
	static void Merge(int[] A, int[] B, int p, int q, int r) {

		int i = p;
		int j = q + 1;
		for (int k = p; k <= r; k++) {
			if ((j > r) || ((i <= q) && (A[i] <= A[j])))
				B[k] = A[i++];
			else
				B[k] = A[j++];
		}
		return;
	}
	
	/**
	 * This is the main method which is the start point. This calls the MergerSort method.
	 * @param args = in line argument 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int h1 = 0;
		int n = Integer.parseInt(args[0]);
		int[] A = new int[n];
		int[] B = new int[n];
		for (int i = 0; i < n; i++) {
			A[i] = n - i;
		}
		long beg = System.currentTimeMillis();
		h1 = MergeSort(A, B, 0, n - 1);
		long end = System.currentTimeMillis();

		System.out.println(end - beg);
		if (h1 % 2 == 1) {
			A = B;
		}
		for (int j = 0; j < A.length - 1; j++) {
			if (A[j] > A[j + 1]) {
				System.out.println("Sorting failed :-(");
				return;
			}
		}
		System.out.println("Success!");
	}

}
