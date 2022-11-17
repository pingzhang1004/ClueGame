package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame{

	private static Board board;
	
	//a JFrame to hold our primary panels.
	//. This class that will contain our entry point (i.e. main()) for the game as well as other initializations.
	public ClueGame() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		GameControlPanel controlPanel = new GameControlPanel(board);  // create the panel
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
		
		//When the game starts, display the message
		JOptionPane.showMessageDialog(null, "You are " + board.getPlayersList().get(0).getName() + ".\nCan you find the solution\nbefore the Computer player?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	// Main to test the panel
	public static void main(String[] args) {
	
		ClueGame frame = new ClueGame();
		
	}
	
}
