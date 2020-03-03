import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NonogramGUI implements ActionListener {

	//MEMBER VARIABLES:
	Nonogram puzzle;
	NonogramPanel panel;
	JFrame f;
	Container p;
	protected JButton resetButton, submitButton;
	JLabel winMessage;

	//CONSTRUCTOR:
	public NonogramGUI(Nonogram puzzle) {
		//PUZZLE BOARD: *************************
		this.puzzle = puzzle;
		this.panel = new NonogramPanel(puzzle);
		f = new JFrame("JAEREN'S NONOGRAM APP");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p = f.getContentPane();
		//RESET BUTTON: *************************
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		p.add(resetButton, BorderLayout.WEST);
		p.add(panel, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
		//SUBMIT BUTTON: ************************
		submitButton = new JButton("SUBMIT");
		submitButton.addActionListener(this);
		p.add(submitButton, BorderLayout.EAST);
		p.add(panel, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
		//WIN MESSAGE: **************************
		winMessage = new JLabel("YOU WIN!");
	}//END CONSTRUCTOR

	//listens for clicks on either reset button or submit button:
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == resetButton) {
			System.out.println("Reset button pressed");
			puzzle.handleResetButtonClick();
			panel.repaint();
		} else if (e.getSource() == submitButton) {
			System.out.println("Submit button pressed.");
			puzzle.handleSubmitButtonClick();
			panel.repaint();
			//if guess is correct, display winning message pop-up:
			if (puzzle.isGuessCorrect()) {
				JOptionPane.showMessageDialog(
						null,
						"YOU WIN!",
						"Message", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}//END actionPerformed()

	//MAIN METHOD:
	public static void main (String[] args) {
		String pic = "..XXX..\n.XX.XX.\nXX...XX\nX.....X\nXX...XX\n.XX.XX.\n..XXX..";
		Nonogram nono = new Nonogram(pic);
		NonogramGUI inst = new NonogramGUI(nono);
	}//END main()

}//END class NonogramGUI