package network;

import java.net.*;
import java.io.*;

import logic.Game;
import userinterface.GUIController;
import utils.Trace;

public class Client {
	
	//private GUIController gui = new GUIController();
	private userinterface.GUIController gui;
	
	//Okay so here's what you gotta do. Add a parameter to the current client constructor so it will now take (string ip, int port, Gui controller view)
	//Then in your constructor instead of creating a new Gui controller, you just say: gui = view
	//And then what I do in my constructor when I call it is I create a new client. So it only happens once. My it will be client = new Client("127.0.0.1", 5050, this)
	
	private int ID = 0;
	private Socket socket            = null;
	private Thread thread            = null;
	private ClientThread   client    = null;
	private BufferedReader console   = null;
	private BufferedReader streamIn  = null;
	private BufferedWriter streamOut = null;
	
	private Boolean connected		 = false;
	private Boolean gameScreenLaunched = false;
	private Boolean updateAllPlayersInfo = false;
	private Boolean updateShowPlayerHand = false;
	private Boolean cardPlayed = false;
	private Boolean gameReady = false;
	private Boolean joinGame = false;
	
	public Client (String serverName, int serverPort) {  
		System.out.println(ID + ": Establishing connection. Please wait ...");

		try {  
			this.socket = new Socket(serverName, serverPort);
			this.ID = socket.getLocalPort();
	    	System.out.println(ID + ": Connected to server: " + socket.getInetAddress());
	    	System.out.println(ID + ": Connected to portid: " + socket.getLocalPort());
	      this.start();
		} catch(UnknownHostException uhe) {  
			System.err.println(ID + ": Unknown Host");
			Trace.getInstance().exception(this,uhe);
		} catch(IOException ioe) {  
			System.out.println(ID + ": Unexpected exception");
			Trace.getInstance().exception(this,ioe);
	   }
		
//		gui = new GUIController(this);
		
	}
	
	
	//public Client(GUIController gui){
	//	this.gui = gui;
	//}


	public int getID () {
		return this.ID;
	}
	
	
   public void start() throws IOException {  
	   try {
	   	console	= new BufferedReader(new InputStreamReader(System.in));
		   streamIn	= new BufferedReader(new InputStreamReader(socket.getInputStream()));
		   streamOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		   if (thread == null) {  
		   	  client = new ClientThread(this, socket);
		   	  
		      connected = true;
		   }
	   } catch (IOException ioe) {
      	Trace.getInstance().exception(this,ioe);
         throw ioe;
	   }
   }

	public void sendMessageToServer(String msg) { 
		
		//running all the time
		//was looking at the console
		//take a msg
		try {  
			if (streamOut != null) {
				streamOut.write(msg + "\n");
				streamOut.flush();
			} else {
				System.out.println(ID + ": Stream Closed");
			}
		}
         catch(IOException e) {  
         	Trace.getInstance().exception(this,e);
         	stop();
         }
   }
	

   public void handle (String msg) {
   		if (msg.equalsIgnoreCase("quit!")) {  
			System.out.println(ID + "Good bye. Press RETURN to exit ...");
			stop();
		} 
   		//from server
   		else if (msg.equalsIgnoreCase("launch game ready screen")){
   			gameScreenLaunched = true;
   			gui = new GUIController(this);
   			gui.launchGameReadyWindow();   			
   		}
   		//from gui
   		else if (msg.contains("tokenRequest")){
   			System.out.println("tokenRequest");
   			
   			sendMessageToServer("drawToken");
   		}
   		//from server   		
   		else if (msg.contains("drawToken")){
   			String[] tokenNumber = msg.split(",");
   			int tn = Integer.parseInt(tokenNumber[1]);
   			System.out.println("Client: drawToken, token number: "+tn);
   			gui.displayTokenColour(tn);
   		}
   		//from gui
   		else if (msg.contains("joinGame")){
   			System.out.println("Client:joinGame:"+msg);
   			joinGame = true;
   			sendMessageToServer(msg);
   		}

   		//from server to gui
   		else if (msg.contains("openMainGameScreen")){
   			System.out.println("~~~~~~~Client: launchMainGameScreen");
   			gui.launchGamePlayWindow();
   			//gui.setAllPlayersInfo("");
   		}
   		
   		else if (msg.contains("updateGameScreen")){
   			gui.setAllPlayersInfo("lol");
   			System.out.println("~~~~~~~~Client: updateGameScreen");
   		}
   		
   		else if (msg.contains("gameReady")){
   			gameReady = true;
   		}
   		
   		//else if (msg.equalsIgnoreCase("launchMainGameCurrentPlayer")){
   		//	System.out.println("launchMainGameScreen currentPlayer");
   		//}
   		//from server
   		else if (msg.contains("GAMEINFORMATION~")){
   			System.out.println("message to client containing GAMEINFORMATION~");
   			updateAllPlayersInfo = true;
   			gui.setAllPlayersInfo(msg);
   		}
   		//from server
   		else if (msg.contains("PLAYERHAND~")){
   			System.out.println("message to specific player containing PLAYERHAND~");
   			updateShowPlayerHand = true;
   			gui.showPlayerHand(msg);
   		}
   		else {
			System.out.println(msg);
		}
   }

   public void cardPlayed(String msg){
	   String message = "cardPlayed" + msg;
	   //sendMessageToServer(message);
	   cardPlayed = true;
   }
   
   
   public void stop() {  
      try { 
      	if (thread != null) thread = null;
    	  	if (console != null) console.close();
    	  	if (streamIn != null) streamIn.close();
    	  	if (streamOut != null) streamOut.close();

    	  	if (socket != null) socket.close();

    	  	this.socket = null;
    	  	this.console = null;
    	  	this.streamIn = null;
    	  	this.streamOut = null;    	  
      } catch(IOException ioe) {  
    	  Trace.getInstance().exception(this,ioe);
      }
      client.close();  
   }

	public Boolean isConnected() {
		return connected;
	}

	public Object getGameScreenLaunched() {
		return gameScreenLaunched;
	}

	public Object getUpdateAllPlayersInfo() {
		return updateAllPlayersInfo;
	}
	
	public Object getUpdateShowPlayerHand() {
		return updateShowPlayerHand;
	}
	
	public Object getCardPlayed() {
		return cardPlayed;
	}
	
	public Object getGameReady(){
		return gameReady;
	}
	
	public Object getJoinGame(){
		return joinGame;
	}
}
