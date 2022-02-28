
//	Contains a handful of output functions

public class View {

	public static void deck(Deck deck) {
		System.out.println("\tDeck");
		System.out.println(deck);
	}
	
	public static void position(Deck deck) {
		
		System.out.println("\tCard Positions");
		
		String out = "";
		
		for(int i = 0; i < deck.getSize(); i++) {
			for(Integer pos : deck.getCard(i).getPosition())
				out += pos + ", ";
			out += "\n";
		}
		
		System.out.println(out);
	}
	
	public static void deltaPosition(Deck deck) {
		System.out.println("\tDelta Positions");
		
		for(int i = 0; i < deck.getSize(); i++) {
			
			int[] deltaPos = deck.getCard(i).aggregatePosition();
			String out = "";
			
			for(Integer pos : deltaPos)
				out += pos + ", ";
			
			System.out.println(out);
			
		}
	}
	
	public static void averageDelta(Deck deck) {
		System.out.println("\tAverage Delta Position");
		
		for(int i = 0; i < deck.getSize(); i++)
			System.out.println(deck.getCard(i).averageDelta());
	}
	
}
