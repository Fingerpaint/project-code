package nl.tue.fingerpaint.client.gui.buttons;

import java.util.logging.Level;
import java.util.logging.Logger;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.gui.panels.NotificationPopupPanel;
import nl.tue.fingerpaint.client.gui.panels.SaveItemPopupPanel;
import nl.tue.fingerpaint.client.gui.textboxes.SaveNameTextBox;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to actually initiate a saving action.
 * 
 * @author Group Fingerpaint
 * @see SaveItemPopupPanel
 * @see SaveNameTextBox
 */
public class SaveItemPanelButton extends Button implements ClickHandler {

	/**
	 * Reference to the entrypoint. Used to save items.
	 */
	protected Fingerpaint fp;

	/**
	 * Construct a new button that can be used to initiate a saving action.
	 * 
	 * @param parent
	 *            Reference to the model, used to save items.
	 */
	public SaveItemPanelButton(Fingerpaint parent) {
		super(FingerpaintConstants.INSTANCE.btnSave());
		this.fp = parent;
		addClickHandler(this);
		setEnabled(false);
		addStyleName("panelButton");
		ensureDebugId("saveItemPanelButton");
	}

	/**
	 * Tries to save the chosen result. If successful, a notification is shown;
	 * otherwise, the overwrite save panel is created.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		int result = fp.save(GuiState.saveNameTextBox.getText(), false);
		
		switch (result) {
		case StorageManager.SAVE_SUCCESSFUL:
			new NotificationPopupPanel(
					FingerpaintConstants.INSTANCE.saveSuccess())
			        .show(GuiState.DEFAULT_TIMEOUT);
			GuiState.saveItemPanel.hide();
			
			// Again disable this button, when saving was successful.
			setEnabled(false);
			break;
		case StorageManager.NOT_INITIALISED_ERROR:
			new NotificationPopupPanel(FingerpaintConstants.INSTANCE.notInitialisedError()).show(GuiState.DEFAULT_TIMEOUT);
			Logger.getLogger("").log(Level.WARNING, "Local storage is not initialised.");
			break;
		case StorageManager.NAME_IN_USE_ERROR:
			GuiState.overwriteButtonsPanel.remove(GuiState.closeSaveButton);
			GuiState.overwriteButtonsPanel.add(GuiState.overwriteSaveButton);
			GuiState.overwriteButtonsPanel.add(GuiState.closeSaveButton);

			GuiState.overwriteSavePanel.center();
			GuiState.saveItemPanel.hide();
			break;
		case StorageManager.QUOTA_EXCEEDED_ERROR:
			new NotificationPopupPanel(FingerpaintConstants.INSTANCE.quotaExceededError()).show(GuiState.DEFAULT_TIMEOUT);
			Logger.getLogger("").log(Level.WARNING, "Local storage is full.");
			break;
		case StorageManager.NONEXISTANT_KEY_ERROR:
			new NotificationPopupPanel(FingerpaintConstants.INSTANCE.nonexistantKeyError()).show(GuiState.DEFAULT_TIMEOUT);
			Logger.getLogger("").log(Level.WARNING, "Key does not exist in local storage.");
			break;
		case StorageManager.UNKNOWN_ERROR:
			new NotificationPopupPanel(FingerpaintConstants.INSTANCE.unknownError()).show(GuiState.DEFAULT_TIMEOUT);
			Logger.getLogger("").log(Level.WARNING, "Unknown error.");
			break;
		}
	}
}
