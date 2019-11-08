package pong;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Simple class created for playing audio in the game.*/
public class Audio {
	
	/**
	 * Takes in a path to an audio file to play, 
	 * along with a Boolean to determine whether the audio should be looped
	 * 
	 * @param	path 	the path to the audio file
	 * @param	loop	whether or not the audio should be looped continuously*/
	public void play(String path, Boolean loop) {
		
		Thread audioThread = new Thread () {
			public void run () {
				try {
					File file = new File(path);
					
					if (file.exists()) {
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
						Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						
						if (loop) {
							clip.loop(Clip.LOOP_CONTINUOUSLY);
						}
						
						clip.start();
					}
					else {
						System.out.println("File doesn't seem to exist..");
					}
				}
				catch (Exception e) {
					System.out.println("Something went wrong...");
				}	
			}
		};
		
		audioThread.start();
	}
	
}
