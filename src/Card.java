
//	Represents the data of a specific Card in a Deck
//		Along with the usual suit and number, this will also keep track of posiiton througout the Deck

import java.util.ArrayList;

public class Card {

	protected String suit;
	protected String face;
	
	protected ArrayList<Integer> position;
	
	public Card(String face, String suit) {
		setFace(face);
		setSuit(suit);
		
		position = new ArrayList<Integer>();
	}
	
	public String getSuit() {
		return suit;
	}
	
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public String getFace() {
		return suit;
	}
	
	public void setFace(String face) {
		this.face = face;
	}
	
	
	//	Todo
//	public void updatePosition(Deck parent) {
//		int pos = 0;
//		
//		position.add(pos);
//	}
	
	public void addPosition(int pos) {
		position.add(pos);
	}
	
	public ArrayList<Integer> getPosition() {
		return position;
	}
	
	public int getCurrentPosition() {
		return position.get(position.size() - 1);
	}
	
	public void clearPosition() {
		position.clear();
	}
	
	
	public int[] aggregatePosition() {
		
		if(position.size() == 1)
			return new int[] {0};
		
		int[] deltaPos = new int[position.size() - 1];
		
		
		//	Gets the change in position
		for(int i = 0; i < position.size() - 1; i++)
			deltaPos[i] = position.get(i + 1) - position.get(i);
		
		return deltaPos;
	}
	
	//	Takes all change to be positive
	public double averageDelta() {
		int[] aggPos = aggregatePosition();
		
		double aveDel = 0;
		for(int i = 0; i < aggPos.length; i++)
			aveDel += Math.abs(aggPos[i]);
		
		aveDel /= aggPos.length;
		
		return aveDel;
	}
	
	
	
	public String toString() {
		return face + " of " + suit;
	}
}
