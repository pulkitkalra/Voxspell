package voxspell;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
/**
 * Class extend Quiz Frame and is used to a Review Test instead of New Test by reusing New Test
 * functionality.
 * @author pulkit
 *
 */
@SuppressWarnings("serial")
public class ReviewFrame extends QuizFrame {
	private Clip clip;
	/**
	 * constructor tells super class that we are dealing with a review quiz and informs that
	 * modification method needs to be called.
	 */
	public ReviewFrame(){
		super("r");
		modifyGUI();
	}
	/**
	 * Method modifies the new quiz Gui to fit a review quiz. this includes changing relevant labels
	 * and switching the video reward button for the audio reward button.
	 */
	public void modifyGUI(){
		lblNewSpellingQuiz.setText("   Review Quiz   ");
		dtrpnStartANew.setText("This mode allows you to review the mistakes you have made."
				+ "\nOnly the levels that have words to review will be shown in the List of levels");
		quizEndCard.remove(btnPlayVideo);
		// adding an audio reward.
		final JToggleButton btnPlayAudio = new JToggleButton("Play Audio Reward");
		btnPlayAudio.setFont(new Font("Dialog", Font.BOLD, 14));
		btnPlayAudio.setBounds(206, 275, 199, 39);
		btnPlayAudio.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnPlayAudio.getText().equals("Play Audio Reward")){
					try 
					{   
						AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(MainMenu._resourcesPath+VoxStore.currentSong));
						// music credits: PPAP Song Tropical Remix | NCS | Free Music Bank
						AudioFormat format = inputStream.getFormat();
						DataLine.Info info = new DataLine.Info(Clip.class, format);
						clip = (Clip)AudioSystem.getLine(info);
						clip.open(inputStream);
						clip.start();
						btnPlayAudio.setText("Stop this trauma");
					}
					catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1)
					{
						e1.printStackTrace();
					}
				} else if (btnPlayAudio.getText().equals("Stop this trauma")){
					clip.stop();
					btnPlayAudio.setText("Play Audio Reward");
				}
			}
		});
		btnPlayAudio.setToolTipText("You can redeem audio reward upon finishing a review quiz!");
		quizEndCard.add(btnPlayAudio);
	}
}
