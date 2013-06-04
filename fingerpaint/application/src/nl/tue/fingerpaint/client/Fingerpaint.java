package nl.tue.fingerpaint.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import nl.tue.fingerpaint.client.Geometry.StepAddedListener;
import nl.tue.fingerpaint.client.gui.MenuToggleButton;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.resources.FingerpaintResources;
import nl.tue.fingerpaint.client.serverdata.ServerDataCache;
import nl.tue.fingerpaint.client.simulator.SimulatorService;
import nl.tue.fingerpaint.client.simulator.SimulatorServiceAsync;
import nl.tue.fingerpaint.client.storage.ResultStorage;
import nl.tue.fingerpaint.client.storage.StorageManager;
import nl.tue.fingerpaint.shared.GeometryNames;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.LineChart;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Group Fingerpaint
 */
public class Fingerpaint implements EntryPoint {
	/** Class to keep track of everything the user has selected */
	protected ApplicationState as;

	// Button to toggle between black and white drawing colour
	private ToggleButton toggleColor;

	// Button to reset the distribution to all white
	private Button resetDistButton;

	// Button to save the current results
	private Button saveResultsButton;

	// Button to load an initial distribution
	private Button loadInitDistButton;

	// Button to load a mixing protocol
	private Button loadProtocolButton;

	// --------------------------------------------------------------------------------------
	// Popup Panel to handle the loading of an initial distribution or mixing
	// protocol

	// Vertical Panel to hold the textbar and the cancel button in the load
	// popuppanel
	private VerticalPanel loadVerticalPanel;

	// close Button inside the save popup menu
	private Button closeLoadButton;
	
	private PopupPanel protocolPanelContainer;

	// --------------------------------------------------------------------------------------

	// Button to remove previously saved results

	// --------------------------------------------------------------------------------------

	// Button to adapt the drawing tool
	// TODO: Change this to a button on which the current tool is drawn

	// Panel that covers the entire application and blocks the user from
	// accessing other features
	private static FlowPanel loadingPanel = new FlowPanel();
	private Label loadingPanelMessage;

	// Popup Panel to handle the saving of the current results
	private PopupPanel saveItemPanel;

	// Vertical Panel to hold the textbar and the save button in the save
	// popuppanel
	private VerticalPanel saveItemVerticalPanel;

	// Horizontal Panel to hold the Save and Cancel buttons in the popup panel
	private HorizontalPanel saveButtonsPanel;

	// Textbox to input the name in to name the file
	private TextBox saveNameTextBox;

	// Cancel Button inside the save popup menu
	private Button cancelSaveResultsButton;

	// Popup Panel that appears after the Save button in the save popup panel
	// has been pressed
	private PopupPanel overwriteSavePanel;

	// Vertical Panel to hold the save message and the ok/overwrite button in
	// the confirm save popup panel
	private VerticalPanel overwriteSaveVerticalPanel;

	// Label to hold the save message
	private Label saveMessageLabel;

	// Horizontal Panel to hold the ok or overwrite/cancel button(s) in the
	// confirm save popup panel
	private HorizontalPanel overwriteButtonsPanel;

	// Ok / Cancel button to close the save results popup panel
	private Button closeSaveButton;

	// --------------------------------------------------------------------------------------

	private Button comparePerformanceButton;

	private PopupPanel compareSelectPopupPanel;

	private Button compareButton;

	private Button cancelCompareButton;

	private PopupPanel comparePopupPanel;

	private SimplePanel compareGraphPanel;
	
	private Button closeCompareButton;

	private Button newCompareButton;

	// --------------------------------------------------------------------------------------

	// Button to remove previously saved results
	private Button removeSavedResultsButton;

	// Popup Panel to handle the removing of results
	private PopupPanel removeResultsPanel;

	// Vertical panel to hold the flex panel and close button
	private VerticalPanel removeResultsVerticalPanel;

	// FlexTable to hold all the result entries
	private FlexTable resultsFlexTable;

	// Button to close the remove results popup panel
	private Button closeResultsButton;

	private Button saveProtocolButton;

	private Button saveDistributionButton;

	private Button saveItemPanelButton;

	private Button overwriteSaveButton;

	private PopupPanel viewSingleGraphPopupPanel;
	private VerticalPanel viewSingleGraphVerticalPanel;
	private HorizontalPanel viewSingleGraphHorizontalPanel;
	private SimplePanel viewSingleGraphGraphPanel;
	private Button viewSingleGraph;
	private Button closeSingleGraphViewButton;
	private Button exportSingleGraphButton;

	// Button to adapt the drawing tool
	// TODO: Change this to a button on which the current tool is drawn
	private Button toolSelectButton;

	// PopupPanel which contains options for selecting a different drawing tool
	private PopupPanel toolSelector;

	// The panel in the popup panel to seperate the toolSelector from the
	// toolSizer
	private HorizontalPanel popupPanelPanel;

	// The panel in the popup panel that contains the different drawing tools
	private VerticalPanel popupPanelMenu;

	// Button to select the square drawing tool
	// TODO: Change this to a button on which a square is drawn
	private ToggleButton squareDrawingTool;

	// Button to select the circle drawing tool
	// TODO: Change this to a button on which a circle is drawn
	private ToggleButton circleDrawingTool;

	// Vertical panel to contain all menu items
	private VerticalPanel menuPanel = new VerticalPanel();
	
	// Button to toggle if the menu is visible or not
	private MenuToggleButton menuToggleButton;

	// Panel for loading stuff
	private static PopupPanel loadPanel = new PopupPanel();

	// The NumberSpinner and label to define the step size
	// TODO: The text 'Step size' should be translated later on
	private Label sizeLabel = new Label("Step size");
	private NumberSpinner sizeSpinner;

	private NumberSpinner cursorSizeSpinner;

	// Checkbox that needs to be checked to define a protocol. If it isn't
	// checked, steps are executed directly.
	private CheckBox defineProtocolCheckBox;

	// The NumberSpinner and label to define how many times the mixing protocol
	// is executed
	// TODO: The text '#steps' should be translated later on
	private Label nrStepsLabel = new Label("#steps");
	private NumberSpinner nrStepsSpinner;

	/**
	 * Shows the textual representation of the mixing protocol.
	 */
	// Button that executes the current mixing run when it is pressed
	private Button mixNowButton;

	// Button that resets the protocol when it is pressed
	private Button resetProtocolButton;

	/*
	 * The NumberSpinner to set the #steps parameter. Its settings are described
	 * via the following parameters.
	 */
	private final double NRSTEPS_DEFAULT = 1.0;
	private final double NRSTEPS_RATE = 1.0;
	private final double NRSTEPS_MIN = 1.0;
	private final double NRSTEPS_MAX = 50.0;

