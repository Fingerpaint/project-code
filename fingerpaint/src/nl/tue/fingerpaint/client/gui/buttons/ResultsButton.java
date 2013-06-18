package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.menu.MenuLevelSwitcher;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to enter the submenu with results related
 * actions.
 * 
 * @author Group Fingerpaint
 */
public class ResultsButton extends FastButton implements PressHandler {

	/**
	 * Construct a new button that can be used to enter the submenu with
	 * distribution related actions.
	 */
	public ResultsButton() {
		super(FingerpaintConstants.INSTANCE.btnResults());
		addPressHandler(this);
		ensureDebugId("resultsButton");
	}

	/**
	 * Enter the submenu with distribution related actions.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		MenuLevelSwitcher.showSub1MenuResults();
	}

}
