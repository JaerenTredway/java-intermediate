import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

/**
 * CS251 Lab Section 003
 * Lab 4: Nonograms 1
 * @author Jaeren Tredway
 * @version 1.0
 */
public class Nonogram {

	//MEMBER VARIABLES:
	//NOTE: These are static to allow testing in main() and to show results
    // in the terminal via main()
	public static int height;   		//The number of rows in the puzzle
	public static int width;    		//The number of columns in the puzzle
	public static int maxRowGroups; 	// max number of groups in any one row
	public static int maxColGroups; 	// max number of groups in any one col
    static boolean[][] targetSolution;	// the correct puzzle solution
	static boolean[][] guess;  	    	//the user's guess for a solution
	//this will store the actual group sizes for each row of targetSolution:
	static int[][] rowGroupLength;
	//this will store the actual group sizes for each column of targetSolution:
	static int[][] colGroupLength;
	//this will store the actual group sizes for each row of guess:
	static int[][] guessRowGroupLength;
	//this will store the actual group sizes for each column of guess:
	static int[][] guessColGroupLength;
	boolean[][] mousePressAt;	//records index where mouse is pressed
	boolean[][] mouseReleaseAt;	//records index where mouse is released
	int pressedRow;				//the row where mouse was pressed
	int pressedCol;				//the col where mouse was pressed
	int releasedRow;			//the row where mouse was released
	int releasedCol;			//the col where mouse was released


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
		this.mousePressAt = new boolean[height][width];
		this.mouseReleaseAt = new boolean[height][width];
		pressedRow = 0;
		pressedCol = 0;
		releasedRow = 0;
		releasedCol = 0;

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

	//makes a 1D array corresponding to a row or a column and finds the groups:
	private static int[ ] findGroupLengths (boolean[ ] data, int maxGroups) {
		// figure out the groups in data, and record their
		// lengths in rowGroupLengths and colGroupLengths
		int[] result = new int[maxGroups];
		int groupSizeCount = 0;
		int currentStorageIndex = 0;

		for (int i = 0; i < data.length; i++) {
			//if you find a painted (true) cell, add 1 to group size:
			if (data[i] == true) {
				groupSizeCount++;
			//if you don't find a painted (true) cell, report current group size
				// and
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
	//groups and assign them to either 1) rowGroupLength and colGroupLength, or
	// 2) guessRowGroupLength and guessColGroupLength. Invoke this on either
	//targetSolution or guess:
	public static void assignGroups (boolean[][] input2DArray) {

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

    //checks group lengths of guess vs. targetSolution to see if they match:
	public static boolean isGuessCorrect () {
		// return true if the guess has all the correct row/column
		// group lengths.  It does not have to match the "solution" field:
		boolean result = true;
		String rowReport = "";
		String colReport = "";

		for (int i = 0; i < height; i++) {
			//check row groups:
			for (int j = 0; j < maxRowGroups; j++) {
				if (rowGroupLength[i][j] != guessRowGroupLength[i][j]) {
					result = false;
					rowReport = "You're guess's row groups don't " +
							"match the puzzle";
				}
			}

			//check col groups:
			for (int j = 0; j < maxColGroups; j++) {
				if (colGroupLength[i][j] != guessColGroupLength[i][j]) {
					result = false;
					colReport = "You're guess's column groups don't " +
							"match the puzzle";
				}
			}
		}

		System.out.println(rowReport);
		System.out.println(colReport);
		return result;
	}//END isGuessCorrect()

	//resets the guess to blank:
	public static void resetGuess () {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				guess[i][j] = false;
			}
		}
	}

	//print out a 2D array:
	public static void print2DArray (boolean[][] inputArray) {
		System.out.println(toString(inputArray));
	}

    //****************GUI methods: *****************************************
    // The next 4 methods are callback methods that will be invoked by the
	// GUI when appropriate:

	//switch the paint on the clicked cell:
	//WARNING: don't use this method, as it's execution will be
	// reversed by (mousePress + mouseRelease) in same cell. Wicked bug.
	public void handleMouseClickAt(int i, int j) {

	}

	//records the location of mouse press in pressedRow and pressedCol:
	public void handleMousePressAt(int i, int j) {
		//i is the y-value, j is the x-value
		try {
			if (i < height && i >= 0 && j < width && j >= 0) {
				mousePressAt[i][j] = guess[i][j];
				pressedRow = i;
				pressedCol = j;
			}
		} catch (ArrayIndexOutOfBoundsException arrIndxOOBEx_1) {
			throw new ArrayIndexOutOfBoundsException("\nClick was not on " +
                    "board.");
		}
	}

	//records the location of mouse release in releaseRow and releaseCol, and
	// switches the paint on that cell and all cells between press and
	// release (if they're in the same row or same column):
	public void handleMouseReleaseAt(int i, int j) {

		//assign values to variables: **************************************
		try {
			if (i < height && i >= 0 && j < width && j >= 0) {
				mouseReleaseAt[i][j] = guess[i][j];
				releasedRow = i;
				releasedCol = j;
			}
		} catch (ArrayIndexOutOfBoundsException arrIndxOOBEx_2) {
			throw new ArrayIndexOutOfBoundsException("\nClick was not on " +
                    "board.");
		}

		//for press and release in SAME CELL: *******************************
		if (pressedRow == releasedRow && pressedCol == releasedCol) {
			guess[i][j] = !guess[i][j];
		}

		//for press and release in SAME ROW: ********************************
		if (releasedRow == pressedRow) {
			if (releasedCol > pressedCol) {
				int stripeLength = releasedCol - pressedCol + 1;
				for (int k = releasedCol; k > releasedCol - stripeLength; k--) {
					guess[pressedRow][k] = !guess[pressedRow][k];
				}
			}

			if (releasedCol < pressedCol) {
				int stripeLength = pressedCol - releasedCol + 1;
				for (int k = pressedCol; k > pressedCol - stripeLength; k--) {
					guess[pressedRow][k] = !guess[pressedRow][k];
				}
			}
		}//END same row

		//for press and release in SAME COL: ********************************
		if (releasedCol == pressedCol) {
			if (releasedRow > pressedRow) {
				int stripeLength = releasedRow - pressedRow + 1;
				for (int k = releasedRow; k > releasedRow - stripeLength; k--) {
					guess[k][pressedCol] = !guess[k][pressedCol];
				}
			}

			if (releasedRow < pressedRow) {
				int stripeLength = pressedRow - releasedRow + 1;
				for (int k = pressedRow; k > pressedRow - stripeLength; k--) {
					guess[k][pressedCol] = !guess[k][pressedCol];
				}
			}
		}//END same col

		//check for WINNING CONDITION at each mouse release: ****************
		//NOTE: this has been enhanced with the GUI submit button:
        assignGroups(guess);
		System.out.println(isGuessCorrect() ? "This is a correct " +
				"solution" : "This is not a correct solution");
	}//END handleMouseReleaseAt()

	//resets the painted cells to blank when reset button clicked:
	public void handleResetButtonClick( ) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				guess[i][j] = false;
			}
		}
	}

	//checks for a winning solution when the submit button is clicked:
	public void handleSubmitButtonClick( ) {
		assignGroups(guess);
        System.out.println("Submission being analyzed:");
		System.out.println(isGuessCorrect() ?
                "You're guess is a match, YOU WIN!" :
                "Keep trying...");
	}

	//****************END GUI section **************************************


	//MAIN METHOD used for testing: *****************************************
	public static void main(String[] args) {

		//MAKE A NONOGRAM FROM A STRING:
		String pic =    "......X.XX\n" +
                        "........XX\n" +
                        ".......X..\n" +
                        ".........X\n" +
                        ".....XX...\n" +
                        "XX..XXXX..\n" +
                        "XX.XXXXXX.\n" +
                        ".XXXXXXXX.\n" +
                        "....X..X..\n" +
                        "...XX.XX..\n";

        //String pic = "XX...X\n.X.XXX\n.X.XX.\n.XXX..\n.XXXX.\n...X..";

		Nonogram testNono = new Nonogram(pic);


//********************  TESTS SECTION: ************************************
		System.out.println("height = " + height);
		System.out.println("width = " + width);
		System.out.println("maxColGroups = " + maxColGroups);
		System.out.println("maxRowGroups = " + maxRowGroups);

/*		System.out.println("\nTEST original pic String = \n" + pic);

		String testString = toString(testNono.targetSolution);
		System.out.println("\nTEST toString: \n" + testString);
*/
		System.out.println("\nTEST rowGroupLength: ");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < maxRowGroups; j++) {
				System.out.print(rowGroupLength[i][j] + " / ");
			}
			System.out.println();
		}

