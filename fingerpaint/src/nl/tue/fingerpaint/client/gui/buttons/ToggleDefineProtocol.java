package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.menu.MenuLevelSwitcher;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.ui.Button;

/**
 * ToggleButton that can be used to indicate if a protocol is being defined. If
 * not in default state, then adding a step will execute it immediately.
 * 
 * @author Group Fingerpaint
 */
public class ToggleDefineProtocol extends FastButton implements PressHandler {

	/**
	 * Reference to the model. Used to change if the user is defining a
	 * protocol.
	 */
	protected ApplicationState as;

	/**
	 * Construct a new {@link Button} that can be used to enter a submenu in
	 * which all protocol related actions are grouped.
	 * 
	 * @param appState
	 *            Reference to the model. Used to change if the user is
	 *            defining a protocol.
	 * @see ToggleDefineProtocol
	 */
	public ToggleDefineProtocol(ApplicationState appState) {
		super(FingerpaintConstants.INSTANCE.btnDefProt());
		this.as = appState;
		addPressHandler(this);
		ensureDebugId("ToggleDefineProtocol");
	}

	/**
	 * Toggles the protocol widgets as visible or invisible, and resets the
	 * protocol if the widgets are being hidden.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		// this is set to false in the BackStopDefiningProtocolButton
		as.setIsDefiningProtocol(true);
		MenuLevelSwitcher.showSub1MenuDefineProtocol();
	}
}
