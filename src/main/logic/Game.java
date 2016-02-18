package logic;

public class Game {
	private int numOfPlayers;
	private Player[] players;
	
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

	public void addPlayer(String playerName) {
		for (int i=0; i<numOfPlayers; i++) {
			if (players[i].getName().equals("")) {
				players[i].setName(playerName);
			}
		}
	}

	public Player[] getPlayers() {
		return players;
	}

}
