package nl.tue.fingerpaint.suites;

import junit.framework.Test;
import junit.framework.TestSuite;
import nl.tue.fingerpaint.shared.ServerDataResultTest;
import nl.tue.fingerpaint.shared.model.MixingProtocolTest;
import nl.tue.fingerpaint.shared.model.RectangleMixingStepTest;
import nl.tue.fingerpaint.shared.simulator.SimulationResultTest;
import nl.tue.fingerpaint.shared.simulator.SimulationTest;
import nl.tue.fingerpaint.shared.simulator.SimulatorServiceTest;
import nl.tue.fingerpaint.shared.utils.ColourTest;

import com.google.gwt.junit.tools.GWTTestSuite;

/**
 * <p>
 * A test suite to run all the GWT unit tests for classes that are shared
 * between the Client side and the Server side of the application. If you want
 * to run all tests in this suite, select Run As > GWT JUnit Test. <br/>
 * This test suite runs all the tests of the following classes:
 * </p>
 * <ul>
 * <li> {@link MixingProtocolTest}</li>
 * <li> {@link RectangleMixingStepTest}</li>
 * <li> {@link SimulationResultTest}</li>
 * <li> {@link SimulationTest}</li>
 * <li> {@link SimulatorServiceTest}</li>
 * </ul>
 * 
 * @author Group Fingerpaint
 */
//	,
// 
public class SharedSuite extends GWTTestSuite {

	public static Test suite() {
	    TestSuite suite = new TestSuite("Shared Tests");
	    suite.addTestSuite(MixingProtocolTest.class); 
	    suite.addTestSuite(RectangleMixingStepTest.class);
	    suite.addTestSuite(SimulationResultTest.class);
	    suite.addTestSuite(SimulationTest.class);
	    suite.addTestSuite(ColourTest.class);
	    suite.addTestSuite(ServerDataResultTest.class);
	    suite.addTestSuite(SimulatorServiceTest.class);
	    return suite;
	  }
}