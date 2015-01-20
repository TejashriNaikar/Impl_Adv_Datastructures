import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * Develop a program that implements arithmetic with
 * large integers, of arbitrary size.
 * @author txn141130
 *
 */
public class LinkedList {

	static int base = 10;
	/**
	 * This program takes a string and converts it into a linked list. 
	 * @param number = String input
	 * @return
	 */
	public static LinkedList<Integer> strToNum(String number) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		//iterate through all the characters
		for(int i=0; i<number.length(); i++){
			//add the each element at the beginning, so that the first 
			// number is the last element in the list
			list.addFirst(Character.getNumericValue(number.charAt(i)));
		}
		return list;
	}
	
	/**
	 * Convert a linked list to String
	 * @param nums
	 * @return
	 */
	public static String numToStr(LinkedList<Integer> nums) {
		String number = "";
		Iterator<Integer> iterator = nums.listIterator();
		int i;
		while (iterator.hasNext()) {
			//append each element of the list to the string
			number += iterator.next() + "";
		}
		//reverse the string
		number = new StringBuffer(number).reverse().toString();
		//handle any number that have zeros at the beginning. so that they will not cause any problem
		for(i=0; i<number.length(); i++){
			if(number.charAt(i)!='0'){
				break;
			}
		}
		if(i!=0){
			number=number.substring(i);
		}
		return number;
	}
	
	/**
	 * This method performs addition on two lists. Each node in both the lists are iterated and added. 
	 * if the number is bigger than the base then carry is taken to add during next iteration.
	 * @param list1 = linked lists
	 * @param list2 = linked lists
	 * @return
	 */
	public static LinkedList<Integer> addLinkLists(LinkedList<Integer> list1,
			LinkedList<Integer> list2) {
		int carry = 0;
		int add = 0;
		int sum = 0;
		int i = 0;
		LinkedList<Integer> finalList = new LinkedList<Integer>();
		Iterator<Integer> iterator1 = list1.iterator();
		Iterator<Integer> iterator2 = list2.iterator();
		//iterate through each of the two lists
		while (iterator1.hasNext() && iterator2.hasNext()) {
			//add them
			add = iterator1.next() + iterator2.next() + carry;
			finalList.add(add % base);
			//calculate the carry
			carry = add / base;
		}
		System.out.println(sum);
		
		//handle the remaining unvisited nodes
		while (iterator1.hasNext()) {
			add = iterator1.next() + carry;
			finalList.add(add % base);
			carry = add / base;
		}
		
		//handle the remaining unvisited nodes
		while (iterator2.hasNext()) {
			add = iterator2.next() + carry;
			finalList.add(add % base);
			carry = add / base;
		}
		
		//handle if carry is der
		if (carry != 0) {
			finalList.add(carry % base);
		}
		numToStr(finalList);
		return finalList;
	}
	
	/**
	 * this takes as an input two linked lists and checks if the first number is greater than the second number. 
	 * If not then it returns a zero. If the condition is true the we iterate through both the lists nodes and 
	 * subtract them, if the first node is less than the second one the we add base to the first node 
	 * and take a borrow for the next iteration. If any nodes are not visited then that list alone will be 
	 * iterated separately and  subtraction is carried on that.
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static LinkedList<Integer> subtractLinkLists(LinkedList<Integer> list1, LinkedList<Integer> list2) {
		int borrow = 0;
		int difference = 0;
		String num3 = numToStr(list1);
		String num4 = numToStr(list2);
		//check if list1 is greater than list2
		if(list1.size() < list2.size()){
			//if yes return zero
			return strToNum("0");
		} else if(num4.compareTo(num3) > 0){		
			//if yes return zero
			return strToNum("0");
		}else {
		
		LinkedList<Integer> finalList = new LinkedList<Integer>();
		Iterator<Integer> iterator1 = list1.iterator();
		Iterator<Integer> iterator2 = list2.iterator();
		int num1, num2;
		//iterate through the lists and subtract each element
		while (iterator1.hasNext() && iterator2.hasNext()) {
			num1 = iterator1.next();
			num2 = iterator2.next();
			//check if base has to be added to first element
			if (num1 < num2 || num1 < (num2 + borrow)) {
				difference = (num1 + base) - num2 - borrow;
			} else {
				difference = num1 - num2 - borrow;
			}
			//add the difference to the list
			finalList.add(difference % base);
			//calculate i borrow is required or not
			if (num1 < num2 || num1 < (num2 - borrow)) {
				borrow = 1;
			} else {
				borrow = 0;
			}

		}
		
		//handle the remaining unvisited nodes
		while (iterator1.hasNext()) {
			num1 = iterator1.next();
			if (num1 < borrow){
				difference = (num1 + base) - borrow;
			} else {
				difference = num1 - borrow;
			}
			
			finalList.add(difference % base);
			if ( num1 < borrow) {
				borrow = 1;
			} else {
				borrow = 0;
			}
		
		}

		numToStr(finalList);

		return finalList;
		}
	}
	
	/**
	 * This takes as an input two linked list. It takes one node at a time 
	 * from second list and multiples it with each number in the second list an kept 
	 * in one list. When the second number will be multiplied, this will be added to the previous one.
	 * @param list1
	 * @param list2
	 * @return
	 */

	public static LinkedList<Integer> multiple(LinkedList<Integer> list1,
			LinkedList<Integer> list2) {
		int product = 0;
		int add = 0;
		int count = 0;
		int carry = 0;
		int i=0;
		int sum = 0;
		int multiplier = 0;
		LinkedList<Integer> productList = new LinkedList<Integer>();
		LinkedList<Integer> finalProductList = null;
		LinkedList<Integer> addList = new LinkedList<Integer>();
		Iterator<Integer> iterator1 = list1.iterator();
		Iterator<Integer> iterator2 = list2.iterator();
		
		//Iterate through second list
		while (iterator2.hasNext()) {
			multiplier = iterator2.next();
			iterator1 = list1.iterator();
			productList = new LinkedList<Integer>();
			//iterate through first list
			while (iterator1.hasNext()) {
				//calculate the product
				product = (iterator1.next() * multiplier) + carry;
				productList.add(product % base);
				//calculate the carry
				carry = product / base;
			}
			//if carry exixts the add it to the end
			if(carry!=0){
				productList.add(carry);
				carry=0;
			}
			if (finalProductList != null) {
				addList = finalProductList;
				finalProductList = new LinkedList<Integer>();
				Iterator<Integer> iterator3 = addList.iterator();
				Iterator<Integer> iterator4 = productList.iterator();
				
				//Move the pointer of finalproduct list those many elements as that haen been 
				//multiplied till now
				for ( i = 0; i < count; i++) {
					if (iterator3.hasNext()) {
						finalProductList.add(iterator3.next());
					}
				}
				
				//add the two lists so as to produce the final product
				while (iterator3.hasNext() && iterator4.hasNext()) {
					add = iterator3.next() + iterator4.next() + carry;
					finalProductList.add(add % base);
					carry = add / base;
				}
				
				//handle the remaining unvisited nodes
				while (iterator3.hasNext()) {
					add = iterator3.next() + carry;
					finalProductList.add(add % base);
					carry = add / base;
				}
				
				//handle the remaining unvisited nodes
				while (iterator4.hasNext()) {
					add = iterator4.next() + carry;
					finalProductList.add(add % base);
					carry = add / base;
				}
				
				//handle the carry
				if (carry != 0) {
					finalProductList.add(carry);
					carry=0;
				}
								

			} else {
				finalProductList = productList;
			}
			count++;
		}
		
		return finalProductList;
	}
	
	/**
	 * This takes as an input two linked list. keeps decreasing the list2 by 1 till it reaches zero. 
	 * and multiplies list1 with itself.
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static LinkedList<Integer> power(LinkedList<Integer> list1, LinkedList<Integer> list2){
		int sum = 0;
		LinkedList<Integer> powerList = new LinkedList<Integer>();
		powerList = list1;
		boolean multiply = false;
		LinkedList<Integer> one = strToNum("1");
		//subtract by one, so that we decrement for each iteration
		list2 = subtractLinkLists(list2, one);
		while(true){
			Iterator<Integer> iterator = list2.iterator();
			multiply = false;
			//Check list2, if it has all the elements as zero, it means we
			//hae done all the multiplication and got the answer
			while (iterator.hasNext()){
				if(iterator.next() > 0){
					multiply = true;
					break;
				}
			}
			//if list2 is not zero
			if(multiply){
				powerList = multiple(powerList, list1);
				list2 = subtractLinkLists(list2, one);
			}else{
				break;
			}			
		}
		
		return powerList;
	}
	
	/**
	 * This is the main method and the driver program that takes care of the inputs.
	 * Then calls the methods on depending on the provided inputs. And this is quite complex
	 * @param args
	 */

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a number");
		int linenum = 0;
		String cmd = null;
		//HashMaps to take care of the inputs
		//this will have all the inputs initially, which will be separated out into other maps
		Map<Integer, String> input = new HashMap<Integer, String>();
		//This will contain the linked list pertaining to variables
		Map<Character, LinkedList<Integer>> lists = new HashMap<Character, LinkedList<Integer>>();
		//this will contain all the commands to be executed
		Map<Integer, String[]> commands = new HashMap<Integer, String[]>();
		//Collect all the inputs
		 while(in.hasNextLine()) {
			 try{
				 linenum = in.nextInt();
				 cmd = in.next();
				 input.put(linenum, cmd);
			 }catch(Exception e){
				 System.out.println("End of inputs");
			 }
			
		 }
		
		 //process them
		for(Integer i : input.keySet()){
			//check if these are values
			if(input.get(i).matches("(\\w)\\=(\\d+)")){
				String[] inputs = input.get(i).split("=");
				//if yes the add them
				lists.put(inputs[0].charAt(0),strToNum(inputs[1]));
			} else{
				//else they will be commands
				String[] inputs = input.get(i).split("=");
				commands.put(i, inputs);
			}
		}
		Integer x = 1002, y=0;
