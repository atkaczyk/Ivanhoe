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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import utils.Config;

//import com.sun.prism.paint.Color;

public class CardHand extends JPanel implements ActionListener {
	JButton [] cards;

	GUIController gui;
	JScrollPane scrollPane;
	JButton playCards;
	JButton donePlayingCards;
	JPanel panel;
	int counter;
	boolean maxCards = false;
	int numCards =5;
	String cardToSend;
	public CardHand(GUIController control) { //should take in the number of cards
		super();
		gui = control;
		cardToSend = "";
		scrollPane = new JScrollPane();

		panel = new JPanel();
		panel.setLayout(new FlowLayout());

		scrollPane.setViewportView(panel);
		scrollPane.setHorizontalScrollBarPolicy(scrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		this.add(scrollPane, BorderLayout.CENTER);

		playCards = new JButton("Play Card");
		playCards.addActionListener(this);
		this.add(playCards);

		//		donePlayingCards = new JButton("Done Drawing Cards");
		//		donePlayingCards.addActionListener(this);
		//		this.add(donePlayingCards);
		setSize(150, 300); 
		setVisible(true);
	}
	public void showCardsInHand(String cardsInHand){ 
		/* clear the hands before adding more */
		panel.removeAll();
		System.out.println("IN THE CARD HAND: THERE ARE THIS MANY PANEL COMPONENTS >> " + panel.getComponentCount());

		String[] str = cardsInHand.split(",");
		cards = new JButton[str.length]; 

		//System.out.println("IN SHOW CARDS IN HAND IN CARD HAND: RETRIEVING THIS IMAGE  >> " + Config.CARD_NAME_TO_PICTURES.get(str[0]));
		//System.out.println("IN CARD HAND >>" + cardsInHand);
		for(int i = 0; i < cards.length; i++) {
			cards[i] = new JButton(); 
			cards[i].setName(str[i]);
			/* GETTING NULL EXCEPTIONS BECAUSE MY HASHMAP DOES NOT HAVE SOME CARDS INCLUDING ... Maiden 1.. .etc */
			//System.out.println("IN CARD HAND >> TRYING TO SHOW THIS CARD >>Cards/"+Config.CARD_NAME_TO_PICTURES.get(str[i]));

			ImageIcon icon = new ImageIcon(this.getClass().getResource("Cards/"+Config.CARD_NAME_TO_PICTURES.get(str[i])));
			Image img = icon.getImage() ;  
			Image newimg = img.getScaledInstance(150, 250,  java.awt.Image.SCALE_SMOOTH ) ; 
			icon = new ImageIcon( newimg );
			cards[i].setEnabled(true);
			cards[i].setIcon(icon);
			cards[i].setContentAreaFilled(false);
			cards[i].setBorderPainted(false);
			cards[i].setOpaque(false);
			cards[i].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e){
					if(maxCards == false){
						maxCards = true;
						cardToSend += ((JButton) e.getSource()).getName();
						((JButton) e.getSource()).setEnabled(false); //setBackground(Color.BLACK);
						System.out.println("Play these cards: " + cardToSend);
					} else {
						if(((JButton) e.getSource()).getName().equals(cardToSend)) { //.getBackground() == Color.BLACK) {
							((JButton) e.getSource()).setEnabled(true);
							System.out.println("HELOOO I SHOULD BE ENABLED");
							maxCards = false;
							cardToSend = cardToSend.replace(((JButton) e.getSource()).getName(), "");
							 //setBackground(Color.WHITE);
							System.out.println("Play these cards: " + cardToSend);
						} else {
							System.out.println("Play this card before selecting another");
							//JOptionPane.showMessageDialog(, "Play this card before selecting another");
						}
					}
					;}}); 

			panel.add(cards[i]);
			panel.setSize(cards[i].getWidth(), cards[i].getHeight()*3);
			scrollPane.setViewportView(panel);
		}
		//scrollPane.setViewportView(panel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Done Drawing Cards")) {
			System.out.println("Done Drawing Cards");
		}
		else if (action.equals("Play Card")) {
			System.out.println("YOU JUST PLAYED " + cardToSend);
			maxCards = false;
			gui.sendCardToPlay(cardToSend); 
			//	gui.disablePlayButtons("Draw a Card");
			cardToSend = "";
		}
		else if (action.equals("Name")) {
			System.out.println("Name");

		}

	}

}
