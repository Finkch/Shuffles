
//	This class contains the information for a Deck of cards
//		These decks can range from 1-N cards big, but will usually start wiht the standard 52

import java.util.ArrayList;

public class Deck {

	protected ArrayList<Card> deck;
	
	
	public Deck() {
		deck = new ArrayList<Card>();
	}
	
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public int getSize() {
		return deck.size();
	}
	
	
	public Card removeCard(int index) {
		return deck.remove(index);
	}
	
	public Card draw() {
		return removeCard(0);
	}
	
	public void drawFrom(Deck drawFrom) {
		addCard(drawFrom.draw());
	}
	
	
	public void addCard(Card in) {
		in.addPosition(getSize());
		deck.add(in);
	}
	
	//	Same as add, except doesn't update position
	public void move(Card in) {
		deck.add(in);
	}
	
	
	public Card getCard(int index) {
		
		if(index >= getSize() || index < 0)
			return null;
		
		return deck.get(index);
	}
	
	
	
	public void updatePositions() {
		for(int i = 0; i < deck.size(); i++)
			deck.get(i).addPosition(i);
	}
	
	
	
	
	
	public boolean isEmpty() {
		return getSize() == 0;
	}
	
	
	
	
	public double averageDelta() {
		double average = 0;
		
		for(Card card : deck)
			average += card.averageDelta();
		
		return average / getSize();
	}
	
	public double getVariance() {		
		double averageDelta = averageDelta();;
		double averageSquare = 0;
		
		
		for(Card card : deck)
			averageSquare += Math.pow(card.averageDelta(), 2);
		
		averageSquare /= getSize();
		
		
		return averageSquare - Math.pow(averageDelta, 2);
	}
	
	
	
	
	public String toString() {
		String out = "";
		
		for(Card card : deck)
			out += card.toString() + "\n";
		
		return out;
	}
}
