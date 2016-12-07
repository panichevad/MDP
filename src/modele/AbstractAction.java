package modele;

/**
 * classe qui represente des actions abstraites caracteris?es par une chaine de
 * caractere
 *
 */
public class AbstractAction {

	/**
	 * nom (et identifiant) de l'action
	 */
	String action;

	/**
	 * constructeur
	 * 
	 * @param s
	 *            id de l'action
	 */
	public AbstractAction(String s) {
		this.action = s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAction other = (AbstractAction) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		return true;
	}

	/**
	 * affiche l'action
	 */
	public String toString() {
		return this.action;
	}

}
