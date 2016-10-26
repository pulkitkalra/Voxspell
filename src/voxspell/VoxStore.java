package voxspell;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * The VoxStore class provides the panel for the Vox Store - this lets you use switch choose
 * your video and audio reward.
 * @author pulkit
 *
 */
public class VoxStore extends JPanel {
	
	public static String currentVideo;
	public static String currentSong;
	private String normalVid = "big_buck_bunny_1_minute.avi";
	private String videoLevel2 = "cat.mp4";
	private String videoLevel3 = "beach.mp4";
	private JButton bunnyVideo;
	private JButton btnCatVideo;
	private JButton btnDrone;
	private JButton buttonMusic1; 
	private JButton btnPpap;
	
	/**
	 * Create the panel.
	 */
	public VoxStore() {
		setUpPanel();
		currentVideo = normalVid;
		currentSong =  "musicDef.wav";
		bunnyVideo.setText("Selected");
		bunnyVideo.setBackground((Color.CYAN));
		buttonMusic1.setText("Selected");
		buttonMusic1.setBackground((Color.CYAN));
	}
	/**
	 * painting background.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(MainMenu.bkg, 0, 0, null);
	}
	/**
	 * method used to set up buttons and their corresponding listeners.
	 * To have a button as selected - I set it's text be selected, change it colour and reset
	 * the other buttons back to their normal appearance.
	 */
	public void setUpPanel(){
		setLayout(null);
		setBounds(10, 100, 600, 400);
		// title
		JLabel lblVoxStore = new JLabel("VOX STORE");
		lblVoxStore.setFont(new Font("DejaVu Sans", Font.BOLD, 24));
		lblVoxStore.setBounds(223, 12, 165, 44);
		add(lblVoxStore);
		// video reward
		JLabel lblSelectYourVideo = new JLabel("Select your Video Reward");
		lblSelectYourVideo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSelectYourVideo.setBounds(34, 57, 265, 26);
		add(lblSelectYourVideo);
		// audio reward
		JLabel lblSelectYourAudio = new JLabel("Select your Audio Reward");
		lblSelectYourAudio.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSelectYourAudio.setBounds(34, 226, 265, 26);
		add(lblSelectYourAudio);
		// bunny Video button
		bunnyVideo = new JButton("Big Bunny");
		bunnyVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentVideo = normalVid;
				bunnyVideo.setText("Selected");
				bunnyVideo.setBackground((Color.CYAN));
				btnCatVideo.setText("Cat Video");
				btnCatVideo.setBackground((Color.LIGHT_GRAY));
				btnDrone.setText("Beach Video");
				btnDrone.setBackground((Color.LIGHT_GRAY));
			}
		});
		bunnyVideo.setFont(new Font("Dialog", Font.BOLD, 14));
		bunnyVideo.setBounds(34, 189, 155, 35);
		add(bunnyVideo);
		// cat video button
		btnCatVideo = new JButton("Cat Video");
		btnCatVideo.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCatVideo.setBounds(228, 189, 155, 35);
		btnCatVideo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				currentVideo = videoLevel2;
				bunnyVideo.setText("Big Bunny");
				bunnyVideo.setBackground((Color.LIGHT_GRAY));
				btnCatVideo.setText("Selected");
				btnCatVideo.setBackground((Color.CYAN));
				btnDrone.setText("Beach Video");
				btnDrone.setBackground((Color.LIGHT_GRAY));
			}
		});
		add(btnCatVideo);
		// beach video button
		btnDrone = new JButton("Beach Video");
		btnDrone.setFont(new Font("Dialog", Font.BOLD, 14));
		btnDrone.setBounds(419, 189, 155, 35);
		btnDrone.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				currentVideo = videoLevel3;
				bunnyVideo.setText("Big Bunny");
				bunnyVideo.setBackground((Color.LIGHT_GRAY));
				btnCatVideo.setText("Cat Video");
				btnCatVideo.setBackground((Color.LIGHT_GRAY));
				btnDrone.setText("Selected");
				btnDrone.setBackground((Color.CYAN));
			}
		});
		add(btnDrone);
		// instruments audio button
		buttonMusic1 = new JButton("Instruments");
		buttonMusic1.setFont(new Font("Dialog", Font.BOLD, 14));
		buttonMusic1.setBounds(34, 352, 155, 36);
		buttonMusic1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				currentSong =  "musicDef.wav";
				buttonMusic1.setText("Selected");
				buttonMusic1.setBackground(Color.CYAN);
				btnPpap.setText("PPAP");
				btnPpap.setBackground(Color.LIGHT_GRAY);
			}
		});
		add(buttonMusic1);
		// PPAP audio button: keep volume low
		btnPpap = new JButton("PPAP");
		btnPpap.setFont(new Font("Dialog", Font.BOLD, 14));
		btnPpap.setBounds(228, 352, 155, 36);
		btnPpap.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				currentSong =  "PPAPshort.wav";
				buttonMusic1.setText("Instruments");
				buttonMusic1.setBackground(Color.LIGHT_GRAY);
				btnPpap.setText("Selected");
				btnPpap.setBackground(Color.CYAN);
			}
		});
		add(btnPpap);
		
		// setting up JLabels with images for each button below:
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(MainMenu._resourcesPath+"bunny.png"));
		label.setBounds(34, 85, 155, 92);
		add(label);
		
		JLabel label_1 = new JLabel();
		label_1.setIcon(new ImageIcon(MainMenu._resourcesPath+"cat.png"));
		label_1.setBounds(233, 85, 145, 92);
		add(label_1);
		
		JLabel label_2 = new JLabel();
		label_2.setIcon(new ImageIcon(MainMenu._resourcesPath+"beach.png"));
		label_2.setBounds(421, 85, 145, 92);
		add(label_2);
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(MainMenu._resourcesPath+"music.png"));
		label_3.setBounds(44, 253, 135, 87);
		add(label_3);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(MainMenu._resourcesPath+"music.png"));
		label_4.setBounds(233, 253, 145, 87);
		add(label_4);
		
		//Home button
		JButton btnBackToMain = new JButton(" Home");
		btnBackToMain.setIcon(new ImageIcon(MainMenu._resourcesPath+"home.png"));
		btnBackToMain.setFont(new Font("Dialog", Font.BOLD, 14));
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.cardLayout.show(MainMenu.cardPanel, "1");
			}
		});
		btnBackToMain.setBounds(450, 340, 115, 45);
		add(btnBackToMain);
	}
}
