package userinterface;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePlayButtonPanel extends JPanel implements ActionListener{

	GamePlayButtonPanel() {
		JButton button;
		JButton withdrawButton;
		JButton endTurnButton;
		JButton drawCardButton;

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();


		button = new JButton("Discard Pile");
		c.fill = GridBagConstraints.HORIZONTAL;

		c.ipady = 120;      //make this component tall
		c.weightx = 0.5;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 0;
		add(button, c);

		withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(this);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;
		c.weightx = 0.5;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 0;
		add(withdrawButton, c);

		endTurnButton = new JButton("Announce End of Turn");
		endTurnButton.addActionListener(this);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;      //make this component tall
		c.weightx = 0.0;
		c.gridx = 1;
		c.gridy = 1;
		add(endTurnButton, c);

		drawCardButton = new JButton("Draw a Card");
		drawCardButton.addActionListener(this);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0;
		c.gridx = 1;
		c.gridy = 2;
		add(drawCardButton, c);

		setSize(200, 100); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Withdraw")) {
			System.out.println("Withdraw");
		}
		else if (action.equals("Draw a Card")) {
			System.out.println("DrawCard");
			GamePlayWindow.playerCard1.display.add(new JButton(),  "blue2.jpg");

		}
		else if (action.equals("Announce End of Turn")) {
			System.out.println("End of Turn");

		}


	}



}
