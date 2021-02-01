package fr.Jack.exceptions;

/**
 * Exception caractérisant l'impossibilité d'initialiser les bombes des joueurs.
 * 
 * @author jackhogg
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BombInitialisationException extends Exception {
	
	public BombInitialisationException(String classe) {
		super("Impossible d'initialiser les bombes dans la classe " + classe + ".");
	}

}
