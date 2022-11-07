package clueGame;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer(String name, String strColor, int row, int column) {
		super(name, strColor, row, column);
	}

	// Given a room, the computer player will create a suggestion composed of that room, a weapon and player from those cards the computer player has not seen.
	public Solution createSuggestion(Room room) {

		int randomPerson = (int)(Math.random() * getUnseenPeople().size());
		int randomWeapon = (int)(Math.random() * getUnseenWeapons().size());

		// Get random person and weapon
		String roomName = room.getName();
		String personName = getUnseenPeople().get(randomPerson).getCardName();
		String weaponName = getUnseenWeapons().get(randomWeapon).getCardName();

		Card roomCard = new Card(CardType.ROOM, roomName);
		Card personCard = new Card(CardType.PERSON, personName);
		Card weaponCard = new Card(CardType.WEAPON, weaponName);

		Solution suggestion = new Solution(roomCard, personCard, weaponCard);
		return suggestion;
	}

	// If a target is in a room and the room is not in that player's seen list, select the room (or if multiple rooms select randomly).
	// Otherwise, select a target randomly from the target list.
	public BoardCell selectTarget(Set<BoardCell> targets, Map<Character, Room> roomMap) {
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<BoardCell> roomTargets = new ArrayList<BoardCell>();
		String roomName;

		// Get unseen rooms
		for (Card card : getUnseenRooms()) {
			roomName = card.getCardName();
			nameList.add(roomName);

		}

		// Get valid targets
		for (BoardCell cell : targets) {
			if (cell.isRoomCenter()) {
				roomName = roomMap.get(cell.getInitial()).getName();
				if (nameList.contains(roomName)) {
					roomTargets.add(cell);
				}
			}
		}

		// rooms can be selected
		if (roomTargets.size() != 0) {
			int randomRoom = (int)(Math.random() * roomTargets.size()); 
			return roomTargets.get(randomRoom);
		}
		// room can not be selected
		else {
			int randomRoom = (int)(Math.random() * targets.size()); 
			ArrayList<BoardCell> targetsList = new ArrayList<BoardCell>();
			targetsList.addAll(targets);
			return targetsList.get(randomRoom);

		}

	}
}
