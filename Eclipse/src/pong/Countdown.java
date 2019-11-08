package pong;

import java.awt.Graphics2D;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Countdown extends Observable {
	
	private Text text;
	private long currentTime;
	private Thread countdownThread;
	private Boolean began = false;
	private Audio tickAudio;

	public Countdown(long startTime) {
		text = new Text(60);
		tickAudio = new Audio();
		
		countdownThread = new Thread () {
			public void run() {
				currentTime = startTime;
				while (currentTime > -1000) {
					currentTime -= 1000;
					if (currentTime <= 3000 && currentTime > -1000) {
						tickAudio.play("Audio/ping.wav", false);
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		countdownThread.start();
	}
	
	public void render (Graphics2D g, int x) {
		if (currentTime > 0) {
			text.setText(g, String.valueOf(currentTime * 0.001), x, 100);	
		}
		else {
			if (currentTime == 0) {
				text.setText(g, "GO!", x, 100);
				if (!began) {
					beginGame();
					began = true;
				}
			}
			else {
				if (currentTime < 0) {
					text.setText(g, "", x, 100);
					began = false;
				}
			}
		}
	}
	
	public void beginGame () {
		setChanged();
		notifyObservers();
	}

}