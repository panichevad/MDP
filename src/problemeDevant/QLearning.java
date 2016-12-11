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
        this.gamma = 0.99;

        this.t = new HashMap<StateXYO,Map<ActionOriente,Integer>>();
        for (int i = 0; i < probleme.allState().size(); i++) {
            Map<ActionOriente, Integer> qValeurAction = new HashMap<ActionOriente, Integer>();
            for (int j = 0; j < probleme.allAction().size(); j++) {
                qValeurAction.put(probleme.allAction().get(j), 0);
            }
            this.t.put(probleme.allState().get(i), qValeurAction);
        }
    }

    private void updateExperience(StateXYO etatDep, ActionOriente action, StateXYO etatAr, double
            recompense){
        int tValue = this.t.get(etatDep).get(action) + 1;
        Map<ActionOriente, Integer> actionT = this.t.get(etatDep);
        actionT.put(action, tValue);
        double qVal = this.qval.getVal(etatDep,action);
        double qValEval = this.qval.getValMax(etatAr);
        double alpha = (1/(double)tValue);
        System.out.println("aplpha " + alpha);
        qVal += alpha*(recompense + this.gamma*qValEval - qVal) ;
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
        }
        return actionChoisie;
    }

    private void effectuerUneIteration(){
        ActionOriente action = choisirAction();
        StateXYO etatArr = this.pr.transition(this.etatCur, action).tirage();
        double recompense = this.pr.recompense(this.etatCur, action, etatArr);
        System.out.println("recomp " + recompense);
        updateExperience(this.etatCur, action, etatArr, recompense);
        this.etatCur = etatArr;
    }

    private void replacerAleatoirement(){
        int index = new Random().nextInt(this.pr.allState().size());
        this.etatCur = this.pr.allState().get(index);
    }

    public void apprendre(long n) {
        for (int i = 0; i < n; i++) {
            if (this.etatCur.x == -1 && this.etatCur.y == -1) {
                replacerAleatoirement();
            }
            effectuerUneIteration();
            System.out.println("Sum is " + this.qval.somme());
        }
    }

}
