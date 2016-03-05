package network;

import java.io.Console;
import java.util.Scanner;

import javax.swing.JOptionPane;

import network.Server;
import utils.Config;
import utils.Level;
import utils.Trace;

public class StartServer {
	private static Boolean done = Boolean.FALSE;
	private static Boolean started = Boolean.FALSE;

	private static Scanner sc = new Scanner(System.in);
	private static Server server = null;

	public static void main(String[] argv) {

		// Use the IDE console if there is no system console
		Console c = System.console();
		if (c == null) {
			System.err.println("No System Console....");
			System.err.println("Use IDE Console....");
		}

		System.out.println("Starting server ...");
		Trace.getInstance().setLevel(Level.STDOUT);
		server = new Server(Config.DEFAULT_PORT);
		started = Boolean.TRUE;

		System.out.println("Type the number of players participating in the game.");

		String[] choices = { "2", "3", "4", "5" };
	    String numPlayers = "";
	    numPlayers = (String) JOptionPane.showInputDialog(null, "How many players will be in the game?",
	        "", JOptionPane.QUESTION_MESSAGE, null,
	        choices, // Array of choices
	        choices[0]); // Initial choice
		
	    String input = numPlayers;
	    
		do {
			if (input.equalsIgnoreCase("SHUTDOWN") && started) {
				System.out.println("Shutting server down ...");
				Trace.getInstance().close();
				server.shutdown();
				started = Boolean.FALSE;
				done = Boolean.TRUE;
			}

			else if (!numPlayers.equals("")) {
				System.out.println(numPlayers);
				int numOfPlayers = Integer.parseInt(input);
				server.displayGameStart(numOfPlayers);
				server.getGame().setNumPlayers(numOfPlayers);
			}
			input = sc.nextLine();
			
		} while (!done);

		System.exit(1);
	}
}

