package nl.tue.fingerpaint.suites;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MasterSuite extends TestSuite {
	public static Test suite() {
	    TestSuite suite = new TestSuite("Master Tests");
	    suite.addTest(ClientSuite.suite());
	    suite.addTest(SeleniumSuite.suite());
	    suite.addTest(ServerSuite.suite());
	    suite.addTest(SharedSuite.suite());
	    return suite;
	  }
}
