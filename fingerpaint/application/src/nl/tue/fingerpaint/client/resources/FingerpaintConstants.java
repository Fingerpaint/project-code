package nl.tue.fingerpaint.client.resources;

import nl.tue.fingerpaint.client.gui.GuiState;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

/**
 * The {@code FingerpaintConstants} are a set of localised constants. They can
 * be used in the GUI for I18N for example.
 * 
 * @author Group Fingerpaint
 */
public interface FingerpaintConstants extends Constants {
	// --- PUBLIC GLOBALS -----------------------------------------------------
	/**
	 * An instance that can be used to obtain constants.
	 */
	public static final FingerpaintConstants INSTANCE = GWT
			.create(FingerpaintConstants.class);

	// --- CONSTANTS FOR LOADING ----------------------------------------------
	/**
	 * @return Localised string that indicates that the application is loading
	 *         geometries and mixers from the server.
	 */
	@DefaultStringValue("Loading geometries and mixers...")
	public String loadingGeometries();

	// --- CONSTANTS FOR LABELS -----------------------------------------------
	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#nrStepsLabel} label.
	 */
	@DefaultStringValue("#steps")
	public String lblNrSteps();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#labelProtocolLabel} label.
	 */
	@DefaultStringValue("Protocol:")
	public String lblProtocol();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#sizeLabel} label.
	 */
	@DefaultStringValue("Step size")
	public String lblStepSize();

	// -- CONSTANTS FOR SAVE MESSAGES -----------------------------------------
	/**
	 * @return Localised string that describes that an item has been saved
	 *         successfully.
	 */
	@DefaultStringValue("Save successful.")
	public String saveSuccess();

	/**
	 * @return Localised string that describes that an item could not be saved
	 *         (because the name was already in use).
	 */
	@DefaultStringValue("This name is already in use. "
			+ "Choose whether to overwrite existing file " + "or to cancel.")
	public String nameInUse();

	// --- CONSTANTS FOR BUTTONS ----------------------------------------------
	/**
	 * @return Localised string that contains the text for
	 *         {@link GuiState#squareDrawingTool}.
	 */
	@DefaultStringValue("square")
	public String btnSquareDraw();

	/**
	 * @return Localised string that contains the text for
	 *         {@link GuiState#circleDrawingTool}.
	 */
	@DefaultStringValue("circle")
	public String btnCircleDraw();
	
	/**
	 * @return Localised string that contains the text for a save button.
	 */
	@DefaultStringValue("Save")
	public String btnSave();

	/**
	 * @return Localised string that contains the text for a cancel button.
	 */
	@DefaultStringValue("Cancel")
	public String btnCancel();

	/**
	 * @return Localised string that contains the text for a close button.
	 */
	@DefaultStringValue("Close")
	public String btnClose();

	/**
	 * @return Localised string that contains the text for an overwrite button.
	 */
	@DefaultStringValue("Overwrite")
	public String btnOverwrite();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#toolSelectButton} button.
	 */
	@DefaultStringValue("Select Tool")
	public String btnSelectTool();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#saveDistributionButton} button.
	 */
	@DefaultStringValue("Save Distribution")
	public String btnSaveDist();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#loadInitDistButton} button.
	 */
	@DefaultStringValue("Load Initial Distribution")
	public String btnLoadDist();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#loadInitDistButton} button.
	 */
	@DefaultStringValue("Reset Distribution")
	public String btnResetDist();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#saveResultsButton} button.
	 */
	@DefaultStringValue("Save Results")
	public String btnSaveResults();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#removeSavedResultsButton} button.
	 */
	@DefaultStringValue("Remove Saved Results")
	public String btnRemoveResults();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#saveProtocolButton} button.
	 */
	@DefaultStringValue("Save Protocol")
	public String btnSaveProt();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#loadProtocolButton} button.
	 */
	@DefaultStringValue("Load Mixing Protocol")
	public String btnLoadProt();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#resetProtocolButton} button.
	 */
	@DefaultStringValue("Reset Protocol")
	public String btnResetProt();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#mixNowButton} button.
	 */
	@DefaultStringValue("Mix Now")
	public String btnMixNow();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#viewSingleGraph} button.
	 */
	@DefaultStringValue("View single graph")
	public String btnViewSingleGraph();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#exportSingleGraphButton} button.
	 */
	@DefaultStringValue("Export graph")
	public String btnExportGraph();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#comparePerformanceButton} button.
	 */
	@DefaultStringValue("Compare Performance")
	public String btnComparePerfomance();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#compareButton} button.
	 */
	@DefaultStringValue("Compare")
	public String btnCompare();

	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#newCompareButton} button.
	 */
	@DefaultStringValue("New Comparison")
	public String btnNewCompare();

	// -- CONSTANTS FOR CHECKBOXES --------------------------------------------
	/**
	 * @return Localised string that contains the text for the
	 *         {@link GuiState#defineProtocolCheckBox} checkbox.
	 */
	@DefaultStringValue("Define Protocol")
	public String cbDefProt();

	// --- CONSTANTS FOR ERRORS -----------------------------------------------
	/**
	 * @return Localised string to indicate that a request timeout occurred.
	 */
	@DefaultStringValue("The simulation server did not respond in"
			+ " time. Try again later.")
	public String simulationRequestTimeout();

	/**
	 * @return Localised string to indicate that an error has occurred.
	 */
	@DefaultStringValue("An error occurred!")
	public String errorOccured();

}
