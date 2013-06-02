package nl.tue.fingerpaint.client.gui;

import nl.tue.fingerpaint.client.gui.animation.Direction;
import nl.tue.fingerpaint.client.gui.animation.SlideAnimation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * <p>This button is used to show and hide the menu panel in the application.</p>
 * 
 * @author Group Fingerpaint
 */
public class MenuToggleButton extends Button implements ClickHandler {

	/** The ID of the button element. */
	public static final String ELEMENT_ID = "menuToggleButton";
	
	/** Text that is shown in the button when the menu is visible and can be hidden. */
	public static final String HIDE_TEXT = "-";
	/** Text that is shown in the button when the menu is not visible and can be shown. */
	public static final String SHOW_TEXT = "+";
	
	/** How long the sliding animation takes. */
	public static final int DURATION = 1000;
	
	/** Indicate if the menu is shown or not. */
	protected boolean shown = true;
	/** The MenuPanel that is shown/hidden. */
	protected VerticalPanel menuPanel;
	/** Animation to hide menu panel. */
	protected SlideAnimation menuAnimation;
	
	/**
	 * Construct a new {@link MenuToggleButton}.
	 * 
	 * @param menuPanel The element to be animated.
	 */
	public MenuToggleButton(VerticalPanel menuPanel) {
		super();
		
		this.menuPanel = menuPanel;
		
		setText(HIDE_TEXT);
		getElement().setId(ELEMENT_ID);
		addClickHandler(this);
		menuAnimation = new SlideAnimation(menuPanel.getElement(), Direction.RIGHT);
	}

	@Override
	public void onClick(ClickEvent event) {
		if (shown) {
			setText(SHOW_TEXT);
			menuAnimation.doSlideOut(DURATION);
		} else {
			setText(HIDE_TEXT);
			menuAnimation.doSlideIn(DURATION);
		}
		
		// Actually toggle state
		shown = !shown;
	}
	
}
