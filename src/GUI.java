import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GUI extends JFrame{
	private static final long serialVersionUID = 1L;
	Font style1 = new Font("Arial", Font.ITALIC, 14);
	Font style2 = new Font("Arial", Font.ITALIC, 12);
    JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		createMenuBar();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void createMenuBar() {
		JMenu menuFile = new JMenu("Plik");
		menuFile.setFont(style1);
		
		JMenuItem loadItem = new JMenuItem("Wczytaj plik html z danymi EGiB");
		loadItem.setFont(style2);
		
		menuFile.add(loadItem);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.setFont(style1);
		frame.setJMenuBar(menuBar);
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 345, 393);
		frame.setMinimumSize(new Dimension(300,350));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0};
		gridBagLayout.columnWidths = new int[]{201, 115};
		gridBagLayout.rowHeights = new int[]{41, 200, 100};
		frame.getContentPane().setLayout(gridBagLayout);
			
		JLabel lblInfoFieldNum = new JLabel(
				"<html><div style='text-align:center;'>Podwójne klikniêcie w nr dzia³ki spowoduje "
				+ "skopiowanie danych do schowka</div></html>");
		lblInfoFieldNum.setFont(new Font("Arial Narrow", Font.ITALIC, 10));
		GridBagConstraints gbc_lblInfoFieldNum = new GridBagConstraints();
		gbc_lblInfoFieldNum.anchor = GridBagConstraints.NORTH;
		gbc_lblInfoFieldNum.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInfoFieldNum.insets = new Insets(0, 0, 5, 0);
		gbc_lblInfoFieldNum.gridx = 1;
		gbc_lblInfoFieldNum.gridy = 0;
		lblInfoFieldNum.setMaximumSize(new Dimension(54,115));
		frame.getContentPane().add(lblInfoFieldNum, gbc_lblInfoFieldNum);
		
		JPanel panel_FieldData = new JPanel();
		GridBagConstraints gbc_FieldData = new GridBagConstraints();
		gbc_FieldData.insets = new Insets(0, 0, 5, 5);
		gbc_FieldData.gridx = 0;
		gbc_FieldData.gridy = 1;
		frame.getContentPane().add(panel_FieldData, gbc_FieldData);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		JPanel panel_Clipboard = new JPanel();
		GridBagConstraints gbc_Clipboard_1 = new GridBagConstraints();
		gbc_Clipboard_1.gridwidth = 2;
		gbc_Clipboard_1.insets = new Insets(0, 0, 0, 5);
		gbc_Clipboard_1.fill = GridBagConstraints.BOTH;
		gbc_Clipboard_1.gridx = 0;
		gbc_Clipboard_1.gridy = 2;
		frame.getContentPane().add(panel_Clipboard, gbc_Clipboard_1);
		
	}
}
