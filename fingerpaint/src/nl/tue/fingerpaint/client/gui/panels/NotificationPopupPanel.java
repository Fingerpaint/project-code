package nl.tue.fingerpaint.client.gui.panels;

import nl.tue.fingerpaint.client.gui.animation.Direction;
import nl.tue.fingerpaint.client.gui.animation.SlideAnimation;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;

/**
 * A panel to show a notification message.
 * 
 * @author Group Fingerpaint
 */
public class NotificationPopupPanel {
	
	/** Duration of animation to show/hide a notification. */
	public static final int ANIMATION_DURATION = 200;
	/** Classname that is added to this panel. */
	public static final String NOTIFICATION_CLASS = "popupPanelNotification";
	
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
	/** Animation to show/hide the panel. */
	private SlideAnimation panelAnimation;

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
		panel.setAnimationEnabled(false);
		panel.setModal(false);
		panel.getElement().addClassName(NOTIFICATION_CLASS);
		panelAnimation = new SlideAnimation(panel.getElement(), Direction.LEFT);
	}

	/**
	 * Put the pop up panel at the top left of the screen.
	 * If it was not visible, show it.
	 */
	public void setTopLeft() {
		panel.setPopupPositionAndShow(new PositionCallback() {
			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				panel.setPopupPosition(0, 0);
				panelAnimation.doSlideOut(1);
				panelAnimation.doSlideIn(ANIMATION_DURATION);
			}
		});
	}
	
	/**
	 * Hides this NotificationPanel.
	 */
	public void hide() {
		panelAnimation.doSlideOut(ANIMATION_DURATION);
		Timer doAfterAnimation = new Timer() {
			@Override
			public void run() {
				panel.hide();
			}
		};
		doAfterAnimation.schedule(ANIMATION_DURATION + 10);
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
		
		setTopLeft();
		
		if (timeOut > 0) {			
			Timer t = new Timer() {
				public void run() {
					hide();
				}
			};
	
			t.schedule(timeOut);
		}
	}
}