	/*
	 * The initialisation parameters for the cursorSizeSpinner these sizes
	 * represent cursor pixels
	 */
	private final double CURSOR_DEFAULT = 3.0;
	private final double CURSOR_RATE = 1.0;
	private final double CURSOR_MIN = 1.0;
	private final double CURSOR_MAX = 50.0; // good value should be determined
											// for performance

	private static final String LOADINGPANEL_ID = "loading-overlay";
	private static final String LOADINGPANEL_MESSAGE_ID = "loading-overlay-message";

	/**
	 * Stores how long in milliseconds a SAVE_SUCCESS_MESSAGE should be shown in
	 * a NotificationPanel.
	 */
	private static final int SAVE_SUCCESS_TIMEOUT = 2000;

	/**
	 * The message that is shown to the user upon a successful save.
	 */
	private static final String SAVE_SUCCESS_MESSAGE = "Save successful.";

	// Holds the mixingPerformance of the last run.
	private GraphVisualisator graphVisualisator;

	/**
	 * Shows the textual representation of the mixing protocol.
	 */
	private Label labelProtocolRepresentation = new Label();

	private String lastSaveButtonClicked;

	private Label labelProtocolLabel;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Load CSS
		FingerpaintResources.INSTANCE.css().ensureInjected();

		// Initialise the loading panel
		// Add animation image
		Image loadImage = new Image(FingerpaintResources.INSTANCE.loadImage()
				.getSafeUri());
		loadingPanel.add(loadImage);

		// Add label that may contain explanatory text
		loadingPanelMessage = new Label(
				FingerpaintConstants.INSTANCE.loadingGeometries(), false);
		loadingPanelMessage.getElement().setId(LOADINGPANEL_MESSAGE_ID);
		loadingPanel.add(loadingPanelMessage);
		loadingPanel.getElement().setId(LOADINGPANEL_ID);

		// initialise the underlying model of the application
		as = new ApplicationState();
		as.setNrSteps(1.0);
		setLoadingPanelVisible(true);
		ServerDataCache.initialise(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				setLoadingPanelVisible(false);
				loadMenu();
			}

