package nl.tue.fingerpaint.suites;

import junit.framework.Test;
import junit.framework.TestSuite;
import nl.tue.fingerpaint.client.gui.NumberSpinnerTest;
import nl.tue.fingerpaint.client.model.ApplicationStateTest;
import nl.tue.fingerpaint.client.model.DrawingToolTest;
import nl.tue.fingerpaint.client.model.RectangleGeometryTest;
import nl.tue.fingerpaint.client.storage.FingerpaintJsonizerTest;
import nl.tue.fingerpaint.client.storage.FingerpaintZipperTest;
import nl.tue.fingerpaint.client.storage.ResultStorageTest;
import nl.tue.fingerpaint.client.storage.StorageManagerTest;

import com.google.gwt.junit.tools.GWTTestSuite;

/**
 * <p> A test suite to run all the GWT unit tests for the Client side of the application. If you want to
 * run all tests in this suite, select Run As > GWT JUnit Test. <br/>
 * This test suite runs all the tests of the following classes: </p>
 * <ul>
 * <li> {@link ApplicationStateTest} </li>
 * <li> {@link DrawingToolTest} </li>
 * <li> {@link NumberSpinnerTest} </li>
 * <li> {@link RectangleGeometryTest} </li>
 * <li> {@link FingerpaintJsonizerTest} </li>
 * <li> {@link FingerpaintZipperTest} </li>
 * <li> {@link ResultStorageTest} </li>
 * <li> {@link StorageManagerTest} </li>
 * </ul>
 * 
 * @author Group Fingerpaint
 */
public class ClientSuite extends GWTTestSuite {

	public static Test suite() {
	    TestSuite suite = new TestSuite("Client Tests");
	    suite.addTestSuite(ApplicationStateTest.class); 
	    suite.addTestSuite(DrawingToolTest.class);
	    suite.addTestSuite(NumberSpinnerTest.class);
	    suite.addTestSuite(RectangleGeometryTest.class);
	    suite.addTestSuite(FingerpaintJsonizerTest.class);
	    suite.addTestSuite(FingerpaintZipperTest.class);
	    suite.addTestSuite(ResultStorageTest.class);
	    suite.addTestSuite(StorageManagerTest.class);
	    return suite;
	  }
}
