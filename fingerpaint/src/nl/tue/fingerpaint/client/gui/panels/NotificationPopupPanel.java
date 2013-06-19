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
	 * Hides this NotificationPanel.
	 */
	public void hide() {
		panel.hide();
	}
	
	/**
	 * Shows this NotificationPanel for the {@code timeout} milliseconds.
	 * 
	 * @param timeOut
	 *            The amount of time, in milliseconds, this panel is shown.
	 *            When less than 0, the panel will be shown indefinitely.
	 *            (That is, until it is hidden manually.)
	 *            When exactly 0, the panel will not be shown.
	 */
	public void show(int timeOut) {
		if (timeOut == 0) {
			return;
		}
		
		panel.center();
		
		if (timeOut > 0) {			
			Timer t = new Timer() {
				public void run() {
					panel.hide();
				}
			};
	
			t.schedule(timeOut);
		}
	}
}
