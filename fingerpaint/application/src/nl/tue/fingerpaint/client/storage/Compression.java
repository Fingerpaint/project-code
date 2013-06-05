package nl.tue.fingerpaint.client.storage;

public class Compression {

	public Compression() {
	}

	public static native String zip(String input) /*-{
		var zip = new $wnd.JSZip();
		zip.file("Hello.txt", input, {
			compression : "DEFLATE"
		});
		var content = zip.generate();
		//location.href="data:application/zip;base64,"+content;
		return content;
	}-*/;

	public static native String unzip(String input) /*-{
		var zip = new $wnd.JSZip();
		zip.load(input);
		var content = zip.generate();
		return content;
	}-*/;

}
