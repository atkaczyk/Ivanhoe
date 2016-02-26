package userinterface;


import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		playerStats.setLocation(20,20);
		
		ImageIcon a = new ImageIcon("Z:\\Ivanhoe\\3004_Iteration1\\src\\Images\\Avatar.jpg");
		JLabel avatar = new JLabel();
		avatar.setIcon(a);
		avatar.setSize(a.getIconWidth(),a.getIconHeight());
		avatar.setLocation(200, 20);
		

		display = new PlayerCardDisplay();

		display.add(new JButton(), Color.white);
		display.add(new JButton(), Color.gray);
		display.add(new JButton(), Color.black);
		display.setLocation(350,20);
		
		
		add(playerStats);
		add(avatar);
		add(display);

		setSize(600, 500); 

	}
	
	public void setPlayerCard(String playerName, int numToken, int score, boolean turn){
		setBorder(BorderFactory.createTitledBorder("playerName")); 
		playerStats = new PlayerCardStats(playerName, numToken, score, turn);
		add(playerStats);
		
	}
	
	public void setPlayerDisplay(){
		//for(all of the strings in the array, for each image add a new button)
		display.add(new JButton(), Color.white);
		add(display);
	}


}

