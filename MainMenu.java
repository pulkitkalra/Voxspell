package voxspell;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	protected static String _path;
	protected static String _resourcesPath;
	protected static File wordFile;
	protected static String currentFile;
	protected static List<WordList> currentLists;
	public static JPanel cardPanel;
	//private JPanel jp1, jp2, jp3;
	public static CardLayout cardLayout = new CardLayout();
	private QuizFrame qframe;
	private ReviewFrame rframe;
	private StatsPanel sframe;
	
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
		// SETTING DEFAULT VOICE (Has to be done here)
		VoiceInstance.setType(VoiceType.DEF_VOICE.getVoice());
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainMenu(){
		obtainPath();
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
		/*currentFile = "d";
		FilePathInstance.setType(MainMenu._path+"myFile.txt");
		List defaultList = new List();*/
		//FilePathInstance.setType(MainMenu._path+"NZCER-spelling-lists.txt");

		// Set the frames content pane to use a JLabel
		// whose icon property has been set to use the image
		// we just loaded                        
	}

	/*protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	        g.drawImage(bgImage, 0, 0, null);
	}*/

	public void obtainPath() {
		// Reference: http://stackoverflow.com/questions/2837263/how-do-i-get-the-directory-that-the-currently-executing-jar-file-is-in
		// Credit: Vahan Margaya: Stack OverFlow user.
		String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		absolutePath = absolutePath.replaceAll("%20", " ");

		_path = absolutePath + "/";
		_resourcesPath = _path + "resources"+"/";
	}
	

	public void setUpGUI(){

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 600, 400);
		cardPanel = new JPanel();
		cardPanel.setBounds(0, 0, 600, 400);
		cardPanel.setLayout(cardLayout);
		//QuizFrame qframe = new QuizFrame("n");

		//SettingsFrame settings = new SettingsFrame();
		contentPane = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(bkg, 0, 0, null);
			}
		};
		contentPane.setLayout(null);
		//jp1.setLayout(null);
		getContentPane().setLayout(null);
		getContentPane().add(cardPanel);
		//Adding the first card
		cardPanel.add(contentPane, "1");

		// adding rest of the cards
		cardPanel.add(qframe, "QuizFrame");
		cardPanel.add(rframe, "Review");
		cardPanel.add(sframe, "Stats");
		//cardPanel.add(fra, "Settings");

		contentPane.setBackground(UIManager.getColor("EditorPane.inactiveForeground"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		//contentPane.setLayout(null);

		JLabel lblVoxspell = new JLabel("");
		lblVoxspell.setIcon(new ImageIcon(MainMenu._resourcesPath+"logo.png"));
		lblVoxspell.setForeground(new Color(51, 102, 51));
		//lblVoxspell.setFont(new Font("Microsoft YaHei", Font.BOLD, 38));
		lblVoxspell.setBounds(166, 12, 272, 69);
		contentPane.add(lblVoxspell);

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

		JButton btnStartBee = new JButton(" Start a Spelling Bee");
		btnStartBee.setIcon(new ImageIcon(MainMenu._resourcesPath+"spBee.png"));
		btnStartBee.setFont(new Font("Dialog", Font.BOLD, 14));
		btnStartBee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStartBee.setBounds(175, 247, 248, 47);
		btnStartBee.setFocusable(false);
		contentPane.add(btnStartBee);

		JButton btnSettings = new JButton("");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsFrame sframe = new SettingsFrame();
			}
		});

		Icon icon = new ImageIcon(MainMenu._resourcesPath+"settingsIcon.png");
		Image img = ((ImageIcon) icon).getImage() ;  
		Image newimg = img.getScaledInstance( 45, 45,java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		btnSettings.setIcon(icon);
		btnSettings.setFocusable(false);
		btnSettings.setToolTipText("Settings");


		btnSettings.setBounds(493, 298, 76, 75);
		contentPane.add(btnSettings);

		//cardLayout.show(cardPanel, "1");
	}
}
