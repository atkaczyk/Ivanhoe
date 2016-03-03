package userinterface;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.JFrame;

import network.Client;
import utils.Config;

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

		client.handle("updateGameInformation");
		gamePlayWindow.setVisible(true);

	}
	//String tempPlayersInfo = "GAMEINFORMATION~playerName,012,true,false, false,30,true(withdraw),false(this represents whether or not it is your turn)#Charge,Blue (Axe) 2,Red (Sword) 3@playerName,012,true,false, false,30,true#Charge,Blue (Axe) 2,Red (Sword) 3";
	public void setAllPlayersInfo(String str){ 
		String[] player = str.split("@");

		for (int i = 0; i < player.length; i++){
			String[] playerInfo = player[i].split("#");

			gamePlayWindow.setPlayerCardStats(i , playerInfo[0]);
			if(playerInfo.length == 1){}
			else {
				gamePlayWindow.setPlayerCardDisplay(playerInfo[1]);
			}
		}
	}
	public void showPlayerHand(String hand){
	//	System.out.println("RECIEVING THE CARD HAND >> " + hand);
		gamePlayWindow.updateCardHand(hand); 
	}
	public void sendTokenRequest(){
		client.handle("tokenRequest");
	}
	public void displayTokenColour(int tokenColour){
		gameReadyWindow.setFinalToken(tokenColour);
	}
	public void sendJoinGame(String playerName, int tokenColour) {
		client.handle("joinGame," + playerName + "," + tokenColour);
	}
	public void sendCardToPlay(String cardToSend) { //cardToSend is filename, "charge.jpg" 
		client.handle("requestToPlayThisCard," + cardToSend);
	}
	public void requestToDrawCard() {
		client.handle("requestToDrawCard:");	
	}
	public void requestToEndTurn() {
		client.handle("requestToEndTurn:");	
	}
	public void requestToWithdraw() {
		client.handle("requestToWithdraw:");	
		
	}
}
