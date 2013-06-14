package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.menu.MenuLevelSwitcher;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to go up one level in the menu structure.
 * 
 * @author Group Fingerpaint
 */
public class BackMenuButton extends Button implements ClickHandler {

	/**
	 * Construct a new button that can be used to go up one level in the menu
	 * structure.
	 */
	public BackMenuButton() {
		super(FingerpaintConstants.INSTANCE.btnBack());
		addClickHandler(this);
		ensureDebugId("backMenuButton");
	}

	/**
	 * Go up one level in the menu.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		MenuLevelSwitcher.goBack();
	}

}
