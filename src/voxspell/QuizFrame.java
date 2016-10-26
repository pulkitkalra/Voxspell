package voxspell;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Video.Video;
import logic.BackgroundProcess;
import logic.NewQuiz;
import logic.Observer;
import logic.Result;
import logic.ReviewQuiz;
import logic.Test;
import logic.TestFactory;

/**
 * The QuizFrame class is central to the test and quiz functionality implemented in this application
 * An object of quizframe is a Jpanel --> created as a card for the MainMenu Frame and is only
 * ever instantiated once: on start up.
 * This class is responsible for building the interface, updating the interface and for coupling
 * the interface with the logic.
 * It has 3 central methods which set up the 3 panes. These 3 methods must coexist in the class
 * as the panels update and interact with each other often. Although this makes the class slighlty
 * longer, this is the compromise made for largely not breaking encapsulation.
 * @author pulkit
 *
 */
public class QuizFrame extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	protected JPanel jp1, jp2, quizEndCard;
	private CardLayout cardLayout = new CardLayout();
	protected JLabel lblNewSpellingQuiz;
	private Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	private JLabel lblChooseYourSpelling;
	private JLabel lblSciencelistNewSpelling;
	private JLabel status1;
	private JLabel lblLevelAccuracy;
	private JLabel lblLongestStreak;
	private JTextField textPane;
	private JButton replayBtn;
	private JLabel lblLevelProgress;
	private JButton btnStartLevel;
	private JTable table;
	private JLabel lblQuizSummary;
	protected JButton btnPlayVideo;
	private JButton btnReplayLevel;
	private JButton btnNextLevel;
	private JProgressBar progressBar;
	private JEditorPane textOutput;
	protected TestFactory _test;
	private Test _testType;
	private int _level;
	private ActionListener submitWord;
	private BackgroundProcess _bgworker;
	private JComboBox<String> comboBox;
	private JLabel lblFeedback;
	private UpdateQuizView view = new UpdateQuizView();
	private String[][] statsTable; 
	protected JEditorPane dtrpnStartANew;
	private JScrollPane scrollPane;
	private String attempt;
	private JLabel labelScore;

	//Lists:
	private WordList l1;
	private WordList l2;
	
	// currentList:
	private WordList currentList;
	
	/**
	 * constructor checks whether we have New Quiz or Review Quiz and then sets up the GUI.
	 * @param type
	 */
	public QuizFrame(String type) {
		if (type.equals("n")){
			_testType = Test.NewQuiz;
		} else {
			_testType = Test.ReviewQuiz;
		}
		setUpGUI();
	}
	
	/**
	 * Method sets up panel1: The first New/ Review Quiz screen that appears. Also contains
	 * implementation for allowing actions on button register and let the user proceed.
	 */
	public void setUpPanel1(){
		jp1 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainMenu.bkg, 0, 0, null);
			}
		};
		jp1.setLayout(null);

		//Adding the first card
		this.add(jp1, "1");

		lblNewSpellingQuiz = new JLabel("New Spelling Quiz");
		lblNewSpellingQuiz.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
		lblNewSpellingQuiz.setBounds(195, 11, 233, 52);
		jp1.add(lblNewSpellingQuiz);


		Border title = BorderFactory.createTitledBorder(
				loweredetched, "title");
		((TitledBorder)title).setTitleJustification(TitledBorder.RIGHT);

		lblChooseYourSpelling = new JLabel("Select your spelling list:");
		lblChooseYourSpelling.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblChooseYourSpelling.setBounds(40, 152, 235, 34);
		jp1.add(lblChooseYourSpelling);
		
		// Home button (1)
		JButton btnBackToMenu = new JButton(" Home");
		btnBackToMenu.setFont(new Font("Dialog", Font.BOLD, 14));
		btnBackToMenu.setIcon(new ImageIcon(MainMenu._resourcesPath+"home.png"));
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.cardLayout.show(MainMenu.cardPanel, "1");
			}
		});
		btnBackToMenu.setBounds(31, 343, 115, 45); // Standard size: 115 x 45
		jp1.add(btnBackToMenu);
		
		// Button is used to check valid Level input. User cannot select Level 0 or no level.
		JButton btnStartQuiz = new JButton("Start Quiz");
		btnStartQuiz.setFont(new Font("Dialog", Font.BOLD, 14));
		btnStartQuiz.setBackground(new Color(0, 255, 153));
		btnStartQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex()!=0){
					_level = Integer.parseInt(comboBox.getSelectedItem().
							toString().replaceAll("[^0-9]",""));
					cardLayout.show(QuizFrame.this, "2");
					UpdateQuizView v = new UpdateQuizView();
					v.resetView(textOutput, textPane, progressBar, status1, _level, lblLevelAccuracy,
							lblLongestStreak, lblFeedback, btnStartLevel);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a Level to begin", 
							"Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnStartQuiz.setBounds(334, 248, 225, 44);
		jp1.add(btnStartQuiz);

		// info box: New Quiz
		dtrpnStartANew = new JTextPane();
		dtrpnStartANew.setEditable(false);
		dtrpnStartANew.setFont(new Font("Nimbus Sans L", Font.PLAIN, 18));
		dtrpnStartANew.setBackground(UIManager.getColor("CheckBox.background"));
		dtrpnStartANew.setForeground(SystemColor.activeCaption);
		dtrpnStartANew.setText("Start a new quiz by selecting the list you wish to use. \nYou need to then select a level. "
				+ "\nYou must score 9/10 to proceed to the next level!");
		dtrpnStartANew.setBounds(30, 61, 529, 91);
		jp1.add(dtrpnStartANew);
		
		// combo box for lists.
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Select a list to begin"}));
		comboBox.setBounds(40, 248, 230, 44);
		jp1.add(comboBox);
		
		// label for whuch list has been selected
		final JLabel labelQuiz = new JLabel();
		labelQuiz.setFont(new Font("Dialog", Font.BOLD, 14));
		labelQuiz.setBounds(40, 302, 400, 34);
		jp1.add(labelQuiz);

		/**
		 * INITIALSIONG THE WORDLISTS (CUSTOME AND DEFAULT).
		 */
		// Default List
		JButton btnUseDefaultList = new JButton("Use Default List");
		btnUseDefaultList.setFont(new Font("Dialog", Font.BOLD, 14));
		btnUseDefaultList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.currentFile = "d"; // set default list as "d".
				FilePathInstance.setType(MainMenu._resourcesPath+"myFile.txt"); // get instance
				lblSciencelistNewSpelling.setText("Default List: New Spelling Quiz");
				labelQuiz.setText("Default List selected");
				String[] levelList;
				if (_testType == Test.NewQuiz){ // ensure test is New Quiz, other wise different
					//check required
					if (l1 == null){
						l1 = new WordList("d");
						MainMenu.currentLists.add(0, l1);
					}
					levelList = new String[l1.getLevels()]; // generating list of levels (dynamically)
					levelList[0] = "Select a level";
					for (int i =1; i<l1.getLevels();i++){
						levelList[i] = "Level "+i;
					}
					comboBox.setModel(new DefaultComboBoxModel<String>(levelList));
				} else { // dealing with a review quiz.
					lblSciencelistNewSpelling.setText(" Default List: Review Quiz ");
					l1 = MainMenu.currentLists.get(0);
					if (l1 == null){
						l1 = new WordList("d");
						MainMenu.currentLists.add(0, l1);
					}
					List<Integer> nonUsedlevels = l1.getRedundantLevels(); // levels that do not have any review words
					levelList = new String[l1.getLevels()-nonUsedlevels.size()];
					int index = 1;
					levelList[0] = "Select a level";
					for (int i =1; i<l1.getLevels();i++){
						if (!nonUsedlevels.contains(i)){ // not allowing redundant levels to appear in level list.
							levelList[index] = "Level "+i;
							index++;
						}
					}
				}
				currentList  = l1;
				comboBox.setModel(new DefaultComboBoxModel<String>(levelList));
				if (levelList.length == 1){ // list of levels is 1 (only the first info index) 
					JOptionPane.showMessageDialog(null, "There are no words to review in this list!","Error",JOptionPane.ERROR_MESSAGE);
					cardLayout.show(QuizFrame.this, "1"); // so no words to review in this list.
				}
			}
		});
		btnUseDefaultList.setBounds(40, 192, 230, 44);
		jp1.add(btnUseDefaultList);
		
		// method for uploading custom quiz.
		JButton btnUploadCustomList = new JButton("Use custom list");
		btnUploadCustomList.setFont(new Font("Dialog", Font.BOLD, 14));
		btnUploadCustomList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.currentFile = "c";
				FileChoose choose = new FileChoose();
				// if review quiz, we need to ensure list has been uploaded in new quiz.
				if (!choose.exists && _testType == Test.ReviewQuiz){
					JOptionPane.showMessageDialog(null, "You have not uploaded a Custom List!"
							+ "\nPlease return to New Quiz to upload and attempt list.");
					return;
				}
				// get the name of the uploaded file.
				String name = choose.getCustomName();
				if (name!=null){
					FilePathInstance.setType(MainMenu._resourcesPath+name);
				} else {
					return;  // if no file selected
				}
				labelQuiz.setText("Custom List selected: "+ name);
				String[] levelList;
				if (_testType == Test.NewQuiz){
					lblSciencelistNewSpelling.setText("Custom List: New Spelling Quiz");
					if (l2 == null || SettingsFrame.reset){
						l2 = new WordList("c");
						MainMenu.currentLists.add(1, l2);
						SettingsFrame.reset = false;
					}
					levelList = new String[l2.getLevels()];
					levelList[0] = "Select a level";
					for (int i =1; i<l2.getLevels();i++){
						levelList[i] = "Level "+i;
					}
					comboBox.setModel(new DefaultComboBoxModel<String>(levelList));
				} else {
					lblSciencelistNewSpelling.setText("   Custom List: Review Quiz   ");
					l2 = MainMenu.currentLists.get(1);
					if (l2 == null){
						l2 = new WordList("c");
						MainMenu.currentLists.add(1, l2);
					}
					List<Integer> nonUsedlevels = l2.getRedundantLevels();
					levelList = new String[l2.getLevels()-nonUsedlevels.size()];
					levelList[0] = "Select a level";
					int index = 1;
					for (int i =1; i<l2.getLevels();i++){
						if (!nonUsedlevels.contains(i)){
							levelList[index] = "Level "+i;
							index++;
						}
					}
				}
				currentList  = l2;
				comboBox.setModel(new DefaultComboBoxModel<String>(levelList));
				if (levelList.length == 1){
					JOptionPane.showMessageDialog(null, "There are no words to review in this list!","Error",JOptionPane.ERROR_MESSAGE);
					cardLayout.show(QuizFrame.this, "1");
				}
			}
		});
		btnUploadCustomList.setBounds(334, 192, 225, 44);
		jp1.add(btnUploadCustomList);
	}
	/**
	 * Method set up the panel used for testing. Couples the logic for starting quiz and operation
	 * of a quiz with the interface.
	 */
	@SuppressWarnings("serial")
	public void setUpPanel2(){
		jp2 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainMenu.bkg, 0, 0, null);
			}
		};
		jp2.setLayout(null);

		this.add(jp2, "2");
		// label: Title
		lblSciencelistNewSpelling = new JLabel("Default List: New Spelling Quiz");
		lblSciencelistNewSpelling.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
		lblSciencelistNewSpelling.setBounds(112, 12, 402, 39);
		jp2.add(lblSciencelistNewSpelling);
		// Level status
		status1 = new JLabel("Current Level: ");
		status1.setFont(new Font("Dialog", Font.BOLD, 12));
		status1.setBounds(15, 96, 182, 29);
		jp2.add(status1);
		// level accuracy
		lblLevelAccuracy = new JLabel("Level Accuracy: ");
		lblLevelAccuracy.setFont(new Font("Dialog", Font.BOLD, 12));
		lblLevelAccuracy.setBounds(15, 136, 182, 29);
		jp2.add(lblLevelAccuracy);
		// streak label
		lblLongestStreak = new JLabel("Current Streak:");
		lblLongestStreak.setFont(new Font("Dialog", Font.BOLD, 12));
		lblLongestStreak.setBounds(15, 176, 182, 29);
		jp2.add(lblLongestStreak);
		// progress bar
		progressBar = new JProgressBar();
		progressBar.setValue(50);
		progressBar.setBounds(15, 289, 200, 41);
		jp2.add(progressBar);
		// abort button
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(MainMenu._resourcesPath+"abort.png"));
		btnNewButton.setToolTipText("Abort Quiz");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to abort the quiz?"
						+ "\nNote: Statistics for this level will not be stored "
						+ "\nand you will lose your level progress.",
						"Abort Quiz", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					cardLayout.show(QuizFrame.this, "1");
				}
				else {
					return;
				}

			}
		});
		btnNewButton.setBounds(15, 348, 49, 40);
		jp2.add(btnNewButton);
		// text output: shows word number etc.
		textOutput = new JEditorPane();
		textOutput.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		textOutput.setBackground(new Color(255, 255, 255));
		textOutput.setBounds(210, 109, 364, 96);
		textOutput.setEditable(false);
		textOutput.setFocusable(false);
		jp2.add(textOutput);
		// user enters the answer here
		textPane = new JTextField();
		textPane.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		textPane.setBounds(210, 218, 262, 39);
		jp2.add(textPane);
		textPane.setColumns(10);
		submitWord = new ActionListener(){ // action listener used to submit a word.
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textPane.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "No word was entered!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (!textPane.getText().matches(("[a-zA-Z]+"))){
					JOptionPane.showMessageDialog(null, "Only letters of the alphabet are allowed.\n"
							+ "(Capital letters are allowed)", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					textPane.setText("");
					return;
				}
				attempt = textPane.getText(); // recording what the user entered
				textPane.setText("");
				textPane.removeActionListener(this);
				textPane.requestFocusInWindow();
				_bgworker = new BackgroundProcess(attempt,_test, QuizFrame.this,currentList);
				_bgworker.execute();
			}

		};
		textPane.addActionListener(submitWord);
		// replay button
		replayBtn = new JButton("Hear Word Again");
		replayBtn.setFont(new Font("Dialog", Font.BOLD, 14));
		replayBtn.setBounds(15, 220, 182, 36);
		replayBtn.addActionListener(new ActionListener(){
			// has functionality to say word via festival again.
			@Override
			public void actionPerformed(ActionEvent e) {
				textPane.requestFocusInWindow();
				SwingWorker<Void,Void> replay = new SwingWorker<Void,Void>(){
					@Override
					protected Void doInBackground() throws Exception {
						System.out.println(_test);
						_test.festival(_test.getTestList().get(_test.getWordNumber()),"1.5");
						return null;
					}
					@Override
					protected void done(){
						replayBtn.setEnabled(true);
					}
				};
				replayBtn.setEnabled(false);
				replay.execute();
			}
		});
		jp2.add(replayBtn);
		// settings button
		JButton btnSettings = new JButton("");
		Icon icon = new ImageIcon(MainMenu._resourcesPath+"settingsIcon.png");
		Image img = ((ImageIcon) icon).getImage() ;  
		Image newimg = img.getScaledInstance( 45, 45,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		btnSettings.setIcon(icon);
		btnSettings.setFocusable(false);
		btnSettings.setToolTipText("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsFrame settings = new SettingsFrame();
			}
		});
		jp2.add(btnSettings);
		// progess Bar
		btnSettings.setBounds(498, 299, 76, 75);
		lblLevelProgress = new JLabel("Level progress:");
		lblLevelProgress.setBounds(15, 268, 106, 20);
		jp2.add(lblLevelProgress);
		// action listner for new test: ewrapped with SwingWorker: concurrency
		ActionListener newTest = (new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingWorker<Void,Void> replay2 = new SwingWorker<Void,Void>(){
					@Override
					protected Void doInBackground() throws Exception {
						textOutput.setText("Please spell Word 1: ");
						replayBtn.setEnabled(true);
						textPane.setEnabled(true);
						textPane.requestFocusInWindow();
						status1.setText("Current Level: Level " +_level);
						switch(_testType){
						case NewQuiz: 
							_test = new NewQuiz(_level);
							break;
						case ReviewQuiz:
							_test = new ReviewQuiz(_level);
							break;
						} // switching frames below:
						if (!_test.startTest(currentList)){
							cardLayout.show(QuizFrame.this, "1");
						}
						if (!_test.hasWords()){
							cardLayout.show(QuizFrame.this, "1");
						}
						return null;
					}
					@Override
					protected void done(){
						replayBtn.setEnabled(true);
					}
				};
				btnStartLevel.setEnabled(false);
				replay2.execute();
			}
		});
		// panel: used to start the test. always has the focus on startup.
		btnStartLevel = new JButton("Begin Test");
		btnStartLevel.setBackground(new Color(224, 255, 255));
		btnStartLevel.setFont(new Font("Dialog", Font.BOLD, 14));
		btnStartLevel.requestFocusInWindow();
		btnStartLevel.setBounds(211, 57, 166, 32);
		btnStartLevel.addActionListener(newTest);
		jp2.add(btnStartLevel);
		// another button used to submit response
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Dialog", Font.BOLD, 14));
		btnSubmit.setBounds(479, 217, 95, 39);
		btnSubmit.addActionListener(submitWord);
		jp2.add(btnSubmit);
		// icon showing cross/ tick
		lblFeedback = new JLabel();
		lblFeedback.setFont(new Font("Dialog", Font.BOLD, 16));
		lblFeedback.setIcon(new ImageIcon());
		lblFeedback.setBounds(251, 269, 221, 105);
		jp2.add(lblFeedback);
	}
	/**
	 * this method configures the third and final panel required to set up the end of quiz
	 * configures the buttons which allow next level, same level, back to home and reward.
	 * Also adds the table as required.
	 */
	public void setUpPanel3(){
		quizEndCard = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainMenu.bkg, 0, 0, null);
			}
		};
		//***************************
		// Adding the 3rd card
		this.add(quizEndCard, "3");
		quizEndCard.setLayout(null);
		// title
		JLabel lblQuizComplete = new JLabel("Quiz Complete!");
		lblQuizComplete.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
		lblQuizComplete.setBounds(195, 11, 211, 39);
		quizEndCard.add(lblQuizComplete);
		// quiz score (always /10 for new quiz)
		lblQuizSummary = new JLabel("Quiz Score: ");
		lblQuizSummary.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblQuizSummary.setBounds(30, 47, 102, 28);
		quizEndCard.add(lblQuizSummary);
		// video reward: New Quiz
		btnPlayVideo = new JButton("Play Video Reward");
		btnPlayVideo.setFont(new Font("Dialog", Font.BOLD, 14));
		btnPlayVideo.setBounds(206, 275, 199, 39);
		btnPlayVideo.addActionListener(new ActionListener(){ // listener for playing video
			@Override
			public void actionPerformed(ActionEvent e) {
				String selection = VoxStore.currentVideo;
				if (selection!=null) {
					Video vid = new Video();
					vid.start(selection);
				}
			}
		});
		btnPlayVideo.setToolTipText("You redeem reward with a score of 9 or more!");
		quizEndCard.add(btnPlayVideo);
		// replay level
		btnReplayLevel = new JButton("Replay Level");
		btnReplayLevel.setFont(new Font("Dialog", Font.BOLD, 14));
		btnReplayLevel.setBounds(30, 275, 147, 39);
		btnReplayLevel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeLevel(false);
			}
		});
		quizEndCard.add(btnReplayLevel);
		// next level button
		btnNextLevel = new JButton("Next Level");
		btnNextLevel.setFont(new Font("Dialog", Font.BOLD, 14));
		btnNextLevel.setBounds(437, 275, 137, 39);
		btnNextLevel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeLevel(true);
			}
		});
		btnNextLevel.setToolTipText("You can Level Up with a score of 9 or more!");
		quizEndCard.add(btnNextLevel);
		// home button
		JButton btnBackToMain = new JButton(" Home");
		btnBackToMain.setIcon(new ImageIcon(MainMenu._resourcesPath+"home.png"));
		btnBackToMain.setFont(new Font("Dialog", Font.BOLD, 14));
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(QuizFrame.this, "1");
				MainMenu.cardLayout.show(MainMenu.cardPanel, "1");
			}
		});
		btnBackToMain.setBounds(30, 335, 115, 45);
		quizEndCard.add(btnBackToMain);
		// button to return back to new quiz
		JButton btnFinishQuiz = new JButton("Finish Quiz");
		btnFinishQuiz.setFont(new Font("Dialog", Font.BOLD, 14));
		btnFinishQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(QuizFrame.this, "1");
			}
		});
		btnFinishQuiz.setBounds(407, 335, 167, 39);
		quizEndCard.add(btnFinishQuiz);
		this.add(quizEndCard,"new");
		// quiz score
		labelScore = new JLabel("~/10");
		labelScore.setFont(new Font("Dialog", Font.BOLD, 14));
		labelScore.setBounds(133, 47, 102, 28);
		quizEndCard.add(labelScore);
	}
	/**
	 * setsUpGui when called upon by the constructor. Calls the 3 methods that create the panels.
	 */
	public void setUpGUI(){
		setBounds(10, 100, 600, 400);
		this.setBounds(0, 0, 600, 400);
		this.setLayout(cardLayout);
		// set up 1st panel.
		setUpPanel1();
		// set up 2nd panel
		setUpPanel2();
		//set up 3rd panel: end of quiz
		setUpPanel3();
	}
	/**
	 * Method for updating the JTextArea on the GUI and handling completion of levels.
	 */
	public void update(Result result) {
		textPane.addActionListener(submitWord);
		if (view.updateView(_test,result, textOutput, textPane, attempt, progressBar, 
				lblLevelAccuracy, lblLongestStreak, lblFeedback)){
			// Do nothing
		} else { // Quiz Finished.
			if (scrollPane != null){
				quizEndCard.remove(scrollPane);
			}
			statsTable = view.getTable();
			int score = view.getAttempts();
			int count = _test.getWordCount();
			labelScore.setText(score+"/"+count);
			List<Integer> listCorrect = view.getCorrectArrayIndex();
			view = new UpdateQuizView();
			table = new JTable();
			if (_testType==Test.ReviewQuiz){ // configuring buttons for review quiz.
				btnPlayVideo.setEnabled(false);
				btnNextLevel.setEnabled(true);
			} else if (score< 9){
				btnPlayVideo.setEnabled(false);
				btnNextLevel.setEnabled(false);
			} else {
				btnPlayVideo.setEnabled(true);
				btnNextLevel.setEnabled(true);
			}
			@SuppressWarnings("serial")
			DefaultTableModel model = new DefaultTableModel(statsTable,
					new String[] {"Word", "Attempts"}){
				@Override
				public boolean isCellEditable(int row, int column) {
					//all cells false
					return false;
				} // this table model us added to table to allow non-editable and table renderer.
			}; 
			table.setModel(model);
			table.setRowHeight(30);
			table.getColumnModel().getColumn(0).setPreferredWidth(350);
			table.getColumnModel().getColumn(1).setPreferredWidth(190);
			table.setDefaultRenderer(Object.class, new MyRenderer(listCorrect)); // Adding renderer to table.
			table.setFillsViewportHeight(true); 
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(table);
			scrollPane.setBounds(30, 81, 540, 180);
			quizEndCard.add(scrollPane);
			cardLayout.show(this, "new");
			// Updating stats file:
			currentList.saveWordProgress();
		}
	}
	/**
	 * Method used to change levels (or keep them same). Shows warning message if no more levels available.
	 * @param levelUp
	 *  TRUE if you want to level up, else false.
	 */
	public void changeLevel(boolean levelUp){
		if (levelUp){
			if (_level == currentList.getLevels()-1){
				JOptionPane.showMessageDialog(null, "No more levels in this quiz!");
				return;
			} else {
				_level++;
			}
		}
		view.resetView(textOutput, textPane, progressBar, status1, _level, 
				lblLevelAccuracy, lblLongestStreak, lblFeedback, btnStartLevel);
		cardLayout.show(QuizFrame.this, "2");
	}
}
