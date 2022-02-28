
//	Contains all of the standard actions of deck manipulation, such as shuffling

public class Dealer {

	//	Chooses the shuffle operation
	public static Deck shuffle(Deck deck, String shuffle, double other) {
		return shuffle(deck, shuffle, 1, other);
	}
	
	public static Deck shuffle(Deck deck, String shuffle, int repetitions, double other) {
		switch(shuffle) {
			case "outerFaro" 	: 	return deck = outerFaroShuffle(deck, repetitions);
			case "innerFaro"	: 	return deck = innerFaroShuffle(deck, repetitions);
			case "randomDraw" 	:	return deck = randomDrawShuffle(deck, repetitions);
			case "fiftyShuffle"	:	return deck = fiftyFiftyShuffle(deck, repetitions);
			case "humanShuffle"	:	return deck = humanShuffle(deck, repetitions, other);
		}
		
		return null;
	}
	
	
	
	
	//	Fills a deck with the standard 52 cards
	public static void standardPopulate(Deck deck) {
		String[] faces = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
		String[] facesSimple = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
		
		String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
		
		for(int suitIndex = 0; suitIndex < suits.length; suitIndex++)
			for(int faceIndex = 0; faceIndex < faces.length; faceIndex++)
				deck.addCard(new Card(facesSimple[faceIndex], suits[suitIndex]));
	}
	
	
	public static Deck split(Deck toSplit, int index) {
		Deck splitDeck = new Deck();
		
		
		//	Moves all cards after the index into the new deck
		while(toSplit.getCard(index) != null)
			splitDeck.move(toSplit.removeCard(index));
		
		return splitDeck;
	}
	
	
	
	
	//	Performs #repetition amount of outer Faro shuffles
	//		Faro: one card from left, one card from right, one card from left...; perfectly interwoven
	//		Outer: the top card stays on the top, the bottom stays on the bottom
	public static Deck outerFaroShuffle(Deck deck, int repetitions) {
		for(int i = 0; i < repetitions; i++)
			deck = outerFaroShuffle(deck);
		
		return deck;
	}
	
	public static Deck outerFaroShuffle(Deck deck) {
		Deck split = split(deck, deck.getSize() / 2);
		Deck out = new Deck();
		
		while(!deck.isEmpty() && !split.isEmpty()) {
			if(!deck.isEmpty())
				out.drawFrom(deck);
			
			if(!split.isEmpty())
				out.drawFrom(split);
		}
		
		return out;
	}
	
	
	
	//	Performs #repetition amount of inner Faro shuffles
	//		Faro: one card from left, one card from right, one card from left...; perfectly interwoven
	//		Inner: the top card moves down one position, the bottom card moves up one position
	public static Deck innerFaroShuffle(Deck deck, int repetitions) {
		for(int i = 0; i < repetitions; i++)
			deck = innerFaroShuffle(deck);
		
		return deck;
	}
	
	public static Deck innerFaroShuffle(Deck deck) {
		Deck split = split(deck, deck.getSize() / 2);
		Deck out = new Deck();
		
		while(!deck.isEmpty() && !split.isEmpty()) {
			if(!split.isEmpty())
				out.drawFrom(split);
			
			if(!deck.isEmpty())
				out.drawFrom(deck);
		}
		
		return out;
	}

	
	
	
	//	Performs #repetition amount of random draw shuffles
	//		Chooses a random number from 1-deckSize, then adds that card to a new deck; returns the new deck
	public static Deck randomDrawShuffle(Deck deck, int repetitions) {
		for(int i = 0; i < repetitions; i++)
			deck = randomDrawShuffle(deck);
		
		return deck;
	}
	
	public static Deck randomDrawShuffle(Deck deck) {
		Deck out = new Deck();
		
		while(!deck.isEmpty())
			out.addCard(deck.removeCard((int)(Math.random() * deck.getSize())));
		
		return out;
	}
	
	
	
	//	Performs #repetition amount of 50-50 shuffles
	//		At each step, has a 50% chance of choosing a card from either deck and moving into the out deck
	public static Deck fiftyFiftyShuffle(Deck deck, int repetitions) {
		for(int i = 0; i < repetitions; i++)
			deck = fiftyFiftyShuffle(deck);
		
		return deck;
	}
	
	public static Deck fiftyFiftyShuffle(Deck deck) {
		Deck split = split(deck, deck.getSize() / 2);
		Deck out = new Deck();
		
		while(!deck.isEmpty() || !split.isEmpty()) {
			if(deck.isEmpty())
				out.drawFrom(split);
			else if(split.isEmpty())
				out.drawFrom(deck);
			else if(Math.random() < 0.5)
				out.drawFrom(deck);
			else
				out.drawFrom(split);
		}
		
		return out;
	}
	
	//	Performs #repetition amount of "human" shuffles
	//		Tries to perform Faro shuffles, but imperfectly
	//			Starts by randomly choosing a card from either the left or right deck
	//			Then has a #odds chance of not messing up the order each round
	public static Deck humanShuffle(Deck deck, int repetitions, double odds) {
		for(int i = 0; i < repetitions; i++)
			deck = humanShuffle(deck, odds);
		
		return deck;
	}
	
	
	public static Deck humanShuffle(Deck deck, double odds) {
		Deck split = split(deck, deck.getSize() / 2);
		Deck out = new Deck();
		
		boolean outer = true;
		if(Math.random() < 0.5)
			outer = !outer;
		
		
		while(!deck.isEmpty() || !split.isEmpty()) {
			
			if(!deck.isEmpty() && outer || split.isEmpty())
				out.drawFrom(deck);
			else if(!split.isEmpty() && !outer || deck.isEmpty())
				out.drawFrom(split);
			
			
			if(Math.random() < odds)
				outer = !outer;
		}
		
		
		return out;
	}
}
