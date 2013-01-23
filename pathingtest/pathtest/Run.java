package pathtest;
import javax.swing.JFrame;

import pathtest.AStarModule.UnreachableNodeException;

public class Run {
	public static void main(String[] args) throws UnreachableNodeException {
		JFrame frame = new JFrame("MapTest");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ViewPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
}
