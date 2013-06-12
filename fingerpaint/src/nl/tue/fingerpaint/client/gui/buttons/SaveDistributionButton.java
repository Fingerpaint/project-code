package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button to save an initial concentration distribution.
 * 
 * @author Group Fingerpaint
 */
public class SaveDistributionButton extends Button implements ClickHandler {

	/**
	 * Reference to the entrypoint. Used to change which save button has been clicked
	 * last.
	 */
	protected Fingerpaint fp;

	/**
	 * Construct a new button that can be used to save a concentration
	 * distribution. When clicked, it will open the save item panel.
	 * 
	 * @param parent
	 *            Reference to the entrypoint, used to change which save button has
	 *            been clicked last.
	 */
	public SaveDistributionButton(Fingerpaint parent) {
		super(FingerpaintConstants.INSTANCE.btnSaveDist());
		this.fp = parent;
		addClickHandler(this);
		ensureDebugId("saveDistributionButton");
	}
	/**
	 * Creates the save distribution popup panel.
	 * @param event The event that has fired.
	 */ 
	@Override
	public void onClick(ClickEvent event) {
		fp.setLastClicked(StorageManager.KEY_INITDIST);
		fp.showSavePanel();
	}

}
