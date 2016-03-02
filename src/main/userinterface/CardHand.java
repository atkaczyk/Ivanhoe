package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//import com.sun.prism.paint.Color;

public class CardHand extends JPanel implements ActionListener {
	JButton [] cards;

	GUIController guiController;
	JScrollPane scrollPane;
	JButton playCards;
	JButton donePlayingCards;
	JPanel panel;
	int counter;
	boolean maxCards = false;
	int numCards =5;
	String cardToSend;
	public CardHand() { //should take in the number of cards
		super();
		cardToSend = "";
		scrollPane = new JScrollPane();

		panel = new JPanel();
		panel.setLayout(new FlowLayout());

		scrollPane.setViewportView(panel);
		scrollPane.setHorizontalScrollBarPolicy(scrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		this.add(scrollPane, BorderLayout.CENTER);

		playCards = new JButton("Play Cards");
		playCards.addActionListener(this);
		this.add(playCards);
		
		donePlayingCards = new JButton("Done Drawing Cards");
		donePlayingCards.addActionListener(this);
		this.add(donePlayingCards);
		setSize(150, 300); 
		setVisible(true);
	}
	public void showCardsInHand(String cardsInHand){ //has to take in the arguments of the cards played
		
		String[] str = cardsInHand.split(",");
		cards = new JButton[str.length];//"This is the token you retrieved");
		
		for(int i = 0; i < str.length; i++) {
			cards[i] = new JButton();//"This is the token you retrieved");
			cards[i].setName(str[i]);
			ImageIcon icon = new ImageIcon(this.getClass().getResource("Cards/"+str[i]));
			Image img = icon.getImage() ;  
			Image newimg = img.getScaledInstance(150, 250,  java.awt.Image.SCALE_SMOOTH ) ; 
			
			icon = new ImageIcon( newimg );
			   
			cards[i].setIcon(icon);
			//cards[i].
			cards[i].addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e){
					//String cardName = ((JButton) e.getSource()).getName();
					if(maxCards == false){
						maxCards = true;
						((JButton) e.getSource()).setBackground(Color.BLACK);
						cardToSend += ((JButton) e.getSource()).getName();
						System.out.println("Play these cards: " + cardToSend);
					} else {
						if(((JButton) e.getSource()).getBackground() == Color.BLACK) {
							maxCards = false;
							cardToSend = cardToSend.replace(((JButton) e.getSource()).getName(), "");
							((JButton) e.getSource()).setBackground(Color.WHITE);
							System.out.println("Play these cards: " + cardToSend);
						} else {
							System.out.println("Play this card before selecting another");
						}
					}
				;}}); 

	//		cards[i].setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
			//return cards to send.
			panel.add(cards[i]);
			panel.setSize(cards[i].getWidth(), cards[i].getHeight()*3);
			scrollPane.setViewportView(panel);
			
	}
}
@Override
public void actionPerformed(ActionEvent e) {
	String action = e.getActionCommand();
	if (action.equals("Done Drawing Cards")) {
		System.out.println("Done Drawing Cards");
		//return the something? 
	}
	else if (action.equals("Play Cards")) {
		System.out.println("YOU JUST PLAYED " + cardToSend);
		guiController.sendCardToPlay(cardToSend);
	//	showCardsInHand(); //has to take in the information of the selected cards
		//getSelectedCards(); //and send them to the client.
	
	}
	else if (action.equals("Name")) {
		System.out.println("Name");

	}
	
}

}
