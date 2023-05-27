import java.util.ArrayList;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;

public class TargetGUI implements ActionListener {
	
	private JFrame frame;
	private JPanel contentPane;
	private JTextField textField;
	private BackEnd backend;
	private JLabel correct;
	private JLabel incorrect;
	private JButton button;
	private JTextArea textArea;
	private JScrollPane scroll;
	private JMenu menu;
	private JMenuItem newGame;
	private JMenuItem rules;
	private JMenuItem quit;
	private String rulesMessage;

	/**
	 * Create the frame.
	 */
	public TargetGUI(BackEnd backend) {
		
		frame = new JFrame("Target");
		frame.setPreferredSize(new Dimension(600, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		this.backend = backend;
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		menu = new JMenu("Menu");
		menuBar.add(menu);
		
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(this);
		menu.add(newGame);
		
		rules = new JMenuItem("Rules");
		rules.addActionListener(this);
		menu.add(rules);
		
		quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		menu.add(quit);
		
		frame.getContentPane().add(contentPane);
		frame.pack();
		
		startNewGame();
		
		frame.setVisible(true);
		
		rulesMessage = "Rules:\n";
		rulesMessage += "Make as many words as you can with the letters in the grid.\n";
		rulesMessage += "Words must be at least four letters long.\n";
		rulesMessage += "Words must contain the letter in the blue box.\n";
		rulesMessage += "Plurals and proper nouns are allowed.";
	}
	
	public void startNewGame() {
		
		this.backend.createTargetData();
		
		char[] shuffledLetters = this.backend.getShuffledLetters();
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		
		for (int y = 10; y <= 120; y += 55) {
			for (int x = 10; x <= 120; x += 55) {
				JLabel label = new JLabel("");
				label.setBackground(Color.LIGHT_GRAY);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setOpaque(true);
				label.setBorder(null);
				label.setBounds(x, y, 50, 50);
				labels.add(label);
			}
		}
		
		for (int i = 0; i < labels.size(); i += 1) {
			labels.get(i).setText(String.valueOf(shuffledLetters[i]));
			contentPane.add(labels.get(i));
		}
		
		labels.get(4).setForeground(Color.WHITE);
		labels.get(4).setBackground(Color.BLUE);
		
		textField = new JTextField();
		textField.setBounds(10, 199, 100, 20);
		textField.addActionListener(this);
		contentPane.add(textField);
		textField.setColumns(10);
		
		button = new JButton("<");
		
		button.setBounds(120, 199, 50, 20);
		button.addActionListener(this);
		contentPane.add(button);
		
		correct = new JLabel(backend.correctScoreToString());
		correct.setBounds(226, 26, 100, 14);
		contentPane.add(correct);
		
		incorrect = new JLabel(backend.incorrectScoreToString());
		incorrect.setBounds(226, 56, 100, 14);
		contentPane.add(incorrect);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		scroll = new JScrollPane(textArea);
		scroll.setBounds(226, 81, 100, 89);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scroll);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button || e.getSource() == textField) {
			String word = this.textField.getText().toUpperCase();
			processUserEntry(word);
			textField.setText("");
		}
		
		else if (e.getSource() == newGame) {
			contentPane.removeAll();
			String message = "The remaining words were:\n\n" + backend.possibleWordsToString();
			JOptionPane.showMessageDialog(frame, message);
			backend.resetData();
			startNewGame();
			frame.repaint();
		}
		
		else if (e.getSource() == rules) {
			JOptionPane.showMessageDialog(frame, rulesMessage);
		}
		
		else if (e.getSource() == quit) {
			String message = "The remaining words were:\n\n" + backend.possibleWordsToString();
			JOptionPane.showMessageDialog(frame, message);
			System.exit(0);
		}
	}
	
	public void processUserEntry(String word) {
		backend.checkUserEnteredWord(word);
		correct.setText(backend.correctScoreToString());
		incorrect.setText(backend.incorrectScoreToString());
		textArea.append(word + "\n");
	}
}
