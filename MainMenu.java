package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.UIManager;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainMenu(){
		frame = this;
		frame.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("EditorPane.inactiveForeground"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblVoxspell = new JLabel("VOXSPELL");
		lblVoxspell.setForeground(new Color(51, 102, 51));
		lblVoxspell.setFont(new Font("Microsoft YaHei", Font.BOLD, 38));
		lblVoxspell.setBounds(190, 12, 208, 69);
		contentPane.add(lblVoxspell);
		
		JButton btnNewSpellingQuiz = new JButton("Start a New Quiz");
		btnNewSpellingQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				QuizFrame qFrame = new QuizFrame();
				qFrame.setLocationRelativeTo(null);
			}
		});
		btnNewSpellingQuiz.setBounds(173, 91, 225, 41);
		btnNewSpellingQuiz.setFocusable(false);
		contentPane.add(btnNewSpellingQuiz);
		
		JButton btnStartAReview = new JButton("Start a Review Quiz");
		btnStartAReview.setBounds(173, 156, 225, 41);
		btnStartAReview.setFocusable(false);
		contentPane.add(btnStartAReview);
		
		JButton btnViewMyStatistics = new JButton("View my Statistics");
		btnViewMyStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				ViewStatsFrame sFrame = new ViewStatsFrame();
				sFrame.setLocationRelativeTo(null);
			}
		});
		btnViewMyStatistics.setBounds(173, 280, 225, 41);
		btnViewMyStatistics.setFocusable(false);
		contentPane.add(btnViewMyStatistics);
		
		JButton btnStartBee = new JButton("Start a Spelling Bee");
		btnStartBee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStartBee.setBounds(173, 219, 225, 41);
		btnStartBee.setFocusable(false);
		contentPane.add(btnStartBee);
		
		JButton btnSettings = new JButton("");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				SettingsFrame settings = new SettingsFrame();
				settings.setLocationRelativeTo(null);
			}
		});
		
		Icon icon = new ImageIcon("C:\\Users\\Pulkit\\Documents\\Workspace\\250_sorting\\voxspell_gui\\settingsIcon.png");
		Image img = ((ImageIcon) icon).getImage() ;  
		Image newimg = img.getScaledInstance( 45, 45,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		btnSettings.setIcon(icon);
		btnSettings.setFocusable(false);
		btnSettings.setToolTipText("Settings");
		

		btnSettings.setBounds(496, 274, 76, 75);
		contentPane.add(btnSettings);
		
		
	}
}
