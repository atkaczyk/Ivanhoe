package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import utils.Config;


/**
 * Contains JPanel that organizes the scroll pane and card handler
 * Sets and organizes contents of the individual player card hands,
 * @author Alisa Tkaczyk
 **/


public class CardHand extends JPanel implements ActionListener {
	JButton [] cards;

	GUIController gui;
	JScrollPane scrollPane;
	JButton playCards;
	JButton donePlayingCards;
	JPanel panel;
	JLabel curPlayerName;
	int counter;
	boolean maxCards = false;
	int numCards =5;
	String cardToSend;
	public CardHand(GUIController control) { 
		super();
		gui = control;
		cardToSend = "";
		scrollPane = new JScrollPane();

		panel = new JPanel();
		panel.setName("Cards Panel");
		panel.setLayout(new FlowLayout());
		panel.setBackground(new Color(137, 78, 72));
		panel.setOpaque(true);
		scrollPane.setViewportView(panel);
		scrollPane.setName("Scroll Pane");
		scrollPane.setHorizontalScrollBarPolicy(scrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(900, 220));
		scrollPane.setBackground(new Color(0, 0, 0));
		scrollPane.setOpaque(true);
		this.add(scrollPane, BorderLayout.CENTER);

		setSize(150, 200); 
		setVisible(true);
	}
	
	/**
	 * @param cardsInHand takes a string representing a list of the cards in the players hand
	 * this is parsed and adds each individual card to the hand one at a time
	 */
	public void showCardsInHand(String cardsInHand){ 
		panel.removeAll();
		String[] str = cardsInHand.split(",");
		cards = new JButton[str.length]; 

		for(int i = 0; i < cards.length; i++) {
			cards[i] = new JButton(); 
			cards[i].setName(str[i]);

			ImageIcon icon = new ImageIcon(this.getClass().getResource("Images/Cards/"+Config.CARD_NAME_TO_PICTURES.get(str[i])));
			Image img = icon.getImage() ;  
			Image newimg = img.getScaledInstance(130, 180,  java.awt.Image.SCALE_SMOOTH ) ; 
			icon = new ImageIcon( newimg );
			cards[i].setEnabled(true);
			cards[i].setIcon(icon);
			cards[i].setDisabledIcon(icon);
			cards[i].setContentAreaFilled(false);
			cards[i].setBorderPainted(false);
			cards[i].setOpaque(false);
			cards[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					gui.sendCardToPlay( ((JButton) e.getSource()).getName()); }}); 
			panel.add(cards[i]);
			panel.setSize(cards[i].getWidth(), cards[i].getHeight()*3);
			scrollPane.setViewportView(panel);			
		}
		this.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
	}
	
	public void setEnableHandButtons(boolean b){
		Component[] str = panel.getComponents();
		for (int i = 0; i < str.length; i++){
			str[i].setEnabled(b);
		}
	}
}