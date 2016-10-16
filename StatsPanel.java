package voxspell;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

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
	private JComboBox<String> comboBox_1;
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
		/*frame = this;
		frame.setResizable(false);*/
		setUpGUI();
		if (MainMenu.currentLists != null){
			if (MainMenu.currentLists.get(0)!=null){
				l1 = MainMenu.currentLists.get(0);
			}
			if (MainMenu.currentLists.get(1)!=null){
				l2 = MainMenu.currentLists.get(1);
			}
		}

		/*frame.setVisible(true);
		frame.setLocationRelativeTo(null);	
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});*/
	}
	/**
	 * Setting up GUI: Using absolute layout - this method adds the relevant content to the 
	 * frame. E.g. buttons and labels.
	 */
	public void setUpGUI(){

		setBounds(100, 100, 600, 400);
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
		jp1.setLayout(null);
		jp2.setLayout(null);
		//Adding the first card.
		this.add(jp1, "1");

		lblNewSpellingQuiz = new JLabel("Statistics");
		lblNewSpellingQuiz.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
		lblNewSpellingQuiz.setBounds(222, 12, 142, 52);
		jp1.add(lblNewSpellingQuiz);


		Border title = BorderFactory.createTitledBorder(
				loweredetched, "title");
		((TitledBorder)title).setTitleJustification(TitledBorder.RIGHT);

		lblChooseYourSpelling = new JLabel("Choose a spelling list to get Started:");
		lblChooseYourSpelling.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblChooseYourSpelling.setBounds(94, 62, 387, 34);
		jp1.add(lblChooseYourSpelling);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Select a list to begin", "Default List", 
		"Custom List"}));
		comboBox.setBounds(96, 107, 385, 39);
		jp1.add(comboBox);

		// Setting the list in use.


		JButton btnUploadCustomList = new JButton("View Stats");
		btnUploadCustomList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(null, "Please select a list","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					setLists();
				}
				currentList.saveWordProgress();
				/*if (currentList == null){
					JOptionPane.showMessageDialog(null, "Please attempt this quiz to view it's Stats","Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}*/
				String[] levelList = new String[currentList.getLevels()];
				levelList[0] = "Select a level";
				for (int i =1; i<currentList.getLevels();i++){
					levelList[i] = "Level "+i;
				}
				comboBox_1.setModel(new DefaultComboBoxModel<String>(levelList));
				
				cardLayout.show(StatsPanel.this, "2");
			}
		});
		btnUploadCustomList.setBounds(214, 183, 166, 44);
		jp1.add(btnUploadCustomList);

		JButton btnBackToMenu = new JButton("Back to Main Menu");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.cardLayout.show(MainMenu.cardPanel, "1");
			}
		});
		btnBackToMenu.setBounds(10, 316, 172, 34);
		jp1.add(btnBackToMenu);

		btnClearAllStats = new JButton("Clear All Stats");
		btnClearAllStats.setBounds(214, 257, 166, 44);
		btnClearAllStats.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(null, "Please select a list to Clear Stats");
				} else {
					setLists();
					currentList.clearLevelStats();
					if (currentList.equals(l1)){
						l1 = null;
					} else if (currentList.equals(l2)){
						l2 = null;
					}
				}
			}
		});
		jp1.add(btnClearAllStats);

		// Adding the second card
		this.add(jp2, "2");

		comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(10, 78, 424, 29);
		jp2.add(comboBox_1);
		if (comboBox_1.getSelectedIndex()==0){
			scrollPane.setEnabled(false);
		}

		lblSciencelistNewSpelling = new JLabel("ScienceList: Statistics");
		lblSciencelistNewSpelling.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
		lblSciencelistNewSpelling.setBounds(181, 11, 234, 39);
		jp2.add(lblSciencelistNewSpelling);

		lblSelectALevel = new JLabel();
		lblSelectALevel.setText("Select a level to begin: ");
		lblSelectALevel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblSelectALevel.setBounds(10, 47, 182, 20);
		jp2.add(lblSelectALevel);



		btnStartLevel = new JButton("View Stats Now");
		btnStartLevel.setBounds(444, 78, 130, 29);
		btnStartLevel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				table = new LevelStats(comboBox_1.getSelectedIndex(),currentList);
				table.setFillsViewportHeight(true); 
				scrollPane.getViewport().add(table);

			}

		});
		jp2.add(btnStartLevel);


		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 118, 564, 192);
		jp2.add(scrollPane);

		button = new JButton("Back to Main Menu");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.cardLayout.show(MainMenu.cardPanel, "1");
			}
		});
		button.setBounds(10, 316, 170, 34);
		jp2.add(button);

		btnBackToStatistics = new JButton("Back to Statistics");
		btnBackToStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(StatsPanel.this, "1");
			}
		});
		btnBackToStatistics.setBounds(426, 316, 148, 34);
		jp2.add(btnBackToStatistics);
	}
	
	public void setLists(){
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
			FilePathInstance.setType(MainMenu._resourcesPath+"NZCER-spelling-lists.txt");
			if (l2 == null){
				l2 = new WordList("c");
				MainMenu.currentLists.add(1, l2);
			}

			currentList  = l2;
		}
	}

}