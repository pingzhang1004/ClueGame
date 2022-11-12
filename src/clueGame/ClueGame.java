package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class ClueGame extends JFrame{

	//a JFrame to hold our primary panels.
	//. This class that will contain our entry point (i.e. main()) for the game as well as other initializations.
	public ClueGame() {
		// Board is singleton, get the only instance
		Board board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		GameControlPanel gPanel = new GameControlPanel();  // create the panel
		KnownCardsPanel kPanel = new KnownCardsPanel(board); 
		int i = kPanel.getWidth();
		int j = kPanel.getHeight();
		add(board, BorderLayout.CENTER);
		add(gPanel, BorderLayout.SOUTH);
		add(kPanel, BorderLayout.EAST);
		setSize(new Dimension(1200,1200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close		
		setVisible(true);
		
		revalidate();
		repaint();
	}
	
	// Main to test the panel
	public static void main(String[] args) {
	
		ClueGame frame = new ClueGame();
		
		
		
	}
	
}
