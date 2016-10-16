package voxspell;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SettingsFrame extends JFrame {

	private JPanel contentPane;
	private JFrame frame;

	/**
	 * Create the frame.
	 */
	public SettingsFrame() {

		frame = this;
		frame.setResizable(false);
		setUpGUI();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});

	}

	public void setUpGUI(){

		setBounds(100, 100, 600, 400);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainMenu.bkg, 0, 0, null);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
		lblSettings.setBounds(244, 11, 106, 52);
		contentPane.add(lblSettings);

		JLabel lblCurrentVoice = new JLabel("Change Voice");
		lblCurrentVoice.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblCurrentVoice.setBounds(56, 104, 179, 34);
		contentPane.add(lblCurrentVoice);


		JLabel lblVoice = new JLabel("Current Voice");
		lblVoice.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblVoice.setBounds(56, 61, 179, 34);
		contentPane.add(lblVoice);

		JLabel status1 = new JLabel("Default");
		status1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		status1.setBounds(264, 61, 179, 34);
		contentPane.add(status1);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Default Voice", "New Zealand Voice"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(264, 106, 268, 34);
		comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedItem().equals("Default Voice")){
					VoiceInstance.setType(VoiceType.DEF_VOICE.getVoice());
					status1.setText("Default");
				} else if (comboBox.getSelectedItem().equals("New Zealand Voice")){
					VoiceInstance.setType(VoiceType.NZ_VOICE.getVoice());
					status1.setText("New Zealand Voice");
				}

			}

		});
		contentPane.add(comboBox);




		JLabel lblCurrentVoiceSpeed = new JLabel("Current Voice Speed");
		lblCurrentVoiceSpeed.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblCurrentVoiceSpeed.setBounds(56, 149, 179, 34);
		contentPane.add(lblCurrentVoiceSpeed);

		JLabel lblSlowFastNormal = new JLabel("Slow/ Fast/ Normal");
		lblSlowFastNormal.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblSlowFastNormal.setBounds(264, 149, 179, 34);
		contentPane.add(lblSlowFastNormal);

		JLabel label_2 = new JLabel("Change Voice");
		label_2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		label_2.setBounds(56, 192, 179, 34);
		contentPane.add(label_2);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Very Slow", "Slow", "Normal", "Fast"}));
		comboBox_1.setSelectedIndex(2);
		comboBox_1.setBounds(264, 194, 268, 34);
		contentPane.add(comboBox_1);

		JLabel lblViewMySpelling = new JLabel("View My Spelling Lists");
		lblViewMySpelling.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
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

		JButton button_1 = new JButton("Save Changes");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		button_1.setBounds(10, 316, 170, 34);
		contentPane.add(button_1);
		frame.add(contentPane);
	}
}
