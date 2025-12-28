import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;

public class GUI {

	private JFrame frame;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(301, 55, 123, 195);
		frame.getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 55, 301, 195);
		frame.getContentPane().add(panel);
		
		JMenuItem mntmPlik = new JMenuItem("Plik");
		mntmPlik.setHorizontalAlignment(SwingConstants.LEFT);
		mntmPlik.setBounds(0, 0, 51, 22);
		mntmPlik.setHorizontalTextPosition(SwingConstants.LEFT);
		frame.getContentPane().add(mntmPlik);
		
		JMenuItem mntmUstawienia = new JMenuItem("Ustawienia");
		mntmUstawienia.setBounds(56, 0, 101, 22);
		frame.getContentPane().add(mntmUstawienia);
		
		JLabel lblInfoFieldNum = new JLabel("<html><div style='text-align:center;'>Podwójne klikniêcie w nr dzia³ki \r\nspowoduje skopiowanie danych \r\ndo schowka</div></html>");
		lblInfoFieldNum.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoFieldNum.setFont(new Font("Arial Narrow", Font.ITALIC, 10));
		lblInfoFieldNum.setBounds(301, 11, 123, 45);
		frame.getContentPane().add(lblInfoFieldNum);
	}
}
