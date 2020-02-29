import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class NonogramGUI implements ActionListener {

	Nonogram puzzle;
	NonogramPanel panel;
	
	public NonogramGUI(Nonogram puzzle) {
		this.puzzle = puzzle;
		this.panel = new NonogramPanel(puzzle);;
		JFrame f = new JFrame("Jaeren's Nonogram App");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container p = f.getContentPane();

		JButton resetButton = new JButton("RESET");
		resetButton.addActionListener(this);
		p.add(resetButton,BorderLayout.WEST);
		p.add(panel,BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.addActionListener(this);
        p.add(submitButton,BorderLayout.EAST);
        p.add(panel,BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
	}
	
    // handle reset button click event:
    public void actionPerformed(ActionEvent e) {
        System.out.println("Reset button pressed.");
        puzzle.handleResetButtonClick();
        panel.repaint();
    }

    // handle submit button click event:
  /*  public void actionPerformed(ActionEvent e) {
        System.out.println("Reset button pressed.");
        puzzle.handleResetButtonClick();
        panel.repaint();
    }
*/
	public static void main (String[] args) {
		String pic = "..XXX..\n.XX.XX.\nXX...XX\nX.....X\nXX...XX\n.XX.XX.\n..XXX..";
		Nonogram nono = new Nonogram(pic);
		NonogramGUI inst = new NonogramGUI(nono);
	}
	
}
