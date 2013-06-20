package nl.tue.fingerpaint.client.gui.panels;

import nl.tue.fingerpaint.client.gui.GuiState;

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
	 * taking place in the panel and center it.
	 */
	public void setIsLoading() {
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
