package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// An exception (extends Exception) to indicate data files have bad format
public class BadConfigFormatException extends Exception {

	// default constructor
	public BadConfigFormatException() {
		super("File Format Is Not Correct!");
	}

	public BadConfigFormatException(String message) {
		super(message);
		
		// Handle FileNotFoundException
		try {
			// Open the file
			PrintWriter output = new PrintWriter("logfile.txt");
			// write into the file
			output.println(message);
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file doesn't exist!!!");
		}
	}
}
