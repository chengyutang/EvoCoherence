import java.util.*;
import java.io.*;
import java.lang.Math.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class CoherenceNetwork {

	public List<Node> nodeList = new ArrayList<Node>();

	// Default constructor
	public CoherenceNetwork() {

		Node b1  = new Node("B1",  "The available data are unclear about whether evolution actually occurs.");
		Node b2  = new Node("B2",  "Most scientists accept evolutionary theory to be scientifically valid.");
		Node b3  = new Node("B3",  "Organisms existing today are the result of evolutionary processes that have occurred over millions of years.");
		Node b4  = new Node("B4",  "The age of the earth is less than 20,000 yesrs.");
		Node b5  = new Node("B5",  "Human being on earth today are the result of evolutionary processes that have occurred over millions of years.");
		Node b6  = new Node("B6",  "The theory of evolution helps me appreciate characteristics and behaviors observed in living forms.");
		Node b7  = new Node("B7",  "The theory of evolution cannot be correct, since it disagrees with the accounts of creation in many religious texts.");
		Node b8  = new Node("B8",  "Whether you accept evolutionary science should be a matter of what the evidence indicates.");
		Node b9  = new Node("B9",  "People who accept that human beings are the result of evolutionary processes are very different from me.");
		Node b10 = new Node("B10", "Biology is important to me.");
		Node b11 = new Node("B11", "Being good at biology will be useful to me in my future career.");
		Node b12 = new Node("B12", "My biology abilities are important to my academic success.");
		Node b13 = new Node("B13", "Doing well in biology is critical to my future success.");
		Node b14 = new Node("B14", "I consider myself a religious person.");
		Node b15 = new Node("B15", "I consider myself a spiritual person.");
		Node b16 = new Node("B16", "I'm a biology major.");
		
		// establish the connections
		b1.addNeighbor(b2, 0, -1);
		b1.addNeighbor(b3, 0, -1);
		b1.addNeighbor(b5, 0, -1);
		b1.addNeighbor(b7, 0,  1);
		b2.addNeighbor(b8, 0,  1);
		b3.addNeighbor(b5, 0,  1);
		b3.addNeighbor(b4, 0, -1);
		b3.addNeighbor(b6, 0,  1);
		b3.addNeighbor(b9, 0, -1);
		b5.addNeighbor(b4, 0, -1);
		b5.addNeighbor(b6, 0,  1);
		b5.addNeighbor(b9, 0, -1);
		b6.addNeighbor(b7, 0, -1);
		b6.addNeighbor(b10, 0, 1);
		b7.addNeighbor(b10, 0, -1);
		b7.addNeighbor(b14, 0, 1);
		b7.addNeighbor(b15, 0, 1);
		b10.addNeighbor(b16, 0, 1);

		this.nodeList.addAll(Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16));
	}

	// Constructor, called when an XML file is provided to create customized network
	public CoherenceNetwork(String fileName) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(fileName);
			doc.getDocumentElement().normalize();

			// Build this.nodeList
			NodeList xmlNodeList = doc.getElementsByTagName("node");
			for (int i = 0; i < xmlNodeList.getLength(); i++) {
				Element curNode = (Element) xmlNodeList.item(i);
				String tag = curNode.getElementsByTagName("tag").item(0).getTextContent();
				String text = curNode.getElementsByTagName("text").item(0).getTextContent();
				double activation = Double.parseDouble(curNode.getElementsByTagName("activation").item(0).getTextContent());
				this.nodeList.add(new Node(tag, text, activation));
				// System.out.printf("Node %s added\n", curNodeObj.getTag());
			}

			// Build edges
			NodeList xmlEdgeList = doc.getElementsByTagName("connection");
			for (int i = 0; i < xmlEdgeList.getLength(); i++) {
				Element curEdge = (Element) xmlEdgeList.item(i);
				Node nodeA = getNode(curEdge.getElementsByTagName("end1").item(0).getTextContent());
				Node nodeB = getNode(curEdge.getElementsByTagName("end2").item(0).getTextContent());
				double weight = Double.parseDouble(curEdge.getElementsByTagName("weight").item(0).getTextContent());
				int sign = Integer.parseInt(curEdge.getElementsByTagName("sign").item(0).getTextContent());
				nodeA.addNeighbor(nodeB, weight, sign);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	public Node getNode(String tag) {
		for (Node node: this.nodeList) {
			if (node.getTag().equals(tag)) {
				return node;
			}
		}
		System.out.printf("Node %s not found.\n", tag);
		return null;
	}

	public void printNodeList() {
		for (Node node: this.nodeList) {
			System.out.printf("%s ", node.getTag());
		}
		System.out.printf("\n");
	}

	public void removeNode(int index) {
		this.nodeList.remove(index - 1);
		// printNodeList();
		Node nodeToRemove = this.nodeList.get(index - 1);
		for (Node neighbor: nodeToRemove.getNeighbors().keySet()) {
			neighbor.getNeighbors().remove(nodeToRemove);
		}
	}

	// Set activation values for all nodes in order given a list of activation values
	public void setActivations(List<Double> activations) {
		for (int i = 0; i < this.nodeList.size(); i++) {
			this.nodeList.get(i).setActivation(activations.get(i));
		}
	}

	// Print the activation value of each node
	public void printActivations() {
		System.out.printf("Printing node activation values\nNumber of nodes: %d\n", this.nodeList.size());
		for (Node node: this.nodeList) {
			System.out.printf("%s: %f\n", node.getTag(), node.getActivation());
		}	
	}

	// find all edges and return them as a set of Edge objects
	public Set<Edge> generateEdgeSet() {
		Set<Edge> edgeSet = new HashSet<Edge>();
		for (Node node: this.nodeList) {
			for (Node neighbor: node.getNeighbors().keySet()) {
				edgeSet.add(new Edge(node, neighbor, new WeightSign(node.getWeight(neighbor), node.getSign(neighbor))));
			}
		}
		return edgeSet;
	}

	// Print the weight of each edge of the network in random order
	public void printWeights() {
		Set<Edge> edgeSet = generateEdgeSet();
		System.out.printf("\nPrinting edge weights\nNumber of edges: %d\n", edgeSet.size());
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
					weight =  1 - (Math.abs(curNode.getActivation() - neighbor.getActivation())) / 2;
				} else {
					weight = - (Math.abs(curNode.getActivation() - neighbor.getActivation())) / 2;
				}
				// double weight =  (curNode.getSign(neighbor) + 1) / 2 - (Math.abs(curNode.getActivation() - neighbor.getActivation()));
				curNode.setWeight(neighbor, weight);
			}
		}
	}

	// Traverse every node in the network using BFS and print their tags
	public void nodeTraversal(Node start) {
		Set<Node> visited = new HashSet<Node>();
		visited.add(start);
		System.out.println(start.getTag());
		for (Node neighbor: start.getNeighbors().keySet()) {
			if (!visited.contains(neighbor)) {
				nodeTraversal(neighbor);
			}
		}
	}

	public void runner() {
		// System.out.println("\nStart running...");
		int cnt = 0;
		while (!settled()) {
			// if ((cnt % 1000) == 0) {
			// 	System.out.printf("Iter %d\n", cnt);
			// }
			// System.out.printf("Iter %d\n", cnt);
			for (Node node: this.nodeList) {
				node.computeNewActivation();
			}

			for (Node node: this.nodeList) {
				node.updateActivation();
			}
			cnt += 1;
		}
		System.out.println("Finished.\n");
		resetSettled();
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

	public void resetSettled() {
		for (Node node: this.nodeList) {
			node.resetSettled();
		}
	}
}