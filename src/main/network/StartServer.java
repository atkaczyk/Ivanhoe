package network;

import java.io.Console;
import java.util.Scanner;

import javax.swing.JOptionPane;

import network.Server;
import utils.Config;
import utils.Level;
import utils.Trace;

/**
 * 
 * @author Victoria Gray, Sophia Brandt, Alisa Tkaczyk
 * 
 * 1. Begin the game by running this StartServer class.
 * 2. It will call the Server class and prompt you to enter the port you would like it to run on.
 *
 */
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
		
		//Asking user for server port
		String s = (String)JOptionPane.showInputDialog(
                null,
                "Enter the Server Port (eg. 5050)",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);
		
		//If a string was returned, say so.
		//if ((s != null) && (s.length() > 0)) {
		//    setLabel("Green eggs and... " + s + "!");
		//    return;
		//}

		//If you're here, the return value was null/empty.
		//setLabel("Come on, finish the sentence!");
		
		//server = new Server(Config.DEFAULT_PORT); 		//cannot use default port. need to prompt user for the port
		server = new Server(Integer.parseInt(s)); 
		started = Boolean.TRUE;

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
				int numOfPlayers = Integer.parseInt(input);
				server.displayGameStart(numOfPlayers);
				server.getGame().setNumPlayers(numOfPlayers);
			}
			input = sc.nextLine();
			
		} while (!done);

		System.exit(1);
	}
}

