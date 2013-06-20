package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;

import java.util.ArrayList;
import java.util.Set;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.ResultStorage;
import nl.tue.fingerpaint.client.storage.StorageManager;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Button that can be used to compare the performance of the selected mixing
 * runs.
 * 
 * @author Group Fingerpaint
 */
public class CompareButton extends FastButton implements PressHandler {
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
		addPressHandler(this);
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
	public void onPress(PressEvent event) {
		// close popup to show we are busy loading
		GuiState.compareSelectPopupPanel.hide();
		GuiState.loadPanel.setIsLoading();
		
		// Now wait a bit until this has happened, before
		// loading a distribution from the storage, as that
		// is very CPU intensive
		Timer runLater = new Timer() {
			@Override
			public void run() {
				loadGraph();
			}
		};
		runLater.schedule(100);
	}
	
	/**
	 * Load wanted results from storage and show a graph, comparing their
	 * performance.
	 */
	private void loadGraph() {
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
						GuiState.loadPanel.hide();
						GuiState.comparePopupPanel.center();
					}
				});
	}
}
