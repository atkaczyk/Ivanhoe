package utils;

import java.util.HashMap;
import java.util.Map;

public class Config {
	public static final int PURPLE = 0;
	public static final int RED = 1;
	public static final int YELLOW = 2;
	public static final int GREEN = 3;
	public static final int BLUE = 4;
	public static final int WHITE = 5;
	
	public static final int[] ALL_TOKEN_COLOURS = {PURPLE, RED, YELLOW, GREEN, BLUE};
	public static final String [] TOKEN_COLOUR_NAMES = {"Purple", "Red", "Yellow", "Green", "Blue"};
	
	public static final int MAX_CLIENTS = 5;
	public static final int DEFAULT_PORT = 5050;
	public static final String DEFAULT_HOST = "127.0.0.1";
	public static final boolean PRINT_STACK_TRACE = false;
	
	public static final Map<String, String> CARD_NAME_TO_PICTURES = new HashMap<String, String>();
	static {
		CARD_NAME_TO_PICTURES.put("Blue (Axe) 2","blue2.jpg");
		CARD_NAME_TO_PICTURES.put("Blue (Axe) 3","blue3.jpg");
		CARD_NAME_TO_PICTURES.put("Blue (Axe) 4","blue4.jpg");
		CARD_NAME_TO_PICTURES.put("Blue (Axe) 5","blue5.jpg");
		CARD_NAME_TO_PICTURES.put("Green (No Weapon) 1","green1.jpg");
		CARD_NAME_TO_PICTURES.put("Maiden 6","maiden6.jpg");
		CARD_NAME_TO_PICTURES.put("Purple (Jousting) 3","purple3.jpg");
		CARD_NAME_TO_PICTURES.put("Purple (Jousting) 4","purple4.jpg");
		CARD_NAME_TO_PICTURES.put("Purple (Jousting) 5","purple5.jpg");
		CARD_NAME_TO_PICTURES.put("Purple (Jousting) 7","purple7.jpg");
		CARD_NAME_TO_PICTURES.put("Red (Sword) 3","red3.jpg");	
		CARD_NAME_TO_PICTURES.put("Red (Sword) 4","red4.jpg");
		CARD_NAME_TO_PICTURES.put("Red (Sword) 5","red5.jpg");
		CARD_NAME_TO_PICTURES.put("Squire 2","squire2.jpg");
		CARD_NAME_TO_PICTURES.put("Squire 3","squire3.jpg");
		CARD_NAME_TO_PICTURES.put("Yellow (Morningstar) 2","yellow2.jpg");
		CARD_NAME_TO_PICTURES.put("Yellow (Morningstar) 3","yellow3.jpg");
		CARD_NAME_TO_PICTURES.put("Yellow (Morningstar) 4","yellow4.jpg");
		CARD_NAME_TO_PICTURES.put("Adapt","adapt.jpg");
		CARD_NAME_TO_PICTURES.put("Break Lance","breaklance.jpg");
		CARD_NAME_TO_PICTURES.put("Change Weapon","changeweapon.jpg");
		CARD_NAME_TO_PICTURES.put("Charge","charge.jpg");
		CARD_NAME_TO_PICTURES.put("Countercharge","countercharge.jpg");
		CARD_NAME_TO_PICTURES.put("Disgrace","disgrace.jpg");
		CARD_NAME_TO_PICTURES.put("Dodge","dodge.jpg");
		CARD_NAME_TO_PICTURES.put("Drop Weapon","dropweapon.jpg");
		CARD_NAME_TO_PICTURES.put("Ivanhoe","ivanhoe.jpg");
		CARD_NAME_TO_PICTURES.put("Knock Down","knockdown.jpg");
		CARD_NAME_TO_PICTURES.put("Outmaneuver","outmaneuver.jpg");
		CARD_NAME_TO_PICTURES.put("Outwit","outwit.jpg");
		CARD_NAME_TO_PICTURES.put("Retreat","retreat.jpg");
		CARD_NAME_TO_PICTURES.put("Riposte","riposte.jpg");
		CARD_NAME_TO_PICTURES.put("Shield","shield.jpg");
		CARD_NAME_TO_PICTURES.put("Stunned","stunned.jpg");
		CARD_NAME_TO_PICTURES.put("Unhorse","unhorse.jpg");
	}

}
