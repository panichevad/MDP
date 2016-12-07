package modele;

import java.util.List;

/**
 * construit un probleme MDP de maniere generique a partir de
 * <li>S la classe representant les etats
 * <li>A la classe representant les actions
 * 
 * <p>
 * classe abstraite dont il faut definir toutes les methodes
 * 
 * @param <S>
 *            classe representant les etats
 * @param <A>
 *            classe representant les actions
 */
public abstract class Probleme<S, A> {

	/**
	 * fonction de recompense
	 * 
	 * @param dep
	 *            etat de depart
	 * @param action
	 *            action suivie
	 * @param arriv
	 *            etat arrivee
	 * 
	 * @return la recompense lorsqu'on part de 'dep' et qu'on arrive dans
	 *         'arriv' en faisant 'action'
	 */
	public abstract double recompense(S dep, A action, S arriv);

	/**
	 * fonction de transition
	 * 
	 * @param dep
	 *            etat de depart
	 * @param action
	 *            action effectuee
	 * 
	 * @return distribution sur l'etat d'arrivee quand on part de 'dep' et qu'on
	 *         fait l'action 'action'
	 */
	public abstract Distribution<S> transition(S dep, A action);

	/**
	 * @return la liste des ?tats possibles du MDP
	 */
	public abstract List<S> allState();

	/**
	 * @return la liste des actions possibles du MDP
	 */
	public abstract List<A> allAction();

}
