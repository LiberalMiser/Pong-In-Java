package pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class BorderNet {
	
	private float dash[] = {10.0f};
	private BasicStroke basicStroke;

	public BorderNet() {
		basicStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
	}

	public void render(Graphics2D g, int x, int y) {
		g.setColor(Color.GRAY);
		g.setStroke(basicStroke);
		g.draw(new Line2D.Double(x, 0, x, y));
	}
	
}
