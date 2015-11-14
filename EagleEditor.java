import java.awt.EventQueue;

/**
 *Runs the  
 *
 * @author Tomisin Jenrola
 * @version 2015-9-28
 */
public class EagleEditor {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorFrame frame = new EditorFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
