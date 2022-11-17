package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	
	private static Board board;

	
	//Constructor for the panel, it does 90% of the work
	public GameControlPanel(Board board)  {
		//initialize the Panel
		theGuess = new JTextField(20);
		theGuess.setEditable(false);
		theGuessResult = new JTextField(20);
		theGuessResult.setEditable(false);
		theRoll = new JTextField(5);
		theRoll.setEditable(false);
		theCurrentPlayer = new JTextField(10);
		theCurrentPlayer.setEditable(false);
		
		board.finishedTurn = false;
		
		this.board = board;
		
		
		board.gameControl();
		createControlPanel();
		setTurn(board.getCurrentPlayer(), board.getRoll());
		
		
	}
	
	private void createControlPanel() {
		setLayout(new GridLayout(2,0));
		// Use a grid layout, 1 row, 4 columns
		JPanel playTurnPanel = new JPanel();
		playTurnPanel.setLayout(new GridLayout(1,4));
		playTurnPanel.setPreferredSize(null);
		// Use a grid layout,  2 row, 0 columns
		JPanel currentPlayPanel = new JPanel();	
		currentPlayPanel.setPreferredSize(null);
		JLabel playTurnLable= new JLabel("Whose Turn?");
		playTurnLable.setPreferredSize(null);
		currentPlayPanel.add(playTurnLable);
		currentPlayPanel.add(theCurrentPlayer);	    
		// Use a grid layout,  0 row, 20 columns
		JPanel rollPanel = new JPanel();	
		rollPanel.setPreferredSize(null);
		JLabel rollLable = new JLabel("Roll:");
		rollPanel.add(rollLable);
		rollPanel.add(theRoll);
		// new two buttons for the "Make Accusation" and "Next!"
		JButton accusationButton = new JButton("Make Accusation");	
		accusationButton.setPreferredSize(null);
		JButton nextButton = new JButton("Next!");		
		nextButton.setPreferredSize(null);
		
		nextButton.addActionListener(new ButtonListener());
		
		playTurnPanel.add(currentPlayPanel);
		playTurnPanel.add(rollPanel);
		playTurnPanel.add(accusationButton);
		playTurnPanel.add(nextButton);

		add(playTurnPanel);
		
		//initialize the guessGroupPanel
		// Use a grid layout, 0 row, 2 columns
		JPanel guessGroupPanel = new JPanel();					
		guessGroupPanel.setLayout(new GridLayout(0,2));
		guessGroupPanel.setPreferredSize(null);
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

		guessGroupPanel.add(guessPanel);
		guessGroupPanel.add(guessResultPanel);
		
		add(guessGroupPanel);
		
	}

	public void setGuess(String guess) {		

		theGuess.setText(guess);
	}

	public void setGuessResult(String guessResult) {

		theGuessResult.setText(guessResult);
	}

	public void setTurn(Player currentPlayer, int roll) {

		theCurrentPlayer.setText(currentPlayer.getName());		
		theRoll.setText(String.valueOf(roll));
		playerColor= currentPlayer.getColor();
		theCurrentPlayer.setBackground(playerColor);		
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("Button pressed");
			if (board.finishedTurn) {
				board.finishedTurn =false;
				board.gameControl();
				setTurn(board.getCurrentPlayer(), board.getRoll());
				//while (!board.checkGameProcess()) {
			}
			else {
				JOptionPane.showMessageDialog(null, "Please finish your turn!");
			}
		}
		    
	}
	
	
	
	
	// Main to test the panel
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel(board);  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(new Dimension(700, 200));  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		// test filling in the data
		panel.setTurn(new ComputerPlayer("Miss Kate", "red", 0, 15), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

}
