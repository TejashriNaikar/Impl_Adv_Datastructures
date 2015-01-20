import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Develop a search engine using appropriate data structures to make the search efficient. The search engine should be able to search for items based on the id as well as name, given the name consists of one or more long ints.
 * @author txn141130
 *
 */
public class txn141130_P6 {
	
	//RedBlack tree, using this to save the items
	static Map<Long, txn141130_P6_Product> items = new TreeMap<Long, txn141130_P6_Product>();
	//Hashing used to save the name parts n its corresponding matching item
	static Map<Long, TreeMap<Long, txn141130_P6_Product>> namePartsMap = new HashMap<Long, TreeMap<Long, txn141130_P6_Product>>();
	
	
	/**
	 * This method inserts the items into two maps. One is the tree map and the other hash map. 
	 * Given an id of an item, if it already exists then the name and price of the object are 
	 * updated and return 0. If not then a fresh insertion is made in the tree map. 
	 *  The name of the item is split into different long ints and for each long int an 
	 *  entry in the hashmap is made and the item is inserted in the map associated with this 
	 *  key. If the long int already exists then the map corresponding to this key is got and 
	 *  the item is added into it.If insertion of element is done on an existing id, the along with 
	 *  updating the price and name of the item in the treemap, the hashmap item entries containing
	 *   the previous name are removed. If the name does not exist, then only price is updated.
	 * @param inputs = array of String
	 * @param input = String
	 * @return
	 */
	public static int insert(String[] inputs, String input) {
		//one more String array used to ignore the empty strings 
		String insertInputs[] = new String[inputs.length];
		int j = 0;
		//from the array inputs all the valid strings will be copied into insertInputs array  
		for(int i=0; i<inputs.length; i++){
			if(!inputs[i].trim().equals("") && !inputs[i].trim().isEmpty()){
				insertInputs[j++] = inputs[i];
			}
		}
		
		//putting the inputs in the proper data format and into variables
		long id = Long.parseLong(insertInputs[1].trim());
		//convert the given price to pennies   
		String val = new BigDecimal(insertInputs[2].trim()).multiply(new BigDecimal(100)).toString();
		long price = Long.parseLong(val.substring(0,val.indexOf(".")));
		
		String name = null;
		
		//building the name for saving in the object
		if(insertInputs.length > 4){
			for(int i=3 ; i< j-1 ; i++){
				if(name==null){
					name = insertInputs[i];
				}else {
					name = name+" "+ insertInputs[i];
				}
				
			}
			name = name.trim();
		}
		
		//checking if the item with the given id already exists 
		if(items.containsKey(id)){			
			txn141130_P6_Product product = items.get(id);
			
			//checking if the name is provided or not 
			if(name != null && !name.isEmpty() && !name.equals(product.getName())){
				String[] nameParts = product.getName().split(" ");
				
				//removing the existing entries from the map, for the old name 
				for(int i=0 ; i< nameParts.length ; i++){
					if(namePartsMap.get(Long.parseLong(nameParts[i]))!=null){
						if(namePartsMap.get(Long.parseLong(nameParts[i])).size() == 1){
							namePartsMap.remove(Long.parseLong(nameParts[i]));
						}else{
							namePartsMap.get(Long.parseLong(nameParts[i])).remove(id);
						}
					}
					
				}
 				product.setName(name);
 				
 				//making fresh entries into the map for the new name
				for (int i = 3; i < j-1 ; i++) {
					long namePart = Long.parseLong(insertInputs[i]);
					//if the name part already exists, the ass the item in its map
					if (namePartsMap.containsKey(namePart)) {
						namePartsMap.get(namePart).put(id, product);
					} else {
						//if the name part does not exist, then create a new entry
						TreeMap<Long, txn141130_P6_Product> products = new TreeMap<Long, txn141130_P6_Product>();
						products.put(id, (product));
						namePartsMap.put(namePart, products);
					}
				}
			}			
			product.setPrice(price);
			items.put(id, product);
			
			return 0;
		}else{
			
			//making an entry into the tree map 
			txn141130_P6_Product product = new txn141130_P6_Product(id, price, name);
			items.put(id, product);
			
			//making entries into the hash map based on the name
			for(int i=3 ; i< j-1 ; i++){
				long namePart = Long.parseLong(insertInputs[i]);
				//if the name part already exists, the ass the item in its map
				if(namePartsMap.containsKey(namePart)){
					namePartsMap.get(namePart).put(id, product);
				}else {
					//if the name part does not exist, then create a new entry
					TreeMap<Long, txn141130_P6_Product> products = new TreeMap<Long, txn141130_P6_Product>();
					products.put(id, (product));
					namePartsMap.put(namePart, products);
				}
			}
			//convert to pennies
			return 1*100;
		}
		
	}
	
