package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.menu.MenuLevelSwitcher;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to go up one level in the menu structure.
 * 
 * @author Group Fingerpaint
 */
public class BackMenuButton extends FastButton implements PressHandler {

	/**
	 * Construct a new button that can be used to go up one level in the menu
	 * structure.
	 */
	public BackMenuButton() {
		super(FingerpaintConstants.INSTANCE.btnBack());
		addPressHandler(this);
		ensureDebugId("backMenuButton");
	}

	/**
	 * Go up one level in the menu.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		MenuLevelSwitcher.goBack();
	}

}
