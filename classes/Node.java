import java.util.HashMap;
import java.lang.Math.*;

public class Node {
	
	private String tag;
	private String name;
	private double activation = 0;
	private double newActivation = 0;
	private HashMap<Node, Double> neighbors;
	private boolean settled = false;


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

	public boolean settled() {
		return this.settled;
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

	public double getNetInput() {
		double netInput = 0;
		for (Node neighbor: this.neighbors.keySet()) {
			double excitation = 0.0;
			double inhibition = 0.0;
			if(this.getWeight(neighbor)>0){
            excitation+=neighbor.getActivation()*(this.getWeight(neighbor));
			}
			else{
				inhibition+=neighbor.getActivation()*(this.getWeight(neighbor));
			}
			netInput += excitation+inhibition;
		}
		return netInput;
	}

	public double getWeight(Node node){
       double wt = this.neighbors.get(node);
       return wt;
	}

	public void computeNewActivation() {
		double netInput = getNetInput();
		Parameters parameters = new Parameters();
		if (netInput > 0) {
			this.newActivation = this.activation * (1 - parameters.decay) + netInput * (parameters.maximum - this.activation);
		} else {
			this.newActivation = this.activation * (1 - parameters.decay) + netInput * (this.activation - parameters.minimum);
		}

		/*this.newActivation = Math.max(this.newActivation, parameters.minimum);
		this.newActivation = Math.min(this.newActivation, parameters.maximum);
        */
		if(this.newActivation > parameters.maximum){
         this.newActivation = parameters.maximum;
		}
		if(this.newActivation < parameters.minimum){
        this.newActivation = parameters.minimum;
		}

		if (Math.abs(this.newActivation - this.activation) < parameters.threshold) {
			this.settled = true;
		}
		else{
			this.settled = false;
		}
	}

	public void updateActivation() {
		this.activation = this.newActivation;
	}
}