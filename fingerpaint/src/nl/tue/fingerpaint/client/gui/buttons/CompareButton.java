package nl.tue.fingerpaint.client.gui.buttons;

import java.util.ArrayList;
import java.util.Set;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.ResultStorage;
import nl.tue.fingerpaint.client.storage.StorageManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to compare the performance of the selected mixing
 * runs.
 * 
 * @author Group Fingerpaint
 */
public class CompareButton extends Button implements ClickHandler {
	/**
	 * Reference to the entrypoint. Used to call the create graph functionality.
	 */
	protected Fingerpaint fp;

	/**
	 * Construct a new button that can be used to compare the performance of the
	 * selected mixing
	 * 
	 * @param parent
	 *            Reference to the entrypoint, used to call the create graph
	 *            functionality.
	 */
	public CompareButton(Fingerpaint parent) {
		super(FingerpaintConstants.INSTANCE.btnCompare());
		this.fp = parent;
		addClickHandler(this);
		addStyleName("panelButton");
		ensureDebugId("compareButton");
	}

	/**
	 * Plots the selected performance results in one graph. Adds the names of
	 * the selected runs to the legend, and their results to the graph, and
	 * finally shows the panel containing the graphs.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<double[]> graphs = new ArrayList<double[]>();
		Set<String> chosenNames = GuiState.compareSelectPopupCellList
				.getSelectionModel().getSelectedSet();
		for (String s : chosenNames) {
			names.add(s);
			ResultStorage rs = StorageManager.INSTANCE.getResult(s);
			if (rs != null) {
				graphs.add(rs.getSegregation());
			}
		}

		GuiState.compareGraphPanel.clear();
		fp.createGraph(GuiState.compareGraphPanel, names, graphs,
				new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Boolean result) {
						GuiState.compareSelectPopupPanel.hide();
						GuiState.comparePopupPanel.center();
					}
				});
	}
}
