package userinterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//needed to use swing components e.g. JFrame public class FirstApplication extends JFrame { public FirstApplication(String title) { super(title); // Set the title of the window
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import network.Client;

public class GameReadyWindow extends JFrame implements ActionListener{
	//private Client client = new Client();

	JLabel token;
	ImageIcon[] tokens;

	ImageIcon blueToken;
	ImageIcon whiteToken;
	ImageIcon redToken;
	ImageIcon purpleToken;
	ImageIcon greenToken;
	ImageIcon yellowToken;

	JTextField playerName;
	JButton playerNameSubmit;

	JButton gameReady;

	JLabel finalToken;
	JLabel Step1;

	JButton tokenRequest;

	int tokenNum;
	GUIController guiController;
	JLabel Step2;
	JLabel Step3 ;
	JLabel avatar;
	javax.swing.JFrame frame ;

	public GameReadyWindow(Client client){
		super(); // Set the title of the window
		//frame = new javax.swing.JFrame("This appears at the top of the window");
		guiController = new GUIController(client);
		blueToken= new ImageIcon(this.getClass().getResource("Images/Tokens/blueToken.png"));
		whiteToken =new ImageIcon(this.getClass().getResource("Images/Tokens/greyToken.png"));
		redToken= new ImageIcon(this.getClass().getResource("Images/Tokens/redToken.png"));
		purpleToken =new ImageIcon(this.getClass().getResource("Images/Tokens/purpleToken.png"));
		greenToken=new ImageIcon(this.getClass().getResource("Images/Tokens/greenToken.png"));
		yellowToken =new ImageIcon(this.getClass().getResource("Images/Tokens/goldToken.png"));

		tokens = new ImageIcon[]{purpleToken, redToken, yellowToken, greenToken, blueToken, whiteToken}; //blueToken, greyToken, redToken, purpleToken, greenToken, goldToken};

		setPreferredSize(new Dimension(500,500));

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		Step1 = new JLabel("Step 1: Enter your Name");
		c.fill = GridBagConstraints.HORIZONTAL;

		c.ipady = 10;      //make this component tall
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		add(Step1, c);

		avatar = new JLabel();
		avatar.setIcon(new ImageIcon(this.getClass().getResource("Images/Avatar.jpg")));
		c.ipady = avatar.getHeight();     
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		add(avatar, c);


		playerName = new JTextField();	
		playerName.setPreferredSize(new Dimension(100, 50));	
		c.ipady = 10;   
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		add(playerName, c);

		playerNameSubmit = new JButton("Submit");
		c.ipady = 10;    
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		add(playerNameSubmit, c);
		playerNameSubmit.addActionListener(this);


		Step2 = new JLabel("Step 2: Click here to pick a token");
		c.fill = GridBagConstraints.CENTER;
		c.ipady = 10;   
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;

		Step2.setVisible(false);
		add(Step2, c);

		token = new JLabel();
		token.setIcon(new ImageIcon(this.getClass().getResource("Images/Token.jpg")));
		c.ipady = token.getHeight();   
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 1;

		token.setVisible(false);
		add(token, c);

		tokenRequest = new JButton("Press to Retrieve a Token");
		c.weightx = 0.8;
		c.gridx = 2;
		c.gridy = 3;

		tokenRequest.setVisible(false);
		add(tokenRequest, c);

		tokenRequest.addActionListener(this);


		Step3 = new JLabel("Step 3: PREPARE TO BATTLE");
		c.fill = GridBagConstraints.CENTER;
		c.ipady = 10;   
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 0;
		Step3.setVisible(false);
		add(Step3, c);

		//THE FINAL TOKEN IS THE VALUE RECIEVED FROM THE SERVER AND UPDATES THE ICON ACCORDINGLY. MUST BE DONE SEPARATELY.
		finalToken = new JLabel();//"This is the token you retrieved");
		finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/Token.jpg")));
		c.ipady = finalToken.getHeight();   
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 1;

		finalToken.setVisible(false);
		add(finalToken, c);

		gameReady = new JButton("Join Game");
		//gameReady.setIcon(new ImageIcon(this.getClass().getResource("Images/GameReady.jpg")));
		c.weightx = 0.8;
		c.gridx = 3;
		c.gridy = 3;

		gameReady.setVisible(false);
		add(gameReady, c);
		gameReady.addActionListener(this);

		setVisible(true);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE); // allow window to close
		setSize(500, 500); // Set the size of the window

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Submit")) {

			System.out.println(playerName.getText());
			Step2.setVisible(true);

			token.setVisible(true);
			tokenRequest.setVisible(true);

			Step3.setText("Step 3: " + playerName.getText() + " PREPARE TO BATTLE");
			Step2.setText("Step 2: " + playerName.getText() + " Press to Retrieve a Token");
			//	playerName.setText(" ");

		}
		else if (action.equals("Press to Retrieve a Token")){
			Step3.setVisible(true);
			finalToken.setVisible(true);
			gameReady.setVisible(true);

			guiController.sendTokenRequest();
		}
		else if(action.equals("Join Game")){
			guiController.sendJoinGame(playerName.getText(), tokenNum);		
		}

	}

	public void getFinalToken(){
		//RECIEVE RANDOM TOKEN FROM THE SERVER, UPDATE THE SCREEN

	}
	public void setFinalToken(int tokenColour) {
		// TODO Auto-generated method stub
		tokenNum = tokenColour;
		finalToken.setIcon(tokens[tokenColour]);
	}

}

