package userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
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
	JButton tokenRequest;

	int tokenNum;
	GUIController guiController;
	JLabel Step;
	JLabel avatar;
	javax.swing.JFrame frame ;

	public GameReadyWindow(Client client){
		super(); // Set the title of the window
		//frame = new javax.swing.JFrame("This appears at the top of the window");
		ImageIcon bImg = new ImageIcon(this.getClass().getResource("Images/buttonB.jpg"));

		ImageIcon icon = new ImageIcon(this.getClass().getResource("Images/ReadyB.jpg"));
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance(500, 400,  java.awt.Image.SCALE_SMOOTH ) ; 
		icon = new ImageIcon( newimg );
		JLabel background = new JLabel(icon);
	
		background.setBounds(0,0,500, 400);
		this.setContentPane(background);
		
		
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

		c.fill = GridBagConstraints.CENTER;
		Step = new JLabel("Step 1: Enter Your Name");
		c.ipady = 10;   
		c.weightx = 0.5;
		c.gridwidth= 5;
		c.gridx = 0;
		c.gridy = 0;
		Step.setFont(new Font("Old English Text MT", Font.BOLD, 26));
		Step.setBackground(new Color(242, 202, 150));
		Step.setVisible(true);
		add(Step, c);

		//avatar = new JLabel();
		avatar = new JLabel();
		ImageIcon icon2 = new ImageIcon(this.getClass().getResource("Images/PrincessAvatar1.png"));
		Image img2 = icon2.getImage() ;  
		Image newimg2 = img2.getScaledInstance(100, 150,  java.awt.Image.SCALE_SMOOTH ) ; 
		icon = new ImageIcon(newimg2);
		avatar.setIcon(icon2);
	//	avatar.setIcon(new ImageIcon(this.getClass().getResource("Images/Avatar.jpg")));
		c.ipady = 10 + avatar.getHeight();    
		c.ipadx = avatar.getWidth();
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		add(avatar, c);


		playerName = new JTextField();	
		playerName.setPreferredSize(new Dimension(90, 25));	

		c.ipady = 20;   
		c.ipadx = 90;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		add(playerName, c);

		playerNameSubmit = new JButton(); //(bImg);//
		playerNameSubmit.setText("Submit");
		playerNameSubmit.setBackground(new Color(118, 108, 81));
		playerNameSubmit.setBorderPainted(true);
		playerNameSubmit.setFont(new Font("Monotype Corsiva", Font.BOLD, 16));
		c.ipady = 10;    
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		add(playerNameSubmit, c);
		playerNameSubmit.addActionListener(this);


		token = new JLabel();
		token.setIcon(whiteToken); //new ImageIcon(this.getClass().getResource("Images/Token.jpg")));
		c.ipady = token.getHeight();   
		c.ipadx = token.getWidth() + token.getWidth()*6;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;


		token.setVisible(false);
		add(token, c);

		tokenRequest = new JButton("Press to Retrieve a Token");
		tokenRequest.setBackground(new Color(118, 108, 81));
		tokenRequest.setBorderPainted(true);
		tokenRequest.setFont(new Font("Monotype Corsiva", Font.BOLD, 16));
		c.weightx = 0.8;
		c.gridx = 0;
		c.gridy = 3;

		tokenRequest.setVisible(false);
		add(tokenRequest, c);

		tokenRequest.addActionListener(this);

		finalToken = new JLabel();//"This is the token you retrieved";
		finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/tokenNew.png")));
		c.ipady = finalToken.getHeight();   
		c.ipadx = finalToken.getWidth() + avatar.getWidth()*4;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;

		finalToken.setVisible(false);
		add(finalToken, c);

		gameReady = new JButton("Join Game");
		gameReady.setBackground(new Color(118, 108, 81));
		gameReady.setBorderPainted(true);
		gameReady.setFont(new Font("Monotype Corsiva", Font.BOLD, 16));
		c.weightx = 0.8;
		c.gridx = 0;
		c.gridy = 3;

		gameReady.setVisible(false);
		add(gameReady, c);
		gameReady.addActionListener(this);

		getContentPane().setBackground(new Color(242, 202, 150));
		setVisible(true);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE); // allow window to close
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setSize(500, 400); // Set the size of the window

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Submit")) {

			avatar.setVisible(false);
			playerNameSubmit.setVisible(false);
			playerName.setVisible(false);

			token.setVisible(true);
			tokenRequest.setVisible(true);

			Step.setText("Step 2: " + playerName.getText() + " Press to Retrieve a Token");
		}
		else if (action.equals("Press to Retrieve a Token")){
			Step.setText("Step 3: " + playerName.getText() + " Prepare to Battle");
			finalToken.setVisible(true);
			gameReady.setVisible(true);


			token.setVisible(false);
			tokenRequest.setVisible(false);
			guiController.sendTokenRequest();

		}
		else if(action.equals("Join Game")){
			Step.setText("Greetings " + playerName.getText() + " ... Please Wait");
			avatar.setVisible(true);
			finalToken.setVisible(false);
			gameReady.setVisible(false);

			guiController.sendJoinGame(playerName.getText(), tokenNum);	

		}
	}
	public void setFinalToken(int tokenColour) {
		tokenNum = tokenColour;
		finalToken.setIcon(tokens[tokenColour]);
	}

}

