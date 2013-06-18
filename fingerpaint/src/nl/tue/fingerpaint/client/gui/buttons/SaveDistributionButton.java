package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;

/**
 * Button to save an initial concentration distribution.
 * 
 * @author Group Fingerpaint
 */
public class SaveDistributionButton extends FastButton implements PressHandler {

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
		addPressHandler(this);
		ensureDebugId("saveDistributionButton");
	}
	/**
	 * Creates the save distribution popup panel.
	 * @param event The event that has fired.
	 */ 
	@Override
	public void onPress(PressEvent event) {
		fp.setLastClicked(StorageManager.KEY_INITDIST);
		fp.showSavePanel();
	}

}
