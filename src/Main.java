/**
 * Created by ZLO on 07.12.2016.
 */
import problemeDevant.*;

public class Main {
    public static void main(String[] args) {
        ProblemeDevant pr = new ProblemeDevant();
        ValueIteration valIter = new ValueIteration(pr);
        //valIter.faireUneIteration();
        valIter.fairePlusieursIterations(100);
        valIter.qval.toStrings();
        //valIter.faireUneIteration();
        //valIter.qval.toStrings();
    }

}
