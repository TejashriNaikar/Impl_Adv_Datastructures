import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//Kruskal's algorithm for finding minimum spanning tree in undirected graph
class Node {
	// Class for node
	private int u;
	private int parent;
	private int ranking;

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getU() {
		return u;
	}

	public void setU(int u) {
		this.u = u;
	}

}

class Edge implements Comparable<Edge> {

	private int u;
	private int v;
	private int weight;

	public Edge(int u, int v, int weight) {
		super();
		this.u = u;
		this.v = v;
		this.weight = weight;
	}

	public int getU() {
		return u;
	}

	public void setU(int u) {
		this.u = u;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge arg0) {
		if (this.weight < arg0.getWeight()) {
			return -1;
		} else if (this.weight > arg0.getWeight()) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "( " + this.u + " , " + this.v + " )";
	}

}

/**
 * @author Tejashri Naikar
 *
 */
public class Kruskals {

	static Map<Integer, Node> nodes = new HashMap<Integer, Node>(); // Map that stores nodes with key as node name and value as node

	static List<Edge> edges = new ArrayList<Edge>();
	// List to store edges

	static List<Edge> finalEdges = new ArrayList<Edge>();
	// list that stores the final edges to be printed
	static Comparator<Edge> weightComparator = new Comparator<Edge>() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Edge o1, Edge o2) {
			if (o1.getWeight() < o2.getWeight()) {
				return -1;
			} else if (o1.getWeight() > o2.getWeight()) {
				return 1;
			} else {
				return 0;
			}
		}

	};

	/**
	 * @param u  -  id of the node
	 * creates a node with the given id u and add it to the map
	 */
	public static void makeSet(int u) {
		Node node = new Node();
		node.setParent(u);
		node.setRanking(0);
		node.setU(u);
		nodes.put(u, node);
	}

	/**
	 * method finds and returns the parent node of the given node
	 * @param node - node whose parent needs to be found
	 * @return the parent of the current node
	 */
	public static int find(Node node) {
		if (node.getU() != node.getParent()) {
			node.setParent(find(nodes.get(node.getParent())));
		}
		return node.getParent();

	}

	/**
	 * the methods unions two nodes if the edges between them does not form a cycle
	 * @param u - input node u
	 * @param v - input node v
	 */
	public static void union(Node u, Node v) {
		if (u.getRanking() > v.getRanking()) {
			v.setParent(u.getU());
		} else if (u.getParent() < v.getParent()) {
			u.setParent(v.getU());
		} else {
			v.setParent(u.getU());
			int rank = u.getRanking();
			u.setRanking(rank++);
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int path = 0;
		// System.out.println("Enter the data");
		int vertex = in.nextInt();
		int edges1 = in.nextInt();
		// String input = in.nextLine();
		// String[] stdInput = input.split(" ");
		Integer[] inputs = new Integer[3];
		int j = 0, k = 0;
		/*
		 * for (int i = 0; i < stdInput.length; i++) { if
		 * (!stdInput[i].trim().isEmpty() && !stdInput[i].equals("") && j < 3) {
		 * inputs[j++] = Integer.parseInt(stdInput[i]); } }
		 */
		// int noOfEdges = inputs[1];
		long startTime = System.currentTimeMillis();
		int noOfEdges = edges1;
		while (k < noOfEdges) {
			if (in.hasNext()) {
				// input = in.nextLine();
				// stdInput = input.split(" ");
				j = 0;
				// for (int i = 0; i < stdInput.length; i++) {
				// if (!stdInput[i].trim().isEmpty()
				// && !stdInput[i].equals("") && j < 3) {
				// inputs[j++] = Integer.parseInt(stdInput[i]);
				// }
				// }
				for (int i = 0; i < 3; i++) {
					inputs[i] = in.nextInt();
				}
				if (!nodes.containsKey(inputs[0])) {
					makeSet(inputs[0]);
				}
				if (!nodes.containsKey(inputs[1])) {
					makeSet(inputs[1]);
				}
				Edge edge = new Edge(inputs[0], inputs[1], inputs[2]);
				edges.add(edge);
				k++;
			}
		}
		Collections.sort(edges); // sort the edges in non decreasing order
		Iterator<Edge> iterator = edges.iterator(); // create an iterator for the edges
		while (iterator.hasNext()) {
			Edge edge = iterator.next();
			int u = find(nodes.get(edge.getU())); // find parent of u
			int v = find(nodes.get(edge.getV())); // find parent of v
			if (u != v) {
				finalEdges.add(edge);
				path += edge.getWeight();
				union(nodes.get(u), nodes.get(v)); // union the two nodes
			}
		}
		iterator = finalEdges.iterator();
		System.out.println(path);
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		long endTime = System.currentTimeMillis(); // end time of algorithm
		long totalRunningTime = endTime - startTime; // Total running time in milliseconds
		System.out.println("\nTotal running time in MilliSecs : "
				+ totalRunningTime);
	}

}
