package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.gui.panels.NotificationPopupPanel;
import nl.tue.fingerpaint.client.gui.panels.SaveItemPopupPanel;
import nl.tue.fingerpaint.client.gui.textboxes.SaveNameTextBox;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;

import com.google.gwt.user.client.Timer;

/**
 * Button that can be used to actually initiate a saving action.
 * 
 * @author Group Fingerpaint
 * @see SaveItemPopupPanel
 * @see SaveNameTextBox
 */
public class SaveItemPanelButton extends FastButton implements PressHandler {

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
		addPressHandler(this);
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
	public void onPress(PressEvent event) {
		GuiState.loadPanel.setIsLoading(FingerpaintConstants.INSTANCE.lblSaving());
		GuiState.saveItemPanel.hide();
		Timer doLater = new Timer() {
			@Override
			public void run() {
				save(false);
				GuiState.loadPanel.hide();
			}
		};
		doLater.schedule(100);
	}
	
	/**
	 * Save the current protocol, distribution or mixing results, depending on
	 * which save button was pressed last. The result of this saving action
	 * is handled nicely with popups, including asking the user what to do
	 * in case a name already exists. When {@code canOverwrite} is true, an
	 * existing item will be overwritten without asking the user anything.
	 * 
	 * @param canOverwrite
	 *            If the user should be asked what to do when a saved item
	 *            with the given name already exists or not.
	 */
	public void save(boolean canOverwrite) {
		int result = fp.save(GuiState.saveNameTextBox.getText(), canOverwrite);
		
		switch (result) {
			case StorageManager.SAVE_SUCCESSFUL:
				new NotificationPopupPanel(
						FingerpaintConstants.INSTANCE.saveSuccess())
				        .show(GuiState.DEFAULT_TIMEOUT);
				
				// Again disable this button, when saving was successful.
				setEnabled(false);
				break;
			case StorageManager.NOT_INITIALISED_ERROR:
				new NotificationPopupPanel(FingerpaintConstants.INSTANCE.
						notInitialisedError(), NotificationPopupPanel.
						TYPE_ERROR)
					.show(GuiState.DEFAULT_TIMEOUT);
				Logger.getLogger("").log(Level.WARNING, "Local storage is not initialised.");
				break;
			case StorageManager.NAME_IN_USE_ERROR:
				new NotificationPopupPanel(FingerpaintConstants.INSTANCE.
						nameInUseError(), NotificationPopupPanel.
						TYPE_ERROR)
					.show(GuiState.DEFAULT_TIMEOUT);
				GuiState.overwriteButtonsPanel.remove(GuiState.closeSaveButton);
				GuiState.overwriteButtonsPanel.add(GuiState.overwriteSaveButton);
				GuiState.overwriteButtonsPanel.add(GuiState.closeSaveButton);
	
				GuiState.overwriteSavePanel.center();
				GuiState.saveItemPanel.hide();
				break;
			case StorageManager.QUOTA_EXCEEDED_ERROR:
				new NotificationPopupPanel(FingerpaintConstants.INSTANCE.
						quotaExceededError(), NotificationPopupPanel.
						TYPE_ERROR)
					.show(GuiState.DEFAULT_TIMEOUT);
				Logger.getLogger("").log(Level.WARNING, "Local storage is full.");
				break;
			case StorageManager.NONEXISTANT_KEY_ERROR:
				new NotificationPopupPanel(FingerpaintConstants.INSTANCE.
						nonexistantKeyError(), NotificationPopupPanel.
						TYPE_ERROR)
					.show(GuiState.DEFAULT_TIMEOUT);
				Logger.getLogger("").log(Level.WARNING, "Key does not exist in local storage.");
				break;
			case StorageManager.UNKNOWN_ERROR:
				new NotificationPopupPanel(FingerpaintConstants.INSTANCE.
						unknownError(), NotificationPopupPanel.
						TYPE_ERROR)
					.show(GuiState.DEFAULT_TIMEOUT);
				Logger.getLogger("").log(Level.WARNING, "Unknown error.");
				break;
		}
	}
}
