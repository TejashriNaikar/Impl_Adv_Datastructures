package com.utd.ds.algo;

import java.io.IOException;

/**
 * Implementation of Better Merge Sort using Divide and Conquer and
 * recursion method. 
 * This has a better run time compared to normal Merge Sort of
 * Merge Sort. 
 * This implementation divides the elements into parts in recursive
 * manner till it splits the numbers to single terms. Then start merging these
 * small units along with sorting to get the final Sorted array.
 * 
 * @author Tejashri Naikar
 * 
 */

public class BetterMergeSort {
	
	/**
	 * This method calls itself recursively and divides array into smallest
	 * possible array.
	 * @param A = array containing numbers
	 * @param p = start of the array
	 * @param r = end of the array
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
	 * This method compares the values of A, and the elements under comparison are 
	 * sorted and put into the destination array. This uses an auxiliary array B.
	 * @param A = array containing numbers
	 * @param p = start of the array
	 * @param q = mid point of the array
	 * @param r = end of the array
	 */
	static void Merge(int[] A, int p, int q, int r) {
		int ls = q - p + 1;
		int rs = r - q;
		int[] B = new int[ls+rs];
		for (int i = p; i <= r; i++){
			B[i - p] = A[i];
		}
		int i = 0;
		int j = ls;
		for (int k = p; k <= r; k++) {
			if ((j >=(ls+rs)) || ((i < ls) && (B[i] <= B[j])))
				A[k] = B[i++];
			else
				A[k] = B[j++];
		}
		return;
	}
	
	public static void main(String[] args) throws IOException {
		int n = Integer.parseInt(args[0]);
		int[] A = new int[n];
		for (int i = 0; i < n; i++) {
			A[i] = n - i;
		}
		long beg = System.currentTimeMillis();
		MergeSort(A, 0, n - 1);
		long end = System.currentTimeMillis();
		
		System.out.println(end-beg);
		for (int j = 0; j < A.length - 1; j++) {
			if (A[j] > A[j + 1]) {
				System.out.println("Sorting failed :-(");
				return;
			}
		}
		System.out.println("Success!");
	}

}
