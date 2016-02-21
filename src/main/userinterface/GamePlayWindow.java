package userinterface;

//import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;


public class GamePlayWindow extends JFrame{

	JFrame frame ;
	static PlayerCard playerCard0;
	PlayerCard playerCard1;
	PlayerCard playerCard2;
	PlayerCard playerCard3;
	PlayerCard playerCard4;
	
	PlayerCardDisplay	playerDisplay;
	//will hold the player cards
	//will hold the cardLayout discard pile: still to be made

	public GamePlayWindow(){
		super(); 

		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		JButton button;
		GamePlayButtonPanel buttonPanel;
		
		button = new JButton("Ivanhoe");
		
		c.weightx =1;
		c.weighty =1;
		c.ipadx = 100;
		c.gridx = 0;
		c.gridy = 0;
		add(button, c);

		button = new JButton("Game States");
		c.weighty =0.0;
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
		c.ipady = 100;
		c.weightx = 0.0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 3;
		add(buttonPanel, c);
		
		button = new JButton("MY CARD HAND DISPLAY SCROLLER");
		c.ipady = 300;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 4;
		add(button, c);

		// Set program to stop when window closed
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1500, 1000); // manually computed sizes
		setResizable(true);
	}
}
