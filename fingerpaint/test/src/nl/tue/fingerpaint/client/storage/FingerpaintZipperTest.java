package nl.tue.fingerpaint.client.storage;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link FingerpaintZipper}.
 * 
 * @author Group Fingerpaint
 */
public class FingerpaintZipperTest extends GWTTestCase {

	/**
	 * A test to check whether the {@code zip} and {@code unzip} functions work
	 * correctly.
	 */
	@Test
	public void testZip() {
		final String test = "testString";
		final String result = FingerpaintZipper.unzip(FingerpaintZipper
				.zip(test));
		assertEquals("String after zipping and unzipping", test, result);
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
