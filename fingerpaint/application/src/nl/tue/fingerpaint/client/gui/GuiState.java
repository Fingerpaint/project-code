package nl.tue.fingerpaint.client.gui;

import nl.tue.fingerpaint.client.model.MixingStep;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * <p>
 * A class that contains references to all the GUI elements used in the
 * Fingerpaint application.
 * </p>
 * <p>
 * All widgets that can be initialised, must be initialised in this class.
 * </p>
 * 
 * @author Group Fingerpaint
 */
public class GuiState {
	// --- PUBLIC GLOBALS -----------------------------------------------------
	/** The ID for the loading panel. */
	public static final String LOADINGPANEL_ID = "loading-overlay";

	/** The ID for the message to be displayed in the loading panel. */
	public static final String LOADINGPANEL_MESSAGE_ID = "loading-overlay-message";

	/** The default value of {@link #nrStepsSpinner}. */
	public static final double NRSTEPS_DEFAULT = 1.0;
	/** The rate of {@link #nrStepsSpinner}. */
	public static final double NRSTEPS_RATE = 1.0;
	/** The minimum value of {@link #nrStepsSpinner}. */
	public static final double NRSTEPS_MIN = 1.0;
	/** The maximum value of {@link #nrStepsSpinner}. */
	public static final double NRSTEPS_MAX = 50.0;

	/**
	 * Stores how long in milliseconds a SAVE_SUCCESS_MESSAGE should be shown in
	 * a NotificationPanel.
	 */
	public static final int SAVE_SUCCESS_TIMEOUT = 2000;

	// --- PROTECTED GLOBALS --------------------------------------------------
	/*
	 * The below initialisation parameters for the cursorSizeSpinner represent
	 * cursor pixels.
	 */
	// TODO: determine good value for MIN and MAX.
	/** The default value of {@link #cursorSizeSpinner}. */
	protected static final double CURSOR_DEFAULT = 3.0;
	/** The rate of {@link #cursorSizeSpinner}. */
	protected static final double CURSOR_RATE = 1.0;
	/** The minimum value of {@link #cursorSizeSpinner}. */
	protected static final double CURSOR_MIN = 1.0;
	/** The maximum value of {@link #cursorSizeSpinner}. */
	protected static final double CURSOR_MAX = 50.0;

	// --- LOADING APPLICATION WIDGETS ----------------------------------------
	/**
	 * Panel that covers the entire application and blocks the user from //
	 * accessing other features.
	 */
	public static FlowPanel loadingPanel = new FlowPanel();

	/**
	 * The message to be shown during the loading animation of the loading
	 * panel.
	 */
	public static Label loadingPanelMessage = new Label();

	// --- MENU WIDGETS -------------------------------------------------------
	/** Vertical panel to contain all menu items. */
	public static VerticalPanel menuPanel = new VerticalPanel();
	/** Wrapper for the {@link #menuPanel}, used in animation. */
	public static SimplePanel menuPanelWrapper = new SimplePanel();

	/** Button to toggle whether the menu is visible. */
	public static MenuToggleButton menuToggleButton = new MenuToggleButton(
			menuPanelWrapper);

	// --- DRAWING TOOL WIDGETS -----------------------------------------------
	/**
	 * Pop-up panel which contains options for selecting a different drawing
	 * tool.
	 */
	public static PopupPanel toolSelector = new PopupPanel(true);

	/**
	 * Panel in the pop-up panel to separate the tool selection and size
	 * selection options for the drawing tool.
	 */
	public static HorizontalPanel popupPanelPanel = new HorizontalPanel();

	/** Panel in the pop-up panel that contains the different drawing tools. */
	public static VerticalPanel popupPanelMenu = new VerticalPanel();

	/** Numberspinner to change the size of the drawing tool. */
	public static NumberSpinner cursorSizeSpinner = new NumberSpinner(
			CURSOR_DEFAULT, CURSOR_RATE, CURSOR_MIN, CURSOR_MAX, true);

