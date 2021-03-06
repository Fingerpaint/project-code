package nl.tue.fingerpaint.suites;


import junit.framework.Test;
import junit.framework.TestSuite;
import nl.tue.fingerpaint.client.gui.GraphVisualisatorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * <p> A test suite to run all the Selenium tests for the application. If you want to
 * run all tests in this suite, select Run As > JUnit Test. <br/>
 * This test suite runs all the tests of the following classes: </p>
 * <ul>
 * <li> {@link GraphVisualisatorTest} </li>
 * </ul>
 * 
 * @author Group Fingerpaint
 */
public class SeleniumSuite extends TestSuite {
	public static Test suite() {
	    TestSuite suite = new TestSuite("Selenium Tests");
	    return suite;
	  }
}
