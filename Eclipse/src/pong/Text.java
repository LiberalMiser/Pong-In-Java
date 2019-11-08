package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Text {
	
	private Color textColour = Color.white;
	private Font font;

	public Text(int fontSize) {
		font = new Font(null, 0, fontSize);
	}
	
	public void setText(Graphics2D g, String text, int x, int y) {
		g.setColor(textColour);
		g.setFont(font);
		g.drawString(text, x, y);
	}

}