package userinterface;

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
		gamePlayWindow.setPlayerCardDisplay();
		gamePlayWindow.updateCardHand();
		
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

	public void sendGameReady() {
		client.handle("gameReady");
	}
	public void displayTokenColour(int tokenColour){
		System.out.println("The Token Colour is: " + tokenColour);
		gameReadyWindow.setFinalToken(tokenColour);
	}
}
