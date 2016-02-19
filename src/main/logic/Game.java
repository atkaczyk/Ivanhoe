package logic;

import utils.Config;

public class Game {
	private int numOfPlayers;
	private Player[] players;
	
	// To keep track of whose turn it is
	private Player currentPlayer;
	
	// Needed to start the game
	private int playersRegistered = 0;
	private int tokensPicked = 0;
	private int[] startingGameTokens = Config.ALL_TOKEN_COLOURS;
	
	public void setNumPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
		players = new Player[numOfPlayers];
	}

	public int getNumPlayers() {
		return numOfPlayers;
	}

	public void addPlayer(String playerName, int tokenColour) {
		for (int i=0; i<numOfPlayers; i++) {
			if (players[i] == null) {
				players[i] = new Player();
				players[i].setName(playerName);
				
				if (tokenColour == Config.PURPLE) {
					currentPlayer = players[i];
				}
				break;
			}
		}
		
		playersRegistered++;
	}

	public Player[] getPlayers() {
		return players;
	}
	
	public int getNextToken() {
		tokensPicked++;
		return startingGameTokens[tokensPicked-1];
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Boolean isReadyToStart() {
		return numOfPlayers == playersRegistered;
	}

	public Object getTournamentNumber() {
		// TODO Auto-generated method stub
		return null;
	}
}
