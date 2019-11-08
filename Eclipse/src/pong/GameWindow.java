package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings({ "serial", "deprecation" })
public class GameWindow extends JPanel implements Runnable, KeyListener, Observer {

	private Boolean updateGame = true;
    private Boolean resizable = false;
    private Color backgroundColour = Color.BLACK;
    private long frameUpdateInterval = 10;
    private int player1UpKey = KeyEvent.VK_W, player1DownKey = KeyEvent.VK_S;
    private int player2UpKey = KeyEvent.VK_UP, player2DownKey = KeyEvent.VK_DOWN;
    private int pauseKey = KeyEvent.VK_ESCAPE;
    
    private JFrame jFrame;
    private Graphics2D graphics2D;
    private JButton continueButton;
    
    private Ball ball;
    private Audio backgroundMusic = new Audio();
    private BorderNet net;
    private Paddle player1, player2;
    private Text player1ScoreText, player2ScoreText;
    private Countdown countdown;
    
    public GameWindow() {
        jFrame = new JFrame(Strings.GAME_WINDOW_TITLE);
        
        jFrame.setVisible(true);
        jFrame.setResizable(resizable);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(Dimensions.WINDOW_WIDTH, Dimensions.WINDOW_HEIGHT);
        jFrame.setLocationRelativeTo(null);
        
        setSize(jFrame.getWidth(), jFrame.getHeight());
        
        jFrame.add(this);
        
        net = new BorderNet();
        ball = new Ball((getWidth() / 2) - (Dimensions.BALL_RADIUS / 2), (getHeight() / 2) - (Dimensions.BALL_RADIUS));
        player1 = new Paddle(new Vector(40, (getHeight() / 2) - (Dimensions.PADDLE_HEIGHT / 2)));
        player2 = new Paddle(new Vector((getWidth() - 40) - Dimensions.PADDLE_WIDTH, (getHeight() / 2) - (Dimensions.PADDLE_HEIGHT / 2)));

        player1ScoreText = new Text(35);
        player2ScoreText = new Text(35);
        
        continueButton = new JButton("CONTINUE");
        continueButton.setLocation((getWidth() / 2) - (150 / 2), (getHeight() / 2) - 40);
        continueButton.setSize(150, 40);
        continueButton.setVisible(false);
        continueButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				initialize();
				continueButton.setVisible(false);
			}
        	
        });
        this.add(continueButton);

        jFrame.addKeyListener(this);
    	backgroundMusic.play("Audio/background.wav", true);
        
        initialize();
    }
    
    public void paintComponent (Graphics g) {
        graphics2D = (Graphics2D) g;
        graphics2D.setColor(backgroundColour);
    	graphics2D.fillRect(0, 0, getWidth(), getHeight());
    	
    	net.render(graphics2D, getWidth() / 2, getHeight());
    	ball.render(graphics2D);
    	player1.render(graphics2D);
    	player2.render(graphics2D);
    	
    	player1ScoreText.setText(graphics2D, String.valueOf(player1.getScore()), 100, 75);
    	player2ScoreText.setText(graphics2D, String.valueOf(player2.getScore()), getWidth() - 120, 75);
    	
    	countdown.render(graphics2D, 453);
    	
    	repaint();
    }
    
    private void initialize () {
    	jFrame.requestFocus();
    	countdown = new Countdown(4000);
    	countdown.addObserver(ball);
    	ball.addObserver(this);
    	
    	ball.setPosition(new Vector((getWidth() / 2) - (Dimensions.BALL_RADIUS / 2), (getHeight() / 2) - (Dimensions.BALL_RADIUS)));
    }
    
    private void update (double deltaTime) {
    	ball.move(new Vector(getWidth(), getHeight()), player1, player2);
    	player1.move(getHeight() - Dimensions.PADDLE_HEIGHT);
    	player2.move(getHeight() - Dimensions.PADDLE_HEIGHT);
    	
    	ball.detectPaddleCollision(player1, player2);
    }
    
    public void run () {
    	while (true) {
			if (updateGame) {
				update(0.001);
			}
			
			try {
				Thread.sleep(frameUpdateInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == pauseKey) {
			updateGame = !updateGame;
		}
		
		//Player 1 Controls:
		if (e.getKeyCode() == player1UpKey) {
			player1.setMoveUp(true);
		}

		if (e.getKeyCode() == player1DownKey) {
			player1.setMoveDown(true);
		}
		
		//Player 2 Controls:
		if (e.getKeyCode() == player2UpKey) {
			player2.setMoveUp(true);
		}

		if (e.getKeyCode() == player2DownKey) {
			player2.setMoveDown(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Player 1 Controls:
		if (e.getKeyCode() == KeyEvent.VK_W) {
			player1.setMoveUp(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			player1.setMoveDown(false);
		}
		

		//Player 1 Controls:
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player2.setMoveUp(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player2.setMoveDown(false);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		continueButton.setVisible(true);
	}

}
