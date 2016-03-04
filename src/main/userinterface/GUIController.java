package userinterface;

import javax.swing.JOptionPane;

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

		client.handle("updateGameInformation");
		gamePlayWindow.setVisible(true);
	}
	public void launchTournamentColour(){
		Object[] possibilities = {"Purple", "Red", "Yellow", "Green", "Blue"};
		String s = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
		                    "Select your tournament colour\n If it is invalid, you will be asked to select it again",
		                    "Customized Dialog",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities,
		                    "colour");
		if(s.equals("Purple")){
			s = "0";
		} else if(s.equals("Red")){
			s = "1";
		} else if(s.equals("Yellow")){
			s = "2";
		} else if(s.equals("Green")){
			s = "3";
		} else if(s.equals("Blue")){
			s = "4";
		} 
		client.handle("TournamentColourRequest:" + s);
	}
	
	public void setTournamentColour(String s){ 
		gamePlayWindow.setTournamentColour(s);
	}
	
	public void displayTokenColour(int tokenColour){
		gameReadyWindow.setFinalToken(tokenColour);
	}	
	//String tempPlayersInfo = "GAMEINFORMATION~playerName,012,true,false, false,30,true(withdraw),false(this represents whether or not it is your turn)#Charge,Blue (Axe) 2,Red (Sword) 3@playerName,012,true,false, false,30,true#Charge,Blue (Axe) 2,Red (Sword) 3";
	public void setAllPlayersInfo(String str){ 
		String[] player = str.split("@");
		for (int i = 0; i < player.length; i++){
			String[] playerInfo = player[i].split("#");

			gamePlayWindow.setPlayerCardStats(i , playerInfo[0]);
			if(playerInfo.length == 1){}
			else {
				gamePlayWindow.setPlayerCardDisplay(i, playerInfo[1]);
			}
		}
	}
	public void showPlayerHand(String hand){
	//	System.out.println("RECIEVING THE CARD HAND >> " + hand);
		gamePlayWindow.hand.showCardsInHand(hand); //updateCardHand(hand);
	}
	public void sendTokenRequest(){
		client.handle("tokenRequest");
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
//	public void disablePlayButtons(String string) {
//		gamePlayWindow.buttonPanel.drawCardButton.setEnabled(false);
//		
//	}
}
