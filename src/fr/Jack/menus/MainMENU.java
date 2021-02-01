package fr.Jack.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.Jack.BatailleNavale;
import fr.Jack.FichierSauvegarde;
import fr.Jack.GameAPI;
import fr.Jack.exceptions.MenuLaunchException;

/**
 * Cette classe créé les caractéristiques du menu principal.
 * 
 * @author jackhogg
 * @version 1.0
 */
public class MainMENU {

	private final String title = "Main Menu";
	private int mode = 0;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MainMENU() throws MenuLaunchException {
		GameAPI game = new GameAPI();

		// Fenêtre du menu
		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon("explo.png","");

		// Panel principal, regroupant différents autre Panels
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		// Panel d'en-tête
		JPanel headingPanel = new JPanel();
		JLabel headingLabel = new JLabel("Bienvenue dans le jeu de la bataille navale de Jack !");
		headingPanel.add(headingLabel);

		// Création du panel avec les contraites du layout
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constr = new GridBagConstraints();

		// Eléments du Panel
		JLabel userNameLabel = new JLabel("Entrez votre pseudo :");
		JTextField userNameTxt = new JTextField(15);
		JButton button = new JButton("Commencer la partie");
		JButton button2 = new JButton("Lancer sauvegarde");
		FichierSauvegarde fichier = new FichierSauvegarde("sauvegarde.txt");
		String[] elements = { "Facile", "Moyen", "Difficile" };
		JComboBox liste1 = new JComboBox(elements);
		JLabel liste = new JLabel("Difficult\u00e9 :");
		JLabel warning = new JLabel("ATTENTION : Bruitages, pensez \u00e0 ajuster votre son.");
		Font font = new Font("Arial",Font.BOLD,12);
		warning.setFont(font);

		liste1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (e.getItem() == "Facile")
						mode = 0;
					if (e.getItem() == "Moyen")
						mode = 1;
					if (e.getItem() == "Difficile")
						mode = 2;
				}
			}
		});

		// Ajout des évènements aux boutons
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new BatailleNavale(userNameTxt.getText(), game, mode);
				} catch (MenuLaunchException e1) {
					e1.setMenu("Bataille Navale");
					e1.getMessage();
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});

		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!fichier.isEmpty()) {

					try {
						/*
						 * Avant de relancer le jeu, on fait en sorte que tous les paramètres se remettent en place
						 */
						ArrayList<Object> objets = fichier.lire();
						HashMap<String, int[][]> grilles = (HashMap<String, int[][]>) objets.get(0);

						game.setGrille(grilles.get("Joueur"));
						game.setGrilleAdv(grilles.get("Ordinateur"));
						game.setUtilisables((HashMap<Integer, Boolean>) objets.get(1));
						game.setUtilisablesAdv((HashMap<Integer, Boolean>) objets.get(2));

						//On relance la bataille navale avec les paramètres remis comme avant
						BatailleNavale bataille = new BatailleNavale(userNameTxt.getText(), game, mode);

						// Autres paramètres et attributs
						bataille.setCoups((int) objets.get(4));
						bataille.getGameTimer().setSeconds(((int) objets.get(3)));
						bataille.setMode((int) objets.get(5));
						bataille.setCases((ArrayList<int[]>) objets.get(6));

						for (int i = 0; i < ((ArrayList<Integer>) objets.get(7)).size(); i++) {
							game.getBateaux().get(i).setVies(((ArrayList<Integer>) objets.get(7)).get(i));
						}

						for (int i = 0; i < ((ArrayList<Integer>) objets.get(8)).size(); i++) {
							game.getBateauxAdv().get(i).setVies(((ArrayList<Integer>) objets.get(8)).get(i));
						}

						// Le code modifie l'interface en conséquence.
						for (int i = 1; i < 11; i++) {
							for (int j = 1; j < 11; j++) {
								if (game.getGrilleAdv()[i - 1][j - 1] == -1) {
									bataille.getButtonsAdv()[i - 1][j - 1].setBackground(Color.GREEN);
									bataille.getButtonsAdv()[i - 1][j - 1].setEnabled(false);
									bataille.getButtonsAdv()[i - 1][j - 1].setIcon(icon);
								} else if (game.getGrilleAdv()[i - 1][j - 1] == -2) {
									bataille.getButtonsAdv()[i - 1][j - 1].setBackground(Color.RED);
									bataille.getButtonsAdv()[i - 1][j - 1].setEnabled(false);
								}
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (MenuLaunchException e2) {
						e2.setMenu("Bataille Navale");
						e2.getMessage();
						e2.printStackTrace();
					}

					frame.dispose();
				}
			}
		});

		constr.insets = new Insets(5, 10, 5, 10);
		constr.anchor = GridBagConstraints.CENTER;

		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 1;

		panel.add(userNameLabel, constr);
		constr.gridwidth = 1;
		constr.gridx = 1;
		constr.anchor = GridBagConstraints.WEST;
		panel.add(userNameTxt, constr);
		constr.anchor = GridBagConstraints.CENTER;
		constr.gridy = 1;
		constr.gridx = 0;
		constr.gridwidth = 1;

		panel.add(liste, constr);
		constr.gridx = 1;
		panel.add(liste1, constr);
		constr.gridwidth = 1;
		constr.gridx = 0;
		constr.gridy = 2;

		constr.insets = new Insets(25, 0, 10, 0);
		panel.add(button, constr);
		constr.gridx = 1;
		panel.add(button2, constr);
		constr.gridwidth = 2;
		constr.gridx = 0;
		constr.gridy = 3;
		panel.add(warning, constr);

		mainPanel.add(headingPanel);
		mainPanel.add(panel);

		// Création de la fenêtre
		frame.setTitle(this.title);
		frame.setIconImage(icon.getImage());
		frame.add(mainPanel);
		frame.pack();
		frame.setSize(550, 250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
