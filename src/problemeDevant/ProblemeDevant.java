package problemeDevant;

import java.util.ArrayList;
import java.util.List;

import modele.Distribution;
import modele.Probleme;

/**
 * repr?sente un probleme d'un agent qui se d?place dans un labyrinthe et qui
 * peut avancer de deux cases quand il demande ? se d?placer
 * 
 * <li>les etats sont des objets de type StateXYO (x,y et orientation)
 * <li>les actions sont des objets de type ActionOriente (gauche, droite,
 * devant)
 * 
 * @author vthomas
 *
 */
public class ProblemeDevant extends Probleme<StateXYO, ActionOriente> {

	/**
	 * attribut pour la liste des etats possibles (creer dans allState)
	 */
	private ArrayList<StateXYO> ETATS=null;

	/**
	 * attribut pour la liste des actions possibles (creer dans allAction)
	 */
	private ArrayList<ActionOriente> ACTIONS=null;

	/**
	 * attribut taille du labyrinthe
	 */
	public int taille;

	/**
	 * trous du labyrinthe
	 */
	public boolean[][] trous;

	/**
	 * objectifs a atteindre et valeur associ?e
	 */
	public double[][] tresor;

	/**
	 * probabilite d'avancer deux fois
	 */
	public static double proba_AvancerDeuxFois = 0.5;

	/**
	 * construit le probleme par defaut
	 */
	public ProblemeDevant() {
		// cnstruit partie du probleme
		this(5);

		// les trous
		int[][] trous = { { 0, 0 }, { 2, 1 }, { 3, 1 }, { 2, 3 }, { 0, 4 } };
		for (int nb = 0; nb < trous.length; nb++)
			this.changerMur(trous[nb][0], trous[nb][1]);

		// les tresors
		this.mettreTresor(3, 0, 100);
		this.mettreTresor(3, 4, 50);
	}

	/**
	 * creation d'un probleme de laby sans trou ni tresor
	 * 
	 * @param n
	 *            taille du laby
	 */
	public ProblemeDevant(int n) {
		this.taille = n;
		this.trous = new boolean[n][n];
		this.tresor = new double[n][n];
	}

	/**
	 * ajoute un tresor au labyrinthe
	 * 
	 * @param x
	 *            position tresor
	 * @param y
	 *            position tresor
	 * @param valeur
	 *            valeur du tresor (recompense)
	 */
	public void mettreTresor(int x, int y, double valeur) {
		this.tresor[x][y] = valeur;
	}

	/**
	 * permet de changer la valeur d'un trou
	 * 
	 * @param x
	 *            position
	 * @param y
	 *            position
	 */
	public void changerMur(int x, int y) {
		this.trous[x][y] = !this.trous[x][y];
	}

	@Override
	/**
	 * matrice de transition
	 * <ul>
	 * <li>si on va a gauche ou a droite, resultat deterministe
	 * <li>si on avance, proba d'avancer deux fois
	 * </ul>
	 */
	public Distribution<StateXYO> transition(StateXYO pS, ActionOriente pAction) {

		// on recupere l'etat oriente
		StateXYO s = (StateXYO) pS;
		ActionOriente action = (ActionOriente) pAction;

		// si etat est (-1,-1) terminal ==> on reste dans terminal
		if (etreTerminal(s)) {
			Distribution<StateXYO> distrib = new Distribution<>();
			distrib.ajouter(new StateXYO(-1, -1, 0), 1);
			return (distrib);
		}

		// si on part d'un tresor ==> etat terminal
		if (this.tresor[s.x][s.y] != 0) {
			Distribution<StateXYO> distrib = new Distribution<StateXYO>();
			distrib.ajouter(new StateXYO(-1, -1, 0), 1);
			return (distrib);
		}

		// Calcul des deplacements etat normaux

		// si on tourne a droite ou a gauche, juste le resultat
		if ((action == ActionOriente.DROITE) || (action == ActionOriente.GAUCHE)) {
			Distribution<StateXYO> distrib = new Distribution<StateXYO>();
			distrib.ajouter(action.nouvelEtat(s, taille), 1.0);
			return (distrib);
		}

		// si on avance, ajouter deux etats
		if ((action == ActionOriente.DEVANT)) {
			Distribution<StateXYO> distrib = new Distribution<StateXYO>();

			// on avance une fois avec proba de 1-proba_avancer
			StateXYO avanceUneFois = action.nouvelEtat(s, taille);
			distrib.ajouter(avanceUneFois, 1.0 - proba_AvancerDeuxFois);

			// on avance deux fois avec proba de proba_avancer
			StateXYO avanceDeuxFois = action.nouvelEtat(avanceUneFois, taille);
			distrib.ajouter(avanceDeuxFois, proba_AvancerDeuxFois);

			// retourne distribution
			return (distrib);
		}

		throw new Error("probleme action" + action);

	}

