package nl.tue.fingerpaint.client.storage;

/**
 * Convenience class that provides native JavaScript functions for compressing
 * and decompressing Strings.
 * 
 * @author Group Fingerpaint
 */
public class FingerpaintZipper {

	/**
	 * Compresses a string and puts it in a .zip file.
	 * 
	 * @param input
	 *            The string to compress
	 * @return The compressed string
	 */
	public static native String zip(String input) /*-{
		var zip = new $wnd.JSZip();
		zip.file("Name.txt", input, {
			base64 : false,
			compression : "DEFLATE"
		});
		var content = zip.generate();
		//location.href = "data:application/zip;base64," + content;
		return "#" + content;
	}-*/;

	/**
	 * Unzips a .zip file, that was zipped by {@code FingerpaintZipper.zip} and
	 * decompresses its contents.
	 * 
	 * @param input
	 *            The string representation of the .zip file
	 * @return The decompressed content of the .zip file
	 */
	public static String unzip(String input) {
		return unzipper(input.substring(1));
	}

	/**
	 * Unzips a .zip file, and decompresses its contents.
	 * 
	 * @param input
	 *            The string representation of the .zip file
	 * @return The decompressed content of the .zip file
	 */
	private static native String unzipper(String input) /*-{
		var zip = new $wnd.JSZip(input, {
			base64 : true
		});
		var file = zip.file("Name.txt");
		return file.data;
	}-*/;
}
