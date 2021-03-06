package pathtest;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import pathtest.AStarModule.UnreachableNodeException;

public class ViewPanel extends JPanel {

	GridWorld world;
	Dude[] dudes;
	Point[] subTargets;
	Point target;
	final int AMOUNT_DUDES = 30;

	public ViewPanel() throws UnreachableNodeException {

		addMouseListener(mouseController);

		setPreferredSize(new Dimension(600, 600));

		world = new GridWorld(new Dimension(600, 600));
		
		dudes = new Dude[AMOUNT_DUDES];
		subTargets = new Point[AMOUNT_DUDES];
		for (int i = 0; i < dudes.length; i++) {
			dudes[i] = new Dude(new V2(10, 10 + i));	
		}
		
		target = new Point(9, 9);

		for (int i = 2; i < 9; i++) {
			world.changeColors(Color.RED, new Point(4, i));
		}
		for (int i = 4; i < 8; i++) {
			world.changeColors(Color.RED, new Point(i, 4));
		}

		world.rebuildGraph();
		
		new Timer(16, new ActionListener() {

			void dudeStuff(int index) {
				Dude dude = dudes[index];
				Point dudeTile = world.getTile(dude.pos);
				try {
					if (!dude.collidingWith(world.walls)) {
						List<Point> path = world.getPath(dudeTile, target);
						if (path.size() > 2) {
							subTargets[index] = path.get(1);
						} else {
							subTargets[index] = target;
						}
					} else {
						if (world.isCollidingWalls(world.getCenter(subTargets[index]))) {
							subTargets[index] = target;
						}
					}
				} catch (Exception uhoh) {
					
				}
				dude.update(world.getCenter(subTargets[index]));
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < dudes.length; i++) {
					dudeStuff(i);
				}

				for (int i = 0; i < dudes.length; i++) {
					for (int i2 = 0; i2 < dudes.length; i2++) {
						if (i != i2) {
							dudes[i].handleOverlap(dudes[i2]);
						}
					}
				}
				ViewPanel.this.repaint();
			}
		}).start();
	}

	@Override
	public void paintComponent(Graphics g) {
		world.draw(g, getSize());
		for (Dude dude : dudes) {
			dude.draw(g);
		}
		Point td = world.getCenter(target).toPoint();
		g.setColor(Color.BLACK);
		g.drawOval(td.x - 20, td.y - 20, 40, 40);
		g.drawString("Left- Click to set destination, Right-Click to place wall.", 5 , 20);
	}
	
	MouseAdapter mouseController = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			Point clicked = world.getTile(new V2(e.getPoint()));
			if (SwingUtilities.isRightMouseButton(e)) {
				world.changeColors(Color.RED, clicked);
				world.rebuildGraph();
			} else {
				target = clicked;
			}
			
			repaint();
		}
	};
	
}
