package clueGame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



//A dialog box for making Suggestion and making accusation
public class SuggestionGUI extends JFrame{

	//main 函数测试用的，合并逻辑后可以删除？
	private static Board board;

	private JTextField roomName;
	private JComboBox<String> personComboBox;
	private JComboBox<String> weaponComboBox;

	private String guessText;
	private String guessRoom;
	private String guessPerson;
	private String guessWeapon;

	//titleString is a parameter to distinguish suggestion or accusation
	public SuggestionGUI(String titleString,Board board) {	
		this.board = board;	

		//create dialog 
		setSize(new Dimension(300, 150));
		// dialog title display
		setTitle("Make a " + titleString);
		setLayout(new GridLayout(0, 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

		//room display
		JLabel roomLabel = new JLabel(" Current Room");
		roomName = new JTextField();
		roomName.setFont(new Font("SansSerif", Font.BOLD, 12));
		add(roomLabel);
		roomName = createRoomField();
		add(roomName);

		//person display
		JLabel personLabel = new JLabel(" Person");		
		add(personLabel);
		personComboBox  = createPersonCombo();
		add(personComboBox);

		//weapon display
		JLabel weaponLabel = new JLabel(" Weapon");	
		add(weaponLabel);
		weaponComboBox  = createWeaponCombo();
		add(weaponComboBox);

		ComboListener listener = new ComboListener();
		personComboBox.addActionListener(listener);
		weaponComboBox.addActionListener(listener);


		JButton submitButton = new JButton("Submit");	
		submitButton.setPreferredSize(null);
		add(submitButton);

		JButton cancelButton = new JButton("Cancel");		
		cancelButton.setPreferredSize(null);
		add(cancelButton);

		submitButton.addActionListener(new submitButtonListener());
		cancelButton.addActionListener(new cancelButtonListener());

	}


	public JTextField createRoomField() {
		JTextField textField = new JTextField();
		Entry<Character, Room> entry = board.getRoomMap().entrySet().iterator().next();
		Room firstRoom = entry.getValue();
		textField.setText(firstRoom.getName());
		textField.setEditable(false);
		//set room for Guess Field in Control Panel
		guessRoom = firstRoom.getName();
		return textField;
	}

	public JComboBox<String> createPersonCombo() {
		JComboBox<String> combo = new JComboBox<String>();
		for(Player player: board.getPlayersList()) {
			combo.addItem(player.getName());
		}
		return combo;
	}


	public JComboBox<String> createWeaponCombo() {
		JComboBox<String> combo = new JComboBox<String>();
		for(Card card: board.getCardsList()) {
			if(card.getCardType() == CardType.WEAPON)
				combo.addItem(card.getCardName());
		}
		return combo;
	}


	//set person and weapon for Guess Field in Control Panel
	private class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == personComboBox)
				guessPerson = personComboBox.getSelectedItem().toString();
			else
				guessWeapon = weaponComboBox.getSelectedItem().toString();
		}
	}

	// When submit button is pressed
	private class submitButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			guessText = guessPerson + ", "+ guessWeapon + ", " + guessRoom ;
			dispose();
		}
	}


	// When submit button is pressed
	private class cancelButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			guessText = null;
			guessPerson = null;
			guessWeapon = null;
			dispose();
		}
	}



	public String getGuessText() {
		return guessText;
	}


	//main 函数测试用的，合并逻辑后可以删除？
	public static void main(String[] args) {

		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		SuggestionGUI gui = new SuggestionGUI("Suggestion",board);
		gui.setVisible(true);
	}
}
