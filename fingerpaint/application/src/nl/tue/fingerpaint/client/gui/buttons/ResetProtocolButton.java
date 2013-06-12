package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button to reset the current mixing protocol.
 * 
 * @author Group Fingerpaint
 */
public class ResetProtocolButton extends Button implements ClickHandler {
	/**
	 * Reference to the entrypoint. Used to reset the protocol and hide all related
	 * widgets.
	 */
	protected Fingerpaint fp;

	/**
	 * Construct a new button that can be used to reset the current mixing
	 * protocol and hide all related widgets.
	 * 
	 * @param parent
	 *            Reference to the entrypoint, used to reset the current mixing
	 *            protocol and all related widgets.
	 */
	public ResetProtocolButton(Fingerpaint parent) {
		super(FingerpaintConstants.INSTANCE.btnResetProt());
		this.fp = parent;
		addClickHandler(this);
		ensureDebugId("resetProtocolButton");
	}
	/**
	 * Resets the protocol to empty.
	 * @param event The event that has fired.
	 */ 
	@Override
	public void onClick(ClickEvent event) {
		fp.resetProtocol();
	}

}
