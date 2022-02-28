
//	Skyler A. is the dealer of this code
//		Created on 2021-10-16

//	I've always been curious as to the 'best' way to shuffle, and how single cards get individually moved around a deck
//		This program has been built to investigate that

public class DeckShuffleStartup {

	public static void main(String[] args) {
		
		//	Initialises the plotter
		Plot.initialise();
		
		
		//	Creates a deck & populates it
		Deck deck = new Deck();
		Dealer.standardPopulate(deck);
		
		
		//	Shuffles the deck
		//		"outerFaro"
		//		"innerFaro"
		//		"randomDraw"
		//		"fiftyShuffle"
		//		"humanShuffle"
		
		int shuffles = 26;
		deck = Dealer.shuffle(deck, "humanShuffle", shuffles, 0.75);
		
		
		//	Shows some data
		View.deck(deck);
		View.position(deck);
		View.deltaPosition(deck);
		View.averageDelta(deck);

		System.out.println("\n");
		System.out.println("Average change in position:\t" + deck.averageDelta() + "\nVariance in change in position:\t" + deck.getVariance());
		
		
		//	Draws the plot
		Plot.drawPlot(deck, "Variance", shuffles, deck.getSize(), 13);
	}

}
