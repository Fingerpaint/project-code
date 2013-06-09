package nl.tue.fingerpaint.client.gui.checkboxes;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;

/**
 * CheckBox that can be used to indicate if a protocol is being defined. If not
 * checked, then adding a step will execute it immediately.
 * 
 * @author Group Fingerpaint
 */
public class DefineProtocolCheckBox extends CheckBox implements ClickHandler {

	/**
	 * Reference to the entrypoint. Used to change the visibility of the
	 * protocol widgets.
	 */
	protected Fingerpaint fp;

	/**
	 * Construct a new {@link CheckBox} that can be used to indicate if a
	 * protocol is being defined.
	 * 
	 * @param parent
	 *            Reference to the entrypoint, used to change the visibility of
	 *            the protocol widgets.
	 * @see DefineProtocolCheckBox
	 */
	public DefineProtocolCheckBox(Fingerpaint parent) {
		super(FingerpaintConstants.INSTANCE.cbDefProt());
		this.fp = parent;
		addClickHandler(this);
		ensureDebugId("defineProtocolCheckbox");
	}

	@Override
	public void onClick(ClickEvent event) {
		if (GuiState.defineProtocolCheckBox.getValue()) {
			fp.setProtocolWidgetsVisible(true);
		} else {
			fp.resetProtocol();
			fp.setProtocolWidgetsVisible(false);
		}
	}

}
