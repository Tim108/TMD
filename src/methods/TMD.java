package methods;

import methods.knn.KNN;
import methods.randomforest.Forest;

/**
 * Created by Tim on 27/05/2016.
 */
public class TMD {

    public static void main(String[] args) {
        System.out.println("You need to call the static method testIt(String method)");
        testIt("randomforestl");
    }

    public static boolean testIt(String method) {
        KNN knn = new KNN();
        Forest forest = new Forest();
        if (method.equals("decisiontree"))
            forest.randomforest(1);
        else if (method.equals("randomforest"))
            forest.randomforest(5);
        else if (method.equals("knn"))
            knn.knn();
        else {
            System.out.println("Invalid input, the options are 'decisiontree', 'randomforest', 'knn'");
            return false;
        }
        return true;
    }
}
