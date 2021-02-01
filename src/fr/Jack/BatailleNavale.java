package fr.Jack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import fr.Jack.exceptions.MenuLaunchException;

/**
 * Cette classe gère le jeu (externe) donc l'interface, les boutons des grilles, et les différentes composantes telles que
 * les barres d'informations, les boutons radios et les boutons classiques.
 * 
 * @author jackhogg
 * @version 1.0
 */
public class BatailleNavale {

	private int IDbombe = 0, nbCoups = 0;
	private GameAPI game;
	private JFrame frame;
	private JButton[][] boutons, boutonsAdv;
	private JLabel barreInfo, labelJoueur, labelAdversaire;
	private TimerJeu timer;
	private int mode;
	private ArrayList<int[]> listeCases = new ArrayList<int[]>();

	public BatailleNavale(String pseudo, GameAPI game, int mode) throws MenuLaunchException {
		TimerJeu timer = new TimerJeu();
		JFrame frame = new JFrame();
		FichierSauvegarde fichier = new FichierSauvegarde("sauvegarde.txt");

		this.timer = timer;
		this.frame = frame;
		this.mode = mode;
		this.game = game;

		frame.setTitle("Bienvenue dans la bataille navale " + pseudo + " !");

		// Création du GUI
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

		// Layout contenant les deux grilles
		JPanel corps = new JPanel();
		corps.setLayout(new GridBagLayout());

		// Création des contraintes
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = new Insets(5, 20, 5, 20);
		gbc1.anchor = GridBagConstraints.WEST;
		corps.setSize(1100, 500);
		gbc1.gridx = 0;
		gbc1.gridy = 0;

		// Indexes des colonnes
		String alpha = "ABCDEFGHIJ";
		String[] lettre = alpha.split("");

		// Panel de la grille du joueur
		JPanel grille = new JPanel();
		grille.setLayout(new GridLayout(11, 11));
		this.boutons = new JButton[10][10];

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (i != 0 && j == 0) {
					JLabel label = new JLabel("" + i, SwingConstants.CENTER);
					grille.add(label);
				} else if (i == 0 && j != 0) {
					JLabel label = new JLabel("" + lettre[j - 1], SwingConstants.CENTER);
					grille.add(label);
				} else if (i == 0 && j == 0) {
					JLabel label = new JLabel("", SwingConstants.CENTER);
					grille.add(label);
				} else {
					JButton button = new JButton();
					button.setBackground(Color.BLUE);
					if (game.getGrille()[i - 1][j - 1] > 0) {
						button.setBackground(Color.GRAY);
					} else if (game.getGrille()[i - 1][j - 1] == -1) {
						button.setBackground(Color.GREEN);
					} else if (game.getGrille()[i - 1][j - 1] == -2) {
						button.setBackground(Color.RED);
					}
					button.setEnabled(false);
					grille.add(button);
					this.boutons[i - 1][j - 1] = button;
				}
			}
		}
		grille.setPreferredSize(new Dimension(550, 500));
		grille.setMinimumSize(new Dimension(550, 500));
		grille.setMaximumSize(new Dimension(550, 500));

		corps.add(grille, gbc1);
		gbc1.gridx = 1;

		// Création de la grille adverse
		JPanel grilleAdv = new JPanel();
		grilleAdv.setLayout(new GridLayout(11, 11));
		this.boutonsAdv = new JButton[10][10];

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (i != 0 && j == 0) {
					JLabel label = new JLabel("" + i, SwingConstants.CENTER);
					grilleAdv.add(label);
				} else if (i == 0 && j != 0) {
					JLabel label = new JLabel("" + lettre[j - 1], SwingConstants.CENTER);
					grilleAdv.add(label);
				} else if (i == 0 && j == 0) {
					JLabel label = new JLabel("", SwingConstants.CENTER);
					grilleAdv.add(label);
				} else {
					SlotGame c = new SlotGame(this, game, i - 1, j - 1);
					grilleAdv.add(c.getButton());
					c.getButton().setBackground(Color.BLUE);
					this.boutonsAdv[i - 1][j - 1] = c.getButton();
				}
			}
		}
		grilleAdv.setPreferredSize(new Dimension(550, 500));
		grilleAdv.setMinimumSize(new Dimension(550, 500));
		grilleAdv.setMaximumSize(new Dimension(550, 500));

		corps.add(grilleAdv, gbc1);

		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.anchor = GridBagConstraints.CENTER;

		JLabel labelGrille = new JLabel("Votre grille", SwingConstants.CENTER);
		Font fontgrille = new Font("Caruban",Font.BOLD,15);
		labelGrille.setFont(fontgrille);
		this.labelJoueur = labelGrille;
		corps.add(labelGrille, gbc1);

		gbc1.gridx = 1;

		JLabel labelGrilleAdv = new JLabel("Grille adverse", SwingConstants.CENTER);
		Font fontgrilleadv = new Font("Caruban",Font.BOLD,15);
		labelGrilleAdv.setFont(fontgrilleadv);
		this.labelAdversaire = labelGrilleAdv;
		corps.add(labelGrilleAdv, gbc1);

		gbc1.gridx = 0;
		gbc1.gridy = 2;
		gbc1.gridwidth = 2;

		JLabel message = new JLabel("Vous \u00eates le/la bienvenue dans cette partie de bataille navale !");
		this.barreInfo = message;

		corps.add(message, gbc1);

		gbc1.gridx = 0;
		gbc1.gridy = 4;
		gbc1.gridwidth = 1;
		gbc1.gridheight = 2;

		
		// Créations des boutons
		JButton recommence = new JButton(), sauvegarde = new JButton();

		recommence.setText("Recommencer");
		recommence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				GameAPI newGame = new GameAPI();
				fichier.effacer();
				getGameTimer().setEnd();
				
				try {
					new BatailleNavale(pseudo, newGame, mode);
				} catch (MenuLaunchException e1) {
					e1.setMenu("Bataille Navale");
					e1.getMessage();
					e1.printStackTrace();
				}
			}
		});

		sauvegarde.setText("Sauvegarder");
		sauvegarde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					HashMap<String, int[][]> grilles = new HashMap<String, int[][]>();
					grilles.put("Joueur", game.getGrille());
					grilles.put("Ordinateur", game.getGrilleAdv());

					fichier.sauver(grilles, game, getInstance());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				getGameTimer().setEnd();
				frame.dispose();
			}
		});

		corps.add(recommence, gbc1);
		gbc1.gridy = 5;
		corps.add(sauvegarde, gbc1);

		gbc1.insets = new Insets(0, 5, 5, 5);
		gbc1.gridx = 1;
		gbc1.gridy = 3;
		gbc1.gridheight = 1;

		// Regroupe l'ensemble des boutons radios
		// Au changement d'état d'un bouton, l'ID de la bombe change
		ButtonGroup bg = new ButtonGroup();
		ImageIcon icon = new ImageIcon("bombe.png","");
		JLabel bombes = new JLabel("BOMBES", icon, JLabel.CENTER);
		Font font = new Font("Arial",Font.BOLD,12);
		bombes.setFont(font);
		JRadioButton br1 = new JRadioButton("Classique (Illimit\u00e9es)");
		br1.setSelected(true);
		br1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					IDbombe = 0;
				}
			}
		});
		JRadioButton br2 = new JRadioButton("Horizentale (1)");
		br2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					IDbombe = 1;
				}
			}
		});
		JRadioButton br3 = new JRadioButton("Verticale (1)");
		br3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					IDbombe = 2;
				}
			}
		});
		JRadioButton br4 = new JRadioButton("En croix (1)");
		br4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					IDbombe = 3;
				}
			}
		});
		bg.add(br1);
		bg.add(br2);
		bg.add(br3);
		bg.add(br4);

		gbc1.anchor = GridBagConstraints.CENTER;
		corps.add(bombes, gbc1);
		gbc1.gridy = 4;
		corps.add(br1, gbc1);
		gbc1.gridy = 5;
		corps.add(br2, gbc1);
		gbc1.gridy = 6;
		corps.add(br3, gbc1);
		gbc1.gridy = 7;
		corps.add(br4, gbc1);

		main.add(corps);

		// Création de la fenêtre
		frame.add(main);
		frame.setIconImage(new ImageIcon("explo.png").getImage());
		frame.pack();
		frame.setSize(1200, 800);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Recupère l'identifiant de la bombe utilisée.
	 * @return ID de la bombe utilisée
	 */
	public int getBombType() {
		return this.IDbombe;
	}

	/**
	 * Récupère le nombre de tentatives.
	 * @return Nombre de tentatives.
	 */
	public int getCoups() {
		return this.nbCoups;
	}

	/**
	 * Redéfinit le nombre de tentatives.
	 * Utilisée pour le lancement d'une sauvegarde.
	 * @param nbCoups Nouveau nombre de tentatives
	 */
	public void setCoups(int nbCoups) {
		this.nbCoups = nbCoups;
	}

	/**
	 * Représente une nouvelle tentative.
	 */
	public void nouveauCoup() {
		this.nbCoups++;
	}

	/**
	 * Récupère l'ensemble des boutons, représentants les cases, de la grille du joueur.
	 * @return Tableau de JButton
	 */
	public JButton[][] getButtons() {
		return this.boutons;
	}

	/**
	 * Récupère l'ensemble des boutons, représentants les cases, de la grille de l'adversaire.
	 * @return Tableau de JButtons
	 */
	public JButton[][] getButtonsAdv() {
		return this.boutonsAdv;
	}

	/**
	 * Récupère la fenêtre de l'interface.
	 * @return JFrame représentant la fenêtre actuelle.
	 */
	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Récupère la barre d'information principale.
	 * @return JLabel représentant la barre d'information du jeu.
	 */
	public JLabel getInfoBar() {
		return this.barreInfo;
	}

	/**
	 * Récupère la barre d'information du joueur.
	 * @return JLabel représentant la barre d'information du joueur.
	 */
	public JLabel getInfoBarJoueur() {
		return this.labelJoueur;
	}

	/**
	 * Récupère la barre d'information de l'adversaire.
	 * @return JLabel représentant la barre d'information de l'adversaire.
	 */
	public JLabel getInfoBarAdversaire() {
		return this.labelAdversaire;
	}

	/**
	 * Récupère le timer du jeu.
	 * @return Timer
	 * @see TimerJeu
	 */
	public TimerJeu getGameTimer() {
		return this.timer;
	}

	/**
	 * Récupère l'instance créée.
	 * @return L'instance de cette classe.
	 */
	public BatailleNavale getInstance() {
		return this;
	}
	
	/**
	 * Récupère le mode de jeu (difficulté)
	 * @return Entier représentant la difficulté (0, 1, 2)
	 */
	public int getMode() {
		return this.mode;
	}
	
	/**
	 * Redéfinit la difficulté du jeu
	 * @param mode Difficulté du jeu (0, 1, 2)
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	/**
	 * Récupère une liste de cases.
	 * @return Liste de cases
	 */
	public ArrayList<int[]> getCases() {
		return this.listeCases;
	}
	
	/**
	 * Redéfinit la liste des cases
	 * @param liste Nouvelle liste de cases
	 */
	public void setCases(ArrayList<int[]> liste) {
		this.listeCases = liste;
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

}
