package methods.knn;

import methods.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tim on 25/05/2016.
 */
public class KNN {

    private static final int numFeatures = 15;

    private static final String walking = "1.1111e+07"; //walking
    private static final String running = "2.2222e+07"; //running
    private static final String cycling = "3.3333e+07"; //cycling
    private static final String car = "4.4444e+07"; //car

    public void knn() {
        long starttime = System.currentTimeMillis();
        ArrayList<Feature<Integer>>[] data = new ArrayList[numFeatures];
        for (int i = 0; i < numFeatures; i++) {
            data[i] = new ArrayList<>();
        }


        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("data/datasf1t5.csv")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] datas = content.split("\n");
        int x = (int) Math.floor(data.length * 0.8);

        String[] trainlines = new String[x];
        System.arraycopy(datas, 0, trainlines, 0, x);
        String[] testlines = new String[data.length - x];
        System.arraycopy(datas, x, testlines, 0, data.length - x);


        String[] lines = trainlines;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
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
            for (int j = 0; j < numFeatures; j++) {
                Double dub = Double.parseDouble(words[j]);
                Feature f = new Feature(c, "" + j, dub);
                data[j].add(f);
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
//            System.out.println("Expected\t\t\t\tResult");

        lines = testlines;
        List<String> results = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            //read, split line
            String[] words = line.split(",");

            // check if there is the right number of attributes + 1 class
            if (words.length != numFeatures + 1) {
                continue;
            }

            Map<String, Double> attributes = new HashMap<String, Double>();
            for (int j = 0; j < numFeatures; j++) {
                attributes.put("" + j, Double.parseDouble(words[j]));
            }

            Instance<String> instance = new Instance(attributes);
            space.compute(instance, false);

            String result = instance.category;
            String actual = words[numFeatures];
            results.add(actual + "," + result);
//                System.out.println(actual + "\t\t\t\t" + result);

            total++;
            if (result.equals(result)) correct++;
        }

        Path file = Paths.get("results.csv");
        try {
            Files.write(file, results, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(100 * correct / total + "%");

        double s = (double) (System.currentTimeMillis() - starttime) / 1000;
        int h = (int) Math.floor(s / ((double) 3600));
        s -= (h * 3600);
        int m = (int) Math.floor(s / ((double) 60));
        s -= (m * 60);
        System.out.println("" + h + "hr " + m + "m " + s + "sec");

    }
}
