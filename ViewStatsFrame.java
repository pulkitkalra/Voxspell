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

public class ViewStatsFrame extends JFrame {
	
	private JFrame frame;
	private static final long serialVersionUID = 1L;
	private JPanel cardPanel, jp1, jp2;
	private CardLayout cardLayout = new CardLayout();
	private JLabel lblNewSpellingQuiz;
	private Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	private JLabel lblChooseYourSpelling;
	private JLabel lblSciencelistNewSpelling;
	private JLabel lblSelectALevel;
	private JComboBox comboBox_1;
	private JButton btnStartLevel;
	private JButton btnClearAllStats;
	private ScrollPane scrollPane;
	private JTable table;
	private JButton button;
	private JButton btnBackToStatistics;

	public ViewStatsFrame() {
		frame = this;
		frame.setVisible(true);
		setBounds(100, 100, 600, 400);
		cardPanel = new JPanel();
		cardPanel.setBounds(0, 0, 584, 361);
		cardPanel.setLayout(cardLayout);
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp1.setLayout(null);
		jp2.setLayout(null);
		
		//Adding the first card.
		cardPanel.add(jp1, "1");

		lblNewSpellingQuiz = new JLabel("Statistics");
		lblNewSpellingQuiz.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
		lblNewSpellingQuiz.setBounds(250, 11, 106, 52);
		jp1.add(lblNewSpellingQuiz);


		Border title = BorderFactory.createTitledBorder(
				loweredetched, "title");
		((TitledBorder)title).setTitleJustification(TitledBorder.RIGHT);
		
		lblChooseYourSpelling = new JLabel("Choose a spelling list to get Started:");
		lblChooseYourSpelling.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblChooseYourSpelling.setBounds(94, 62, 387, 34);
		jp1.add(lblChooseYourSpelling);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select a list to begin", "ScienceQuiz", "DefaultList1"}));
		comboBox.setBounds(96, 107, 385, 39);
		jp1.add(comboBox);
		
		JButton btnUploadCustomList = new JButton("View Stats");
		btnUploadCustomList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "2");
			}
		});
		btnUploadCustomList.setBounds(214, 183, 166, 44);
		jp1.add(btnUploadCustomList);
		
		JButton btnBackToMenu = new JButton("Back to Main Menu");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainMenu main = new MainMenu();
				main.setLocationRelativeTo(null);
			}
		});
		btnBackToMenu.setBounds(10, 316, 148, 34);
		jp1.add(btnBackToMenu);
		
		btnClearAllStats = new JButton("Clear All Stats");
		btnClearAllStats.setBounds(214, 257, 166, 44);
		jp1.add(btnClearAllStats);
		
		// Adding the second card
		cardPanel.add(jp2, "2");
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Select a level", "Level 1", "Level 2", "Level 3", "Level 4"}));
		comboBox_1.setBounds(10, 78, 424, 29);
		jp2.add(comboBox_1);
		
		lblSciencelistNewSpelling = new JLabel("ScienceList: Statistics");
		lblSciencelistNewSpelling.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
		lblSciencelistNewSpelling.setBounds(181, 11, 234, 39);
		jp2.add(lblSciencelistNewSpelling);
		
		lblSelectALevel = new JLabel();
		lblSelectALevel.setText("Select a level to begin: ");
		lblSelectALevel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblSelectALevel.setBounds(10, 47, 182, 20);
		jp2.add(lblSelectALevel);
		getContentPane().setLayout(null);
		getContentPane().add(cardPanel);
		Icon icon = new ImageIcon("C:\\Users\\Pulkit\\Documents\\Workspace\\250_sorting\\voxspell_gui\\settingsIcon.png");
		Image img = ((ImageIcon) icon).getImage() ;  
		Image newimg = img.getScaledInstance( 38, 38,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		
		btnStartLevel = new JButton("View Stats");
		btnStartLevel.setBounds(444, 78, 130, 29);
		jp2.add(btnStartLevel);
		
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"WORD", "CORRECT", "INCORRECT"},
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
				"New column", "New column", "New column"
			}
		));
		table.setBounds(10, 118, 564, 192);
		
		scrollPane = new ScrollPane();
		scrollPane.add(table);
		scrollPane.setBounds(10, 113, 564, 192);
		jp2.add(scrollPane);
		
		button = new JButton("Back to Main Menu");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainMenu main = new MainMenu();
				main.setLocationRelativeTo(null);
			}
		});
		button.setBounds(10, 316, 148, 34);
		jp2.add(button);
		
		btnBackToStatistics = new JButton("Back to Statistics");
		btnBackToStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "1");
			}
		});
		btnBackToStatistics.setBounds(426, 316, 148, 34);
		jp2.add(btnBackToStatistics);
		
		
	}
	
}