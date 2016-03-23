package userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import network.Client;
import utils.Config;


public class GamePlayWindow extends JFrame{

	JFrame frame;
	PlayerCard playerCard0;
	PlayerCard playerCard1;
	PlayerCard playerCard2;
	PlayerCard playerCard3;
	PlayerCard playerCard4;
	CardHand hand;

	PlayerCardDisplay	playerDisplay;
	GUIController gui;
	JLabel tournamentColour;
	Color tColour;
	JPanel GameStats ;
	GamePlayButtonPanel buttonPanel;
	//will hold the player cards
	//will hold the cardLayout discard pile: still to be made
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

		setLayout(new GridLayout(2,1)); 

		upperPanel = new JPanel(new GridLayout(3,2));
		
		GameStats = new JPanel(new FlowLayout());
		

		JLabel title = new JLabel ();
		ImageIcon icon2 =new ImageIcon(this.getClass().getResource("Images/ivanhoeheader.png"));
		Image img2 = icon2.getImage() ;  
		Image newimg2 = img2.getScaledInstance(400, 150,  java.awt.Image.SCALE_SMOOTH ) ; 
		icon2 = new ImageIcon( newimg2 );
		title.setIcon(icon2);
		
		tournamentColour = new JLabel("");
		tournamentColour.setBackground(Color.white);
		tournamentColour.setFont(new Font("Century", Font.BOLD, 18));
		
		tournamentNumber = new JLabel("");
		tournamentNumber.setFont(new Font("Century", Font.BOLD, 18));

		GameStats.add(title);
		GameStats.add(tournamentNumber);
		GameStats.add(tournamentColour);
		GameStats.setOpaque(false);
		GameStats.setBackground(new Color(242, 202, 150));
		
		upperPanel.add(GameStats);

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

		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout());

		buttonPanel = new GamePlayButtonPanel(gui);
		hand = new CardHand(gui); //"MY CARD HAND DISPLAY SCROLLER");
		lowerPanel.add(hand);
		lowerPanel.add(buttonPanel);
		lowerPanel.setBackground(new Color(242, 202, 150));
		lowerPanel.setPreferredSize(new Dimension(hand.getHeight(), hand.getWidth()+buttonPanel.getWidth()));
		
		upperPanel.setOpaque(false);
		add(upperPanel);
		lowerPanel.setOpaque(false);
		add(lowerPanel);
		
		// Set program to stop when window closed
		//this.setBackground(new Color(242, 202, 150));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//pack();
		setSize(1400, 1000); // manually computed sizes
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		//setResizable(true);
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
		tournamentNumber.setText("Tournament #" + player[1] + " is...");

	}
	public void setTournamentColour(String s){
		if (s.equals("-1")){
			tColour = Color.BLACK;
		} else {
			if(s.equals("0")){
				tColour =new Color(107, 66, 130);
			} else if(s.equals("1")){
				tColour = new Color(167, 63, 53);
			} else if(s.equals("2")){
				tColour = new Color(160, 145, 112);
			} else if(s.equals("3")){
				tColour = new Color(143, 184, 142);
			} else if(s.equals("4")){ //, 62, 149);
				tColour =new Color(85, 110, 188);
			} 
			tournamentColour.setText(Config.TOKEN_COLOUR_NAMES[Integer.parseInt(s)]);	
		}
		tournamentColour.setForeground(tColour);
		tournamentColour.setBackground(tColour);
		tournamentNumber.setBackground(tColour);
		tournamentNumber.setForeground(tColour);

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



/* 
 * FOR LATER TO DISABLE ONLY CARD BUTTONS
 * 
 * public List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container)
                compList.addAll(getAllComponents((Container) comp));
        }
        return compList;

        ---

        List<Component> comps = getAllComponents(panel);
for (Component comp : comps) {
       comp.setEnabled(false);
}
 * 
 */



