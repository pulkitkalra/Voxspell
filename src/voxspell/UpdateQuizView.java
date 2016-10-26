package voxspell;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import logic.Result;
import logic.TestFactory;

/**
 * Class helps the Quiz Frame class update the Quiz Frame - helps move some of the content from
 * the Quiz Frame so it can be handled elsewhere - better for maintainance.
 * @author pulkit
 *
 */
public class UpdateQuizView {
	private ImageIcon tick = new ImageIcon(MainMenu._resourcesPath+"tick.png");
	private ImageIcon cross = new ImageIcon(MainMenu._resourcesPath+"cross.png");
	//private ImageIcon warn = new ImageIcon(MainMenu._path+"warning.png");
	private String[][] table = new String[10][2];
	private int attempts;

	private int streak;
	/**
	 * Method updates the view of Quiz Completed Card.
	 * @param _test
	 * The test object in operation.
	 * @param result
	 * The result object in operation.
	 * @param textOutput
	 * The text Output pane which displays the output (e.g. "Please spell ...") 
	 * @param textPane
	 * The text field that accepts user input 
	 * @param attempt
	 * The word attempt entered by user in text Field
	 * @param progressBar
	 * The progress Bar which updates after every correct/incorrect answer
	 * @param lblLevelAccuracy
	 * This status represents the Current Accuracy of the level: i.e. "Level Accuracy: 50%"
	 * @param lblLongestStreak
	 * @param lblFeedback
	 * @return
	 */
	public boolean updateView(TestFactory _test, Result result, JEditorPane textOutput, JTextField textPane, 
			String attempt, JProgressBar progressBar, JLabel lblLevelAccuracy,
			JLabel lblLongestStreak, JLabel lblFeedback) {
		String status;
		int wordNumber = _test.getWordNumber()+1;
		String word;
		if (wordNumber == 1){
			word =  _test.getTestList().get(wordNumber - 1);
		} else {
			word =  _test.getTestList().get(wordNumber - 2);
		}

		int noOfWords = _test.getWordCount();
		progressBar.setMaximum(noOfWords);
		int progressNumber = (wordNumber - 1);

		textPane.setEnabled(true);

		switch (result){

		case Correct:
			attempts++;
			streak++;
			status = "CORRECT";
			lblLongestStreak.setText("Current Streak: "+streak);
			progressBar.setValue(progressNumber);
			table[wordNumber-2][0] = word;
			table[wordNumber-2][1] = status;
			lblFeedback.setIcon(tick);
			lblFeedback.setText("    Correct!");
			int percentage = (int) (((double)attempts/progressNumber)*100);
			lblLevelAccuracy.setText("Level Accuracy: "+percentage+"%");
			if (wordNumber < noOfWords+1){
				textOutput.setForeground(Color.BLACK);
				textOutput.setText("Please spell Word "+wordNumber+": ");		
			} else {
				return false;
			}

			//correct = true;
			break;
		case Incorrect_1:
			textOutput.setForeground(Color.RED);
			textOutput.setText("You entered: " + attempt
					+ "\n Incorrect, try once more: ");
			lblFeedback.setIcon(cross);
			lblFeedback.setText("    Try Again!");
			//attempts++;
			break;

		case Incorrect_2:
			/*textOutput.setForeground(Color.RED);
			textOutput.setText("You entered: " + attempt
					+ "\n Incorrect!");*/
			status = "INCORRECT";
			lblLongestStreak.setText("Current Streak: 0");
			streak = 0;
			progressBar.setValue(progressNumber);
			//stats.updateLevelRecord(_level, result);
			table[wordNumber-2][0] = word;
			table[wordNumber-2][1] = status;
			lblFeedback.setIcon(cross);
			lblFeedback.setText("    Incorrect");
			int percentage2 = (int) (((double)attempts/progressNumber)*100);
			lblLevelAccuracy.setText("Level Accuracy: "+percentage2+"%");
			if (wordNumber < noOfWords+1){
				textOutput.setForeground(Color.BLACK);
				textOutput.setText("Please spell Word "+wordNumber+": ");
				/*table[wordNumber-2][0] = word;
				table[wordNumber-2][1] = status;*/
			} else {
				return false; // quiz complete
			}
			break;
		}	
		//textPane.setText("");
		return true;
	}
	/**
	 * getter method for the table
	 * @return
	 * String[][] representing data for table.
	 */
	public String[][] getTable(){
		return table;
	}
	/**
	 * method resets the view of the GUI everytime a new quiz is started.
	 * @param textOutput
	 * @param textPane
	 * @param progressBar
	 * @param status1
	 * @param level
	 * @param lblLevelAccuracy
	 * @param lblLongestStreak
	 * @param lblFeedback
	 * @param btnStartLevel
	 */
	public void resetView(JEditorPane textOutput, JTextField textPane, 
			JProgressBar progressBar, JLabel status1, int level, JLabel lblLevelAccuracy, 
			JLabel lblLongestStreak, JLabel lblFeedback, JButton btnStartLevel) {
		attempts = 0; // reset attempts
		streak = 0; // reset streal
		btnStartLevel.requestFocusInWindow(); //focus
		btnStartLevel.setEnabled(true); // start button enables
		status1.setText("Current Level: Level " +level); // correct text
		textPane.setEnabled(false); // btn disables
		textPane.setText(""); // no text in field
		textOutput.setText("CLICK THE BEGIN TEST BUTTON TO   START!");
		lblFeedback.setIcon(null); // no default icon
		lblFeedback.setText(""); // no feedback text
		progressBar.setValue(0); // progressBar reset
		lblLevelAccuracy.setText("Level Accuracy: - ");
		textOutput.setForeground(Color.BLACK);
		lblLongestStreak.setText("Current Streak: 0");

	}
	/**
	 * returns list containing all the rows which are correct.
	 */
	public List<Integer> getCorrectArrayIndex(){
		List<Integer> list = new ArrayList<Integer>();
		for (int i =0; i< table.length;i++){
			if (table[i][1]!= null && table[i][1].equals("CORRECT")){
				list.add(i);
			}
		}
		return list;
	}
	/**
	 * getter method for getting attempts (int)
	 * @return
	 */
	public int getAttempts() {
		return attempts;
	}
}
