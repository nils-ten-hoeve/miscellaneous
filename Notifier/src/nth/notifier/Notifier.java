package nth.notifier;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nth.notifier.images.ImageFactory;
import nth.notifier.time.TimeDialog;
import nth.notifier.time.TimeStatus;
import nth.notifier.time.TimeUpdater;

public class Notifier {

	private TimeStatus timeStatus = TimeStatus.WORKING;
	private TrayIcon trayIcon;
	private String timeToolTip;
	private String emailToolTip;
	private Integer numberOfUnreadEmails = 0;
	private String timeToGo;

	public static void main(String[] args) {

		/* Use an appropriate Look and Feel */
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		// Schedule a job for the event-dispatching thread:
		// adding TrayIcon.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Notifier notifier = new Notifier();
				notifier.createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI() {
		// Check the SystemTray support
		if (!SystemTray.isSupported()) {
			JOptionPane.showMessageDialog(null, "Notifier could not be added to system tray (not ssupported).");
			return;
		}

		final PopupMenu popup = new PopupMenu();
		Image image = ImageFactory.getImage(TimeStatus.INVALID_START_TIME, "");// TODO
		trayIcon = new TrayIcon(image);
		final SystemTray tray = SystemTray.getSystemTray();

		// Create a popup menu components
		MenuItem timeSettings = new MenuItem("Time settings");
		// MenuItem emailSettings = new MenuItem("E-mail settings");
		MenuItem exitItem = new MenuItem("Exit");

		// Add components to popup menu
		popup.add(timeSettings);
		// popup.addSeparator();
		// popup.add(emailSettings);
		popup.addSeparator();
		popup.add(exitItem);

		trayIcon.setPopupMenu(popup);
		// trayIcon.setImageAutoSize(true);
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			JOptionPane.showMessageDialog(null, "Notifier could not be added to system tray.");
			return;
		}

		final TimeDialog timeDialog = new TimeDialog();
		final TimeUpdater timeUpdater = new TimeUpdater(this, timeDialog);
		timeUpdater.update();

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				exit(tray, timeUpdater);
			}

		}));

		trayIcon.addMouseListener(new MouseListener() {
			Timer timer = new Timer(300, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// single click
					timer.stop();
					timeDialog.setVisible(true);
					// refresh dialog and icon
					timeUpdater.update();
				}
			});

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (timer.isRunning()) {
						// double click
						timer.stop();
					} else {
						timer.restart();
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// trayIcon.addMouseListener(new MouseListener() {
		//
		// @Override
		// public void mouseReleased(MouseEvent e) {
		// }
		//
		// @Override
		// public void mousePressed(MouseEvent e) {
		// }
		//
		// @Override
		// public void mouseExited(MouseEvent e) {
		// }
		//
		// @Override
		// public void mouseEntered(MouseEvent e) {
		// }
		//
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
		// timeDialog.setVisible(true);
		// timeUpdater.update();
		// } else if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
		// timeDialog.setVisible(false);
		// emailUpdater.update();
		// }
		// }
		// });

		timeSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeDialog.setVisible(true);
				timeUpdater.update();
			}
		});

		// emailSettings.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// JOptionPane.showMessageDialog(null, "TODO E-Mail settings");
		// }
		// });
		//

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit(tray, timeUpdater);
			}
		});

	}

	public void update(TimeStatus timeStatus, String timeToolTip, String timeToGo) {
		this.timeToolTip = timeToolTip;
		this.timeToGo = timeToGo;
		if (timeStatus != this.timeStatus) {
			this.timeStatus = timeStatus;
			switch (timeStatus) {
			case INVALID_START_TIME:
				trayIcon.displayMessage(getClass().getSimpleName(), "Invalid start time.", TrayIcon.MessageType.WARNING);
				break;
			}
		}
		updateImage();
		updateToolTip();// TODO also update on trayIcon mouseover to refresh time
	}

	public void update(Integer numberOfUnreadEmails, String emailToolTop) {
		this.emailToolTip = emailToolTop;
		if (numberOfUnreadEmails == null && this.numberOfUnreadEmails != null) {
			trayIcon.displayMessage(getClass().getSimpleName(), "Invalid start time.", TrayIcon.MessageType.WARNING);
		}
		this.numberOfUnreadEmails = numberOfUnreadEmails;
		updateImage();
		updateToolTip();// TODO also update on trayIcon mouseover to refresh time
	}

	private void updateImage() {
		// Image image = ImageFactory.getImage(timeStatus);
		Image image = ImageFactory.getImage(timeStatus, timeToGo);
		trayIcon.setImage(image);
	}

	private void updateToolTip() {
		StringBuffer toolTip = new StringBuffer(timeToolTip);
		if (emailToolTip != null) {
			toolTip.append("  ");
			toolTip.append(emailToolTip);
		}
		trayIcon.setToolTip(toolTip.toString());
	}

	private void exit(final SystemTray tray, final TimeUpdater timeUpdater) {
		timeUpdater.stopTimer();
		tray.remove(trayIcon);
		System.exit(0);
	}
}
