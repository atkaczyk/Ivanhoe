package network;

import network.Client;
import utils.Config;

public class StartClient {

	public static void main (String[] argv){
		new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	}
	
}
