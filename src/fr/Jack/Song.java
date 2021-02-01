package fr.Jack;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Nous pouvons gérer les différents sons émis lors d'évènements.
 * 
 * @author jackhogg
 * @version 1.0
 */
public class Song {
	
	public Song() {
		
	}
	
	/**
	 * Joue le son d'un bâteau touché.
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public void playHit() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		String soundName = "Tir.wav";    
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}
	
	/**
	 * Joue le son d'un bâteau coulé.
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public void playDrown() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		String soundName = "Coule.wav";    
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}
	
	/**
	 * Joue le son d'une victoire.
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public void playVictory() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		String soundName = "Victoire.wav";    
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}
	
	/**
	 * Joue le son d'une défaite.
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public void playDefeat() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		String soundName = "Defaite.wav";    
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}

}
