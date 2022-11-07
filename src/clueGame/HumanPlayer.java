package clueGame;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, String strColor, int row, int column) {
		super(name, strColor, row, column);
		
	}

	@Override
	public BoardCell selectTarget(Set<BoardCell> targets, Map<Character, Room> roomMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Solution createSuggestion(Room room) {
		// TODO Auto-generated method stub
		return null;
	}


}
