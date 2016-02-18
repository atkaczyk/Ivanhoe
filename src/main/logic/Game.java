package logic;

import utils.Config;

public class Game {
	private int numOfPlayers;
	private Player[] players;
	private int tokensPicked = 0;
	
	private int[] startingGameTokens = Config.ALL_TOKEN_COLOURS;
	
	public void setNumPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
		players = new Player[numOfPlayers];
		
		for (int i = 0; i < numOfPlayers; i++) {
			players[i] = new Player();
		}
	}

	public int getNumPlayers() {
		return numOfPlayers;
	}

	public void addPlayer(String playerName, int tokenColour) {
		for (int i=0; i<numOfPlayers; i++) {
			if (players[i].getName().equals("")) {
				players[i].setName(playerName);
			}
		}
	}

	public Player[] getPlayers() {
		return players;
	}
	
	public int getNextToken() {
		tokensPicked++;
		return startingGameTokens[tokensPicked-1];
	}

	public Player getStartingPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
}
