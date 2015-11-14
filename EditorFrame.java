import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *  @author Tomisin Jenrola
 *  @version 31/10/15
*/

public class EditorFrame extends JFrame
{

	private JTextArea textArea;
	private JFileChooser fileChooser = new JFileChooser();
	private JPanel panel;
	private JMenu menu;
	private SearchDialog dialog;
	private WordCounter wCount;
	private WordCountDialog wordDialog;
   	private String selectedText;
	private JMenuItem saveAs;

	private static final int FRAME_WIDTH = 1000;
	private static final int  FRAME_HEIGHT = 600;

	public EditorFrame() {		
		setTitle("EagleEdit");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		content();
	}

	private void content() {
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5,5,5,5));
		panel.setLayout(new BorderLayout(2, 3));

		textArea = new JTextArea(20, 20);
		dialog = new SearchDialog(textArea);	
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel.add(scrollPane);

		JMenuBar menuBar = new JMenuBar();
		panel.add(menuBar, BorderLayout.NORTH);

		JMenu file = new JMenu("File");
		menuBar.add(file);

		JMenuItem open = new JMenuItem("Open File...");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileChooser.showOpenDialog(EditorFrame.this) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						FileReader fileReader = new FileReader(file);

						int filelength = (int) file.length();
						char[] buffer = new char[filelength];

						fileReader.read(buffer, 0, filelength);	
						fileReader.close();
						textArea.setText(new String(buffer));
						setTitle("Eagle Edit(" + file.getName() + ")");
					}
					catch (IOException ioe) {
						JOptionPane.showMessageDialog(EditorFrame.this, "Error. Can't load file.");
					}
				}
			}
		});
		open.setToolTipText("Open a new file");
		file.add(open);

		saveAs = new JMenuItem("Save As");
		saveAs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				File file = fileChooser.getSelectedFile();
			    if( fileChooser.showSaveDialog(EditorFrame.this) != JFileChooser.APPROVE_OPTION ) return;
			    file = fileChooser.getSelectedFile();

			    try {
			        FileWriter writer = new FileWriter(file);
			        writer.write(textArea.getText());
			        writer.close();
			        EditorFrame.this.setTitle("Eagle Edit (" + file.getName() + ")");
			    } catch (IOException ioe) {
			        JOptionPane.showMessageDialog(EditorFrame.this, "Error. Can't write file.");
			    }
			}
		});
		saveAs.setToolTipText("Save the current version of your file with a new name or as another document");
		file.add(saveAs);

		JMenuItem save = new JMenuItem("Save");
		ActionListener saveListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				File file = fileChooser.getSelectedFile();
			    if (e.getSource().equals(saveAs) || file == null) {
			        if( fileChooser.showSaveDialog(EditorFrame.this) != JFileChooser.APPROVE_OPTION )
			        	return;
			        file = fileChooser.getSelectedFile();
			    }
			    try {
			        FileWriter writer = new FileWriter(file);
			        writer.write(textArea.getText());
			        writer.close();
			        EditorFrame.this.setTitle("Eagle Edit (" + file.getName() + ")");
			    } catch (IOException ioe) {
			        JOptionPane.showMessageDialog(EditorFrame.this, "Error. Can't write file.");
			    }
			}
		};
		save.addActionListener(saveListener);
		save.setToolTipText("Save current file");
		file.add(save);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setToolTipText("Close the editor");
		file.add(exit);

		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);

		JMenuItem copy = new JMenuItem("Copy");
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.copy();
			}
		});
		copy.setToolTipText("Copy the selected area to the clipboard");
		edit.add(copy);

		JMenuItem cut = new JMenuItem("Cut");
		cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.cut();
			}
		});
		cut.setToolTipText("Cut out the selected portion to the clipboard");
		edit.add(cut);

		JMenuItem paste = new JMenuItem("Paste");
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.paste();
			}
		});
		paste.setToolTipText("Paste text on the clipboard");
		edit.add(paste);

		JMenu tools = new JMenu("Tools");
		menuBar.add(tools);

		JMenuItem search = new JMenuItem("Search");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(true);
			}
		});
		search.setToolTipText("Replace a word, character, phrase or sentence within the file with another string");
		tools.add(search);

		JMenuItem wordCount = new JMenuItem("Word Count");
		wordCount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String theText = textArea.getText();
               selectedText = textArea.getSelectedText();
               if(theText != null) {
                  textArea.setCaretPosition(0);
                  WordCounter counter = new WordCounter(theText, selectedText);
                  counter.countWord();
                  counter.countLine();
                  counter.countSelected();
                  wordDialog = new WordCountDialog(EditorFrame.this, counter);
                  wordDialog.show();
               }
            }
         });
		wordCount.setToolTipText("Check the number of words and characters in the file");
		tools.add(wordCount);
		
		add(panel);
	}

	/**
     * The dialog box that displays the number of words, lines, and characters.
     * This is a modification of the Sugiharto Widjaja's code so that the number of selected words and characters can be displayed
     * @author Tomisin Jenrola
     * @version 31/10/15
	*/

	class WordCountDialog extends JDialog
	{
  		
   		public static final int WSIZE = 185;	// The width of the frame 		
   		public static final int HSIZE = 185;	// The height of the frame

   		/**
   	 	 * Create the dialog box
   	 	 */
   		public WordCountDialog(JFrame master, WordCounter counter)
   		{

      		super(master, "Word Count", false);
   			selectedText = textArea.getSelectedText();
      		Container contentPane = getContentPane();
      		contentPane.add(new JLabel(
       		 "<HTML>Word Count :<BR><HR>"
        	 + "Words : " + counter.getNumOfWords() + "<BR>"
        	 + "Selected words : " + counter.getNumOfSelectedWords() + "<BR>"
         	 + "Characters : " + counter.getNumOfChars() + "<BR>"
        	 + "Selected characters : " + counter.getNumOfSelectedCharacters() + "<BR>"
         	 + "Lines : " + counter.getNumOfLines() + "</HTML>"),
         	BorderLayout.CENTER);
      		JButton close = new JButton("Close");
      		close.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
              	 setVisible(false);
            	}
         	});
         	JPanel pane = new JPanel();
        	pane.add(close);
         	contentPane.add(pane, BorderLayout.SOUTH);

         	setSize(WSIZE, HSIZE);
   		}
	}

	public static void main(String[] args) {
		EditorFrame test = new EditorFrame();
		test.setVisible(true);
	}

}
