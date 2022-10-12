package clueGame;

// An exception (extends Exception) to indicate data files have bad format
public class BadConfigFormatException extends Exception {

	// default constructor
	public BadConfigFormatException() {
		super("File Format Is Not Correct!");
	}

	public BadConfigFormatException(String message) {
		super(message);
	}
}
