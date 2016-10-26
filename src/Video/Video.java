package Video;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import voxspell.MainMenu;

/**
 * The Video class provides the functionality to play the video reward which is redeemed at the
 * end of successful completition of a level.
 * @author pulkit
 *
 */

public class Video {

	private final JFrame frame;

	private final EmbeddedMediaPlayerComponent mediaPlayer = new EmbeddedMediaPlayerComponent();
	private String normalVid;
	private String videoLevel2;
	private String videoLevel3;
	private final EmbeddedMediaPlayer video;
	private MainMenu _main;
	private int _level;
	public String currentVideo;
	
	/**
	 * Video constructor (2 args) is responsible for initializing the relevant Video files and
	 * the GUI used to display the video.
	 * @param main
	 * @param level
	 */
	/**
	 * Constructor is responsible for constructing JPanel that displays the Video and the GUI
	 * @param main
	 * @param level
	 */
	public Video() {

		normalVid = MainMenu._resourcesPath + "big_buck_bunny_1_minute.avi";
		videoLevel2 = MainMenu._resourcesPath + "cat.mp4";
		videoLevel3 = MainMenu._resourcesPath + "beach.mp4";


		GridBagLayout gbl = new GridBagLayout();

		JPanel cp = new JPanel();
		cp.setLayout(gbl);

		GridBagConstraints gbc;

		gbc = new GridBagConstraints();
		gbc.weightx = 1.0f;
		gbc.weighty = 1.0f;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;


		video = mediaPlayer.getMediaPlayer();

		cp.add(mediaPlayer, gbc);

		gbc = new GridBagConstraints();
		gbc.weightx = 1.0f;
		gbc.weighty = 0.0f;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;

		JPanel vidPanel = new JPanel();

		final JButton pauseButton = new JButton("Pause");
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pauseButton.getText().equals("Pause")){
					video.pause();
					pauseButton.setText("Play");
				}
				else {
					video.play();
					pauseButton.setText("Pause");
				}
			}
		});

		final JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (stopButton.getText().equals("Stop")){
					video.stop();
					pauseButton.setEnabled(false);
					pauseButton.setText("Pause");
					stopButton.setText("Replay");
				}
				else {
					video.play();
					pauseButton.setEnabled(true);
					stopButton.setText("Stop");
				}
			}
		});
		JButton muteButton = new JButton("Mute");
		muteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				video.mute();
			}
		});

		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				video.stop();
				frame.dispose();
			}
		});
		JButton skipButton = new JButton("Forward");
		skipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				video.skip(5000);
			}
		});

		JButton skipBackButton = new JButton("Rewind");
		skipBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				video.skip(-5000);
			}
		});

		vidPanel.add(muteButton);
		vidPanel.add(pauseButton);
		vidPanel.add(stopButton);
		vidPanel.add(exitButton);
		vidPanel.add(skipBackButton);
		vidPanel.add(skipButton);


		cp.add(vidPanel, gbc);

		frame = new JFrame("Video Reward");
		frame.setContentPane(cp);
		frame.setSize(800, 600);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				video.stop();
				frame.dispose();
			}
		});
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	/**
	 * Method used to start playing a Video file based on user selection. 
	 * Also checks that video file actually exists.
	 * @param selection
	 */
	public void start(String selection) {
		String playableVid="";
		
		switch (selection) {
			case "big_buck_bunny_1_minute.avi":  playableVid = normalVid;
			frame.setTitle("Big Buck Bunny");
			break;
			
			case "cat.mp4": playableVid = videoLevel2;
			frame.setTitle("Cat!");
			break;
			
			case "beach.mp4": playableVid = videoLevel3;	 
			frame.setTitle("Drone on a Beach");
			break;
		}

		File f1 = new File(playableVid);
		if(!f1.exists() && !f1.isDirectory()) { 
			frame.dispose();
			JOptionPane.showMessageDialog(null, "Video File not found:"+playableVid
					+ "\nPlease ensure you include it in your working directory", 
					"Error!", JOptionPane.ERROR_MESSAGE);
			
		} else {
			mediaPlayer.getMediaPlayer().playMedia(playableVid);
		}
	}

}
