package userinterface;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Connected to the Card Hand for the main game screen
 * and the components it is made of.
 * Holds contents of the button panel and sends messages to the guiController when a button is pressed
 *  Contains setters for the entire game state.
 * @author Alisa Tkaczyk
 **/

public class GamePlayButtonPanel extends JPanel implements ActionListener{
	
	GUIController gui;
	JButton button;
	JButton withdrawButton;
	JButton endTurnButton;
	JButton drawCardButton;
	JLabel curPlayerName;
	
	GamePlayButtonPanel(GUIController gui1) {
		gui = gui1;

		setLayout(new FlowLayout());//new BoxLayout(this, BoxLayout.Y_AXIS)); 
		curPlayerName = new JLabel("CurPlayerName");
		curPlayerName.setFont(new Font("Century", Font.BOLD, 16));
		add(curPlayerName);

		JLabel empty = new JLabel();
		empty.setSize(10, 10);
		ImageIcon bImg = new ImageIcon(this.getClass().getResource("Images/buttonB.jpg"));

		button = new JButton("Discard Pile");
		button.setHorizontalTextPosition(JButton.CENTER); //remove
		button.setVerticalTextPosition(JButton.CENTER);//remove

		button.setText("Discard Pile"); //remove
		button.setBackground(new Color(118, 108, 81));
		button.setBorderPainted(true);
		button.setSize(new Dimension(bImg.getIconHeight(),bImg.getIconWidth()));
		button.setFont(new Font("Monotype Corsiva", Font.BOLD, 16));
		add(button);
		add(empty);

		withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(this);
		withdrawButton.setBackground(new Color(118, 108, 81));
		withdrawButton.setBorderPainted(true);
		withdrawButton.setFont(new Font("Monotype Corsiva", Font.BOLD, 16));
		add(withdrawButton);
		add(empty);

		endTurnButton = new JButton("Announce End of Turn");
		endTurnButton.addActionListener(this);
		endTurnButton.setBackground(new Color(118, 108, 81));
		endTurnButton.setBorderPainted(true);
		endTurnButton.setFont(new Font("Monotype Corsiva", Font.BOLD, 16));
		add(endTurnButton);
		add(empty);

		drawCardButton = new JButton("Draw a Card");
		drawCardButton.addActionListener(this);
		drawCardButton.setBackground(new Color(118, 108, 81));
		drawCardButton.setBorderPainted(true);
		drawCardButton.setFont(new Font("Monotype Corsiva", Font.BOLD, 16));
		add(drawCardButton);
		//add(Box.createRigidArea(new Dimension(5,10)));
		setPreferredSize(new Dimension(200, 200)); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Withdraw")) {
			gui.requestToWithdraw();
			//setWithdrawEnable(false);
		}
		else if (action.equals("Draw a Card")) {
			gui.requestToDrawCard();
			//setDrawCardEnable(false);
		}
		else if (action.equals("Announce End of Turn")) {
			gui.requestToEndTurn();
		}
	}

	public void setWithdrawEnable(boolean b) {
		withdrawButton.setEnabled(b);
	}

	public void setDrawCardEnable(boolean b) {
		drawCardButton.setEnabled(b);
	}

	public void setName(String str){
		curPlayerName.setText("Actions for " +str);
	}

	public void setEnableOptionButtons(boolean b){
		Component[] str = this.getComponents();
		for (int i = 0; i < str.length; i++){
			str[i].setEnabled(b);
		}
	}

}
