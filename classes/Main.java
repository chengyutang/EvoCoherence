import java.util.*;
import java.io.*;
import java.lang.reflect.Field;

public class Main {
	public static void main(String[] args) {
		CoherenceNetwork myNetwork = new CoherenceNetwork();
		List<String> opinions = Arrays.asList("agree", "somewhatAgree", "somewhatAgree", "neitherAgreeNorDisagree", "neitherAgreeNorDisagree", "somewhatAgree", "agree", "agree", "somewhatAgree", "stronglyDisagree", "stronglyDisagree", "stronglyDisagree", "stronglyDisagree", "agree", "agree", "stronglyAgree");
		myNetwork.setActivations(generateActivationList(opinions));
		myNetwork.printActivations();
		myNetwork.calculateWeights();
		printWeights(myNetwork);
		myNetwork.runner();
		myNetwork.printActivations();
	}

	public static HashMap<String, Double> generateActivationMap(){
		ActivationParameters activationParams  = new ActivationParameters();
		HashMap<String,Double> pairs= new HashMap<String,Double>();
		Field[] f = activationParams.getClass().getDeclaredFields();
		for(int i=0; i<f.length; i++){

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

	public static List<Double> generateActivationList(List<String> opinions) {
		HashMap<String, Double> activationMap = generateActivationMap();
		List<Double> activationList = new ArrayList<>();
		for (String op: opinions) {
			activationList.add(activationMap.get(op));
		}
		return activationList;
	}

	public static void printWeights(CoherenceNetwork myNetwork) {
		System.out.printf("B1 B2 %f\n", myNetwork.b1.getWeight(myNetwork.b2));
		System.out.printf("B1 B3 %f\n", myNetwork.b1.getWeight(myNetwork.b3));
		System.out.printf("B1 B5 %f\n", myNetwork.b1.getWeight(myNetwork.b5));
		System.out.printf("B1 B7 %f\n", myNetwork.b1.getWeight(myNetwork.b7));
		System.out.printf("B2 B8 %f\n", myNetwork.b2.getWeight(myNetwork.b8));
		System.out.printf("B3 B5 %f\n", myNetwork.b3.getWeight(myNetwork.b5));
		System.out.printf("B3 B4 %f\n", myNetwork.b3.getWeight(myNetwork.b4));
		System.out.printf("B3 B6 %f\n", myNetwork.b3.getWeight(myNetwork.b6));
		System.out.printf("B3 B9 %f\n", myNetwork.b3.getWeight(myNetwork.b9));
		System.out.printf("B5 B4 %f\n", myNetwork.b5.getWeight(myNetwork.b4));
		System.out.printf("B5 B6 %f\n", myNetwork.b5.getWeight(myNetwork.b6));
		System.out.printf("B5 B9 %f\n", myNetwork.b5.getWeight(myNetwork.b9));
		System.out.printf("B6 B7 %f\n", myNetwork.b6.getWeight(myNetwork.b7));
		System.out.printf("B6 B10 %f\n", myNetwork.b6.getWeight(myNetwork.b10));
		System.out.printf("B7 B10 %f\n", myNetwork.b7.getWeight(myNetwork.b10));
		System.out.printf("B7 B14 %f\n", myNetwork.b7.getWeight(myNetwork.b14));
		System.out.printf("B7 B15 %f\n", myNetwork.b7.getWeight(myNetwork.b15));
		System.out.printf("B10 B16 %f\n", myNetwork.b10.getWeight(myNetwork.b16));
	}
}