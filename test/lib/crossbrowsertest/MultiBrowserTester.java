package lib.crossbrowsertest;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Test-class used to run a Selenium browser-test concurrently on multiple remote browsers.
 * 
 * Using a given {@link CapabilitiesProvider}, a series of {@link TestRunners} are created which will 
 * concurrently invoke {@link CrossBrowserTest#test(org.openqa.selenium.WebDriver, TestRunner)} of a given test.
 * 
 * Screenshots can be taken for each browser and compared to each other to ensure compatibility with all browsers.
 * 
 * In order to keep everything as generic as possible, is ResultObject can be specified, that will be returned by
 * the {@CrossBrowserTest} and in turn reported back for each test by this class. In most cases this will be a {@link Boolean}.
 * 
 * @author Lasse Blaauwbroek
 * @param ResultObject The object that will be returned as the result of the test.
 */
public class MultiBrowserTester<ResultObject> {
	
	/**
	 * Data class containing the ResultObject of a specific {@link DesiredCapabilities browser} with
	 * a specific {@link Dimension}.
	 * 
	 * @author Lasse Blaauwbroek
	 *
	 * @param <ResultObject> The ResultObject that is returned from a test
	 */
	public static class ResultTriple<ResultObject> {
		
		private ResultObject result;
		private DesiredCapabilities browser;
		private Dimension dimension;
		
		/**
		 * Constructs the container.
		 * 
		 * @param result The result of a test
		 * @param browser The browser the test was run in
		 * @param dimension The dimension the browser had
		 */
		public ResultTriple(ResultObject result, DesiredCapabilities browser, Dimension dimension) {
			if (result == null) {
				throw new NullPointerException("Argument result cannot be null");
			}
			if (browser == null) {
				throw new NullPointerException("Argument browser cannot be null");
			}
			if (dimension == null) {
				throw new NullPointerException("Argument dimension cannot be null");
			}
			this.result = result;
			this.browser = browser;
			this.dimension = dimension;
		}
		
		/**
		 * Returns the result object.
		 * 
		 * @return Result object
		 */
		public ResultObject getResultObject() {
			return result;
		}
		
		/**
		 * Returns the browser the test was run in.
		 * 
		 * @return The used browser
		 */
		public DesiredCapabilities getBrowser() {
			return browser;
		}
		
		/**
		 * Returns the dimension the browser had.
		 * 
		 * @return The dimension of the browser
		 */
		public Dimension getDimension() {
			return dimension;
		}
	}

	/**
	 * URL to the remote host the tests are being dispatched to.
	 */
	private URL seleniumServer;
	
	/**
	 * The test that needs to be run.
	 */
	private CrossBrowserTest<ResultObject> test;
	
	/**
	 * Holds a possible exception that can occur in a (@link TestRunner}.
	 */
	private MultiBrowserTesterException exception = null;
	
	/**
	 * Maps browsers to a mapping of browser dimension to an assigned {@link TestRunner} that is currently executing.
	 */
	private Map<DesiredCapabilities, Thread> lastAddedTest = new HashMap<DesiredCapabilities, Thread>();
	
	private List<ResultTriple<ResultObject>> results = new ArrayList<ResultTriple<ResultObject>>();
	
	/**
	 * Provides a list of supported browser by the host.
	 */
	private CapabilitiesProvider browserProvider;

	/**
	 * Creates a new MultiBrowserTester instance given a test to be run, the {@link URL} to the remote server and a {@link CapabilitiesProvider}.
	 * 
	 * @param test The test to be run
	 * @param seleniumServer The remote host
	 * @param browserProvider Provides capabilities for different browsers and dimensions of these browsers
	 */
	public MultiBrowserTester(CrossBrowserTest<ResultObject> test, URL seleniumServer, CapabilitiesProvider browserProvider) {

		if (test == null) {
			throw new NullPointerException("Agrument test cannot be null");
		}
		if (seleniumServer == null) {
			throw new NullPointerException(
					"Argument seleniumServer cannot be null");
		}
		if (browserProvider == null) {
			throw new NullPointerException("Argument browserProvider cannot be null");
		}

		this.test = test;
		this.seleniumServer = seleniumServer;
		this.browserProvider = browserProvider;
	}

	private int i = 0;

	public synchronized int getI() {
		i++;
		return i;
	}

	/**
	 * Runs the specified {@link CrossBrowserTest} on all browsers and dimensions specified by the {@link CapabilitiesProvider}.
	 * 
	 * @throws MultiBrowserTesterException If something goes wrong with a test
	 */
	public List<ResultTriple<ResultObject>> testAll() throws MultiBrowserTesterException {
		List<Thread> threadList = new ArrayList<Thread>();
		for (Dimension dimension : browserProvider.getDimensions()) {
			threadList.addAll(testDimensionAsync(dimension));
		}
		for (Thread thread : threadList) {
			while(thread.isAlive()) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					// We do not catch exception because no interrupt is expected
				}
				if (exception != null) {
					throw exception;
				}
			}
		}
		List<ResultTriple<ResultObject>> ret = results;
		results = new ArrayList<ResultTriple<ResultObject>>();
		return ret;
	}
	
	/**
	 * Runs the specified {@link CrossBrowserTest} on all browsers (as specified by the {@link CapabilitiesProvider} with a given {@link Dimension}.
	 * 
	 * @param dimension The dimension the browsers need to have during the tests
	 * @throws MultiBrowserTesterException If something goes wrong with a test
	 */
	public List<ResultTriple<ResultObject>> testDimension(Dimension dimension) throws MultiBrowserTesterException {
		List<Thread> threadList = testDimensionAsync(dimension);
		for (Thread thread : threadList) {
			while(thread.isAlive()) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					// We do not catch exception because no interrupt is expected
				}
				if (exception != null) {
					throw exception;
				}
			}
		}
		List<ResultTriple<ResultObject>> ret = results;
		results = new ArrayList<ResultTriple<ResultObject>>();
		return ret;
	}

	/**
	 * Runs the specified {@link CrossBrowserTest} on a given {@link DesiredCapabilities browser} with a given {@link Dimension}.
	 * 
	 * @param dimension The dimension the browser should have
	 * @param browser The browser to run the test against
	 * @throws MultiBrowserTesterException If something goes wrong with a test
	 */
	public ResultTriple<ResultObject> testSpecific(Dimension dimension, DesiredCapabilities browser) throws MultiBrowserTesterException {
		Thread thread = testSpecificAsync(dimension, browser);
		while(thread.isAlive()) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// We do not catch exception because no interrupt is expected
			}
			if (exception != null) {
				throw exception;
			}
		}
		ResultTriple<ResultObject> ret = results.get(0);
		results = new ArrayList<ResultTriple<ResultObject>>();
		return ret;
	}
	
	/**
	 * Invoked by a {@link TestRunner} if it's test is complete.
	 * 
	 * @param dimension The dimension of the browser during the completed test
	 * @param browser The browser the test was ran against
	 */
	public synchronized void notifyTestComplete(Dimension dimension, DesiredCapabilities browser, ResultObject result) {
		results.add(new ResultTriple<ResultObject>(result, browser, dimension));
	}

	/**
	 * Invoked by a {@link TestRunner} if there was a problem running the test.
	 * 
	 * @param e The occurred exception
	 * @param dimension The dimension of the browser during the completed test
	 * @param browser The browser the test was ran against
	 */
	public synchronized void notifyTestException(MultiBrowserTesterException e, Dimension dimension, DesiredCapabilities browser) {
		if (exception == null) {
			exception = e;
		}
	}
	
	/**
	 * Runs the specified {@link CrossBrowserTest} on all browsers (as specified by the {@link CapabilitiesProvider} with a given {@link Dimension}.
	 * 
	 * @param dimension The dimension the browsers need to have during the tests
	 * @throws MultiBrowserTesterException If something goes wrong with a test
	 */
	private List<Thread> testDimensionAsync(Dimension dimension) {
		List<Thread> threadList = new ArrayList<Thread>();
		for (DesiredCapabilities browser : browserProvider.getCapabilitiesList()) {
			threadList.add(testSpecificAsync(dimension, browser));
		}
		return threadList;
	}

	/**
	 * Runs the specified {@link CrossBrowserTest} on a given {@link DesiredCapabilities browser} with a given {@link Dimension}.
	 * 
	 * @param dimension The dimension the browser should have
	 * @param browser The browser to run the test against
	 * @throws MultiBrowserTesterException If something goes wrong with a test
	 */
	private synchronized Thread testSpecificAsync(Dimension dimension, DesiredCapabilities browser) {
		TestRunner<ResultObject> runner;
		if (isSequentialBrowser(browser) && lastAddedTest.containsKey(browser)) {
			runner = new TestRunner<ResultObject>(seleniumServer, browser, test, this, dimension, lastAddedTest.get(browser));
		} else {
			runner = new TestRunner<ResultObject>(seleniumServer, browser, test, this, dimension, null);
		}
		Thread thread = new Thread(runner);
		lastAddedTest.put(browser, thread);
		thread.start();
		return thread;
	}

	/**
	 * Check whether or not the given browser needs to be run sequentially, or can have multiple instances running simultaneous.
	 * 
	 * @param browser The browser to check for
	 * @return Whether or not the browser is sequential
	 */
	private synchronized boolean isSequentialBrowser(DesiredCapabilities browser) {
		for (DesiredCapabilities d : browserProvider.getSequentialCapabilities()) {
			if (browser.getBrowserName().equals(d.getBrowserName())) {
				return true;
			}
		}
		return false;
	}
}
