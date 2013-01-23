package pathtest;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.*;
import java.util.Map.Entry;

import pathtest.AStarModule.AStarGraph;
import pathtest.AStarModule.AStarNode;
import pathtest.AStarModule.UnreachableNodeException;


public class GridWorld {

	int xCells = 15, yCells = 15;
	AStarModule<Point> module;
	AStarGraph<Point> graph;
	Dimension size;
	
	int tilew;
	int tileh;


	Color[][] colors;
	
	/**
	 * Enable/disable nav cache.
	 * Slow on rebuild, but querying for paths is way fast.
	 */
	boolean cache = false;
	
	Map<Point, List<Point>> navCache;

	public GridWorld(Dimension screenSize) {
		graph = new AStarGraph<Point>();
		colors = new Color[xCells][yCells];
		buildMap();
		module = new AStarModule<Point>(graph);
		this.size = screenSize;
		navCache = new HashMap<Point, List<Point>>();
		tilew = size.width / xCells;
		tileh = size.height / yCells;
	}

	private void buildMap() {
		// fill colors
		for (int x = 0; x < xCells; x++) {
			for (int y = 0; y < yCells; y++) {
				colors[x][y] = Color.CYAN;
			}
		}

		// build graph
		for (int x = 0; x < xCells; x++) {
			for (int y = 0; y < yCells; y++) {
				graph.addNode(new AStarNode<Point>(new Point(x, y),
						new Point.Double(x, y)));
			}
		}
		for (int x = 0; x < xCells - 1; x++) {
			for (int y = 0; y < yCells; y++) {
				graph.getNode(new Point(x, y)).addOmniLink(
						graph.getNode(new Point(x + 1, y)));
			}
		}
		for (int x = 0; x < xCells; x++) {
			for (int y = 0; y < yCells - 1; y++) {
				graph.getNode(new Point(x, y)).addOmniLink(
						graph.getNode(new Point(x, y + 1)));
			}
		}
		/*for (int x = 0; x < xCells - 1; x++) {
			for (int y = 0; y < yCells - 1; y++) {
				graph.getNode(new Point(x, y)).addOmniLink(
						graph.getNode(new Point(x + 1, y + 1)));
			}
		}
		for (int x = 1; x < xCells; x++) {
			for (int y = 1; y < yCells; y++) {
				graph.getNode(new Point(x, y)).addOmniLink(
						graph.getNode(new Point(x - 1, y - 1)));
			}
		}*/
	}

	public List<Point> getPath(Point a, Point b) throws UnreachableNodeException {
		if (cache) {
			return navCache.get(new Point(pointToIndex(a), pointToIndex(b)));
		} else {
			return getPathCostly(a, b);
		}
	}
	
	private List<Point> getPathCostly(Point a, Point b) throws UnreachableNodeException {
		List<Point> p = new LinkedList<Point>();
		List<AStarNode<Point>> s = module.findPathTo(graph.getNode(a),
				graph.getNode(b));
		for (AStarNode<Point> ss : s) {
			p.add(ss.getData());
		}
		return p;
	}

	public void changeColors(Color color, List<Point> points) {
		changeColors(color, points.toArray(new Point[0]));
	}

	public void changeColors(Color color, Point... points) {
		for (Point p : points) {
			colors[p.x][p.y] = color;
		}
	}

	public void draw(Graphics g, Dimension size) {
		// Draw grid
		for (int x = 0; x < xCells; x++) {
			for (int y = 0; y < yCells; y++) {
				g.setColor(colors[x][y]);
				g.fillRect(x * tilew, y * tileh, tilew, tileh);
				g.setColor(Color.black);
				g.drawRect(x * tilew, y * tileh, tilew, tileh);
			}
		}

	}
	
	public Point getTile (V2 v) {
		return new Point(v.getWidth() / tilew, v.getHeight() / tileh);
	}
	
	public V2 getCenter (Point tile) {
		return new V2(tilew * tile.x + tilew / 2, tileh * tile.y + tileh / 2);
	}

	public void rebuildGraph() {

		for (int x = 0; x < xCells; x++) {
			for (int y = 0; y < yCells; y++) {
				if (colors[x][y] != Color.CYAN) {
					AStarNode<Point> s = graph.getNode(new Point(x, y));
					for( Entry<AStarNode<Point>, Double> neighbor : s.getChildren().entrySet()) {
						neighbor.getKey().addPathCost(s, Double.MAX_VALUE);
					}
				}
			}
		}
		
		if (cache) {
			navCache.clear();
			for (int i = 0; i < xCells * yCells; i++) {
				for (int i2 = 0; i2 < xCells * yCells; i2++) {
					System.out.println("Rebuilding navigation cache: " + (int)(i * 100 / (xCells * yCells)) + "%");
					if (i == i2) {
						navCache.put(new Point(i, i2), new LinkedList<Point>());
					} else {
						Point fromPoint = indexToPoint(i);
						Point toPoint = indexToPoint(i2);
						try {
							navCache.put(new Point(i, i2), getPathCostly(fromPoint, toPoint));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}	
			}
		}
	}
	
	private Point indexToPoint(int index) {
		int y = index / xCells;
		int x = index % yCells;
		return new Point(x, y);
	}
	
	private int pointToIndex(Point a) {
		return a.y * xCells + a.x;
	}	
}
