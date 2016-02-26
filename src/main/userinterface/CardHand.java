package userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.prism.paint.Color;

public class CardHand extends JPanel implements ActionListener {
	JButton [] cards;

	JScrollPane scrollPane;
	JButton playCards;
	JPanel panel;
	int counter;
	int maxCards = 0;
	int numCards =5;
	String cardToSend;
	public CardHand() { //should take in the number of cards
		super();

		cardToSend = "";
		scrollPane = new JScrollPane();

		panel = new JPanel();
		panel.setLayout(new FlowLayout());

		cards = new JButton[numCards];//"This is the token you retrieved");
		//cards.addActionListener(this);
		cards[0] = new JButton("First");
		cards[0].setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
		panel.add(cards[0]);

		panel.setSize(cards[0].getWidth(), cards[0].getHeight()*3);
		scrollPane.setViewportView(panel);
		scrollPane.setHorizontalScrollBarPolicy(scrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(500, 225));
		this.add(scrollPane, BorderLayout.CENTER);

		playCards = new JButton("Play Cards");
		playCards.addActionListener(this);
		this.add(playCards);
		setSize(100, 150); 
		setVisible(true);
	}

	public void showCardsInHand(){ //has to take in the arguments of the cards played
		for(int i = 0; i < numCards; i++) {
			cards[i] = new JButton();//"This is the token you retrieved");
			cards[i].setName("card_" + i);
			cards[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){

					//if(((JButton) e.getSource()).getBackground() == Color.BLACK){
				//((JButton) e.getSource()).setBackground(Color.white);
					String cardName = ((JButton) e.getSource()).getName();
					//String regex = "\\s*\\b" + cardName + "\\b\\s*";
					cardToSend = cardToSend.replace(cardName, "");
					maxCards -=1;
					/*if(cardsToSend.contains(cardName.toLowerCase()))
					System.out.println(true);
				System.out.println("Play these cards (removed a card): " + cardsToSend);*/
					//} else {
					if (maxCards < 1) {
						//	((JButton) e.getSource()).setBackground(Color.BLACK);
						cardToSend += ((JButton) e.getSource()).getName();
						System.out.println("Play these cards: " + cardToSend);
						maxCards +=1;

					} else {
						System.out.println("Play this card before selecting another");

					}
					//						}

					;}}); 

			cards[i].setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
			panel.add(cards[i]);
			panel.setSize(cards[i].getWidth(), cards[i].getHeight()*3);
			scrollPane.setViewportView(panel);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(cards[0])) {
			System.out.println("Withdraw");
		}
		else if (action.equals("Play Cards")) {
			System.out.println("YOU JUST PLAYED " + cardToSend);
			showCardsInHand(); //has to take in the information of the selected cards
			//getSelectedCards(); //and send them to the client.
			//GamePlayWindow.playerCard0.display.add(new JButton(), Color.blue);

		}
		else if (action.equals("Name")) {
			System.out.println("Name");

		}
	}

}
