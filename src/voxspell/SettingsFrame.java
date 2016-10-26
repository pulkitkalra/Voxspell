package voxspell;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Voice.VoiceInstance;
import Voice.VoiceType;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;

/**
 * this class creates the Settings Frame which is used to generate the settings page we can see.
 * It has functionality to allow voice type and speed to change, and to view help and about info
 * Also it we have the option to remove the current custom list.
 * @author pkal608
 *
 */
@SuppressWarnings("serial")
public class SettingsFrame extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	protected static boolean reset = false;

	/**
	 * Create the frame: cannot be resized and other properties set.
	 */
	public SettingsFrame() {

		frame = this;
		//frame.pack();
		frame.setResizable(false);
		setUpGUI();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});

	}
	/**
	 * Method configures the GUI by building the relevant components like buttons and combo boxes and generating 
	 * their labels dynamically.
	 * There is functionality to change voice type, speed, the current custom list, about, instructions and save changes
	 */
	public void setUpGUI(){

		setBounds(100, 100, 600, 425);
		setTitle("Settings");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainMenu.bkg, 0, 0, null);
			}
		};
		contentPane.setLayout(null);
		getContentPane().setLayout(null);
		contentPane.setBounds(0, 0, 600, 425);
		// settings laebl
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
		lblSettings.setBounds(244, 11, 106, 52);
		contentPane.add(lblSettings);
		// change voice label
		JLabel lblCurrentVoice = new JLabel("Change Voice");
		lblCurrentVoice.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblCurrentVoice.setBounds(56, 104, 179, 34);
		contentPane.add(lblCurrentVoice);
		// voice label
		JLabel lblVoice = new JLabel("Current Voice");
		lblVoice.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblVoice.setBounds(56, 61, 179, 34);
		contentPane.add(lblVoice);
		// current voice label
		String name = "";
		int index = 0;
		if (VoiceInstance.getTypeInstance().equals("kal_diphone")){
			name = "Default Voice";
		} else {
			name = "New Zealand Voice";
			index = 1;
		}
		final JLabel statusVoice = new JLabel(name);
		statusVoice.setFont(new Font("Dialog", Font.BOLD, 14));
		statusVoice.setBounds(264, 61, 179, 34);
		contentPane.add(statusVoice);
		// change voice combo box
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Default Voice", "New Zealand Voice"}));
		comboBox.setSelectedIndex(index);
		comboBox.setBounds(264, 106, 268, 34);
		comboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedItem().equals("Default Voice")){
					VoiceInstance.setTypeInstance(VoiceType.DEF_VOICE.getVoice());
					statusVoice.setText("Default");
					//Scanner sc = new Scanner
				} else if (comboBox.getSelectedItem().equals("New Zealand Voice")){
					VoiceInstance.setTypeInstance(VoiceType.NZ_VOICE.getVoice());
					statusVoice.setText("New Zealand Voice");
				}
			}
		});
		contentPane.add(comboBox);
		// change speed label
		JLabel lblCurrentVoiceSpeed = new JLabel("Current Voice Speed");
		lblCurrentVoiceSpeed.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblCurrentVoiceSpeed.setBounds(56, 149, 179, 34);
		contentPane.add(lblCurrentVoiceSpeed);
		// current speed label
		String name2 = "";
		int index2 = 0;
		String strSpeed = VoiceInstance.getSpeedInstance();
		if (strSpeed.equals("0.7")){
			name2 = "Fast";
			index2 = 3;
		} else if (strSpeed.equals("1.0")) {
			name2 = "Normal";
			index2 = 2;
		} else if (strSpeed.equals("1.5")) {
			name2 = "Slow";
			index2 = 1;
		} else {
			name2 = "Very Slow";
		}
		final JLabel lblSlowFastNormal = new JLabel(name2);
		lblSlowFastNormal.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSlowFastNormal.setBounds(264, 149, 179, 34);
		contentPane.add(lblSlowFastNormal);
		// change voice speed label
		JLabel label_2 = new JLabel("Change Voice Speed");
		label_2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		label_2.setBounds(56, 192, 179, 34);
		contentPane.add(label_2);
		// change voice speed combobox
		final JComboBox<String> speedSelector = new JComboBox<String>();
		speedSelector.setFont(new Font("Dialog", Font.BOLD, 14));
		speedSelector.setModel(new DefaultComboBoxModel<String>(new String[] {"Very Slow", "Slow", "Normal", "Fast"}));
		speedSelector.setSelectedIndex(index2);
		speedSelector.setBounds(264, 194, 268, 34);
		speedSelector.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected = (String) speedSelector.getSelectedItem();
				lblSlowFastNormal.setText(selected);
				System.out.println(selected);
				if (selected.equals("Very Slow")){
					VoiceInstance.setSpeedInstance("2.0");
				} else if (selected.equals("Slow")){
					VoiceInstance.setSpeedInstance("1.5");
				} else if (selected.equals("Normal")){
					VoiceInstance.setSpeedInstance("1.0");
				} else {
					VoiceInstance.setSpeedInstance("0.7");
				}
			}
		});
		contentPane.add(speedSelector);
		// custom list label
		JLabel lblViewMySpelling = new JLabel("Custom List");
		lblViewMySpelling.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblViewMySpelling.setBounds(56, 240, 179, 34);
		contentPane.add(lblViewMySpelling);
		// custom list change label
		JButton btnViewLists = new JButton("Remove Custom list");
		btnViewLists.setFont(new Font("Dialog", Font.BOLD, 14));
		btnViewLists.setBounds(261, 248, 271, 40);
		btnViewLists.addActionListener(new ActionListener(){
			@Override
			/**
			 * This will go through and delete all files associated with the current custom list
			 * so next time a new quiz is tried, user can upload another custom list.
			 */
			public void actionPerformed(ActionEvent e) {
				FileChoose choose = new FileChoose();
				if (!choose.exists){
					JOptionPane.showMessageDialog(null, "You have not uploaded a Custom List!");
					return;
				}	
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove custom list in operation?"
						+ "\nThis action will erase all stats for all words in this list", "Confirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					try {
						String name = choose.getFileName();
						File f1 = new File(MainMenu._resourcesPath + name);
						File f2 = new File(MainMenu._resourcesPath + name + "-stats");
						File f3 = new File(MainMenu._resourcesPath + name + "-failed");
						f1.delete();
						f2.delete();
						f3.delete();
						reset = true;
						//MainMenu.currentLists.add(1,null);
						File customList = new File(MainMenu._resourcesPath+"customStatus.txt");
						customList.delete();
						File customListNew = new File(MainMenu._resourcesPath+"customStatus.txt");
						JOptionPane.showMessageDialog(null, "Custom List Removed!"
								+ "\nYou can now go to New Quiz and upload a Custom List and try it!");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					return;
				}
			}
		});
		contentPane.add(btnViewLists);
		// help button
		JButton btnAboutVoxspell = new JButton("Help & Instructions");
		btnAboutVoxspell.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAboutVoxspell.setBounds(370, 300, 220, 40);
		btnAboutVoxspell.setIcon(new ImageIcon(MainMenu._resourcesPath+"help.png"));
		btnAboutVoxspell.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "Once you are in the main menu, simply select one of the four"
						+ "\nbuttons and follow instructions from there."
						+ "\nA New Quiz is used to test 10 words from a spelling list."
						+ "\nReview Quiz lets you look back and correct your mistakes during a new quiz"
						+ "\nVisit the Vox Store to select you video and audio rewards!"
						+ "\nSettings page can help you make configuration changes in the application."
						+ "\n\nCustom List guidlines: (1) must choose a .txt file which defines at least"
						+ "\none level. (2) Levels must be defined like %Level 1 and in order. (3) No "
						+ "\nblack spaces or lines. (4) Each level MUST HAVE at least 10 WORDS."
						+ "\n\nSee the User Manual for a complete guide.";
				JOptionPane.showMessageDialog(null, message, "Help", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		contentPane.add(btnAboutVoxspell);
		// about button
		JButton button = new JButton("About Voxspell");
		button.setFont(new Font("Dialog", Font.BOLD, 14));
		button.setBounds(370, 350, 220, 40);
		button.setIcon(new ImageIcon(MainMenu._resourcesPath+"info.png"));
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "                   ABOUT VOXSPELL                 "
							   + "\nDeveloped as a Project for SOFTENG 206"
							   + "\nAuthor: Pulkit Kalra,   upi: pkal608"
							   + "\n\nCREDITS:"
							   + "\nPeople: @Darius Au - prototype project partner"
							   + "\nImage Icons: www.iconfinder.com (under the "
							   + "\n       creative commons licence)."
							   + "\nVideo: Royalty Free Videos from -"
							   + "\n       www.cyberwebfx.com   and "
							   + "\n	    Africa Travel Channel (via YouTube)"
							   + "\nSong credits: "
							   + "\n Audio Library: No CopyRight Music (via YouTube)"
							   + "\n\nALL RIGHTS RESERVED. Pulkit Kalra 2016";
				JOptionPane.showMessageDialog(null, message, "About", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		contentPane.add(button);
		// save changes (exit) button
		JButton button_1 = new JButton("Save Changes");
		button_1.setFont(new Font("Dialog", Font.BOLD, 14));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		button_1.setBounds(24, 354, 170, 34);
		contentPane.add(button_1);
		frame.getContentPane().add(contentPane);
	}
}
