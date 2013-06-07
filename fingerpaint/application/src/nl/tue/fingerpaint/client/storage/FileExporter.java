package nl.tue.fingerpaint.client.storage;

public class FileExporter {

	/**
	 * Exports the line chart that is currently shown on screen as an svg
	 * image.
	 */
	public static void exportGraph(String svg) {
		// Add tags to indicate that this should be an svg image
		svg = svg.substring(0, 4)
				+ " xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\""
				+ svg.substring(4, svg.length());
		
		promptSvgDownload(svg);
	}
	
	/**
	 * Saves a string representing an svg file to disk by showing a file download dialog.
	 * @param svg File to save, in string representation.
	 */
	private static native void promptSvgDownload(String svg) /*-{
		var blob = new Blob([svg], {type: "data:img/svg;base64"});
		$wnd.saveAs(blob, "graph.svg");
//		var saveAs = new $wnd.FileSaver(blob, "graph.svg");
	}-*/;
}
