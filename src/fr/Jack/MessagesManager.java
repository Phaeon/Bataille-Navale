package fr.Jack;

/**
 * On a la possibilité de récupérer différents messages dans cette classe.
 * 
 * @author jackhogg
 * @version 1.0
 */
public class MessagesManager {

	private BatailleNavale bataille;

	public MessagesManager(BatailleNavale bataille) {
		this.bataille = bataille;
	}

	/**
	 * Message lorsque le bâteau coule.
	 * @param B Bâteau qui a coulé
	 */
	public void bateauCoule(Bateau B) {
		this.bataille.getInfoBarAdversaire().setText("Le b\u00e2teau " + B.getName() + " a coul\u00e9 !");
	}

	/**
	 * Message lorsque le bâteau est touché.
	 * @param B Bâteau qui a été touché
	 */
	public void bateauTouche(Bateau B) {
		this.bataille.getInfoBarAdversaire().setText("Le b\u00e2teau " + B.getName() + " a \u00e9t\u00e9 touch\u00e9 !");
	}

	/**
	 * Message lorsqu'un tir a été raté.
	 */
	public void tirManque() {
		this.bataille.getInfoBarAdversaire().setText("Aucune cible de touch\u00e9e...");
	}

	/**
	 * Message lorsque l'adversaire coule le bâteau.
	 * @param B Bâteau qui a coulé
	 */
	public void bateauCouleParAdversaire(Bateau B) {
		this.bataille.getInfoBarJoueur().setText("Votre b\u00e2teau " + B.getName() + " a coul\u00e9 !");
	}

	/**
	 * Message lorsque l'adversaire a touché le bâteau.
	 * @param B Bâteau qui a été touché
	 */
	public void bateauToucheParAdversaire(Bateau B) {
		this.bataille.getInfoBarJoueur().setText("Votre b\u00e2teau " + B.getName() + " a \u00e9t\u00e9 touch\u00e9 !");
	}

	/**
	 * Message lorsque l'adversaire rate son tir.
	 */
	public void tirManqueParAdversaire() {
		this.bataille.getInfoBarJoueur().setText("Ouf, aucun d\u00e9g\u00e3t !");

	}
}