	/**
	 * This takes the string array as an input, extracts the id that has to be 
	 * searched for. From the treemap we check if the item with the given id exists, 
	 * if yes then the price corresponding to the item is returned, else zero is returned.
	 * @param inputs
	 * @return
	 */
	public static long find(String[] inputs){
		//one more String array used to ignore the empty strings 
		String findItemInputs[] = new String[inputs.length];
		int j = 0;
		//from the array inputs all the valid strings will be copied into finItemInputs array  
		for(int i=0; i<inputs.length; i++){
			if(!inputs[i].trim().equals("") && !inputs[i].trim().isEmpty()){
				findItemInputs[j++] = inputs[i];
			}
		}
		long id = Long.parseLong(findItemInputs[1].trim());
		//checking if the id already exists or not in the map
		if(items.containsKey(id)){
			//getting the item
			txn141130_P6_Product product = items.get(id);
			return product.getPrice();
		}
		return 0;
		
	}
	
	/**
	 * This takes the string array as an input, extracts the id that has to 
	 * be deleted. From the treemap we check if the item with the given id 
	 * exists, if yes then the we add all the long ints that are present in 
	 * the name and return it, else zero is returned.
	 * @param inputs = array of String 
	 * @return
	 */
	public static long deleteItem(String[] inputs) {
		//one more String array used to ignore the empty strings 
		String deleteItemInputs[] = new String[inputs.length];
		int j = 0;
		//from the array inputs all the valid strings will be copied into deleteItemInputs array  
		for(int i=0; i<inputs.length; i++){
			if(!inputs[i].trim().equals("") && !inputs[i].trim().isEmpty()){
				deleteItemInputs[j++] = inputs[i];
			}
		}
		long id = Long.parseLong(deleteItemInputs[1].trim());
		long sumOfName = 0;
		
		//checking if the item already exists or not in the map
		if (items.containsKey(id)) {
			txn141130_P6_Product product = items.get(id);
			items.remove(id);
			
			//removing the existing entries from the map, for the item deleted
			if (product.getName() != null || !product.getName().isEmpty()) {
				String[] nameParts = product.getName().split(" ");
				for (int i = 0; i < nameParts.length; i++) {
					long namePart = Long.parseLong(nameParts[i]);
					//adding the parts
					sumOfName += namePart;
					if (namePartsMap.get(namePart) != null) {
						if (namePartsMap.get(namePart).size() == 1) {
							namePartsMap.remove(namePart);
						} else {
							namePartsMap.get(namePart).remove(id);
						}
					}

				}
			}

		}
		//convert to pennies 
		return sumOfName*100;
	}
	
	
	/**
	 * This takes the string array as an input, extracts the lower bound, higher 
	 * bound and the percentage increase that needs to be done. Since we are using
	 * a tree map, the elements will be accessed in a sorted order of their id. Hence we 
	 * iterate through the treemap from first element till the element whos id is greater
	 *  than the higher bound and then break the iteration. Till this element all those items 
	 *  price who’s id fall between the range are incremented by the given percentage.
	 *  
	 * @param inputs = array of Strings
	 * @return
	 */
	public static long priceHike(String[] inputs){

		long sumOfNetIncrease = 0;
		//one more String array used to ignore the empty strings 
		long priceHikeInputs[] = new long[inputs.length-1];
		int j = 0;
		//from the array inputs all the valid strings will be copied into priceHikeInputs array  
		for(int i=1; i<inputs.length; i++){
			if(!inputs[i].trim().equals("") && !inputs[i].trim().isEmpty()){
				priceHikeInputs[j++] = Long.parseLong(inputs[i]);
			}
		}
		
		//iterate through the values from first 
		for(txn141130_P6_Product product : items.values()){
			
			//check if the upper bound has reached to break 
			if(product.getId() > priceHikeInputs[1]){
				break;
			}else if(product.getId() < priceHikeInputs[0]){
				//if still below lower bound continue
				continue;
			}else{
				// when the id between the upper and lower bound increase the price
				//to the give percentage
				long priceIncrease = product.getPrice()*priceHikeInputs[2]/100;
				sumOfNetIncrease += priceIncrease;
				product.setPrice(product.getPrice()+priceIncrease);
			}
		}
		return sumOfNetIncrease;
	}
	
	
	/**
	 * This takes the string array as an input, extracts the long int. From 
	 * the hashmap, check if we have an entry for this long int, if yes then 
	 * iterate through the map of items corresponding to this long int to find 
	 * the item with minimum price.
	 * 
	 * @param inputs = array of Strings
	 * @return
	 */
	public static long FindMinPrice(String[] inputs){
		//one more String array used to ignore the empty strings 
		String findMinPriceInputs[] = new String[inputs.length];
		int j = 0;
		//from the array inputs all the valid strings will be copied into findMinPriceInputs array  
		for(int i=0; i<inputs.length; i++){
			if(!inputs[i].trim().equals("") && !inputs[i].trim().isEmpty()){
				findMinPriceInputs[j++] = inputs[i];
			}
		}
		//extract the name part
		long namePart = Long.parseLong(findMinPriceInputs[1].trim());
		long minAmt = 0;
		
		//check if the hashmap contains the name part
		if(namePartsMap.containsKey(namePart)){
			
			//get all the objects associated with this name
			TreeMap<Long, txn141130_P6_Product> products = namePartsMap.get(namePart);
			
			//check for the minimum price among them
			for(txn141130_P6_Product product: products.values()){
				if(minAmt == 0){
					minAmt = product.getPrice();
				} else {
					if(product.getPrice() < minAmt){
						minAmt = product.getPrice();
					}
				}
			}
		}
		return minAmt;
	}
	
