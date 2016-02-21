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
		gameReadyWindow.setVisible(true);

		do {			
		} while(tokenRequest == false);
		tokenRequest = gameReadyWindow.getTokenRequest();
	}
	
	public void launchGamePlayWindow(){
		gameReadyWindow.setVisible(false);
		gamePlayWindow.setVisible(true);
		
	}
}
