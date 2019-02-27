import java.util.HashMap;
import java.lang.Math.*;
import java.lang.reflect.Field;

public class Node {
	
	private String tag;
	private String name;
	private double activation = 0;
	private double newActivation = 0;
	private HashMap<Node, WeightSign> neighbors;
	private boolean settled = false;

	// Constructor, called when the activation value is not given.
	public Node(String tag, String name) {
		this.tag = tag;
		this.name = name;
		this.neighbors = new HashMap<Node, WeightSign>();
	}

	// Constructor, called when the activation value is given.
	public Node(String tag, String name, double activation) { //, String opinion) {
		this.tag = tag;
		this.name = name;
		this.activation = activation;
		// this.opinion = opinion;
		this.neighbors = new HashMap<Node, WeightSign>(); // <nodeTage, WeightSign>
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	// Return the name of the node
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getActivation() {
		return this.activation;
	}

	public void setActivation(double activation) {
		this.activation = activation;
	}

	// Return a hashmap that contains all its neighbors
	public HashMap<Node, WeightSign> getNeighbors() {
		return this.neighbors;
	}

	// Given a neighbor and return the weight of the connection between them
	public double getWeight(Node neighbor) {
       return this.neighbors.get(neighbor).getWeight();
	}

	public void setWeight(Node neighbor, double weight) {
		this.neighbors.get(neighbor).setWeight(weight);
	}

	public int getSign(Node neighbor) {
		return this.neighbors.get(neighbor).getSign();
	}

	public boolean settled() {
		return this.settled;
	}

	public HashMap<String, Double> generateWeightList(){
		WeightParameters weightParams  = new WeightParameters();
		HashMap<String,Double> pairs= new HashMap<String,Double>();
		Field[] f = weightParams.getClass().getDeclaredFields();
		for(int i=0; i<f.length; i++){

			String fieldName = f[i].getName();
			f[i].setAccessible(true);

			try {
				Object newObj = f[i].get(weightParams.getClass());
				String activ = String.valueOf(f[i].get(weightParams.getClass()));
				Double act = Double.parseDouble(activ);
				pairs.put(fieldName,act);

			} catch (Exception e){
				System.out.println(e);
			}

		}
		return pairs;
	}

	public void addNeighbor(Node neighbor, double weight, int sign) {
		// HashMap<String, Double> weightList = neighbor.generateWeightList();
		if (!this.neighbors.containsKey(neighbor)) {
			// weight = generateWeight(neighbor, sign, weightList);
			this.neighbors.put(neighbor, new WeightSign(weight, sign));
			neighbor.addNeighbor(this, weight, sign);
		}
	}

	public void getValue() {
		HashMap<String, Double> pair = generateWeightList();
		System.out.println(pair);
	}

	// public void addNeighbor(Node neighbor, double weight, boolean directional) {
	// 	if (!this.neighbors.containsKey(neighbor)) {
	// 		this.neighbors.put(neighbor, weight);
	// 		if (directional) {
	// 			neighbor.addNeighbor(this, weight, true);
	// 		}
	// 	}
	// }

	public double getNetInput() {
		double netInput = 0;
		for (Node neighbor: this.neighbors.keySet()) {
			double excitation = 0.0;
			double inhibition = 0.0;
			if(this.getWeight(neighbor) > 0){
            	excitation += neighbor.getActivation() * (this.getWeight(neighbor));
			} else {
				inhibition += neighbor.getActivation() * (this.getWeight(neighbor));
			}
			netInput += excitation + inhibition;
		}
		return netInput;
	}

	public void computeNewActivation() {
		double netInput = getNetInput();
		Parameters parameters = new Parameters();
		if (netInput > 0) {
			this.newActivation = this.activation * (1 - parameters.decay) + netInput * (parameters.maximum - this.activation);
		} else {
			this.newActivation = this.activation * (1 - parameters.decay) + netInput * (this.activation - parameters.minimum);
		}
		if (this.newActivation > parameters.maximum) {
        	this.newActivation = parameters.maximum;
		}
		if (this.newActivation < parameters.minimum) {
        	this.newActivation = parameters.minimum;
		}

		if (Math.abs(this.newActivation - this.activation) < parameters.threshold) {
			this.settled = true;
		} else {
			this.settled = false;
		}
	}

	public void updateActivation() {
		this.activation = this.newActivation;
	}

	public static void main(String[] args) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException{
		Node node = new Node("earth is spherical", "B1");
		node.generateWeightList();
		node.getValue();

	}
}