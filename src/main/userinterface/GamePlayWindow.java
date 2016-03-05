package userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		
		setLayout(new GridLayout(2,1)); 

		JPanel upperPanel = new JPanel(new GridLayout(3,2));
		tournamentColour = new JLabel("Ivanhoe ********** Tournament Colour **********");
		tournamentColour.setBackground(Color.white);
		upperPanel.add(tournamentColour);
	
		playerCard0 = new PlayerCard("INACTIVE");
		upperPanel.add(playerCard0);

		playerCard1 = new PlayerCard("INACTIVE");
		upperPanel.add(playerCard1);

		playerCard2 = new PlayerCard("INACTIVE");
		upperPanel.add(playerCard2);

		playerCard3 = new PlayerCard("INACTIVE");
		upperPanel.add(playerCard3);

		playerCard4 = new PlayerCard("INACTIVE");
		upperPanel.add(playerCard4);
		add(upperPanel);
		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout());
		
		buttonPanel = new GamePlayButtonPanel(gui);
		hand = new CardHand(gui); //"MY CARD HAND DISPLAY SCROLLER");
		lowerPanel.add(hand);
		lowerPanel.add(buttonPanel);
		lowerPanel.setBackground(Color.green);
		lowerPanel.setPreferredSize(new Dimension(hand.getHeight(), hand.getWidth()+buttonPanel.getWidth()));
		System.out.println(lowerPanel.getHeight() + ", " + lowerPanel.getWidth());
		System.out.println(hand.getHeight() + ", " + hand.getWidth()+buttonPanel.getWidth());
		add(lowerPanel);
		
		// Set program to stop when window closed
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1400, 1000); // manually computed sizes
		setResizable(true);
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
		tournamentColour.setForeground(tColour);
	}

	public JPanel getButtonPanel() {
		return buttonPanel;
	}

	public void setDrawCardButton(boolean b) {
		System.out.println("YOU MADE IT TO THE GAME PLAY WINDOW");
		buttonPanel.setDrawCardButton(b);

		System.out.println("THERE WAS SOMETHING CALLED ON BUTTON!");
		
	}

}
