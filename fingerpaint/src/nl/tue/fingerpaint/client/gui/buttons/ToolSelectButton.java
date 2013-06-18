package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.menu.MenuLevelSwitcher;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to change the drawing tool.
 * 
 * @author Group Fingerpaint
 */
public class ToolSelectButton extends FastButton implements PressHandler {

	/**
	 * Construct the {@link ToolSelectButton}.
	 */
	public ToolSelectButton() {
		super(FingerpaintConstants.INSTANCE.btnSelectTool());
		addPressHandler(this);
		ensureDebugId("toolSelectButton");
	}

	/**
	 * Shows the popup in which to select the drawing tool.
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		MenuLevelSwitcher.showSub1MenuToolSelector();
	}
}
