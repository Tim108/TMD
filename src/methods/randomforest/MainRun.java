package methods.randomforest;

import java.util.ArrayList;
import java.util.HashMap;

public class MainRun {
    public static void main(String[] args) {

        System.out.println("Random-Forest with Categorical support");

        String DataInfo, traindata, testdata, foraccuracy, withthreads;
        int numTrees, numThreads, numAttris, Ms;
        numAttris = 30;
        numThreads = 1;
        numTrees = 1;
        traindata = "data/training/featuresMixedTrain.csv";
        testdata = "data/testing/featuresMixedTest.csv";
        DataInfo = "N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,L";
        DescribeTrees DT = new DescribeTrees(traindata, DataInfo);
        ArrayList<ArrayList<String>> Train = DT.CreateInputCateg(traindata, DataInfo);
        ArrayList<ArrayList<String>> Test = DT.CreateInputCateg(testdata, DataInfo);
        ArrayList<Character> DataLayout = DT.CreateFinalLayout(DataInfo);

        /**
         * For class-labels
         */
        HashMap<String, Integer> Classes = new HashMap<String, Integer>();
        for (ArrayList<String> dp : Train) {
            String clas = dp.get(dp.size() - 1);
            if (Classes.containsKey(clas))
                Classes.put(clas, Classes.get(clas) + 1);
            else
                Classes.put(clas, 1);
        }


        int M = DataLayout.size() - 1;
        if (numAttris < 1)
            Ms = (int) Math.round(Math.log(M) / Math.log(2) + 1);
        else
            Ms = numAttris;
        int C = Classes.size();
        RandomForest RFC = new RandomForest(DataLayout, numTrees, numThreads, M, Ms, C, Train, Test);
        RFC.Start(true, true);
        // JUST HAVE A TESTFILE OF 115200 ROWS, THAT EQUALS TO 16 HOURS OF ACTIVITY. THEN IT SHOULD BE CLEAR HOW MUCH POWER IT USES.
    }
}
