package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.animation.RotationAnimation;
import nl.tue.fingerpaint.client.gui.animation.SizeAnimation;
import nl.tue.fingerpaint.client.resources.FingerpaintResources;
import nl.tue.fingerpaint.shared.utils.Utils;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * <p>This button is used to show and hide the menu panel in the application.</p>
 * 
 * @author Group Fingerpaint
 */
public class MenuToggleButton extends Button implements ClickHandler {

	/** The ID of the button element. */
	public static final String ELEMENT_ID = "menuToggleButton";
	
	/** How long the sliding animation takes. */
	public static final int DURATION = 300;
	/** At which angle the toggle button content is when the menu is hidden. */
	public static final int HIDDEN_ANGLE = 0;
	/** At which angle the toggle button content is when the menu is shown. */
	public static final int SHOWN_ANGLE = 135;
	
	/** Indicate if the menu is shown or not. */
	protected boolean shown = true;
	/** The MenuPanel that is shown/hidden. */
	protected SimplePanel menuPanel;
	/** Animation to hide menu panel. */
	protected SizeAnimation menuAnimation;
	/** Animation to rotate the button. */
	protected RotationAnimation buttonAnimation;
	
	/**
	 * Construct a new {@link MenuToggleButton}.
	 * 
	 * @param menuPanelWrapper The element to be animated.
	 */
	public MenuToggleButton(SimplePanel menuPanelWrapper) {
		super();
		
		// Set ID and load image element in button
		getElement().setId(ELEMENT_ID);
		ImageElement imgEl = ImageElement.as(DOM.createElement("img"));
		imgEl.setPropertyString("src", FingerpaintResources.INSTANCE.plusImage().getSafeUri().asString());
		getElement().appendChild(imgEl);
		
		// Set variables
		this.menuPanel = menuPanelWrapper;
		addClickHandler(this);
		this.menuAnimation = new SizeAnimation(menuPanelWrapper.getElement(), SizeAnimation.ANIMATE_WIDTH);
		this.buttonAnimation = new RotationAnimation(imgEl, SHOWN_ANGLE);
	}

	/**
	 * Toggles the visibility of the main menu.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		if (shown) {
			buttonAnimation.doRotate(DURATION, HIDDEN_ANGLE, false);
			menuAnimation.doHide(DURATION);
		} else {
			buttonAnimation.doRotate(DURATION, SHOWN_ANGLE, true);
			menuAnimation.doShow(DURATION, new AsyncCallback<Boolean>() {
				@Override
				public void onSuccess(Boolean result) {
					menuPanel.getElement().getStyle().clearWidth();
					refreshMenuSize();
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// will not be called
				}
			});
		}
		
		// Actually toggle state
		shown = !shown;
	}
	
	/**
	 * Internally update the size of the element that is animated.
	 */
	public void refreshMenuSize() {
		// Save current value for width
		int oldWidthPx = Utils.parseIntFromCss(menuPanel.getElement().getStyle().getWidth());
		
		// Clear values, so the element is visible and we can get the size
		menuPanel.getElement().getStyle().clearWidth();
		
		// Update size internally
		menuAnimation.refreshSize();
		
		// Restore value of width
		if (oldWidthPx >= 0) {
			// if less than 0, the width of the menu is not in pixels...
			// we do not know what to do in that case :-(
			menuPanel.getElement().getStyle().setWidth(oldWidthPx, Unit.PX);
		}
	}
}
