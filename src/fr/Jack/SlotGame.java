package fr.Jack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import fr.Jack.animations.Animation;
import fr.Jack.animations.AnimationFinJoueur;

/**
 * La classe permet de créer un bouton, pour l'ajouter dans la grille du joueur.
 * On récupère les coordonnées (x,y) pour pouvoir modifier le bouton en fonction
 * de l'impact réalisé par le joueur lors du clique.
 * 
 * @author jackhogg
 * @version 1.0
 */
public class SlotGame {

	private JButton button;
	private GameAPI game;
	private BatailleNavale bataille;

	public SlotGame(BatailleNavale b, GameAPI game, int x, int y) {
		this.game = game;
		this.button = new JButton();
		this.bataille = b;
		MessagesManager mess = new MessagesManager(b);
		Song S = new Song();
		ImageIcon icon = new ImageIcon("explo.png", "");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				if (game.getJoueur() == 1) {
					if (bataille.getBombType() == 0) {
						mess.tirManque();
						int joue = game.jouer(x, y);
						if (joue == 0) {
							button.setEnabled(false);
							button.setBackground(Color.RED);
						} else if (joue > 0) {
							button.setEnabled(false);
							button.setBackground(Color.GREEN);
							button.setIcon(icon);
							getBateau(joue).perdVie();

							if (getBateau(joue).aCoule()) {
								mess.bateauCoule(getBateau(joue));

								try {
									S.playDrown();
								} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
									e.printStackTrace();
								}

								Thread anim = new Animation(bataille, getBateau(joue), 1);
								anim.start();
							} else {
								mess.bateauTouche(getBateau(joue));

								try {
									S.playHit();
								} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
									e.printStackTrace();
								}
							}
						}
					} else if (bataille.getBombType() == 1) {

						if (game.getUtilisables().get(1)) {
							mess.tirManque();
							for (int i = 0; i < 10; i++) {
								int joue = game.jouer(x, i);
								if (joue == 0) {
									bataille.getButtonsAdv()[x][i].setEnabled(false);
									bataille.getButtonsAdv()[x][i].setBackground(Color.RED);
								} else if (joue > 0) {
									bataille.getButtonsAdv()[x][i].setEnabled(false);
									bataille.getButtonsAdv()[x][i].setBackground(Color.GREEN);
									bataille.getButtonsAdv()[x][i].setIcon(icon);
									getBateau(joue).perdVie();

									if (getBateau(joue).aCoule()) {
										mess.bateauCoule(getBateau(joue));

										try {
											S.playDrown();
										} catch (UnsupportedAudioFileException | IOException
												| LineUnavailableException e) {
											e.printStackTrace();
										}

										Thread anim = new Animation(bataille, getBateau(joue), 1);
										anim.start();
									} else {
										mess.bateauTouche(getBateau(joue));

										try {
											S.playHit();
										} catch (UnsupportedAudioFileException | IOException
												| LineUnavailableException e) {
											e.printStackTrace();
										}
									}
								}
							}
							game.getUtilisables().put(1, false);
						} else {
							bataille.getInfoBar().setText("La bombe a d\u00e9j\u00e0 \u00e9t\u00e9 utilis\u00e9e !");
							return;
						}
					} else if (bataille.getBombType() == 2) {

						if (game.getUtilisables().get(2)) {
							mess.tirManque();
							for (int i = 0; i < 10; i++) {
								int joue = game.jouer(i, y);
								if (joue == 0) {
									bataille.getButtonsAdv()[i][y].setEnabled(false);
									bataille.getButtonsAdv()[i][y].setBackground(Color.RED);
								} else if (joue > 0) {
									bataille.getButtonsAdv()[i][y].setEnabled(false);
									bataille.getButtonsAdv()[i][y].setBackground(Color.GREEN);
									bataille.getButtonsAdv()[i][y].setIcon(icon);
									getBateau(joue).perdVie();

									if (getBateau(joue).aCoule()) {
										mess.bateauCoule(getBateau(joue));

										try {
											S.playDrown();
										} catch (UnsupportedAudioFileException | IOException
												| LineUnavailableException e) {
											e.printStackTrace();
										}

										Thread anim = new Animation(bataille, getBateau(joue), 1);
										anim.start();
									} else {
										mess.bateauTouche(getBateau(joue));

										try {
											S.playHit();
										} catch (UnsupportedAudioFileException | IOException
												| LineUnavailableException e) {
											e.printStackTrace();
										}
									}
								}
							}
							game.getUtilisables().put(2, false);
						} else {
							bataille.getInfoBar().setText("La bombe a d\u00e9j\u00e0 \u00e9t\u00e9 utilis\u00e9e !");
							return;
						}
					} else if (bataille.getBombType() == 3) {

						if (game.getUtilisables().get(3)) {
							mess.tirManque();
							int headX = ((x - 1) >= 0) ? x - 1 : 0;
							int tailX = ((x + 1) < 10) ? x + 1 : 9;
							int headY = ((y - 1) >= 0) ? y - 1 : 0;
							int tailY = ((y + 1) < 10) ? y + 1 : 9;

							for (int i = headY; i <= tailY; i++) {
								int joue = game.jouer(x, i);
								if (joue == 0) {
									bataille.getButtonsAdv()[x][i].setEnabled(false);
									bataille.getButtonsAdv()[x][i].setBackground(Color.RED);
								} else if (joue > 0) {
									bataille.getButtonsAdv()[x][i].setEnabled(false);
									bataille.getButtonsAdv()[x][i].setBackground(Color.GREEN);
									bataille.getButtonsAdv()[x][i].setIcon(icon);
									getBateau(joue).perdVie();

									if (getBateau(joue).aCoule()) {
										mess.bateauCoule(getBateau(joue));

										try {
											S.playDrown();
										} catch (UnsupportedAudioFileException | IOException
												| LineUnavailableException e) {
											e.printStackTrace();
										}

										Thread anim = new Animation(bataille, getBateau(joue), 1);
										anim.start();
									} else {
										mess.bateauTouche(getBateau(joue));

										try {
											S.playHit();
										} catch (UnsupportedAudioFileException | IOException
												| LineUnavailableException e) {
											e.printStackTrace();
										}
									}
								}
							}

							for (int i = headX; i <= tailX; i++) {
								int joue = game.jouer(i, y);
								if (joue == 0) {
									bataille.getButtonsAdv()[i][y].setEnabled(false);
									bataille.getButtonsAdv()[i][y].setBackground(Color.RED);
								} else if (joue > 0) {
									bataille.getButtonsAdv()[i][y].setEnabled(false);
									bataille.getButtonsAdv()[i][y].setBackground(Color.GREEN);
									bataille.getButtonsAdv()[i][y].setIcon(icon);
									getBateau(joue).perdVie();

									if (getBateau(joue).aCoule()) {
										mess.bateauCoule(getBateau(joue));

										try {
											S.playDrown();
										} catch (UnsupportedAudioFileException | IOException
												| LineUnavailableException e) {
											e.printStackTrace();
										}

										Thread anim = new Animation(bataille, getBateau(joue), 1);
										anim.start();
									} else {
										mess.bateauTouche(getBateau(joue));

										try {
											S.playHit();
										} catch (UnsupportedAudioFileException | IOException
												| LineUnavailableException e) {
											e.printStackTrace();
										}
									}
								}
							}
							game.getUtilisables().put(3, false);
						} else {
							bataille.getInfoBar().setText("La bombe a d\u00e9j\u00e0 \u00e9t\u00e9 utilis\u00e9e !");
							return;
						}
					}

					bataille.nouveauCoup();

					if (game.gagne(game.getGrilleAdv())) {

						bataille.getGameTimer().setEnd();
						game.setJoueur(3);  // Mis à 3 afin d'éviter que le joueur puisse jouer pendant la fin de partie
						try {
							S.playVictory();
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
							e.printStackTrace();
						}
						Thread anim = new AnimationFinJoueur(bataille, game, bataille.getButtons());
						Thread anim2 = new AnimationFinJoueur(bataille, game, bataille.getButtonsAdv());
						anim.start();
						anim2.start();

					} else {
						game.setJoueur(2);
						new AttaqueOrdi(game, bataille);
					}
				}
			}
		});

	}

	/**
	 * Retourne le bouton de la classe
	 * 
	 * @return Un bouton créé lors de l'instanciation de la classe
	 */
	public JButton getButton() {
		return this.button;
	}

	/**
	 * Recupère un bâteau à partir de son ID, dans le cas où ce bâteau existe
	 * 
	 * @param id L'ID que l'on souhaite récupérer
	 * @return Un bâteau associé à un ID
	 */
	public Bateau getBateau(int id) {
		for (Bateau B : game.getBateauxAdv()) {
			if (B.getID() == id)
				return B;
		}

		return null;
	}
}
