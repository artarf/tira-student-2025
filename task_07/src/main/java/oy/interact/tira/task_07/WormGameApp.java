package oy.interact.tira.task_07;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import oy.interact.tira.util.NotYetImplementedException;

public class WormGameApp {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WormGameApp().run();
			}
		});
	}

	private void run() {
		try {
			JFrame mainFrame = new JFrame("Snakeses");
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			WormGamePanel gamePanel = new WormGamePanel();
			mainFrame.getContentPane().add(gamePanel);
			mainFrame.getContentPane().setMinimumSize(GameViewConstants.dimension);
			mainFrame.getContentPane().setPreferredSize(GameViewConstants.dimension);
			mainFrame.getContentPane().setMaximumSize(GameViewConstants.dimension);

			mainFrame.setResizable(false);
			mainFrame.pack();

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int top = (screenSize.height / 2) - (mainFrame.getHeight() / 2);
			int left = (screenSize.width / 2) - (mainFrame.getWidth() / 2);
			mainFrame.setLocation(new Point(left, top));

			mainFrame.setVisible(true);
		} catch (NotYetImplementedException e) {
			final String message = String.format("Quitting the game because of error:\n%s", e.getLocalizedMessage());
			JOptionPane.showMessageDialog(null, message, "Error in Game", JOptionPane.ERROR_MESSAGE);
		}

	}
}
