package stats;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import voxspell.FileChoose;
import voxspell.FilePathInstance;
import voxspell.LevelStats;
import voxspell.MainMenu;
import voxspell.WordList;
/**
 * Stats Panel class creates the statistics panel used to show the stats for each list.
 * @author pulkit
 *
 */
public class StatsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jp1;
	private JPanel jp2;
	private CardLayout cardLayout = new CardLayout();
	private JLabel lblNewSpellingQuiz;
	private Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	private JLabel lblChooseYourSpelling;
	private JLabel lblSciencelistNewSpelling;
	private JLabel lblSelectALevel;
	private JComboBox<String> levelComboBox;
	private JButton btnStartLevel;
	private JButton btnClearAllStats;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton button;
	private JButton btnBackToStatistics;
	private JComboBox<String> comboBox;
	private WordList currentList;
	protected WordList l1;
	protected WordList l2;;
	/**
	 * Constructor creates the GUI. Responsible for frame handling.
	 */
	public StatsPanel() {
		setUpGUI();
		if (MainMenu.currentLists != null){
			if (MainMenu.currentLists.get(0)!=null){
				l1 = MainMenu.currentLists.get(0);
			}
			if (MainMenu.currentLists.get(1)!=null){
				l2 = MainMenu.currentLists.get(1);
			}
		}
	}
	/**
	 * Setting up GUI: Using absolute layout - this method adds the relevant content to the 
	 * frame. E.g. buttons and labels.
	 */
	public void setUpGUI(){

		setBounds(100, 100, 600, 400);
		this.setBounds(0, 0, 600, 400);
		this.setLayout(cardLayout);
		jp1 = new JPanel() { // setting background for panel
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
		jp1.setLayout(null);
		jp2.setLayout(null);
		//Adding the first card.
		this.add(jp1, "1");
		// stats label
		lblNewSpellingQuiz = new JLabel("Statistics");
		lblNewSpellingQuiz.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
		lblNewSpellingQuiz.setBounds(244, 12, 142, 52);
		jp1.add(lblNewSpellingQuiz);
		// spelling list to start label
		lblChooseYourSpelling = new JLabel("Choose a spelling list to get Started:");
		lblChooseYourSpelling.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblChooseYourSpelling.setBounds(103, 63, 387, 34);
		jp1.add(lblChooseYourSpelling);
		// list combo box
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Select a list to begin", "Default List", 
		"Custom List"}));
		comboBox.setBounds(105, 108, 385, 39);
		jp1.add(comboBox);

		// Setting the list in use.
		JButton btnUploadCustomList = new JButton("View Stats");
		btnUploadCustomList.setFont(new Font("Dialog", Font.BOLD, 14));
		btnUploadCustomList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean temp = false;
				if (comboBox.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(null, "Please select a list","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					if (setLists()){
						temp = true;
						if (currentList!=null){
							currentList.saveWordProgress();
						}
					}
					
					
				}
				if (temp){
					String[] levelList = new String[currentList.getLevels()];
					levelList[0] = "Select a level";
					for (int i =1; i<currentList.getLevels();i++){
						levelList[i] = "Level "+i;
					}
					levelComboBox.setModel(new DefaultComboBoxModel<String>(levelList));

					cardLayout.show(StatsPanel.this, "2");
				}
			}
		});
		btnUploadCustomList.setBounds(206, 183, 189, 44);
		jp1.add(btnUploadCustomList);
		// Home button
		JButton btnBackToMain = new JButton(" Home");
		btnBackToMain.setIcon(new ImageIcon(MainMenu._resourcesPath+"home.png"));
		btnBackToMain.setFont(new Font("Dialog", Font.BOLD, 14));
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MainMenu.cardLayout.show(MainMenu.cardPanel, "1");
			}
		});
		btnBackToMain.setBounds(30, 335, 115, 45);
		jp1.add(btnBackToMain);
		
		// clear all stats button
		btnClearAllStats = new JButton("Clear All Stats");
		btnClearAllStats.setFont(new Font("Dialog", Font.BOLD, 14));
		btnClearAllStats.setBounds(206, 257, 189, 44);
		btnClearAllStats.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(null, "Please select a list to Clear Stats");
				} else {
					int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear stats?"
							+ "\nThis action will erase all stats for all words in this list", "Confirm",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						if (setLists()){
							if (currentList!=null){
								currentList.clearLevelStats();
								if (currentList.equals(l1)){
									l1 = null;
								} else if (currentList.equals(l2)){
									l2 = null;
								}
							}
						} else { return; }
					} else {
						return;
					}
				}
			}
		});
		jp1.add(btnClearAllStats);

		// Adding the second card
		this.add(jp2, "2");
		// combo box for levels
		levelComboBox = new JComboBox<String>();
		levelComboBox.setFont(new Font("Dialog", Font.BOLD, 14));
		levelComboBox.setBounds(16, 78, 422, 39);
		jp2.add(levelComboBox);
		if (levelComboBox.getSelectedIndex()==0){
			scrollPane.setEnabled(false);
		}
		// statistics title
		lblSciencelistNewSpelling = new JLabel("Statistics");
		lblSciencelistNewSpelling.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
		lblSciencelistNewSpelling.setBounds(242, 12, 130, 39);
		jp2.add(lblSciencelistNewSpelling);
		// instruction label
		lblSelectALevel = new JLabel();
		lblSelectALevel.setText("Select a level to begin: ");
		lblSelectALevel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblSelectALevel.setBounds(16, 47, 282, 29);
		jp2.add(lblSelectALevel);

		// view stats button
		btnStartLevel = new JButton("View Stats ");
		btnStartLevel.setFont(new Font("Dialog", Font.BOLD, 14));
		btnStartLevel.setBounds(450, 78, 130, 39);
		btnStartLevel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (levelComboBox.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(null, "Please slect a level!", "Error!", 
							JOptionPane.ERROR_MESSAGE);
				}
				table = new LevelStats(levelComboBox.getSelectedIndex(),currentList);
				table.setFillsViewportHeight(true); 
				scrollPane.setViewportView(table);
			}
		});
		jp2.add(btnStartLevel);
		// scroll pane which will containa a table
		scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 131, 564, 192);
		jp2.add(scrollPane);
		// Home button
		button = new JButton(" Home");
		button.setIcon(new ImageIcon(MainMenu._resourcesPath+"home.png"));
		button.setFont(new Font("Dialog", Font.BOLD, 14));
		button.setBounds(16, 335, 115, 45);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(StatsPanel.this, "1");
				MainMenu.cardLayout.show(MainMenu.cardPanel, "1");
			}
		});
		jp2.add(button);
		// back to stats button
		btnBackToStatistics = new JButton("Back to Statistics");
		btnBackToStatistics.setFont(new Font("Dialog", Font.BOLD, 14));
		btnBackToStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(StatsPanel.this, "1");
			}
		});
		btnBackToStatistics.setBounds(397, 335, 182, 45);
		jp2.add(btnBackToStatistics);
	}
	/**
	 * method used to set Lists based on combo box selection: setting either the current or
	 * custom list and checking if user has at least attempted them.
	 * @return
	 */
	public boolean setLists(){
		if (comboBox.getSelectedIndex()==1){
			MainMenu.currentFile = "d";
			FilePathInstance.setType(MainMenu._resourcesPath+"myFile.txt");
			if (l1 == null){
				l1 = new WordList("d");
				MainMenu.currentLists.add(0, l1);
			}
			currentList  = l1;
		} else {
			MainMenu.currentFile = "c";
			FileChoose choose = new FileChoose();
			if (!choose.exists){
				JOptionPane.showMessageDialog(null, "You have not uploaded a Custom List!"
						+ "\nPlease return to New Quiz to upload and attempt list.");
				return false;
			}
			String name = choose.getCustomName();
			if (name!=null){
				FilePathInstance.setType(MainMenu._resourcesPath+name);
			} else {
				return false;
			}
			if (l2 == null){
				l2 = new WordList("c");
				MainMenu.currentLists.add(1, l2);
			}
			currentList  = l2;
		}
		return true;
	}

}