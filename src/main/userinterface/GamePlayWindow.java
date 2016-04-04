package userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import network.Client;

/**
 * Connected to the guiController and the components it is made of.
 * Holds contents of the main game screen, including the player cards,
 * button panels, and card hand scroller.
 * Contains setters for the entire game state.
 * @author Alisa Tkaczyk
 **/


public class GamePlayWindow extends JFrame{

	JFrame frame;
	PlayerCard playerCard0;
	PlayerCard playerCard1;
	PlayerCard playerCard2;
	PlayerCard playerCard3;
	PlayerCard playerCard4;
	CardHand hand;

	GUIController gui;
	JLabel tournamentColour;
	Color tColour;
	JPanel GameStats ;      
	GamePlayButtonPanel buttonPanel;
	JLabel tournamentNumber;
	JPanel upperPanel;
	JPanel lowerPanel;

	public GamePlayWindow(Client client){
		super(); 

		ImageIcon icon = new ImageIcon(this.getClass().getResource("Images/GameB.jpg"));
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance(1400, 1000,  java.awt.Image.SCALE_SMOOTH ) ; 
		icon = new ImageIcon( newimg );
		JLabel background = new JLabel(icon);
		
		background.setBounds(0,0,1400, 1000);
		setContentPane(background);

		gui = new GUIController(client);

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JLabel title = new JLabel ();
		ImageIcon icon2 =new ImageIcon(this.getClass().getResource("Images/ivanhoeheader.png"));
		Image img2 = icon2.getImage() ;  
		Image newimg2 = img2.getScaledInstance(400, 150,  java.awt.Image.SCALE_SMOOTH ) ; 
		icon2 = new ImageIcon( newimg2 );
		title.setIcon(icon2);

		ImageIcon icon3 =new ImageIcon(this.getClass().getResource("Images/tokenNew.png"));
		Image img3 = icon3.getImage() ;  
		Image newimg3 = img3.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH ) ; 
		icon3 = new ImageIcon( newimg3 );

		tournamentColour = new JLabel(icon3);
		tournamentColour.setFont(new Font("Century", Font.BOLD, 18));

		tournamentNumber = new JLabel();
		tournamentNumber.setFont(new Font("Century", Font.BOLD, 28));

		GameStats = new JPanel(new FlowLayout());
		GameStats.add(title);
		GameStats.add(tournamentNumber);
		GameStats.add(tournamentColour);
		GameStats.setOpaque(false);

		playerCard0 = new PlayerCard("INACTIVE");
		playerCard1 = new PlayerCard("INACTIVE");
		playerCard2 = new PlayerCard("INACTIVE");
		playerCard3 = new PlayerCard("INACTIVE");
		playerCard4 = new PlayerCard("INACTIVE");
		
		upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //new GridLayout(3,2));
		upperPanel.add(GameStats);
		upperPanel.add(playerCard0);
		upperPanel.add(playerCard1);
		upperPanel.add(playerCard2);
		upperPanel.add(playerCard3);
		upperPanel.add(playerCard4);
		
