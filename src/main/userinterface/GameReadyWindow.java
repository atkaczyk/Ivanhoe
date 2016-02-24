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

public class GameReadyWindow extends JFrame implements ActionListener{

	JLabel token;
	ImageIcon[] tokens;

	ImageIcon blueToken;
	ImageIcon greyToken;
	ImageIcon redToken;
	ImageIcon purpleToken;
	ImageIcon greenToken;
	ImageIcon goldToken;

	JTextField playerName;
	JButton playerNameSubmit;
	JButton gameReady;
	JLabel finalToken;
	JLabel Step1;
	JButton tokenRequest;

	JLabel Step2;
	JLabel Step3 ;
	JLabel avatar;
	javax.swing.JFrame frame ;

	public GameReadyWindow(){

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
		token.setIcon(new ImageIcon(this.getClass().getResource("Images/Avatar.jpg")));
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

		 gameReady = new JButton("Game Ready");
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



//	public static void main(String args[]) { // Instantiate a FirstApplication object so you can display it FirstApplication frame = new FirstApplication("FirstApplication
//		GameReadyWindow startWindow = new GameReadyWindow("");
//		startWindow.setVisible(true);
//	}


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
			//SEND THE PLAYERS NAME TO BE STORED.
			playerName.setText(" ");

		}
		else if (action.equals("Press to Retrieve a Token")){
			System.out.println("tokenRequest");
			Step3.setVisible(true);
			finalToken.setVisible(true);
			gameReady.setVisible(true);
			//SEND THE REQUEST FOR A TOKEN GUI CONTROL WHO WILL SEND TO SERVER?
			//OR JUST SEND TO THE SERVER
			//client.send(
		}
		else if(action.equals("Game Ready")){
			System.out.println("Game Ready");
			//SEND MESSAGE GAME READY TO GUI CONTROL WHO WILL SEND TO SERVER?
			//OR JUST SEND TO THE SERVER
		}

	}
	
	public void getFinalToken(){
		//RECIEVE RANDOM TOKEN FROM THE SERVER, UPDATE THE SCREEN
		
	}
}

