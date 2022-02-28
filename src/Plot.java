import java.awt.Font;
import java.util.ArrayList;

public class Plot {

	private static final int width = 800;
	private static final int height = width;
	
	private static final double smallBuffer = 0.05;
	private static final double s 			= 1 - smallBuffer;
	private static final double center 		= s / 2 + smallBuffer;
	
	
	public static void initialise() {
		
		//	Creates the canvas
		StdDraw.setCanvasSize(width, height);
		
		//	Turns off animation mode
		StdDraw.show();
		
		// Makes sure the starting frame is black
		clear();
	}
	
	
	public static void drawPlot(Deck deck, String yAxis, int shuffles, int yScale) {
		drawPlot(deck, yAxis, shuffles, yScale, 10000);
	}
	
	public static void drawPlot(Deck deck, String yAxis, int shuffles, int yScale, int frequency) {
	
		clear();
	
		drawAxis(yAxis);
		xScaleLabel(shuffles, 0);
		yScaleLabel(yScale);
		
		switch(yAxis) {
			case "Variance"	:	plotVariance(deck, yScale, frequency);
								break;
			case "Position"	:	plotPosition(deck, yScale, frequency, 1);
								break;
		}
		
		StdDraw.show();
	}
	
	
	
	//	Clears the canvas
	private static void clear() {
		StdDraw.clear(StdDraw.BLACK);
	}

	
	//	Plots a line
	private static void plotPosition(Deck deck, int yScale, int frequency, int offset) {
		StdDraw.setPenRadius(0.002);
		
		for(int i = 0; i < deck.getSize(); i++)
			if(i == 0 || i == 51 || i % frequency == 0)
				plot(deck.getCard(i).getPosition(), yScale, offset);
		
		StdDraw.setPenRadius(0.001);
	}
	
	private static void plotVariance(Deck deck, int yScale, int frequency) {
		double[] average = new double[deck.getCard(0).aggregatePosition().length];
		int [] intAve = new int[average.length];
		for(int i = 0; i < average.length; i++)
			average[i] = 0;
		
		StdDraw.setPenRadius(0.002);
		
		for(int i = 0; i < deck.getSize(); i++) {
			
			//	Plots the change in position for the selected cards
			if(i == 0 || i == 51 || i % frequency == 0)
				plot(deck.getCard(i).aggregatePosition(), yScale);
			
			
			//	Adds the change in position to a new array
			//		For every card, not just selected cards
			for(int j = 0; j < deck.getCard(i).aggregatePosition().length; j++)
				average[j] += Math.abs(deck.getCard(i).aggregatePosition()[j]);
		}
		
		//	Finds the average change in position each step
		for(int i = 0; i < average.length; i++)
			intAve[i] = (int)(average[i] /= deck.getSize());
		
		
		//	Draws the average change in position
		StdDraw.setPenRadius(0.005);
		plot(intAve, yScale);
		StdDraw.setPenRadius(0.001);
		
	}
	
	private static void plot(ArrayList<Integer> nums, int yScale, int offset) {
		
		//	Gets the scale of each piece
		double screenRange	= 1 - smallBuffer;
		double xRange		= screenRange / (nums.size() - 1);
		double yRange		= screenRange / yScale;
		
		//	Creates the line
		//		Iterates backwards through nums as, for whatever reason, nums is backwards
		//			I could fix it from the other end, but frankly I don't know why it do as it do
		for(int i = 0; i < nums.size() - 1; i++)
			StdDraw.line(i * xRange + smallBuffer, (nums.get(nums.size() - i - 1) + offset) * yRange + smallBuffer, (i + 1) * xRange + smallBuffer, (nums.get(nums.size() - i - 2) + offset) * yRange + smallBuffer);
	}
	
	private static void plot(int[] nums, int yScale) {
		
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		for(int i = 0; i < nums.length; i++)
			arrList.add(Math.abs(nums[i]));
		
		plot(arrList, yScale, 0);
	}
	
	
	//	Does the work of drawing the plot
	private static void drawAxis(String yAxis) {
		
		//	Updates the pen
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.setPenRadius(0.001);
			
		//	Horizontal line with some buffer on either end
		StdDraw.line(smallBuffer, smallBuffer, 1, smallBuffer);
		
		//	Vertical line with some buffer on either end
		StdDraw.line(smallBuffer, smallBuffer, smallBuffer, 1);
		
		//	Labels the horizontal
		StdDraw.text((1 - smallBuffer) / 2 + smallBuffer, 0 - smallBuffer / 1.5, "Shuffles");
		
		//	Labels the vertical
		StdDraw.text(0 - smallBuffer / 1.5, (1 - smallBuffer) / 2 + smallBuffer, yAxis, 90);
	}
	
	
	
	//	Draws both ticks and text along an x-Axis
	private static void xScaleLabel(int shuffles, double yPos) {
		xScaleLabelTick(shuffles, yPos);
		xScaleLabelText(shuffles, yPos);
	}
	
