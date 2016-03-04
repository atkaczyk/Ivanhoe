package userinterface;

//import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import network.Client;


public class GamePlayWindow extends JFrame{

	JFrame frame;
	static PlayerCard playerCard0;
	static PlayerCard playerCard1;
	PlayerCard playerCard2;
	PlayerCard playerCard3;
	PlayerCard playerCard4;
	CardHand hand;

	PlayerCardDisplay	playerDisplay;
	GUIController gui;
	JLabel tournamentColour;
	Color tColour;

	GamePlayButtonPanel buttonPanel;
	//will hold the player cards
	//will hold the cardLayout discard pile: still to be made

	public GamePlayWindow(Client client){
		super(); 
		gui = new GUIController(client);
		
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		JButton button;

		button = new JButton("Ivanhoe");

		c.weightx =1;
		c.weighty =1;
		c.ipadx = 10;
		c.ipady= 0;
		c.gridx = 0;
		c.gridy = 0;
		add(button, c);

		tournamentColour = new JLabel("********** Tournament Colour **********");
		tournamentColour.setBackground(Color.white);
		c.weighty =1;
		c.gridx = 2;
		c.gridy = 0;
		add(tournamentColour, c);

		playerCard0 = new PlayerCard("INACTIVE");
		c.weighty =.1;
		c.ipadx = 400;
		c.ipady = 165;
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		add(playerCard0, c);

		playerCard1 = new PlayerCard("INACTIVE");
		//c.ipadx = 300;
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 2;
		c.gridy = 1;
		add(playerCard1, c);

		playerCard2 = new PlayerCard("INACTIVE");
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		add(playerCard2, c);


		playerCard3 = new PlayerCard("INACTIVE");
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 2;
		c.gridy = 2;
		add(playerCard3, c);


		playerCard4 = new PlayerCard("INACTIVE");
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 3;
		add(playerCard4, c);

		buttonPanel = new GamePlayButtonPanel(gui);
		c.ipadx = 100;
		c.ipady = 80;
		c.weightx = 0.0;
		c.weighty =1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 3;
		add(buttonPanel, c);

		hand = new CardHand(gui); //"MY CARD HAND DISPLAY SCROLLER");
		c.ipady = 300;     
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 4;
		add(hand, c);

		// Set program to stop when window closed
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1500, 1000); // manually computed sizes
		setResizable(true);
	}

	//can now take in a string and parse to all players.
	public void setPlayerCardStats(int i, String playerInfo) { //String playerName, String numToken, String score, String turn) {
		if(i == 0) {
			playerCard0.setPlayerStats(playerInfo);
		}else if( i == 1) {
			playerCard1.setPlayerStats(playerInfo);
		}
		else if (i ==2){
			playerCard2.setPlayerStats(playerInfo);
		}
		else if (i ==3){
			playerCard3.setPlayerStats(playerInfo);
		}
		else if (i ==4){
			playerCard4.setPlayerStats(playerInfo);

		}
	}

	public void setPlayerCardDisplay(int i, String str) {
		if(i == 0) {
			playerCard0.setPlayerDisplay(str);
		}else if( i == 1) {
			playerCard1.setPlayerDisplay(str);
		}
		else if (i ==2){
			playerCard2.setPlayerDisplay(str);
		}
		else if (i ==3){
			playerCard3.setPlayerDisplay(str);
		}
		else if (i ==4){
			playerCard4.setPlayerDisplay(str);
		}
	}

	public void updateCardHand(String cardsInHand){
		hand.showCardsInHand(cardsInHand);

	}

	public void setTournamentColour(String s) {
		if(s.equals("0")){
			tColour = Color.MAGENTA;
		} else if(s.equals("1")){
			tColour = Color.RED;
		} else if(s.equals("2")){
			tColour = Color.YELLOW;
		} else if(s.equals("3")){
			tColour = Color.GREEN;
		} else if(s.equals("4")){
			tColour = Color.BLUE;
		}
		
		//System.out.println("I TRIED TO SET THE TOURNAMENT COLOUR TO: >>" + tColour );
		tournamentColour.setForeground(tColour);
	}

}
