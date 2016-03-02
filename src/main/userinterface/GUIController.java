package userinterface;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.JFrame;

import network.Client;

public class GUIController {
	GameReadyWindow gameReadyWindow;
	GamePlayWindow gamePlayWindow;
	Client client;

	public GUIController(Client c){ 
		client = c;	
	}
	//	public static void main(String[] args){
	//		//JFrame frame = new GameReadyWindow();//client);
	//		//frame.setVisible(true);
	//		GUIController gui = new GUIController();
	//		gui.launchGamePlayWindow();
	//	}

	public void launchGameReadyWindow(){
		gameReadyWindow = new GameReadyWindow(client);
		gameReadyWindow.setVisible(true);
	}

	public void launchGamePlayWindow(){
		gameReadyWindow.setVisible(false);
		gamePlayWindow = new GamePlayWindow(client);

		client.handle("updateGameInformation");		//setAllPlayersInfo("lol");
		gamePlayWindow.setVisible(true);
		
	}

	public void update(){

	}

	/*
	 * in public String getDisplayAsString() { in the player class, I need more than just the name of the card,
	 I need the value as well possibly something else about it.. idk yet 
	 */
	//updating all the players information - including cardStats, Display, and Card Hand
	String tempPlayersInfo = "GAMEINFORMATION~@playerName,012,true,false, false,30,true#Charge,Blue (Axe) 2,Red (Sword) 3";
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

	public void setAllPlayersInfo(String str){ //everything is good here, just have to parse the msg string
		
		
		gamePlayWindow.setPlayerCardStats("player TEST", 000, 111, true); //Should take in name, playerName, numToken, score, turn

		// NOTE I GET: Charge,Blue (Axe) 2,Red (Sword) 3, I NEED: charge.jpg,blue2.jpg,red3.jpg
		gamePlayWindow.setPlayerCardDisplay("charge.jpg,blue2.jpg,red3.jpg"); //this needs each players 
		gamePlayWindow.updateCardHand("charge.jpg,blue2.jpg,blue3.jpg,blue4.jpg,blue5.jpg"); //should take in hand info
	}

	//retrieves all cards in the players hand
	public void showPlayerHand(String str){

	}

	//	public void sendClient(){
	//		client.send(gameReadyWindow.getTokenRequest());
	//	}


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
