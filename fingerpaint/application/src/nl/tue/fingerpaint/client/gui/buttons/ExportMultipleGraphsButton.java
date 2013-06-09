package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to export multiple graphs.
 * 
 * @author Group Fingerpaint
 */
public class ExportMultipleGraphsButton extends Button implements ClickHandler {
	
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
		addClickHandler(this);
		ensureDebugId("exportMultipleGraphsButton");
	}
	
	@Override
	public void onClick(ClickEvent event) {
		fp.exportGraph();
	}

}
