package userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import network.Client;
import utils.Config;


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
	JPanel GameStats ;
	GamePlayButtonPanel buttonPanel;
	//will hold the player cards
	//will hold the cardLayout discard pile: still to be made
	JLabel tournamentNumber;

	public GamePlayWindow(Client client){
		super(); 
		gui = new GUIController(client);

		setLayout(new GridLayout(2,1)); 

		JPanel upperPanel = new JPanel(new GridLayout(3,2));
		GameStats = new JPanel(new FlowLayout());


		tournamentColour = new JLabel("**********");
		tournamentColour.setBackground(Color.white);

		tournamentNumber = new JLabel("Tournament Number ");
		//JPanel tournamentStuff = new JPanel();
		//tournamentStuff.add(tournamentColour);
		//tournamentStuff.add(tournamentNumber);

		JLabel title = new JLabel ();
		ImageIcon icon =new ImageIcon(this.getClass().getResource("Images/title.jpg"));
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance(400, 150,  java.awt.Image.SCALE_SMOOTH ) ; 
		icon = new ImageIcon( newimg );
		title.setIcon(icon);

		GameStats.add(title);
		GameStats.add(tournamentColour);
		GameStats.add(tournamentNumber);

		GameStats.setBackground(Color.getHSBColor(24, 240, 196));
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
	public void setCurrentPlayerName(String str){
		hand.setName(str);
	}
	public void setTournamentNumAndColour(String s) {
		String[] player = s.split(",");
		setTournamentColour(player[0]);
		tournamentNumber.setText("Its Tournament Number " + player[1]);
	}
	public void setTournamentColour(String s){
		if (s.equals("-1")){
			tColour = Color.BLACK;
		} else {
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
			tournamentColour.setText("This tournament Colour is " + Config.TOKEN_COLOUR_NAMES[Integer.parseInt(s)]);	
		}
		tournamentColour.setForeground(tColour);
	}
	public void setDrawCardButton(boolean b) {
		System.out.println("YOU MADE IT TO THE GAME PLAY WINDOW");
		buttonPanel.setDrawCardButton(b);

		System.out.println("THERE WAS SOMETHING CALLED ON BUTTON!");

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



