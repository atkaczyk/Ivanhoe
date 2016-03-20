package userinterface;

import javax.swing.JOptionPane;

import network.Client;
import utils.Config;

public class GUIController {
	GameReadyWindow gameReadyWindow;
	GamePlayWindow gamePlayWindow;
	Client client;
	private int mainScreenCounter = 0;

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
	//String tempPlayersInfo = "GAMEINFORMATION~playerName,012,true,false, false,30,true(withdraw),false(turn)#Charge,Blue (Axe) 2,Red (Sword) 3@playerName,012,true,false, false,30,true#Charge,Blue (Axe) 2,Red (Sword) 3";
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
			if(mainScreenCounter == 0) {
				System.out.println("THIS HAPPENS ONCE ZERO TO ONE" );
				mainScreenCounter =1;
				gamePlayWindow.resetDrawCards();
			}
			gamePlayWindow.setPlayable(true); //setEnabled(true);//setPlayerScreenEnabled(true); //setEnabled(true);

			gamePlayWindow.repaint();
		}else if(str.equals("false")){
			if(mainScreenCounter == 1) {
				mainScreenCounter =0;

				System.out.println("THIS HAPPENS ONCE ONE TO ZERO" );
			}
			gamePlayWindow.setPlayable(false);
			gamePlayWindow.repaint();
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
		else if (cardInfo[0].equals("Stunned")){
			playStunned(cardInfo[1]);
		} 
	}
	private void playStunned(String msg) {
		String[] p = msg.split(",");
		String chosenName = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Stunned Card!\n"
						+ "Play this card on any one opponent stil in the tournament, but separate from the opponent's display\n"
						+ "While the player is stunned, THEY may only add one new card to his display each turn"
						+ "Select the player whom you wish to Stun",
						"Stunned",
						JOptionPane.QUESTION_MESSAGE,
						null,
						p,
				"Players");
		client.handle("actionInfoGathered~Stunned@"+ chosenName);
	}

	public void adaptNeedMoreInfo(String msg) {
		String [] set = msg.split("_");
		String rets = "";
		for(int i=0; i<set.length; i++){
			String card = (String)JOptionPane.showInputDialog(
					gamePlayWindow,
					"Someone played the Adapt Card!\n"
							+ "\n"
							+ "Select the card you want to keep of value " + set[i].split("@")[0],
							"Outwit",
							JOptionPane.QUESTION_MESSAGE,
							null,
							set[i].split("@")[1].split(","),
					"Cards");
			rets+= set[i].split("@")[0] + "-" + card + ",";
		}
		client.handle("adaptGiveInfo@"+rets.substring(0,rets.length()-1));

	}
	private void playOutwit(String msg) {
		String [] myPlayerCards = msg.split("\\|")[0].split(","); //new String[15];
		String myChosenCard = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Outwit Card!\n"
						+ "Place one of your face up cards in front of an opponent, take one face up card from this opponent and place it face up in front of yourself. This may include SHEILD and STUNNED cards \n"
						+ "Select the card you want to swap",
						"Outwit",
						JOptionPane.QUESTION_MESSAGE,
						null,
						myPlayerCards,
				"Cards");	
		String[] playersInfo = msg.split("\\|")[1].split("#");	
		String [] pNames = new String[playersInfo.length];
		String [] pCards = new String[15]; 
		//get the players names in the pinfo string
		for(int i = 0; i< playersInfo.length; i++){
			pNames[i] = playersInfo[i].split("-")[0];
		}
		String chosenName = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Outwit Card!\n"
						+ "Place one of your face up cards in front of an opponent, take one face up card from this opponent and place it face up in front of yourself. This may include SHEILD and STUNNED cards \n"
						+ "Select the player you want to swap a card with.",
						"Outwit",
						JOptionPane.QUESTION_MESSAGE,
						null,
						pNames,
				"Player");
		for(int i = 0; i< playersInfo.length; i++){
			if(playersInfo[i].contains(chosenName)){
				pCards = playersInfo[i].split("-")[1].split(",");
			}
		}
		String chosenCard = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Outwit Card!\n"
						+ "And you chose to swap " + chosenName + "\n"
						+ "Select the card that you'd like to swap.",
						"Outwit",
						JOptionPane.QUESTION_MESSAGE,
						null,
						pCards,
				"Cards");
		client.handle("actionInfoGathered~Outwit@"+ myChosenCard + "," + chosenName +","+ chosenCard);
	}
	private void playDodge(String msg) {
		String[] playersInfo = msg.split("#");
		String [] pNames = new String[playersInfo.length];
		String [] pCards = new String[15]; //um just gonna make max possible cards per player 15
		//pick the player you wish to attack.
		for(int i = 0; i< playersInfo.length; i++){
			pNames[i] = playersInfo[i].split("-")[0];

		}

		String chosenName = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Dodge Card!\n"
						+ "You may discard any one card from any one opponent's display \n"
						+ "Select the player you want to choose a card from.",
						"Dodge",
						JOptionPane.QUESTION_MESSAGE,
						null,
						pNames,
				"Player");

		for(int i = 0; i< playersInfo.length; i++){
			if(playersInfo[i].contains(chosenName)){
				pCards = playersInfo[i].split("-")[1].split(",");
			}
		}

		String chosenCard = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Dodge Card!\n"
						+ "And you chose to steal from " + chosenName + "\n"
						+ "Select the card that you'd like to take.",
						"Dodge",
						JOptionPane.QUESTION_MESSAGE,
						null,
						pCards,
				"Player");

		client.handle("actionInfoGathered~Dodge@"+ chosenName +","+ chosenCard);
	}
	private void playKnockDown(String msg) {
		String[] p = msg.split(",");
		String chosenName = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Knock Down Card!\n"
						+ "You may draw at random one card from any one opponent's hand and add it to your own hand (without revealing the card)\n"
						+ "Select the player whom you wish to draw from",
						"Knock Down",
						JOptionPane.QUESTION_MESSAGE,
						null,
						p,
				"Players");
		client.handle("actionInfoGathered~Knock Down@"+ chosenName);
	}
	private void playRetreat(String msg) {
		String[] p = msg.split(",");
		String chosenCard = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Retreat Card!\n"
						+ "You may take any one card from your own display back into your hand.\n"
						+ "Select the card you wish to retreat.",
						"Retreat",
						JOptionPane.QUESTION_MESSAGE,
						null,
						p,
				"Cards");
		client.handle("actionInfoGathered~Retreat@"+ chosenCard);
	}
	private void playBreakLance(String msg) {
		String[] p = msg.split(",");
		String chosenName = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Break Lance Card!\n "
						+ "One opponent must discard all purple cards from his display \n"
						+ "Select the player to use this on",
						"Break Lance",
						JOptionPane.QUESTION_MESSAGE,
						null,
						p,
				"Players");
		client.handle("actionInfoGathered~Break Lance@"+ chosenName);
	}
	private void playChangeWeapon(String tokens) {
		String temp = "";
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
		client.handle("actionInfoGathered~Change Weapon@"+ convertNameToNumber(chosenColour));
	}
	private void playUnhorse(String tokens) {
		String temp = "";
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
		client.handle("actionInfoGathered~Unhorse@"+ convertNameToNumber(chosenColour));
	}
	private void playRiposte(String msg){
		String[] p = msg.split(",");
		/*String pInfo = null;
				for (int i = 0; i< p.length; i++){
			pInfo = p[i].split("[");			
		}

		//temp = temp.substring(0, temp.length()-1);
		String[] options = p; //.split(" ");*/
		String chosenName = (String)JOptionPane.showInputDialog(
				gamePlayWindow,
				"You played the Riposte Card!\n "
						+ "You may take the last card of any opponents display, and add it to your own display \n"
						+ "Select the player you want to Steal from.",
						"Riposte",
						JOptionPane.QUESTION_MESSAGE,
						null,
						p,
				"Player-Card-");
		String[] retVal = chosenName.split("-");
		client.handle("actionInfoGathered~Riposte@"+ retVal[0]);
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
		client.handle("PurpleWinTokenColourChoice~" + convertNameToNumber(rets));
	}
	private String convertNameToNumber(String chosenColour) {
		String temp = "";
		if(chosenColour.contains("Purple")){
			temp  = "0";
		} else if(chosenColour.contains("Red")){
			temp  = "1";
		} else if(chosenColour.contains("Yellow")){
			temp  = "2";
		} else if(chosenColour.contains("Green")){
			temp  = "3";
		} else if(chosenColour.contains("Blue")){
			temp  = "4";
		} 
		return temp;
	}
}
