package pathtest;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Dude {
	V2 pos;
	int r = 10;
	float speed = 2;

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

	/**
	 * Worst method ever
	 */
	public void handleOverlap(Dude other) {
		V2 connect = other.pos.sub(pos);
		float connectMag = connect.mag();	
		if (connectMag < r + other.r) {
			V2 midpoint = pos.add( connect.unit().scale(connectMag / 2) );
			pos = midpoint.add( pos.sub(midpoint).unit().scale(r) );
			other.pos = midpoint.add( other.pos.sub(midpoint).unit().scale(r) );
		}
	}
	
	public boolean collidingWith(List<Rectangle> walls) {
		int r = this.r / 2;
		Rectangle s = new Rectangle((int) (pos.x - r), (int) (pos.y -r), r * 2, r * 2);
		for (Rectangle wall : walls) {
			if (wall.intersects(s)) {
				return true;
			}
		}
		return false;
	}
}
