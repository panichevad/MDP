package modele;

import java.util.ArrayList;

/**
 * represente une distribution sur des objets d'un ensemble E
 * 
 * 
 * @author vthomas
 *
 */
public class Distribution<E> {

	/**
	 * la repr?sentation de la distribution
	 * <li>elements repr?sente les valeurs de la distribution
	 * <li>probas les probabilit?s associ?es
	 * 
	 * <p>
	 * les probas sont associ?es ? l'element de meme indice
	 */
	public ArrayList<E> elements = new ArrayList<E>();
	public ArrayList<Double> probas = new ArrayList<Double>();

	/**
	 * ajoute un element sur la distribution
	 * 
	 * @param elem
	 *            element ? ajouter
	 * @param proba
	 *            probabilit? correspondante
	 */
	public void ajouter(E elem, double proba) {
		elements.add(elem);
		probas.add(proba);
	}

	/**
	 * echantillonner ==> effectuer un tirage sur la distribution
	 * 
	 * @return la valeur qui a ?t? ?chantillon?e selon la distribution
	 */
	public E tirage() {
		double somme = 0;
		double rd = Math.random();

		for (int i = 0; i < this.elements.size(); i++) {
			// on ajoute la valeur ? la somme
			somme = somme + this.probas.get(i);

			// si rd est plus petit
			if (rd < somme) {
				return (this.elements.get(i));
			}
		}

		throw new Error("probleme echantillonage");
	}

}
