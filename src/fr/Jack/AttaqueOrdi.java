package fr.Jack;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import fr.Jack.animations.Animation;
import fr.Jack.animations.AnimationFinIA;

/**
 * La classe permet de créer un bouton, pour l'ajouter dans la grille de
 * l'adversaire. On récupère les coordonnées (x,y) pour pouvoir modifier le
 * bouton en fonction de l'impact réalisé par l'ordinateur lors de son attaque.
 * 
 * @author jackhogg
 * @version 1.0
 */
public class AttaqueOrdi {

	private GameAPI game;
	private BatailleNavale bataille;

	public AttaqueOrdi(GameAPI game, BatailleNavale bataille) {
		this.game = game;
		this.bataille = bataille;
		Song S = new Song();
		ImageIcon icon = new ImageIcon("explo.png", "");
		MessagesManager mess = new MessagesManager(bataille);
		double r = 0;
		int id = 0, x, y;

		/*
		 * Choix aléatoire de la bombe Si l'id est à 0, c'est une bombe classique,
		 * Sinon, il faut vérifier que la bombe est utilisable.
		 */
		do {
			r = Math.random();
			id = 0;
			if (r >= 0.45 - (bataille.getMode() * 0.05) && r < 0.60 - (bataille.getMode() * 0.05))
				id = 1;
			else if (r >= 0.60 - (bataille.getMode() * 0.05) && r < 0.75 - (bataille.getMode() * 0.05))
				id = 2;
			else if (r >= 0.75 - (bataille.getMode() * 0.05))
				id = 3;
		} while ((id >= 1 && game.getUtilisablesAdv().get(id) == false));

		// Choix des coordonnées en mode facile
		if (bataille.getMode() == 0) {
			do {
				x = (int) Math.floor(Math.random() * 10);
				y = (int) Math.floor(Math.random() * 10);
			} while (game.getGrille()[x][y] == -1 || game.getGrille()[x][y] == -2);
		} else {
			// Choix des coordonnées en mode moyen ou difficile
			if (bataille.getCases().size() == 0) {

				do {
					x = (int) Math.floor(Math.random() * 10);
					y = (int) Math.floor(Math.random() * 10);
				} while ((x + y) % plusPetitBateau() != 0 || game.getGrille()[x][y] == -1
						|| game.getGrille()[x][y] == -2);

			} else {
				Random r2 = new Random();
				int ele;

				ele = r2.nextInt(bataille.getCases().size());
				x = bataille.getCases().get(ele)[0];
				y = bataille.getCases().get(ele)[1];

				bataille.getCases().remove(ele);
			}
		}

		// Par défaut, le tir est manqué
		mess.tirManqueParAdversaire();

		/*
		 * Le code reconnait la bombe utilisée, Si le joueur touche un navire, on
		 * vérifie si celui-ci a coulé ou pas.
		 */
		if (id == 0) { //Bombe classique
			int joue = game.jouer(x, y);
			if (joue == 0) {
				bataille.getButtons()[x][y].setBackground(Color.RED);
			} else if (joue > 0) {
				bataille.getButtons()[x][y].setBackground(Color.GREEN);
				bataille.getButtons()[x][y].setIcon(icon);

				getBateau(joue).perdVie();

				if (bataille.getMode() > 0) { // Ajout des cases adjacentes en cas de difficulté choisie
					ajouterAdjacentsX(bataille.getMode(), game.getGrille(), x, y);
					ajouterAdjacentsY(bataille.getMode(), game.getGrille(), x, y);
				}

				// Lance une animation en cas de bâteau coulé
				if (getBateau(joue).aCoule()) {
					mess.bateauCouleParAdversaire(getBateau(joue));
					Thread anim = new Animation(bataille, getBateau(joue), 2);
					anim.start();
				} else {
					mess.bateauToucheParAdversaire(getBateau(joue));

					try {
						S.playDrown(); // Son d'une bombe
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						e.printStackTrace();
					}

					try {
						S.playHit(); // Son d'une bombe
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						e.printStackTrace();
					}
				}
			}
		} else if (id == 1) { //Bombe horizontale

			if (game.getUtilisablesAdv().get(1)) { //Vérifie si la bombe est disponible
				for (int i = 0; i < 10; i++) {
					int joue = game.jouer(x, i);
					if (joue == 0) {
						bataille.getButtons()[x][i].setBackground(Color.RED);
					} else if (joue > 0) {
						bataille.getButtons()[x][i].setBackground(Color.GREEN);
						bataille.getButtons()[x][i].setIcon(icon);

						getBateau(joue).perdVie();

						if (bataille.getMode() > 0) // Ajout des cases adjacentes en cas de difficulté choisie
							ajouterAdjacentsX(bataille.getMode(), game.getGrille(), x, i);

						// Lance une animation en cas de bâteau coulé
						if (getBateau(joue).aCoule()) {
							mess.bateauCouleParAdversaire(getBateau(joue));

							try {
								S.playDrown(); // Son d'une bombe
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
								e.printStackTrace();
							}

							Thread anim = new Animation(bataille, getBateau(joue), 2);
							anim.start();
						} else {
							mess.bateauToucheParAdversaire(getBateau(joue));
							try {
								S.playHit(); // Son d'une bombe
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
								e.printStackTrace();
							}
						}
					}
				}
				game.getUtilisablesAdv().put(1, false);
			} else
				bataille.getInfoBar().setText("La bombe a d\u00e9j\u00e0 \u00e9t\u00e9 utilis\u00e9e !");
		} else if (id == 2) { // Bombe verticale

			if (game.getUtilisablesAdv().get(2)) { //Vérifie si la bombe est disponible
				for (int i = 0; i < 10; i++) {
					int joue = game.jouer(i, y);
					if (joue == 0) {
						bataille.getButtons()[i][y].setBackground(Color.RED);
					} else if (joue > 0) {
						bataille.getButtons()[i][y].setBackground(Color.GREEN);
						bataille.getButtons()[i][y].setIcon(icon);

						getBateau(joue).perdVie();

						if (bataille.getMode() > 0) // Ajout des cases adjacentes en cas de difficulté choisie
							ajouterAdjacentsY(bataille.getMode(), game.getGrille(), i, y);

						// Lance une animation en cas de bâteau coulé
						if (getBateau(joue).aCoule()) {
							mess.bateauCouleParAdversaire(getBateau(joue));

							try {
								S.playDrown(); // Son d'une bombe
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
								e.printStackTrace();
							}

							Thread anim = new Animation(bataille, getBateau(joue), 2);
							anim.start();
						} else {
							mess.bateauToucheParAdversaire(getBateau(joue));
							try {
								S.playHit(); // Son d'une bombe
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
								e.printStackTrace();
							}
						}
					}
				}
				game.getUtilisablesAdv().put(2, false);
			} else
				bataille.getInfoBar().setText("La bombe a d\u00e9j\u00e0 \u00e9t\u00e9 utilis\u00e9e !");
		} else if (id == 3) { // Bombe en croix

			if (game.getUtilisablesAdv().get(3)) { //Vérifie si la bombe est utilisable
				int headX = ((x - 1) >= 0) ? x - 1 : 0;
				int tailX = ((x + 1) < 10) ? x + 1 : 9;
				int headY = ((y - 1) >= 0) ? y - 1 : 0;
				int tailY = ((y + 1) < 10) ? y + 1 : 9;

				for (int i = headY; i <= tailY; i++) {
					int joue = game.jouer(x, i);
					if (joue == 0) {
						bataille.getButtons()[x][i].setBackground(Color.RED);
					} else if (joue > 0) {
						bataille.getButtons()[x][i].setBackground(Color.GREEN);
						bataille.getButtons()[x][i].setIcon(icon);

						getBateau(joue).perdVie();

						if (bataille.getMode() > 0) // Ajout des cases adjacentes en cas de difficulté choisie
							ajouterAdjacentsX(bataille.getMode(), game.getGrille(), x, i);

						// Lance une animation en cas de bâteau coulé
						if (getBateau(joue).aCoule()) {
							mess.bateauCouleParAdversaire(getBateau(joue));

							try {
								S.playDrown(); // Son d'une bombe
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
								e.printStackTrace();
							}

							Thread anim = new Animation(bataille, getBateau(joue), 2);
							anim.start();
						} else {
							mess.bateauToucheParAdversaire(getBateau(joue));
							try {
								S.playHit(); // Son d'une bombe
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
								e.printStackTrace();
							}
						}
					}
				}

				for (int i = headX; i <= tailX; i++) {
					int joue = game.jouer(i, y);
					if (joue == 0) {
						bataille.getButtons()[i][y].setBackground(Color.RED);
					} else if (joue > 0) {
						bataille.getButtons()[i][y].setBackground(Color.GREEN);
						bataille.getButtons()[i][y].setIcon(icon);

						getBateau(joue).perdVie();

						// Lance une animation en cas de bâteau coulé
						if (bataille.getMode() > 0)
							ajouterAdjacentsY(bataille.getMode(), game.getGrille(), i, y);

						if (getBateau(joue).aCoule()) {
							mess.bateauCouleParAdversaire(getBateau(joue));

							try {
								S.playDrown(); // Son d'une bombe
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
								e.printStackTrace();
							}

							Thread anim = new Animation(bataille, getBateau(joue), 2);
							anim.start();
						} else {
							try {
								S.playHit(); // Son d'une bombe
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
								e.printStackTrace();
							}
							mess.bateauToucheParAdversaire(getBateau(joue));
						}
					}
				}
				game.getUtilisablesAdv().put(3, false);
			} else
				bataille.getInfoBar().setText("La bombe a d\u00e9j\u00e0 \u00e9t\u00e9 utilis\u00e9e !");
		}

		// Vérification du gain
		if (game.gagne(game.getGrille())) {
			bataille.getGameTimer().setEnd();
			try {
				S.playDefeat(); // Son d'une bombe
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}

			// Animation en cas de gain
			Thread anim = new AnimationFinIA(bataille, game, bataille.getButtons());
			Thread anim2 = new AnimationFinIA(bataille, game, bataille.getButtonsAdv());
			anim.start();
			anim2.start();
		} else
			game.setJoueur(1);
	}

