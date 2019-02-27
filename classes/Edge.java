import java.util.*;

public class Edge {

	public Node a;
	public Node b;
	private Double weight;
	// public Set<Node> twoEnds = new HashSet<Node>();

	public Edge(Node a, Node b, Double weight) {
		// this.twoEnds.add(a);
		// this.twoEnds.add(b);
		this.a = a;
		this.b = b;
		this.weight = weight;
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
		return this.weight;
	}
}