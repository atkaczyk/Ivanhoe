package userinterface;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerCardStats extends JPanel{
	// panel 1: labels for name, #tokens, score, holding(special card
	
	 JLabel playerName;
	 JLabel numTokens;
	 JLabel score;
	 JLabel specialCard;
	 JLabel turn;
	 JButton changeDisplay;
	 
	public PlayerCardStats (String pName, int numToken1, int score1, boolean turn1) {
		 setLayout(null); 
		 int top = 10;
		 
		 playerName = new JLabel(" " + pName);
		 playerName.setLocation(0, top);
		 playerName.setSize(100,30);
		 add(playerName);
		 
		 numTokens = new JLabel("# Tokens: " + numToken1);
		 numTokens.setLocation(0, top + 15);
		 numTokens.setSize(100,30);
		 add(numTokens);
		 
		 score = new JLabel("Score:");
		 score.setLocation(0, top + 30);
		 score.setSize(100,30);
		 add(score);
		 
		 specialCard = new JLabel("Special Cards: " + score1 );
		 specialCard.setLocation(0, top + 45);
		 specialCard.setSize(100,30);
		 add(specialCard);  
		 
		 if (turn1 == true){
			 turn = new JLabel("< YOUR TURN >");
	
		 } else {
			 turn = new JLabel("< NOT YOUR TURN > ");
		 }
		 
		 turn.setLocation(0, top + 60);
		 turn.setSize(100,30);
		 turn.setForeground(Color.red);
		 add(turn);
		 
		 changeDisplay = new JButton("Steal Card");
		 changeDisplay.setLocation(0, top + 90);
		 changeDisplay.setSize(100, 20);
		 changeDisplay.setEnabled(true);
		 changeDisplay.setBackground(Color.blue);
		 changeDisplay.setForeground(Color.yellow);
		 add(changeDisplay);
		 setSize(100, 100);	 
	}

	public void setPlayerName(String playerName2) {
		playerName.setText(playerName2);
	}

	public void setPlayerTokens(int numToken) {
		numTokens.setText("UPDATED: " + numToken);	
	}

	public void setPlayerScore(int score2) {
		score.setText("UPDATED: " + score2);	
	}

	public void setPlayerTurn(boolean turn2) {
		turn.setText("UPDATED: " + turn2);	
	}
	public void setSpecialCards(String str){
		specialCard.setText("Special Cards: " + str);
	}
}
