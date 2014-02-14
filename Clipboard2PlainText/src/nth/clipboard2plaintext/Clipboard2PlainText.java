package nth.clipboard2plaintext;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Clipboard2PlainText {

	/**
	 * @param args
	 * 
	 *            Gets (rich) text as plain text from the clip board and put it
	 *            on the clip board
	 */
	public static void main(String[] args) {
		final Clipboard clipboard = Toolkit.getDefaultToolkit()
				.getSystemClipboard();

		try {
			String plainText = getPlainTextFromClipboard(clipboard);

			putPlainTextOnClipBoard(clipboard, plainText);

			showPopUp();
		} catch (Exception e) {
			// failed. do nothing
		}
		//quit application
		System.exit(0);
	}

	private static String getPlainTextFromClipboard(final Clipboard clipboard)
			throws Exception {
		Transferable clipData = clipboard.getContents(clipboard);
		if (clipData != null
				&& clipData.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			return (String) (clipData.getTransferData(DataFlavor.stringFlavor));
		}
		throw new Exception("Failed");
	}

	private static void putPlainTextOnClipBoard(final Clipboard clipboard,
			String plainText) {
		StringSelection data = new StringSelection(plainText);
		clipboard.setContents(data, data);
	}

	private static void showPopUp() {
		//JOptionPane.showMessageDialog(null, "Clip board 2 plain text", "Clip board text is converted to plain text.", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane pane = new JOptionPane();
		pane.setMessage("Clip board text is converted to plain text.");
		pane.setIcon(null);
		final JDialog dialog = pane.createDialog("Clip board 2 plain text");
		Timer timer = new Timer(1000, new ActionListener() {
			
			@Override
			     public void actionPerformed(ActionEvent e) {         
				dialog.setVisible(false);         // or maybe you'll need dialog.dispose() instead?     
				}
			}
		); 
		timer.setRepeats(false); 
		timer.start(); 
		dialog.setVisible(true); 
	}
}
