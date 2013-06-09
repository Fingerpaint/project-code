package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to close a panel in which "things" from the local
 * storage can be selected. These "things" may be protocols, distributions, ...
 * 
 * @author Group Fingerpaint
 */
public class CloseLoadButton extends Button implements ClickHandler {

	/**
	 * Construct a new button that can be used to close a popup panel.
	 */
	public CloseLoadButton() {
		super(FingerpaintConstants.INSTANCE.btnClose());
		addClickHandler(this);
		ensureDebugId("closeLoadButton");
	}

	@Override
	public void onClick(ClickEvent event) {
		GuiState.loadPanel.removeFromParent();
	}

}
