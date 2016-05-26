package methods.knn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tim on 25/05/2016.
 */
public class MainRun {

    private static final String pathtTrain = "data/training/featuresMixedTrain.csv";
    private static final String pathTest = "data/testing/featuresMixedTest.csv";

    private static final int numFeatures = 30;

    private static final String walking = "walking";
    private static final String running = "running";
    private static final String cycling = "cycling";
    private static final String car = "car";

    public static void main(String[] args) throws IOException {
        ArrayList<Feature<Integer>>[] data = new ArrayList[numFeatures];
        for (int i = 0; i < numFeatures; i++) {
            data[i] = new ArrayList<>();
        }

        BufferedReader BR = new BufferedReader(new FileReader(pathtTrain));
        String line;
        while ((line = BR.readLine()) != null) {
            //read, split line
            String[] words = line.split(",");

            // check if there is the right number of attributes + 1 class
            if (words.length != numFeatures + 1) {
                continue;
            }

            // determine class
            String s = (String) words[numFeatures];
            String c = "";
            if (s.equals(walking)) c = walking;
            else if (s.equals(running)) c = running;
            else if (s.equals(cycling)) c = cycling;
            else if (s.equals(car)) c = car;
            else System.out.println("### shit fucked up");

            // parse the attributes to doubles
            for (int i = 0; i < numFeatures; i++) {
                Double d = Double.parseDouble(words[i]);
                Feature f = new Feature(c, "" + i, d);
                data[i].add(f);
            }
        }

        // put the data in the right format (features)
        Feature<Integer>[][] features = new Feature[numFeatures][];
        for (int i = 0; i < numFeatures; i++) {
            Feature<Integer>[] fl = new Feature[data[i].size()];
            for (int j = 0; j < data[i].size(); j++) {
                fl[j] = data[i].get(j);
            }
            features[i] = fl;
        }

//        // print it
//        for (int i = 0; i < features.length; i++) {
//            System.out.print("[");
//            for (int j = 0; j < features[i].length; j++) {
//                System.out.print(features[i][j].toString() + " ");
//            }
//            System.out.println("]");
//        }

        // make the feature space
        FeatureSpace space = new FeatureSpace(features);
        space.k = 1;

        // now for each test make an instance and test it.
        int total = 0;
        int correct = 0;
        System.out.println("Expected\t\t\t\tResult");
        BR = new BufferedReader(new FileReader(pathTest));
        while ((line = BR.readLine()) != null) {
            //read, split line
            String[] words = line.split(",");

            // check if there is the right number of attributes + 1 class
            if (words.length != numFeatures + 1) {
                continue;
            }

            Map<String, Double> attributes = new HashMap<String, Double>();
            for (int i = 0; i < numFeatures; i++) {
                attributes.put("" + i, Double.parseDouble(words[i]));
            }

            Instance<String> instance = new Instance(attributes);
            space.compute(instance, false);

            String result = instance.category;
            String actual = words[numFeatures];
            System.out.println(actual + "\t\t\t\t" + result);

            total++;
            if(result.equals(result)) correct++;
        }
        System.out.println(100 * correct / total + "%");
    }
}
