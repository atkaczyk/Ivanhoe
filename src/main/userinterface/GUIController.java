package userinterface;

import network.Client;

public class GUIController {
	GameReadyWindow gameReadyWindow;
	GamePlayWindow gamePlayWindow;
	Client client;

	private boolean tokenRequest;

	public GUIController(){

		Client = new Client(5050, "127.0.0.1");
		boolean tokenRequest;		
		gameReadyWindow = new GameReadyWindow();
		gamePlayWindow = new GamePlayWindow();
	}

	public void launchGameReadyWindow(){
		GameReadyWindow startWindow = new GameReadyWindow();
		startWindow.setVisible(true);
		//do {			
		//} while(tokenRequest == false);g
		//tokenRequest = gameReadyWindow.getTokenRequest();
	}

	public void launchGamePlayWindow(){
		gameReadyWindow.setVisible(false);
		GamePlayWindow startWindow = new GamePlayWindow();
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
