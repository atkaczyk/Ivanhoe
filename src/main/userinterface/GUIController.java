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

	public void update(){

	}

	//updating all the players information - including cardStats, Display, and Card Hand
	public ArrayList<String[]> parseInfo(String str){
		ArrayList<String[]> result = new ArrayList<String[]>();

		str = str.split("~")[1];
		String[] parsed = str.split("@");

		for (int i=1;i<parsed.length;i++) {
			// this now contains all the player information
			String[] playerInfo = parsed[i].split(",");
			result.add(playerInfo);
		}
		return result;
	}

	String tempPlayersInfo = "GAMEINFORMATION~playerName,012,true,false, false,30,true(withdraw),false(this represents whether or not it is your turn)#Charge,Blue (Axe) 2,Red (Sword) 3@playerName,012,true,false, false,30,true#Charge,Blue (Axe) 2,Red (Sword) 3";
	public void setAllPlayersInfo(String str){ //everything is good here, just have to parse the msg string
		String[] player = str.split("@");

		for (int i = 0; i < player.length; i++){
			String[] playerInfo = player[i].split("#");

			gamePlayWindow.setPlayerCardStats(playerInfo[0]);
			gamePlayWindow.setPlayerCardDisplay(playerInfo[1]);

		}
	}
	//retrieves all cards in the players hand // Charge,Blue (Axe) 2,Red (Sword) 3
	public void showPlayerHand(String str){
		gamePlayWindow.updateCardHand("charge.jpg,blue2.jpg,blue3.jpg,blue4.jpg,blue5.jpg"); //should take in hand info

	}

	public void sendTokenRequest(){
		client.handle("tokenRequest");
	}
	public void displayTokenColour(int tokenColour){
		System.out.println("The Token Colour is: " + tokenColour);
		gameReadyWindow.setFinalToken(tokenColour);
	}
	public void sendJoinGame(String playerName, int tokenColour) {
		client.handle("joinGame," + playerName + "," + tokenColour);

		System.out.println("This player just joined the game: " + playerName);
	}

	public void sendCardToPlay(String cardToSend) { //cardToSend is filename, "charge.jpg" 
		client.handle("requestToPlayThisCard," + cardToSend);

	}
}
