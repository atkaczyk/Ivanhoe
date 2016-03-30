package userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import logic.SimpleCard;

/**
 * Connected to the PlayerCard: used to represent
 * the unique stats for each individual player. 
 *  
 * @author Alisa Tkaczyk
 * 
 * Contains setters for each players game state including their name, 
 * score, turn, special cards and in play status.
 **/

public class PlayerCardStats extends JPanel{
	JLabel playerName;
	JLabel numTokens;
	JLabel score;
	JLabel specialCard;
	JLabel turn;
	JButton changeDisplay;
	JLabel isWithdrawn;
	String sCards;
	JLayeredPane tokenSet;

	public PlayerCardStats (String pName, int numToken1, int score1, boolean turn1) {
		setLayout(null); 
		int top = 10;

		sCards = "";

		playerName = new JLabel(" " + pName);
		playerName.setLocation(0, top);
		playerName.setFont(new Font("Century", Font.BOLD, 14));
		playerName.setSize(100,30);
		add(playerName);

		tokenSet = new JLayeredPane();
		tokenSet.setLocation(0, top + 100);
		tokenSet.setPreferredSize(new Dimension(100,30)); //setSize(100,30);
		tokenSet.setBackground(new Color(0,0,0));
		tokenSet.setOpaque(true);
		//add(tokenSet);

		numTokens = new JLabel(""); //"# Tokens: " + numToken1);
		numTokens.setLocation(0, top + 15);
		numTokens.setSize(100,30);
		numTokens.setFont(new Font("Century", Font.PLAIN, 14));
		add(numTokens);

		score = new JLabel(""); //Score:");
		score.setLocation(0, top + 30);
		score.setSize(100,30);
		score.setFont(new Font("Century", Font.PLAIN, 14));
		add(score);

		specialCard = new JLabel(""); //("Special Card " + score1 );
		specialCard.setLocation(0, top + 45);
		specialCard.setFont(new Font("Century", Font.PLAIN, 14));
		specialCard.setSize(100,30);
		add(specialCard);  

		turn = new JLabel(""); //("< Inactive >");
		turn.setLocation(0, top + 60);
		turn.setSize(100,30);
		turn.setFont(new Font("Century", Font.PLAIN, 14));
		turn.setForeground(Color.red);
		add(turn);

		isWithdrawn = new JLabel("");
		isWithdrawn.setFont(new Font("Century", Font.BOLD, 14));
		isWithdrawn.setLocation(0, top + 75);
		isWithdrawn.setSize(100, 30);
		isWithdrawn.setVisible(true);
		add(isWithdrawn);

		setOpaque(true);
		setBackground(new Color(153,125,106));
		setSize(130, 140);	 
	}

	public void setPlayerName(String playerName2) {
		playerName.setText(playerName2);
	}

	/**
	 * @param String of numbers that represent the colours of the tokens in the players pool
	 */
	public void setPlayerTokens(String s) {
		// TURN STR which is 012 into its proper colour..
		int space = 0;

		ImageIcon icon =new ImageIcon(this.getClass().getResource("Images/Tokens/purpleToken.png"));
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH ) ; 
		icon = new ImageIcon( newimg );
		String temp = "";
		if(s.contains("0")){
			temp += "P";
			space++;
			JLabel pTok = new JLabel(icon);
			pTok.setOpaque(false);
			pTok.setSize(30, 30);
			pTok.setBounds((space * 20), 0, pTok.getWidth(), pTok.getHeight());
			tokenSet.add(pTok, space);
		}
		if(s.contains("1")){
			temp += "R";
			space++;
			JLabel pTok = new JLabel(icon);
			pTok.setOpaque(false);
			pTok.setSize(30, 30);
			pTok.setBounds((space * 20), 0, pTok.getWidth(), pTok.getHeight());
			tokenSet.add(pTok, space);
		}
		if(s.contains("2")){
			temp += "Y";
			space++;
			JLabel pTok = new JLabel(icon);
			pTok.setOpaque(false);
			pTok.setSize(30, 30);
			pTok.setBounds((space * 20), 0, pTok.getWidth(), pTok.getHeight());
			tokenSet.add(pTok, space);
		}
		if(s.contains("3")){
			temp += "G";
			space++;
			JLabel pTok = new JLabel(icon);
			pTok.setOpaque(false);
			pTok.setSize(30, 30);
			pTok.setBounds((space * 20), 0, pTok.getWidth(), pTok.getHeight());
			tokenSet.add(pTok, space);
		}
		if(s.contains("4")){
			temp += "B";
			space++;
			JLabel pTok = new JLabel(icon);
			pTok.setOpaque(false);
			pTok.setSize(30, 30);
			pTok.setBounds((space * 20), 0, pTok.getWidth(), pTok.getHeight());
			tokenSet.add(pTok, space);
		} 

		//this.add(tokenSet);
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

	public void setShield(String str) {
		if (str.equals("true")){
			if(sCards.contains("Shield")){} else{
				sCards += "Shield ";
			}
		} else if (str.equals("false")){
			sCards= "";
		}
		specialCard.setText("* " + sCards);

	}
	public void setStunned(String str) {
		if (str.equals("true")){
			if(sCards.contains("Stunned")){

			} else{
				sCards += "Stunned ";
			}
		} else if (str.equals("false")){
			if(sCards.contains("Shield")){
				sCards = "Shield";
			}else{
				sCards = "";
			}
		}
		specialCard.setText("* " + sCards);

	}

	public void setIvanhoe(String string) {
		// TODO Auto-generated method stub

	}
}
