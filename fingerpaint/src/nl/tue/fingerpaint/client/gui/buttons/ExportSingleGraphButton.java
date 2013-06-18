package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to export the image of the current mixing
 * performance.
 * 
 * @author Group Fingerpaint
 */
public class ExportSingleGraphButton extends FastButton implements PressHandler {
	/**
	 * Reference to the entrypoint. Used to call the export functionality.
	 */
	protected Fingerpaint fp;

	/**
	 * Construct a new button that can be used to export the image of the
	 * current mixing performance.
	 * 
	 * @param parent
	 *            Reference to the entrypoint, used to call the export
	 *            functionality.
	 */
	public ExportSingleGraphButton(Fingerpaint parent) {
		super(FingerpaintConstants.INSTANCE.btnExportGraph());
		this.fp = parent;
		addPressHandler(this);
		addStyleName("panelButton");
		ensureDebugId("exportSingleGraphButton");
	}

	/**
	 * Exports the graph to an external svg file.
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		fp.exportGraph(false);
	}

}
