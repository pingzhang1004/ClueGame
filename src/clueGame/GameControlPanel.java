package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {

	private JTextField theGuess;
	private JTextField theGuessResult;
	private JTextField theCurrentPlayer;
	private JTextField theRoll;
	private Color playerColor;

	//Constructor for the panel, it does 90% of the work
	public GameControlPanel()  {
		theGuess = new JTextField(20);
		theGuess.setEditable(false);
		//theGuess.setPreferredSize(new Dimension(40, 40));
		theGuessResult = new JTextField(20);
		theGuessResult.setEditable(false);
		//theGuessResult.setPreferredSize(new Dimension(40, 40));
		theRoll = new JTextField(5);
		theRoll.setEditable(false);
		//theRoll.setPreferredSize(new Dimension(5, 5));
		theCurrentPlayer = new JTextField(20);
		theCurrentPlayer.setEditable(false);
		//theCurrentPlayer.setPreferredSize(new Dimension(20, 20));
		
		JPanel panel  = createControlPanel();
		add(panel);
	}

	//	private JPanel createKnownCardPanel() {
	//	 return null;
	//	}
	//	

	private JPanel createControlPanel() {
	
		//initialize the  createControlPane 
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2,0));		
		// Use a grid layout, 1 row, 4 columns
		JPanel playTurnPanel = new JPanel();
		playTurnPanel.setLayout(new GridLayout(1,4));
		// Use a grid layout,  2 row, 0 columns
		JPanel currentPlayPanel = new JPanel();	
		currentPlayPanel.setLayout(new GridLayout(2,0));
		JLabel playTurnLable= new JLabel("Whose Turn?");
		currentPlayPanel.add(playTurnLable);
		currentPlayPanel.add(theCurrentPlayer);
		// Use a grid layout,  0 row, 20 columns
		JPanel rollPanel = new JPanel();	
		rollPanel.setLayout(new GridLayout(0,2));
		JLabel rollLable = new JLabel("Roll:");
		rollPanel.add(rollLable);
		rollPanel.add(theRoll);
		// new two buttons for the "Make Accusation" and "Next!"
		JButton accusationButton = new JButton("Make Accusation");								
		JButton nextButton = new JButton("Next!");

		controlPanel.add(playTurnPanel);
		playTurnPanel.add(currentPlayPanel);
		playTurnPanel.add(rollPanel);
		playTurnPanel.add(accusationButton);
		playTurnPanel.add(nextButton);

		//initialize the guessGroupPanel
		// Use a grid layout, 0 row, 2 columns
		JPanel guessGroupPanel = new JPanel();					
		guessGroupPanel.setLayout(new GridLayout(0,2));
		// Use a grid layout,  1 row, 0 columns
		JPanel guessPanel = new JPanel();		
		guessPanel.setLayout(new GridLayout(1,0));
		guessPanel.add(theGuess);
		// add label, text
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		// Use a grid layout,  1 row, 0 columns
		JPanel guessResultPanel = new JPanel();		
		guessResultPanel.setLayout(new GridLayout(1,0));
		guessResultPanel.add(theGuessResult);
		// add label, text
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));

		controlPanel.add(guessGroupPanel);
		guessGroupPanel.add(guessPanel);
		guessGroupPanel.add(guessResultPanel);
		
		return controlPanel;
	}


	// Main to test the panel
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// test filling in the data
		panel.setTurn(new ComputerPlayer("Miss Kate", "red", 0, 15), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
		//panel.setPreferredSize(frame.getSize());
		//panel.set
	}

	public void setGuess(String guess) {		

		theGuess.setText(guess);
	}

	public void setGuessResult(String guessResult) {

		theGuessResult.setText(guessResult);
	}

	public void setTurn(ComputerPlayer currentPlayer, int roll) {

		theCurrentPlayer.setText(currentPlayer.getName());		
		theRoll.setText(String.valueOf(roll));
		playerColor= currentPlayer.getColor();
		theCurrentPlayer.setBackground(playerColor);
		
	}
}