/*		System.out.println("\n^-- confirm row groups with findGroupLengths:");
		for (int i = 0; i < height; i++) {
			System.out.println(Arrays.toString(findGroupLengths(testNono
                    .targetSolution[i], maxRowGroups)));
		}
*/
		System.out.println("\nTEST colGroupLength: ");
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < maxColGroups; j++) {
				System.out.print(colGroupLength[i][j] + " / ");
			}
			System.out.println();
		}
/*		System.out.println("\n^-- check column groups against nonogram:");
		System.out.println(pic);
*/
		System.out.println("\nTEST guess vs. targetSolution: ");
		//INCORRECT GUESS TEST:
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

		//System.out.println("TEST isGuessCorrect: ");
		System.out.println(isGuessCorrect() ? "You got a solution!" : "No " +
				"solution yet.");
		//END incorrect guess test

		//CORRECT GUESS TEST:
		//build a correct guess to test:
		resetGuess();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				guess[i][j] = targetSolution[i][j];
			}
		}

		//assign values to guessRowGroupLength[][] and guessColGroupLength[][]:
		assignGroups(guess);

		//print out the guess's row groups:
		System.out.println("\nTEST 2: your guess's row groups:");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < maxRowGroups; j++) {
				System.out.print(guessRowGroupLength[i][j] + " / ");
			}
			System.out.println();
		}

		//System.out.println("TEST isGuessCorrect: ");
		System.out.println(isGuessCorrect() ? "You got a solution!" : "No " +
				"solution yet.");
        //END correct guess test
//**********************END TEST SECTION ************************************

		//START THE GUI:
		//user interface for the puzzle solution passed into it:
		NonogramGUI gui = new NonogramGUI(testNono);

	}//END main() method
}//END class Nonogram
