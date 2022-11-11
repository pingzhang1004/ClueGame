package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class ClueGame extends JFrame{

	public ClueGame() {
		// Board is singleton, get the only instance
		Board board = Board.getInstance();
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		GameControlPanel gPanel = new GameControlPanel();  // create the panel
		KnownCardsPanel kPanel = new KnownCardsPanel(board); 
		add(board, BorderLayout.CENTER);
		add(gPanel, BorderLayout.SOUTH);
		add(kPanel, BorderLayout.EAST);
		setSize(new Dimension(1200,1200));
		
		setVisible(true);
		
		revalidate();
		repaint();
	}
	
	// Main to test the panel
	public static void main(String[] args) {

		
		
		ClueGame frame = new ClueGame();
		
		
		
	}
	
}
