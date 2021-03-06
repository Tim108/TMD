package methods.randomforest;

import java.util.ArrayList;
import java.util.HashMap;

public class Forest {
    public void randomforest(int numTrees) {

        System.out.println("Random-Forest with Categorical support");

        String DataInfo, traindata, testdata, foraccuracy, withthreads;
        int numThreads, numAttris, Ms;
        numAttris = 0;
        numThreads = 1;
        //DataInfo = "N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,L";
        DataInfo = "N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,L";
        DescribeTrees DT = new DescribeTrees("train", DataInfo);
        ArrayList<ArrayList<String>> Train = DT.CreateInputCateg("train", DataInfo);
        ArrayList<ArrayList<String>> Test = DT.CreateInputCateg("test", DataInfo);
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
        // JUST HAVE A TESTFILE OF 1920 ROWS, THAT EQUALS TO 16 HOURS OF ACTIVITY. THEN IT SHOULD BE CLEAR HOW MUCH POWER IT USES.
    }
}
