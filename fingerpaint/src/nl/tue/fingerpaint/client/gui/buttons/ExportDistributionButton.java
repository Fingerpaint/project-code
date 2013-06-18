package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.FileExporter;

/**
 * Button that can be used to export a distribution.
 * 
 * @author Group Fingerpaint
 */
public class ExportDistributionButton extends FastButton implements PressHandler {
	
	/**
	 * Reference to the entrypoint. Used to export the graphs.
	 */
	protected ApplicationState as;
	
	/**
	 * Construct a new button that can be used to export multiple graphs.
	 * 
	 * @param parent
	 *            Reference to the entrypoint, used to export the graphs.
	 */
	public ExportDistributionButton(ApplicationState parent) {
		super(FingerpaintConstants.INSTANCE.btnExportDist());
		this.as = parent;
		addPressHandler(this);
		ensureDebugId("ExportDistributionButton");
	}
	
	/**
	 * Exports the currently shown distribution to an external svg file. 
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		String svg = as.getGeometry().getCanvasImage();
		FileExporter.exportSvgImage(svg);	
	}
}
