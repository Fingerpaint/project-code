package nl.tue.fingerpaint.client.gui.buttons;

import java.util.ArrayList;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Button that can be used to compare the performance of previously saved mixing
 * runs.
 * 
 * @author Group Fingerpaint
 */
public class ComparePerformanceButton extends Button implements ClickHandler {
	/**
	 * Construct a new button that can be used to compare the performance of
	 * previously saved mixing runs. When clicked, it opens the
	 * {@link GuiState#compareSelectPopupPanel} pop-up.
	 */
	public ComparePerformanceButton() {
		super(FingerpaintConstants.INSTANCE.btnComparePerfomance());

		addClickHandler(this);
		ensureDebugId("comparePerformanceButton");
	}

	@Override
	public void onClick(ClickEvent event) {
		ArrayList<String> resultNames = (ArrayList<String>) StorageManager.INSTANCE
				.getResults();

		GuiState.compareSelectPopupCellList.setRowCount(resultNames.size());

		// Push the data into the widget.
		GuiState.compareSelectPopupCellList.setRowData(0, resultNames);

		GuiState.compareSelectPopupPanel
				.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
					public void setPosition(int offsetWidth, int offsetHeight) {
						GuiState.compareSelectPopupPanel.center();
					}
				});
	}
}
