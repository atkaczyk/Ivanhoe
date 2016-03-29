/**
 * @author Victoria Gray, Sophia Brandt, Alisa Tkaczyk
 * 
 * 1. After running the StartServer then you may run The StartClient, which will start a client.
 * 2. Continue running a new StartServer until you have started the number of clients that you requested in the beginning popup.
 * 3. You can start a max of 5 clients. When the all the clients have been started then the main game play screen will open.
 *
 */

package network;

import javax.swing.JOptionPane;

import network.Client;
import utils.Config;

public class StartClient {

	public static void main (String[] argv){
		
		//Asking user for host
		String host = (String)JOptionPane.showInputDialog(
				null,
		        "Enter the Server Host IP Adress (eg. 127.0.0.1)",
		        "Customized Dialog",
		        JOptionPane.PLAIN_MESSAGE,
		        null,
		        null,
		        null);
		
		//Asking user for port
		String port = (String)JOptionPane.showInputDialog(
				null,
		        "Enter the Client Port (eg. 5050)",
		        "Customized Dialog",
		        JOptionPane.PLAIN_MESSAGE,
		        null,
		        null,
		        null);
		
		new Client(host, Integer.parseInt(port));		//dont use defaults. make popup to ask client for info
	}
	
}
