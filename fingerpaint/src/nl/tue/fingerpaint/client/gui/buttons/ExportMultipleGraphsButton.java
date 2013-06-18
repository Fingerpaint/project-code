package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to export multiple graphs.
 * 
 * @author Group Fingerpaint
 */
public class ExportMultipleGraphsButton extends FastButton implements PressHandler {
	
	/**
	 * Reference to the entrypoint. Used to export the graphs.
	 */
	protected Fingerpaint fp;
	
	/**
	 * Construct a new button that can be used to export multiple graphs.
	 * 
	 * @param parent
	 *            Reference to the entrypoint, used to export the graphs.
	 */
	public ExportMultipleGraphsButton(Fingerpaint parent) {
		super(FingerpaintConstants.INSTANCE.btnExportGraphs());
		this.fp = parent;
		addPressHandler(this);
		addStyleName("panelButton");
		ensureDebugId("exportMultipleGraphsButton");
	}
	
	/**
	 * Exports the graph to an external svg file.
	 * @param event The event that has fired. 
	 */
	@Override
	public void onPress(PressEvent event) {
		fp.exportGraph(true);
	}

}
