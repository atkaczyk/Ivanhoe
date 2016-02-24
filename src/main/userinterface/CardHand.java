package userinterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CardHand extends JPanel {

//  JScrollPane scrollpane;

  public CardHand() {
    super();
    JScrollPane scrollPane = new JScrollPane();
    
	final JPanel compsToExperiment = new JPanel();
	compsToExperiment.setLayout(new FlowLayout());

	JButton finalToken = new JButton();//"This is the token you retrieved");
	finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
	compsToExperiment.add(finalToken);
	finalToken = new JButton();//"This is the token you retrieved");
	finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/Avatar.jpg")));
	compsToExperiment.add(finalToken);
	finalToken = new JButton();//"This is the token you retrieved");
	finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/GameReady.jpg")));
	compsToExperiment.add(finalToken);
	finalToken = new JButton();//"This is the token you retrieved");
	finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
	compsToExperiment.add(finalToken);
	finalToken = new JButton();//"This is the token you retrieved");
	finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
	compsToExperiment.add(finalToken);	
	finalToken = new JButton();
	finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
	compsToExperiment.add(finalToken);
	finalToken = new JButton();//"This is the token you retrieved");
	finalToken.setIcon(new ImageIcon(this.getClass().getResource("Images/card.png")));
	compsToExperiment.add(finalToken);
	
	compsToExperiment.setSize(finalToken.getWidth(), finalToken.getHeight()*3);
	scrollPane.setViewportView(compsToExperiment);
	scrollPane.setHorizontalScrollBarPolicy(scrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	scrollPane.setSize(finalToken.getWidth(), finalToken.getHeight()*3);
//	this.add(compsToExperiment, BorderLayout.CENTER);
	this.add(scrollPane, BorderLayout.CENTER);
	 
//this.getViewport().add(compsToExperiment);
    setSize(100, 150); 

    setVisible(true);
 }

}
