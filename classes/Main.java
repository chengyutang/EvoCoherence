import java.util.*;
import java.io.*;
import java.lang.reflect.Field;

public class Main {
	public static void main(String[] args) {
		// CoherenceNetwork myNetwork = new CoherenceNetwork();
		List<String> opinions = Arrays.asList("agree", "somewhatAgree", "stronglyDisagree", "neitherAgreeNorDisagree", "neitherAgreeNorDisagree", "somewhatAgree", "agree", "agree", "somewhatAgree", "stronglyDisagree", "stronglyDisagree", "stronglyDisagree", "stronglyDisagree", "agree", "agree", "stronglyAgree");
		// myNetwork.setActivations(prepareActivationList(opinions));
		// myNetwork.printActivations();
		// myNetwork.calculateWeights();
		// myNetwork.printWeights();
		// // printWeights(myNetwork);
		// myNetwork.runner();
		// myNetwork.printActivations();

		// test codes
		// CoherenceNetwork myNetwork = new CoherenceNetwork("sampleXML.xml");
		// myNetwork.printActivations();
		// myNetwork.printWeights();
		// myNetwork.calculateWeights();
		// myNetwork.printWeights();
		// myNetwork.runner();
		// myNetwork.printActivations();

		// CoherenceNetwork myNetwork = new CoherenceNetwork();
		// List<Double> activationPool = Arrays.asList(0.0, 0.17, 0.33, 0.4, 0.67, 0.83, 1.0);

		// List<Double> finalActivations = new ArrayList<Double>();
		// for (Double newActivation: activationPool) {
		// 	System.out.println("-----------------------");
		// 	myNetwork.setActivations(prepareActivationList(opinions));
		// 	myNetwork.nodeList.get(1).setActivation(newActivation);
		// 	// myNetwork.nodeList.get(9).setActivation(newActivation);
		// 	// myNetwork.nodeList.get(13).setActivation(newActivation);
		// 	// myNetwork.printActivations();
		// 	myNetwork.calculateWeights();
		// 	// myNetwork.printWeights();
		// 	myNetwork.runner();
		// 	// myNetwork.printActivations();
		// 	// System.out.printf("Activation of label: %f\n\n", myNetwork.nodeList.get(6).getActivation());
		// 	finalActivations.add(myNetwork.nodeList.get(6).getActivation());
		// }

		// for (Double acti: finalActivations) {
		// 	System.out.printf("%f ", acti);
		// }
		// System.out.println();



		CoherenceNetwork myNetwork = new CoherenceNetwork();
		myNetwork.setActivations(prepareActivationList(opinions));
		myNetwork.calculateWeights();
		myNetwork.runner();
		System.out.printf("%f\n\n", myNetwork.getNode("B7").getActivation());

		int i = 1;
		for (Node node: myNetwork.nodeList) {
			if (i == 7) {
				i++;
				continue;
			}
			myNetwork = new CoherenceNetwork();
			myNetwork.setActivations(prepareActivationList(opinions));
			myNetwork.removeNode(i);
			myNetwork.printNodeList();
			myNetwork.calculateWeights();
			myNetwork.runner();
			System.out.printf("%f\n\n", myNetwork.getNode("B7").getActivation());
			i++;
		}
	}

	public static List<Double> prepareActivationList(List<String> opinions) {
		HashMap<String, Double> activationMap = generateActivationMap();
		List<Double> activationList = new ArrayList<>();
		for (String op: opinions) {
			activationList.add(activationMap.get(op));
		}
		return activationList;
	}

	public static HashMap<String, Double> generateActivationMap() {
		ActivationParameters activationParams = new ActivationParameters();
		HashMap<String,Double> pairs = new HashMap<String, Double>();
		Field[] f = activationParams.getClass().getDeclaredFields();
		for(int i = 0; i < f.length; i++){

			String fieldName = f[i].getName();
			f[i].setAccessible(true);

			try {
				Object newObj = f[i].get(activationParams.getClass());
				String activ = String.valueOf(f[i].get(activationParams.getClass()));
				Double act = Double.parseDouble(activ);
				pairs.put(fieldName,act);

			} catch (Exception e){
				System.out.println(e);
			}

		}
		return pairs;
	}

	// public static void printWeights(CoherenceNetwork myNetwork) {
	// 	System.out.printf("B1 B2 %f\n", myNetwork.b1.getWeight(myNetwork.b2));
	// 	System.out.printf("B1 B3 %f\n", myNetwork.b1.getWeight(myNetwork.b3));
	// 	System.out.printf("B1 B5 %f\n", myNetwork.b1.getWeight(myNetwork.b5));
	// 	System.out.printf("B1 B7 %f\n", myNetwork.b1.getWeight(myNetwork.b7));
	// 	System.out.printf("B2 B8 %f\n", myNetwork.b2.getWeight(myNetwork.b8));
	// 	System.out.printf("B3 B5 %f\n", myNetwork.b3.getWeight(myNetwork.b5));
	// 	System.out.printf("B3 B4 %f\n", myNetwork.b3.getWeight(myNetwork.b4));
	// 	System.out.printf("B3 B6 %f\n", myNetwork.b3.getWeight(myNetwork.b6));
	// 	System.out.printf("B3 B9 %f\n", myNetwork.b3.getWeight(myNetwork.b9));
	// 	System.out.printf("B5 B4 %f\n", myNetwork.b5.getWeight(myNetwork.b4));
	// 	System.out.printf("B5 B6 %f\n", myNetwork.b5.getWeight(myNetwork.b6));
	// 	System.out.printf("B5 B9 %f\n", myNetwork.b5.getWeight(myNetwork.b9));
	// 	System.out.printf("B6 B7 %f\n", myNetwork.b6.getWeight(myNetwork.b7));
	// 	System.out.printf("B6 B10 %f\n", myNetwork.b6.getWeight(myNetwork.b10));
	// 	System.out.printf("B7 B10 %f\n", myNetwork.b7.getWeight(myNetwork.b10));
	// 	System.out.printf("B7 B14 %f\n", myNetwork.b7.getWeight(myNetwork.b14));
	// 	System.out.printf("B7 B15 %f\n", myNetwork.b7.getWeight(myNetwork.b15));
	// 	System.out.printf("B10 B16 %f\n", myNetwork.b10.getWeight(myNetwork.b16));
	// }
}