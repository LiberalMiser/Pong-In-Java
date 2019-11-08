package pong;

import javax.swing.UIManager;

public class MainEntryPoint {

    public static void main(String[] args) {
        try {
        	UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        	} 
        catch(Exception e) {
        	
        }
        GameWindow gameWindow = new GameWindow();
        Thread gameThread = new Thread(gameWindow);
        
        gameThread.start();
    }

}
