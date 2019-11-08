package pong;

import java.awt.Color;
import java.awt.Graphics2D;

public class Paddle {
	
	private Color color = Color.WHITE;
	private Vector position = new Vector(40, 10);
	private int velocityY;
	private int speed = 1;
	private int score = 0;
	private final double drag = 0.65;
	
	private Boolean moveUp = false, moveDown = false;

	public Paddle(Vector position) {
		this.position = position;
	}
	
	public void render (Graphics2D g) {
		g.setColor(color);
		g.fillRect(position.x, position.y, Dimensions.PADDLE_WIDTH, Dimensions.PADDLE_HEIGHT);
	}
	
	public void move (int windowHeight) {
		position.y += velocityY;
		if (moveUp) {
			velocityY -= speed;
		}
		if (moveDown) {
			velocityY += speed;
		}
		
		if (!moveUp && !moveDown) {
			velocityY *= drag;
		}
		
		if (position.y < 0) {
			position.y = 0;
		}
		if (position.y > windowHeight) {
			position.y = windowHeight;
		}
		
	}
	
	public void setMoveUp(Boolean move) {
		moveUp = move;
	}
	
	public void setMoveDown(Boolean move) {
		moveDown = move;
	}
	
	public int getScore () {
		return score;
	}
	
	public Vector getPosition () {
		return position;
	}

	public void incrementScore(int amount) {
		score += amount;
	}
	
}