//		for(Integer i: commands.keySet()){
//			if(i<x){
//				x=i;
//			}
//		}
		x=0;
		List cmss = new ArrayList(commands.keySet());
		Collections.sort(cmss);
		//This perform all the operations, till there are no one left
		while(true){
			if(y < cmss.size()){
				x=(Integer) cmss.get(y);
				if(commands.containsKey(x)){
					
					//extract the command
					String[] storedCommand = commands.get(x);
					//in the following condition we check the command string contains
					//which operation. Depending on that we perorm the operation
					//check for the addition
					if(storedCommand.length > 1 && storedCommand[1].contains("+")){
						LinkedList<Integer> newList;
						//here we get the linked lists on that particular variable and perform the operation
						newList = addLinkLists(lists.get(storedCommand[1].charAt(0)), lists.get(storedCommand[1].charAt(2)));
						//save the linked list against the variable
						lists.put(storedCommand[0].charAt(0), newList);
					}
					
					//check or subtraction
					else if(storedCommand.length > 1 && storedCommand[1].contains("-")){
						LinkedList<Integer> newList;
						//here we get the linked lists on that particular variable and perform the operation
						newList = subtractLinkLists(lists.get(storedCommand[1].charAt(0)), lists.get(storedCommand[1].charAt(2)));	
						//save the linked list against the variable
						lists.put(storedCommand[0].charAt(0), newList);
					}
					
					//check for multiplication
					else if(storedCommand.length > 1 && storedCommand[1].contains("*")){
						LinkedList<Integer> newList;
						//here we get the linked lists on that particular variable and perform the operation
						newList = multiple(lists.get(storedCommand[1].charAt(0)), lists.get(storedCommand[1].charAt(2)));	
						//save the linked list against the variable
						lists.put(storedCommand[0].charAt(0), newList);
					}
					
					//check for power
					else if(storedCommand.length > 1 && storedCommand[1].contains("^")){
						LinkedList<Integer> newList;
						//here we get the linked lists on that particular variable and perform the operation
						newList = power(lists.get(storedCommand[1].charAt(0)), lists.get(storedCommand[1].charAt(2)));		
						//save the linked list against the variable
						lists.put(storedCommand[0].charAt(0), newList);
					}
					
					//for this symbol we  check if the left had variable has reached zero or not, if not then we 
					//make it execute the commands again, till it becomes zero
					else if(storedCommand[0].contains("?")){
						LinkedList<Integer> newList = lists.get(storedCommand[0].charAt(0));
						Iterator<Integer> iterator = newList.iterator();
						while (iterator.hasNext()){
							if(iterator.next() > 0){
								x = Character.getNumericValue(storedCommand[0].charAt(2));
								y = cmss.indexOf(x) - 1;
								break;
							}
						}
					}else {
						//here we print the variable
						if(storedCommand[0].length() == 1){
							System.out.println(numToStr(lists.get(storedCommand[0].charAt(0)))+"");
						}
					}
					y++;
				} else{
					break;
				}
			}
			else{
				break;
			}
			
		}
		
	}

}
