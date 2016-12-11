package problemeDevant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZLO on 06.12.2016.
 */
public class QValeur {
    private Map<StateXYO, Map<ActionOriente, Double>> qValeur;

    /**
     * consturcteur
     * @param problem
     */
    public QValeur(ProblemeDevant problem){
        this.qValeur = new HashMap<StateXYO, Map<ActionOriente, Double>>();
        for (int i = 0; i < problem.allState().size(); i++) {
            Map<ActionOriente, Double> qValeurAction = new HashMap<ActionOriente, Double>();

            for (int j = 0; j < problem.allAction().size(); j++) {
                qValeurAction.put(problem.allAction().get(j), 0.0);

            }
            this.qValeur.put(problem.allState().get(i), qValeurAction);
        }
    }

    /**
     * acceder a la valeur associee a l'etat et l'action
     * @param etat
     * @param action
     * @return valeur
     */
    public double getVal(StateXYO etat, ActionOriente action){

        return this.qValeur.get(etat).get(action);
    }

    /**
     * modifier la valeur pour l'etat et l'action donnes
     * @param etat
     * @param action
     * @param nValeur
     */
    public void setVal(StateXYO etat, ActionOriente action, double nValeur){
        Map<ActionOriente, Double> qValeurAction = new HashMap<ActionOriente, Double>();
        qValeurAction = this.qValeur.get(etat);
        qValeurAction.put(action, nValeur);
    }

    /**
     * retourner la meilleure action pour etat
     * @param etat
     * @return
     */
    public ActionOriente getActionMax(StateXYO etat){
        double valeurMax = -100000;


        ActionOriente meilleureAction = null;
        Map<ActionOriente, Double> qValeurAction;
        qValeurAction = this.qValeur.get(etat);
        for (Map.Entry<ActionOriente, Double> entry: qValeurAction.entrySet()) {
           //System.out.println(entry.getValue().toString());
            //System.out.println(entry.getKey().toString());
            if(entry.getValue() >= valeurMax){

                valeurMax = entry.getValue();
                meilleureAction = entry.getKey();
            }
        }
        //System.out.println(meilleureAction);
        return meilleureAction;
    }

    /**
     * retourner la valeur maximale pour l'etat
     * @param etat
     * @return
     */
    public double getValMax(StateXYO etat){
        double valeurMax = -100000;
        Map<ActionOriente, Double> qValeurAction;
        qValeurAction = this.qValeur.get(etat);
        for (Map.Entry<ActionOriente, Double> entry: qValeurAction.entrySet()) {
            if(entry.getValue() > valeurMax){
                valeurMax = entry.getValue();
            }
        }
        return valeurMax;
    }

    public void toStrings(){
        System.out.println("|State  \t |Forward \t |Right \t |Left   \t |Best action");

        for (Map.Entry<StateXYO, Map<ActionOriente, Double>> entry: this.qValeur.entrySet()) {
            StateXYO etat = entry.getKey();
            ActionOriente meilleureAction = getActionMax(etat);
            double valDevant = getVal(etat, ActionOriente.DEVANT);
            double valDroit = getVal(etat, ActionOriente.DROITE);
            double valGauche = getVal(etat, ActionOriente.GAUCHE);

            System.out.println("|" + etat.toString() + "\t |" + valDevant + "\t |" + valDroit
                    +"\t |" + valGauche + "\t |" + meilleureAction);
        }
    }

    public double somme(){
        double somme  = 0.0;
        for (Map.Entry<StateXYO, Map<ActionOriente, Double>> entry: this.qValeur.entrySet()) {
            StateXYO etat = entry.getKey();

            double valDevant = getVal(etat, ActionOriente.DEVANT);
            double valDroit = getVal(etat, ActionOriente.DROITE);
            double valGauche = getVal(etat, ActionOriente.GAUCHE);
            somme = somme + valDevant + valDroit + valGauche;
        }
        return somme;
    }

    public Map<StateXYO,ActionOriente> getPolitique(){
        Map<StateXYO,ActionOriente> politiqueOptimale = new HashMap<StateXYO,ActionOriente>();
        for (Map.Entry<StateXYO, Map<ActionOriente, Double>> entry: qValeur.entrySet()) {
            StateXYO etat = entry.getKey();
            ActionOriente meilleureAction = getActionMax(etat);
            politiqueOptimale.put(etat, meilleureAction);
        }
        return politiqueOptimale;
    }

}

