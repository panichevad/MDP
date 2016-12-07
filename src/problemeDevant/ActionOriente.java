package problemeDevant;

import modele.AbstractAction;

/**
 * classe qui represente les actions du probleme Devant
 * <li>action gauche consistant ? changer son orientation
 * <li>action droite consistant ? changer son orientation
 * <li>action devant consistant a avancer
 *
 */
public class ActionOriente extends AbstractAction {

	/**
	 * les actions possibles d'un robot oriente ==> constantes de la classe
	 */
	public static ActionOriente GAUCHE = new ActionOriente("Gauche");
	public static ActionOriente DROITE = new ActionOriente("Droite");
	public static ActionOriente DEVANT = new ActionOriente("Devant");

	/**
	 * constructeur des actions orientees (en prive pour ?viter de creer
	 * n'importe quelle action ? l'exterieur)
	 * 
	 * @param s
	 *            nom de l'action
	 */
	private ActionOriente(String s) {
		super(s);
	}

	/**
	 * determine etat en fonction de l'action this (deterministe)
	 * 
	 * @param etat
	 *            etat de depart
	 * @param taille
	 *            taille du labyrinthe
	 * 
	 * @return l'etat obtenu lorsque l'action se d?roule une fois
	 */
	public StateXYO nouvelEtat(StateXYO etat, int taille) {
		StateXYO nouvelEtat = new StateXYO(etat);

		// si c'est gauche, on diminue orientation
		if (this == GAUCHE) {
			nouvelEtat.orientation = (nouvelEtat.orientation + 3) % 4;
			return nouvelEtat;
		}

		// si c'est droite, on augmente orientation
		if (this == DROITE) {
			nouvelEtat.orientation = (nouvelEtat.orientation + 1) % 4;
			return nouvelEtat;
		}

		// si c'est devant, on avance de 1
		if (this == DEVANT) {

			// en fonction de l'orientation
			switch (nouvelEtat.orientation) {

			// gere avancee nord
			case StateXYO.ORIENTE_NORD:
				nouvelEtat.y--;
				if (nouvelEtat.y < 0)
					nouvelEtat.y = 0;
				break;

			// gere avancee sud
			case StateXYO.ORIENTE_SUD:
				nouvelEtat.y++;
				if (nouvelEtat.y >= taille)
					nouvelEtat.y = taille - 1;
				break;

			// gere avancee ouest
			case StateXYO.ORIENTE_OUEST:
				nouvelEtat.x--;
				if (nouvelEtat.x < 0)
					nouvelEtat.x = 0;
				break;

			// gere avance est
			case StateXYO.ORIENTE_EST:
				nouvelEtat.x++;
				if (nouvelEtat.x >= taille)
					nouvelEtat.x = taille - 1;
				break;

			// par defaut
			default:
				throw new Error("" + nouvelEtat.orientation);
			}

			return (nouvelEtat);
		}

		// action inconnue
		throw new Error("action non reconnue" + this);
	}

}
