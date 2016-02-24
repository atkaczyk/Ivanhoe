package userinterface;

public class GUIController {
	GameReadyWindow gameReadyWindow;
	GamePlayWindow gamePlayWindow;
	
	
	private boolean tokenRequest;
	
	public GUIController(){
		boolean tokenRequest;		
		gameReadyWindow = new GameReadyWindow();
		gamePlayWindow = new GamePlayWindow();
	}
	
	public void launchGameReadyWindow(){
		GameReadyWindow startWindow = new GameReadyWindow();
		startWindow.setVisible(true);
		//do {			
		//} while(tokenRequest == false);
		//tokenRequest = gameReadyWindow.getTokenRequest();
	}
	
	public void launchGamePlayWindow(){
		gameReadyWindow.setVisible(false);
		gamePlayWindow.setVisible(true);
		
	}
	public void setAllPlayersInfo(String str){
		
	}
	
	//retrieves all cards in the players hand
	public void showPlayerHand(String str){
	
	}
	
	
	public void sendClient(){
		//client.send(gameReadyWindow.getTokenRequest());
	}
}