	@Override
	public double recompense(StateXYO pDep, ActionOriente pAction, StateXYO pRes) {

		// cast pour recuperer type reel
		StateXYO dep = pDep;
		StateXYO res = pRes;
		ActionOriente action = pAction;

		// si etat de depart ou arrivee est terminal, rien ne se passe
		if (etreTerminal(dep))
			return (0);

		// si l'action est une rotation
		if ((action == ActionOriente.DROITE) || (action == ActionOriente.GAUCHE))
			return -1;

		// si on avance
		if (action == ActionOriente.DEVANT) {
			// regarde ce qui se passe case suivante
			StateXYO suivant = action.nouvelEtat(dep, taille);
			double valeur = -1;
			valeur += recompenseEtatArrivee(suivant);

			// si on a avance une fois
			if (res.equals(suivant)) {
				// on retourne valeur
				return (valeur);
			} else {
				// sinon on ajoute valeur apres deuxieme deplacement
				StateXYO deuxieme = action.nouvelEtat(suivant, taille);
				valeur += recompenseEtatArrivee(deuxieme);
				return (valeur);
			}
		}
		throw new Error("probleme");
	}

	/**
	 * donne une recompense en fonction de l'?tat d'arriv?e
	 * 
	 * @param suivant
	 * @return
	 */
	private double recompenseEtatArrivee(StateXYO suivant) {
		double valeur = 0;

		// si c'est un trou ? l'arrivee
		if (this.trous[suivant.x][suivant.y])
			valeur = -1000;

		// si c'est un tresor
		if (this.tresor[suivant.x][suivant.y] != 0)
			valeur = this.tresor[suivant.x][suivant.y];
		return valeur;
	}

	private boolean etreTerminal(StateXYO s) {
		return s.x == -1 && s.y == -1;
	}

	@Override
	/**
	 * retourne la liste des etats
	 */
	public ArrayList<StateXYO> allState() {
		// retourne la liste des etats selon singleton

		// si la liste n'a pas ete cree, on la cree
		if (this.ETATS == null)
			this.creerEtats();

		// on la retourne
		return this.ETATS;
	}

	/**
	 * methode qui cree la liste d'etat une fois pour toute
	 */
	void creerEtats() {
		ArrayList<StateXYO> etats = new ArrayList<StateXYO>();

		// construit tous les etats de la carte
		for (int x = 0; x < this.taille; x++)
			for (int y = 0; y < this.taille; y++)
				// pour chaque orientation
				for (int or = 0; or < 4; or++)
					etats.add(new StateXYO(x, y, or));

		// etats finaux
		etats.add(new StateXYO(-1, -1, 0));
		etats.add(new StateXYO(-1, -1, 1));
		etats.add(new StateXYO(-1, -1, 2));
		etats.add(new StateXYO(-1, -1, 3));

		// on la stocke pour ne pas le recreer
		this.ETATS = etats;
	}

	@Override
	/**
	 * retourne la liste d'actions
	 * <ul>
	 * <li>avancer
	 * <li>tourner a gauche
	 * <li>tourner a droite
	 * </ul>
	 */
	public List<ActionOriente> allAction() {
		if (this.ACTIONS == null)
			this.creerActions();
		return this.ACTIONS;

	}

	/**
	 * pour ne creer la liste d'actions qu'une fois
	 */
	private void creerActions() {
		ArrayList<ActionOriente> actions = new ArrayList<ActionOriente>();
		actions.add(ActionOriente.DEVANT);
		actions.add(ActionOriente.DROITE);
		actions.add(ActionOriente.GAUCHE);
		this.ACTIONS = actions;
	}

}
