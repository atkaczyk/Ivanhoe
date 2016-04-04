package userinterface;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import utils.Config;

/**
 * The player card holds two panels from separate classes 
 * including the playerCardStats, and playerCardDisplay	
 * // The player card will hold 3 panels
	// panel 1: labels for name, #tokens, score, holding(special card
	// panel 2: avatar 
	// panel 3: display of cards
 * @author Alisa Tkaczyk
 **/

public class PlayerCard extends JPanel{
	public JLayeredPane display;
	JLabel avatar;
	PlayerCardStats playerStats;
	private int numCardsInDisplay;

	public PlayerCard(String name){	
		ImageIcon icon = new ImageIcon(this.getClass().getResource("Images/ivanhoeheader.png"));
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance(500, 160,  java.awt.Image.SCALE_SMOOTH ) ; 
		icon = new ImageIcon( newimg );
		JLabel background = new JLabel(icon);

		background.setBounds(0,0, 500,160);
		//add(background);

		numCardsInDisplay= 0;

		//setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); 
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder(" ")); 
		playerStats = new PlayerCardStats(name, 0, 0, false);
		playerStats.setLocation(15,15);
		playerStats.setPreferredSize(new Dimension(130, 70));

		ImageIcon a = new ImageIcon(this.getClass().getResource("Images/Avatar.jpg"));
		avatar = new JLabel(a);
		avatar.setSize(a.getIconWidth(),a.getIconHeight() + 5);
		avatar.setLocation(playerStats.getWidth()+30, 15);
		avatar.setBackground(new Color(137, 78, 72));

		display = new JLayeredPane();

		display.setLocation(playerStats.getWidth()+avatar.getWidth()+45, 15 );
		display.setBackground(new Color(137, 78, 72));
		display.setSize(400, 130); 
		display.setPreferredSize(new Dimension(300, 150));
		add(playerStats);
		add(avatar);
		add(display);

		setOpaque(false);
		setSize(650, 160);
		setPreferredSize(new Dimension(650, 160)); 
	}

	/**
	 * Setting the players Game Stats by handling the input
	 * @param playerInfo which is a string containing the state of the player
	 */
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

	/** 
	 * removes all of the cards from a players display
	 */
	public void emptyPlayerDisplay(){
		numCardsInDisplay=0;
		display.removeAll();	
	}

	/**
	 * Setting the display by accessing the PlayerCardDisplay class object and initializing its cards.
	 * @param str of integers representing cards in the display
	 */
	public void setPlayerDisplay(String str){ 
		emptyPlayerDisplay();
		if(str.isEmpty()){}
		else {
			String[] cards = str.split(",");
			if(cards.length < 1){
				addCardToDisplay(new JLabel(), Config.CARD_NAME_TO_PICTURES.get(cards[0]));
			}
			else{
				for(int i =0; i<cards.length; i++){
					addCardToDisplay(new JLabel(), Config.CARD_NAME_TO_PICTURES.get(cards[i]));
				}
			}
		}
		//add(display);
		//repaint();
	}
	public void addCardToDisplay(JLabel card,String str){
		ImageIcon icon =new ImageIcon(this.getClass().getResource("Images/Cards/"+str));
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance(80, 125,  java.awt.Image.SCALE_SMOOTH ) ; 
		icon = new ImageIcon( newimg );
		card.setIcon(icon);
		card.setOpaque(false);
		card.setSize(80, 125);
		card.setBounds((numCardsInDisplay * 20), 0, card.getWidth(), card.getHeight());
		
		display.add(card, new Integer(numCardsInDisplay));
		numCardsInDisplay ++;
		//this.add(lp);
		this.repaint();
	}

	public void setAvatar(String aviName) {
		avatar.setIcon(new ImageIcon(this.getClass().getResource("Images/"+aviName+".jpg")));
	}
}
