package userinterface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

//import javafx.scene.shape.Box;

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

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		
		
		button = new JButton("Discard Pile");
		add(button);
		//add(Box.createRigidArea(new Dimension(5,10)));

		withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(this);
		add(withdrawButton);
		//add(Box.createRigidArea(new Dimension(5,10)));
		
		endTurnButton = new JButton("Announce End of Turn");
		endTurnButton.addActionListener(this);
		//endTurnButton.setPreferredSize(new Dimension(10,10));
		add(endTurnButton);
		//add(Box.createRigidArea(new Dimension(5,10)));

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
			gui.requestToDrawCard(); //this should add a card to the hand.
			//GamePlayWindow.playerCard1.display.add(new JButton(),  "blue2.jpg");
		}
		else if (action.equals("Announce End of Turn")) {
			gui.requestToEndTurn();
		}


	}



}
