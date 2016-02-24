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

	public CardHand() {
		super();
		JScrollPane scrollPane = new JScrollPane();

		final JPanel compsToExperiment = new JPanel();
		compsToExperiment.setLayout(new FlowLayout());

		//compsToExperiment.setPreferredSize(new Dimension(400, 600));
		JButton finalToken = new JButton();//"This is the token you retrieved");
		finalToken.addActionListener(this);
		finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
		compsToExperiment.add(finalToken);
		finalToken = new JButton("Name");//"This is the token you retrieved");
		finalToken.addActionListener(this);
		finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
		compsToExperiment.add(finalToken);
		finalToken = new JButton();//"This is the token you retrieved");
		finalToken.addActionListener(this);
		finalToken.setName("This");
		finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
		compsToExperiment.add(finalToken);


		compsToExperiment.setSize(finalToken.getWidth(), finalToken.getHeight()*3);
		scrollPane.setViewportView(compsToExperiment);
		scrollPane.setHorizontalScrollBarPolicy(scrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(500, 225));
		this.add(scrollPane, BorderLayout.CENTER);
		JButton playCards = new JButton("Play Cards");

		playCards.addActionListener(this);
		this.add(playCards);

		setSize(100, 150); 
		setVisible(true);
	}

	public void updateHand(){

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("null")) {
			System.out.println("Withdraw");
		}
		else if (action.equals("Play Cards")) {
			System.out.println("Play Cards");
			//GamePlayWindow.playerCard0.display.add(new JButton(), Color.blue);

		}
		else if (action.equals("Name")) {
			System.out.println("Name");

		}
	}
}