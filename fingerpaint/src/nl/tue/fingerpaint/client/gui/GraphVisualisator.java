package nl.tue.fingerpaint.client.gui;

import java.util.ArrayList;

import nl.tue.fingerpaint.shared.utils.Colour;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.DataView;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

/**
 * <p>
 * Class for the creation of segregation graphs.
 * </p>
 * 
 * <h2>How to call this class</h2>
 * <p>
 * Load the visualisation API, passing the {@code onLoadCallback} to be called when
 * loading is done. This should be done as follows:
 * <pre>
 *   GraphVisualisator gv = new GraphVisualisator();
 *   Runnable onLoad = gv.createGraph(...);
 *   VisualizationUtils.loadVisualizationApi(onLoad, LineChart.PACKAGE);
 * </pre>
 * </p>
 * 
 * @author Group Fingerpaint
 */
public class GraphVisualisator {

	/**
	 * ArrayList containing all the lists of segregation points that have to be
	 * displayed in the graph.
	 */
	private ArrayList<double[]> segregationPoints = new ArrayList<double[]>();

	/**
	 * ArrayList containing the names of the mixing runs corresponding to the
	 * lists of segregation points.
	 */
	private ArrayList<String> mixingRunNames = new ArrayList<String>();

	/** The amount of points to be displayed along the x-axis */
	private int xAxisLength = 0;

	/** Stores the chart. */
	private LineChart lineChart;

	/**
	 * Returns the runnable object to initiate drawing the graph in
	 * Fingerpaint.createGraph()
	 * 
	 * @param panel
	 *            The panel the graph will be added to
	 * @param names
	 *            List of names of the different plots in the chart
	 * @param performance
	 *            Values of the different plots
	 * @param onLoad
	 *            A callback to execute when the graph has been loaded.
	 * @param height
	 *            The height of the image to be drawn
	 * @param width
	 *            The width of the image to be drawn
	 * @return The runnable object to initiate drawing the graph
	 */
	public Runnable createGraph(Panel panel, ArrayList<String> names,
			ArrayList<double[]> performance, AsyncCallback<Boolean> onLoad,
			int height, int width) {
		for (int i = 0; i < names.size(); i++) {
			addSegregationResult(performance.get(i));
		}
		mixingRunNames = names;
		return getOnLoadCallBack(panel, onLoad, height, width);
	}

	/**
	 * Returns the runnable object to initiate drawing the graph.
	 * 
	 * @param panel
	 *            The panel the graph will be added to.
	 * @param onLoad
	 *            The function that should be called upon completion.
	 * @param height
	 *            The height of the window in which the graph will be shown.
	 * @param width
	 *            The width of the window in which the graph will be shown.
	 * @return The runnable object to initiate drawing the graph.
	 */
	private Runnable getOnLoadCallBack(final Panel panel,
			final AsyncCallback<Boolean> onLoad, final int height,
			final int width) {
		// Create a callback to be called when the visualisation API
		// has been loaded.
		return new Runnable() {
			public void run() {
				// Create a line chart visualisation.
				lineChart = new LineChart(createTable(), createOptions(height,
						width));
				panel.add(lineChart);
				onLoad.onSuccess(true);
			}
		};
	}

	/**
	 * Adds a list of segregation points to {@code segregationPoints}.
	 * 
	 * @param newPerformance
	 *            The list of segregation points to be added
	 */
	private void addSegregationResult(double[] newPerformance) {
		double[] newPerformanceWithStartPoint = new double[newPerformance.length + 1];
		// set mix performance startvalue to 1.0
		newPerformanceWithStartPoint[0] = 1.0;
		System.arraycopy(newPerformance, 0, newPerformanceWithStartPoint, 1,
				newPerformance.length);
		xAxisLength = Math
				.max(xAxisLength, newPerformanceWithStartPoint.length);
		this.segregationPoints.add(newPerformanceWithStartPoint);
	}

	/**
	 * Clears the list of with lists of segregation points
	 */
	public void clearSegregationResults() {
		segregationPoints.clear();
		mixingRunNames.clear();
	}

	/**
	 * Creates and returns the options to set to the graph.
	 * 
	 * @param height
	 *            The height of the window in which the graph will be shown.
	 * @param width
	 *            The width of the window in which the graph will be shown.
	 * @return The options to be set
	 */
	private Options createOptions(int height, int width) {
		Options options = Options.create();
		options.setHeight(height);
		options.setWidth(width);
		options.setTitle("Mixing Performance");
		
		// horizontal axis
		AxisOptions horAxisOptions = AxisOptions.create();
		horAxisOptions.setTitle("Number of Steps");
		options.setHAxisOptions(horAxisOptions);
		
		// vertical axis
		AxisOptions verAxisOptions = AxisOptions.create();
		verAxisOptions.setTitle("Mixing Performance");
		verAxisOptions.setIsLogScale(true);
		verAxisOptions.setMinValue(0);
		verAxisOptions.setMaxValue(1);
		
		Options verGrid = Options.create();
	    verGrid.set("color", Colour.GRAY.toHexString());
	    verGrid.set("count", 2d);
	    verAxisOptions.set("gridlines", verGrid);
	    
		Options verMinorGrid = Options.create();
		verMinorGrid.set("color", Colour.LIGHT_GRAY.toHexString());
		verMinorGrid.set("count", 9d);
	    verAxisOptions.set("minorGridlines", verMinorGrid);
		
		options.setVAxisOptions(verAxisOptions);
		
		return options;
	}

	/**
	 * Returns the table to be displayed in the graph.
	 * 
	 * @return The AbstractDataTable containing all datapoints from
	 *         {@code segregationPoints}
	 */
	private AbstractDataTable createTable() {
		// Underlying data
		DataTable data = DataTable.create();

		data.addColumn(ColumnType.STRING, "Number of steps");
		for (int i = 0; i < mixingRunNames.size(); i++) {
			data.addColumn(ColumnType.NUMBER, mixingRunNames.get(i));
		}

		data.addRows(xAxisLength);
		for (int i = 0; i < segregationPoints.size(); i++) {
			for (int j = 0; j < segregationPoints.get(i).length; j++) {
				data.setValue(j, 0, Integer.toString(j));// x-value
				data.setValue(j, i + 1, segregationPoints.get(i)[j]);// y-value
			}
		}

		// Data view -- read only, and no location column
		DataView result = DataView.create(data);
		return result;
	}
}
