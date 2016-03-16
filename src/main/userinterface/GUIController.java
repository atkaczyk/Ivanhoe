package userinterface;

import javax.swing.JOptionPane;

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
	public void setTournamentInfo(String s) {
		gamePlayWindow.setTournamentNumAndColour(s);
	}
	public void setTournamentColour(String s) {
		gamePlayWindow.setTournamentColour(s);
	}
	public void displayTokenColour(int tokenColour){
		gameReadyWindow.setFinalToken(tokenColour);
	}	
	//String tempPlayersInfo = "GAMEINFORMATION~playerName,012,true,false, false,30,true(withdraw),false(this represents whether or not it is your turn)#Charge,Blue (Axe) 2,Red (Sword) 3@playerName,012,true,false, false,30,true#Charge,Blue (Axe) 2,Red (Sword) 3";
	public void setAllPlayersInfo(String str){ 
		String[] player = str.split("@");
		for (int i = 0; i < player.length; i++){	
			String [] playerInfo = player[i].split("#");

			gamePlayWindow.setPlayerCardStats(i , playerInfo[0]);
			if(playerInfo.length == 1){
				gamePlayWindow.emptyPlayerDisplay(i);
			}else {
				gamePlayWindow.setPlayerCardDisplay(i, playerInfo[1]);
			}
		}
	}
	public void setCurrentPlayerName(String str){
		gamePlayWindow.setCurrentPlayerName(str);
	}
	public void showPlayerHand(String hand){
		gamePlayWindow.hand.showCardsInHand(hand); 
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
		//disableDrawCardButton
	}
	public void requestToEndTurn() {
		client.handle("requestToEndTurn:");	
	}
	public void requestToWithdraw() {
		client.handle("requestToWithdraw:");	
	}
	public void displayTournamentWinner(String s) {
		String[] player = s.split(",");
		JOptionPane.showMessageDialog(gamePlayWindow, "CONGRATULATIONS TO : " + player[0] + " THEY WON THE " + Config.TOKEN_COLOUR_NAMES[Integer.parseInt(player[2])] + " TOURNAMENT #" + player[1]);
		client.handle("finalWinnerCheck");	
	}
	public void displayFinalWinner(String name) {
		JOptionPane.showMessageDialog(gamePlayWindow, "CONGRATULATIONS TO : " + name + " THEY WON THE ENTIRE GAME!!!");
		gamePlayWindow.setVisible(false);
		gamePlayWindow.dispose();
		client.handle("quit!");
	}
	public void setEnableMainScreen(String str){
		if(str.equals("true")){
			gamePlayWindow.setEnabled(true);//setPlayerScreenEnabled(true); //setEnabled(true);
		}else if(str.equals("false")){
			gamePlayWindow.setEnabled(false);
		}
	}

	public void getActionCardInfo(String info){
		String[] cardInfo = info.split("@");
		if(cardInfo[0].equals("Riposte")){
			playRiposte(cardInfo[1]);
		} 
		else if (cardInfo[0].equals("Unhorse")){
			playUnhorse(cardInfo[1]);
		}  
		else if (cardInfo[0].equals("Change Weapon")){
			playChangeWeapon(cardInfo[1]);
		}  
		else if (cardInfo[0].equals("Break Lance")){
			playBreakLance(cardInfo[1]);
		}  
		else if (cardInfo[0].equals("Dodge")){
			playDodge(cardInfo[1]);
		}  
		else if (cardInfo[0].equals("Retreat")){
			playRetreat(cardInfo[1]);
		}  
		else if (cardInfo[0].equals("Knock Down")){
			playKnockDown(cardInfo[1]);
		}  
		else if (cardInfo[0].equals("Outwit")){
			playOutwit(cardInfo[1]);
		}  
		else if (cardInfo[0].equals("Adapt")){
			//playRiposte(cardInfo[1]);
		}  
		else if (cardInfo[0].equals("Shield")){
			//playRiposte(cardInfo[1]);
		}  
		else if (cardInfo[0].equals("Stunned")){
			//	playRiposte(cardInfo[1]);
		}  
		else if (cardInfo[0].equals("Ivanhoe")){
			//	playRiposte(cardInfo[1]);
		} 
	}
	private void playOutwit(String string) {

	}
	private void playKnockDown(String string) {
		// TODO Auto-generated method stub

	}
	private void playRetreat(String string) {
		// TODO Auto-generated method stub

	}
	private void playDodge(String string) {
		// TODO Auto-generated method stub

	}
	private void playBreakLance(String msg) {
		String[] p = msg.split(",");
		String chosenName = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Break Lance Card!\n You may take the last card of any opponents display, and add it to your own display \n"
				+ "Select the player you want to Steal from.",
				"Break Lance",
				JOptionPane.QUESTION_MESSAGE,
				null,
				p,
				"Player[Card]");
		client.handle("actionInfoGathered~Break Lance@"+ chosenName);
	}
	private void playChangeWeapon(String tokens) {
		String temp = null;
		if(tokens.contains("0")){
			temp += "Purple,";
		}
		if(tokens.contains("1")){
			temp  += "Red,";
		}
		if(tokens.contains("2")){
			temp  += "Yellow,";
		}
		if(tokens.contains("3")){
			temp  += "Green,";
		}
		if(tokens.contains("4")){
			temp  += "Blue";
		} 
		
		String[] options = temp.split(",");
		String chosenColour = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Change Weapon Card!\n Change the tournament colour from red, blue or yellow to a different one of these colours.\n"
				+ "Select the colour you want to change to.",
				"Change Weapon",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				"Colours");
		client.handle("ChangeWeapon@"+ chosenColour);
	}
	private void playUnhorse(String tokens) {
		String temp = null;
		if(tokens.contains("0")){
			temp += "Purple,";
		}
		if(tokens.contains("1")){
			temp  += "Red,";
		}
		if(tokens.contains("2")){
			temp  += "Yellow,";
		}
		if(tokens.contains("3")){
			temp  += "Green,";
		}
		if(tokens.contains("4")){
			temp  += "Blue";
		} 
		
		String[] options = temp.split(",");
		String chosenColour = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Unhorse Card!\n Change the tournament colour, from purple to red blue or yellow.\n"
				+ "Select the colour you want to change to.",
				"Unhorse",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				"Colours");
		client.handle("actionInfoGathered~Unhorse@"+ chosenColour);
	}
	public void playRiposte(String msg){
		String[] p = msg.split(",");
		/*String pInfo = null;
				for (int i = 0; i< p.length; i++){
			pInfo = p[i].split("[");			
		}
		 
		//temp = temp.substring(0, temp.length()-1);
		String[] options = p; //.split(" ");*/
		String chosenName = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Riposte Card!\n You may take the last card of any opponents display, and add it to your own display \n"
				+ "Select the player you want to Steal from.",
				"Riposte",
				JOptionPane.QUESTION_MESSAGE,
				null,
				p,
				"Player[Card]");
		client.handle("actionInfoGathered~Riposte@"+ chosenName);
	}

	public void actionCardPlayedMessage(String cardnamecommaplayer){
		String[] player = cardnamecommaplayer.split(",");
		JOptionPane.showMessageDialog(gamePlayWindow,  "THE ACTION CARD "+ player[0] + " WAS PLAYED BY " + player[1]);
	}
	public void displayErrorMessage(String msg) {
		JOptionPane.showMessageDialog(gamePlayWindow, msg);

	}
	//This gets called when the player announces the end of their turn or disables the card button 
	public void disableDrawCardButton() {
		//gamePlayWindow.setDrawCardButton(false);// WHY THE NULL POINTER EXCEPTION?
		System.out.println("disaBLE THE FUCKING DRAW CARD BUTTON");
	}
	public void launchPurpleWinTokenChoice(String s){
		String temp = "";
		String rets = "";
		if(s.contains("0")){
			temp  += "Purple ";
			//numTokens.setForeground(Color.MAGENTA);
		}
		if(s.contains("1")){
			temp  += "Red ";
			//tColour = Color.RED;
		}
		if(s.contains("2")){
			temp  += "Yellow ";
			//tColour = Color.YELLOW;
		}
		if(s.contains("3")){
			temp  += "Green ";
			//tColour = Color.GREEN;
		}
		if(s.contains("4")){
			temp  += "Blue ";
			//tColour = Color.BLUE;
		} 
		temp = temp.substring(0, temp.length()-1);
		String[] possibilities = temp.split(" ");
		rets = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You won the jousting (purple) tournament!\nSelect the token you want to collect.",
				"Select Token Colour",
				JOptionPane.QUESTION_MESSAGE,
				null,
				possibilities,
				"colour");

		if(rets.contains("Purple")){
			temp  = "0";
			//numTokens.setForeground(Color.MAGENTA);
		} else if(rets.contains("Red")){
			temp  = "1";
			//tColour = Color.RED;
		} else if(rets.contains("Yellow")){
			temp  = "2";
			//tColour = Color.YELLOW;
		} else if(rets.contains("Green")){
			temp  = "3";
			//tColour = Color.GREEN;
		} else if(rets.contains("Blue")){
			temp  = "4";
			//tColour = Color.BLUE;
		} 
		client.handle("PurpleWinTokenColourChoice~" + temp);
	}
}
