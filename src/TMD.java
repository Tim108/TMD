import static methods.knn.MainRun.knn;
import static methods.randomforest.MainRun.randomforest;

/**
 * Created by Tim on 27/05/2016.
 */
public class TMD {

    public static void main(String[] args) {
        testIt("knn");
    }

    public static boolean testIt(String method) {
        if (method.equals("decisiontree"))
            randomforest(1);
        else if (method.equals("randomforest"))
            randomforest(5);
        else if (method.equals("knn"))
            knn();
        else {
            System.out.println("Invalid input, the options are 'decisiontree', 'randomforest', 'knn'");
            return false;
        }
        return true;
    }
}
