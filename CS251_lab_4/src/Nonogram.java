import java.util.Arrays;

/**
 * CS251 Lab Section 003
 * Lab 4: Nonograms 1
 * @author Jaeren Tredway
 * @version 1.0
 */
public class Nonogram {

	//MEMBER VARIABLES:
	public static int height;   		//The number of rows in the puzzle
	public static int width;    		//The number of columns in the puzzle
	public static int maxRowGroups; 	// max number of groups in any one row
	public static int maxColGroups; 	// max number of groups in any one col
		// Note: the above fields are used by the GUI classes --^
	private static boolean[][] targetSolution;	// the correct puzzle solution
	static boolean[ ][ ] guess;  				//the user's guess for a
	// solution
	static int[ ][ ] rowGroupLength;
		//will store the actual group sizes for each row of targetSolution
	static int[ ][ ] colGroupLength;
		//will store the actual group sizes for each column of targetSolution
	static int[ ][ ] guessRowGroupLength;
	//will store the actual group sizes for each row of guess
	static int[ ][ ] guessColGroupLength;
	//will store the actual group sizes for each column of guess

	//2D-ARRAY-INPUT CONSTRUCTOR:
	public Nonogram(boolean[ ][ ] targetSolution) {

		this.height = 			findHeight(toString(targetSolution));
		this.width = 			findWidth(toString(targetSolution));
		this.maxRowGroups = 	(int)Math.ceil((double)height/2);
		this.maxColGroups = 	(int)Math.ceil((double)width/2);
		this.targetSolution = 	targetSolution;
		this.guess = 			new boolean[height][width];
		this.rowGroupLength = 	new int[height][maxRowGroups];
		this.colGroupLength = 	new int[width][maxColGroups];
		//populate rowGroupLength and colGroupLength with this:
		assignGroups(targetSolution);
		this.guessRowGroupLength = new int[height][maxRowGroups];
		this.guessColGroupLength = new int[width][maxColGroups];

	}//END 2D-ARRAY-INPUT CONSTRUCTOR

	//STRING-INPUT CONSTRUCTOR:
	public Nonogram(String s) {
		this(stringToBooleanArray(s));
	}

	//find the height of the nonogram:
	static int findHeight (String pic) {
		int result = 1;
		//find height from nonogram's String data (pic):
		for (int i = 0; i < pic.length(); i++) {
			if (pic.charAt(i) == '\n') {
				result++;
			}
		}
		return result;
	}

	//find the width of the nonogram:
	static int findWidth (String pic) {
		int result = 0;
		//find width from nonogram's String data (pic):
		for (int i = 0; i < pic.length(); i++) {
			if (pic.charAt(i) != '\n') {
				result++;
			} else if (pic.charAt(i) == '\n') {
				break;
			}
		}
		return result;
	}

	//convert 2D boolean array into String:
	public static String toString(boolean[][] nonogram) {
		// TODO: this should include the group lengths as well as the current
		//  guess (?)
		// (this will be mainly used as an debugging tool)
		// empty cell or white 	= '.'
		// full cell or black 	= 'X'

		String result = "";

		for (int i = 0; i < nonogram.length; i++) {
			for (int j = 0; j < nonogram[i].length; j++) {
				if (nonogram[i][j] == true) {
					result += "X";
				} else if (nonogram[i][j] == false) {
					result += ".";
				}
			}

			//TODO: watch for height bugs here:
			if (i < nonogram.length-1) {
				result += "\n";
			}
		}
		return result;
	}

	//convert a string into a 2D boolean array:
	private static boolean[][] stringToBooleanArray(String s) {

		String[ ] lines = s.split("\n");
		boolean[][] rv = new boolean[lines.length][];
		for (int i=0; i<lines.length; i++) {
			String line = lines[i];
			rv[i] = new boolean[line.length()];
			for (int j=0; j<line.length(); j++) {
				rv[i][j] = (line.charAt(j)!='.');
			}
		}
		return rv;
		//rv stands for return value
	}

