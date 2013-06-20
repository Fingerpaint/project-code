package nl.tue.fingerpaint.client.gui.menu;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.gui.animation.Direction;
import nl.tue.fingerpaint.client.gui.animation.MarginAnimation;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;

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
	public static void go(final int level, final AsyncCallback<Boolean> callback) {
		int mainMenuPanelWidth = GuiState.menuPanels[0].getOffsetWidth();
		
		// clear height of menupanel that we animate to
		GuiState.menuPanels[level].getElement().getStyle().clearHeight();
		
		if (level >= 0) {
			MENU_ANIMATION.doAnimate(level * -mainMenuPanelWidth,
					MENU_SWITCH_DURATION,
					new AsyncCallback<Boolean>() {
				
				@Override
				public void onSuccess(Boolean result) {
					for (int i = 0; i < GuiState.menuPanels.length; i++) {
						if (i != level) {
							GuiState.menuPanels[i].getElement().getStyle().
									setHeight(10, Unit.PX);
						}
					}
					
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
		GuiState.menuPanels[1].clear();
		GuiState.menuPanels[1].add(GuiState.labelProtocolLabel);
		GuiState.menuPanels[1].add(GuiState.mixNowButton);
		GuiState.menuPanels[1].add(GuiState.viewSingleGraphButton);
		GuiState.menuPanels[1].add(GuiState.sizeProtocolMenuLabel);
		GuiState.menuPanels[1].add(GuiState.sizeProtocolMenuSpinner);
		GuiState.menuPanels[1].add(GuiState.nrStepsLabel);
		GuiState.menuPanels[1].add(GuiState.nrStepsSpinner);
		GuiState.menuPanels[1].add(GuiState.labelProtocolRepresentation);
		GuiState.menuPanels[1].add(GuiState.resetProtocolButton);
		GuiState.menuPanels[1].add(GuiState.protocolsButton);
		GuiState.menuPanels[1].add(GuiState.backStopDefiningProtocol);
		
		level = 1;
		go(level);
	}
	
	/**
	 * Switch to the level 1 menu in which a user can save, load, remove and
	 * export distributions.
	 */
	public static void showSub1MenuDistributions() {
		GuiState.menuPanels[1].clear();
		GuiState.menuPanels[1].add(GuiState.distributionsLabel);
		GuiState.menuPanels[1].add(GuiState.saveDistributionButton);
		GuiState.menuPanels[1].add(GuiState.loadInitDistButton);
		GuiState.menuPanels[1].add(GuiState.removeInitDistButton);
		GuiState.menuPanels[1].add(GuiState.exportDistributionButton);
		GuiState.menuPanels[1].add(GuiState.backMenu1Button);
		
		level = 1;
		go(level);
	}
	
	/**
	 * Switch to the level 1 menu in which a user can save, load and remove
	 * results.
	 */
	public static void showSub1MenuResults() {
		GuiState.menuPanels[1].clear();
		GuiState.menuPanels[1].add(GuiState.resultsLabel);
		GuiState.menuPanels[1].add(GuiState.saveResultsButton);
		GuiState.menuPanels[1].add(GuiState.loadResultsButton);
		GuiState.menuPanels[1].add(GuiState.removeSavedResultsButton);	
		GuiState.menuPanels[1].add(GuiState.comparePerformanceButton);
		GuiState.menuPanels[1].add(GuiState.backMenu1Button);
		
		level = 1;
		go(level);
	}
	
	/**
	 * Switch to the level 1 menu in which a user can change the drawing tool.
	 */
	public static void showSub1MenuToolSelector() {
		GuiState.menuPanels[1].clear();		
		GuiState.menuPanels[1].add(GuiState.drawingToolLabel);
		GuiState.menuPanels[1].add(GuiState.toolMenuToggleColour);
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(GuiState.squareDrawingTool);
		hp.add(GuiState.circleDrawingTool);
		GuiState.menuPanels[1].add(hp);
		GuiState.menuPanels[1].add(GuiState.cursorSizeSpinner);
		GuiState.menuPanels[1].add(GuiState.backMenu1Button);
		
		level = 1;
		go(level);
	}

	/**
	 * Switch to the level 2 menu in which a user can save, load and remove
	 * protocols.
	 */
	public static void showSub2MenuProtocols() {
		GuiState.menuPanels[2].clear();
		GuiState.menuPanels[2].add(GuiState.protocolsLabel);
		GuiState.menuPanels[2].add(GuiState.saveProtocolButton);
		GuiState.menuPanels[2].add(GuiState.loadProtocolButton);
		GuiState.menuPanels[2].add(GuiState.removeSavedProtButton);
		GuiState.menuPanels[2].add(GuiState.backMenu2Button);
		
		level = 2;
		go(level);
	}
	
}
