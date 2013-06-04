package suits;

import nl.tue.fingerpaint.server.simulator.NativeCommunicatorTest;

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
@RunWith(Suite.class)

//Run all the tests of each of the following classes:
@SuiteClasses({
	// NativeCommunicatorTest.class
	})
public class ServerUnit {
	/*
	 * --- IMPORTANT -----------------------------------------------
	 * This test suite doesn't execute any tests at the moment, as the Fortran file
	 * cannot be accessed correctly.
	 * -------------------------------------------------------------
	 */
}
