package pathtest;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import pathtest.AStarModule.UnreachableNodeException;

public class ViewPanel extends JPanel {

	GridWorld world;
	Dude dude;

	public ViewPanel() throws UnreachableNodeException {

		setPreferredSize(new Dimension(600, 600));

		world = new GridWorld(new Dimension(600, 600));
		dude = new Dude(new V2(10, 10));

		for (int i = 2; i < 9; i++) {
			world.changeColors(Color.RED, new Point(4, i));
		}
		for (int i = 4; i < 8; i++) {
			world.changeColors(Color.RED, new Point(i, 4));
		}

		world.rebuildGraph();
		
		new Timer(16, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Point dudeTile = world.getTile(dude.pos);
					List<Point> path = world.getPath(dudeTile, new Point(9, 9));
					world.changeColors(Color.GREEN, path);
					Point cGoal = dudeTile;
					if (path.size() > 1) {
						cGoal = path.get(1);
					}
					dude.update(world.getCenter(cGoal));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				ViewPanel.this.repaint();
			}
		}).start();
	}

	@Override
	public void paintComponent(Graphics g) {
		world.draw(g, getSize());
		dude.draw(g);
	}
}