	/**
	 * Recupère un bâteau à partir de son ID, dans le cas où ce bâteau existe
	 * 
	 * @param id L'ID que l'on souhaite récupérer
	 * @return Un bâteau associé à un ID
	 */
	public Bateau getBateau(int id) {
		for (Bateau B : game.getBateaux()) {
			if (B.getID() == id)
				return B;
		}

		return null;
	}

	/**
	 * Ajoute les cases adjacentes au-dessus et en-dessous d'une case donnée dans
	 * une liste.
	 * 
	 * @param mode   Difficulté du jeu
	 * @param grille Grille du joueur
	 * @param x      Coordonnée x
	 * @param y      Coordonnée y
	 */
	public void ajouterAdjacentsX(int mode, int[][] grille, int x, int y) {
		int[] casesAdj;

		if (x - 1 >= 0 && grille[x - 1][y] >= mode - 1) {
			casesAdj = new int[2];
			casesAdj[0] = x - 1;
			casesAdj[1] = y;
			bataille.getCases().add(casesAdj);
		}

		if (x + 1 < 10 && grille[x + 1][y] >= mode - 1) {
			casesAdj = new int[2];
			casesAdj[0] = x + 1;
			casesAdj[1] = y;
			bataille.getCases().add(casesAdj);
		}
	}