	/** Button to toggle between black and white drawing colour. */
	public static ToggleColourButton toggleColor;

	/** Button to change the shape of the selected drawing tool. */
	// TODO: Change this to a button on which the current tool is drawn
	public static Button toolSelectButton = new Button(
			FingerpaintConstants.INSTANCE.btnSelectTool());

	/** Button to select the square-shaped drawing tool. */
	// TODO: Change this to a button on which a square is drawn
	public static ToggleButton squareDrawingTool = new ToggleButton(
			FingerpaintConstants.INSTANCE.btnSquareDraw(),
			FingerpaintConstants.INSTANCE.btnSquareDraw());

	/** Button to select the circle-shaped drawing tool. */
	// TODO: Change this to a button on which a circle is drawn
	public static ToggleButton circleDrawingTool = new ToggleButton(
			FingerpaintConstants.INSTANCE.btnCircleDraw(),
			FingerpaintConstants.INSTANCE.btnCircleDraw());

	// --- INITIAL DISTRIBUTION WIDGETS ---------------------------------------
	/** Button to save an initial concentration distribution. */
	public static Button saveDistributionButton = new Button(
			FingerpaintConstants.INSTANCE.btnSaveDist());

	/** Button to load an initial concentration distribution. */
	public static Button loadInitDistButton = new Button(
			FingerpaintConstants.INSTANCE.btnLoadDist());

	/** Button to reset the current distribution to completely white. */
	public static Button resetDistButton = new Button(
			FingerpaintConstants.INSTANCE.btnResetDist());

	// -- SAVE MIXING RESULTS WIDGETS -----------------------------------------
	/** Button to save the current mixing result. */
	public static Button saveResultsButton = new Button(
			FingerpaintConstants.INSTANCE.btnSaveResults());

	// --- REMOVE RESULTS WIDGETS ---------------------------------------------
	/** Pop-up panel to handle the removal of results. */
	public static PopupPanel removeResultsPanel = new PopupPanel();

	/** Vertical panel to hold the flextable and close button. */
	public static VerticalPanel removeResultsVerticalPanel = new VerticalPanel();

	/** Flextable to hold all the result entries. */
	public static FlexTable resultsFlexTable = new FlexTable();

	/** Button to remove previously saved mixing results. */
	public static Button removeSavedResultsButton = new Button(
			FingerpaintConstants.INSTANCE.btnRemoveResults());

	/** Button to close the remove results pop-up panel. */
	public static Button closeResultsButton = new Button(
			FingerpaintConstants.INSTANCE.btnClose());

	// --- MIXING PROTOCOL WIDGETS --------------------------------------------
	/** Container to hold all the widgets related to a mixing protocol. */
	public static PopupPanel protocolPanelContainer = new PopupPanel();

	/**
	 * The numberspinner to define how many times the mixing protocol is
	 * executed.
	 */
	public static NumberSpinner nrStepsSpinner = new NumberSpinner(
			NRSTEPS_DEFAULT, NRSTEPS_RATE, NRSTEPS_MIN, NRSTEPS_MAX, true);

	/** Button to save the current mixing protocol. */
	public static Button saveProtocolButton = new Button(
			FingerpaintConstants.INSTANCE.btnSaveProt());

	/** Button to load a mixing protocol. */
	public static Button loadProtocolButton = new Button(
			FingerpaintConstants.INSTANCE.btnLoadProt());

	/** Button that resets the current mixing protocol when pressed. */
	public static Button resetProtocolButton = new Button(
			FingerpaintConstants.INSTANCE.btnResetProt());

	/**
	 * Button that executes the current mixing run (initial distribution and
	 * mixing protocol) when pressed.
	 */
	public static Button mixNowButton = new Button(
			FingerpaintConstants.INSTANCE.btnMixNow());

	/**
	 * Label to be displayed above the {@link #nrStepsSpinner}, to explain its
	 * purpose.
	 */
	public static Label nrStepsLabel = new Label(
			FingerpaintConstants.INSTANCE.lblNrSteps());

