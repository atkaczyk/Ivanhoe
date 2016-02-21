package userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//needed to use swing components e.g. JFrame public class FirstApplication extends JFrame { public FirstApplication(String title) { super(title); // Set the title of the window
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameReadyWindow extends JFrame implements MouseListener{

	JLabel token;

	ImageIcon blueToken;
	ImageIcon greyToken;
	ImageIcon redToken;
	ImageIcon purpleToken;
	ImageIcon greenToken;
	ImageIcon goldToken;
	
	Boolean tokenRequest;

	ImageIcon[] tokens;
	javax.swing.JFrame frame ;
	

	public GameReadyWindow() {

			super(); // Set the title of the window
			//frame = new javax.swing.JFrame("This appears at the top of the window");

			blueToken= new ImageIcon(this.getClass().getResource("Images/Tokens/blueToken.png"));
			greyToken =new ImageIcon(this.getClass().getResource("Images/Tokens/greyToken.png"));
			redToken= new ImageIcon(this.getClass().getResource("Images/Tokens/redToken.png"));
			purpleToken =new ImageIcon(this.getClass().getResource("Images/Tokens/purpleToken.png"));
			greenToken=new ImageIcon(this.getClass().getResource("Images/Tokens/greenToken.png"));
			goldToken=new ImageIcon(this.getClass().getResource("Images/Tokens/goldToken.png"));

			tokens = new ImageIcon[]{blueToken, greyToken, redToken, purpleToken, greenToken, goldToken};



			setPreferredSize(new Dimension(500,500));
			setLayout(new GridLayout(2,1)); //entire layout (num cells required, 2 * 1)

			JPanel upperPanel = new JPanel();
			JPanel lowerPanel = new JPanel();

			upperPanel.setLayout(new GridLayout(1, 2)); //1 column 2 rows, splitting the top row into 2
			lowerPanel.setLayout(new GridLayout(1,1));

			JPanel playerSide = new JPanel(new BorderLayout());
			JPanel tokenSide = new JPanel();
			JPanel readySide = new JPanel();

			JLabel Step1 = new JLabel("Step 1: Enter your Name");
			JLabel Step2 = new JLabel("Step 2: Click here to pick a token");
			JLabel Step3 = new JLabel("Step 3: PREPARE TO BATTLE");


			JLabel avatar = new JLabel();
			avatar.setIcon(new ImageIcon(this.getClass().getResource("Images/Avatar.jpg")));

			JTextField playerName = new JTextField();	
			playerName.setPreferredSize(new Dimension(100, 50));	

			JLabel gameReady = new JLabel();		
			gameReady.setIcon(new ImageIcon(this.getClass().getResource("Images/GameReady.jpg")));
			gameReady.setName("game ready");
			gameReady.addMouseListener(this);

			token = new JLabel();
			token.setIcon(tokens[1]);

			JLabel tokenSelect = new JLabel("Press to Retrieve a Token");
			//tokenSelect.setIcon(new ImageIcon(this.getClass().getResource("Images/Token.jpg")));
			tokenSelect.setName("token select");
			tokenSelect.addMouseListener(this);

			JLabel finalToken = new JLabel("This is the token you retrieved");
			finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/Token.jpg")));


			Step1.setHorizontalAlignment(JLabel.CENTER);
			Step1.setVerticalAlignment(JLabel.BOTTOM);
			avatar.setHorizontalAlignment(JLabel.CENTER);
			playerName.setHorizontalAlignment(JLabel.CENTER);
			playerSide.add(Step1, BorderLayout.NORTH);
			playerSide.add(avatar, BorderLayout.CENTER);
			playerSide.add(playerName, BorderLayout.SOUTH);


			Step2.setHorizontalAlignment(JLabel.CENTER);
			Step2.setVerticalAlignment(JLabel.BOTTOM);
			token.setHorizontalAlignment(JLabel.CENTER);
			tokenSelect.setHorizontalAlignment(JLabel.CENTER);

			tokenSide.add(Step2, BorderLayout.NORTH);
			tokenSide.add(token, BorderLayout.CENTER);
			tokenSide.add(tokenSelect, BorderLayout.SOUTH);

			Step3.setHorizontalAlignment(JLabel.CENTER);
			Step3.setVerticalAlignment(JLabel.BOTTOM);
			gameReady.setHorizontalAlignment(JLabel.CENTER);
			readySide.add(Step3, BorderLayout.NORTH);
			readySide.add(gameReady, BorderLayout.CENTER);
			readySide.add(finalToken, BorderLayout.SOUTH);

			upperPanel.add(playerSide);
			upperPanel.add(tokenSide);
			lowerPanel.add(readySide);

			add(upperPanel);
			add(lowerPanel);

			setVisible(true);
			pack();
			setDefaultCloseOperation(EXIT_ON_CLOSE); // allow window to close
			setSize(300, 100); // Set the size of the window

	}

	public static void main(String args[]) { // Instantiate a FirstApplication object so you can display it FirstApplication frame = new FirstApplication("FirstApplication
		GameReadyWindow startWindow = new GameReadyWindow();
		startWindow.setVisible(true);

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
//when mouse clicked on retrieve token
//tokenRequest = true;
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public boolean getTokenRequest(){
		return tokenRequest;
	}
}





