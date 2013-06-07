package nl.tue.fingerpaint.client.gui.panels;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * A panel to show a notification message.
 * 
 * @author Group Fingerpaint
 */
public class NotificationPopupPanel {
	/**
	 * How long the notification panel will be visible.
	 */
	protected int timeOut;
	/**
	 * Label that contains the message to show.
	 */
	protected Label messageLabel;
	/**
	 * Actual notification panel.
	 */
	protected PopupPanel panel;

	/**
	 * Creates a {@link NotificationPopupPanel} with the message {@code message}.
	 * 
	 * @param message
	 *            The message to show in the notification.
	 */
	public NotificationPopupPanel(String message) {
		messageLabel = new Label(message);

		panel = new PopupPanel();
		panel.add(messageLabel);
		panel.setAnimationEnabled(true);
		panel.setModal(false);
	}

	/**
	 * Shows this NotificationPanel for the {@code timeout} milliseconds.
	 * 
	 * @param timeOut
	 *            The amount of time, in milliseconds, this panel is shown.
	 */
	public void show(int timeOut) {
		panel.center();
		panel.show();

		Timer t = new Timer() {
			public void run() {
				panel.hide();
			}
		};

		t.schedule(timeOut);
	}
}
