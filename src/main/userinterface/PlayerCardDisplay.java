package userinterface;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class PlayerCardDisplay extends JPanel {
	private JLayeredPane lp;

	JButton top;
	JButton middle;
	JButton bottom;
	int numCardsInDisplay;
	int displaySize;

	public  PlayerCardDisplay() {
		super();
		displaySize = 100;
		numCardsInDisplay = 0; 

		lp = new JLayeredPane();
		lp.setPreferredSize(new Dimension(300, 300));

		this.add(lp);

		setSize(300, 150); 
	}

	public void add(JButton card, Color colour){
		numCardsInDisplay ++;
		card.setBackground(colour);
		card.setSize(80, 130);
		card.setBounds((numCardsInDisplay * 20), 0, card.getWidth(), card.getHeight());
		lp.add(card,  new Integer(numCardsInDisplay));

		System.out.println(numCardsInDisplay);
		this.add(lp);
	}

}