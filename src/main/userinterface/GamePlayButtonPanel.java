package userinterface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePlayButtonPanel extends JPanel implements ActionListener{

	GUIController gui;

	JButton button;
	JButton withdrawButton;
	JButton endTurnButton;
	JButton drawCardButton;
	GamePlayButtonPanel(GUIController gui1) {
		gui = gui1;
		JButton button;
		JButton withdrawButton;
		JButton endTurnButton;
		JButton drawCardButton;

		setLayout(new FlowLayout());//new BoxLayout(this, BoxLayout.Y_AXIS)); 
		JLabel empty = new JLabel();
		empty.setSize(10, 10);

		button = new JButton("Discard Pile");
		button.setBackground(new Color(118, 108, 81));
		button.setBorderPainted(true);
		button.setFont(new Font("Serif", Font.BOLD, 16));
		add(button);
		add(empty);

		withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(this);
		withdrawButton.setBackground(new Color(118, 108, 81));
		add(withdrawButton);
		add(empty);

		endTurnButton = new JButton("Announce End of Turn");
		endTurnButton.addActionListener(this);
		endTurnButton.setBackground(new Color(118, 108, 81));
		add(endTurnButton);
		add(empty);

		drawCardButton = new JButton("Draw a Card");
		drawCardButton.addActionListener(this);
		drawCardButton.setBackground(new Color(118, 108, 81));
		add(drawCardButton);
		//add(Box.createRigidArea(new Dimension(5,10)));

		this.setBackground(new Color(242, 202, 150));
		setPreferredSize(new Dimension(200, 150)); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Withdraw")) {
			gui.requestToWithdraw();
			gui.setEnableMainScreen("false");
		}
		else if (action.equals("Draw a Card")) {
			gui.requestToDrawCard();
		//	setDrawCardButton(false);//this should add a card to the hand.
			//GamePlayWindow.playerCard1.display.add(new JButton(),  "blue2.jpg");
		}
		else if (action.equals("Announce End of Turn")) {
			gui.requestToEndTurn();
		}
	}

	public void setDrawCardButton(boolean b) {
		//drawCardButton.setEnabled(b);
		//System.out.println("THE DRAW CARD BUTTON SHOULD BE >>" + b);

	}



}
