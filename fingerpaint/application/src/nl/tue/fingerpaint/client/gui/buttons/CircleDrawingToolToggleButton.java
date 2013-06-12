package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.model.drawingtool.CircleDrawingTool;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * {@link ToggleButton} that is used to select the circular drawing tool.
 * 
 * @author Group Fingerpaint
 */
public class CircleDrawingToolToggleButton extends ToggleButton implements ClickHandler {

	/** Reference to the "parent" class. Used for executing mixing runs. */
	private Fingerpaint fp;
	/** Reference to the state of the application, to update stuff there. */
	private ApplicationState as;

	/**
	 * Construct the {@link CircleDrawingTool}.
	 * 
	 * @param parent  A reference to the Fingerpaint class.
	 * @param appState Reference to the model that holds the state of the application.
	 */
	public CircleDrawingToolToggleButton(Fingerpaint parent,
			ApplicationState appState) {
		super(FingerpaintConstants.INSTANCE.btnCircleDraw(), FingerpaintConstants.INSTANCE.btnCircleDraw());
		
		this.fp = parent;
		this.as = appState;

		addClickHandler(this);
		ensureDebugId("circleDrawingTool");
	}

	/**
	 * Changes to the circular drawing tool with the correct cursor size.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		if (!isDown()) {
			setDown(true);
		} else {
			as.getGeometry().setDrawingTool(
					new CircleDrawingTool(fp.getCursorSize()));

			GuiState.squareDrawingTool.setDown(false);
		}
	}

}
