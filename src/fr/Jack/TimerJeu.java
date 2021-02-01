package fr.Jack;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Cette classe lance un timer en secondes.
 * 
 * @author jackhogg
 * @version 1.0
 * 
 */
public class TimerJeu {
	
	private boolean arret = false;
	private int secondes = 0;
	
	public TimerJeu() {
		
		
		Timer chrono = new Timer();
		chrono.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (arret == true) cancel();
				secondes++;
			}
		}, 0, 1000);
	}
	
	/**
	 * Permet d'arrêter le chronomètre.
	 */
	public void setEnd() {
		this.arret = true;
	}
	
	/**
	 * Permet de relancer le chronomètre en cas d'arrêt de celui-ci
	 */
	public void setStart() {
		this.arret = false;
	}
	
	/**
	 * Retourne le temps en secondes
	 * @return Le temps écoulé en secondes depuis l'instanciation du timer
	 */
	public int getSeconds() {
		return this.secondes;
	}
	
	/**
	 * Redéfinit le temps en secondes
	 * @param sec le temps en secondes
	 */
	public void setSeconds(int sec) {
		if (sec >= 0) this.secondes = sec;
		else System.out.println("Impossible d'affecter un temps n\u00e9gatif.");
	}


}
