package userinterface;

//import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

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
	//will hold the player cards
	//will hold the cardLayout discard pile: still to be made

	public GamePlayWindow(Client client){
		super(); 

		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		JButton button;
		GamePlayButtonPanel buttonPanel;
		
		button = new JButton("Ivanhoe");
		
		c.weightx =1;
		c.weighty =1;
		c.ipadx = 10;
		c.ipady= 0;
		c.gridx = 0;
		c.gridy = 0;
		add(button, c);

		button = new JButton("Game States");
		c.weighty =1;
		c.gridx = 2;
		c.gridy = 0;
		add(button, c);
		
		playerCard0 = new PlayerCard("Still the Greatest");

		c.ipadx = 500;
		c.ipady = 200;
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		add(playerCard0, c);

		playerCard1 = new PlayerCard("Alisa the Great")	;
		//c.ipadx = 300;
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 2;
		c.gridy = 1;
		add(playerCard1, c);

		playerCard2 = new PlayerCard("Alisa")	;
		button = new JButton("Player Card 3");
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		add(playerCard2, c);


		playerCard3 = new PlayerCard("Great")	;
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 2;
		c.gridy = 2;
		add(playerCard3, c);


		playerCard4 = new PlayerCard("MY Player Card");
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 3;
		add(playerCard4, c);
		
		buttonPanel = new GamePlayButtonPanel();
		c.ipadx = 100;
		c.ipady = 80;
		c.weightx = 0.0;
		c.weighty =1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 3;
		add(buttonPanel, c);
		
		hand = new CardHand(); //"MY CARD HAND DISPLAY SCROLLER");
		c.ipady = 300;      //change back to 200?
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
	public void setPlayerCardStats(String playerInfo) { //String playerName, String numToken, String score, String turn) {
		playerCard0.setPlayerStats(playerInfo);
		playerCard1.setPlayerStats(playerInfo);
		playerCard2.setPlayerStats(playerInfo);
		playerCard3.setPlayerStats(playerInfo);
		playerCard4.setPlayerStats(playerInfo);
	}

	public void setPlayerCardDisplay(String str) {
		System.out.println("trying to set the player card display");
		playerCard0.setPlayerDisplay(str); //"NAME", 5, 10, false);
		playerCard1.setPlayerDisplay("green1.jpg");
		playerCard2.setPlayerDisplay("purple3.jpg");
		playerCard3.setPlayerDisplay("purple3.jpg");
		playerCard4.setPlayerDisplay("purple3.jpg");
	}

	public void updateCardHand(String cardsInHand){
		// TODO Auto-generated method stub
		hand.showCardsInHand( cardsInHand);
		
	}
}
