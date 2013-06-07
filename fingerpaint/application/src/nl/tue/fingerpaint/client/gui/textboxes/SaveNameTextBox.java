package nl.tue.fingerpaint.client.gui.textboxes;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.gui.panels.SaveItemPopupPanel;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.TextBox;

/**
 * TextBox that can be used to let the user input a name for an item to save.
 * 
 * @author Group Fingerpaint
 * @see SaveItemPopupPanel
 */
public class SaveNameTextBox extends TextBox implements KeyPressHandler {

	/**
	 * Construct a new {@link TextBox} that can be used to input a name for an
	 * item to save.
	 */
	public SaveNameTextBox() {
		setMaxLength(30);
		
		addKeyPressHandler(this);
		
		ensureDebugId("saveNameTextBox");
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		String text = GuiState.saveNameTextBox.getText();
		String inputCharacter = Character.toString(event.getCharCode());
		int textlength = text.length();
		if (inputCharacter
				.matches("[~`!@#$%^&*()+={}\\[\\]:;\"|\'\\\\<>?,./\\s]")) {
			cancelKey();
		} else if (inputCharacter.matches("[A-Za-z0-9]")) {
			textlength++;
		} else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE) {
			textlength--;
		} else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			GuiState.saveItemPanelButton.click();
		}
		GuiState.saveItemPanelButton.setEnabled(textlength > 0);
	}
}
