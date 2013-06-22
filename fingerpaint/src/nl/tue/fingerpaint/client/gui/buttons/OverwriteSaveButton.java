package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.Timer;

/**
 * Button that can be used to overwrite the item that is currently being saved.
 * 
 * @author Group Fingerpaint
 */
public class OverwriteSaveButton extends FastButton implements PressHandler {
	/**
	 * Reference to the entrypoint. Used to overwrite the item that is currently
	 * being saved.
	 */
	protected Fingerpaint fp;

	/**
	 * Construct a new button that can be used to overwrite the item that is
	 * currently being saved. It also shows a dialogue if the save has been
	 * successful and hides the overwrite save panel.
	 * 
	 * @param parent
	 *            Reference to the entrypoint, used to overwrite the item that
	 *            is currently being saved.
	 */
	public OverwriteSaveButton(Fingerpaint parent) {
		super(FingerpaintConstants.INSTANCE.btnOverwrite());
		this.fp = parent;
		addPressHandler(this);
		ensureDebugId("overwriteSaveButton");
	}

	/**
	 * Saves the selected item, shows a notification panel indicating success
	 * and hides the overwite panel.
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		GuiState.loadPanel.setIsLoading(FingerpaintConstants.INSTANCE.lblSaving());
		GuiState.overwriteSavePanel.hide();
		Timer doLater = new Timer() {
			@Override
			public void run() {
				GuiState.saveItemPanelButton.save(true);
				GuiState.loadPanel.hide();
				
				// Upon successful overwrite, disable the save button.
				GuiState.saveItemPanelButton.setEnabled(false);
			}
		};
		doLater.schedule(100);
	}

}
