package clueGame;

public enum CardType {

	ROOM("Room"), PERSON("Person"), WEAPON("Weapon");
	private String value;
	
	CardType(String value) {
		this.value = value;

	}

	public String toString() {
		return value;
	}
	
	
	
	
}
