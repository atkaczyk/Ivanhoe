package userinterface;

import javax.swing.JTextField;

import network.Client;

public class GUIController {
	GameReadyWindow gameReadyWindow;
	GamePlayWindow gamePlayWindow;
	Client client;

	public GUIController(Client c){ 
		client = c;	
	}

	public void launchGameReadyWindow(){
		gameReadyWindow = new GameReadyWindow(client);
		gameReadyWindow.setVisible(true);
	}

	public void launchGamePlayWindow(){
		gameReadyWindow.setVisible(false);
		gamePlayWindow = new GamePlayWindow(client);
		gamePlayWindow.setVisible(true);
	}
	
	public void update(){
		gamePlayWindow.setPlayerCardStats(); //Should take in name, playerName, numToken, score, turn
		gamePlayWindow.setPlayerCardDisplay(); //this needs each players 
		gamePlayWindow.updateCardHand(); //should take in hand info
		
	}
	public void sendTokenRequest(){
		client.handle("tokenRequest");
	}

	public void setAllPlayersInfo(String str){

	}

	//retrieves all cards in the players hand
	public void showPlayerHand(String str){

	}

	public void sendClient(){
		//client.send(gameReadyWindow.getTokenRequest());
	}
	public void sendJoinGame(String playerName, int tokenColour) {
		client.handle("joinGame," + playerName + "," + tokenColour);

		System.out.println("This player just joined the game: " + playerName);
	}
	public void displayTokenColour(int tokenColour){
		System.out.println("The Token Colour is: " + tokenColour);
		gameReadyWindow.setFinalToken(tokenColour);
	}
	
}