	/**
	 * This takes the string array as an input, extracts the long int. From the 
	 * hashmap, check if we have an entry for this long int, if yes then iterate through 
	 * the map of items corresponding to this long int to find the item with maximum price.
	 * 
	 * @param inputs = array of strings
	 * @return
	 */
	public static long FindMaxPrice(String[] inputs){
		//one more String array used to ignore the empty strings 
		String findMaxPriceInputs[] = new String[inputs.length];
		int j = 0;
		//from the array inputs all the valid strings will be copied into findMaxPriceInputs array  
		for(int i=0; i<inputs.length; i++){
			if(!inputs[i].trim().equals("") && !inputs[i].trim().isEmpty()){
				findMaxPriceInputs[j++] = inputs[i];
			}
		}
		
		//extract the name part
		long namePart = Long.parseLong(findMaxPriceInputs[1].trim());
		long maxAmt = 0;
		
		//check if the hashmap contains the name part
		if(namePartsMap.containsKey(namePart)){
			//get all the objects associated with this name
			TreeMap<Long, txn141130_P6_Product> products = namePartsMap.get(namePart);
			
			//check for the maximum price among them
			for(txn141130_P6_Product product: products.values()){
				if(maxAmt == 0){
					maxAmt = product.getPrice();
				} else {
					if(product.getPrice() > maxAmt){
						maxAmt = product.getPrice();
					}
				}
			}
		}
		return maxAmt;
	}
	
	/**
	 * this takes the string array as an input, extracts the long int and 
	 * the lower price bound and the higher price bound. From the hashmap, 
	 * check if we have an entry for this long int, if yes then iterate through the 
	 * map of items corresponding to this long int to find which all items have 
	 * their price within this range and return the count. 
	 * 
	 * @param inputs = array of Strings
	 * @return
	 */
	public static long FindPriceRange(String[] inputs){
		//one more String array used to ignore the empty strings 
		String findPriceRangeInputs[] = new String[inputs.length];
		int j = 0;
		//from the array inputs all the valid strings will be copied into findPriceRangeInputs array  
		for(int i=0; i<inputs.length; i++){
			if(!inputs[i].trim().equals("") && !inputs[i].trim().isEmpty()){
				findPriceRangeInputs[j++] = inputs[i];
			}
		}
		
		//extract the required inputs
		long namePart = Long.parseLong(findPriceRangeInputs[1].trim());
		float lowerPriceRange = Float.parseFloat(findPriceRangeInputs[2].trim())*100;
		float higherPriceRange = Float.parseFloat(findPriceRangeInputs[3].trim())*100;
		long count = 0;
		
		//check if the hashmap contains the name part
		if(namePartsMap.containsKey(namePart)){
			//get all the objects associated with this name
			TreeMap<Long, txn141130_P6_Product> products = namePartsMap.get(namePart);
			
			//iterate through the values
			for(txn141130_P6_Product product : products.values()){
				//check which all items price falls in the range
				if(product.getPrice() >= lowerPriceRange && product.getPrice() <= higherPriceRange){
					//for each price within the range, increment the price
					count ++;
				}
			}
		}
		//convert to pennies
		return count*100;
	}

	
	/**
	 * Main method of the project reads each line of input from stdin as a string and splits it based of empty space “ “.
	 * Based on the first word, the switch case decides what operation has to be performed.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Data");
		long operationOutput = 0;
		
		//Scan all the lines 
		while (sc.hasNext()) { 
			String input = sc.nextLine();
			//split the input based on " "
			String[] inputs = input.split(" ");
			//check if they are comments
			if (!inputs[0].equals("#")) {
				//use switch case to decide the operation
				switch (inputs[0]) {
				case "Insert":
					operationOutput +=  insert(inputs, input);
					break;
				case "PriceHike": 
					operationOutput += priceHike(inputs);
					break;
				case "FindMaxPrice":
					operationOutput +=  FindMaxPrice(inputs);
					break;
				case "FindMinPrice":
					operationOutput += FindMinPrice(inputs);
					break;
				case "FindPriceRange":
					operationOutput += FindPriceRange(inputs);
					break;
				case "Delete":
					operationOutput += deleteItem(inputs);
					break;
				case "Find":
					operationOutput += find(inputs);
					break;
				default:
					break;

				}
			}
		}
		
		//print the final output converting back to floating number
		System.out.println(new BigDecimal(operationOutput).divide(new BigDecimal(100)));
	}
}


/**
 * This is the class which will hold the information a item. For every item, an object
 * of this class is created.
 *   
 * @author txn141130
 *
 */
public class txn141130_P6_Product {

	long id;
	long price;
	String name;

	public txn141130_P6_Product(long id, long price, String name) {
		super();
		this.id = id;
		this.price = price;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "id :"+this.id+" name :"+this.name + " price :"+this.price;
	}

}
