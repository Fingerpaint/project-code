package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.menu.MenuLevelSwitcher;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to go up one level in the menu structure and stop
 * defining a protocol.
 * 
 * @author Group Fingerpaint
 */
public class BackStopDefiningProtocolButton extends FastButton implements PressHandler {

	/**
	 * Reference to the model. Used to change if the user is defining a
	 * protocol.
	 */
	protected ApplicationState as;
	
	/**
	 * Construct a new button that can be used to go up one level in the menu
	 * structure and stop defining a protocol.
	 * 
	 * @param appState
	 *            Reference to the model. Used to change if the user is
	 *            defining a protocol.
	 */
	public BackStopDefiningProtocolButton(ApplicationState appState) {
		super(FingerpaintConstants.INSTANCE.btnStopDefProt());
		this.as = appState;
		addPressHandler(this);
		ensureDebugId("backStopDefiningProtocolButton");
	}

	/**
	 * Go up one level in the menu and stop defining a protocol.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		as.setIsDefiningProtocol(false);
		MenuLevelSwitcher.goBack();
	}

}
