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

public class QuizFrame extends JPanel implements Observer {

	//private JFrame frame;
	private static final long serialVersionUID = 1L;
	private JPanel jp1, jp2, jp3;
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
	private JButton btnPlayVideo;
	private JButton btnReplayLevel;
	private JButton btnNextLevel;
	private JProgressBar progressBar;
	private JEditorPane textOutput;
	private TestFactory _test;
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

	private WordList currentList;

	public QuizFrame(String type) {
		if (type.equals("n")){
			_testType = Test.NewQuiz;
		} else {
			_testType = Test.ReviewQuiz;
		}
		setUpGUI();
		/*frame = this;
		frame.setResizable(false);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);	
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});*/
	}




	@SuppressWarnings("serial")
	public void setUpGUI(){
		setBounds(10, 100, 600, 400);
		//this = new JPanel();
		this.setBounds(0, 0, 600, 400);
		this.setLayout(cardLayout);
		jp1 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainMenu.bkg, 0, 0, null);
			}
		};
		jp2 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainMenu.bkg, 0, 0, null);
			}
		};
		jp3 = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainMenu.bkg, 0, 0, null);
			}
		};
		jp1.setLayout(null);
		jp2.setLayout(null);

		//Adding the first card.s
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
		lblChooseYourSpelling.setBounds(40, 152, 230, 34);
		jp1.add(lblChooseYourSpelling);

		JButton btnBackToMenu = new JButton(" Home");
		btnBackToMenu.setIcon(new ImageIcon(MainMenu._resourcesPath+"home.png"));
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.cardLayout.show(MainMenu.cardPanel, "1");
			}
		});
		btnBackToMenu.setBounds(26, 331, 115, 45); // Standard size: 115 x 45
		jp1.add(btnBackToMenu);

		JButton btnStartQuiz = new JButton("Start Quiz");
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
		btnStartQuiz.setBounds(334, 248, 210, 44);
		jp1.add(btnStartQuiz);

		dtrpnStartANew = new JTextPane();
		dtrpnStartANew.setEditable(false);
		dtrpnStartANew.setFont(new Font("Nimbus Sans L", Font.PLAIN, 18));
		dtrpnStartANew.setBackground(UIManager.getColor("CheckBox.background"));
		dtrpnStartANew.setForeground(SystemColor.activeCaption);
		dtrpnStartANew.setText("Start a new quiz by selecting the list you wish to use. \nYou need to then select a level. "
				+ "\nYou must score 9/10 to proceed to the next level!");
		dtrpnStartANew.setBounds(30, 61, 529, 91);
		jp1.add(dtrpnStartANew);

		comboBox = new JComboBox<String>();
		/*comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});*/
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Select a list to begin"}));
		comboBox.setBounds(40, 248, 210, 44);
		jp1.add(comboBox);

		JLabel labelQuiz = new JLabel("");
		labelQuiz.setBounds(40, 304, 199, 25);
		jp1.add(labelQuiz);
		
		/***********************************************************************************************************************
		 * COME HERE TO SEE THE LISTS INTIALISED.
		 */

		JButton btnUseDefaultList = new JButton("Use Default List");
		btnUseDefaultList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.currentFile = "d";
				FilePathInstance.setType(MainMenu._resourcesPath+"myFile.txt");
				lblSciencelistNewSpelling.setText("Default List: New Spelling Quiz");
				labelQuiz.setText("Default List selected");
				String[] levelList;
				if (_testType == Test.NewQuiz){
					
					if (l1 == null){
						l1 = new WordList("d");
						MainMenu.currentLists.add(0, l1);
					}
					levelList = new String[l1.getLevels()];
					// this line is need to initialise the levelWordRecords
					//Stats s = new Stats();
					levelList[0] = "Select a level";
					for (int i =1; i<l1.getLevels();i++){
						levelList[i] = "Level "+i;
					}
					comboBox.setModel(new DefaultComboBoxModel<String>(levelList));
				} else {
					lblSciencelistNewSpelling.setText(" Default List: Review Quiz ");
					l1 = MainMenu.currentLists.get(0);
					if (l1 == null){
						l1 = new WordList("d");
						MainMenu.currentLists.add(0, l1);
					}
					System.out.println("");
					List<Integer> nonUsedlevels = l1.getRedundantLevels();
					levelList = new String[l1.getLevels()-nonUsedlevels.size()];
					//String[] levelList = new String[l1.getLevels()];
					// this line is need to initialise the levelWordRecords
					//Stats s = new Stats();
					int index = 1;
					levelList[0] = "Select a level";
					for (int i =1; i<l1.getLevels();i++){
						if (!nonUsedlevels.contains(i)){
							levelList[index] = "Level "+i;
							index++;
						}

					}
				}
				currentList  = l1;
				comboBox.setModel(new DefaultComboBoxModel<String>(levelList));
				if (levelList.length == 1){
					JOptionPane.showMessageDialog(null, "There are no words to review in this list!","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnUseDefaultList.setBounds(40, 192, 210, 44);
		jp1.add(btnUseDefaultList);

		JButton btnUploadCustomList = new JButton("Use custom list");
		btnUploadCustomList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.currentFile = "c";
				FilePathInstance.setType(MainMenu._resourcesPath+"NZCER-spelling-lists.txt");
				
				labelQuiz.setText("Custom List selected");
				String[] levelList;
				if (_testType == Test.NewQuiz){
					lblSciencelistNewSpelling.setText("Custom List: New Spelling Quiz");
					if (l2 == null){
						l2 = new WordList("c");
						MainMenu.currentLists.add(1, l2);
					}

					levelList = new String[l2.getLevels()];
					// this line is need to initialise the levelWordRecords
					//Stats s = new Stats();
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
					//String[] levelList = new String[l1.getLevels()];
					// this line is need to initialise the levelWordRecords
					//Stats s = new Stats();

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
				}
			}
		});
		btnUploadCustomList.setBounds(334, 192, 210, 44);
		jp1.add(btnUploadCustomList);

		// Adding the second card
		this.add(jp2, "2");

		lblSciencelistNewSpelling = new JLabel("Default List: New Spelling Quiz");
		lblSciencelistNewSpelling.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
		lblSciencelistNewSpelling.setBounds(112, 12, 402, 39);
		jp2.add(lblSciencelistNewSpelling);

		status1 = new JLabel("Current Level: ");
		status1.setFont(new Font("Dialog", Font.BOLD, 12));
		status1.setBounds(15, 96, 182, 29);
		jp2.add(status1);

		lblLevelAccuracy = new JLabel("Level Accuracy: ");
		lblLevelAccuracy.setFont(new Font("Dialog", Font.BOLD, 12));
		lblLevelAccuracy.setBounds(15, 136, 182, 29);
		jp2.add(lblLevelAccuracy);

		lblLongestStreak = new JLabel("Current Streak:");
		lblLongestStreak.setFont(new Font("Dialog", Font.BOLD, 12));
		lblLongestStreak.setBounds(15, 176, 182, 29);
		jp2.add(lblLongestStreak);

		progressBar = new JProgressBar();
		progressBar.setValue(50);
		progressBar.setBounds(15, 289, 200, 41);
		jp2.add(progressBar);

		JButton btnNewButton = new JButton("x");
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
		btnNewButton.setBounds(15, 348, 47, 34);
		jp2.add(btnNewButton);

		textOutput = new JEditorPane();
		textOutput.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		textOutput.setBackground(new Color(255, 255, 255));
		textOutput.setBounds(210, 109, 364, 96);
		textOutput.setEditable(false);
		textOutput.setFocusable(false);
		jp2.add(textOutput);

		textPane = new JTextField();
		textPane.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		textPane.setBounds(210, 218, 262, 39);
		jp2.add(textPane);
		textPane.setColumns(10);

		submitWord = new ActionListener(){

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
				attempt = textPane.getText();
				textPane.setText("");
				textPane.removeActionListener(this);
				textPane.requestFocusInWindow();
				//textPane.setEnabled(false);
				_bgworker = new BackgroundProcess(attempt,_test, QuizFrame.this,currentList);
				_bgworker.execute();
				//_test.logic(textPane.getText());
			}

		};

		textPane.addActionListener(submitWord);

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
		//getRootPane().setLayout(null);
		//getRootPane().add(this);
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


		btnSettings.setBounds(498, 299, 76, 75);

		lblLevelProgress = new JLabel("Level progress:");
		lblLevelProgress.setBounds(15, 268, 106, 20);
		jp2.add(lblLevelProgress);
		
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
						//comboBox.setEnabled(false);

						switch(_testType){
						case NewQuiz: 
							_test = new NewQuiz(_level);
							break;
						case ReviewQuiz:
							_test = new ReviewQuiz(_level);
							break;
						}

						_test.startTest(currentList);
						if (!_test.hasWords()){
							textOutput.setText("");
							comboBox.setEnabled(true);
							//testResult = 0;
							replayBtn.setEnabled(false);
							textPane.setEnabled(false);
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
		
		btnStartLevel = new JButton("Begin Test");
		btnStartLevel.setFont(new Font("Dialog", Font.BOLD, 14));
		btnStartLevel.requestFocusInWindow();
		btnStartLevel.setBounds(211, 57, 166, 32);
		btnStartLevel.addActionListener(newTest);

		jp2.add(btnStartLevel);

		JButton btnNewButton_1 = new JButton("Complete");
		btnNewButton_1.setFont(new Font("Dialog", Font.BOLD, 14));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(QuizFrame.this, "new");
			}
		});
		btnNewButton_1.setBounds(31, 50, 130, 34);
		jp2.add(btnNewButton_1);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Dialog", Font.BOLD, 14));
		btnSubmit.setBounds(479, 217, 95, 39);
		btnSubmit.addActionListener(submitWord);
		jp2.add(btnSubmit);

		lblFeedback = new JLabel();
		lblFeedback.setFont(new Font("Dialog", Font.BOLD, 16));
		lblFeedback.setIcon(new ImageIcon());
		lblFeedback.setBounds(251, 269, 221, 105);
		jp2.add(lblFeedback);

		//*********************************************************************************************
		// Adding the 3rd card
		this.add(jp3, "3");
		jp3.setLayout(null);

		JLabel lblQuizComplete = new JLabel("Quiz Complete!");
		lblQuizComplete.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
		lblQuizComplete.setBounds(195, 11, 211, 39);
		jp3.add(lblQuizComplete);

		lblQuizSummary = new JLabel("Quiz Score: ");
		lblQuizSummary.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblQuizSummary.setBounds(30, 47, 102, 28);
		jp3.add(lblQuizSummary);

		btnPlayVideo = new JButton("Play Video Reward");
		btnPlayVideo.setFont(new Font("Dialog", Font.BOLD, 14));
		btnPlayVideo.setBounds(206, 275, 199, 39);
		btnPlayVideo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "Please Select Video Speed: "; 
				String[] videoSpeed = { "Normal", "Fast (x2)", "Slow (x0.5)" };
				String initialSelection = "Normal";
				String selection = (String) JOptionPane.showInputDialog(null, message,
						"Reward", JOptionPane.QUESTION_MESSAGE, null, videoSpeed, initialSelection);

				if (selection!=null) {
					Video vid = new Video(2);
					vid.start(selection);
				}

			}

		});
		btnPlayVideo.setToolTipText("You redeem reward with a score of 9 or more!");
		jp3.add(btnPlayVideo);

		btnReplayLevel = new JButton("Replay Level");
		btnReplayLevel.setFont(new Font("Dialog", Font.BOLD, 14));
		btnReplayLevel.setBounds(30, 275, 147, 39);
		btnReplayLevel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeLevel(false);
			}
			
		});
		
		// or simple: cardLayout.show(QuizFrame.this, "2");
		jp3.add(btnReplayLevel);

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

		jp3.add(btnNextLevel);
		
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

		jp3.add(btnBackToMain);

		JButton btnFinishQuiz = new JButton("Finish Quiz");
		btnFinishQuiz.setFont(new Font("Dialog", Font.BOLD, 14));
		btnFinishQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(QuizFrame.this, "1");
			}
		});
		btnFinishQuiz.setBounds(407, 335, 167, 39);
		jp3.add(btnFinishQuiz);
		this.add(jp3,"new");
		
		labelScore = new JLabel("~/10");
		labelScore.setFont(new Font("Dialog", Font.BOLD, 14));
		labelScore.setBounds(133, 47, 102, 28);
		jp3.add(labelScore);
		//jp3.add(table);
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
				jp3.remove(scrollPane);
			}

			statsTable = view.getTable();
			int score = view.getAttempts();
			labelScore.setText(score+"/10");
			List<Integer> listCorrect = view.getCorrectArrayIndex();
			view = new UpdateQuizView();
			table = new JTable();
			if (score< 9){
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
				}
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
			jp3.add(scrollPane);
			cardLayout.show(this, "new");
			//jp3.remove(scrollPane);

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
