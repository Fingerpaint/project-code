package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.menu.MenuLevelSwitcher;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to go up one level in the menu structure.
 * 
 * @author Group Fingerpaint
 */
public class BackMenuButton extends FastButton implements PressHandler {

	/** Class name for every button of this type. */
	public static final String STYLE_NAME = "backMenuButton";
	
	/**
	 * Construct a new button that can be used to go up one level in the menu
	 * structure, with a default ID.
	 */
	public BackMenuButton() {
		this(null);
	}
	
	/**
	 * Construct a new button that can be used to go up one level in the menu
	 * structure.
	 * 
	 * @param id
	 *            The ID for the button. When {@code null}, "backMenuButton" is
	 *            used as the ID for the element.
	 */
	public BackMenuButton(String id) {
		super(FingerpaintConstants.INSTANCE.btnBack());
		addPressHandler(this);
		getElement().setId((id == null ? "backMenuButton" : id));
		getElement().addClassName(STYLE_NAME);
	}

	/**
	 * Go up one level in the menu.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		MenuLevelSwitcher.goBack();
	}

}
