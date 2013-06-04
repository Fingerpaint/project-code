package nl.tue.fingerpaint.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.DataView;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.visualizations.LineChart.Options;

/**
 * Class for the creation of segregation graphs
 * 
 * HOW TO CALL THIS CLASS: Load the visualisation API, passing the
 * onLoadCallback to be called when loading is done.
 * VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
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
	 * @param onLoad A callback to execute when the graph has been loaded.
	 * @return The runnable object to initiate drawing the graph
	 */
	public Runnable createGraph(Panel panel, ArrayList<String> names,
			ArrayList<double[]> performance, AsyncCallback<Boolean> onLoad) {
		for (int i = 0; i < names.size(); i++) {
			addSegregationResult(performance.get(i));
		}
		mixingRunNames = names;
		return getOnLoadCallBack(panel, onLoad);
	}

	/**
	 * Returns the runnable object to initiate drawing the graph.
	 * 
	 * @param panel
	 *            The panel the graph will be added to
	 * @return The runnable object to initiate drawing the graph
	 */
	private Runnable getOnLoadCallBack(final Panel panel, final AsyncCallback<Boolean> onLoad) {
		// Create a callback to be called when the visualisation API
		// has been loaded.
		return new Runnable() {
			public void run() {
				// Create a line chart visualisation.
				LineChart lc = new LineChart(createTable(), createOptions());
				panel.add(lc);
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
		xAxisLength = Math.max(xAxisLength, newPerformance.length);
		this.segregationPoints.add(newPerformance);
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
	 * @return The options to be set
	 */
	private Options createOptions() {
		Options options = Options.create();
		// TODO: What happens when this is not hard-coded anymore?
		options.setWidth(400);
		options.setHeight(240);
		options.setTitle("Mixing Performance");
		options.setTitleX("Number of steps");
		options.setTitleY("Mixing performance");
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
				data.setValue(j, 0, Integer.toString(j + 1));
				data.setValue(j, i + 1, segregationPoints.get(i)[j]);
			}
		}

		// Data view -- read only, and no location column
		DataView result = DataView.create(data);
		return result;
	}
}
