package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;

import java.util.Set;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to start a new comparison.
 * 
 * @author Group Fingerpaint
 */
public class NewCompareButton extends FastButton implements PressHandler {

	/**
	 * Construct a new button that can be used to start a new comparison. When
	 * clicked, it closes {@link GuiState#comparePopupPanel} and opens
	 * {@link GuiState#compareSelectPopupPanel}.
	 */
	public NewCompareButton() {
		super(FingerpaintConstants.INSTANCE.btnNewCompare());
		addPressHandler(this);
		addStyleName("panelButton");
		ensureDebugId("newCompareButton");
	}

	/**
	 * Clears all previously selected results.
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		Set<String> selected = GuiState.compareSelectPopupCellList
				.getSelectionModel().getSelectedSet();
		for (String s : selected) {
			GuiState.compareSelectPopupCellList.getSelectionModel()
					.setSelected(s, false);
		}
		GuiState.comparePopupPanel.hide();
		GuiState.compareSelectPopupPanel.show();
	}

}
