import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * This creates the search dialog for the Find, Replace and ReplaceAll functions
 * @author Tomisin Jenrola
 * @version 31/10/15
 */


public class SearchDialog extends JDialog 
{

	private final JPanel contentPanel = new JPanel();
	private JTextField findTextField;
	private JTextField replaceTextField;

	private JTextArea tArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SearchDialog dialog = new SearchDialog(new JTextArea());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SearchDialog(JTextArea tA) {
		tArea = tA;
		setTitle("Search");
		setBounds(100, 100, 350, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		ActionListener listener = new SearchListener();
		
		JLabel lblSearchFor = new JLabel("Search for: ");
		lblSearchFor.setBounds(12, 0, 100, 34);
		contentPanel.add(lblSearchFor);
		
		findTextField = new JTextField();
		findTextField.setBounds(12, 31, 180, 19);
		contentPanel.add(findTextField);
		findTextField.setColumns(10);
		
		JButton btnFind = new JButton("Find");
		btnFind.setBounds(219, 28, 117, 25);
		btnFind.addActionListener(listener);
		contentPanel.add(btnFind);
		
		JLabel lblReplaceWith = new JLabel("Replace with: ");
		lblReplaceWith.setBounds(12, 66, 117, 15);
		contentPanel.add(lblReplaceWith);
		
		replaceTextField = new JTextField();
		replaceTextField.setBounds(12, 93, 180, 19);
		contentPanel.add(replaceTextField);
		replaceTextField.setColumns(10);
		
		JButton btnReplace = new JButton("Replace");
		btnReplace.setBounds(219, 75, 117, 25);
		btnReplace.addActionListener(listener);
		contentPanel.add(btnReplace);
		
		JButton btnReplaceAll = new JButton("Replace All");
		btnReplaceAll.setBounds(219, 102, 117, 25);
		btnReplaceAll.addActionListener(listener);
		contentPanel.add(btnReplaceAll);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.setActionCommand("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * Construct the search dialog
	  * @author brown
	 */
	class SearchListener implements ActionListener
	{

		private String findItem;
		private String replaceItem;
		@Override
		public void actionPerformed(ActionEvent e) {
			findItem = findTextField.getText();
			replaceItem = replaceTextField.getText();
			switch( e.getActionCommand() ){
			case "Replace All":
				tArea.setText(tArea.getText().replaceAll(findItem, replaceItem));
				break;
			case "Find":
				findnext();
				break;
			case "Replace":				
				if(tArea.getSelectedText() != null)
					if( tArea.getSelectedText().equals(findItem))
						tArea.replaceSelection(replaceItem);
		        findnext();
		        break;
		    case "Close":
		     	System.exit(0);
			}

		}

	    /**
	     * 
	     * Find the location of the next occurrence of the findItem- string in the tArea
	     * and make it the tArea's current selection. This only searches from the current
	     * selection checks to end-of-text; on hitting the end of the text it pops up a message
	     * dialog warning, and places the selection at the beginning
	     * of the editor pane.  This is not the best UI semantics, but is an easy way to do 
	     * something that makes sense.
	     * 
	     */
	    private void findnext() {
	        int lpos = tArea.getSelectionEnd();
	        int index = tArea.getText().indexOf(findItem, lpos);
	        // debugging: System.out.println(index);
	        if (index < 0) {
	            JOptionPane.showMessageDialog(SearchDialog.this, findItem + ": End of text reached");
	            tArea.select(0, 0);
	        } else {
	            tArea.select(index, index + findItem.length());
	            tArea.setFocusable(true);
	        }

	    }
	}
}
