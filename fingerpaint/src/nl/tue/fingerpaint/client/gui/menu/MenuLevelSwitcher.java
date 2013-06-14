package nl.tue.fingerpaint.client.gui.menu;

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
	 * @param level Menu level to show.
	 */
	public static void go(int level) {
		int mainMenuPanelWidth = GuiState.mainMenuPanel.getOffsetWidth();
		if (level >= 0) {
			MENU_ANIMATION.doAnimate(level * -mainMenuPanelWidth, MENU_SWITCH_DURATION);
		}
	}
	
	/**
	 * Go up one level.
	 */
	public static void goBack() {
		if (level > 0) {
			level--;
			go(level);
		}
	}
	
	/**
	 * Switch to the level 1 menu in which a user can save, load, remove and
	 * export distributions.
	 */
	public static void showSub1MenuDistributions() {
		GuiState.subLevel1MenuPanel.clear();
		GuiState.subLevel1MenuPanel.add(GuiState.saveDistributionButton);
		GuiState.subLevel1MenuPanel.add(GuiState.loadInitDistButton);
		GuiState.subLevel1MenuPanel.add(GuiState.removeInitDistButton);
		GuiState.subLevel1MenuPanel.add(GuiState.exportDistributionButton);
		GuiState.subLevel1MenuPanel.add(GuiState.backMenu1Button);
		
		level = 1;
		go(level);
	}
	
	/**
	 * Switch to the level 1 menu in which a user can change the drawing tool.
	 */
	public static void showSub1MenuToolSelector() {
		GuiState.subLevel1MenuPanel.clear();
		GuiState.subLevel1MenuPanel.add(GuiState.squareDrawingTool);
		GuiState.subLevel1MenuPanel.add(GuiState.circleDrawingTool);
		GuiState.subLevel1MenuPanel.add(GuiState.cursorSizeSpinner);
		GuiState.subLevel1MenuPanel.add(GuiState.backMenu1Button);
		
		level = 1;
		go(level);
	}
	
}
