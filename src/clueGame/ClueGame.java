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
		
		GameControlPanel controlPanel = new GameControlPanel();  // create the panel
		KnownCardsPanel cardsPanel = new KnownCardsPanel(board); 
		
		// set frame size and title
		setSize(1200,1200);
		setTitle("Clue Game - CSCI306");
		
		// set frame layout
		add(board, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		add(cardsPanel, BorderLayout.EAST);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close		
		setVisible(true);
		
	}
	
	// Main to test the panel
	public static void main(String[] args) {
	
		ClueGame frame = new ClueGame();	
		
	}
	
}
