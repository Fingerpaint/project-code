package nl.tue.fingerpaint.suites;

import junit.framework.Test;
import junit.framework.TestSuite;
import nl.tue.fingerpaint.server.simulator.NativeCommunicatorTest;
import nl.tue.fingerpaint.shared.ServerDataResultTest;
import nl.tue.fingerpaint.shared.model.MixingProtocolTest;
import nl.tue.fingerpaint.shared.model.RectangleMixingStepTest;
import nl.tue.fingerpaint.shared.simulator.SimulationResultTest;
import nl.tue.fingerpaint.shared.simulator.SimulationTest;
import nl.tue.fingerpaint.shared.simulator.SimulatorServiceTest;
import nl.tue.fingerpaint.shared.utils.ColourTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * <p> A test suite to run all the jUnit tests for the Server side of the application. If you want to
 * run all tests in this suite, select Run As > JUnit Test.<br/>
 * This test suite runs all the tests of the following classes: </p>
 * <ul>
 * <li> {@link NativeCommunicatorTest} </li>
 * </ul>
 * 
 * @author Group Fingerpaint
 */
public class ServerSuite extends TestSuite {
	public static Test suite() {
	    TestSuite suite = new TestSuite("Server Tests");
	    suite.addTestSuite(NativeCommunicatorTest.class); 
	    return suite;
	  }
}
