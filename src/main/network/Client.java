package network;

import java.net.*;
import java.io.*;

import logic.Game;
import userinterface.GUIController;
import utils.Trace;

public class Client {
	
	private userinterface.GUIController gui;
	
	private int ID = 0;
	private Socket socket            = null;
	private Thread thread            = null;
	private ClientThread   client    = null;
	private BufferedReader console   = null;
	private BufferedReader streamIn  = null;
	private BufferedWriter streamOut = null;
	
	private Boolean connected = false;
	private Boolean gameScreenLaunched = false;
	private Boolean updateAllPlayersInfo = false;
	private Boolean updateShowPlayerHand = false;
	private Boolean cardPlayed = false;
	private Boolean gameReady = false;
	private Boolean joinGame = false;
	private Boolean withdraw = false;
	private Boolean winner = false;
	private Boolean finalWinner = false;
	private Boolean playerActive = false;
	private Boolean tournamentInfo = false;
	private Boolean tokenRequest = false;
	private Boolean updateGameInfo = false;
	
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
   			tokenRequest = true;
   			sendMessageToServer("drawToken");
   		}
   		//from server   		
   		else if (msg.contains("drawToken")){
   			String[] tokenNumber = msg.split(",");
   			int tn = Integer.parseInt(tokenNumber[1]);
   			gui.displayTokenColour(tn);
   		}
   		//from gui
   		else if (msg.contains("joinGame")){
   			joinGame = true;
   			sendMessageToServer(msg);
   		}
   		//from server to gui
   		else if (msg.contains("openMainGameScreen")){
   			gui.launchGamePlayWindow();
   		}
   		//from gui
   		else if (msg.contains("updateGameInformation")){
   			sendMessageToServer(msg);
   		}
   		//from server to gui
   		else if (msg.contains("PLAYERINFORMATION~")){
   			String[] gameInfo = msg.split("~");
   			String gi = gameInfo[1].substring(1);
   			updateAllPlayersInfo = true;
   			gui.setAllPlayersInfo(gi);
   		}
   		//from server to gui
   		else if (msg.contains("PLAYERHAND~")){
   			String[] playerHand = msg.split("~");
   			updateShowPlayerHand = true;
   			gui.showPlayerHand(playerHand[1]);
   		}
   		//from server to gui
   		else if(msg.contains("PLAYERSPECIFICINFO~")){
   			playerActive = true;
   			String[] result = msg.split("~");
   			String[] playerInfo = result[1].split(",");
   			System.out.println("CLIENT: PLAYERSPECIFICINFO: "+result[1]);
   			gui.setEnableMainScreen(playerInfo[0]);
   			gui.setCurrentPlayerName(playerInfo[1]);
   		}
   		//from server to gui
   		else if(msg.contains("TOURNAMENTINFO~")){
   			tournamentInfo = true;
   			String[] result = msg.split("~");
   			System.out.println("CLIENT: TOURNAMENTINFO: "+result[1]);
   			gui.setTournamentInfo(result[1]);   			
   		}
   		//from server to gui
//   		else if(msg.contains("PLAYERSPECIFICINFO~")){
//   			String[] result = msg.split("~");
//   			System.out.println("CLIENT: PLAYERSPECIFICINFO: "+result[1]);
//   			
//   		}
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
   		//from gui to server
   		else if(msg.contains("requestToDrawCard")){
   			System.out.println("I NEED TO DRAW CARD");
   			//sends me card to send to game...the file name
   			cardPlayed(msg);
   		}
   		else if(msg.contains("requestToPlayThisCard")){
   			//sends me card to send to game...the file name
   			cardPlayed(msg);
   		}
   		//from server to gui
   		else if(msg.contains("actionCardPlayedMessage")){
   			String messageToDisplay = msg.split("~")[1];
   			System.out.println("CLIENT: actionCardPlayedMessage: "+messageToDisplay);
   			gui.actionCardPlayedMessage(messageToDisplay);
   		}
   		//from gui to server
   		else if(msg.contains("requestToEndTurn")){
   			System.out.println("CLIENT: requestToEndTurn");
   			sendMessageToServer(msg);
   		}
   		//from gui to server
   		else if(msg.contains("requestToWithdraw")){
   			System.out.println("CLIENT: requestToWithdraw");
   			withdraw = true;
   			sendMessageToServer(msg);
   		}
   		//from server to gui
   		else if(msg.contains("PurpleWinTokenChoice~")){
   			String[] choices = msg.split("~");
   			System.out.println("CLIENT: PurpleWinTokenChoice~: "+choices[1]);
   			gui.launchPurpleWinTokenChoice(choices[1]);
   		}
   		//from gui to server
   		else if(msg.contains("PurpleWinTokenColourChoice~")){
   			System.out.println("CLIENT: PurpleWinTokenColourChoice~"+msg);
   			sendMessageToServer(msg);
   		}
   		//from server to gui
   		else if(msg.contains("tournamentWinner~")){
   			System.out.println("\n\nI RECEIVED:"+msg);
   			winner = true;
   			String[] winningT = msg.split("~");
   			System.out.println("CLIENT: winner: "+winningT[1]);
   			gui.displayTournamentWinner(winningT[1]);
   		}
   		//from gui to server
   		else if(msg.contains("finalWinnerCheck")){
   			winner = true;
   			System.out.println("CLIENT: finalWinnerCheck");
   			finalWinner = true;
   			sendMessageToServer(msg);
   		}
   		//from server to gui
   		else if(msg.contains("gameWinner~")){
   			String[] gWinner = msg.split("~");
   			System.out.println("CLIENT: gameWinner: "+gWinner[1]);
   			gui.displayFinalWinner(gWinner[1]);
   		}
   		//setGameStats
   		
   		
   		else if(msg.contains("ERROR~")){
   			String[] errMessage = msg.split("~");
   			System.out.println("CLIENT: ERROR message: "+errMessage[1]);
   			gui.displayErrorMessage(errMessage[1]);
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
	
	public Object getWithdraw(){
		return withdraw;
	}
	public Object getWinner(){
		return winner;
	}
	public Object getFinalWinner(){
		return finalWinner;
	}
	public Object getPlayerActive(){
		return playerActive;
	}
	public Object getTournamentInfo(){
		return tournamentInfo;
	}
	public Object getTokenRequest(){
		return tokenRequest;
	}
	public Object getUpdateGameInfo(){
		return updateGameInfo;
	}
}
