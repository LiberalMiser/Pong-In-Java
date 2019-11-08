package pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Ball extends Observable implements Observer {
	
	private int speed = 20;
	private Color colour = Color.CYAN;
	private Vector position = new Vector(300, 300);
	private Boolean allowMovement = true;
	private Audio impactAudio;
	
	private Boolean moveUp = false, moveDown = false, moveLeft = false, moveRight = false;

	public Ball(int x, int y) {
		position.x = x;
		position.y = y;
		
		impactAudio = new Audio();
	}
	
	public void setAllowMovenent (Boolean allow) {
		allowMovement = allow;
	}
	
	public void launch () {
		allowMovement = true;
		
		moveUp = true;
		moveDown = false;
		moveLeft = true;
		moveRight = false;
		speed = 2;
	}
	
	public void move(Vector bounds, Paddle player1, Paddle player2) {
		if (allowMovement) {
			if (position.y < 0) {
				moveDown = true;
				moveUp = false;
				impactAudio.play("Audio/boom.wav", false);
			}
			else {
				if (position.y > bounds.y - Dimensions.BALL_RADIUS) {
					moveUp = true;
					moveDown = false;
					impactAudio.play("Audio/boom.wav", false);
				}
			}
			
			if (position.x < 0) {
				allowMovement = false;
				player2.incrementScore(1);
				missed();
			}
			else {
				if (position.x > bounds.x - Dimensions.BALL_RADIUS) {
					allowMovement = false;
					player1.incrementScore(1);
					missed();
				}
			}
			
			
			if (moveDown) {
				position.y += speed;
			}
			if (moveUp) {
				position.y -= speed;
			}
			if (moveLeft) {
				position.x -= speed;
			}
			if (moveRight) {
				position.x += speed;
			}
		}
	}
	
	public void detectPaddleCollision (Paddle paddle1, Paddle paddle2) {
		if (position.y >= paddle1.getPosition().y 
				&& position.y <= paddle1.getPosition().y + Dimensions.PADDLE_HEIGHT 
				&& position.x >= paddle1.getPosition().x 
				&& position.x <= paddle1.getPosition().x + Dimensions.PADDLE_WIDTH) {
			impactAudio.play("Audio/hit.wav", false);
			moveRight = true;
			moveLeft = false;
			speed += 1;
		}
		if (position.y >= paddle2.getPosition().y 
				&& position.y <= paddle2.getPosition().y + Dimensions.PADDLE_HEIGHT 
				&& position.x >= paddle2.getPosition().x - Dimensions.BALL_RADIUS 
				&& position.x <= paddle2.getPosition().x + Dimensions.PADDLE_WIDTH + Dimensions.BALL_RADIUS) {
			impactAudio.play("Audio/hit.wav", false);
			moveRight = false;
			moveLeft = true;
			speed += 1;
		}
	}
	
	public void render (Graphics2D g) {
		g.setColor(colour);
		g.fillOval(position.x, position.y, Dimensions.BALL_RADIUS, Dimensions.BALL_RADIUS);
	}
	
	public void setPosition (Vector position) {
		this.position = position; 
	}
	
	@Override
	public void update(Observable o, Object arg) {
		launch();
	}

	public void missed () {
		setChanged();
		notifyObservers();
	}
	
}