	/**
	 * Label that shows the textual representation of the current mixing
	 * protocol.
	 */
	public static Label labelProtocolRepresentation = new Label();

	/**
	 * Label to be displayed above the protocol-related buttons, to explain
	 * their purpose.
	 */
	public static Label labelProtocolLabel = new Label(
			FingerpaintConstants.INSTANCE.lblProtocol());

	/**
	 * Checkbox that needs to be checked to define a protocol. If it isn't
	 * checked, steps (wall movements) are executed directly.
	 */
	public static CheckBox defineProtocolCheckBox = new CheckBox(
			FingerpaintConstants.INSTANCE.cbDefProt());

	// --- SAVE POP-UP MENU WIDGETS -------------------------------------------
	/** Pop-up panel to handle the saving of the current results. */
	public static PopupPanel saveItemPanel = new PopupPanel();

	/**
	 * Horizontal panel to hold the Save and Cancel buttons in the pop-up panel.
	 */
	public static HorizontalPanel saveButtonsPanel = new HorizontalPanel();

	/**
	 * Vertical panel to hold the textbox and the save button in the save pop-up
	 * panel.
	 */
	public static VerticalPanel saveItemVerticalPanel = new VerticalPanel();

	/**
	 * Button to save the item under the specified name in the
	 * {@link #saveNameTextBox}.
	 */
	public static Button saveItemPanelButton = new Button(
			FingerpaintConstants.INSTANCE.btnSave());

	/** Cancel button inside the save pop-up menu. */
	public static Button cancelSaveResultsButton = new Button(
			FingerpaintConstants.INSTANCE.btnCancel());

	/** Ok / Cancel button to close the save results or overwrite pop-up panel. */
	public static Button closeSaveButton = new Button(
			FingerpaintConstants.INSTANCE.btnClose());

	/** Textbox to input the name in to name the file. */
	public static TextBox saveNameTextBox = new TextBox();

	/** Label to indicate that the chosen name is already in use. */
	public static Label saveMessageLabel = new Label();

	// --- OVERWRITE POP-UP MENU WIDGETS --------------------------------------
	/**
	 * Pop-up panel that appears after the Save button in the save pop-up panel
	 * has been pressed.
	 */
	public static PopupPanel overwriteSavePanel = new PopupPanel();

	/**
	 * Horizontal panel to hold the OK or Overwrite/Cancel button(s) in the
	 * confirm save pop-up panel.
	 */
	public static HorizontalPanel overwriteButtonsPanel = new HorizontalPanel();

	/**
	 * Vertical panel to hold the save message and the OK/Overwrite button in
	 * the confirm save pop-up panel.
	 */
	public static VerticalPanel overwriteSaveVerticalPanel = new VerticalPanel();

	/** Button to overwrite the item that is currently being saved. */
	public static Button overwriteSaveButton = new Button(
			FingerpaintConstants.INSTANCE.btnOverwrite());

	// --- LOAD POP-UP MENU WIDGETS -------------------------------------------
	/**
	 * Vertical panel to hold the textbox and the cancel button in the load
	 * pop-up panel.
	 */
	public static VerticalPanel loadVerticalPanel = new VerticalPanel();

	/** Pop-up panel to handle the loading of previously saved items. */
	public static PopupPanel loadPanel = new PopupPanel();

	/** Button to close the load pop-up menu. */
	public static Button closeLoadButton = new Button(
			FingerpaintConstants.INSTANCE.btnClose());

	// --- STEP SIZE WIDGETS --------------------------------------------------
	/**
	 * The numberspinner to define the step size of a single wall movement.
	 */
	public static NumberSpinner sizeSpinner = new NumberSpinner(
			MixingStep.STEP_DEFAULT, MixingStep.STEP_UNIT, MixingStep.STEP_MIN,
			MixingStep.STEP_MAX, true);

	/**
	 * Label to be displayed above the {@link #sizeSpinner}, to explain its
	 * purpose.
	 */
	public static Label sizeLabel = new Label(
			FingerpaintConstants.INSTANCE.lblStepSize());

