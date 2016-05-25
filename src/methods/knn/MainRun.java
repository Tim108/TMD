package methods.knn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
        Feature<Integer>[][] features = new Feature[30][];

        ArrayList<Object[]> data = new ArrayList<>();

        BufferedReader BR = new BufferedReader(new FileReader(pathtTrain));
        String line;
        while((line = BR.readLine()) != null){
            Object[] attributes = line.split(",");
            if (attributes.length != numFeatures + 1) {
                continue;
            }
            for(int i = 0; i < attributes.length-1; i++) {
                attributes[i] = Double.parseDouble((String)attributes[i]);
            }

            String s = (String)attributes[numFeatures + 1];
            if(s == walking) attributes[numFeatures+1] = walking;
            if(s == running) attributes[numFeatures+1] = running;
            if(s == cycling) attributes[numFeatures+1] = cycling;
            if(s == car) attributes[numFeatures+1] = car;

            data.add(attributes);
        }

        for(int i = 0; i <= numFeatures; i++) {
            for(int j = 0; j < data.size(); i++) {
                Feature f = new Feature(data.get(j)[numFeatures + 1], ("att" + j), (double)data.get(j)[i]);

            }
        }
    }
}
