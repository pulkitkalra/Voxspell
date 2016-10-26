package voxspell;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import Voice.VoiceInstance;
import Voice.VoiceType;
import stats.StatsPanel;

/**
 * Welcome to VOXSPELL!
 * This is the main class for the application and shows the main Home screen with access
 * to all other parts of the application available here. This is the only entry point for
 * the application.
 * @author pulkit
 *
 */
@SuppressWarnings("serial")
public class MainMenu extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	public static String _path;
	public static String _resourcesPath;
	public static File wordFile;
	public static String currentFile;
	public static List<WordList> currentLists;
	public static JPanel cardPanel;
	//private JPanel jp1, jp2, jp3;
	public static CardLayout cardLayout = new CardLayout();
	private QuizFrame qframe;
	private ReviewFrame rframe;
	private StatsPanel sframe;
	private VoxStore store;
	
	public static BufferedImage bkg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();

					//frame.setLocationRelativeTo(null);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * method initializes the files from the files that store settings.
	 * @throws FileNotFoundException
	 */
	public void initVoice() throws FileNotFoundException{
		Scanner voiceType = new Scanner(new File(_resourcesPath+"voiceType.txt"));
		if (voiceType.nextLine().trim().equals("NZ")){
			VoiceInstance.setTypeInstance(VoiceType.NZ_VOICE.getVoice());
		} else {
			VoiceInstance.setTypeInstance(VoiceType.DEF_VOICE.getVoice());
		}
		voiceType.close();
		Scanner voiceSpeed = new Scanner(new File(_resourcesPath+"voiceSpeed.txt"));
		VoiceInstance.setSpeedInstance(voiceSpeed.nextLine());
		voiceSpeed.close();
		// setting default Video
		VoxStore.currentVideo = "big_buck_bunny_1_minute.avi";
		// setting current Song
		VoxStore.currentSong = "musicDef.wav";
	}
	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainMenu(){
		setTitle("VOXSPELL");
		obtainPath();
		// SETTING THE VOICE BASED ON FILES (Has to be done here)
		try {
			initVoice();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			bkg = ImageIO.read(new File(MainMenu._resourcesPath+"background.JPG"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Background image could not be loaded.");
			e1.printStackTrace();
		}
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		qframe = new QuizFrame("n");
		rframe = new ReviewFrame();
		sframe = new StatsPanel();
		store = new VoxStore();
		frame = this;
		frame.setResizable(false);
		setUpGUI();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		currentLists = new ArrayList<WordList>();
		currentLists.add(null);
		currentLists.add(null);
	}

	/**
	 * method gets path relative to the jar file
	 */
	public void obtainPath() {
		// Reference: http://stackoverflow.com/questions/2837263/how-do-i-get-the-directory-that-the-currently-executing-jar-file-is-in
		// Credit: Vahan Margaya: Stack OverFlow user.
		String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		absolutePath = absolutePath.replaceAll("%20", " ");
		_path = absolutePath + "/";
		_resourcesPath = _path + "resources"+"/";
	}
	/**
	 * method used to configure the gui - panel with buttons and their listeners.
	 */
	public void setUpGUI(){
		setBounds(100, 100, 600, 400);
		// cardPanel is the main panel in this class which embeds other cards.
		cardPanel = new JPanel();
		cardPanel.setBounds(0, 0, 600, 400);
		cardPanel.setLayout(cardLayout);
		contentPane = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(bkg, 0, 0, null);
			}
		};
		contentPane.setLayout(null);
		getContentPane().setLayout(null);
		getContentPane().add(cardPanel);
		//Adding the first card
		cardPanel.add(contentPane, "1");
		// adding rest of the cards
		cardPanel.add(qframe, "QuizFrame");
		cardPanel.add(rframe, "Review");
		cardPanel.add(sframe, "Stats");
		cardPanel.add(store, "Store");
		contentPane.setBackground(UIManager.getColor("EditorPane.inactiveForeground"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// TITLE
		JLabel lblVoxspell = new JLabel("");
		lblVoxspell.setIcon(new ImageIcon(MainMenu._resourcesPath+"logo.png"));
		lblVoxspell.setForeground(new Color(51, 102, 51));
		lblVoxspell.setBounds(166, 12, 272, 69);
		contentPane.add(lblVoxspell);
		// new wuiz button
		JButton btnNewSpellingQuiz = new JButton(" Start a New Quiz     ");
		btnNewSpellingQuiz.setBackground(UIManager.getColor(Color.green));
		btnNewSpellingQuiz.setFont(new Font("Dialog", Font.BOLD, 14));
		btnNewSpellingQuiz.setIcon(new ImageIcon(MainMenu._resourcesPath+"newQuiz.png"));
		btnNewSpellingQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "QuizFrame");
			}
		});
		btnNewSpellingQuiz.setBounds(175, 109, 248, 47);
		btnNewSpellingQuiz.setFocusable(false);
		contentPane.add(btnNewSpellingQuiz);
		// review quiz button
		JButton btnStartAReview = new JButton("Start a Review Quiz");
		btnStartAReview.setIcon(new ImageIcon(MainMenu._resourcesPath+"review.png"));
		btnStartAReview.setFont(new Font("Dialog", Font.BOLD, 14));
		btnStartAReview.setBounds(175, 180, 248, 47);
		btnStartAReview.setFocusable(false);
		btnStartAReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Review");// showing the review card.
			}
		});
		contentPane.add(btnStartAReview);
		// stats button
		JButton btnViewMyStatistics = new JButton(" View my Statistics");
		btnViewMyStatistics.setIcon(new ImageIcon(MainMenu._resourcesPath+"stats.png"));
		btnViewMyStatistics.setFont(new Font("Dialog", Font.BOLD, 14));
		btnViewMyStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Stats");
			}
		});
		btnViewMyStatistics.setBounds(175, 315, 248, 47);
		btnViewMyStatistics.setFocusable(false);
		contentPane.add(btnViewMyStatistics);
		// vox store button
		JButton btnStartBee = new JButton("  Visit the Vox Store");
		btnStartBee.setIcon(new ImageIcon(MainMenu._resourcesPath+"store.png"));
		btnStartBee.setFont(new Font("Dialog", Font.BOLD, 14));
		btnStartBee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Store");
			}
		});
		btnStartBee.setBounds(175, 247, 248, 47);
		btnStartBee.setFocusable(false);
		contentPane.add(btnStartBee);
		// settings button
		JButton btnSettings = new JButton();
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsFrame sframe = new SettingsFrame();
			}
		});
		// icon for the settings button
		Icon icon = new ImageIcon(MainMenu._resourcesPath+"settingsIcon.png");
		Image img = ((ImageIcon) icon).getImage() ;  
		Image newimg = img.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH) ;  
		icon = new ImageIcon( newimg );
		btnSettings.setIcon(icon);
		btnSettings.setFocusable(false);
		btnSettings.setToolTipText("Settings");
		btnSettings.setBounds(493, 298, 76, 75);
		contentPane.add(btnSettings);
	}
}
