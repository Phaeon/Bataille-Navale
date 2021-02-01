package fr.Jack.exceptions;

/**
 * Exception caractérisant l'impossibilité d'instancier un menu.
 * 
 * @author jackhogg
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MenuLaunchException extends Exception{
	
	private static String menu;
	
	public MenuLaunchException() {
		super("Le menu " + menu + " n'a pu être lanc\u00e9.");
	}
	
	/**
	 * Définit le nom du menu qu'on ne peut instancier.
	 * @param menu Menu
	 */
	public void setMenu(String menu) {
		MenuLaunchException.menu = menu;
	}

}
