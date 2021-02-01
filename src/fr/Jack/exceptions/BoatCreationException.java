package fr.Jack.exceptions;

/**
 * Exception caractérisant l'impossibilité d'instancier les bâteaux.
 * 
 * @author jackhogg
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BoatCreationException extends Exception {
	
	public BoatCreationException() {
		super("Les b\u00e2teaux n'ont pu être cr\u00e9\u00e9 (Class GameAPI).");
	}

}