			@Override
			public void onFailure(Throwable caught) {
				setLoadingPanelVisible(false);
				if (caught instanceof RequestTimeoutException) {
					showError("The simulation server did not respond in"
							+ " time. Try again later");
				} else {
					showError(caught.getMessage());
				}
			}
		});
	}

	/**
	 * Build and show the main menu.
	 */
	protected void loadMenu() {
		// Create a model for the cellbrowser.
		TreeViewModel model = new CustomTreeModel();

		/*
		 * Create the browser using the model. We specify the default value of
		 * the hidden root node as "null".
		 */
		CellBrowser tree = (new CellBrowser.Builder<Object>(model, null))
				.build();

		// Add the tree to the root layout panel.
		RootLayoutPanel.get().add(tree);
		
		//for debugging purposes
		tree.ensureDebugId("cell");
	}

	/**
	 * Show a pop-up with given message that indicates an error has occurred.
	 * 
	 * @param message
	 *            A message that explains the error.
	 */
	protected void showError(String message) {
		final PopupPanel errorPopup = new PopupPanel(false, true);
		errorPopup.setAnimationEnabled(true);
		VerticalPanel verPanel = new VerticalPanel();
		verPanel.add(new Label("An error occurred!"));
		if (message != null) {
			verPanel.add(new Label(message));
		}
		verPanel.add(new Button("Close", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				errorPopup.hide();
			}
		}));
		errorPopup.add(verPanel);
		errorPopup.center();
	}

	/**
	 * Change the message that is displayed in the load panel below the loading
	 * animation.
	 * 
	 * @param message
	 *            The message to show below the animation. When {@code null},
	 *            the message will be deleted.
	 */
	protected void setLoadingPanelMessage(String message) {
		if (message == null) {
			message = "";
		}

		loadingPanelMessage.setText(message);
	}

	/**
	 * The model that defines the nodes in the tree.
	 */
	private class CustomTreeModel implements TreeViewModel {

		/**
		 * Number of levels in the tree. Is used to determine when the browser
		 * should be closed.
		 */
		private final static int NUM_LEVELS = 2;

		/** A selection model that is shared along all levels. */
		private final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();

		/** Updater on the highest level. */
		private final ValueUpdater<String> valueGeometryUpdater = new ValueUpdater<String>() {
			@Override
			public void update(String value) {
				as.setGeometryChoice(value);
				lastClickedLevel = 0;
			}
		};

		/** Updater on level 1. */
		private final ValueUpdater<String> valueMixerUpdater = new ValueUpdater<String>() {
			@Override
			public void update(String value) {
				as.setMixerChoice(value);
				lastClickedLevel = 1;
			}
		};

		/** Indicate which level was clicked the last. */
		private int lastClickedLevel = -1;

		private void setUserChoiceValues(String selectedMixer) {
			// TODO: Actually create a different geometry depending on the
			// chosen geometry...
			as.setGeometry(new RectangleGeometry(Window.getClientHeight() - 20, Window.getClientWidth() - 20));
		}

		public CustomTreeModel() {

			selectionModel
					.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
						@Override
						public void onSelectionChange(SelectionChangeEvent event) {
							String selected = selectionModel
									.getSelectedObject();

							if (selected != null
									&& lastClickedLevel == NUM_LEVELS - 1) {
								setUserChoiceValues(selected);

								// "closes" Cellbrowser widget (clears whole
								// rootpanel)
								// TODO: Make decent close-code
								RootPanel.get().clear();
								
								createMixingWidgets();
							}
						}
					});
		}

		/**
		 * Helper method that initialises the widgets for the mixing interface
		 */
		private void createMixingWidgets() {		
			menuPanel.getElement().setId("menuPanel");

			// Initialise a listener for when a new step is entered to the
			// protocol
			StepAddedListener l = new StepAddedListener() {
				@Override
				public void onStepAdded(MixingStep step) {
					addStep(step);
				}
			};
			as.getGeometry().addStepAddedListener(l);

			// Initialise the cursorSizeSpinner so it can be added to the tool
			// selector popup
			createCursorSizeSpinner();

			// Initialise the toolSelectButton and add to menuPanel
			createToolSelector();
			menuPanel.add(toolSelectButton);

			// Initialise toggleButton and add to
			// menuPanel
			createToggleButton();
			menuPanel.add(toggleColor);

			// Initialise the loadDistButton and add to
			// menuPanel
			// createLoadDistButton();
			// menuPanel.add(loadDistButton);

			// Initialise the resetDistButton and add to menuPanel
			createResetDistButton();
			menuPanel.add(resetDistButton);

			// Initialise the saveProtocolButton and add it to the menuPanel
			createSaveDistributionButton();
			menuPanel.add(saveDistributionButton);

			// Initialise the loadInitDistButton and add it to the menuPanel
			createLoadInitDistButton();
			menuPanel.add(loadInitDistButton);

			// Initialise the saveResultsButton and add it to the menuPanel
			createSaveResultsButton();
			menuPanel.add(saveResultsButton);

			createSaveWidgets();

			// Initialise the removeSavedResultsButton and add it to the
			// menuPanel
			createRemoveSavedResultsButton();
			menuPanel.add(removeSavedResultsButton);

			createViewSingleGraphButton();
			menuPanel.add(viewSingleGraph);

			// Initialise the comparePerformanceButton and add it to the
			// menuPanel
			createComparePerformanceButton();
			menuPanel.add(comparePerformanceButton);

			// Initialise a spinner for changing the length of a mixing protocol
			// step and add to menuPanel.
			createStepSizeSpinner();
			menuPanel.add(sizeLabel);
			menuPanel.add(sizeSpinner);

			// Initialise the checkbox that indicates whether a protocol is
			// being defined, or single steps have to be executed and add to
			// menu panel
			createDefineProtocolCheckBox();
			menuPanel.add(defineProtocolCheckBox);

			// Initialise a spinner for #steps
			createNrStepsSpinner();

			// Initialise the resetProtocol button
			createResetProtocolButton();

			// Initialise the saveProtocolButton and add it to the menuPanel
			createSaveProtocolButton();

			createProtocolRepresentationTextArea();

			// Initialise the mixNow button
			createMixNowButton();

			// Initialise the loadProtocolButton and add it to the menuPanel
			createLoadProtocolButton();

			// TODO: Initialise other menu items and add them to menuPanel
			// Add all the protocol widgets to the menuPanel and hide them
			// initially.
			VerticalPanel protocolPanel = new VerticalPanel();
			protocolPanel.add(nrStepsLabel);
			protocolPanel.add(nrStepsSpinner);
			protocolPanel.add(labelProtocolLabel);
			protocolPanel.add(labelProtocolRepresentation);
			protocolPanel.add(mixNowButton);
			protocolPanel.add(resetProtocolButton);
			protocolPanel.add(saveProtocolButton);
			protocolPanel.add(loadProtocolButton);
			
			protocolPanelContainer = new PopupPanel();
			protocolPanelContainer.getElement().setId("protPanel");
			protocolPanelContainer.setAnimationEnabled(true);
			protocolPanelContainer.add(protocolPanel);
			protocolPanelContainer.setVisible(false);
			
			toggleProtocolWidgets(false);
			
			// Add canvas and menuPanel to the page
			RootPanel.get().add(as.getGeometry().getCanvas());
			RootPanel.get().add(menuPanel);
			menuToggleButton = new MenuToggleButton(menuPanel);
			RootPanel.get().add(menuToggleButton);
			
			//for debugging
			viewSingleGraph.ensureDebugId("viewGraph");
		}

		/**
		 * Get the {@link com.google.gwt.view.client.TreeViewModel.NodeInfo}
		 * that provides the children of the specified value.
		 */
		public <T> NodeInfo<?> getNodeInfo(T value) {
			// When the Tree is being initialised, the last clicked level will
			// be -1,
			// in other cases, we need to load the level after the currently
			// clicked one.
			if (lastClickedLevel < 0) {
				// LEVEL 0. - Geometry
				// We passed null as the root value. Return the Geometries.

				// Create a data provider that contains the list of Geometries.
				ListDataProvider<String> dataProvider = new ListDataProvider<String>(
						Arrays.asList(ServerDataCache.getGeometries()));

				// Return a node info that pairs the data provider and the cell.
				return new DefaultNodeInfo<String>(dataProvider,
						new ClickableTextCell(), selectionModel,
						valueGeometryUpdater);
			} else if (lastClickedLevel == 0) {
				// LEVEL 1 - Mixer (leaf)

				// We want the children of the Geometry. Return the mixers.
				ListDataProvider<String> dataProvider = new ListDataProvider<String>(
						Arrays.asList(ServerDataCache
								.getMixersForGeometry((String) value)));

				// Use the shared selection model.
				return new DefaultNodeInfo<String>(dataProvider,
						new ClickableTextCell(), selectionModel,
						valueMixerUpdater);

			}
			return null;
		}

		/**
		 * Check if the specified value represents a leaf node. Leaf nodes
		 * cannot be opened.
		 */
		// You can define your own definition of leaf-node here.
		public boolean isLeaf(Object value) {
			return lastClickedLevel == NUM_LEVELS - 1;
		}
	}

	/*
	 * Initialises the spinner for the stepSize
	 */
	private void createStepSizeSpinner() {
		// initial initialisation of the spinner
		sizeSpinner = new NumberSpinner(MixingStep.STEP_DEFAULT,
				MixingStep.STEP_UNIT, MixingStep.STEP_MIN, MixingStep.STEP_MAX,
				true);
		
		sizeSpinner.addStyleName("sizeSpinnerInput");
		as.setStepSize(MixingStep.STEP_DEFAULT);

		// set a listener for the spinner
		sizeSpinner.setSpinnerListener(new NumberSpinnerListener() {

			@Override
			public void onValueChange(double value) {
				// change the current mixing step
				as.setStepSize(value);
			}

		});
	}

	/*
	 * Initialises the reset Distribution Button. When this button is pressed,
	 * the current canvas is reset to all white
	 */
	private void createResetDistButton() {
		resetDistButton = new Button("Reset Dist");
		resetDistButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				as.getGeometry().resetDistribution();
			}

		});
	}

	/*
	 * Toggles the visibility and availability of all the protocol widgets.
	 */
	private void toggleProtocolWidgets(boolean value) {
		if (value) {
			protocolPanelContainer.showRelativeTo(menuPanel);
		} else {
			protocolPanelContainer.hide();
		}
		// TODO: make a setEnabled for the numberspinner
//		nrStepsLabel.setVisible(value);
//		nrStepsSpinner.setVisible(value);
//		labelProtocolRepresentation.setVisible(value);
//		mixNowButton.setVisible(value);
//		saveProtocolButton.setVisible(value);
//		saveProtocolButton.setEnabled(value);
//		resetProtocolButton.setVisible(value);
//		resetProtocolButton.setEnabled(value);
//		labelProtocolLabel.setVisible(value);

	}

	/*
	 * Initialises the spinner for the nrSteps.
	 */
	private void createNrStepsSpinner() {
		// Initialise the spinner with the required settings.
		nrStepsSpinner = new NumberSpinner(NRSTEPS_DEFAULT, NRSTEPS_RATE,
				NRSTEPS_MIN, NRSTEPS_MAX, true);
		nrStepsSpinner.ensureDebugId("nrStepsSpinner");
		// Also initialise the initial value in the ApplicationState class.
		as.setNrSteps(NRSTEPS_DEFAULT);

		// The spinner for #steps should update the nrSteps variable whenever
		// the value is changed.
		nrStepsSpinner.setSpinnerListener(new NumberSpinnerListener() {

			@Override
			public void onValueChange(double value) {
				as.setNrSteps(value);
			}
		});
	}

	/*
	 * Initialises the spinner that edits the cursorsize
	 */
	private void createCursorSizeSpinner() {
		cursorSizeSpinner = new NumberSpinner(CURSOR_DEFAULT, CURSOR_RATE,
				CURSOR_MIN, CURSOR_MAX, true);

		cursorSizeSpinner.setSpinnerListener(new NumberSpinnerListener() {

			@Override
			public void onValueChange(double value) {
				as.getGeometry().setDrawingToolSize((int) value - 1);
				// -1 because the drawingTools have a default size of 1 pixel
				// for inputSize 0
			}
		});
	}

	/*
	 * Initialises the toggleColor button. TODO: Use pictures instead of text on
	 * the button.
	 * 
	 * Note: If the button shows "black" it means the current drawing colour is
	 * black. Not 'toggle to black'.
	 */
	private void createToggleButton() {
		toggleColor = new ToggleButton("black", "white");
		toggleColor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				toggleColor();

			}
		});
	}

	/*
	 * Initialises the protocol representation text area. TODO: this code has to
	 * be removed!
	 */
	private void createProtocolRepresentationTextArea() {
		labelProtocolLabel = new Label("Protocol:");
		
		labelProtocolRepresentation.setVisible(false);
		labelProtocolRepresentation.getElement().setId("protLabel");
	}

	/*
	 * Changes the current drawing colour from black to white, and from white to
	 * black.
	 */
	private void toggleColor() {
		if (toggleColor.isDown()) {
			as.getGeometry().setColor(CssColor.make("white"));
		} else {
			as.getGeometry().setColor(CssColor.make("black"));
		}
	}

	/*
	 * Initialises the tool selector, including buttons to select the shape of
	 * the tool, and the slider to select the size of the tool
	 */
	private void createToolSelector() {
		// --Initialise all elements--------------------------------
		toolSelector = new PopupPanel(true);
		popupPanelPanel = new HorizontalPanel();
		popupPanelMenu = new VerticalPanel();
		squareDrawingTool = new ToggleButton("square", "square");
		circleDrawingTool = new ToggleButton("circle", "circle");

		squareDrawingTool.addClickHandler(new ClickHandler() {

			/*
			 * Select the square drawing tool when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {

				if (!squareDrawingTool.isDown()) {
					squareDrawingTool.setDown(true);
				} else {
					as.getGeometry().setDrawingTool(
							new SquareDrawingTool(getCursorSize()));

					circleDrawingTool.setDown(false);
				}
			}
		});
		// Initial drawing tool is square
		squareDrawingTool.setDown(true);

		circleDrawingTool.addClickHandler(new ClickHandler() {

			/*
			 * Select the square drawing tool when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {

				if (!circleDrawingTool.isDown()) {
					circleDrawingTool.setDown(true);
				} else {
					as.getGeometry().setDrawingTool(
							new CircleDrawingTool(getCursorSize()));

					squareDrawingTool.setDown(false);
				}
			}
		});

		// -- Add all Drawings Tools below ---------------------
		popupPanelMenu.add(squareDrawingTool);
		popupPanelMenu.add(circleDrawingTool);

		// --TODO: Add DrawingTool Size slider below ----------------
		popupPanelPanel.add(popupPanelMenu);
		popupPanelPanel.add(cursorSizeSpinner);

		// Add everything to the popup panel
		toolSelector.add(popupPanelPanel);

		// Create the button the triggers the popup panel
		// TODO: The text 'Select Tool' should be translated later on
		toolSelectButton = new Button("Select Tool");
		toolSelectButton.addClickHandler(new ClickHandler() {

			/*
			 * Show the popupPanel when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {
				toolSelector
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								int left = (Window.getClientWidth()
										- offsetWidth - 75);
								int top = 40;
								toolSelector.setPopupPosition(left, top);
							}
						});
			}

		});
	}

	/*
	 * this method is used to acquire the size of the current cursor in pixels
	 * 
	 * @return cursorSizeSpinner.getValue()-1
	 */
	private int getCursorSize() {
		return (int) cursorSizeSpinner.getValue() - 1;
	}

	/*
	 * Initialises the resetProtocol button. When pressed, this button sets a
	 * new (and empty) protocol in the application state, and it clear the
	 * protocol representation text area.
	 */
	private void createResetProtocolButton() {
		// TODO: The text 'Reset Protocol' should be translated later on
		resetProtocolButton = new Button("Reset Protocol");
		resetProtocolButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				resetProtocol();
				// When reset protocol is pressed, the save results button is
				// also disabled.
				saveResultsButton.setEnabled(false);
			}

		});
	}

	/*
	 * Initialises the saveProtocol Button. When this button is pressed, the
	 * currently defined protocol is saved.
	 */
	private void createSaveProtocolButton() {
		// TODO: The text 'Save Results' should be translated later on
		saveProtocolButton = new Button("Save Protocol");
		saveProtocolButton.setEnabled(true);

		saveProtocolButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				saveProtocolButtonOnClick();
			}
		});
	}

	/*
	 * Initialises the createSaveResultsButton. When pressed, this button allows
	 * a user to save a mixing run
	 */
	private void createSaveResultsButton() {
		// TODO: The text 'Save Results' should be translated later on
		saveResultsButton = new Button("Save Results");
		saveResultsButton.setEnabled(true);

		saveResultsButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				saveResultsButtonOnClick();
			}
		});
	}

	/**
	 * Initialises the createSaveButton. When pressed, this button allows a user
	 * to save a mixing run.
	 */
	private void createSaveDistributionButton() {
		// TODO: The text 'Save Distribution' should be translated later on
		saveDistributionButton = new Button("Save Distribution");
		saveDistributionButton.setEnabled(true);

		saveDistributionButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveDistributionButtonOnClick();
			}
		});
	}

	private void createViewSingleGraphButton() {
		viewSingleGraph = new Button("View single graph");
		viewSingleGraph.setEnabled(false);
		viewSingleGraphPopupPanel = new PopupPanel();
		viewSingleGraphPopupPanel.setModal(true);
		viewSingleGraphVerticalPanel = new VerticalPanel();
		viewSingleGraphHorizontalPanel = new HorizontalPanel();
		exportSingleGraphButton = new Button("Export graph");
		viewSingleGraphHorizontalPanel.add(exportSingleGraphButton);
		closeSingleGraphViewButton = new Button("Close");
		viewSingleGraphHorizontalPanel.add(closeSingleGraphViewButton);
		viewSingleGraphGraphPanel = new SimplePanel();

		exportSingleGraphButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO: Insert Method-call which exports the graph
			}
		});
		closeSingleGraphViewButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				viewSingleGraphPopupPanel.hide();
			}
		});

		viewSingleGraph.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ArrayList<double[]> performance = new ArrayList<double[]>();
				performance.add(as.getSegregation());

				// Clear old data
				if (graphVisualisator != null) {
					graphVisualisator.clearSegregationResults();
				}
				viewSingleGraphPopupPanel.clear();
				viewSingleGraphVerticalPanel.clear();
				viewSingleGraphGraphPanel.clear();

				// Make graph and add it to viewSingleGraphVerticalPanel
				 createGraph(
						 viewSingleGraphGraphPanel,
				 new ArrayList<String>(Arrays
				 .asList("Current mixing run")), performance, new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Boolean result) {
						viewSingleGraphVerticalPanel.add(viewSingleGraphGraphPanel);
						viewSingleGraphVerticalPanel
								.add(viewSingleGraphHorizontalPanel);
						viewSingleGraphPopupPanel.add(viewSingleGraphVerticalPanel);
						viewSingleGraphPopupPanel.center();
					}	 
				});

				// .setPopupPositionAndShow(new PopupPanel.PositionCallback() {
				// public void setPosition(int offsetWidth,
				// int offsetHeight) {
				// int left = (Window.getClientWidth() - offsetWidth) / 2;
				// int top = (Window.getClientHeight() - offsetHeight) / 2;
				// viewSingleGraphPopupPanel.setPopupPosition(
				// left, top);
				// }
				// });
			}
		});

	}

	/**
	 * Initialises all widgets that are needed for the save popup panel
	 */
	private void createSaveWidgets() {
		createSavePanel();
		createOverwritePanel();
	}

	private void createOverwritePanel() {
		overwriteSavePanel = new PopupPanel();
		overwriteSavePanel.setModal(true);

		overwriteSaveVerticalPanel = new VerticalPanel();

		saveMessageLabel = new Label();

		overwriteButtonsPanel = new HorizontalPanel();

		overwriteSaveButton = new Button("Overwrite");
		overwriteSaveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				overwriteSaveButtonOnClick();
			}
		});

		closeSaveButton = new Button("Cancel");
		// Hide both popup panels if the OK button was pressed. Hide only the
		// second panel if the cancel button was pressed.
		closeSaveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				closeSaveButtonOnClick();

			}
		});

		// add all components to second popup panel
		overwriteSavePanel.add(overwriteSaveVerticalPanel);
		overwriteSaveVerticalPanel.add(saveMessageLabel);
		overwriteSaveVerticalPanel.add(overwriteButtonsPanel);
		overwriteButtonsPanel.add(closeSaveButton);
	}

	private void createSavePanel() {
		saveItemPanel = new PopupPanel();
		saveItemPanel.setModal(true);

		// Holds the TextBox and HorizontalPanel
		saveItemVerticalPanel = new VerticalPanel();

		// Sets the name to use for saving
		saveNameTextBox = new TextBox();
		saveNameTextBox.setMaxLength(30);

		// Determine whether user input is valid. Enable/disable the save
		// button. Execute save when ENTER is pressed.
		saveNameTextBox.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				handleKeyPress(event);
			}
		});

		// Holds the Save and Cancel buttons
		saveButtonsPanel = new HorizontalPanel();

		// Initially, the save button is disabled; it will become available if
		// "Mix Now" is pressed.
		saveItemPanelButton = new Button("Save");
		saveItemPanelButton.setEnabled(false);
		saveItemPanelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				savePanelButtonOnClick();
			}
		});

		cancelSaveResultsButton = new Button("Cancel");

		// Hide the first popup panel when the cancel button is pressed
		cancelSaveResultsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveItemPanel.hide();
				saveNameTextBox.setText("");
				saveItemPanelButton.setEnabled(false);
			}
		});

		// add all components to first popuppanel
		saveItemPanel.add(saveItemVerticalPanel);
		saveItemVerticalPanel.add(saveNameTextBox);
		saveItemVerticalPanel.add(saveButtonsPanel);
		saveButtonsPanel.add(saveItemPanelButton);
		saveButtonsPanel.add(cancelSaveResultsButton);
	}

	private void saveResultsButtonOnClick() {
		lastSaveButtonClicked = StorageManager.KEY_RESULTS;
		showSavePanel();
	}

	private void saveDistributionButtonOnClick() {
		lastSaveButtonClicked = StorageManager.KEY_INITDIST;
		showSavePanel();
	}

	private void saveProtocolButtonOnClick() {
		lastSaveButtonClicked = StorageManager.KEY_PROTOCOLS;
		showSavePanel();
	}

	private void savePanelButtonOnClick() {
		boolean success = save(saveNameTextBox.getText(), false);
		if (success) {
			NotificationPanel np = new NotificationPanel(SAVE_SUCCESS_MESSAGE);
			np.show(SAVE_SUCCESS_TIMEOUT);
			saveItemPanel.hide();
		} else {
			saveMessageLabel.setText("This name is already in use. "
					+ "Choose whether to overwrite existing file "
					+ "or to cancel.");

			overwriteButtonsPanel.remove(closeSaveButton);
			overwriteButtonsPanel.add(overwriteSaveButton);
			overwriteButtonsPanel.add(closeSaveButton);

			overwriteSavePanel.center();
			overwriteSavePanel.show();
			saveItemPanel.hide();
		}
	}

	private void overwriteSaveButtonOnClick() {
		save(saveNameTextBox.getText(), true);

		NotificationPanel np = new NotificationPanel(SAVE_SUCCESS_MESSAGE);
		np.show(SAVE_SUCCESS_TIMEOUT);
		overwriteSavePanel.hide();
	}

	private void showSavePanel() {
		saveItemPanel.center();
		saveItemPanel.show();
		saveNameTextBox.setText("");
		saveNameTextBox.setFocus(true);
	}

	private void closeSaveButtonOnClick() {
		overwriteSavePanel.hide();
		if (!closeSaveButton.getText().equals("OK")) {
			overwriteSavePanel.remove(overwriteSaveButton);
			saveItemPanel.show();
			saveNameTextBox.setSelectionRange(0, saveNameTextBox.getText()
					.length());
			saveNameTextBox.setFocus(true);
		} else {
			saveNameTextBox.setText("");
			saveItemPanelButton.setEnabled(false);
		}
	}

	private void handleKeyPress(KeyPressEvent event) {
		String text = saveNameTextBox.getText();
		String inputCharacter = Character.toString(event.getCharCode());
		int textlength = text.length();
		if (inputCharacter
				.matches("[~`!@#$%^&*()+={}\\[\\]:;\"|\'\\\\<>?,./\\s]")) {
			saveNameTextBox.cancelKey();
		} else if (inputCharacter.matches("[A-Za-z0-9]")) {
			textlength++;
		} else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE) {
			textlength--;
		} else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			saveItemPanelButton.click();
		}
		saveItemPanelButton.setEnabled(textlength > 0);
	}

	/**
	 * Saves the current protocol, distribution or mixing results, depending on
	 * which save-button was pressed last.
	 * 
	 * @param name Name of save "file".
	 * @param canOverwrite If we can overwrite an already-exisiting "file" with
	 *                     the given name or not.
	 * @return {@code true} if "file" was saved, {@code false} otherwise
	 */
	public boolean save(String name, boolean canOverwrite) {
		if (lastSaveButtonClicked.equals(StorageManager.KEY_INITDIST)) {
			return StorageManager.INSTANCE.putDistribution(
					GeometryNames.getShortName(as.getGeometryChoice()), name,
					as.getGeometry().getDistribution(), canOverwrite);
		} else if (lastSaveButtonClicked.equals(StorageManager.KEY_PROTOCOLS)) {
			return StorageManager.INSTANCE.putProtocol(
					GeometryNames.getShortName(as.getGeometryChoice()), name,
					as.getProtocol(), canOverwrite);
		} else if (lastSaveButtonClicked.equals(StorageManager.KEY_RESULTS)) {
			ResultStorage result = new ResultStorage();
			result.setGeometry(as.getGeometryChoice());
			result.setMixer(as.getMixerChoice());
			result.setDistribution(as.getInitialDistribution());
			result.setMixingProtocol(as.getProtocol());
			result.setSegregation(as.getSegregation());
			result.setNrSteps(as.getNrSteps());

			return StorageManager.INSTANCE
					.putResult(name, result, canOverwrite);
		}
		return false;
	}

	/*
	 * Initialises the loadProtocolButton. When pressed, this button allows a
	 * user to load a protocol. Also initialises the corresponding loading
	 * popup.
	 */
	private void createLoadProtocolButton() {
		loadProtocolButton = new Button("Load Mixing Protocol");

		loadProtocolButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				loadVerticalPanel = new VerticalPanel();
				loadPanel = new PopupPanel();
				loadPanel.setModal(true);
				loadPanel.add(loadVerticalPanel);
				closeLoadButton = new Button("Close");

				closeLoadButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						loadPanel.removeFromParent();
					}
				});

				List<String> geometryProtocols = StorageManager.INSTANCE
						.getProtocols(GeometryNames.getShortName(as
								.getGeometryChoice()));

				// Create a cell to render each value.
				TextCell textCell = new TextCell();

				// Create a CellList that uses the cell.
				CellList<String> cellList = new CellList<String>(textCell);
				cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

				// Add a selection model to handle user selection.
				final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
				cellList.setSelectionModel(selectionModel);
				selectionModel
						.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
							public void onSelectionChange(
									SelectionChangeEvent event) {
								String selected = selectionModel
										.getSelectedObject();

								as.setProtocol(StorageManager.INSTANCE
										.getProtocol(GeometryNames
												.getShortName(as
														.getGeometryChoice()),
												selected));
								labelProtocolRepresentation.setText(as
										.getProtocol().toString());
								mixNowButton.setEnabled(true);

								loadPanel.hide();
							}
						});

				// Set the total row count. This isn't strictly necessary, but
				// it affects
				// paging calculations, so its good habit to keep the row count
				// up to date.
				cellList.setRowCount(geometryProtocols.size(), true);

				// Push the data into the widget.
				cellList.setRowData(0, geometryProtocols);

				loadVerticalPanel.add(cellList);
				loadVerticalPanel.add(closeLoadButton);
				loadPanel.center();
			}
		});
	}

	/*
	 * Initialises the loadInitDistButton. When pressed, this button allows a
	 * user to load an initial distribution to the canvas. Also initialises the
	 * corresponding loading pop up.
	 */
	private void createLoadInitDistButton() {
		loadInitDistButton = new Button("Load Initial Distribution");

		loadInitDistButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				loadVerticalPanel = new VerticalPanel();
				loadPanel = new PopupPanel();
				loadPanel.setModal(true);
				loadPanel.add(loadVerticalPanel);
				closeLoadButton = new Button("Close");

				closeLoadButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						loadPanel.removeFromParent();
					}
				});

				// Get all initial distributions for current geometry
				List<String> geometryDistributions = StorageManager.INSTANCE
						.getDistributions(GeometryNames.getShortName(as
								.getGeometryChoice()));

				// Create a cell to render each value.
				TextCell textCell = new TextCell();

				// Create a CellList that uses the cell.
				CellList<String> cellList = new CellList<String>(textCell);
				cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

				// Add a selection model to handle user selection.
				final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
				cellList.setSelectionModel(selectionModel);
				selectionModel
						.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
							public void onSelectionChange(
									SelectionChangeEvent event) {
								String selected = selectionModel
										.getSelectedObject();

								// get the selected initial distribution, and
								// set it in the AS
								double[] dist = StorageManager.INSTANCE
										.getDistribution(GeometryNames
												.getShortName(as
														.getGeometryChoice()),
												selected);
								as.setInitialDistribution(dist);
								as.drawDistribution();
								loadPanel.removeFromParent();
							}
						});

				// Set the total row count. This isn't strictly necessary, but
				// it affects
				// paging calculations, so its good habit to keep the row count
				// up to date.
				cellList.setRowCount(geometryDistributions.size(), true);

				// Push the data into the widget.
				cellList.setRowData(0, geometryDistributions);

				loadVerticalPanel.add(cellList);
				loadVerticalPanel.add(closeLoadButton);
				loadPanel.center();
			}
		});

	}

	/*
	 * Initialises the removeSavedResultsButton. When pressed, this button
	 * allows a user to remove a previously saved mixing run
	 */
	// TODO : refactor this method so that it uses the StorageManager
	private void createRemoveSavedResultsButton() {
		// TODO: The text 'Remove Saved Results' should be translated later on
		removeSavedResultsButton = new Button("Remove Saved Results");
		removeResultsVerticalPanel = new VerticalPanel();
		removeResultsPanel = new PopupPanel();
		removeResultsPanel.setModal(true);
		removeResultsPanel.add(removeResultsVerticalPanel);

		closeResultsButton = new Button("Close");
		resultsFlexTable = new FlexTable();

		removeSavedResultsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				resultsFlexTable.removeFromParent();
				closeResultsButton.removeFromParent();
				resultsFlexTable = new FlexTable();
				removeResultsVerticalPanel.add(resultsFlexTable);
				removeResultsVerticalPanel.add(closeResultsButton);

				resultsFlexTable.setText(0, 0, "File name");
				resultsFlexTable.setText(0, 1, "Remove");

				resultsFlexTable.getRowFormatter().addStyleName(0,
						"removeListHeader");
				resultsFlexTable.addStyleName("removeList");

				final ArrayList<String> names = (ArrayList<String>) StorageManager.INSTANCE
						.getResults();
				for (int i = 0; i < names.size(); i++) {
					final int row = i + 1;
					final String name = names.get(i);
					resultsFlexTable.setText(row, 0, name);
					Button removeStockButton = new Button("x");
					removeStockButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							int removedIndex = names.indexOf(name);
							names.remove(removedIndex);
							StorageManager.INSTANCE.removeResult(name);
							resultsFlexTable.removeRow(removedIndex + 1);
						}
					});
					resultsFlexTable.setWidget(row, 1, removeStockButton);
				}

				removeResultsPanel.center();
				// .setPopupPositionAndShow(new PopupPanel.PositionCallback() {
				// public void setPosition(int offsetWidth,
				// int offsetHeight) {
				// int left = (Window.getClientWidth() - offsetWidth) / 2;
				// int top = (Window.getClientHeight() - offsetHeight) / 2;
				// removeResultsPanel.setPopupPosition(left, top);
				// }
				// });
			}
		});

		closeResultsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				removeResultsPanel.hide();
			}
		});
	}

	/**
	 * Updates the protocol label to show the textual representation of
	 * {@code step} and adds this to the existing steps in the protocol.
	 * 
	 * @param step
	 *            The new {@code Step} of which the textual representation
	 *            should be added.
	 */
	private void updateProtocolLabel(MixingStep step) {
		String oldProtocol = labelProtocolRepresentation.getText();
		String stepString = step.toString();
		if (stepString.charAt(0) == 'B' || stepString.charAt(0) == 'T') {
			stepString = "&nbsp;" + stepString;
		}

		labelProtocolRepresentation.setVisible(true);
		labelProtocolRepresentation.getElement().setInnerHTML(oldProtocol + stepString + " ");
	}

	/**
	 * <p>
	 * Show or hide an overlay with a loading animation in the centre. Making
	 * this panel visible will make it impossible for the user to give input.
	 * </p>
	 * 
	 * <p>
	 * When hiding the panel, the message will also be reset. Change it with
	 * {@link #setLoadingPanelMessage}.
	 * </p>
	 * 
	 * @param visible
	 *            If the panel should be hidden or shown.
	 */
	protected void setLoadingPanelVisible(boolean visible) {
		if (visible) {
			if (DOM.getElementById(LOADINGPANEL_ID) == null) {
				RootPanel.get().add(loadingPanel);
			}
		} else {
			if (DOM.getElementById(LOADINGPANEL_ID) != null) {
				loadingPanel.removeFromParent();
				setLoadingPanelMessage(null);
			}
		}
	}

	private void createComparePerformanceButton() {
		// TODO : translate the text on all these buttons
		comparePerformanceButton = new Button("Compare Performance");
		compareButton = new Button("Compare");
		cancelCompareButton = new Button("Cancel");
		closeCompareButton = new Button("Close");
		newCompareButton = new Button("New Comparison");
		compareSelectPopupPanel = new PopupPanel();
		compareSelectPopupPanel.setModal(true);
		comparePopupPanel = new PopupPanel();
		comparePopupPanel.setModal(true);
		compareGraphPanel = new SimplePanel();

		// Initialise the cellList to contain all the mixing runs
		TextCell textCell = new TextCell();
		final CellList<String> cellList = new CellList<String>(textCell);
		final MultiSelectionModel<String> selectionModel = new MultiSelectionModel<String>();
		final Handler<String> selectionEventManager = DefaultSelectionEventManager
				.createCheckboxManager();
		cellList.setSelectionModel(selectionModel, selectionEventManager);
		cellList.addCellPreviewHandler(new Handler<String>() {

			@Override
			public void onCellPreview(CellPreviewEvent<String> event) {
				if (BrowserEvents.CLICK
						.equals(event.getNativeEvent().getType())) {

					final String value = event.getValue();
					final Boolean state = !event.getDisplay()
							.getSelectionModel().isSelected(value);
					event.getDisplay().getSelectionModel()
							.setSelected(value, state);
					event.setCanceled(true);
				}
			}
		});

		// ----------------------------------------------------------

		// Initialise all components of the second popup panel
		final VerticalPanel vertPanel = new VerticalPanel();
		HorizontalPanel horPanel = new HorizontalPanel();
		horPanel.add(closeCompareButton);
		horPanel.add(newCompareButton);
		comparePopupPanel.add(vertPanel);
		vertPanel.add(compareGraphPanel);
		vertPanel.add(horPanel);

		closeCompareButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Set<String> selected = selectionModel.getSelectedSet();
				for (String s : selected) {
					selectionModel.setSelected(s, false);
				}
				comparePopupPanel.hide();
			}
		});

		newCompareButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Set<String> selected = selectionModel.getSelectedSet();
				for (String s : selected) {
					selectionModel.setSelected(s, false);
				}
				comparePopupPanel.hide();
				compareSelectPopupPanel.show();
			}
		});

		// ------------------------------------------------------

		// Initialise all components of the first popup panel
		VerticalPanel compareVerticalPanel = new VerticalPanel();
		compareSelectPopupPanel.add(compareVerticalPanel);
		compareVerticalPanel.add(cellList);
		HorizontalPanel compareHorizontalPanel = new HorizontalPanel();
		compareVerticalPanel.add(compareHorizontalPanel);
		compareHorizontalPanel.add(compareButton);
		compareHorizontalPanel.add(cancelCompareButton);

		compareButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ArrayList<String> names = new ArrayList<String>();
				ArrayList<double[]> graphs = new ArrayList<double[]>();
				Set<String> chosenNames = selectionModel.getSelectedSet();
				for (String s : chosenNames) {
					names.add(s);
					ResultStorage rs = StorageManager.INSTANCE.getResult(s);
					if (rs == null) {
						System.out.println("Ik ben null");
					}
					// System.out.println(StorageManager.INSTANCE.getResult(s));
					graphs.add(StorageManager.INSTANCE.getResult(s)
							.getSegregation());
				}

				compareGraphPanel.clear();
				createGraph(compareGraphPanel, names, graphs, new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Boolean result) {
						compareSelectPopupPanel.hide();
						comparePopupPanel.center();
					}
				});
			}
		});

		cancelCompareButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Set<String> selected = selectionModel.getSelectedSet();
				for (String s : selected) {
					selectionModel.setSelected(s, false);
				}
				compareSelectPopupPanel.hide();
			}

		});

		comparePerformanceButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ArrayList<String> resultNames = (ArrayList<String>) StorageManager.INSTANCE
						.getResults();

				cellList.setRowCount(resultNames.size());

				// Push the data into the widget.
				cellList.setRowData(0, resultNames);

				compareSelectPopupPanel
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								compareSelectPopupPanel.center();
							}
						});
			}
		});
		// ---------------------------------------------------------
	}

	/**
	 * Adds a linechart-graph of the {@code performance} to {@code panel}
	 * 
	 * @param panel
	 *            The panel the chart will be added to
	 * @param names
	 *            List of names of the different plots in the chart
	 * @param performance
	 *            Values of the different plots
	 */
	private void createGraph(Panel panel, ArrayList<String> names,
			ArrayList<double[]> performance, AsyncCallback<Boolean> onLoad) {

		graphVisualisator = new GraphVisualisator();
		// Adds the graph to the Panel-parameter of
		// visualisator.getOnLoadCallBack()
		try {
			VisualizationUtils.loadVisualizationApi(
					graphVisualisator.createGraph(panel, names, performance, onLoad),
					LineChart.PACKAGE);
		} catch (Exception e) {
			Window.alert("Loading graph failed.");
			e.printStackTrace();
		}
	}

	/*
	 * Initialises the define Protocol checkbox. When this button is pressed,
	 * the current protocol is reset, and the protocol widgets are shown/hidden.
	 */
	private void createDefineProtocolCheckBox() {
		// TODO: The text 'Define Protocol' should be translated later on
		defineProtocolCheckBox = new CheckBox("Define Protocol");
		defineProtocolCheckBox.ensureDebugId("defineProtocolCheckbox");
		defineProtocolCheckBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (defineProtocolCheckBox.getValue()) {
					toggleProtocolWidgets(true);
				} else {
					resetProtocol();
					toggleProtocolWidgets(false);
				}
			}
		});
	}

	/*
	 * resets the current protocol and the protocol widgets
	 */
	private void resetProtocol() {
		as.setProtocol(new MixingProtocol());
		labelProtocolRepresentation.setText("");
		as.setNrSteps(NRSTEPS_DEFAULT);
		nrStepsSpinner.setValue(NRSTEPS_DEFAULT);
		mixNowButton.setEnabled(false);
	}

	/*
	 * Initialises the mixNow button. When pressed, the current protocol is
	 * executed. TODO: When this button is disabled, hovering it should not make
	 * it appear 'active'
	 */
	private void createMixNowButton() {
		// TODO: The text 'Mix Now' should be translated later on
		mixNowButton = new Button("Mix Now");
		mixNowButton.ensureDebugId("mixNowButton");
		mixNowButton.setEnabled(false);
		mixNowButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				executeMixingRun(as.getProtocol());
			}

		});
	}

	/**
	 * If the {@code Define Protocol} checkbox is ticked, this method adds a new
	 * {@code MixingStep} to the mixing protocol, and updates the text area
	 * {@code taProtocolRepresentation} accordingly.
	 * 
	 * @param step
	 *            The {@code MixingStep} to be added.
	 */
	private void addStep(MixingStep step) {
		if (defineProtocolCheckBox.getValue()) {
			step.setStepSize(as.getStepSize());
			as.addMixingStep(step);

			updateProtocolLabel(step);
			mixNowButton.setEnabled(true);
		} else {
			MixingProtocol protocol = new MixingProtocol();
			step.setStepSize(as.getStepSize());
			protocol.addStep(step);
			executeMixingRun(protocol);
		}
	}

	/**
	 * Saves the initial distribution. Sends all current information about the
	 * protocol and the distribution to the server. Displays the results on
	 * screen.
	 */
	private void executeMixingRun(final MixingProtocol protocol) {
		setLoadingPanelMessage("Preparing data...");
		setLoadingPanelVisible(true);

		// Now use timers, to make sure the loading panel is set up and shown
		// correctly
		// before we start doing some heavy calculations and simulation...
		// Basically, we simply do a 'setTimeout' JavaScript call here
		final Timer doEvenLaterTimer = new Timer() {
			@Override
			public void run() {
				Simulation simulation = new Simulation(as.getMixerChoice(),
						protocol, as.getInitialDistribution(), as.getNrSteps(),
						false);

				TimeoutRpcRequestBuilder timeoutRpcRequestBuilder = new TimeoutRpcRequestBuilder(
						10000);
				SimulatorServiceAsync service = GWT
						.create(SimulatorService.class);
				((ServiceDefTarget) service)
						.setRpcRequestBuilder(timeoutRpcRequestBuilder);
				AsyncCallback<SimulationResult> callback = new AsyncCallback<SimulationResult>() {
					@Override
					public void onSuccess(SimulationResult result) {
						as.getGeometry().drawDistribution(
								result.getConcentrationVectors()[result
										.getConcentrationVectors().length - 1]);
						as.setSegregation(result.getSegregationPoints());
						saveResultsButton.setEnabled(true);
						viewSingleGraph.setEnabled(true);
						setLoadingPanelVisible(false);
					}

					@Override
					public void onFailure(Throwable caught) {
						setLoadingPanelVisible(false);
						if (caught instanceof RequestTimeoutException) {
							showError("The simulation server did not respond in"
									+ " time. Try again later");
						} else {
							showError("Could not reach the server, try again later.");
						}
					}
				};
				// Call the service
				service.simulate(simulation, callback);
			}
		};

		Timer doLaterTimer = new Timer() {
			@Override
			public void run() {
				as.setInitialDistribution(as.getGeometry().getDistribution());

				setLoadingPanelMessage("Running the simulation. Please wait...");
				doEvenLaterTimer.schedule(100);
			}
		};
		doLaterTimer.schedule(100);
	}

}