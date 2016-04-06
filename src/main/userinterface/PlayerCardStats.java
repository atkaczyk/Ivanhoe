package userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

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
	JLabel pToken;
	JLabel rToken;
	JLabel yToken;
	JLabel gToken;
	JLabel bToken;
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
		playerName.setLocation(0, 3);
		playerName.setFont(new Font("Lucida Handwriting", Font.ITALIC, 14));
		playerName.setSize(100,30);
		playerName.setForeground(new Color(220,212,200));
		add(playerName);


		pToken = new JLabel(); 
		pToken.setLayout(new FlowLayout());
		pToken.setLocation(0, top + 15);
		pToken.setSize (20,20);
		pToken.setPreferredSize(new Dimension (20,20));
		add(pToken);

		rToken = new JLabel(); 
		rToken.setLayout(new FlowLayout());
		rToken.setLocation(10, top + 15);
		rToken.setSize (20,20);
		rToken.setPreferredSize(new Dimension (20,20));
		add(rToken);

		yToken = new JLabel(); 
		yToken.setLayout(new FlowLayout());
		yToken.setLocation(20, top + 15);
		yToken.setSize (20,20);
		yToken.setPreferredSize(new Dimension(20,20));
		add(yToken);

		gToken = new JLabel(); 
		gToken.setLayout(new FlowLayout());
		gToken.setLocation(30, top + 15);
		gToken.setSize(20,20);
		gToken.setPreferredSize(new Dimension(20,20));
		add(gToken);

		bToken = new JLabel(); 
		bToken.setLayout(new FlowLayout());
		bToken.setLocation(40, top + 15);
		bToken.setSize (20,20);
		bToken.setPreferredSize(new Dimension(20,20));
		add(bToken);

		score = new JLabel(""); //Score:");
		score.setLocation(0, top + 30);
		score.setSize(100,30);
		score.setForeground(new Color(220,212,200));
		score.setFont(new Font("Century", Font.BOLD, 14));
		add(score);

		specialCard = new JLabel(""); //("Special Card " + score1 );
		specialCard.setLocation(0, top + 45);
		specialCard.setFont(new Font("Century", Font.BOLD, 14));
		specialCard.setForeground(new Color(220,212,200));
		specialCard.setSize(100,30);
		add(specialCard);  

		turn = new JLabel(""); //("< Inactive >");
		turn.setLocation(0, top + 60);
		turn.setSize(100,30);
		turn.setFont(new Font("Century", Font.BOLD, 14));
		turn.setForeground(Color.red);
		add(turn);

		isWithdrawn = new JLabel("");
		isWithdrawn.setFont(new Font("Century", Font.BOLD, 14));
		isWithdrawn.setLocation(0, top + 75);
		isWithdrawn.setSize(100, 30);
		isWithdrawn.setForeground(new Color(220,212,200));
		isWithdrawn.setVisible(true);
		add(isWithdrawn);

		setOpaque(false);
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
		pToken.setIcon(null);
		rToken.setIcon(null);
		yToken.setIcon(null);
		gToken.setIcon(null);
		bToken.setIcon(null);
		
		ImageIcon purple =new ImageIcon(this.getClass().getResource("Images/Tokens/purpleToken.png"));
		Image imgP = purple.getImage() ;  
		Image newimgP = imgP.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH ) ; 
		purple = new ImageIcon( newimgP );

		ImageIcon red =new ImageIcon(this.getClass().getResource("Images/Tokens/redToken.png"));
		Image imgR = red.getImage() ;  
		Image newimgR = imgR.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH ) ; 
		red = new ImageIcon( newimgR );

		ImageIcon yellow =new ImageIcon(this.getClass().getResource("Images/Tokens/goldToken.png"));
		Image imgY = yellow.getImage() ;  
		Image newimgY = imgY.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH ) ; 
		yellow = new ImageIcon( newimgY );

		ImageIcon green =new ImageIcon(this.getClass().getResource("Images/Tokens/greenToken.png"));
		Image imgG = green.getImage() ;  
		Image newimgG = imgG.getScaledInstance(20,20,  java.awt.Image.SCALE_SMOOTH ) ; 
		green = new ImageIcon( newimgG );

		ImageIcon blue =new ImageIcon(this.getClass().getResource("Images/Tokens/blueToken.png"));
		Image imgB = blue.getImage() ;  
		Image newimgB = imgB.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH ) ; 
		blue = new ImageIcon( newimgB );


		if(s.contains("0")){
			pToken.setIcon(purple);
		}
		if(s.contains("1")){
			rToken.setIcon(red);
		}
		if(s.contains("2")){
			yToken.setIcon(yellow);	
		}
		if(s.contains("3")){
			gToken.setIcon(green);	
		}
		if(s.contains("4")){
			bToken.setIcon(blue);	
		} 

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
			if(sCards.contains("Stunned")){
				sCards = "Stunned";
			}else{
				sCards = "";
			}
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
