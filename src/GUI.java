import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

public class GUI extends JFrame{
	private static final long serialVersionUID = 1L;
	Font style1 = new Font("Arial", Font.ITALIC, 14);
	Font style2 = new Font("Arial", Font.ITALIC, 12);
    JFrame frame;
    Path choosedFile;

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
		loadItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LoadingFileProfile loader = new LoadingFileProfile();
        		choosedFile = loader.getPath();
        		if(choosedFile!=null) {
        			ProcessFile fileProcessing = new ProcessFile(choosedFile);
        			if(fileProcessing.run()) {
	        			int result=JOptionPane.showConfirmDialog(null,
	        						"Poprawnie przetworzono plik "+choosedFile.getFileName().toString(),
	        						"Poprawnie przetworzono",
	        						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        				if(result==JOptionPane.OK_OPTION || result == JOptionPane.CANCEL_OPTION)
        					System.exit(0);
        			}
        		}
        	}		
    	});
		
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
		frame.setMaximumSize(new Dimension(600,700));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SetPolishForGUI.setForFileChoosers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0};
		gridBagLayout.columnWidths = new int[]{245, 89};
		gridBagLayout.rowHeights = new int[]{63, 154, 22, 76};
		frame.getContentPane().setLayout(gridBagLayout);
			
		JLabel lblInfoFieldNum = new JLabel(
				"<html><div style='text-align:center;'>Podwójne klikniêcie w nr dzia³ki spowoduje "
				+ "skopiowanie danych do schowka</div></html>");
		lblInfoFieldNum.setFont(new Font("Arial Narrow", Font.ITALIC, 10));
		GridBagConstraints gbc_lblInfoFieldNum = new GridBagConstraints();
		gbc_lblInfoFieldNum.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInfoFieldNum.insets = new Insets(0, 0, 5, 0);
		gbc_lblInfoFieldNum.gridx = 1;
		gbc_lblInfoFieldNum.gridy = 0;
		gbc_lblInfoFieldNum.weightx=0;
		gbc_lblInfoFieldNum.weighty=0;
		lblInfoFieldNum.setMaximumSize(new Dimension(90,70));
		frame.getContentPane().add(lblInfoFieldNum, gbc_lblInfoFieldNum);
		
		JTextField textBox_FieldData = new JTextField();
		GridBagConstraints gbc_FieldData = new GridBagConstraints();
		gbc_FieldData.fill = GridBagConstraints.BOTH;
		gbc_FieldData.insets = new Insets(0, 0, 5, 5);
		gbc_FieldData.gridx = 0;
		gbc_FieldData.gridy = 1;
		gbc_FieldData.weightx=1;
		gbc_FieldData.weighty=1;
		frame.getContentPane().add(textBox_FieldData, gbc_FieldData);
		
		JList<String> listFieldsNumbers = new JList<String>();
		GridBagConstraints gbc_listFieldsNumbers = new GridBagConstraints();
		gbc_listFieldsNumbers.fill = GridBagConstraints.BOTH;
		gbc_listFieldsNumbers.insets = new Insets(0, 0, 5, 0);
		gbc_listFieldsNumbers.gridx = 1;
		gbc_listFieldsNumbers.gridy = 1;
		gbc_listFieldsNumbers.weightx=0;
		frame.getContentPane().add(listFieldsNumbers, gbc_listFieldsNumbers);
		
		JLabel lblAktualnieWSchowku = new JLabel("Aktualnie w schowku");
		GridBagConstraints gbc_lblAktualnieWSchowku = new GridBagConstraints();
		gbc_lblAktualnieWSchowku.gridwidth = 2;
		gbc_lblAktualnieWSchowku.insets = new Insets(0, 0, 5, 5);
		gbc_lblAktualnieWSchowku.gridx = 0;
		gbc_lblAktualnieWSchowku.gridy = 2;
		frame.getContentPane().add(lblAktualnieWSchowku, gbc_lblAktualnieWSchowku);
		
		JTextField textBox_Clipboard = new JTextField();
		GridBagConstraints gbc_Clipboard = new GridBagConstraints();
		gbc_Clipboard.gridwidth = 2;
		gbc_Clipboard.fill = GridBagConstraints.BOTH;
		gbc_Clipboard.gridx = 0;
		gbc_Clipboard.gridy = 3;
		frame.getContentPane().add(textBox_Clipboard, gbc_Clipboard);
		
	}
}
