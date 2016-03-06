package userinterface;

import java.awt.Color;
import java.awt.Font;

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
	 JLabel isWithdrawn;
	public PlayerCardStats (String pName, int numToken1, int score1, boolean turn1) {
		 setLayout(null); 
		 int top = 10;
		 
		 playerName = new JLabel(" " + pName);
		 playerName.setLocation(0, top);
		 playerName.setFont(new Font("Century", Font.BOLD, 14));
		 playerName.setSize(100,30);
		 add(playerName);
		 
		 numTokens = new JLabel("# Tokens: " + numToken1);
		 numTokens.setLocation(0, top + 15);
		 numTokens.setSize(100,30);
		 numTokens.setFont(new Font("Century", Font.PLAIN, 14));
		 add(numTokens);
		 
		 score = new JLabel("Score:");
		 score.setLocation(0, top + 30);
		 score.setSize(100,30);
		 score.setFont(new Font("Century", Font.PLAIN, 14));
		 add(score);
		 
		 specialCard = new JLabel("Special Card " + score1 );
		 specialCard.setLocation(0, top + 45);
		 specialCard.setFont(new Font("Century", Font.PLAIN, 14));
		 specialCard.setSize(100,30);
		 add(specialCard);  
		 
	     turn = new JLabel("< Inactive >");
		 turn.setLocation(0, top + 60);
		 turn.setSize(100,30);
		 turn.setFont(new Font("Century", Font.PLAIN, 14));
		 turn.setForeground(Color.red);
		 add(turn);
		 
//		 changeDisplay = new JButton("Steal Card");
//		 changeDisplay.setLocation(0, top + 90);
//		 changeDisplay.setSize(100, 20);
//		 changeDisplay.setEnabled(true);
//		 changeDisplay.setBackground(Color.blue);
//		 changeDisplay.setForeground(Color.yellow);
//		 add(changeDisplay);
		 
		  isWithdrawn = new JLabel("");
		isWithdrawn.setFont(new Font("Century", Font.BOLD, 14));
		  isWithdrawn.setLocation(0, top + 75);
		  isWithdrawn.setSize(100, 30);
		  isWithdrawn.setVisible(true);
		 add(isWithdrawn);
		 setSize(130, 140);	 
	}

	public void setPlayerName(String playerName2) {
		playerName.setText(playerName2);
	}

	public void setPlayerTokens(String s) {
// TURN STR which is 012 into its proper colour..
		String temp = "";
		if(s.contains("0")){
			temp += "P";
			//numTokens.setForeground(Color.MAGENTA);
		}
		if(s.contains("1")){
			temp += "R";
			//tColour = Color.RED;
		}
		if(s.contains("2")){
			temp += "Y";
			//tColour = Color.YELLOW;
		}
		if(s.contains("3")){
			temp += "G";
			//tColour = Color.GREEN;
		}
		if(s.contains("4")){
			temp += "B";
			//tColour = Color.BLUE;
		} 
		numTokens.setText("Tokens: " +temp);	
	}

	public void setPlayerScore(int score2) {
		score.setText("Score: " + score2);	
	}

	public void setPlayerTurn(String turn2) {
		if(turn2.equals("true")){
			turn.setText(" < Turn >");	
		} else if (turn2.equals("false")){
			turn.setText(" < Waiting > ");	
		}
	}
	public void setSpecialCards(String str){
		specialCard.setText("Special Cards: " + str);
	}

	public void setWithdrawn(String str) {
		if (str.equals("true")){
			isWithdrawn.setForeground(Color.red);
			isWithdrawn.setText("WITHDRAWN");
		} else if (str.equals("false")){
			isWithdrawn.setForeground(Color.black);
			isWithdrawn.setText("IN PLAY");
		}
		
	}
}
