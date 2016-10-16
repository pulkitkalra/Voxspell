package voxspell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * This class provides the functionality to display the About and Instructions about the
 * VOXSPELL application.
 * @author pulkit
 *
 */
public class AppInfo {

	/**
	 * Shows instructions for the game in a formatted, easy to read manner.
	 */
	public void showInstructions(){
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(238,238,238));
		textArea.setFont(new Font("Helvetica",Font.BOLD,14));
		textArea.setMargin(new Insets(10,10,10,10));
		
		textArea.setText("INSTRUCTIONS FOR VOXSPELL"
				+ "\n\nQUIZ"
				+ "\n\nThere are two types of quiz you can attempt."
				+ "\n\n- New Quiz: This Section allows you to attempt 10 randomly selected words"
				+ " from a particular level. Levels go from 1 to 11 and each level has increased"
				+ " difficulty of words. If you get 9 out of 10 words correct, you can progress"
				+ " to the next level. All the while being treated to a Video Reward!"
				+ "\nNew Quiz is the default mode on start-up. To begin a New Quiz, simply"
				+ " select a Level where you wish to begin from the drop down menu and press Start Level."
				+ "\n- Review Quiz: Once you have attempted a New Quiz and find there are mistakes"
				+ "you wish to take another look at - use Review Quiz. Use the drop down menu from"
				+ "the menu bar: Test Menu --> and select Review Quiz. Select a Level and press "
				+ "Submit to get "
				+ "started. You can choose New Quiz under the Test Menu to Switch back to the default "
				+ "mode."
				+ "\n\nSTATISTICS"
				+ "\n\nWhether you are attempting a New Quiz or Review Quiz, statistics for each word"
				+ "and level are available to view at all times. Level Accuracy rates are shown"
				+ "on the panel on the right and individual Level Words Statistics can be seen"
				+ "by selecting the relevant level under Statistics menu. Select Clear All "
				+ "Statistics under the Statistics Menu to reset the individual word stats."
				+ "\nIn addition, the current level is always visible on the bottom left."
				+ "\n\nVOICE"
				+ "\n\nVoice can be changed at any time by selecting one of two voices: Default and"
				+ "NZ from the Voice Drop Down Menu in the Menu bar."
				+ "");
		JScrollPane scrollPane = new JScrollPane(textArea);  
		textArea.setLineWrap(true);  
		//textArea.set
		textArea.setWrapStyleWord(true); 
		textArea.setEditable(false);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		scrollPane.getVerticalScrollBar().setValue(0);
		JOptionPane.showMessageDialog(null, scrollPane, "VOXSPELL Instructions", 
		                                       JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Shows about and other information about the app and Legal status.
	 */
	public void showAbout(){
		String message = "Prototype for VOXSPELL"
				+ "\nAssignment 3: SE206"
				+ "\n\nAll Rights Reserved. "
				+ "\nPulkit Kalra and Darius Au 2016";
		JOptionPane.showMessageDialog(null, message, 
				"About VOXSPELL", JOptionPane.INFORMATION_MESSAGE);
	}
}
