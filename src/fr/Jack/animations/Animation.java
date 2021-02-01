package fr.Jack.animations;

import java.awt.Color;

import fr.Jack.BatailleNavale;
import fr.Jack.Bateau;

/**
 * Classe qui gère l'animation principale lorsqu'un bâteau coule.
 * 
 * @author jackhogg
 * @version 1.0
 */
public class Animation extends Thread {

	private BatailleNavale B;
	private Bateau Bat;
	private int joueur;

	public Animation(BatailleNavale B, Bateau Bat, int joueur) {
		this.B = B;
		this.Bat = Bat;
		this.joueur = joueur;
		
		
	}

	/**
	 * Exécution du Thread
	 */
	@Override
    public void run() {
    	if (Bat.isVertical()) {
            for (int i = this.Bat.getX(); i < Bat.getX() + Bat.getTaille(); i++) {
            	if (this.joueur == 1) B.getButtonsAdv()[i][Bat.getY()].setBackground(Color.BLACK);
            	else B.getButtons()[i][Bat.getY()].setBackground(Color.BLACK);
            	
            	try {
					sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
    	}else {
            for (int i = this.Bat.getY(); i < Bat.getY() + Bat.getTaille(); i++) {
            	if (this.joueur == 1) B.getButtonsAdv()[Bat.getX()][i].setBackground(Color.BLACK);
            	else B.getButtons()[Bat.getX()][i].setBackground(Color.BLACK);
            	
            	try {
					sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
    	}
    }

}
