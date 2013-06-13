package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.gui.panels.NotificationPopupPanel;
import nl.tue.fingerpaint.client.gui.panels.SaveItemPopupPanel;
import nl.tue.fingerpaint.client.gui.textboxes.SaveNameTextBox;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

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
		ensureDebugId("saveItemPanelButton");
	}

	/**
	 * Tries to save the chosen result. If successful, a notification is shown;
	 * otherwise, the overwrite save panel is created.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		boolean success = fp.save(GuiState.saveNameTextBox.getText(), false);
		if (success) {
			NotificationPopupPanel np = new NotificationPopupPanel(
					FingerpaintConstants.INSTANCE.saveSuccess());
			np.show(GuiState.DEFAULT_TIMEOUT);
			GuiState.saveItemPanel.hide();
			
			// Again disable this button, when saving was successful.
			setEnabled(false);
		} else {
			GuiState.overwriteButtonsPanel.remove(GuiState.closeSaveButton);
			GuiState.overwriteButtonsPanel.add(GuiState.overwriteSaveButton);
			GuiState.overwriteButtonsPanel.add(GuiState.closeSaveButton);

			GuiState.overwriteSavePanel.center();
			GuiState.saveItemPanel.hide();
		}
	}

}
