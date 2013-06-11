package nl.tue.fingerpaint.client.gui.buttons;

import java.util.Set;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to start a new comparison.
 * 
 * @author Group Fingerpaint
 */
public class NewCompareButton extends Button implements ClickHandler {

	/**
	 * Construct a new button that can be used to start a new comparison. When
	 * clicked, it closes {@link GuiState#comparePopupPanel} and opens
	 * {@link GuiState#compareSelectPopupPanel}.
	 */
	public NewCompareButton() {
		super(FingerpaintConstants.INSTANCE.btnNewCompare());
		addClickHandler(this);
		ensureDebugId("newCompareButton");
	}

	/**
	 * Clears all previously selected results.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
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
