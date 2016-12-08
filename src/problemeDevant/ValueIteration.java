package problemeDevant;


import modele.Distribution;

/**
 * Created by ZLO on 07.12.2016.
 */
public class ValueIteration {
    private ProblemeDevant probleme;
    public QValeur qval;
    private double gamma;


    public ValueIteration(ProblemeDevant pr){
        probleme = pr;
        qval = new QValeur(probleme);
        gamma = 0.99;
    }

    public QValeur appliquerBellman(QValeur oldValue){
        QValeur newValue = new QValeur(probleme);

        for (int i = 0; i < probleme.allState().size(); i++) {

            for (int j = 0; j < probleme.allAction().size(); j++) {
                double qValCur = 0.0;
                StateXYO etatDep = probleme.allState().get(i);
                ActionOriente actionCur = probleme.allAction().get(j);
                Distribution<StateXYO> dist = probleme.transition(etatDep, actionCur);
                //System.out.println("action " + actionCur);
                //System.out.println("dist" + dist.elements.toString());

                for (int k = 0; k < dist.probas.size(); k++) {
                    StateXYO etatArr = dist.elements.get(k);
                    //System.out.println("Eata Depart "+ etatDep);
                    //System.out.println("Eata Arrivee "+ etatArr);

                    double probaTr = dist.probas.get(k);
                   // System.out.println("proba tr "+ probaTr);
                    double recompense = probleme.recompense(etatDep, actionCur, etatArr);
                    //System.out.println("recom" + recompense);
                    double qValPrec = oldValue.getValMax(etatArr);
                    //System.out.println("qvalprec " + qValPrec);

                    qValCur += probaTr*(recompense + gamma*qValPrec);
                    //System.out.println("qvalCur " + qValCur);
                }

                newValue.setVal(etatDep, actionCur, qValCur);
            }
        }
        return newValue;
    }

    public void faireUneIteration(){
        QValeur qvalNew = appliquerBellman(qval);
        qval = qvalNew;
    }

    public void fairePlusieursIterations(int n){
        for (int i = 0; i < n; i++) {
            faireUneIteration();
            System.out.println(qval.somme());
        }
    }
}