	//takes a 1D array corresponding to a row or a column and finds the groups:
	private static int[ ] findGroupLengths (boolean[ ] data, int maxGroups) {
		// figure out the groups in data, and record their
		// lengths in rowGroupLengths and colGroupLengths
		int[] result = new int[maxGroups];
		int groupSizeCount = 0;
		int currentStorageIndex = 0;

		for (int i = 0; i < data.length; i++) {
			//if you find a painted cell, add 1 to group size:
			if (data[i] == true) {
				groupSizeCount++;
			//if you don't find a painted cell, report current group size and
				// reset group size to zero, and increment storage index:
			} else {
				if (groupSizeCount > 0) {
					result[currentStorageIndex] = groupSizeCount;
					currentStorageIndex++;
					groupSizeCount = 0;
				}
			}
		}
		//report any remaining group after all the data is examined:
		if (groupSizeCount > 0) {
			result[currentStorageIndex] = groupSizeCount;
		}

		return result;
	}

	//invokes findGroupLengths on each row and column of 2D array to find
	// groups and assign them to either rowGroupLength and colGroupLength or
	// guessRowGroupLength and guessColGroupLength. Invoke this on either
	// targetSolution or guess:
	private static void assignGroups (boolean[][] input2DArray) {

		//this corresponds to either rowGroupLength or guessRowGroupLength:
		int rowGroupArray[][] = new int[height][maxRowGroups];
		//this corresponds to either colGroupLength or guessColGroupLength:
		int colGroupArray[][] = new int[width][maxColGroups];

		if (input2DArray.equals(targetSolution)) {
			rowGroupArray = rowGroupLength;
			colGroupArray = colGroupLength;
		} else {
			rowGroupArray = guessRowGroupLength;
			colGroupArray = guessColGroupLength;
		}

		//for each row of input (targetSolution or guess), invoke
		//findGroupLengths on it and assign values to corresponding row in
		//rowGroupLength[][] or guessRowGroupLength[][]:
		for (int i = 0; i < height; i++) {
			int[] temp = findGroupLengths(input2DArray[i], maxRowGroups);
			for (int j = 0; j < maxRowGroups; j++) {
				rowGroupArray[i][j] = temp[j];
			}
		}

		//for each col of input (targetSolution or guess), invoke
		//findGroupLengths on it and assign values to corresponding row in
		//either colGroupLength[][] or guessColGroupLength[][]:

		//in this case, i is column:
		for (int i = 0; i < width; i++) {

			//first make a temporary 1D array to extract the column data:
			boolean[] tempColArray = new boolean[height];
			for (int j = 0; j < height; j++) {
				tempColArray[j] = input2DArray[j][i];
			}

			//find the group lengths and assign them to the colGroupArray
			//from either targetSolution or guess:
			int[] temp = findGroupLengths(tempColArray, maxColGroups);
			for (int j = 0; j < maxColGroups; j++) {
				colGroupArray[i][j] = temp[j];
			}
		}

	}//END assignGroups()

	public static boolean isGuessCorrect () {
		// return true if the guess has all the correct row/column
		// group lengths.  It does not have to match the "solution" field:
		boolean result = true;

		for (int i = 0; i < height; i++) {
			//check row groups:
			for (int j = 0; j < maxRowGroups; j++) {
				if (rowGroupLength[i][j] != guessRowGroupLength[i][j]) {
					result = false;
				}
			}

			//check col groups:
			for (int j = 0; j < maxColGroups; j++) {
				if (colGroupLength[i][j] != guessColGroupLength[i][j]) {
					result = false;
				}
			}
		}
		return result;
	}


	//****************GUI interaction methods: *****************************
	// The next 4 methods are callback methods that will be invoked by the GUI when appropriate.
	// You need to fill them in to provide the correct functionality.
	public void handleMouseClickAt(int i, int j) {
		// Add code here to handle a "mouseClick" event at row i, column j.
		// You should toggle the guessed color for the cell at the clicked location.
		// You will need to handle the possibility that (i,j) is out of bounds (negative or too big).
		// TODO: your code here

	}
	