	// --- VIEW SINGLE GRAPH WIDGETS ------------------------------------------
	/**
	 * Pop-up menu to display the performance of a single graph. It is opened
	 * when {@link #viewSingleGraph} is clicked. It contains a vertical panel.
	 */
	public static PopupPanel viewSingleGraphPopupPanel = new PopupPanel();

	/**
	 * Horizontal panel to contain the Close and Export buttons.
	 */
	public static HorizontalPanel viewSingleGraphHorizontalPanel = new HorizontalPanel();

	/**
	 * Vertical panel to contain the horizontal panel and simple panel of the
	 * single graph pop-up.
	 */
	public static VerticalPanel viewSingleGraphVerticalPanel = new VerticalPanel();

	/**
	 * Simple panel to display the graph of the previously executed mixing run.
	 */
	public static SimplePanel viewSingleGraphGraphPanel = new SimplePanel();

	/** Button to view the performance of the previously executed mixing run. */
	public static Button viewSingleGraph = new Button(
			FingerpaintConstants.INSTANCE.btnViewSingleGraph());

	/** Button to close the performance pop-up. */
	public static Button closeSingleGraphViewButton = new Button(
			FingerpaintConstants.INSTANCE.btnClose());

	/** Button to export the image of the current mixing performance. */
	public static Button exportSingleGraphButton = new Button(
			FingerpaintConstants.INSTANCE.btnExportGraph());

	// --- COMPARE PERFORMANCE WIDGETS ----------------------------------------
	/**
	 * Pop-up panel to display all the previously stored mixing runs with
	 * performance. It also contains the Compare and Close buttons.
	 */
	public static PopupPanel compareSelectPopupPanel = new PopupPanel();

	/**
	 * Pop-up panel that displays the simple panel with the performance graph
	 * and New Comparison and Close buttons.
	 */
	public static PopupPanel comparePopupPanel = new PopupPanel();

	/**
	 * Simple panel that displays a graph with the mixing performance of the
	 * selected mixing runs.
	 */
	public static SimplePanel compareGraphPanel = new SimplePanel();

	/**
	 * Button to compare the performance of previously saved mixing runs. When
	 * clicked, it opens the {@link #compareSelectPopupPanel} pop-up.
	 */
	public static Button comparePerformanceButton = new Button(
			FingerpaintConstants.INSTANCE.btnComparePerfomance());

	/**
	 * Button to compare the performance of the selected mixing runs.
	 */
	public static Button compareButton = new Button(
			FingerpaintConstants.INSTANCE.btnCompare());

	/** Cancel button inside the compare performance pop-up. */
	public static Button cancelCompareButton = new Button(
			FingerpaintConstants.INSTANCE.btnCancel());

	/** Close button inside the compare performance pop-up. */
	public static Button closeCompareButton = new Button(
			FingerpaintConstants.INSTANCE.btnClose());

	/**
	 * Button inside the compare performance pop-up to start a new comparison.
	 * When clicked, it closes the {@link #comparePopupPanel} pop-up and opens
	 * the {@link #compareSelectPopupPanel}pop-up
	 */
	public static Button newCompareButton = new Button(
			FingerpaintConstants.INSTANCE.btnNewCompare());

	/**
	 * sets all debug ID's for easier debugging (except for the CellBrowser)
	 */
	public static void setDebugIDs(){
		defineProtocolCheckBox.ensureDebugId("defineProtocolCheckbox");
		mixNowButton.ensureDebugId("mixNowButton");
		viewSingleGraph.ensureDebugId("viewSingleGraph");
		nrStepsSpinner.ensureDebugId("nrStepsSpinner");
		saveResultsButton.ensureDebugId("saveResultsButton");
		comparePerformanceButton.ensureDebugId("comparePerformanceButton");
		compareSelectPopupPanel.ensureDebugId("compareSelectPopupPanel");
		saveNameTextBox.ensureDebugId("saveNameTextBox");
		saveItemPanelButton.ensureDebugId("saveItemPanelButton");
	}
	
}
