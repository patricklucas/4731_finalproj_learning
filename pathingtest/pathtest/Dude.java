package pathtest;
import java.awt.Color;
import java.awt.Graphics;

public class Dude {
	V2 pos;
	int r = 10;
	float speed = 1;

	public Dude(V2 pos) {
		this.pos = pos;
	}
	
	public void update(V2 target) {
		pos = pos.add(target.sub(pos).unit().scale(speed));
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawOval(pos.getWidth() - r, pos.getHeight() - r, r * 2, r * 2);
	}
}