	public void handleMousePressAt(int i, int j) {
		// Add code here to handle a "mousePress" event at row i, column j.
		// You should record the location of the press in an instance variable.
		// Don't display anything until the mouse is released.
		// You will need to handle the possibility that (i,j) is out of bounds (negative or too big).
		// TODO: your code here

	}

	public void handleMouseReleaseAt(int i, int j) {
		// Add code here to handle a "mouseRelease" event at row i, column j.
		// If there was a previous mousePress event, you should now check whether the "release" cell is
		// in the same row or column as the "press" cell.  If it is, you should flip the color of the 
		// "press" cell, and color all the other cells between the "press" and "release" cells to match.
		// You will need to handle the possibility that (i,j) is out of bounds (negative or too big).
		// TODO: your code here

	}

	public void handleResetButtonClick( ) {
		// Add code to handle a click of the "reset" button.
		// You should reset all cells to be empty (white) again.
		// TODO: your code here
	}
	//****************END GUI section ***************************************


	//MAIN METHOD used for testing:
	public static void main(String[] args) {

		//MAKE A NONOGRAM FROM A STRING:
		String pic = "..XXX..\n.XX.XX.\nXX...XX\nX.....X\nXX...XX\n.XX.XX.\n." +
				".XXXX.";
		Nonogram testNono = new Nonogram(pic);

		//TEST THE MEMBER VARIABLES FOR CORRECT VALUES:
		System.out.println("height = " + height);
		System.out.println("width = " + width);
		System.out.println("maxColGroups = " + maxColGroups);
		System.out.println("maxRowGroups = " + maxRowGroups);

		System.out.println("\nTEST original pic String = \n" + pic);

		String testString = toString(testNono.targetSolution);
		System.out.println("\nTEST toString: \n" + testString);

		System.out.println("\nTEST rowGroupLength: ");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < maxRowGroups; j++) {
				System.out.print(rowGroupLength[i][j] + " / ");
			}
			System.out.println();
		}

		System.out.println("\n^-- confirm row groups with findGroupLengths:");
		for (int i = 0; i < height; i++) {
			System.out.println(Arrays.toString(findGroupLengths(testNono.targetSolution[i], maxRowGroups)));
		}

		System.out.println("\nTEST colGroupLength: ");
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < maxColGroups; j++) {
				System.out.print(colGroupLength[i][j] + " / ");
			}
			System.out.println();
		}
		System.out.println("\n^-- check column groups against nonogram:");
		System.out.println(pic);

		System.out.println("\nTEST guess vs. targetSolution: ");
		//INCORRECT GUESS TEST: **********************************************
		//build an incorrect guess to test:
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				testNono.guess[i][j] = true;
			}
		}
		//assign values to guessRowGroupLength[][] and guessColGroupLength[][]:
		assignGroups(guess);
		//print out the guess's row groups:
		System.out.println("TEST 1: your guess's row groups:");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < maxRowGroups; j++) {
				System.out.print(guessRowGroupLength[i][j] + " / ");
			}
			System.out.println();
		}

		System.out.println("TEST isGuessCorrect: ");
		System.out.println(isGuessCorrect() ? "You got a solution!" : "No " +
				"solution yet.");

		//CORRECT GUESS TEST: ************************************************
		//build a correct guess to test:
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				testNono.guess[i][j] = testNono.targetSolution[i][j];
			}
		}
		//assign values to guessRowGroupLength[][] and guessColGroupLength[][]:
		assignGroups(guess);
		//print out the guess's row groups:
		System.out.println("\n\nTEST 2: your guess's row groups:");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < maxRowGroups; j++) {
				System.out.print(guessRowGroupLength[i][j] + " / ");
			}
			System.out.println();
		}

		System.out.println("TEST isGuessCorrect: ");
		System.out.println(isGuessCorrect() ? "You got a solution!" : "No " +
				"solution yet.");


		//START THE GUI:
		//NonogramGUI gui = new NonogramGUI(nono);
			//interface for the puzzle passed to it

	}//END main() method
}//END class Nonogram
