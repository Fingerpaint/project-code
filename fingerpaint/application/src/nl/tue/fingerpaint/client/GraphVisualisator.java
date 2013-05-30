package nl.tue.fingerpaint.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.DataView;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.visualizations.LineChart.Options;

public class GraphVisualisator {
	// HOW TO CALL THIS CLASS:
	// Load the visualization api, passing the onLoadCallback to be called
	// when loading is done.
	// VisualizationUtils.loadVisualizationApi(onLoadCallback,
	// LineChart.PACKAGE);

	private ArrayList<double[]> segregationPoints = new ArrayList<double[]>();
	private ArrayList<String> mixingRunNames = new ArrayList<String>();

	private int xAxisLength = 0;

	/**
	 * Returns the runnable object to initiate drawing the graph in Fingerpaint.createGraph()
	 * 
	 * @param panel The panel the graph will be added to
	 * @param names List of names of the different plots in the chart
	 * @param performance Values of the different plots
	 * @return
	 */
	public Runnable createGraph(Panel panel, ArrayList<String> names,
			ArrayList<double[]> performance) {
		for (int i = 0; i < names.size(); i++) {
			addSegregationResult(performance.get(i));
		}
		mixingRunNames = names;
		return getOnLoadCallBack(panel);
	}

	private Runnable getOnLoadCallBack(final Panel panel) {
		// Create a callback to be called when the visualization API
		// has been loaded.
		return new Runnable() {
			public void run() {
				// Create a line chart visualization.
				LineChart lc = new LineChart(createTable(), createOptions());
				panel.add(lc);
			}
		};
	}

	private void addSegregationResult(double[] newPerformance) {
		xAxisLength = Math.max(xAxisLength, newPerformance.length);
		this.segregationPoints.add(newPerformance);
	}

	public void clearSegregationResults() {
		segregationPoints.clear();
		mixingRunNames.clear();
	}

	private Options createOptions() {
		Options options = Options.create();
		options.setWidth(400);
		options.setHeight(240);
		options.setTitle("Mixing Performance");
		options.setTitleX("Number of steps");
		options.setTitleY("Mixing performance");
		return options;
	}

	private AbstractDataTable createTable() {
		// Underlying data
		DataTable data = DataTable.create();

		// TODO: Create as many columns as #graphs when viewing multiple
		// graphs
		data.addColumn(ColumnType.STRING, "Number of steps");
		for (int i = 0; i < mixingRunNames.size(); i++) {
			data.addColumn(ColumnType.NUMBER, mixingRunNames.get(i));
		}

		// data.setValue(rowIndex, columnIndex, value)
		// data.setValue(x-axis, mixingRun#, y-axis(value) )
		data.addRows(xAxisLength);

		for (int i = 0; i < segregationPoints.size(); i++) {
			for (int j = 0; j < segregationPoints.get(i).length; j++) {
				// set values x-axis
				data.setValue(j, 0, Integer.toString(j + 1));
				data.setValue(j, i + 1, segregationPoints.get(i)[j]);
				// data.setValue(i, 2, segregationPoints2[i]);//2nd line in
				// the
				// graph
			}

		}

		// Data view -- read only, and no location column
		DataView result = DataView.create(data);
		return result;
	}
}
