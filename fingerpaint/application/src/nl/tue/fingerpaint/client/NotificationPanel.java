package nl.tue.fingerpaint.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * 
 * @author Group Fingerpaint
 *
 */
public class NotificationPanel {
	int timeOut;
	Label messageLabel;
	PopupPanel panel;
	
	/**
	 * Creates a NotificationPanel with the message {@code message}.
	 * @param message The message to show in the notification.
	 */
	public NotificationPanel(String message) {
		messageLabel = new Label(message);
		
		panel = new PopupPanel();
		panel.add(messageLabel);
		panel.setAnimationEnabled(true);
		panel.setModal(false);
	}
	
	/**
	 * Shows this NotificationPanel for the {@code timeout} milliseconds.
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
