package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class QuizFrame extends JFrame {
	
	private JFrame frame;
	private static final long serialVersionUID = 1L;
	private JPanel cardPanel, jp1, jp2, jp3;
	private CardLayout cardLayout = new CardLayout();
	private JLabel lblNewSpellingQuiz;
	private Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	private JLabel lblChooseYourSpelling;
	private JLabel lblSciencelistNewSpelling;
	private JLabel lblSelectALevel;
	private JComboBox comboBox_1;
	private JLabel lblCurrentLevel;
	private JLabel lblLevelAccuracy;
	private JLabel lblLongestStreak;
	private JLabel lblCurrentVoice;
	private JTextField textField;
	private JButton btnHearWordAgain;
	private JLabel lblCorrectIncorrectImage;
	private JLabel lblLevelProgress;
	private JButton btnStartLevel;
	private JTable table;
	private JLabel lblQuizSummary;
	private JLabel lblYouMustGet;
	private JButton btnPlayVideoReward;
	private JButton btnReplayLevel;
	private JButton btnNextLevel;
	private JProgressBar progressBar;

	public QuizFrame() {
		frame = this;
		frame.setVisible(true);
		setBounds(100, 100, 600, 400);
		cardPanel = new JPanel();
		cardPanel.setBounds(0, 0, 584, 361);
		cardPanel.setLayout(cardLayout);
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp1.setLayout(null);
		jp2.setLayout(null);
		
		//Adding the first card.
		cardPanel.add(jp1, "1");

		lblNewSpellingQuiz = new JLabel("New Spelling Quiz");
		lblNewSpellingQuiz.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
		lblNewSpellingQuiz.setBounds(195, 11, 233, 52);
		jp1.add(lblNewSpellingQuiz);


		Border title = BorderFactory.createTitledBorder(
				loweredetched, "title");
		((TitledBorder)title).setTitleJustification(TitledBorder.RIGHT);
		
		JLabel lblStartingA = new JLabel("Starting a new quiz ..");
		lblStartingA.setBorder(title);
		lblStartingA.setBounds(10, 69, 564, 81);
		jp1.add(lblStartingA);
		
		lblChooseYourSpelling = new JLabel("Choose your spelling list:");
		lblChooseYourSpelling.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblChooseYourSpelling.setBounds(30, 161, 191, 34);
		jp1.add(lblChooseYourSpelling);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select a list to begin", "ScienceQuiz", "DefaultList1"}));
		comboBox.setBounds(30, 206, 260, 39);
		jp1.add(comboBox);
		
		JButton btnUploadCustomList = new JButton("Upload custom list");
		btnUploadCustomList.setBounds(360, 203, 183, 44);
		jp1.add(btnUploadCustomList);
		
		JButton btnBackToMenu = new JButton("Back to Main Menu");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainMenu main = new MainMenu();
				main.setLocationRelativeTo(null);
			}
		});
		btnBackToMenu.setBounds(10, 311, 148, 34);
		jp1.add(btnBackToMenu);
		
		JButton btnStartQuiz = new JButton("Start Quiz");
		btnStartQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "2");
			}
		});
		btnStartQuiz.setBounds(214, 269, 166, 34);
		jp1.add(btnStartQuiz);
		
		// Adding the second card
		cardPanel.add(jp2, "2");
		
		lblSciencelistNewSpelling = new JLabel("ScienceList: New Spelling Quiz");
		lblSciencelistNewSpelling.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
		lblSciencelistNewSpelling.setBounds(151, 11, 326, 39);
		jp2.add(lblSciencelistNewSpelling);
		
		lblSelectALevel = new JLabel();
		lblSelectALevel.setText("Select a level to begin: ");
		lblSelectALevel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblSelectALevel.setBounds(10, 47, 182, 20);
		jp2.add(lblSelectALevel);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Select a level", "Level 1", "Level 2", "Level 3", "Level 4"}));
		comboBox_1.setBounds(10, 78, 424, 29);
		jp2.add(comboBox_1);
		
		lblCurrentLevel = new JLabel("Current Level: ");
		lblCurrentLevel.setBounds(10, 118, 182, 29);
		jp2.add(lblCurrentLevel);
		
		lblLevelAccuracy = new JLabel("Level Accuracy: ");
		lblLevelAccuracy.setBounds(10, 147, 182, 29);
		jp2.add(lblLevelAccuracy);
		
		lblLongestStreak = new JLabel("Longest Streak:");
		lblLongestStreak.setBounds(10, 179, 182, 29);
		jp2.add(lblLongestStreak);
		
		lblCurrentVoice = new JLabel("Current Voice:");
		lblCurrentVoice.setBounds(10, 211, 182, 29);
		jp2.add(lblCurrentVoice);
		
		progressBar = new JProgressBar();
		progressBar.setValue(50);
		progressBar.setBounds(10, 291, 195, 20);
		jp2.add(progressBar);
		
		JButton btnNewButton = new JButton("Abort Quiz");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "1");
			}
		});
		btnNewButton.setBounds(10, 322, 106, 23);
		jp2.add(btnNewButton);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(215, 118, 359, 108);
		jp2.add(editorPane);
		
		textField = new JTextField();
		textField.setBounds(215, 237, 359, 29);
		jp2.add(textField);
		textField.setColumns(10);
		
		btnHearWordAgain = new JButton("Hear Word Again");
		btnHearWordAgain.setBounds(10, 240, 160, 23);
		jp2.add(btnHearWordAgain);
		getContentPane().setLayout(null);
		getContentPane().add(cardPanel);
		JButton btnSettings = new JButton("");
		Icon icon = new ImageIcon("C:\\Users\\Pulkit\\Documents\\Workspace\\250_sorting\\voxspell_gui\\settingsIcon.png");
		Image img = ((ImageIcon) icon).getImage() ;  
		Image newimg = img.getScaledInstance( 38, 38,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		btnSettings.setIcon(icon);
		btnSettings.setFocusable(false);
		btnSettings.setToolTipText("Settings");
		jp2.add(btnSettings);
		

		btnSettings.setBounds(498, 275, 76, 75);
		
		lblCorrectIncorrectImage = new JLabel("Correct/ Incorrect Image");
		lblCorrectIncorrectImage.setBounds(215, 270, 262, 75);
		jp2.add(lblCorrectIncorrectImage);
		
		lblLevelProgress = new JLabel("level progress:");
		lblLevelProgress.setBounds(10, 274, 106, 14);
		jp2.add(lblLevelProgress);
		
		btnStartLevel = new JButton("Start Level");
		btnStartLevel.setBounds(444, 78, 130, 29);
		jp2.add(btnStartLevel);
		
		JButton btnNewButton_1 = new JButton("Q Complete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "3");
			}
		});
		btnNewButton_1.setBounds(225, 327, 106, 23);
		jp2.add(btnNewButton_1);
		
		// Adding the 3rd card
		cardPanel.add(jp3, "3");
		jp3.setLayout(null);
		
		JLabel lblQuizComplete = new JLabel("Quiz Complete!");
		lblQuizComplete.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
		lblQuizComplete.setBounds(213, 11, 167, 39);
		jp3.add(lblQuizComplete);
		
		
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"studio", "2", "5"},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Word", "Correct", "Incorrect"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(214);
		table.getColumnModel().getColumn(1).setPreferredWidth(85);
		table.setBounds(78, 55, 442, 161);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.add(table);
		scrollPane.setBounds(20, 81, 522, 147);
		jp3.add(scrollPane);
		
		lblQuizSummary = new JLabel("Quiz Summary:");
		lblQuizSummary.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuizSummary.setBounds(20, 47, 137, 28);
		jp3.add(lblQuizSummary);
		
		lblYouMustGet = new JLabel("You must get at leat 9/10 words correct to progress level and redeem the reward!");
		lblYouMustGet.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblYouMustGet.setBounds(20, 234, 522, 28);
		jp3.add(lblYouMustGet);
		
		btnPlayVideoReward = new JButton("Play Video Reward");
		btnPlayVideoReward.setBounds(207, 273, 167, 28);
		jp3.add(btnPlayVideoReward);
		
		btnReplayLevel = new JButton("Replay Level");
		btnReplayLevel.setBounds(10, 273, 137, 28);
		jp3.add(btnReplayLevel);
		
		btnNextLevel = new JButton("Next Level");
		btnNextLevel.setBounds(437, 273, 137, 28);
		jp3.add(btnNextLevel);
		
		JButton btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainMenu main = new MainMenu();
				main.setLocationRelativeTo(null);
			}
		});
		btnBackToMain.setBounds(10, 322, 147, 28);
		jp3.add(btnBackToMain);
		
		JButton btnFinishQuiz = new JButton("Finish Quiz");
		btnFinishQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "1");
			}
		});
		btnFinishQuiz.setBounds(437, 322, 137, 28);
		jp3.add(btnFinishQuiz);
		//jp3.add(table);
		
	}
	
	public void newQuizSetUp(){
		lblSelectALevel.setText("Select a level to begin: ");
		comboBox_1.setEnabled(true);
		comboBox_1.setSelectedIndex(0);
		btnStartLevel.setEnabled(true);
		textField.setEnabled(true);
		textField.setText("");
		btnHearWordAgain.setEnabled(true);
		progressBar.setValue(0);
		lblLevelAccuracy.setText("Level Accuracy: - ");
		lblLongestStreak.setText("Longest Streak: 0");
		// Will also need to set default correct/ incorrect image.
		
	}
}