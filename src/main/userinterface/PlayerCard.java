package userinterface;


import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Config;

public class PlayerCard extends JPanel{

	// The player card will hold 3 panels
	// panel 1: labels for name, #tokens, score, holding(special card
	// panel 2: avatar 
	// panel 3: display of cards

	public PlayerCardDisplay display;
	PlayerCardStats playerStats;

	public PlayerCard(String name){
		setLayout(null); //new FlowLayout()); 
		setBorder(BorderFactory.createTitledBorder("Player Card")); 
		
		playerStats = new PlayerCardStats(name, 0, 0, false);
		//setPlayerStats("Shit", 1, 2, true);
		playerStats.setLocation(20,20);
		
		ImageIcon a = new ImageIcon("Z:\\Ivanhoe\\3004_Iteration1\\src\\Images\\Avatar.jpg");
		JLabel avatar = new JLabel();
		avatar.setIcon(a);
		avatar.setSize(a.getIconWidth(),a.getIconHeight());
		avatar.setLocation(200, 20);
		

		display = new PlayerCardDisplay();

		display.setLocation(350,20);
		
		
		add(playerStats);
		add(avatar);
		add(display);

		setSize(600, 500); 

	}
	//playerName,012,true,false, false,30,true(withdraw),false(this represents whether or not it is your turn)
	public void setPlayerStats(String playerInfo) { //{String playerName, String numToken, String score, String turn){
		//setBorder(BorderFactory.createTitledBorder("shittyier")); 
		String[] stats = playerInfo.split(",");
		playerStats.setPlayerName(stats[1]);
		playerStats.setPlayerTokens(Integer.parseInt(stats[1]));
		playerStats.setPlayerScore(Integer.parseInt(stats[5]));
		if (stats[7].equals("true")) {
		playerStats.setPlayerTurn(true);
		} else {
			playerStats.setPlayerTurn(false);	
		}
			
		
		//playerStats = new PlayerCardStats(playerName, numToken, score, turn);
		add(playerStats);
		
	}
	
	public void setPlayerDisplay(String str){ //take in a string and parse it and add the number you paRSE
		String[] cards = str.split(",");
		for(int i =0; i<cards.length; i++){
			display.add(new JButton(), Config.CARD_NAME_TO_PICTURES.get(cards[i]));
		}
	}


}

