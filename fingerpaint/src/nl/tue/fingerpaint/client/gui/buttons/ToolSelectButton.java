package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Button that can be used to change the drawing tool.
 * 
 * @author Group Fingerpaint
 */
public class ToolSelectButton extends Button implements ClickHandler {

	/**
	 * Construct the {@link ToolSelectButton}.
	 */
	public ToolSelectButton() {
		super(FingerpaintConstants.INSTANCE.btnSelectTool());
		addClickHandler(this);
		ensureDebugId("toolSelectButton");
	}

	/**
	 * Shows the popup in which to select the drawing tool.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		GuiState.toolSelector
				.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
					public void setPosition(int offsetWidth, int offsetHeight) {
						int left = (Window.getClientWidth() - offsetWidth - 75);
						int top = 40;
						GuiState.toolSelector.setPopupPosition(left, top);
					}
				});
	}
}
