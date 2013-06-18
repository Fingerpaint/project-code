package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.model.drawingtool.SquareDrawingTool;
import nl.tue.fingerpaint.shared.utils.Colour;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * {@link ToggleButton} that is used to select the square drawing tool.
 * 
 * @author Group Fingerpaint
 */
public class SquareDrawingToolToggleButton extends ToggleButton implements ClickHandler {
	
	/** Reference to the "parent" class. Used for executing mixing runs. */
	private Fingerpaint fp;
	/** Reference to the state of the application, to update stuff there. */
	private ApplicationState as;

	/**
	 * Construct the {@link SquareDrawingTool}.
	 * 
	 * @param parent  A reference to the Fingerpaint class.
	 * @param appState Reference to the model that holds the state of the application.
	 */
	public SquareDrawingToolToggleButton(Fingerpaint parent,
			ApplicationState appState) {
		super("", "");
		this.fp = parent;
		this.as = appState;
		
		// Initially, the square drawing tool is selected
		setDown(true);
		
		// Initialise to default foreground colour
		setColour(Colour.BLACK);
		
		addClickHandler(this);
		getElement().setId("squareDrawingTool");
	}

	/**
	 * Toggles the drawing tool to square, with the desired size.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		if (!isDown()) {
			setDown(true);
		} else {
			as.getGeometry().setDrawingTool(
					new SquareDrawingTool(fp.getCursorSize()));

			GuiState.circleDrawingTool.setDown(false);
			// Update faces of all buttons, because that is changed now
			GuiState.toolMenuToggleColour.update(ToggleColourButton.UPDATE_BOTH);
		}
	}
	
	/**
	 * Change the colour of the tool in the button.
	 * 
	 * @param toolColour
	 *            The (new) colour for the tool in the button.
	 *            When {@code null}, the colour will be reset and thus,
	 *            the default colour will be used.
	 */
	public void setColour(Colour toolColour) {
		NodeList<Element> children = getElement().getElementsByTagName("div");
		for (int i = 0; i < children.getLength(); i++) {
			if (children.getItem(i).getClassName().equals("html-face")) {
				if (toolColour != null) {
					children.getItem(i).getStyle().setBackgroundColor(toolColour.toHexString());
				} else {
					children.getItem(i).getStyle().clearBackgroundColor();
				}
			}
		}
	}
}
