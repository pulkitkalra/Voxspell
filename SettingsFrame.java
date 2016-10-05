package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingsFrame extends JFrame {

	private JPanel contentPane;
	private JFrame frame;

	/**
	 * Create the frame.
	 */
	public SettingsFrame() {
		frame = this;
		frame.setVisible(true);
		setBounds(100, 100, 600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
		lblSettings.setBounds(244, 11, 106, 52);
		contentPane.add(lblSettings);
		
		JLabel lblNewLabel = new JLabel("Change Voice");
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblNewLabel.setBounds(56, 104, 179, 34);
		contentPane.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Default Voice", "New Zealand Voice"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(264, 106, 268, 34);
		contentPane.add(comboBox);
		
		JLabel lblCurrentVoice = new JLabel("Current Voice");
		lblCurrentVoice.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblCurrentVoice.setBounds(56, 61, 179, 34);
		contentPane.add(lblCurrentVoice);
		
		JLabel lblDefaultNewZealand = new JLabel("Default/ New Zealand");
		lblDefaultNewZealand.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblDefaultNewZealand.setBounds(264, 61, 179, 34);
		contentPane.add(lblDefaultNewZealand);
		
		JLabel lblCurrentVoiceSpeed = new JLabel("Current Voice Speed");
		lblCurrentVoiceSpeed.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblCurrentVoiceSpeed.setBounds(56, 149, 179, 34);
		contentPane.add(lblCurrentVoiceSpeed);
		
		JLabel lblSlowFastNormal = new JLabel("Slow/ Fast/ Normal");
		lblSlowFastNormal.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblSlowFastNormal.setBounds(264, 149, 179, 34);
		contentPane.add(lblSlowFastNormal);
		
		JLabel label_2 = new JLabel("Change Voice");
		label_2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		label_2.setBounds(56, 192, 179, 34);
		contentPane.add(label_2);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Very Slow", "Slow", "Normal", "Fast"}));
		comboBox_1.setSelectedIndex(2);
		comboBox_1.setBounds(264, 194, 268, 34);
		contentPane.add(comboBox_1);
		
		JLabel lblViewMySpelling = new JLabel("View My Spelling Lists");
		lblViewMySpelling.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblViewMySpelling.setBounds(56, 240, 179, 34);
		contentPane.add(lblViewMySpelling);
		
		JButton btnViewLists = new JButton("View Lists");
		btnViewLists.setBounds(261, 248, 271, 26);
		contentPane.add(btnViewLists);
		
		JButton btnAboutVoxspell = new JButton("Help and Instructions");
		btnAboutVoxspell.setBounds(319, 287, 213, 26);
		contentPane.add(btnAboutVoxspell);
		
		JButton button = new JButton("About Voxspell");
		button.setBounds(319, 324, 213, 26);
		contentPane.add(button);
		
		JButton button_1 = new JButton("Back to Main Menu");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainMenu menu = new MainMenu();
				menu.setLocationRelativeTo(null);
			}
		});
		button_1.setBounds(10, 316, 148, 34);
		contentPane.add(button_1);
	}
}
