package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to export the image of the current mixing
 * performance.
 * 
 * @author Group Fingerpaint
 */
public class ExportSingleGraphButton extends Button implements ClickHandler {
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
		addClickHandler(this);
		addStyleName("panelButton");
		ensureDebugId("exportSingleGraphButton");
	}

	/**
	 * Exports the graph to an external svg file.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		fp.exportGraph(false);
	}

}
