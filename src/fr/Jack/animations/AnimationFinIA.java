package fr.Jack.animations;

import java.awt.Color;

import javax.swing.JButton;

import fr.Jack.BatailleNavale;
import fr.Jack.GameAPI;
import fr.Jack.exceptions.MenuLaunchException;
import fr.Jack.menus.EndMENU;

/**
 * Classe qui gère l'animation finale lors d'une défaite.
 * 
 * @author jackhogg
 * @version 1.0
 */
public class AnimationFinIA extends Thread{
	
	private BatailleNavale B;
	private JButton[][] boutons;
	private GameAPI game;

	public AnimationFinIA(BatailleNavale B, GameAPI game, JButton[][] boutons) {
		this.B = B;
		this.boutons = boutons;
		this.game = game;
	}

	/**
	 * Exécution du Thread.
	 */
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				if ((i == 2 && j == 3) || (i == 3 && j == 3) || (i == 6 && j == 2) || (i == 6 && j == 3)
						|| (i == 6 && j == 4) || (i == 7 && j == 2)) {
					this.boutons[i][j].setBackground(Color.ORANGE);
					this.boutons[i][9 - j].setBackground(Color.ORANGE);
					this.boutons[i][j].setIcon(null);
					this.boutons[i][9 - j].setIcon(null);
				} else {
					this.boutons[i][j].setBackground(Color.BLUE);
					this.boutons[i][9 - j].setBackground(Color.BLUE);
					this.boutons[i][j].setIcon(null);
					this.boutons[i][9 - j].setIcon(null);
				}

				try {
					sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		B.getFrame().dispose();
		try {
			new EndMENU(B, game);
		} catch (MenuLaunchException e) {
			e.setMenu("EndMENU");
			e.getMessage();
			e.printStackTrace();
		}
	}

}
