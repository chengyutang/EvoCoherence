import java.util.*;
import java.io.*;

public class Hello {
	public static void main(String[] args) {
		CoherenceNetwork myNetwork = new CoherenceNetwork();
		System.out.println(myNetwork.b2.getNeighbors().get(myNetwork.b1));
		myNetwork.runner();
		myNetwork.printActivations();
	}
}