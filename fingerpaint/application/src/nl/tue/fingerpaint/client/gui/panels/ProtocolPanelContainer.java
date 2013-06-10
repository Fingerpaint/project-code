package nl.tue.fingerpaint.client.gui.panels;

import nl.tue.fingerpaint.client.gui.animation.SizeAnimation;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
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
		sizeAnimation = new SizeAnimation(getElement(), SizeAnimation.ANIMATE_HEIGHT);
		getElement().getStyle().setHeight(0, Unit.PX);
		getElement().setId("protPanel");
	}
	
	/**
	 * Show/hide this panel with an animation.
	 * 
	 * @param visible If the panel should be made visible or invisible.
	 */
	public void setVisibleAnimated(boolean visible) {
		if (sizeAnimation.getInternalHeight() == 0) {
			refreshSize();
		}
		
		int heightPx = parseIntFromCss(getElement().getStyle().getHeight());
		if (heightPx < 0) {
			return;
		} else if (heightPx > 0 && !visible) {
			sizeAnimation.doHide(ANIMATION_DURATION);
		} else if (heightPx == 0 && visible) {
			sizeAnimation.doShow(ANIMATION_DURATION);
		}
	}
	
	/**
	 * Internally update the size of the element that is animated.
	 */
	public void refreshSize() {
		int heightPx = parseIntFromCss(getElement().getStyle().getHeight());
		getElement().getStyle().clearHeight();
		sizeAnimation.refreshSize();
		getElement().getStyle().setHeight(heightPx, Unit.PX);
	}
	
	/**
	 * Parse an integer from a 'Xpx' string, where 'X' is a number.
	 * 
	 * @param css The above-described string.
	 * @return The integer from above string, or '-1' on error.
	 */
	protected int parseIntFromCss(String css) {
		RegExp pattern = RegExp.compile("([0-9]+)px");
		MatchResult matcher = pattern.exec(css);
		if (matcher != null) {
			return Integer.parseInt(matcher.getGroup(1));
		}
		return -1;
	}

}
