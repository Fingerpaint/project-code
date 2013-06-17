package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.menu.MenuLevelSwitcher;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to enter the submenu with protocols related
 * actions.
 * 
 * @author Group Fingerpaint
 */
public class ProtocolsButton extends Button implements ClickHandler {

	/**
	 * Construct a new button that can be used to enter the submenu with
	 * distribution related actions.
	 */
	public ProtocolsButton() {
		super(FingerpaintConstants.INSTANCE.btnProtocols());
		addClickHandler(this);
		ensureDebugId("protocolsButton");
	}

	/**
	 * Enter the submenu with distribution related actions.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		MenuLevelSwitcher.showSub2MenuProtocols();
	}

}
