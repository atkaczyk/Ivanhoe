package userinterface;

import network.Client;

public class GUIController {
	GameReadyWindow gameReadyWindow;
	GamePlayWindow gamePlayWindow;
	Client client;
	
	private boolean tokenRequest;

	public GUIController(Client c){

		client = c;
		boolean tokenRequest;		
		//gameReadyWindow = new GameReadyWindow(c);
		//gamePlayWindow = new GamePlayWindow(c);
	}

	public void launchGameReadyWindow(){
		GameReadyWindow startWindow = new GameReadyWindow(client);
		startWindow.setVisible(true);
		//do {			
		//} while(tokenRequest == false);g
		//tokenRequest = gameReadyWindow.getTokenRequest();
	}

	public void launchGamePlayWindow(){
		gameReadyWindow.setVisible(false);
		GamePlayWindow startWindow = new GamePlayWindow(client);
		startWindow.setVisible(true);
		gamePlayWindow.setVisible(true);
	}
	
	public void update(){
		gamePlayWindow.setPlayerCardStats();
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
		gameReadyWindow.setFinalToken(tokenColour);
	}
}
