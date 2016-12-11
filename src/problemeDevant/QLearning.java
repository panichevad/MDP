package problemeDevant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * Created by ZLO on 08.12.2016.
 */

public class QLearning {
    private ProblemeDevant pr;
    private StateXYO etatCur;
    private Map<StateXYO,Map<ActionOriente,Integer>> t;
    public QValeur qval;
    private double gamma;

    public QLearning(ProblemeDevant probleme){
        this.pr = probleme;
        this.qval = new QValeur(probleme);
        this.etatCur = new StateXYO(0,3,2);
        this.t = new HashMap<StateXYO,Map<ActionOriente,Integer>>();
        this.gamma = 0.99;
    }

    private void updateExperience(StateXYO etatDep, ActionOriente action, StateXYO etatAr, double
            recompense){
        int tValue  = 0;
        //System.out.println("t value before: " + tValue);
        if (this.t.get(etatDep) == null || this.t.get(etatDep).get(action) == null) {
            tValue = 1;
        }
        else{
            tValue = this.t.get(etatDep).get(action);
            tValue += 1;
        }
        System.out.println("t value after:" +tValue);

        Map<ActionOriente, Integer> actionT = new HashMap<ActionOriente, Integer>();
        actionT.put(action, tValue);
        System.out.println("etat dep" + etatDep);
        System.out.println("action" + action);
        System.out.println("etat arr" + etatAr);

        this.t.put(etatDep, actionT);// mettre a jour t
        double qVal = this.qval.getVal(etatDep,action);
        double qValEval = this.qval.getValMax(etatAr);
        qVal += (1/tValue)*(recompense + this.gamma*qValEval - qVal);
        System.out.println("qval " +qVal);
        this.qval.setVal(etatDep, action, qVal);
    }

    private ActionOriente choisirAction(){
        double epsilon = 0.2;
        double rand = Math.random();
        ActionOriente actionChoisie = null;
        if(rand > epsilon){
            actionChoisie = this.qval.getActionMax(this.etatCur);
        }
        else{
            int index = new Random().nextInt(3);
            actionChoisie = this.pr.allAction().get(index);
            //System.out.println("action chosen: " + actionChoisie);
        }
        return actionChoisie;
    }

    private void effectuerUneIteration(){
        ActionOriente action = choisirAction();
        StateXYO etatArr = this.pr.transition(this.etatCur, action).tirage();
        double recompense = this.pr.recompense(this.etatCur, action, etatArr);
        updateExperience(this.etatCur, action, etatArr, recompense);
        this.etatCur = etatArr;
    }

    private void replacerAleatoirement(){
        int index = new Random().nextInt(this.pr.allState().size());
        //System.out.println("index: " + index);
        //System.out.println("check " + this.pr.allState().get(96));
        this.etatCur = this.pr.allState().get(index);
        //System.out.println("State after change: " + etatCur);
        //this.etatCur = etatNouveau;

    }

    public void apprendre(long n) {
        StateXYO etatTerm = new StateXYO(-1, -1, 0);
        for (int i = 0; i < n; i++) {
            if (this.etatCur.equals(etatTerm)) {
                replacerAleatoirement();
            }
            effectuerUneIteration();
            //System.out.println("Sum is " + this.qval.somme());
        }
    }

}
