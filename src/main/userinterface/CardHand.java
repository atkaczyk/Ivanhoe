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

public class CardHand extends JPanel implements ActionListener {
	JButton finalToken;
	JScrollPane scrollPane;
	JButton playCards;
	JPanel panel;

	public CardHand() {
		super();
		scrollPane = new JScrollPane();

		panel = new JPanel();
		panel.setLayout(new FlowLayout());

		finalToken = new JButton("Name");//"This is the token you retrieved");
		finalToken.addActionListener(this);
		finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
		panel.add(finalToken);

		panel.setSize(finalToken.getWidth(), finalToken.getHeight()*3);
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

	public void playCardsInHand(){ //has to take in the arguments of the cards played
		//for(the number of cards played
		finalToken = new JButton();//"This is the token you retrieved");
		finalToken.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e){
				System.out.println("You clicked the button");}}); 
		
		finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
		panel.add(finalToken);
		panel.setSize(finalToken.getWidth(), finalToken.getHeight()*3);
		scrollPane.setViewportView(panel);
	}
	
	public void getSelectedCards(){
	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(finalToken)) {
			System.out.println("Withdraw");
		}
		else if (action.equals("Play Cards")) {
			System.out.println("Play Cards");
			playCardsInHand(); //has to take in the information of the selected cards
			getSelectedCards(); //and send them to the client.
			//GamePlayWindow.playerCard0.display.add(new JButton(), Color.blue);

		}
		else if (action.equals("Name")) {
			System.out.println("Name");

		}
	}

}
