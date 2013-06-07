package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to close the save results or overwrite pop-up panel.
 * 
 * @author Group Fingerpaint
 */
public class CloseSaveButton extends Button implements ClickHandler {
	/**
	 * Construct a new button that can be used to close the save results or
	 * overwrite pop-up panel.
	 */
	public CloseSaveButton() {
		super(FingerpaintConstants.INSTANCE.btnClose());

		addClickHandler(this);
		ensureDebugId("closeSaveButton");
	}

	@Override
	public void onClick(ClickEvent event) {
		// Hide both popup panels if the OK button was pressed. Hide only the
		// second panel if the cancel button was pressed.
		GuiState.overwriteSavePanel.hide();
		GuiState.overwriteSavePanel.remove(GuiState.overwriteSaveButton);
		GuiState.saveItemPanel.show();
		GuiState.saveNameTextBox.setSelectionRange(0,
				GuiState.saveNameTextBox.getText().length());
		GuiState.saveNameTextBox.setFocus(true);
	}

}
