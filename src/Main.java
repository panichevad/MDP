/**
 * Created by ZLO on 07.12.2016.
 */
import java.util.*;
import problemeDevant.*;

public class Main {
    public static void main(String[] args) {
        ProblemeDevant pr = new ProblemeDevant();
        ValueIteration valIter = new ValueIteration(pr);

        valIter.fairePlusieursIterations(100);
        valIter.qval.toStrings();
        Map<StateXYO, ActionOriente> politique = valIter.qval.getPolitique();
        StateXYO etatDepart = new StateXYO(3,0, 2);
        StateXYO etatArrivee;
        ActionOriente action;
        for (int i = 0; i < 50; i++) {
                action = politique.get(etatDepart);
                etatArrivee = pr.transition(etatDepart, action).tirage();
                etatDepart = etatArrivee;
                System.out.println("Politique " + action);

        }


    }

}
