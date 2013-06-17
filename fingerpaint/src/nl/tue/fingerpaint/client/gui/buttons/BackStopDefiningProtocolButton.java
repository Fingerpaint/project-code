package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.menu.MenuLevelSwitcher;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to go up one level in the menu structure and stop
 * defining a protocol.
 * 
 * @author Group Fingerpaint
 */
public class BackStopDefiningProtocolButton extends Button implements ClickHandler {

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
		addClickHandler(this);
		ensureDebugId("backStopDefiningProtocolButton");
	}

	/**
	 * Go up one level in the menu and stop defining a protocol.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		as.setIsDefiningProtocol(false);
		MenuLevelSwitcher.goBack();
	}

}
