package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.LayoutManager;
import java.awt.CardLayout;

public class CardTrial extends JFrame {

	private JPanel contentPane;
	private JPanel cards;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardTrial frame = new CardTrial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CardTrial() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 80);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnB = new JButton("B1");
		btnB.setBounds(20, 27, 89, 23);
		panel.add(btnB);

		JPanel card1 = new JPanel();
		card1.add(new JButton("Button 1"));
		card1.add(new JButton("Button 2"));
		card1.add(new JButton("Button 3"));

		JPanel card2 = new JPanel();
		card2.add(new JTextField("TextField", 20));

		//Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.setBounds(0, 78, 434, 183);
		cards.setLayout(new CardLayout(0, 0));
		contentPane.add(cards);
		JButton button = new JButton("B2");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.add(card1);
			}
		});
		button.setBounds(162, 27, 89, 23);
		panel.add(button);

		JButton button_1 = new JButton("B3");
		button_1.setBounds(305, 27, 89, 23);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.add(card2);
			}
		});
		panel.add(button_1);


	}

}
