import java.util.Scanner;
/**
 * This is a program that visits all permutations/combinations of n objects
   from a set of n distinct objects, numbered 1..n.
 * @author txn141130 (Tejashri Naikar)
 *
 */

public class PermutationCombination {
	static int count;
	static int choice;
	
	
	
	/**
	 * This method depending on the verbose option either prints all the permutations 
	 * or just counts the permutation
	 * @param A = Array of elements
	 * @param choice = user option
	 */
	public static void visit(int[] A, int k) {
		if (choice > 1) {
			for (int i = 0; i < k; i++) {
				if (A[i] != 0) {
					System.out.print(A[i] + " ");
				}
			}
			System.out.println("");
		}
		count++;
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
	 * This method generates all the permutation of k elements out of n elements.  
	 * This method takes as an input an array of sorted elements and uses the lexicographic
	 * strategy to generate the permutations.
	 * @param A = Array elements
	 * @param A 
	 * @param k
	 */
	public static void permute(int[] A, int k) {
		int m = k - 1;
		int j = k;
		int n = A.length;
		while (true) {
			visit(A, k);
			 j = k;
			while (j < n && A[m] >= A[j]) {
				++j;
			}
			if (j < n) {
				swap(A, m, j);
			} else {
				int p = k;
				int q = n - 1;
				while (p < q) {
					swap(A, p, q);
					p = p + 1;
					q = q - 1;
				}

				int i = m -1;

				while (i >= 0 && A[i] >= A[i + 1]) {
					--i;
				}
				if (i < 0) {
					return;
				}

				j = n - 1;
				while (j > i && A[i] >= A[j]) {
					--j;
				}
				swap(A, i, j);
				p = i + 1;
				q = n - 1;
				while (p < q) {
					swap(A, p, q);
					p = p + 1;
					q = q - 1;
				}
			}
			
		}
	}
	
	
	/**
	 * This method provides all the combinations of k elements 
	 * out of n elements. 
	 * @param A = Array elements
	 * @param i = length of array
	 * @param k = number of elements
	 */
	public static void combination(int[] A, int i, int k) {
		if (k == 0) {
			visit(A, A.length);
		} else if (i < k) {
			return;
		} else {
			A[i - 1] = i;
			combination(A, i - 1, k - 1);
			A[i - 1] = 0;
			combination(A, i - 1, k);
		}
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
	 * This is the main method which initiates the call to permutation or combination based on the
	 * inline arguments provided 
	 * @param args = in line arguments
	 */	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out
				.println("Enter the number of elements n, value for k and the verbose v followed by single space\n Enter the value for v as 4 if the array elements are not distict");
		String[] inputs = sc.nextLine().split(" ");
		int n = Integer.parseInt(inputs[0]);
		int k = Integer.parseInt(inputs[1]);
		choice = Integer.parseInt(inputs[2]);
		//this generates the permutations for non distinct elements. 
		if(choice == 4){
			System.out.println("Enter the " + n
					+ " numbers with only single space after each element ");
			String secondInput = sc.nextLine();
			String[] numbers = secondInput.split(" ");
			int[] A = new int[numbers.length];
			for (int i = 0; i < numbers.length; i++) {
				A[i] = Integer.parseInt(numbers[i]);
			}
			MergeSort(A, 0, A.length - 1);
			permute(A, k);	
		}else{
			//call for generating permutations
			if(choice ==0 || choice == 2){
				int A[] = new int[n];
				for(int i =0; i< n ; i++){
					A[i] = i+1;
				}
				permute(A, k);
			}
			else{
				//call for generating combinations
				int A[] = new int[n];
				combination(A, n, k);
			}
		}		
		
		System.out.println(count);
	}

}
