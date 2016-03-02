package network;

import java.net.*;
import java.io.*;

import utils.Trace;

public class ServerThread extends Thread {
	private int ID = -1;
	private Socket socket = null;
	private Server server = null;
	private BufferedReader streamIn = null;
	private BufferedWriter streamOut = null;
	
	private String clientAddress = null;;

	private boolean done = false;

	public ServerThread(Server server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		this.ID = socket.getPort();
		this.clientAddress = socket.getInetAddress().getHostAddress();
	}

	public int getID() {
		return this.ID;
	}

	public String getSocketAddress () {
		return clientAddress;
	}
	
	/** The server processes the messages and passes it to the client to send it */
	public void send(String msg) {
		try {
			streamOut.flush();
			streamOut.write(msg);
			streamOut.flush();
		} catch (IOException ioe) {
			Trace.getInstance().exception(this,ioe,ID);
			server.remove(ID);
		}
	}

	/** server thread that listens for incoming message from the client
	 * on the assigned port 
	 * */
	public void run() {
		//Trace.getInstance().write(this, "Server Thread Running", ID);
		while (!done) {
			try {
				
				String s = streamIn.readLine();
				if (s != null) {
					/** Received a message and pass to the server to handle */
					server.handle(ID, s);
				}
			} catch (IOException ioe) {
				Trace.getInstance().exception(this,ioe);
				server.remove(ID);
				break;
			}
		}
	}

	public void open() throws IOException {
		streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		streamOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		send("launch game ready screen\n");
	}

	public void close() {
		try {
			if (socket != null) socket.close();
			if (streamIn != null) streamIn.close();
			
			this.done = true;
			this.socket = null;
			this.streamIn = null;
		} catch (IOException e) { }
	}

}