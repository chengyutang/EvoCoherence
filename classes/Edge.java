import java.util.*;

public class Edge {

	private Node a;
	private Node b;
	private WeightSign weightSign;
	// public Set<Node> twoEnds = new HashSet<Node>();

	public Edge(Node a, Node b, WeightSign weightSign) {
		this.a = a;
		this.b = b;
		this.weightSign = weightSign;
	}

	@Override
	public int hashCode() {
		// return this.twoEnds.hashCode();
		return this.a.hashCode() + this.b.hashCode();
	}

	@Override
	public boolean equals(Object otherEdge) {
		if (this == otherEdge) {
			return true;
		}
		if (otherEdge == null || this.getClass() != otherEdge.getClass()) {
			return false;
		}
		Edge oe = (Edge) otherEdge;
		// return this.twoEnds.equals(oe.twoEnds);
		return (this.a == oe.a && this.b == oe.b) || (this.a == oe.b && this.b == oe.a);
	}

	public Double getWeight() {
		return this.weightSign.getWeight();
	}

	public void setWeight(double weight) {
		this.weightSign.setWeight(weight);
	}

	public int getSign() {
		return this.weightSign.getSign();
	}

	public void setSign(int sign) {
		this.weightSign.setSign(sign);
	}

	public Node getA() {
		return this.a;
	}

	public Node getB() {
		return this.b;
	}
}