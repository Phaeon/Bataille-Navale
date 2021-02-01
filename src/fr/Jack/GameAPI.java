package fr.Jack;

import java.util.ArrayList;
import java.util.HashMap;

import fr.Jack.bateaux.BateauContreTorpilleur;
import fr.Jack.bateaux.BateauCroiseur;
import fr.Jack.bateaux.BateauPorteAvion;
import fr.Jack.bateaux.BateauTorpilleur;
import fr.Jack.exceptions.BoatCreationException;
import fr.Jack.exceptions.BombInitialisationException;

/**
 * Cette classe permet de manipuler les données internes au jeu.
 * Grilles des joueurs, bombes et bâteaux des joueurs...
 * 
 * @author jackhogg
 * @version 1.0
 */
public class GameAPI {

	private int[][] grille, grilleAdv;
	private int joueur; // Joueur 1 : Utilisateur; Joueur 2 : ORDI
	private HashMap<Integer, Boolean> utilisables = new HashMap<Integer, Boolean>(),
			utilisablesAdv = new HashMap<Integer, Boolean>();
	private ArrayList<Bateau> bateaux = new ArrayList<Bateau>(), bateauxAdv = new ArrayList<Bateau>();

	public GameAPI() {
		this.grille = new int[10][10];
		this.grilleAdv = new int[10][10];
		this.joueur = 1;
		initialiserGrille();

		try {
			initialiserBateaux(this.bateaux, this.grille);
			initialiserBateaux(this.bateauxAdv, this.grilleAdv);
		} catch (BoatCreationException e) {
			e.getMessage();
			e.printStackTrace();
		}

		try {
			initialiserBombes();
		} catch (BombInitialisationException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * Permet d'effectuer un "tir" sur la grille adverse
	 * 
	 * @param x Coordonnée x
	 * @param y Coordonnée y
	 * @return La valeur d'une case suite au tir
	 */
	public int jouer(int x, int y) {
		if (getJoueur() == 1) {
			int aux = this.grilleAdv[x][y];
			envoieMissile(this.grilleAdv, x, y);
			return aux;
		} else {
			int aux = this.grille[x][y];
			envoieMissile(this.grille, x, y);
			return aux;
		}
	}

	/**
	 * Retourne le joueur actif.
	 * @return La valeur du joueur actif
	 */
	public int getJoueur() {
		return this.joueur;
	}

	/**
	 * Donne la main à un autre joueur.
	 * @param joueur Nouveau joueur actif
	 */
	public void setJoueur(int joueur) {
		this.joueur = joueur;
	}

	/**
	 * Retourne vraie en cas de victoire, lorsque plus aucun bâteau est à flot sur la grille adverse.
	 * 
	 * @param grille La grille adverse
	 * @return L'état du gain
	 */
	public boolean gagne(int[][] grille) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (grille[i][j] > 0)
					return false;
			}
		}

