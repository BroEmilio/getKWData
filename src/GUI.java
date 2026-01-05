import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame{
	private static final long serialVersionUID = 1L;
	Font style1 = new Font("Arial", Font.ITALIC, 14);
	Font style2 = new Font("Arial", Font.ITALIC, 12);
    JFrame frame;
    Path choosedFile;
    ProcessFile fileProcessing = null;
    JList<String> listFieldsNumbers = null;
    DefaultListModel<String> listModel;
    JTextPane textPane = null;
    ArrayList<FieldData> listFieldsData = new ArrayList<FieldData>();
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    JTextArea textBox_Clipboard = null;
    JScrollPane scrollClipboard = null;
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
        				listFieldsData = fileProcessing.listFieldsData;
        				updateList();
        				/*
	        			int result=JOptionPane.showConfirmDialog(null,
	        						"Poprawnie przetworzono plik "+choosedFile.getFileName().toString(),
	        						"Poprawnie przetworzono",
	        						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        				if(result==JOptionPane.OK_OPTION || result == JOptionPane.CANCEL_OPTION)
        					System.exit(0);
        				*/
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
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.5};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0};
		gridBagLayout.columnWidths = new int[]{245, 89};
		gridBagLayout.rowHeights = new int[]{63, 120, 22, 90};
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
		
		textPane = new JTextPane();
		GridBagConstraints gbc_FieldData = new GridBagConstraints();
		gbc_FieldData.fill = GridBagConstraints.BOTH;
		gbc_FieldData.insets = new Insets(0, 0, 5, 5);
		gbc_FieldData.gridx = 0;
		gbc_FieldData.gridy = 1;
		gbc_FieldData.weightx=1;
		gbc_FieldData.weighty=1;
		frame.getContentPane().add(textPane, gbc_FieldData);

		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		listFieldsNumbers = new JList<String>();
		scrollPane.setViewportView(listFieldsNumbers);
		
		
		
		JLabel lblAktualnieWSchowku = new JLabel("Aktualnie w schowku");
		GridBagConstraints gbc_lblAktualnieWSchowku = new GridBagConstraints();
		gbc_lblAktualnieWSchowku.gridwidth = 2;
		gbc_lblAktualnieWSchowku.insets = new Insets(0, 0, 5, 0);
		gbc_lblAktualnieWSchowku.gridx = 0;
		gbc_lblAktualnieWSchowku.gridy = 2;
		frame.getContentPane().add(lblAktualnieWSchowku, gbc_lblAktualnieWSchowku);
		
		scrollClipboard = new JScrollPane();
		textBox_Clipboard = new JTextArea();
		scrollClipboard.add(textBox_Clipboard);
		scrollClipboard.setViewportView(textBox_Clipboard);
		textBox_Clipboard.setEditable(false);
		GridBagConstraints gbc_Clipboard = new GridBagConstraints();
		gbc_Clipboard.gridwidth = 2;
		gbc_Clipboard.fill = GridBagConstraints.BOTH;
		gbc_Clipboard.gridx = 0;
		gbc_Clipboard.gridy = 3;
		frame.getContentPane().add(scrollClipboard, gbc_Clipboard);
		
	}
	
	private void updateList() {
		listModel = new DefaultListModel<String>();
		for(FieldData oneField : listFieldsData) {
			listModel.addElement(oneField.getFieldNumber());
		}
		System.out.println("lM:"+listModel.size());
		//JScrollPane listScroller = new JScrollPane();
		//listScroller.setViewportView(listFieldsNumbers);
		listFieldsNumbers.setModel(listModel);
		//listFieldsNumbers=new JList<String>(listModel);
		
		listFieldsNumbers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFieldsNumbers.setVisibleRowCount(-1);
		frame.repaint();
		
		listFieldsNumbers.addListSelectionListener (new ListSelectionListener()
	    {
	        public void valueChanged (ListSelectionEvent e)
	        {
	            if (e.getValueIsAdjusting ( ) == false)
	            {   
	                //List <String> currentField = listFieldsNumbers.getSelectedValuesList();
	                int currentIndex = listFieldsNumbers.getSelectedIndex();
	                FieldData currentFieldData = listFieldsData.get(currentIndex);
	                textPane.setText(currentFieldData.toString());
	                SimpleAttributeSet sas = new SimpleAttributeSet();
	                StyleConstants.setBold(sas, true);
	                StyleConstants.setFontSize(sas, 14);
	                int stringLength = currentFieldData.getFieldId().length() + currentFieldData.getFieldNumber().length() + 3;
	                textPane.getStyledDocument().setCharacterAttributes(0, stringLength, sas, false);
	                
	                Transferable content = clipboard.getContents(null);
	                if(content !=  null){
	                	textBox_Clipboard.setText(getStringFromTransferable(content));
	                	textBox_Clipboard.updateUI();
	                	textBox_Clipboard.setCaretPosition(0);
	                }
	            }
	        }

	    });
		
		listFieldsNumbers.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		    	listFieldsNumbers = (JList<String>)evt.getSource();
		        if (evt.getClickCount() == 2 || evt.getClickCount() == 3) {
		        	// Double and Triple click detected
		        	int currentIndex = listFieldsNumbers.getSelectedIndex();
		        	String displaingText = listFieldsData.get(currentIndex).toString();
		        	String clipboardText = "";
		        	String[] clipboardArray = displaingText.split("\\n");
		        	displaingText = "Dane dla dzia³ki : " +clipboardArray[0]+"\n";
		        	for(int i=1; i<clipboardArray.length; i++){
		        		displaingText += clipboardArray[i]+"\n";
		        		clipboardText += clipboardArray[i]+"\n";
		        	}
		        	StringSelection selection = new StringSelection(clipboardText);
	                clipboard.setContents(selection, selection);
		            int index = listFieldsNumbers.locationToIndex(evt.getPoint());
		            Transferable content = clipboard.getContents(null);
		            textBox_Clipboard.setText("");
		            textBox_Clipboard.setText(getStringFromTransferable(content));
		            textBox_Clipboard.setCaretPosition(0);
		        } 
		        //scrollClipboard.getVerticalScrollBar().setLocation(new Point(0,0));;	
		        //int index = listFieldsNumbers.locationToIndex(evt.getPoint());
		    }
		});
		
		//listFieldsNumbers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//listFieldsNumbers.setSelectedIndex(0);
		//listFieldsNumbers.addListSelectionListener(this);
		//listFieldsNumbers.setVisibleRowCount(5);
	}
	
	private String getStringFromTransferable(Transferable contents){
		String result = null;
		boolean hasStringText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasStringText) {
		    try {
		        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
		    } catch (UnsupportedFlavorException | IOException ex) {
		        System.out.println(ex); ex.printStackTrace();
		    }	
		}
		return result;
	}
}
