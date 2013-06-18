package nl.tue.fingerpaint.client.gui.menu;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.gui.animation.Direction;
import nl.tue.fingerpaint.client.gui.animation.MarginAnimation;

/**
 * This class provides some static utility functions to switch to/from a menu on
 * one level to another.
 * 
 * @author Group Fingerpaint
 */
public class MenuLevelSwitcher {

	/**
	 * Duration of the animation to switch between two menu levels in
	 * milliseconds.
	 */
	public static final int MENU_SWITCH_DURATION = 300;
	
	/**
	 * Animation that is used to switch between the levels in the menu.
	 */
	protected static final MarginAnimation MENU_ANIMATION = new MarginAnimation(
			GuiState.menuPanelInnerWrapper.getElement(), Direction.LEFT.getId());
	
	/** The currently shown menu level. */
	protected static int level = 0;
	
	/**
	 * Return the currently shown menu level.
	 * 
	 * @return 0 when main menu is shown, 1 when submenu is shown, et cetera.
	 */
	public static int getCurrentLevel() {
		return level;
	}
	
	/**
	 * Show the given menu level.
	 * 
	 * @param level
	 *            Menu level to show.
	 */
	public static void go(int level) {
		go(level, null);
	}
	
	/**
	 * Show the given menu level.
	 * 
	 * @param level
	 *            Menu level to show.
	 * @param callback
	 *            Callback to call when animation has completed.
	 *            Ignored when {@code null}.
	 */
	public static void go(int level, final AsyncCallback<Boolean> callback) {
		int mainMenuPanelWidth = GuiState.mainMenuPanel.getOffsetWidth();
		if (level >= 0) {
			MENU_ANIMATION.doAnimate(level * -mainMenuPanelWidth,
					MENU_SWITCH_DURATION,
					new AsyncCallback<Boolean>() {
				
				@Override
				public void onSuccess(Boolean result) {
					if (callback != null) {
						callback.onSuccess(result);
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// will not occur
				}
			});
		}
	}
	
	/**
	 * Go up one level.
	 */
	public static void goBack() {
		goBack(null);
	}
	
	/**
	 * Go up one level.
	 * 
	 * @param callback
	 *            Callback to call when the animation has completed.
	 *            Is guaranteed to only be called by
	 *            {@link AsyncCallback#onSuccess(Object)} with {@code true} as
	 *            parameter. Ignored when {@code null}.
	 */
	public static void goBack(AsyncCallback<Boolean> callback) {
		if (level > 0) {
			level--;
			go(level, callback);
		}
	}
	
	/**
	 * Switch to the level 1 menu in which a user can define a protocol.
	 * He/she can also access another submenu where he/she can save, load
	 * and remove protocols.
	 */
	public static void showSub1MenuDefineProtocol() {
		GuiState.subLevel1MenuPanel.clear();
		GuiState.subLevel1MenuPanel.add(GuiState.labelProtocolLabel);
		GuiState.subLevel1MenuPanel.add(GuiState.mixNowButton);
		GuiState.subLevel1MenuPanel.add(GuiState.viewSingleGraphButton);
		GuiState.subLevel1MenuPanel.add(GuiState.sizeProtocolMenuLabel);
		GuiState.subLevel1MenuPanel.add(GuiState.sizeProtocolMenuSpinner);
		GuiState.subLevel1MenuPanel.add(GuiState.nrStepsLabel);
		GuiState.subLevel1MenuPanel.add(GuiState.nrStepsSpinner);
		GuiState.subLevel1MenuPanel.add(GuiState.labelProtocolRepresentation);
		GuiState.subLevel1MenuPanel.add(GuiState.resetProtocolButton);
		GuiState.subLevel1MenuPanel.add(GuiState.protocolsButton);
		GuiState.subLevel1MenuPanel.add(GuiState.backStopDefiningProtocol);
		
		level = 1;
		go(level);
	}
	
	/**
	 * Switch to the level 1 menu in which a user can save, load, remove and
	 * export distributions.
	 */
	public static void showSub1MenuDistributions() {
		GuiState.subLevel1MenuPanel.clear();
		GuiState.subLevel1MenuPanel.add(GuiState.distributionsLabel);
		GuiState.subLevel1MenuPanel.add(GuiState.saveDistributionButton);
		GuiState.subLevel1MenuPanel.add(GuiState.loadInitDistButton);
		GuiState.subLevel1MenuPanel.add(GuiState.removeInitDistButton);
		GuiState.subLevel1MenuPanel.add(GuiState.exportDistributionButton);
		GuiState.subLevel1MenuPanel.add(GuiState.backMenu1Button);
		
		level = 1;
		go(level);
	}
	
	/**
	 * Switch to the level 1 menu in which a user can save, load and remove
	 * results.
	 */
	public static void showSub1MenuResults() {
		GuiState.subLevel1MenuPanel.clear();
		GuiState.subLevel1MenuPanel.add(GuiState.resultsLabel);
		GuiState.subLevel1MenuPanel.add(GuiState.saveResultsButton);
		GuiState.subLevel1MenuPanel.add(GuiState.loadResultsButton);
		GuiState.subLevel1MenuPanel.add(GuiState.removeSavedResultsButton);	
		GuiState.subLevel1MenuPanel.add(GuiState.comparePerformanceButton);
		GuiState.subLevel1MenuPanel.add(GuiState.backMenu1Button);
		
		level = 1;
		go(level);
	}
	
	/**
	 * Switch to the level 1 menu in which a user can change the drawing tool.
	 */
	public static void showSub1MenuToolSelector() {
		GuiState.subLevel1MenuPanel.clear();		
		GuiState.subLevel1MenuPanel.add(GuiState.drawingToolLabel);
		GuiState.subLevel1MenuPanel.add(GuiState.toolMenuToggleColour);
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(GuiState.squareDrawingTool);
		hp.add(GuiState.circleDrawingTool);
		GuiState.subLevel1MenuPanel.add(hp);
		GuiState.subLevel1MenuPanel.add(GuiState.cursorSizeSpinner);
		GuiState.subLevel1MenuPanel.add(GuiState.backMenu1Button);
		
		level = 1;
		go(level);
	}

	/**
	 * Switch to the level 2 menu in which a user can save, load and remove
	 * protocols.
	 */
	public static void showSub2MenuProtocols() {
		GuiState.subLevel2MenuPanel.clear();
		GuiState.subLevel2MenuPanel.add(GuiState.protocolsLabel);
		GuiState.subLevel2MenuPanel.add(GuiState.saveProtocolButton);
		GuiState.subLevel2MenuPanel.add(GuiState.loadProtocolButton);
		GuiState.subLevel2MenuPanel.add(GuiState.removeSavedProtButton);
		GuiState.subLevel2MenuPanel.add(GuiState.backMenu2Button);
		
		level = 2;
		go(level);
	}
	
}
