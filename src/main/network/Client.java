package network;

import java.net.*;
import java.io.*;

import logic.Game;
import userinterface.GUIController;
import utils.Trace;

public class Client implements Runnable {
	
	//private GUIController gui = new GUIController();
	private GUIController gui;
	
	
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
	}
	


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
		      thread = new Thread(this);                   
		      thread.start();
		      connected = true;
		   }
	   } catch (IOException ioe) {
      	Trace.getInstance().exception(this,ioe);
         throw ioe;
	   }
   }

	public void run() { 
		
		//running all the time
		//was looking at the console
		//take a msg
		
		
		System.out.println(ID + ": Client Started...");
		while (thread != null) {  
			try {  
				if (streamOut != null) {
					streamOut.flush();
					streamOut.write(console.readLine() + "\n");
				} else {
					System.out.println(ID + ": Stream Closed");
				}
         }
         catch(IOException e) {  
         	Trace.getInstance().exception(this,e);
         	stop();
         }}
		System.out.println(ID + ": Client Stopped...");
   }
	

	public void sendMessageToServer(String msg) { 
		
		//running all the time
		//was looking at the console
		//take a msg
		System.out.println("sending a message to the server");
		try {  
			if (streamOut != null) {
				streamOut.flush();
				streamOut.write(msg + "\n");
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
   		else if (msg.equalsIgnoreCase("launch game ready screen")){
   			gameScreenLaunched = true;
   			gui.launchGameReadyWindow();
   			// wait for an action change
   			// recieve getToken
   			//if (gui.tokenRequest == true){
   			//	client.send();
   			//}
   				
   			
   		}
   		
   		else if (msg.contains("tokenRequest")){
   			System.out.println("tokenRequest");
   			sendMessageToServer("drawToken");
   		}
   		
   		else if (msg.contains("drawToken")){
   			System.out.println("drawToken");
   			String[] tokenNumber = msg.split(" ");
   			//gui.displayTokenColour(tokenNumber[1]);
   			
   			//when UI status is Config.PLAYER_READY == true){
   				//String message = gui.getPlayerInformation();
   				//send the message to the server handle???? using message "gameReady"  			
   			
   		}
   		else if (msg.contains("gameReady")){
   			
   		}
   		else if (msg.equalsIgnoreCase("launchMainGameScreen")){
   			System.out.println("launchMainGameScreen");
   			//gui.launchGamePlayWindow();
   		}
   		else if (msg.equalsIgnoreCase("launchMainGameCurrentPlayer")){
   			System.out.println("launchMainGameScreen currentPlayer");
   		}
   		else if (msg.contains("GAMEINFORMATION~")){
   			System.out.println("message to client containing GAMEINFORMATION~");
   			updateAllPlayersInfo = true;
   			//gui.setAllPlayersInfo(msg);
   		}
   		else if (msg.contains("PLAYERHAND~")){
   			System.out.println("message to specific player containing PLAYERHAND~");
   			updateShowPlayerHand = true;
   			//gui.showPlayerHand(msg);
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
}
