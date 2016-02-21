package userinterface;
/*this class is tentative ---
 * data may be moved into button panel and
 * discard pile may be handled there */

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePlayDiscardPanel  extends JPanel implements ActionListener{

	private CardLayout cardLayoutManager;
	JLabel first;
	JLabel second;
	JLabel third;


	public GamePlayDiscardPanel ( ) { 
		super();
		/* WHY ISNT THE ICON BEING DISPLAYED? */
		/* CONTINUE READING 1406 NOTES 2 */
		CardLayout layoutManager = new CardLayout(0,0);
		setLayout(layoutManager);

		// Add (and give names to) components using the layout manager
		first = new JLabel(new ImageIcon("Images/GameReady.jpg"));
		second = new JLabel(new ImageIcon("Images/Avatar.jpg"));
		third = new JLabel(new ImageIcon("Images/Avatar.jpg"));
		add("first", first);
		add("second", second);
		add("third", third);

		// Pick the component to show, in this case, the first
		layoutManager.show(this, "first");

		setSize(200,172);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