	//	Draws tick marks along an x-axis
	private static void xScaleLabelTick(int shuffles, double yPos) {
		
		//	Determines the required x-axis scale
		double xPos = (1 - smallBuffer) / shuffles;
		
		
		//	Draws a line per scale mark
		for(int i = 1; i <= shuffles; i++) {
			
			if(shuffles >= 20 && i % 2 == 0) {
				StdDraw.line(i * xPos + smallBuffer, smallBuffer + yPos, i * xPos + smallBuffer, smallBuffer - smallBuffer / 3 + yPos);
			}
			else if(shuffles >= 20) {
				StdDraw.line(i * xPos + smallBuffer, smallBuffer + yPos, i * xPos + smallBuffer, smallBuffer - smallBuffer / 5 + yPos);
			}
			else {
				StdDraw.line(i * xPos + smallBuffer, smallBuffer + yPos, i * xPos + smallBuffer, smallBuffer - smallBuffer / 3 + yPos);	
			}
		}
	}
	
	//	Draws text along ax x-axis
	private static void xScaleLabelText(int shuffles, double yPos) {
		
		//	Sets the font to look nice
		setFontSize(12);
		
		//	Determines the required x-axis scale
		double xPos = (1 - smallBuffer) / shuffles;
		
		
//		//	Draws a line per scale mark
//		for(int i = 1; i <= shuffles; i++) {
//			
//			if(shuffles >= 20 && i % 2 == 0) {
//				StdDraw.text(i * xPos + smallBuffer, smallBuffer - smallBuffer / 1.5, i + "");
//				StdDraw.line(i * xPos + smallBuffer, smallBuffer, i * xPos + smallBuffer, smallBuffer - smallBuffer / 3);
//			}
//			else if(shuffles >= 20) {
//				StdDraw.line(i * xPos + smallBuffer, smallBuffer, i * xPos + smallBuffer, smallBuffer - smallBuffer / 5);
//			}
//			else {
//				StdDraw.line(i * xPos + smallBuffer, smallBuffer, i * xPos + smallBuffer, smallBuffer - smallBuffer / 3);
//				StdDraw.text(i * xPos + smallBuffer, smallBuffer - smallBuffer / 1.5, i + "");	
//			}
//			
//		}
		
		
		int clutter = (int)Math.pow(2, ((2 * shuffles + 1) / 20));

		//	Draws a line per scale mark
		for(int i = 1; i <= shuffles; i++) {
			
			if(i % clutter == 0 || i == 1 || i == shuffles) {
				StdDraw.line(i * xPos + smallBuffer, smallBuffer, i * xPos + smallBuffer, smallBuffer - smallBuffer / 3);
				StdDraw.text(i * xPos + smallBuffer, smallBuffer - smallBuffer / 1.5, i + "");
			}
			else if(i % (clutter / 2) == 0) {
				StdDraw.line(i * xPos + smallBuffer, smallBuffer, i * xPos + smallBuffer, smallBuffer - smallBuffer / 5);
			}
		}
		
		setFontSize();
	}
	
	//	Draws tick marks along the y-Axis
	private static void yScaleLabel(int yScale) {
		
		//	Sets the font to look nice
		setFontSize(12);
		
		//	Determines the required y-axis scale
		double yPos = (1 - smallBuffer) / yScale;
		int alternator = 2;
		
		//	Draws a line per scale mark
		for(int i = 1; i <= yScale; i++) {
			if(i % 2 == 0) {
				StdDraw.text(smallBuffer - smallBuffer / 1.5, i * yPos + smallBuffer, i + "");
				alternator = 3;
			}
			else
				alternator = 5;
			
			StdDraw.line(smallBuffer, i * yPos + smallBuffer, smallBuffer - smallBuffer / alternator, i * yPos + smallBuffer);
		}
		
		setFontSize();
	}
	
	//	Draws tick marks along the y-Axis
	private static void yScaleLabel(int yScale, int yExtreme) {
		
		//	Sets the font to look nice
		setFontSize(12);
		
		//	Reduces clutter
		//		Ensures that only even numbers are displayed, so it's nice and symetrical
		//		Uses the power of, well, powers to scale nicely with size
		int clutter = (int)Math.pow(2, ((2 * yExtreme + 1) / 40));
		
		//	Determines the required y-axis scale
		double yPos = s / (2 * yExtreme);
		
		for(int i = 0; i < 2 * yExtreme + 1; i++) {
			if(i % clutter == 0 || i == -1 * yExtreme || i == yExtreme || i == 2 * yExtreme) {
				StdDraw.text(smallBuffer - smallBuffer / 1.5, i * yPos + smallBuffer, (i - yExtreme) + "");
				StdDraw.line(smallBuffer, i * yPos + smallBuffer, smallBuffer - smallBuffer / 3, i * yPos + smallBuffer);
			}
			else
				StdDraw.line(smallBuffer, i * yPos + smallBuffer, smallBuffer - smallBuffer / 5, i * yPos + smallBuffer);
		}
		
		setFontSize();
	}
	
	
	//	Sets the font to the desired size
	//		If there are no input parameters, resets the font size
	private static void setFontSize(int fontSize) {
		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
	}
	
	private static void setFontSize() {
		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 16));
	}
	
}
