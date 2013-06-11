package suites;

import nl.tue.fingerpaint.client.gui.NumberSpinnerTest;
import nl.tue.fingerpaint.client.model.ApplicationStateTest;
import nl.tue.fingerpaint.client.model.MixingProtocolTest;
import nl.tue.fingerpaint.client.model.MixingStepTest;
import nl.tue.fingerpaint.client.model.RectangleGeometryTest;
import nl.tue.fingerpaint.client.simulator.SimulatorServiceTest;
import nl.tue.fingerpaint.client.storage.StorageManagerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * <p> A test suite to run all the GWT unit tests for the Client side of the application. If you want to
 * run all tests in this suite, select Run As > GWT JUnit Test. <br/>
 * This test suite runs all the tests of the following classes: </p>
 * <ul>
 * <li> {@link ApplicationStateTest} </li>
 * <li> {@link MixingProtocolTest} </li>
 * <li> {@link MixingStepTest} </li>
 * <li> {@link NumberSpinnerTest} </li>
 * <li> {@link RectangleGeometryTest} </li>
 * <li> {@link SimulatorServiceTest} </li>
 * <li> {@link StorageManagerTest} </li>
 * </ul>
 * 
 * @author Group Fingerpaint
 */
@RunWith(Suite.class)

// Run all the tests of each of the following classes:
@SuiteClasses({
	ApplicationStateTest.class,
	MixingProtocolTest.class,
	MixingStepTest.class, 
	NumberSpinnerTest.class,
//	RectangleGeometryTest.class,
	SimulatorServiceTest.class,
	StorageManagerTest.class
	})
public class ClientUnit extends GWTTestCase {

	/*
	 * --- IMPORTANT ----------------------------------------------------------- 
	 * The tests for RectangleGeometryTest are currently not executed, as they result in a
	 * timeout and hence, all the other tests that must be executed in a non-default browser
	 * will also fail.
	 * 
	 * All tests that are related to the local storage will possibly fail, as these tests
	 * are executed by Eclipse in a Firefox version that doesn't support local storage.
	 * To resolve this issue, proceed as follows:
	 * + Right-click on ClientUnit.java
	 * + Select "Properties"
	 * + Select "Run/Debug settings"
	 * + Remove the currently shown items (if there are any).
	 * + Click on "New".
	 * + In the pop-up that opens now ("Select Configuration Type"), choose "GWT Unit Test".
	 * + Go to the "Arguments" tab.
	 * + In "VM-arguments", type/copy the following text:
	 *    -Dgwt.args="-runStyle Manual:1"
	 * + Press "Apply" (both in this screen and the Properties-menu).
	 * + Now, you can run this test class as a GWT JUnit test (Run As > GWT JUnit test).
	 * + After a while, the Console tab will show the following text:
	 *   Please navigate your browser to this URL:
	 *   <Some URL>
	 *   Copy this URL to the browser of your choice. A dialogue is shown whether you want
	 *   to accept the connection from the GWT plug-in. Press "OK".
	 *   This may take quite some time, so be patient!
	 *   
	 * -------------------------------------------------------------------
	 */
	
	/**
	 * Returns the module name for the GWT test.
	 * @return String The name of the module.
	 */
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}
	
}
