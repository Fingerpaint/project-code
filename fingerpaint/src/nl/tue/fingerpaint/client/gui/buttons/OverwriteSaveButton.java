package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.gui.panels.NotificationPopupPanel;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

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
		fp.save(GuiState.saveNameTextBox.getText(), true);

		NotificationPopupPanel np = new NotificationPopupPanel(
				FingerpaintConstants.INSTANCE.saveSuccess());
		np.show(GuiState.DEFAULT_TIMEOUT);
		GuiState.overwriteSavePanel.hide();
		
		// Upon successful overwrite, disable the save button.
		GuiState.saveItemPanelButton.setEnabled(false);
	}

}
