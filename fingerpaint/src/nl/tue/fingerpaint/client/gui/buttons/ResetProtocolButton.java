package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button to reset the current mixing protocol.
 * 
 * @author Group Fingerpaint
 */
public class ResetProtocolButton extends FastButton implements PressHandler {
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
		addPressHandler(this);
		ensureDebugId("resetProtocolButton");
	}
	/**
	 * Resets the protocol to empty.
	 * @param event The event that has fired.
	 */ 
	@Override
	public void onPress(PressEvent event) {
		fp.resetProtocol();
	}

}
