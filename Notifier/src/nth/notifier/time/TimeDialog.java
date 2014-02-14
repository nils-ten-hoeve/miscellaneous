package nth.notifier.time;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nth.notifier.propertygrid.PropertyGrid;
import nth.notifier.propertygrid.PropertyRow;
import nth.notifier.propertygrid.PropertyRow.FieldWidth;

public class TimeDialog extends JDialog {
	private static final long serialVersionUID = -3258470936971937946L;
	private JLabel currentTimeField;
	private JLabel stopTimeField;
	private JProgressBar progressBar;
	private JLabel statusField;
	private SpinnerDateModel dateModel;
	private TimeUpdater timeUpdater;
    
    

	public TimeDialog() {
		getContentPane().add(createPropertyGrid());
//TODO		Image clockImage = ImageFactory.getImage(TimeStatus.INVALID_START_TIME);
//		setIconImage(clockImage);
		setTitle("Time Settings");
		setMinimumSize(new Dimension(300, 150));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) screenSize.getWidth() - getWidth() - 20, (int) screenSize.getHeight() - getHeight() - 50);
		setResizable(false);
	}

	private Component createPropertyGrid() {
		PropertyGrid propertyGrid = new PropertyGrid();
		// start time
		PropertyRow propertyRow = new PropertyRow("Start Time", "Time that you started working", createTimeField(), FieldWidth.full, null);// TODO add validator
		propertyGrid.addPropertyRow(propertyRow);

		// current time
		currentTimeField = new JLabel("");
		propertyRow = new PropertyRow("Current time", "Now", currentTimeField, FieldWidth.full, null);
		propertyGrid.addPropertyRow(propertyRow);

		// stop time
		stopTimeField = new JLabel("");
		propertyRow = new PropertyRow("Stop time", "Done working", stopTimeField, FieldWidth.full, null);
		propertyGrid.addPropertyRow(propertyRow);

		// status
		statusField = new JLabel("");
		propertyRow = new PropertyRow("Status", "Status", statusField, FieldWidth.full, null);
		propertyGrid.addPropertyRow(propertyRow);

		// progress bar
		progressBar = new JProgressBar();
		propertyRow = new PropertyRow("Progress", "Progress", progressBar, FieldWidth.full, null);
		propertyGrid.addPropertyRow(propertyRow);

		return propertyGrid;
	}

	private JSpinner createTimeField() {
		// create spinner with field
		JSpinner spinner = new JSpinner();
		dateModel = new SpinnerDateModel();
		dateModel.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// get value from spinner
				Calendar startDateTime = Calendar.getInstance();
				startDateTime.setTime(dateModel.getDate());
				// override with current date
				Calendar now = Calendar.getInstance();
				startDateTime.set(Calendar.YEAR, now.get(Calendar.YEAR));
				startDateTime.set(Calendar.MONTH, now.get(Calendar.MONTH));
				startDateTime.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
				// set new startDateTime
				if (timeUpdater != null) {
					timeUpdater.setStartDateTime(startDateTime.getTime());
				}
			}
		});

		spinner.setModel(dateModel);
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "H:mm");
		spinner.setEditor(dateEditor);
		//set alignment
		JFormattedTextField textField = ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
		textField.setHorizontalAlignment(JFormattedTextField.LEFT);


		return spinner;
	}

	public void update(String currentTime, String stopTime, String toolTip, int workTimeInMinutes, int remainingMinutes) {
		// current time
		currentTimeField.setText(currentTime);
		// stop time
		stopTimeField.setText(stopTime);
		// status
		statusField.setText(toolTip);
		// progress bar
		progressBar.setMaximum(workTimeInMinutes);
		progressBar.setStringPainted(true);
		if (remainingMinutes>0) {
			progressBar.setValue(workTimeInMinutes-remainingMinutes);
		} else {
			progressBar.setValue(workTimeInMinutes);
		}
	}

	
	public void setStartTime(Date startDateTime) {
		dateModel.setValue(startDateTime);// TODO what if null?
	}

	public void setTimeUpdater(TimeUpdater timeUpdater) {
		this.timeUpdater = timeUpdater;
	}
}