	/**
	 * Ajoute les cases adjacentes à hauche et à droite d'une case donnée dans une
	 * liste.
	 * 
	 * @param mode   Difficulté du jeu
	 * @param grille Grille du joueur
	 * @param x      Coordonnée x
	 * @param y      Coordonnée y
	 */
	public void ajouterAdjacentsY(int mode, int[][] grille, int x, int y) {
		int[] casesAdj;
		if (y - 1 >= 0 && grille[x][y - 1] >= mode - 1) {
			casesAdj = new int[2];
			casesAdj[0] = x;
			casesAdj[1] = y - 1;
			bataille.getCases().add(casesAdj);
		}

		if (y + 1 < 10 && grille[x][y + 1] >= mode - 1) {
			casesAdj = new int[2];
			casesAdj[0] = x;
			casesAdj[1] = y + 1;
			bataille.getCases().add(casesAdj);
		}
	}

	/**
	 * Cherche la taille du plus petit bâteau
	 * 
	 * @return Taille du plus petit bâteau encore à flôt
	 */
	public int plusPetitBateau() {
		int taille = getBateau(1).getTaille();

		for (int i = 0; i < game.getBateauxAdv().size(); i++) {
			if (getBateau(i + 1).getTaille() <= taille && !getBateau(i + 1).aCoule())
				taille = getBateau(i + 1).getTaille();
		}

		return taille;
	}

}
