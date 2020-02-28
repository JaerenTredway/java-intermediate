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
		// Note: the above fields are used by the GUI classes
	private boolean[][] testSolution; 	// the correct puzzle solution
	boolean[ ][ ] guess;  				//the user's guess for a solution
	static int[ ][ ] rowGroupLength;
		//will store the actual group sizes for each row
	static int[ ][ ] colGroupLength;
		//will store the actual group sizes for each column

	//2D-ARRAY-INPUT CONSTRUCTOR:
	public Nonogram(boolean[ ][ ] targetSolution) {

		this.height = 		findHeight(toString(targetSolution));
		this.width = 		findWidth(toString(targetSolution));
		this.maxRowGroups = (int)Math.ceil((double)height/2);
		this.maxColGroups = (int)Math.ceil((double)width/2);
		this.testSolution = targetSolution;
		this.guess = 		new boolean[height][width];
		this.rowGroupLength = new int[height][maxRowGroups];
		this.colGroupLength = new int[width][maxColGroups];
		//populate rowGroupLength and colGroupLength:
		assignGroups(targetSolution);

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

	//convert 2D boolean array back into String:
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

	//this is provided for you, it converts a string to a 2D boolean array:
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

	private static void assignGroups (boolean[][] targetSolution) {
		//TODO: for each row of targetSolution, invoke findGroupLengths on it
		// and assign values to corresponding row in rowGroupLength[][]
		for (int i = 0; i < height; i++) {
			int[] temp = findGroupLengths(targetSolution[i], maxRowGroups);
			for (int j = 0; j < maxRowGroups; j++) {
				rowGroupLength[i][j] = temp[j];
			}
		}

		//TODO: for each col of targetSolution, invoke findGroupLengths on it
		// and assign values to corresponding row in colGroupLength[][]
		//in this case, i is column:
		for (int i = 0; i < width; i++) {

			//first make a temporary 1D array to extract the column data:
			boolean[] tempColArray = new boolean[height];
			for (int j = 0; j < height; j++) {
				tempColArray[j] = targetSolution[j][i];
			}


			int[] temp = findGroupLengths(tempColArray, maxColGroups);
			for (int j = 0; j < maxColGroups; j++) {
				colGroupLength[i][j] = temp[j];
			}

		}

	}//END assignGroups()

	boolean isGuessCorrect ( ) {
		// return true iff the guess has all the correct row/column
		// group lengths.  It does not have to match the "solution" field.
		// TODO: your code here

		boolean result = true;

		//TODO: make a 2D for-loop that checks to see if RGL and CGL are
		// equal between guess and targetSolution

		return result;    // you will replace this.
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

		String testString = toString(testNono.testSolution);
		System.out.println("\nTEST toString: \n" + testString);

		System.out.println("\nTEST rowGroupLength: ");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < maxRowGroups; j++) {
				System.out.print(rowGroupLength[i][j] + " / ");
			}
			System.out.println();
		}

		System.out.println("\nTEST confirm row groups with findGroupLengths:");
		for (int i = 0; i < height; i++) {
			System.out.println(Arrays.toString(findGroupLengths(testNono.testSolution[i], maxRowGroups)));
		}

		System.out.println("\nTEST colGroupLength: ");
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < maxColGroups; j++) {
				System.out.print(colGroupLength[i][j] + " / ");
			}
			System.out.println();
		}
		System.out.println(pic);

		//START THE GUI:
		//NonogramGUI gui = new NonogramGUI(nono);
			//interface for the puzzle passed to it

	}//END main() method
}//END class Nonogram