		buttonPanel = new GamePlayButtonPanel(gui);
		hand = new CardHand(gui); 
	
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout());
		lowerPanel.add(hand);
		lowerPanel.add(buttonPanel);
		
		upperPanel.setPreferredSize(new Dimension(playerCard0.getWidth()*2, (playerCard0.getHeight()*3)-30));
		lowerPanel.setPreferredSize(new Dimension(hand.getWidth()+buttonPanel.getWidth(), hand.getHeight() - 30));
		upperPanel.setOpaque(false);
		lowerPanel.setOpaque(false);
		
		add(upperPanel);
		add(lowerPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1400, 850); 
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}

	public void setAvatars(String avatars) {
		String[] avi = avatars.split(",");

		System.out.println("THIS IS ALL I HAVE FOR AVATARS" + avatars);
		for(int i = 0; i < avi.length; i++){
			if(i == 0) {
				playerCard0.setAvatar(avi[i]);
			}else if( i == 1) {
				playerCard1.setAvatar(avi[i]);
			}
			else if (i ==2){
				playerCard2.setAvatar(avi[i]);
			}
			else if (i ==3){
				playerCard3.setAvatar(avi[i]);
			}
			else if (i ==4){
				playerCard4.setAvatar(avi[i]);
			}
		}
		
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
	public void setCurrentPlayerName(String str){
		buttonPanel.setName(str);
	}
	public void setTournamentNumAndColour(String s) {
		String[] player = s.split(",");
		setTournamentColour(player[0]);
		tournamentNumber.setText("T" + player[1]);

	}
	public void setTournamentColour(String s){
		ImageIcon updatedIcon =  new ImageIcon(this.getClass().getResource("Images/tokenNew.png"));

		if (s.equals("-1")){			
		} else {
			if(s.equals("0")){
				tColour =new Color(107, 66, 130);
				updatedIcon =  new ImageIcon(this.getClass().getResource("Images/Tokens/purpleToken.png"));

			} else if(s.equals("1")){
				tColour = new Color(167, 63, 53);
				updatedIcon =  new ImageIcon(this.getClass().getResource("Images/Tokens/redToken.png"));

			} else if(s.equals("2")){
				tColour = new Color(160, 145, 112);
				updatedIcon =  new ImageIcon(this.getClass().getResource("Images/Tokens/goldToken.png"));

			} else if(s.equals("3")){
				tColour = new Color(143, 184, 142);
				updatedIcon =  new ImageIcon(this.getClass().getResource("Images/Tokens/greenToken.png"));

			} else if(s.equals("4")){ //, 62, 149);
				tColour =new Color(85, 110, 188);
				updatedIcon =  new ImageIcon(this.getClass().getResource("Images/Tokens/blueToken.png"));

			} 
		}
		tournamentColour.setIcon(updatedIcon);

	}
	public void setDrawCardButton(boolean b) {
		System.out.println("YOU MADE IT TO THE GAME PLAY WINDOW");
		//	buttonPanel.setDrawCardButton(b);

		System.out.println("THERE WAS SOMETHING CALLED ON BUTTON!");

	}

	public void emptyPlayerDisplay(int i) {
		if(i == 0) {
			playerCard0.emptyPlayerDisplay();
		}else if( i == 1) {
			playerCard1.emptyPlayerDisplay();
		}
		else if (i ==2){
			playerCard2.emptyPlayerDisplay();
		}
		else if (i ==3){
			playerCard3.emptyPlayerDisplay();
		}
		else if (i ==4){
			playerCard4.emptyPlayerDisplay();
		}
	}

	public void setPlayable(boolean b) {
		buttonPanel.setEnableOptionButtons(b);
		hand.setEnableHandButtons(b);
	}

	public void resetDrawCards(){
		buttonPanel.setDrawCardEnable(true);
		buttonPanel.setWithdrawEnable(true);
	}

}

//	public void setPlayerScreenEnabled(boolean b) {
//		//	panel.setEnabled(isEnabled);
//		java.awt.Component[] components = buttonPanel.getComponents();
//		java.awt.Component[] handComponents = hand.getComponents();
//		java.awt.Component[] handPanel = null;
//		java.awt.Component[] cards = null;
//		
//		for(int i = 0; i < components.length; i++) {
//			components[i].setEnabled(b);
//		}
////		for(int i = 0; i < handComponents.length; i++) {
////			System.out.println(handComponents[i].getName() + " IS AT POSITION " + i);
////			//if(handComponents[i] instanceof JScrollPane)
////			handPanel = ((JScrollPane) handComponents[0]).getComponents(); //setEnabled(b);
////			cards = ((java.awt.Container) handPanel[0]).getComponents();
////
////			System.out.println(" THIS MANY CARDS IT MUST BE SCROLL PANE " + cards.length);
////			for(int j = 0; j< cards.length; j ++){
////				cards[j].setEnabled(false);
////			}
//		}
//	}






