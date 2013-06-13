package nl.tue.fingerpaint.client.gui.panels;

import nl.tue.fingerpaint.client.gui.animation.SizeAnimation;
import nl.tue.fingerpaint.shared.utils.Utils;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Panel that contains all widgets that are controls connected to defining a
 * protocol. These are grouped in this container to easily hide and show them.
 * 
 * @author Group Fingerpaint
 */
public class ProtocolPanelContainer extends SimplePanel {

	/**
	 * Duration of the animation to hide/show this panel in milliseconds.
	 */
	public static final int ANIMATION_DURATION = 300;

	/** Animation to hide and show this panel. */
	protected SizeAnimation sizeAnimation;

	/**
	 * Construct a {@link PopupPanel} that is initially hidden, has an ID set
	 * and animations enabled.
	 */
	public ProtocolPanelContainer() {
		super();
		sizeAnimation = new SizeAnimation(getElement(),
				SizeAnimation.ANIMATE_HEIGHT);
		getElement().getStyle().setHeight(0, Unit.PX);
		getElement().getStyle().setDisplay(Display.NONE);
		getElement().setId("protPanel");
	}

	/**
	 * Show/hide this panel with an animation.
	 * 
	 * @param visible
	 *            If the panel should be made visible or invisible.
	 */
	public void setVisibleAnimated(boolean visible) {
		if (sizeAnimation.getInternalHeight() == 0) {
			refreshSize();
		}

		// note: the getProperty may be null, which is why the equals is written
		// this way
		// that prevents nullpointer acces
		boolean isCurrentlyVisible = isVisible();
		if (isCurrentlyVisible && !visible) {
			sizeAnimation.doHide(ANIMATION_DURATION,
					new AsyncCallback<Boolean>() {
						@Override
						public void onSuccess(Boolean result) {
							getElement().getStyle().setDisplay(Display.NONE);
						}

						@Override
						public void onFailure(Throwable caught) {
							// will not be called
						}
					});
		} else if (!isCurrentlyVisible && visible) {
			getElement().getStyle().clearDisplay();
			sizeAnimation.doShow(ANIMATION_DURATION,
					new AsyncCallback<Boolean>() {
						@Override
						public void onSuccess(Boolean result) {
							getElement().getStyle().clearHeight();
							sizeAnimation.refreshSize();
						}

						@Override
						public void onFailure(Throwable caught) {
							// will not be called
						}
					});
		}
	}

	/**
	 * Internally update the size of the element that is animated.
	 */
	public void refreshSize() {
		// Save current values for height and display
		int oldHeightPx = Utils.parseIntFromCss(getElement().getStyle().getHeight());
		String oldDisplay = getElement().getStyle().getDisplay();
		
		// Clear values, so the element is visible and we can get the size
		getElement().getStyle().clearDisplay();
		getElement().getStyle().clearHeight();
		
		// Update size internally
		sizeAnimation.refreshSize();
		
		// Restore values of height and display
		getElement().getStyle().setHeight(oldHeightPx, Unit.PX);
		if ("none".equals(oldDisplay)) {
			getElement().getStyle().setDisplay(Display.NONE);
		}
	}

}
