package network;

import java.io.Console;
import java.util.Scanner;

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

		System.out
				.println("Type \"startGame <number of players><number of rounds>\" to start the game.");

		do {
			String input = sc.nextLine();

			if (input.contains("startGame")) {
				int numOfPlayers = Integer.parseInt(input.split(" ")[1]);
				int numOfSessions = Integer.parseInt(input.split(" ")[2]);
				//server.getGame().start(numOfPlayers, numOfSessions);
				
				server.displayGameStart(numOfPlayers, numOfSessions);
			}

			if (input.equalsIgnoreCase("SHUTDOWN") && started) {
				System.out.println("Shutting server down ...");
				Trace.getInstance().close();
				server.shutdown();
				started = Boolean.FALSE;
				done = Boolean.TRUE;
			}
		} while (!done);

		System.exit(1);
	}
}
