import java.util.*;
import java.io.*;

public class Hello {
	public static void main(String[] args) {
		CoherenceNetwork myNetwork = new CoherenceNetwork();
		System.out.printf("B1 B2 %f\n", myNetwork.b1.getNeighbors().get(myNetwork.b2));
		System.out.printf("B1 B3 %f\n", myNetwork.b1.getNeighbors().get(myNetwork.b3));
		System.out.printf("B1 B5 %f\n", myNetwork.b1.getNeighbors().get(myNetwork.b5));
		System.out.printf("B1 B7 %f\n", myNetwork.b1.getNeighbors().get(myNetwork.b7));
		System.out.printf("B2 B8 %f\n", myNetwork.b2.getNeighbors().get(myNetwork.b8));
		System.out.printf("B3 B5 %f\n", myNetwork.b3.getNeighbors().get(myNetwork.b5));
		System.out.printf("B3 B4 %f\n", myNetwork.b3.getNeighbors().get(myNetwork.b4));
		System.out.printf("B3 B6 %f\n", myNetwork.b3.getNeighbors().get(myNetwork.b6));
		System.out.printf("B3 B9 %f\n", myNetwork.b3.getNeighbors().get(myNetwork.b9));
		System.out.printf("B5 B4 %f\n", myNetwork.b5.getNeighbors().get(myNetwork.b4));
		System.out.printf("B5 B6 %f\n", myNetwork.b5.getNeighbors().get(myNetwork.b6));
		System.out.printf("B5 B9 %f\n", myNetwork.b5.getNeighbors().get(myNetwork.b9));
		System.out.printf("B6 B7 %f\n", myNetwork.b6.getNeighbors().get(myNetwork.b7));
		System.out.printf("B6 B10 %f\n", myNetwork.b6.getNeighbors().get(myNetwork.b10));
		System.out.printf("B7 B10 %f\n", myNetwork.b7.getNeighbors().get(myNetwork.b10));
		System.out.printf("B7 B14 %f\n", myNetwork.b7.getNeighbors().get(myNetwork.b14));
		System.out.printf("B7 B15 %f\n", myNetwork.b7.getNeighbors().get(myNetwork.b15));
		System.out.printf("B10 B16 %f\n", myNetwork.b10.getNeighbors().get(myNetwork.b16));
		myNetwork.runner();
		myNetwork.printActivations();
	}
}