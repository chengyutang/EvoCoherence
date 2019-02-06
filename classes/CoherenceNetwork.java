import java.util.*;
import java.io.*;

public class CoherenceNetwork {

	private Set<Node> visited = new HashSet<Node>();
	// Create Belief/evidence nodes
	public Node b1  = new Node("B1",  "The available data are unclear about whether evolution actually occurs.", 0.2);
	public Node b2  = new Node("B2",  "Most scientists accept evolutionary theory to be scientifically valid.", 0.8);
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
	public List<Node> nodes = new ArrayList<Node>(Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16));

	// Default constructor
	public CoherenceNetwork() {
		
		// establish the connections
		this.b1.addNeighbor(this.b2, 0.4);
		this.b1.addNeighbor(this.b3, 0);
		this.b1.addNeighbor(this.b5, 0);
		this.b1.addNeighbor(this.b7, 0);
		this.b2.addNeighbor(this.b8, 0);
		this.b3.addNeighbor(this.b5, 0);
		this.b3.addNeighbor(this.b4, 0);
		this.b3.addNeighbor(this.b6, 0);
		this.b3.addNeighbor(this.b9, 0);
		this.b5.addNeighbor(this.b4, 0);
		this.b5.addNeighbor(this.b6, 0);
		this.b5.addNeighbor(this.b9, 0);
		this.b6.addNeighbor(this.b7, 0);
		this.b6.addNeighbor(this.b10, 0);
		this.b7.addNeighbor(this.b10, 0);
		this.b7.addNeighbor(this.b14, 0);
		this.b7.addNeighbor(this.b15, 0);
		this.b10.addNeighbor(this.b16, 0);
	}

	// Constructor, called when an XML file is provided to create customized network
	public CoherenceNetwork(String fileName) {

	}

	// Traversal the entire network and print tag of each node
	public void traversal(Node start) {
		this.visited.add(start);
		System.out.println(start.getTag());
		for (Node neighbor: start.getNeighbors().keySet()) {
			if (!this.visited.contains(neighbor)) {
				traversal(neighbor);
			}
		}
	}

	public void runner() {
		int cnt = 0;
		while (!settled()) {
			System.out.printf("Iter %d\n", cnt);
			for (Node node: this.nodes) {
				node.computeNewActivation();
			}

			for (Node node: this.nodes) {
				node.updateActivation();
			}
			cnt += 1;
		}
	}

	public boolean settled() {
		for (Node node: this.nodes) {
			if (!node.settled()) {
				return false;
			}
		}
		return true;
	}

	public void printActivations() {
		for (Node node: this.nodes) {
			System.out.println(node.getActivation());
		}
	}
}