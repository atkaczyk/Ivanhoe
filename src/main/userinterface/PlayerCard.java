package userinterface;


import java.awt.Color;
import java.awt.Dimension;
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
		setBorder(BorderFactory.createTitledBorder(" ")); 

		playerStats = new PlayerCardStats(name, 0, 0, false);
		playerStats.setLocation(15,15);
		playerStats.setPreferredSize(new  Dimension(100, 70));
		playerStats.setBackground(new Color(242, 202, 150));

		ImageIcon a = new ImageIcon(this.getClass().getResource("Images/Avatar.jpg"));
		JLabel avatar = new JLabel();
		avatar.setIcon(a);
		avatar.setSize(a.getIconWidth(),a.getIconHeight());
		avatar.setLocation(playerStats.getWidth(), 15);
		avatar.setBackground(new Color(242, 202, 150));

		display = new PlayerCardDisplay();

		display.setLocation(playerStats.getWidth()+ 10 +a.getIconWidth(),15);
		display.setBackground(new Color(242, 202, 150));
		add(playerStats);
		add(avatar);
		add(display);
		this.setBackground(new Color(119, 105,78));
		setSize(500, 150); 
	}
	//playerName,012,true,false, false,30,true(withdraw),false(this represents whether or not it is your turn)
	public void setPlayerStats(String playerInfo) {
		//		System.out.println("IN THE PLAYER CARD -> SETTING THE PLAYER STATS:     >> " + playerInfo);
		String[] stats = playerInfo.split(",");
		playerStats.setPlayerName(stats[0]);
		playerStats.setPlayerTokens(stats[1]);
		playerStats.setPlayerScore(Integer.parseInt(stats[5]));

		playerStats.setPlayerTurn(stats[7]);

		playerStats.setWithdrawn(stats[6]);

		//Doesn't Work For All Players
		if (stats[6].equals("true")){
			setBorder(BorderFactory.createTitledBorder("WITHDRAWN")); 
		} else if (stats[6].equals("false")){
			setBorder(BorderFactory.createTitledBorder(" ")); 
		}

		add(playerStats);
	}

	public void emptyPlayerDisplay(){
		display.emptyDisplay();
	}

	public void setPlayerDisplay(String str){ //take in a string and parse it and add the number you paRSE
		display.emptyDisplay();
		//System.out.println("IN THE PLAYER CARD -> SETTING THE DISPLAY:  >> " + str);
		if(str.isEmpty()){}
		else {
			String[] cards = str.split(",");

			if(cards.length < 1){
				display.add(new JButton(), Config.CARD_NAME_TO_PICTURES.get(cards[0]));
				//System.out.println("IN THE PLAYER CARD -> SETTING THE DISPLAY: CARDS[0]  >> " + cards[0]);
			}
			else{
				for(int i =0; i<cards.length; i++){
					display.add(new JButton(), Config.CARD_NAME_TO_PICTURES.get(cards[i]));
				}
				add(display);
			}
		}
	}
}

