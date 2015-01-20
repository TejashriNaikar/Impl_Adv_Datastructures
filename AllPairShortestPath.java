import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;


/**
 * This program calculates the number the shortest paths from source to
 * each vertex present in the graph
 * 
 * @author txn141130
 *
 */
public class AllPairShortestPath {
	
	//this map holds all the nodes
	static Map<Integer, Txn141130_P8_PartA_Node> nodes = new HashMap<Integer, Txn141130_P8_PartA_Node>();

	//this is the queue used to help in bellman ford algorithm
	static Queue<Txn141130_P8_PartA_Node> queue = new LinkedList<Txn141130_P8_PartA_Node>();
	
	
	/**
	 * This method takes as an input the parent node, child node and the edge connecting them. 
	 * This is modified from what we have for the actual Bellman Ford. It has two parts. If the 
	 * shortest path encountered is equal to the existing shortest path. This needs to be added 
	 * to the paths. Hence the parent is added to the list of predecessors and the child is added 
	 * to the child list of the parent.The count of the number of shortest paths currently present 
	 * is increased with the number of path present in the parents path. As this node is reachable 
	 * from its parent from those many paths as that of its parent. The addition of this new path 
	 * needs to be propagated to all the child nodes. As their paths will also increase. Hence we 
	 * pick all the children of this node and their children as well and increment the paths. If a 
	 * new shortest path is encountered, it will mean that the existing once is not the correct 
	 * shortest path. Hence we flush out all the data stored before and also remove this child 
	 * information from all the predecessors list. Then store the new shortest path, the predecessor 
	 * and the no of ways in which the child is reachable from this parent. And also return true to the 
	 * calling method, so that these changes can be made to the children nodes. 
	 * 
	 * @param node1 : parent node
	 * @param node2 : child node
	 * @param edge : connecting edge
	 * @return
	 * @throws Exception : if negative cycle found
	 */
	public static boolean relax(Txn141130_P8_PartA_Node node1,
			Txn141130_P8_PartA_Node node2, Txn141130_P8_Edge edge) throws Exception {
		
		//return this for this method
		boolean changed = false;
		Queue<Txn141130_P8_PartA_Node> childQueue = new LinkedList<Txn141130_P8_PartA_Node>();
		int count =0;
		
		//another shortest path is found. Hence make the changes to the paths, predecessors list
		//child list and also increment the the number of path through which this node is reachable
		if (node2.getDistance() == node1.getDistance() + edge.getWeight()) {
			node2.getPredecessor().add(node1);
			node1.getChildren().add(node2);
			node2.getNoPathsFromParent().add(node1.getNoPathsFromS());
			node2.setNoPathsFromS(node2.getNoPathsFromS()+node1.getNoPathsFromS());
			
			childQueue.add(node2);
			
			//propagate the new paths information the child nodes 
			while(!childQueue.isEmpty()){
				count++;
				if(count >= nodes.size()){
					throw new Exception("Non positive cycle exists. DAC not applicable");
				}
				Txn141130_P8_PartA_Node node = childQueue.poll();
				for(Txn141130_P8_PartA_Node n : node.getChildren()){
					n.setNoPathsFromS(n.getNoPathsFromS()+1);
					childQueue.add(n);
				}
			}
			
		}
		
		//a new shortest path is found.
		if (node2.getDistance() > node1.getDistance() + edge.getWeight()) {
			
			//flush out the child information from the old predecessors list
			//as it is no more a child to them
			for(Txn141130_P8_PartA_Node n : node2.getPredecessor()){
				n.getChildren().remove(node2);
			}
			
			//make the new entries according to the new parent
			node2.getNoPathsFromParent().clear();
			node2.setDistance(node1.getDistance()+edge.getWeight());
			node2.getNoPathsFromParent().add(node1.getNoPathsFromS());
			node2.setNoPathsFromS(node1.getNoPathsFromS());
			node2.getPredecessor().add(node1);
			node1.getChildren().add(node2);
			
			//since the shortest path has changed set changed to true 
			changed = true;
		}
		return changed;
	}
	
	
	/**
	 * This algorithm helps to find the shortest path from the source to all the 
	 * vertices present in the graph. This same approach is used to find the number of 
	 * shortest paths present from source to the destination vertex. This takes as an 
	 * input the start vertex, puts this node into the queue. From this node we go through 
	 * the adjacency list and call relax operation on each of the adjacent nodes. If relax 
	 * method returns true, it will mean that the shortest path for the node has changed and 
	 * this must be propagated to all its adjacent nodes. Hence we put this in the queue and 
	 * repeat the steps. This is continued till the queue becomes empty. i.e. till we have found 
	 * all the shortest paths. While doing this if a particular node is visited more than the 
	 * number of vertices present, then it will mean there exists a non positive cycle in the 
	 * graph and then throw an exception DAC is not applicable.
	 * 
	 * @param startertex : source vertex
	 * @throws Exception : if negative cycle found
	 */
	public static void bellmanFord(int startertex) throws Exception {
		
		//add the start vertex to the queue
		queue.add(nodes.get(startertex));
		
		//start calculating the shortest paths, till the queue becomes empty
		while (!queue.isEmpty()) {
			Txn141130_P8_PartA_Node node = queue.poll();
			
			//keep the count about how many times the node has been modified
			int count = node.getCount();
			node.setCount(++count);
			
			//if it exceeds the size of the number of nodes present, it means
			// negative cycle exists, hence throw exception
			if (node.getCount() > nodes.size()) {
				throw new Exception("Non Positive Cycle in the graph. DAC is not applicable");
			} else {
				//pick each node from the adjacency list
				for (Txn141130_P8_Edge edge : node.getAdjList()) {
					Txn141130_P8_PartA_Node node2 = nodes.get(edge.getChild());
					boolean changed = false;
					try{
						//call relax to find the shortest path
						changed = relax(node, node2, edge);
					}catch(Exception e){
						throw new Exception(e);
					}
					//if shortest path found 
					if (changed) {
						if (!queue.contains(node2)) {
							//add to queue
							queue.add(node2);
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * This method reads the inputs from stdin and then initializes all the nodes and 
	 * also create the adjacency list for each node. This method then call the bellmanFord() 
	 * method to find the shortest paths from source to all the vertices. Then prints the output.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the data");
		int k = 0;

		int vertices = in.nextInt();
		int edges = in.nextInt();
		int startertex = in.nextInt();
		int finalVertex = in.nextInt();
		Long beg = System.currentTimeMillis();
		
		//Initialize the node along with its adjacency list
		while (k < edges) {
			if (in.hasNext()) {
				int input1 = in.nextInt();
				int input2 = in.nextInt();
				int input3 = in.nextInt();

				Txn141130_P8_Edge edge = new Txn141130_P8_Edge(input1, input2,
						input3, 0);
				if (!nodes.containsKey(input1)) {
					Txn141130_P8_PartA_Node node = new Txn141130_P8_PartA_Node(input1);
					node.getAdjList().add(edge);
					nodes.put(input1, node);
				} else {
					nodes.get(input1).getAdjList().add(edge);
				}
				if (!nodes.containsKey(input2)) {
					Txn141130_P8_PartA_Node node = new Txn141130_P8_PartA_Node(input2);
					nodes.put(input2, node);
				}
				k++;
			}
		}
		
		//initialize the source vertex
		nodes.get(startertex).setDistance(0);
		nodes.get(startertex).setNoPathsFromS(1);
		try {
			//call bellman ford method
			bellmanFord(startertex);
		} catch (Exception e) {
			
			//catch the exception, print the message and exit the program
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		//calculate the run time of the program
		Long end = System.currentTimeMillis();
		Txn141130_P8_PartA_Node node = nodes.get(finalVertex);
		
		//print the output
		System.out.println(node.getName() +" "+node.getNoPathsFromS() + " "+ (end-beg));
		
		//print this only if the node count is less than 100
		if(nodes.size() <= 100){
			for(Txn141130_P8_PartA_Node n : nodes.values()){
				if(n.getName() == startertex){
					System.out.println(n.getName() + " 0 - " +n.getNoPathsFromS());
				} else if(n.getPredecessor().size() == 0){
					System.out.println(n.getName()+" INF - 0");
				} else {
					System.out.println(n.getName() +" "+n.getDistance() +" "+n.getPredecessor().get(0).getName()+" "+n.getNoPathsFromS());
				}
			}
		}

	}

}


import java.util.ArrayList;
import java.util.List;

public class Txn141130_P8_PartA_Node {

	private int name;
	private int distance;
	private List<Txn141130_P8_PartA_Node> predecessor;
	private List<Txn141130_P8_Edge> adjList;
	private int count = 0;
	private int pathCount;
	private List<Long> noPathsFromParent;
	private long noPathsFromS;
	private List<Txn141130_P8_PartA_Node> children;

	public Txn141130_P8_PartA_Node(int name) {
		this.name = name;
		distance = 99999999;
		predecessor = new ArrayList<Txn141130_P8_PartA_Node>();
		adjList = new ArrayList<Txn141130_P8_Edge>();
		children = new ArrayList<Txn141130_P8_PartA_Node>();
		count = 0;
		noPathsFromParent = new ArrayList<Long>();
		noPathsFromS = 0;
		pathCount = 0;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public List<Txn141130_P8_PartA_Node> getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(List<Txn141130_P8_PartA_Node> predecessor) {
		this.predecessor = predecessor;
	}

	public List<Txn141130_P8_Edge> getAdjList() {
		return adjList;
	}

	public void setAdjList(List<Txn141130_P8_Edge> adjList) {
		this.adjList = adjList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPathCount() {
		return pathCount;
	}

	public void setPathCount(int pathCount) {
		this.pathCount = pathCount;
	}

	public List<Long> getNoPathsFromParent() {
		return noPathsFromParent;
	}

	public void setNoPathsFromParent(List<Long> noPathsFromParent) {
		this.noPathsFromParent = noPathsFromParent;
	}

	public long getNoPathsFromS() {
		return noPathsFromS;
	}

	public void setNoPathsFromS(long noPathsFromS) {
		this.noPathsFromS = noPathsFromS;
	}

	public List<Txn141130_P8_PartA_Node> getChildren() {
		return children;
	}

	public void setChildren(List<Txn141130_P8_PartA_Node> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + pathCount + "," + name + "," + predecessor + ")";
	}

}


/**
 * Edge class containing parent, child, weight and modified fields
 * 
 * @author txn141130
 *
 */
public class Txn141130_P8_Edge implements Comparable<Txn141130_P8_Edge> {
	private int parent;
	private int child;
	private int weight;
	private int modifiedWeight ;
	
	public Txn141130_P8_Edge(int parent, int child, int weight, int modifiedWeight) {
		super();
		this.parent = parent;
		this.child = child;
		this.weight = weight;
		this.modifiedWeight = modifiedWeight;
		
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getModifiedWeight() {
		return modifiedWeight;
	}

	public void setModifiedWeight(int modifiedWeight) {
		this.modifiedWeight = modifiedWeight;
	}
	
	public int otherEnd(int u){
		if( u == child){
			return parent;
		} else {
			return child;
		}
	}

	@Override
	public int compareTo(Txn141130_P8_Edge o) {
		return this.weight - o.getWeight();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "( "+parent+" , "+child+" , "+weight+" ,"+ modifiedWeight +" )";
	}
	

}
