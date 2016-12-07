package problemeDevant;

import modele.AsbtractState;

/**
 * classe qui represent l'etat d'un robot oriente. Un etat caracterise par
 * <li>posX
 * <li>posY
 * <li>orientation
 * 
 * @author vthomas
 *
 */
public class StateXYO extends AsbtractState {

	/**
	 * les constantes
	 */
	public final static int ORIENTE_NORD = 0;
	public final static int ORIENTE_EST = 1;
	public final static int ORIENTE_SUD = 2;
	public final static int ORIENTE_OUEST = 3;

	/**
	 * position
	 */
	public int x;
	public int y;

	/**
	 * orientation
	 */
	int orientation;

	/**
	 * construit un etat
	 * 
	 * @param x
	 *            position en x
	 * @param y
	 *            posituon en y
	 * @param or
	 *            orientation (cf constantes)
	 */
	public StateXYO(int x, int y, int or) {
		this.x = x;
		this.y = y;
		this.orientation = or;
		if ((or < 0) || (or > 3))
			throw new Error("probleme orientation " + or);
	}

	/**
	 * constructeur par copie
	 * 
	 * @param etat
	 *            etat courant
	 */
	public StateXYO(StateXYO etat) {
		this.x = etat.x;
		this.y = etat.y;
		this.orientation = etat.orientation;
	}

	@Override
	// fonction de hashcode
	public int hashCode() {
		int PRIME = 31;
		return x + PRIME * y + PRIME * PRIME * orientation;
	}

	@Override
	// deux etats sont egaux si x, y et orientation
	public boolean equals(Object obj) {
		StateXYO st = (StateXYO) obj;
		// les etats sont egaux si tout est ?gal
		return (st.x == this.x && st.y == this.y && st.orientation == this.orientation);
	}

	@Override
	//affiche l'etat
	public String toString() {
		String or="";
		switch(this.orientation)
		{
		case ORIENTE_EST:
			or="E";
			break;
		case ORIENTE_NORD:
			or="N";
			break;
		case ORIENTE_OUEST:
			or="O";
			break;
		case ORIENTE_SUD:
			or="S";
			break;
		}
		
		return ("(" + this.x + "," + this.y + ":" + or + ")");
	}
}
