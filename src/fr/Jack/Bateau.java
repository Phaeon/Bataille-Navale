package fr.Jack;

import java.util.Random;

/**
 * Classe abstraite caractérisant un bâteau.
 * 
 * @author jackhogg
 * @version 1.0
 */
public abstract class Bateau {

	protected boolean vertical;
	protected int ID, vies, x, y;
	protected String name;
	

	public Bateau(String name, int ID) {
		this.name = name;
		this.ID = ID;
		this.vies = this.getTaille();
		boolean vertical = (Math.random() >= 0.5) ? true : false;
		setVertical(vertical);
	}

	/**
	 * Permet de placer un bâteau sur une grille, sur un emplacement disponibile
	 * 
	 * @param game Jeu (interne)
	 * @param grille Grille où l'on place le bâteau
	 */
	public void placerBateau(GameAPI game, int[][] grille) {
		Random rand = new Random();
		int x, y;

		do {
			x = rand.nextInt(10);
			y = rand.nextInt(10);
		} while (!game.emplacementDisponible(grille, x, y, this.getTaille(), isVertical()));

		this.x = x;
		this.y = y;
		
		if (isVertical()) {
			for (int i = x; i < x + this.getTaille(); i++) {
				grille[i][y] = this.getID();
			}
		} else {
			for (int i = y; i < y + this.getTaille(); i++) {
				grille[x][i] = this.getID();
			}
		}
	}

	/**
	 * Récupère la taille du bâteau
	 * @return Taille du bâteau
	 */
	public abstract Integer getTaille();

	/**
	 * Récupère l'ID du bâteau.
	 * @return ID du bâteau
	 */
	public abstract Integer getID();

	/**
	 * Retourne si le bâteau a coulé.
	 * @return Etat du bâteau
	 */
	public abstract Boolean aCoule();

	/**
	 * Récupère le nombre de cases restantes au bâteau.
	 * @return Vies d'un bâteau
	 */
	public abstract Integer getVies();

	/**
	 * Retourne si le bâteau est vertical ou non.
	 * @return Etat du bâteau.
	 */
	public abstract Boolean isVertical();

	/**
	 * Récupère le nom du bâteau
	 * @return Nom du bâteau
	 */
	public abstract String getName();
	
	public Integer getX() {
		return this.x;
	}
	
	public Integer getY() {
		return this.y;
	}

	/**
	 * Enlève une vie au bâteau.
	 */
	public void perdVie() {
		this.vies--;
	}
	
	/**
	 * Redéfinir les vies d'un bâteau.
	 * @param vies Nouvelles vies du bâteau
	 */
	public void setVies(int vies) {
		this.vies = vies;
	}

	/**
	 * Définie si le bâteau est verticale
	 * @param vertical Bâteau verticale ou non
	 */
	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

}
