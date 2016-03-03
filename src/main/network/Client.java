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
				streamOut.flush();
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
   		}
   		//from gui
   		else if (msg.contains("updateGameInformation")){
   			System.out.println("~~~~~~~~Client: updateGameInformation");
   			sendMessageToServer(msg);
   			//call server and get information
   		}
   		//from server to gui
   		else if (msg.contains("GAMEINFORMATION~")){
   			System.out.println("~~~CLIENT: message to client containing GAMEINFORMATION~");
   			String[] gameInfo = msg.split("~");
   			String gi = gameInfo[1].substring(1);
   			updateAllPlayersInfo = true;
   			gui.setAllPlayersInfo(gi);
   		}
   		//from server to gui
   		else if (msg.contains("PLAYERHAND~")){
   			System.out.println("~~~CLIENT: message to specific player containing PLAYERHAND~");
   			String[] playerHand = msg.split("~");
   			updateShowPlayerHand = true;
   			gui.showPlayerHand(playerHand[1]);
   		}
   		//from server to gui
   		else if(msg.contains("launchTournamentColour")){
   			System.out.println("CLIENT: launchTournamentColour");
   			gui.launchTournamentColour();
   		}
   		//from gui to server
   		else if(msg.contains("TournamentColourRequest")){
   			System.out.println("CLIENT: TournamentColourRequest");
   			sendMessageToServer(msg);
   		}
   		//from server to gui
   		else if(msg.contains("setTournamentColour~")){
   			System.out.println("CLIENT: setTournamentColour~");
   			String[] colour = msg.split("~");
   			gui.setTournamentColour(colour[1]);;
   			
   		}
   		
   		else if(msg.contains("requestToPlayThisCard")){
   			//sends me card to send to game...the file name
   			cardPlayed(msg);
   		}
   		
   		else if (msg.contains("gameReady")){
   			gameReady = true;
   		}
   		
   		//else if (msg.equalsIgnoreCase("launchMainGameCurrentPlayer")){
   		//	System.out.println("launchMainGameScreen currentPlayer");
   		//}

   		
   		else {
			System.out.println(msg);
		}
   }

   public void cardPlayed(String msg){
	   cardPlayed = true;
	   sendMessageToServer(msg);
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
