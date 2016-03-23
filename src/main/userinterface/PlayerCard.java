package userinterface;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

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
		setLayout(null); 
		setBorder(BorderFactory.createTitledBorder(" ")); 

		
		ImageIcon icon = new ImageIcon(this.getClass().getResource("Images/title.jpg"));
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance(500, 160,  java.awt.Image.SCALE_SMOOTH ) ; 
		icon = new ImageIcon( newimg );
		JLabel background = new JLabel(icon);
	
		background.setBounds(0,0, 500,160);
		//add(background);
		
		playerStats = new PlayerCardStats(name, 0, 0, false);
		playerStats.setLocation(15,15);
		playerStats.setPreferredSize(new  Dimension(130, 70));
		playerStats.setBackground(new Color(137, 78, 72));

		ImageIcon a = new ImageIcon(this.getClass().getResource("Images/Avatar.jpg"));
		JLabel avatar = new JLabel();
		avatar.setIcon(a);
		avatar.setSize(a.getIconWidth(),a.getIconHeight() + 5);
		avatar.setLocation(135, 15);
		avatar.setBackground(new Color(137, 78, 72));

		display = new PlayerCardDisplay();

		display.setLocation(185, 15 );//+ a.getIconWidth(),  15); //playerStats.getWidth()+ 10 +a.getIconWidth(),15);
		display.setBackground(new Color(137, 78, 72));
		add(playerStats);
		add(avatar);
		add(display);
		
	//	this.setBackground(new Color(137, 78, 72)); //0,0,0));
		setOpaque(false);
		//add(upperPanel);
		setSize(500, 160); 
		
	}
	//playerName,012,true,false, false,30,true(withdraw),false(this represents whether or not it is your turn)
	public void setPlayerStats(String playerInfo) {
		String[] stats = playerInfo.split(",");
		playerStats.setPlayerName(stats[0]);
		playerStats.setPlayerTokens(stats[1]);

		playerStats.setShield(stats[2]);
		playerStats.setStunned(stats[3]);
		playerStats.setIvanhoe(stats[4]);
		
		playerStats.setPlayerScore(Integer.parseInt(stats[5]));
		
		playerStats.setPlayerTurn(stats[7]);
		playerStats.setWithdrawn(stats[6]);

		add(playerStats);
		repaint();
	}

	public void emptyPlayerDisplay(){
		display.emptyDisplay();
	}

	public void setPlayerDisplay(String str){ //take in a string and parse it and add the number you paRSE
		display.emptyDisplay();
		if(str.isEmpty()){}
		else {
			String[] cards = str.split(",");
			if(cards.length < 1){
				display.add(new JLabel(), Config.CARD_NAME_TO_PICTURES.get(cards[0]));
			}
			else{
				for(int i =0; i<cards.length; i++){
					display.add(new JLabel(), Config.CARD_NAME_TO_PICTURES.get(cards[i]));
				}
				add(display);
			}
		}
		repaint();
	}
}