		return true;
	}

	/**
	 * Initialise les deux grilles
	 */
	public void initialiserGrille() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grille[i][j] = 0;
				grilleAdv[i][j] = 0;
			}
		}
	}

	/**
	 * Affiche une grille
	 * 
	 * @param grille Grille que l'on souhaite afficher
	 */
	public void afficherGrille(int[][] grille) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print(grille[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * Vérifie si un emplacement est disponible aux coordonnées (x,y) pour l'ajout d'un bâteai
	 * 
	 * @param grille Grille pour l'ajout du bâteau
	 * @param x Coordonnée x
	 * @param y Coordonnée y
	 * @param taille Taille du bâteau
	 * @param vertical Si le bâteau est vertical ou non
	 * @return Si le bâteau à la possibilité d'être placé
	 */
	public boolean emplacementDisponible(int[][] grille, int x, int y, int taille, boolean vertical) {
		if (vertical) {
			if (10 - x < taille)
				return false;
			for (int i = x; i < x + taille; i++) {
				if (!verifCase(grille, i, y))
					return false;
			}

		} else {
			if (10 - y < taille)
				return false;
			for (int j = y; j < y + taille; j++) {
				if (!verifCase(grille, x, j))
					return false;
			}
		}

		return true;
	}

	/**
	 * Vérifie si les alentours d'une case sont vides
	 * 
	 * @param grille Grille utilisée pour la vérification
	 * @param x Coordonnée x
	 * @param y Coordonnée y
	 * @return Si la case est bien solitaire et vide.
	 */
	public boolean verifCase(int[][] grille, int x, int y) {
		if (grille[x][y] != 0)
			return false;
		if (x - 1 >= 0) {
			if (grille[x - 1][y] != 0)
				return false;
		}
		
		if (x + 1 < 10) {
			if (grille[x + 1][y] != 0)
				return false;
		}
		if (y - 1 >= 0) {
			if (grille[x][y - 1] != 0)
				return false;
		}
		if (y + 1 < 10) {
			if (grille[x][y + 1] != 0)
				return false;
		}

		return true;
	}
	
	/**
	 * Recupère la grille du joueur.
	 * @return La grille du joueur.
	 */
	public int[][] getGrille() {
		return grille;
	}

	/**
	 * Redéfinit la grille du joueur par une nouvelle grille.
	 * @param grille Nouvelle grille
	 */
	public void setGrille(int[][] grille) {
		this.grille = grille;
	}

	/**
	 * Recupère la grille de l'adversaire.
	 * @return La grille de l'adversaire.
	 */
	public int[][] getGrilleAdv() {
		return grilleAdv;
	}

	/**
	 * Redéfinit la grille de l'adversaire par une nouvelle grille.
	 * @param grilleAdv Nouvelle grille
	 */
	public void setGrilleAdv(int[][] grilleAdv) {
		this.grilleAdv = grilleAdv;
	}

	/**
	 * Effectue un tir et modifie la valeur de la grille en fonction de la valeur précédente.
	 * 
	 * @param grille Grille du joueur
	 * @param x Coordonnée x
	 * @param y Coordonnée y
	 */
	public void envoieMissile(int[][] grille, int x, int y) {

		if (grille[x][y] == 0) {
			grille[x][y] = -2;
		} else if (grille[x][y] > 0) {
			grille[x][y] = -1;
		}

	}

	/**
	 * Initialise tous les bâteaux et les stocke dans une liste
	 * 
	 * @param bateaux Liste des bâteaux
	 * @param grille Grille où placer les bâteaux
	 * @throws BoatCreationException Exception lancée lors de l'échec de l'instanciation des bâteaux
	 */
	public void initialiserBateaux(ArrayList<Bateau> bateaux, int[][] grille) throws BoatCreationException {

		Bateau bt = new BateauTorpilleur("Torpilleur", 1);
		Bateau bct = new BateauContreTorpilleur("Contre-Torpilleur", 2);
		Bateau bct2 = new BateauContreTorpilleur("Contre-Torpilleur", 3);
		Bateau bc = new BateauCroiseur("Croiseur", 4);
		Bateau bpa = new BateauPorteAvion("Porte-Avion", 5);
		bt.placerBateau(this, grille);
		bct.placerBateau(this, grille);
		bct2.placerBateau(this, grille);
		bc.placerBateau(this, grille);
		bpa.placerBateau(this, grille);

		bateaux.add(bt);
		bateaux.add(bct);
		bateaux.add(bct2);
		bateaux.add(bc);
		bateaux.add(bpa);

	}

	/**
	 * Initialise les bombes du joueur et de l'adversaire.
	 * 
	 * @throws BombInitialisationException Exception levée lorsque la liste de bombes est indéfinie
	 */
	public void initialiserBombes() throws BombInitialisationException {
		if (this.utilisables != null) {
			utilisables.put(1, true); // Bombe Horizentale
			utilisables.put(2, true); // Bombe Verticale
			utilisables.put(3, true); // Bombe en croix

			utilisablesAdv.put(1, true); // Bombe Horizentale
			utilisablesAdv.put(2, true); // Bombe Verticale
			utilisablesAdv.put(3, true); // Bombe en croix
		} else
			throw new BombInitialisationException(this.getClass().getName());
	}

	/**
	 * Récupère les bombes du joueur.
	 * @return La liste associée des bombes du joueur
	 */
	public HashMap<Integer, Boolean> getUtilisables() {
		return utilisables;
	}

	/**
	 * Redéfinit la liste associée des bombes du joueur
	 * 
	 * @param utilisables Nouvelle liste associée de bombes
	 */
	public void setUtilisables(HashMap<Integer, Boolean> utilisables) {
		this.utilisables = utilisables;
	}

	/**
	 * Récupère les bombes de l'adversaire.
	 * @return La liste associée des bombes de l'adversaire
	 */
	public HashMap<Integer, Boolean> getUtilisablesAdv() {
		return utilisablesAdv;
	}

	/**
	 * Redéfinit la liste associée des bombes de l'adversaire
	 * 
	 * @param utilisablesAdv Nouvelle liste associée de bombes
	 */
	public void setUtilisablesAdv(HashMap<Integer, Boolean> utilisablesAdv) {
		this.utilisablesAdv = utilisablesAdv;
	}

	/**
	 * Récupère la liste des bâteaux du joueur.
	 * @return Liste de bâteaux
	 */
	public ArrayList<Bateau> getBateaux() {
		return this.bateaux;
	}

	/**
	 * Récupère la liste des bâteaux de l'adversaire.
	 * @return Liste de bâteaux
	 */
	public ArrayList<Bateau> getBateauxAdv() {
		return this.bateauxAdv;
	}

}
