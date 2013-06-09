package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.gui.panels.NotificationPopupPanel;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to overwrite the item that is currently being saved.
 * 
 * @author Group Fingerpaint
 */
public class OverwriteSaveButton extends Button implements ClickHandler {
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
		addClickHandler(this);
		ensureDebugId("overwriteSaveButton");
	}

	@Override
	public void onClick(ClickEvent event) {
		fp.save(GuiState.saveNameTextBox.getText(), true);

		NotificationPopupPanel np = new NotificationPopupPanel(
				FingerpaintConstants.INSTANCE.saveSuccess());
		np.show(GuiState.SAVE_SUCCESS_TIMEOUT);
		GuiState.overwriteSavePanel.hide();
		
		// Upon successful overwrite, disable the save button.
		GuiState.saveItemPanelButton.setEnabled(false);
	}

}
