package modele;

/**
 * classe qui permet de d?finir un ?tat abstrait
 */
public abstract class AsbtractState {

	/**
	 * force la redefinition de hashcode
	 */
	public abstract int hashCode();

	/**
	 * force la redefinition de equals
	 */
	public abstract boolean equals(Object obj);

}
