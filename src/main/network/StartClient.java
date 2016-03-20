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
