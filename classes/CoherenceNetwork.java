import java.util.*;
import java.io.*;
import java.lang.Math.*;

public class CoherenceNetwork {

	private Set<Node> visited = new HashSet<Node>();

	public Node b1  = new Node("B1",  "The available data are unclear about whether evolution actually occurs.");
	public Node b2  = new Node("B2",  "Most scientists accept evolutionary theory to be scientifically valid.");
	public Node b3  = new Node("B3",  "Organisms existing today are the result of evolutionary processes that have occurred over millions of years.");
	public Node b4  = new Node("B4",  "The age of the earth is less than 20,000 yesrs.");
	public Node b5  = new Node("B5",  "Human being on earth today are the result of evolutionary processes that have occurred over millions of years.");
	public Node b6  = new Node("B6",  "The theory of evolution helps me appreciate characteristics and behaviors observed in living forms.");
	public Node b7  = new Node("B7",  "The theory of evolution cannot be correct, since it disagrees with the accounts of creation in many religious texts.");
	public Node b8  = new Node("B8",  "Whether you accept evolutionary science should be a matter of what the evidence indicates.");
	public Node b9  = new Node("B9",  "People who accept that human beings are the result of evolutionary processes are very different from me.");
	public Node b10 = new Node("B10", "Biology is important to me.");
	public Node b11 = new Node("B11", "Being good at biology will be useful to me in my future career.");
	public Node b12 = new Node("B12", "My biology abilities are important to my academic success.");
	public Node b13 = new Node("B13", "Doing well in biology is critical to my future success.");
	public Node b14 = new Node("B14", "I consider myself a religious person.");
	public Node b15 = new Node("B15", "I consider myself a spiritual person.");
	public Node b16 = new Node("B16", "I'm a biology major.");

	public List<Node> nodeList = Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16);

	// Default constructor
	public CoherenceNetwork() {
		
		// establish the connections
		this.b1.addNeighbor(this.b2, 0, -1);
		this.b1.addNeighbor(this.b3, 0, -1);
		this.b1.addNeighbor(this.b5, 0, -1);
		this.b1.addNeighbor(this.b7, 0,  1);
		this.b2.addNeighbor(this.b8, 0,  1);
		this.b3.addNeighbor(this.b5, 0,  1);
		this.b3.addNeighbor(this.b4, 0, -1);
		this.b3.addNeighbor(this.b6, 0,  1);
		this.b3.addNeighbor(this.b9, 0, -1);
		this.b5.addNeighbor(this.b4, 0, -1);
		this.b5.addNeighbor(this.b6, 0,  1);
		this.b5.addNeighbor(this.b9, 0, -1);
		this.b6.addNeighbor(this.b7, 0, -1);
		this.b6.addNeighbor(this.b10, 0, 1);
		this.b7.addNeighbor(this.b10, 0, -1);
		this.b7.addNeighbor(this.b14, 0, 1);
		this.b7.addNeighbor(this.b15, 0, 1);
		this.b10.addNeighbor(this.b16, 0, 1);
	}

	// Constructor, called when an XML file is provided to create customized network
	public CoherenceNetwork(String fileName) {

	}

	// Set activation values for all nodes in order given a list of activation values
	public void setActivations(List<Double> activations) {
		for (int i = 0; i < this.nodeList.size(); i++) {
			this.nodeList.get(i).setActivation(activations.get(i));
		}
	}

	// Print the activation value of each node
	public void printActivations() {
		System.out.println("Activation values");
		for (Node node: this.nodeList) {
			System.out.printf("%s: %f\n", node.getTag(), node.getActivation());
		}	
	}

	// find all edges and return them as a set of Edge objects
	public Set<Edge> generateEdgeSet() {
		Set<Edge> edgeSet = new HashSet<Edge>();
		for (Node node: this.nodeList) {
			for (Node neighbor: node.getNeighbors().keySet()) {
				edgeSet.add(new Edge(node, neighbor, node.getWeight(neighbor)));
			}
		}
		return edgeSet;
	}

	// Print the weight of each edge of the network in random order
	public void printWeights() {
		Set<Edge> edgeSet = generateEdgeSet();
		System.out.printf("Number of edges: %d\n", edgeSet.size());
		for (Edge edge: edgeSet) {
			System.out.printf("%s %s: %f\n", edge.getA().getTag(), edge.getB().getTag(), edge.getWeight());
		}
	}

	// Calculate the weight for each edge according to the activation values of its two ends and its nature/sign
	public void calculateWeights() {
		for (Node curNode: this.nodeList) {
			for (Node neighbor: curNode.getNeighbors().keySet()) {
				double weight = 0;
				if (curNode.getSign(neighbor) == 1) {
					weight =  1 - (Math.abs(curNode.getActivation() - neighbor.getActivation()));
				} else {
					weight = - (Math.abs(curNode.getActivation() - neighbor.getActivation()));
				}
				// double weight =  (curNode.getSign(neighbor) + 1) / 2 - (Math.abs(curNode.getActivation() - neighbor.getActivation()));
				curNode.setWeight(neighbor, weight);
			}
		}
	}

	// Traverse every node in the network using BFS and print their tags
	public void nodeTraversal(Node start) {
		this.visited.add(start);
		System.out.println(start.getTag());
		for (Node neighbor: start.getNeighbors().keySet()) {
			if (!this.visited.contains(neighbor)) {
				nodeTraversal(neighbor);
			}
		}
		this.visited.clear();
	}

	public void runner() {
		System.out.println("\nStart running...");
		int cnt = 0;
		while (!settled()) {
			// if ((cnt % 1000) == 0) {
			// 	System.out.printf("Iter %d\n", cnt);
			// }
			System.out.printf("Iter %d\n", cnt);
			for (Node node: this.nodeList) {
				node.computeNewActivation();
			}

			for (Node node: this.nodeList) {
				node.updateActivation();
			}
			cnt += 1;
		}
		System.out.println("Finished.\n");
	}

	// Return True if all every nodes has settled, False otherwise
	public boolean settled() {
		for (Node node: this.nodeList) {
			if (!node.settled()) {
				return false;
			}
		}
		return true;
	}
}