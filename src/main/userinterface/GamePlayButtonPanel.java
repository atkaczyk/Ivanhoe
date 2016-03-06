package userinterface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
		add(button);
		add(empty);

		withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(this);
		add(withdrawButton);
		add(empty);

		endTurnButton = new JButton("Announce End of Turn");
		endTurnButton.addActionListener(this);
		//endTurnButton.setPreferredSize(new Dimension(10,10));
		add(endTurnButton);
		add(empty);

		drawCardButton = new JButton("Draw a Card");
		drawCardButton.addActionListener(this);
		add(drawCardButton);
		//add(Box.createRigidArea(new Dimension(5,10)));

		this.setBackground(Color.blue);
		setPreferredSize(new Dimension(200, 150)); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Withdraw")) {
			gui.requestToWithdraw();
		}
		else if (action.equals("Draw a Card")) {
			gui.requestToDrawCard();
			setDrawCardButton(false);//this should add a card to the hand.
			//GamePlayWindow.playerCard1.display.add(new JButton(),  "blue2.jpg");
		}
		else if (action.equals("Announce End of Turn")) {
			gui.requestToEndTurn();
		}
	}

	public void setDrawCardButton(boolean b) {
		drawCardButton.setEnabled(b);
		System.out.println("THE DRAW CARD BUTTON SHOULD BE >>" + b);

	}



}
