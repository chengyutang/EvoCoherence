import java.util.HashMap;

public class Node {
	
	private String tag;
	private String name;
	private double activation = 0;
	private HashMap<Node, Double> neighbors;

	// Constructor, called when the activation value is not given.
	public Node(String tag, String name) {
		this.tag = tag;
		this.name = name;
		this.neighbors = new HashMap<Node, Double>();
	}

	// Constructor, called when the activation value is given.
	public Node(String tag, String name, double activation) {
		this.tag = tag;
		this.name = name;
		this.activation = activation;
		this.neighbors = new HashMap<Node, Double>();
	}

	public String getTag() {
		return this.tag;
	}

	// Return the name of the node
	public String getName() {
		return this.name;
	}

	public double getActivation() {
		return this.activation;
	}

	public HashMap<Node, Double> getNeighbors() {
		return this.neighbors;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setActivation(double activation) {
		this.activation = activation;
	}

	public void addNeighbor(Node neighbor, double weight) {
		if (!this.neighbors.containsKey(neighbor)) {
			this.neighbors.put(neighbor, weight);
			neighbor.addNeighbor(this, weight);
		}
	}

	public void addNeighbor(Node neighbor, double weight, boolean directional) {
		if (!this.neighbors.containsKey(neighbor)) {
			this.neighbors.put(neighbor, weight);
			if (directional) {
				neighbor.addNeighbor(this, weight, true);
			}
		}
	}

}