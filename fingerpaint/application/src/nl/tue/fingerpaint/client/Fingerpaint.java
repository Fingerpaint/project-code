package nl.tue.fingerpaint.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.tue.fingerpaint.client.gui.GraphVisualisator;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.gui.NotificationPanel;
import nl.tue.fingerpaint.client.gui.NumberSpinnerListener;
import nl.tue.fingerpaint.client.gui.ToggleColourButton;
import nl.tue.fingerpaint.client.gui.drawingtool.CircleDrawingTool;
import nl.tue.fingerpaint.client.gui.drawingtool.SquareDrawingTool;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.model.Geometry.StepAddedListener;
import nl.tue.fingerpaint.client.model.MixingProtocol;
import nl.tue.fingerpaint.client.model.MixingStep;
import nl.tue.fingerpaint.client.model.RectangleGeometry;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.resources.FingerpaintResources;
import nl.tue.fingerpaint.client.serverdata.ServerDataCache;
import nl.tue.fingerpaint.client.simulator.Simulation;
import nl.tue.fingerpaint.client.simulator.SimulationResult;
import nl.tue.fingerpaint.client.simulator.SimulatorService;
import nl.tue.fingerpaint.client.simulator.SimulatorServiceAsync;
import nl.tue.fingerpaint.client.storage.ResultStorage;
import nl.tue.fingerpaint.client.storage.StorageManager;
import nl.tue.fingerpaint.shared.GeometryNames;

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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
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
	// ---- PROTECTED GLOBALS
	// ---------------------------------------------------------------------
	/** Class to keep track of everything the user has selected */
	protected ApplicationState as;

	/** Holds the mixingPerformance of the last run. */
	protected GraphVisualisator graphVisualisator;

	/**
	 * String to determine the save button (either distribution, results or
	 * protocol) that was clicked last.
	 */
	protected String lastSaveButtonClicked;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Set IDs or debug IDs.
		GuiState.setIDs();
		
		// Load CSS
		FingerpaintResources.INSTANCE.css().ensureInjected();

		// Initialise the loading panel
		// Add animation image
		Image loadImage = new Image(FingerpaintResources.INSTANCE.loadImage()
				.getSafeUri());
		GuiState.loadingPanel.add(loadImage);

		// Add label that may contain explanatory text
		GuiState.loadingPanelMessage = new Label(
				FingerpaintConstants.INSTANCE.loadingGeometries(), false);
		GuiState.loadingPanel.add(GuiState.loadingPanelMessage);

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
					showError(FingerpaintConstants.INSTANCE
							.simulationRequestTimeout());
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
		verPanel.add(new Label(FingerpaintConstants.INSTANCE.errorOccured()));
		if (message != null) {
			verPanel.add(new Label(message));
		}
		verPanel.add(new Button(FingerpaintConstants.INSTANCE.btnClose(),
				new ClickHandler() {
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

		GuiState.loadingPanelMessage.setText(message);
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
			as.setGeometry(new RectangleGeometry(Window.getClientHeight() - 20,
					Window.getClientWidth() - 20));
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
			GuiState.menuPanel.add(GuiState.toolSelectButton);

			// Initialise toggleButton and add to
			// menuPanel
			GuiState.toggleColor = new ToggleColourButton(as);
			GuiState.menuPanel.add(GuiState.toggleColor);

			// Initialise the loadDistButton and add to
			// menuPanel
			// createLoadDistButton();
			// menuPanel.add(loadDistButton);

			// Initialise the resetDistButton and add to menuPanel
			createResetDistButton();
			GuiState.menuPanel.add(GuiState.resetDistButton);

			// Initialise the saveProtocolButton and add it to the menuPanel
			createSaveDistributionButton();
			GuiState.menuPanel.add(GuiState.saveDistributionButton);

			// Initialise the loadInitDistButton and add it to the menuPanel
			createLoadInitDistButton();
			GuiState.menuPanel.add(GuiState.loadInitDistButton);

			// Initialise the saveResultsButton and add it to the menuPanel
			createSaveResultsButton();
			GuiState.menuPanel.add(GuiState.saveResultsButton);

			createSaveWidgets();

			// Initialise the removeSavedResultsButton and add it to the
			// menuPanel
			createRemoveSavedResultsButton();
			GuiState.menuPanel.add(GuiState.removeSavedResultsButton);

			createViewSingleGraphButton();
			GuiState.menuPanel.add(GuiState.viewSingleGraphButton);

			// Initialise the comparePerformanceButton and add it to the
			// menuPanel
			createComparePerformanceButton();
			GuiState.menuPanel.add(GuiState.comparePerformanceButton);

			// Initialise a spinner for changing the length of a mixing protocol
			// step and add to menuPanel.
			createStepSizeSpinner();
			GuiState.menuPanel.add(GuiState.sizeLabel);
			GuiState.menuPanel.add(GuiState.sizeSpinner);

			// Initialise the checkbox that indicates whether a protocol is
			// being defined, or single steps have to be executed and add to
			// menu panel
			createDefineProtocolCheckBox();
			GuiState.menuPanel.add(GuiState.defineProtocolCheckBox);

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
			protocolPanel.add(GuiState.nrStepsLabel);
			protocolPanel.add(GuiState.nrStepsSpinner);
			protocolPanel.add(GuiState.labelProtocolLabel);
			protocolPanel.add(GuiState.labelProtocolRepresentation);
			protocolPanel.add(GuiState.mixNowButton);
			protocolPanel.add(GuiState.resetProtocolButton);
			protocolPanel.add(GuiState.saveProtocolButton);
			protocolPanel.add(GuiState.loadProtocolButton);

			GuiState.protocolPanelContainer.setAnimationEnabled(true);
			GuiState.protocolPanelContainer.add(protocolPanel);
			GuiState.protocolPanelContainer.setVisible(false);

			toggleProtocolWidgets(false);

			// Add canvas and menuPanel to the page
			RootPanel.get().add(as.getGeometry().getCanvas());
			GuiState.menuPanelWrapper.add(GuiState.menuPanel);
			RootPanel.get().add(GuiState.menuPanelWrapper);
			GuiState.menuToggleButton.refreshMenuSize();
			RootPanel.get().add(GuiState.menuToggleButton);
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
		GuiState.sizeSpinner.addStyleName("sizeSpinnerInput");
		as.setStepSize(MixingStep.STEP_DEFAULT);

		// set a listener for the spinner
		GuiState.sizeSpinner.setSpinnerListener(new NumberSpinnerListener() {

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
		GuiState.resetDistButton.addClickHandler(new ClickHandler() {

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
			GuiState.protocolPanelContainer.showRelativeTo(GuiState.menuPanel);
		} else {
			GuiState.protocolPanelContainer.hide();
		}
	}

	/*
	 * Initialises the spinner for the nrSteps.
	 */
	private void createNrStepsSpinner() {
		//Initialise the initial value in the ApplicationState class.
		as.setNrSteps(GuiState.NRSTEPS_DEFAULT);

		// The spinner for #steps should update the nrSteps variable whenever
		// the value is changed.
		GuiState.nrStepsSpinner.setSpinnerListener(new NumberSpinnerListener() {

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
		GuiState.cursorSizeSpinner
				.setSpinnerListener(new NumberSpinnerListener() {

					@Override
					public void onValueChange(double value) {
						as.getGeometry().setDrawingToolSize((int) value - 1);
						// -1 because the drawingTools have a default size of 1
						// pixel
						// for inputSize 0
					}
				});
	}

	/*
	 * Initialises the protocol representation text area. TODO: this code has to
	 * be removed!
	 */
	private void createProtocolRepresentationTextArea() {
		GuiState.labelProtocolRepresentation.setVisible(false);
	}

	/*
	 * Initialises the tool selector, including buttons to select the shape of
	 * the tool, and the slider to select the size of the tool
	 */
	private void createToolSelector() {
		GuiState.squareDrawingTool.addClickHandler(new ClickHandler() {

			/*
			 * Select the square drawing tool when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {
				if (!GuiState.squareDrawingTool.isDown()) {
					GuiState.squareDrawingTool.setDown(true);
				} else {
					as.getGeometry().setDrawingTool(
							new SquareDrawingTool(getCursorSize()));

					GuiState.circleDrawingTool.setDown(false);
				}
			}
		});
		// Initial drawing tool is square
		GuiState.squareDrawingTool.setDown(true);

		GuiState.circleDrawingTool.addClickHandler(new ClickHandler() {

			/*
			 * Select the square drawing tool when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {

				if (!GuiState.circleDrawingTool.isDown()) {
					GuiState.circleDrawingTool.setDown(true);
				} else {
					as.getGeometry().setDrawingTool(
							new CircleDrawingTool(getCursorSize()));

					GuiState.squareDrawingTool.setDown(false);
				}
			}
		});

		// -- Add all Drawings Tools below ---------------------
		GuiState.popupPanelMenu.add(GuiState.squareDrawingTool);
		GuiState.popupPanelMenu.add(GuiState.circleDrawingTool);

		// --TODO: Add DrawingTool Size slider below ----------------
		GuiState.popupPanelPanel.add(GuiState.popupPanelMenu);
		GuiState.popupPanelPanel.add(GuiState.cursorSizeSpinner);

		// Add everything to the popup panel
		GuiState.toolSelector.add(GuiState.popupPanelPanel);

		// Create the button the triggers the popup panel
		GuiState.toolSelectButton.addClickHandler(new ClickHandler() {

			/*
			 * Show the popupPanel when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {
				GuiState.toolSelector
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								int left = (Window.getClientWidth()
										- offsetWidth - 75);
								int top = 40;
								GuiState.toolSelector.setPopupPosition(left,
										top);
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
		return (int) GuiState.cursorSizeSpinner.getValue() - 1;
	}

	/*
	 * Initialises the resetProtocol button. When pressed, this button sets a
	 * new (and empty) protocol in the application state, and it clear the
	 * protocol representation text area.
	 */
	private void createResetProtocolButton() {
		GuiState.resetProtocolButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				resetProtocol();
				// When reset protocol is pressed, the save results button is
				// also disabled.
				GuiState.saveResultsButton.setEnabled(false);
			}

		});
	}

	/*
	 * Initialises the saveProtocol Button. When this button is pressed, the
	 * currently defined protocol is saved.
	 */
	private void createSaveProtocolButton() { 
		GuiState.saveProtocolButton.setEnabled(true);

		GuiState.saveProtocolButton.addClickHandler(new ClickHandler() {

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
		GuiState.saveResultsButton.setEnabled(true);

		GuiState.saveResultsButton.addClickHandler(new ClickHandler() {

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
		GuiState.saveDistributionButton.setEnabled(true);

		GuiState.saveDistributionButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveDistributionButtonOnClick();
			}
		});
	}

	private void createViewSingleGraphButton() {
		GuiState.viewSingleGraphButton.setEnabled(false);
		GuiState.viewSingleGraphPopupPanel.setModal(true);
		GuiState.viewSingleGraphHorizontalPanel
				.add(GuiState.exportSingleGraphButton);
		GuiState.viewSingleGraphHorizontalPanel
				.add(GuiState.closeSingleGraphViewButton);

		GuiState.exportSingleGraphButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO: Insert Method-call which exports the graph
			}
		});
		GuiState.closeSingleGraphViewButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GuiState.viewSingleGraphPopupPanel.hide();
			}
		});

		GuiState.viewSingleGraphButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ArrayList<double[]> performance = new ArrayList<double[]>();
				performance.add(as.getSegregation());

				// Clear old data
				if (graphVisualisator != null) {
					graphVisualisator.clearSegregationResults();
				}
				GuiState.viewSingleGraphPopupPanel.clear();
				GuiState.viewSingleGraphVerticalPanel.clear();
				GuiState.viewSingleGraphGraphPanel.clear();

				// Make graph and add it to viewSingleGraphVerticalPanel
				createGraph(
						GuiState.viewSingleGraphGraphPanel,
						new ArrayList<String>(Arrays
								.asList("Current mixing run")), performance,
						new AsyncCallback<Boolean>() {
							@Override
							public void onFailure(Throwable caught) {
								caught.printStackTrace();
							}

							@Override
							public void onSuccess(Boolean result) {
								GuiState.viewSingleGraphVerticalPanel
										.add(GuiState.viewSingleGraphGraphPanel);
								GuiState.viewSingleGraphVerticalPanel
										.add(GuiState.viewSingleGraphHorizontalPanel);
								GuiState.viewSingleGraphPopupPanel
										.add(GuiState.viewSingleGraphVerticalPanel);
								GuiState.viewSingleGraphPopupPanel.center();
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
		GuiState.overwriteSavePanel.setModal(true);
		GuiState.overwriteSaveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				overwriteSaveButtonOnClick();
			}
		});

		// Hide both popup panels if the OK button was pressed. Hide only the
		// second panel if the cancel button was pressed.
		GuiState.closeSaveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				closeSaveButtonOnClick();
			}
		});

		// add all components to second popup panel
		GuiState.overwriteSavePanel.add(GuiState.overwriteSaveVerticalPanel);
		GuiState.overwriteSaveVerticalPanel.add(GuiState.saveMessageLabel);
		GuiState.overwriteSaveVerticalPanel.add(GuiState.overwriteButtonsPanel);
		GuiState.overwriteButtonsPanel.add(GuiState.closeSaveButton);
	}

	private void createSavePanel() {
		GuiState.saveItemPanel.setModal(true);

		// Sets the name to use for saving
		GuiState.saveNameTextBox.setMaxLength(30);

		// Determine whether user input is valid. Enable/disable the save
		// button. Execute save when ENTER is pressed.
		GuiState.saveNameTextBox.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				handleKeyPress(event);
			}
		});

		// Initially, the save button is disabled; it will become available if
		// "Mix Now" is pressed.
		GuiState.saveItemPanelButton.setEnabled(false);
		GuiState.saveItemPanelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				savePanelButtonOnClick();
			}
		});

		// Hide the first popup panel when the cancel button is pressed
		GuiState.cancelSaveResultsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GuiState.saveItemPanel.hide();
				GuiState.saveNameTextBox.setText("");
				GuiState.saveItemPanelButton.setEnabled(false);
			}
		});

		// add all components to first popuppanel
		GuiState.saveItemPanel.add(GuiState.saveItemVerticalPanel);
		GuiState.saveItemVerticalPanel.add(GuiState.saveNameTextBox);
		GuiState.saveItemVerticalPanel.add(GuiState.saveButtonsPanel);
		GuiState.saveButtonsPanel.add(GuiState.saveItemPanelButton);
		GuiState.saveButtonsPanel.add(GuiState.cancelSaveResultsButton);
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
		// TODO: We need more than a boolean to indicate exit status: if something
		// goes wrong, we return false, causing the "Name already in use"
		// message to appear, which is incorrect in some cases.
		boolean success = save(GuiState.saveNameTextBox.getText(), false);
		if (success) {
			new NotificationPanel(FingerpaintConstants.INSTANCE.saveSuccess()).show(GuiState.SAVE_SUCCESS_TIMEOUT);
			GuiState.saveItemPanel.hide();
		} else {
			GuiState.saveMessageLabel.setText(FingerpaintConstants.INSTANCE.nameInUse());

			GuiState.overwriteButtonsPanel.remove(GuiState.closeSaveButton);
			GuiState.overwriteButtonsPanel.add(GuiState.overwriteSaveButton);
			GuiState.overwriteButtonsPanel.add(GuiState.closeSaveButton);

			GuiState.overwriteSavePanel.center();
			GuiState.overwriteSavePanel.show();
			GuiState.saveItemPanel.hide();
		}
	}

	private void overwriteSaveButtonOnClick() {
		save(GuiState.saveNameTextBox.getText(), true);

		NotificationPanel np = new NotificationPanel(
				FingerpaintConstants.INSTANCE.saveSuccess());
		np.show(GuiState.SAVE_SUCCESS_TIMEOUT);
		GuiState.overwriteSavePanel.hide();
	}

	private void showSavePanel() {
		GuiState.saveItemPanel.center();
		GuiState.saveItemPanel.show();
		GuiState.saveNameTextBox.setText("");
		GuiState.saveNameTextBox.setFocus(true);
	}

	private void closeSaveButtonOnClick() {
		GuiState.overwriteSavePanel.hide();
		if (!GuiState.closeSaveButton.getText().equals("OK")) {
			GuiState.overwriteSavePanel.remove(GuiState.overwriteSaveButton);
			GuiState.saveItemPanel.show();
			GuiState.saveNameTextBox.setSelectionRange(0,
					GuiState.saveNameTextBox.getText().length());
			GuiState.saveNameTextBox.setFocus(true);
		} else {
			
			GuiState.saveNameTextBox.setText("");
			GuiState.saveItemPanelButton.setEnabled(false);
		}
	}

	private void handleKeyPress(KeyPressEvent event) {
		String text = GuiState.saveNameTextBox.getText();
		String inputCharacter = Character.toString(event.getCharCode());
		int textlength = text.length();
		if (inputCharacter
				.matches("[~`!@#$%^&*()+={}\\[\\]:;\"|\'\\\\<>?,./\\s]")) {
			GuiState.saveNameTextBox.cancelKey();
		} else if (inputCharacter.matches("[A-Za-z0-9]")) {
			textlength++;
		} else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE) {
			textlength--;
		} else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			GuiState.saveItemPanelButton.click();
		}
		GuiState.saveItemPanelButton.setEnabled(textlength > 0);
	}

	/**
	 * Saves the current protocol, distribution or mixing results, depending on
	 * which save-button was pressed last.
	 * 
	 * @param name
	 *            Name of save "file".
	 * @param canOverwrite
	 *            If we can overwrite an already-existing "file" with the given
	 *            name or not.
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

			try {
				return StorageManager.INSTANCE
					.putResult(name, result, canOverwrite);
			} catch (Exception e) {
				GWT.log("Saving results encountered an error", e);
				return false;
			}
		}
		return false;
	}

	/*
	 * Initialises the loadProtocolButton. When pressed, this button allows a
	 * user to load a protocol. Also initialises the corresponding loading
	 * popup.
	 */
	private void createLoadProtocolButton() {
		GuiState.loadProtocolButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GuiState.loadPanel = new PopupPanel();
				GuiState.loadPanel.setModal(true);
				GuiState.loadPanel.add(GuiState.loadVerticalPanel);
				GuiState.closeLoadButton = new Button("Close");

				GuiState.closeLoadButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						GuiState.loadPanel.removeFromParent();
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
								GuiState.labelProtocolRepresentation.setText(as
										.getProtocol().toString());
								GuiState.mixNowButton.setEnabled(true);

								GuiState.loadPanel.hide();
							}
						});

				// Set the total row count. This isn't strictly necessary, but
				// it affects
				// paging calculations, so its good habit to keep the row count
				// up to date.
				cellList.setRowCount(geometryProtocols.size(), true);

				// Push the data into the widget.
				cellList.setRowData(0, geometryProtocols);

				GuiState.loadVerticalPanel.add(cellList);
				GuiState.loadVerticalPanel.add(GuiState.closeLoadButton);
				GuiState.loadPanel.center();
			}
		});
	}

	/*
	 * Initialises the loadInitDistButton. When pressed, this button allows a
	 * user to load an initial distribution to the canvas. Also initialises the
	 * corresponding loading pop up.
	 */
	private void createLoadInitDistButton() {
		GuiState.loadInitDistButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				GuiState.loadVerticalPanel = new VerticalPanel();
				GuiState.loadPanel.setModal(true);
				GuiState.loadPanel.add(GuiState.loadVerticalPanel);

				GuiState.closeLoadButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						GuiState.loadPanel.removeFromParent();
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
								GuiState.loadPanel.removeFromParent();
							}
						});

				// Set the total row count. This isn't strictly necessary, but
				// it affects
				// paging calculations, so its good habit to keep the row count
				// up to date.
				cellList.setRowCount(geometryDistributions.size(), true);

				// Push the data into the widget.
				cellList.setRowData(0, geometryDistributions);

				GuiState.loadVerticalPanel.add(cellList);
				GuiState.loadVerticalPanel.add(GuiState.closeLoadButton);
				GuiState.loadPanel.center();
			}
		});

	}

	/*
	 * Initialises the removeSavedResultsButton. When pressed, this button
	 * allows a user to remove a previously saved mixing run
	 */
	// TODO : refactor this method so that it uses the StorageManager
	private void createRemoveSavedResultsButton() {
		GuiState.removeResultsPanel.setModal(true);
		GuiState.removeResultsPanel.add(GuiState.removeResultsVerticalPanel);

		GuiState.removeSavedResultsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GuiState.resultsFlexTable.removeFromParent();
				GuiState.closeResultsButton.removeFromParent();
				GuiState.resultsFlexTable = new FlexTable();
				GuiState.removeResultsVerticalPanel
						.add(GuiState.resultsFlexTable);
				GuiState.removeResultsVerticalPanel
						.add(GuiState.closeResultsButton);

				GuiState.resultsFlexTable.setText(0, 0, "File name");
				GuiState.resultsFlexTable.setText(0, 1, "Remove");

				GuiState.resultsFlexTable.getRowFormatter().addStyleName(0,
						"removeListHeader");
				GuiState.resultsFlexTable.addStyleName("removeList");

				final ArrayList<String> names = (ArrayList<String>) StorageManager.INSTANCE
						.getResults();
				for (int i = 0; i < names.size(); i++) {
					final int row = i + 1;
					final String name = names.get(i);
					GuiState.resultsFlexTable.setText(row, 0, name);
					Button removeStockButton = new Button("x");
					removeStockButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							int removedIndex = names.indexOf(name);
							names.remove(removedIndex);
							StorageManager.INSTANCE.removeResult(name);
							GuiState.resultsFlexTable
									.removeRow(removedIndex + 1);
						}
					});
					GuiState.resultsFlexTable.setWidget(row, 1,
							removeStockButton);
				}

				GuiState.removeResultsPanel.center();
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

		GuiState.closeResultsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GuiState.removeResultsPanel.hide();
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
		String oldProtocol = GuiState.labelProtocolRepresentation.getText();
		String stepString = step.toString();
		if (stepString.charAt(0) == 'B' || stepString.charAt(0) == 'T') {
			stepString = "&nbsp;" + stepString;
		}

		GuiState.labelProtocolRepresentation.setVisible(true);
		GuiState.labelProtocolRepresentation.getElement().setInnerHTML(
				oldProtocol + stepString + " ");
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
			if (DOM.getElementById(GuiState.LOADINGPANEL_ID) == null) {
				RootPanel.get().add(GuiState.loadingPanel);
			}
		} else {
			if (DOM.getElementById(GuiState.LOADINGPANEL_ID) != null) {
				GuiState.loadingPanel.removeFromParent();
				setLoadingPanelMessage(null);
			}
		}
	}

	private void createComparePerformanceButton() {
		GuiState.compareSelectPopupPanel.setModal(true);
		GuiState.comparePopupPanel.setModal(true);

		// Initialise the cellList to contain all the mixing runs
		TextCell textCell = new TextCell();
		final CellList<String> cellList = new CellList<String>(textCell);
		cellList.ensureDebugId("compareSelectPopupCellList");
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
		horPanel.add(GuiState.closeCompareButton);
		horPanel.add(GuiState.newCompareButton);
		GuiState.comparePopupPanel.add(vertPanel);
		vertPanel.add(GuiState.compareGraphPanel);
		vertPanel.add(horPanel);

		GuiState.closeCompareButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Set<String> selected = selectionModel.getSelectedSet();
				for (String s : selected) {
					selectionModel.setSelected(s, false);
				}
				GuiState.comparePopupPanel.hide();
			}
		});

		GuiState.newCompareButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Set<String> selected = selectionModel.getSelectedSet();
				for (String s : selected) {
					selectionModel.setSelected(s, false);
				}
				GuiState.comparePopupPanel.hide();
				GuiState.compareSelectPopupPanel.show();
			}
		});

		// ------------------------------------------------------

		// Initialise all components of the first popup panel
		VerticalPanel compareVerticalPanel = new VerticalPanel();
		GuiState.compareSelectPopupPanel.add(compareVerticalPanel);
		compareVerticalPanel.add(cellList);
		HorizontalPanel compareHorizontalPanel = new HorizontalPanel();
		compareVerticalPanel.add(compareHorizontalPanel);
		compareHorizontalPanel.add(GuiState.compareButton);
		compareHorizontalPanel.add(GuiState.cancelCompareButton);

		GuiState.compareButton.addClickHandler(new ClickHandler() {
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

				GuiState.compareGraphPanel.clear();
				createGraph(GuiState.compareGraphPanel, names, graphs,
						new AsyncCallback<Boolean>() {
							@Override
							public void onFailure(Throwable caught) {
								caught.printStackTrace();
							}

							@Override
							public void onSuccess(Boolean result) {
								GuiState.compareSelectPopupPanel.hide();
								GuiState.comparePopupPanel.center();
							}
						});
			}
		});

		GuiState.cancelCompareButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Set<String> selected = selectionModel.getSelectedSet();
				for (String s : selected) {
					selectionModel.setSelected(s, false);
				}
				GuiState.compareSelectPopupPanel.hide();
			}

		});

		GuiState.comparePerformanceButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ArrayList<String> resultNames = (ArrayList<String>) StorageManager.INSTANCE
						.getResults();

				cellList.setRowCount(resultNames.size());

				// Push the data into the widget.
				cellList.setRowData(0, resultNames);

				GuiState.compareSelectPopupPanel
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								GuiState.compareSelectPopupPanel.center();
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
			VisualizationUtils.loadVisualizationApi(graphVisualisator
					.createGraph(panel, names, performance, onLoad),
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
		GuiState.defineProtocolCheckBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (GuiState.defineProtocolCheckBox.getValue()) {
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
		GuiState.labelProtocolRepresentation.setText("");
		as.setNrSteps(GuiState.NRSTEPS_DEFAULT);
		GuiState.nrStepsSpinner.setValue(GuiState.NRSTEPS_DEFAULT);
		GuiState.mixNowButton.setEnabled(false);
	}

	/*
	 * Initialises the mixNow button. When pressed, the current protocol is
	 * executed. TODO: When this button is disabled, hovering it should not make
	 * it appear 'active'
	 */
	private void createMixNowButton() {
		GuiState.mixNowButton.setEnabled(false);
		GuiState.mixNowButton.addClickHandler(new ClickHandler() {

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
		if (GuiState.defineProtocolCheckBox.getValue()) {
			step.setStepSize(as.getStepSize());
			as.addMixingStep(step);

			updateProtocolLabel(step);
			GuiState.mixNowButton.setEnabled(true);
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
						GuiState.saveResultsButton.setEnabled(true);
						GuiState.viewSingleGraphButton.setEnabled(true);
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