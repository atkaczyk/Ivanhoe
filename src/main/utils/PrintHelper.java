package utils;

import java.util.ArrayDeque;
import java.util.Iterator;

import logic.Card;

public class PrintHelper {
	public static void printCards(ArrayDeque<Card> cards) {
		for(Iterator itr = cards.iterator();itr.hasNext();)  {
			System.out.println(itr.next());
		}
	}
}
