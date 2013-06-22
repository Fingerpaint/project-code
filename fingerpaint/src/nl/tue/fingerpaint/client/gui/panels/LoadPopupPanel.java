package nl.tue.fingerpaint.client.gui.panels;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.ui.PopupPanel;

/**
 * PopupPanel that can be used to show all available "things" in the local
 * storage, that can be loaded in the application. These "things" can be
 * protocols, distributions, ...
 * 
 * @author Group Fingerpaint
 */
public class LoadPopupPanel extends PopupPanel {

	/**
	 * Classname that is added to the element when this panel is showing a
	 * loading notification.
	 */
	public static final String LOADING_CLASS = "popupPanelLoadingNotification";
	
	/** If the panel is showing a loading indication or not. */
	private boolean isLoading;
	
	/**
	 * <p>
	 * Construct a new {@link LoadPopupPanel} that can be used to show all available "things"
	 * in the local storage.
	 * </p>
	 * 
	 * <p>
	 * <b style="color: red;">Note:</b> the {@link GuiState#loadVerticalPanel} needs to be
	 * initialised when constructing this panel.
	 * </p>
	 * 
	 * @see LoadPopupPanel
	 */
	public LoadPopupPanel() {
		super();
		setModal(true);
		setGlassEnabled(true);
		isLoading = false;
		add(GuiState.loadVerticalPanel);
		ensureDebugId("loadPanel");
	}
	
	/**
	 * Given that the popup is showing, put it at the top left of the screen.
	 */
	public void setTopLeft() {
		setPopupPosition(0, 0);
	}
	
	/**
	 * Return if this panel is currently showing a loading indication or not.
	 * 
	 * @return {@code true} if a loading indication is showing, {@code false}
	 *         otherwise
	 */
	public boolean isLoading() {
		return isLoading;
	}
	
	/**
	 * Clear this panel, put a label indicating that some loading action is
	 * taking place in the panel and show it if it was not visible.
	 */
	public void setIsLoading() {
		setIsLoading(null);
	}
	
	/**
	 * Clear this panel, put a label indicating that some action is
	 * taking place in the panel and show it if it was not visible.
	 * 
	 * @param msg
	 *            The message to show. A default message is used when
	 *            {@code null}. This message indicates that a loading
	 *            action is taking place.
	 */
	public void setIsLoading(String msg) {
		if (msg == null) {
			GuiState.loadLabel.setText(FingerpaintConstants.INSTANCE.lblLoad());
		} else {
			GuiState.loadLabel.setText(msg);
		}
		
		boolean animationsEnabled = isAnimationEnabled();
		setAnimationEnabled(false);
		
		center();
		clear();
		add(GuiState.loadLabel);
		setTopLeft();
		
		setAnimationEnabled(animationsEnabled);
		isLoading = true;
		getElement().addClassName(LOADING_CLASS);
	}

	/**
	 * Put the {@link GuiState#loadVerticalPanel} in this panel
	 * and show/center it. Also removes the loading class and
	 * all related loading stuff from the panel.
	 */
	@Override
	public void show() {
		clear();
		add(GuiState.loadVerticalPanel);
		
		super.show();
		center();
		isLoading = false;
		getElement().removeClassName(LOADING_CLASS);
	}
}
