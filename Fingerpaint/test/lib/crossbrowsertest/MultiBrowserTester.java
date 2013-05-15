package lib.crossbrowsertest;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lib.screenshotcomp.ScreenshotComparator;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Test-class used to run a Selenium browser-test concurrently on multiple
 * remote browsers.
 * 
 * Using a given {@link CapabilitiesProvider}, a series of {@link TestRunner
 * TestRunners} are created which will concurrently invoke
 * {@link CrossBrowserTest#test(org.openqa.selenium.WebDriver, TestRunner)} of a
 * given test.
 * 
 * Screenshots can be taken for each browser and compared to each other to
 * ensure compatibility with all browsers.
 * 
 * In order to keep everything as generic as possible, is ResultObject can be
 * specified, that will be returned by the {@CrossBrowserTest
 * 
 * 
 * 
 * } and in turn reported back for each test by this class. In
 * most cases this will be a {@link Boolean}.
 * 
 * @author Lasse Blaauwbroek
 * @param <ResultObject>
 *            The object that will be returned as the result of the test.
 */
public class MultiBrowserTester<ResultObject> {

	/**
	 * Data class containing the ResultObject of a specific
	 * {@link DesiredCapabilities browser} with a specific {@link Dimension}.
	 * 
	 * @author Lasse Blaauwbroek
	 * 
	 * @param <ResultObject>
	 *            The ResultObject that is returned from a test
	 */
	public static class ResultTriple<ResultObject> {

		/**
		 * The result
		 */
		private ResultObject result;
		
		/**
		 * The browser
		 */
		private DesiredCapabilities browser;
		
		/**
		 * The dimension of the browser
		 */
		private Dimension dimension;

		/**
		 * Constructs the container.
		 * 
		 * @param result
		 *            The result of a test
		 * @param browser
		 *            The browser the test was run in
		 * @param dimension
		 *            The dimension the browser had
		 */
		public ResultTriple(ResultObject result, DesiredCapabilities browser,
				Dimension dimension) {
			if (result == null) {
				throw new NullPointerException("Argument result cannot be null");
			}
			if (browser == null) {
				throw new NullPointerException(
						"Argument browser cannot be null");
			}
			if (dimension == null) {
				throw new NullPointerException(
						"Argument dimension cannot be null");
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
	 * Maps browsers to a mapping of browser dimension to an assigned
	 * {@link TestRunner} that is currently executing.
	 */
	private Map<DesiredCapabilities, Thread> lastAddedTest = new HashMap<>();

	/**
	 * A list of results that have been returned by a {@link TestRunner}
	 */
	private List<ResultTriple<ResultObject>> results = new ArrayList<>();
	
	/**
	 * Keeps the screenshots the test makes. For every screenshot the test makes, there is an entry
	 * in the list, with a mapping from a dimension to a mapping of the browser with that dimension to the
	 * screenshot resulting from that browser.
	 */
	private List<Map<Dimension, Map<DesiredCapabilities, BufferedImage>>> screenshots = new ArrayList<>();

	/**
	 * Provides a list of supported browser by the host.
	 */
	private CapabilitiesProvider browserProvider;
	
	/**
	 * Compares screenshots taken by different browsers.
	 */
	private ScreenshotComparator screenshotComparator;
	
	/**
	 * Creates a new MultiBrowserTester instance given a test to be run, the
	 * {@link URL} to the remote server and a {@link CapabilitiesProvider}.
	 * 
	 * A standard screenshot comparator will be used when this constructor is called.
	 * 
	 * @param test
	 *            The test to be run
	 * @param seleniumServer
	 *            The remote host
	 * @param browserProvider
	 *            Provides capabilities for different browsers and dimensions of
	 *            these browsers
	 */
	public MultiBrowserTester(CrossBrowserTest<ResultObject> test,
			URL seleniumServer, CapabilitiesProvider browserProvider) {
		this(test, seleniumServer, browserProvider, new ScreenshotComparator());
	}

	/**
	 * Creates a new MultiBrowserTester instance given a test to be run, the
	 * {@link URL} to the remote server and a {@link CapabilitiesProvider}.
	 * 
	 * @param test
	 *            The test to be run
	 * @param seleniumServer
	 *            The remote host
	 * @param browserProvider
	 *            Provides capabilities for different browsers and dimensions of
	 *            these browsers
	 * @param screenshotComparator The comparator for the screenshots the test takes
	 */
	public MultiBrowserTester(CrossBrowserTest<ResultObject> test,
			URL seleniumServer, CapabilitiesProvider browserProvider, ScreenshotComparator screenshotComparator) {

		if (test == null) {
			throw new NullPointerException("Agrument test cannot be null");
		}
		if (seleniumServer == null) {
			throw new NullPointerException(
					"Argument seleniumServer cannot be null");
		}
		if (browserProvider == null) {
			throw new NullPointerException(
					"Argument browserProvider cannot be null");
		}
		if (screenshotComparator == null) {
			throw new NullPointerException(
					"Argument screenshotComparator cannot be null");
		}

		this.test = test;
		this.seleniumServer = seleniumServer;
		this.browserProvider = browserProvider;
		this.screenshotComparator = screenshotComparator;
	}

	/**
	 * Runs the specified {@link CrossBrowserTest} on all browsers and
	 * dimensions specified by the {@link CapabilitiesProvider}.
	 * 
	 * @return List of results of running the test on all browsers.
	 * @throws MultiBrowserTesterException
	 *             If something goes wrong with a test
	 */
	public List<ResultTriple<ResultObject>> testAll()
			throws MultiBrowserTesterException {
		List<Thread> threadList = new ArrayList<>();
		screenshots = new ArrayList<>();
		for (Dimension dimension : browserProvider.getDimensions()) {
			threadList.addAll(testDimensionAsync(dimension));
		}
		for (Thread thread : threadList) {
			
			while (thread.isAlive()) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					// We do not catch exception because no interrupt is
					// expected
				}
				if (exception != null) {
					throw exception;
				}
			}
		}
		List<ResultTriple<ResultObject>> ret = results;
		results = new ArrayList<>();
		return ret;
	}

	/**
	 * Runs the specified {@link CrossBrowserTest} on all browsers (as specified
	 * by the {@link CapabilitiesProvider} with a given {@link Dimension}.
	 * 
	 * @param dimension
	 *            The dimension the browsers need to have during the tests
	 * @return List of results of running the test on the given browser with all
	 *         dimensions.
	 * @throws MultiBrowserTesterException
	 *             If something goes wrong with a test
	 */
	public List<ResultTriple<ResultObject>> testDimension(Dimension dimension)
			throws MultiBrowserTesterException {
		screenshots = new ArrayList<>();
		List<Thread> threadList = testDimensionAsync(dimension);
		for (Thread thread : threadList) {
			while (thread.isAlive()) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					// We do not catch exception because no interrupt is
					// expected
				}
				if (exception != null) {
					throw exception;
				}
			}
		}
		List<ResultTriple<ResultObject>> ret = results;
		results = new ArrayList<>();
		return ret;
	}

	/**
	 * Runs the specified {@link CrossBrowserTest} on a given
	 * {@link DesiredCapabilities browser} with a given {@link Dimension}.
	 * 
	 * @param dimension
	 *            The dimension the browser should have
	 * @param browser
	 *            The browser to run the test against
	 * @return The result of running the test in a given browser with a given
	 *         dimension.
	 * @throws MultiBrowserTesterException
	 *             If something goes wrong with a test
	 */
	public ResultTriple<ResultObject> testSpecific(Dimension dimension,
			DesiredCapabilities browser) throws MultiBrowserTesterException {
		screenshots = new ArrayList<>();
		Thread thread = testSpecificAsync(dimension, browser);
		while (thread.isAlive()) {
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
		results = new ArrayList<>();
		return ret;
	}
	
	/**
	 * Retrieves the list of screenshots taken by the last test-run.
	 * 
	 * Each screenshot taken by a test is an entry in the list. The entry is a mapping from a dimension
	 * to a mapping of browsers with that dimension to the screenshot resulting from that browser.
	 * 
	 * @return The list of screenshots
	 */
	public synchronized List<Map<Dimension, Map<DesiredCapabilities, BufferedImage>>> getLastScreenshots() {
		return screenshots;
	}
	
	/**
	 * Compares the screenshots taken by the last test-run and returns the result.
	 * 
	 * For each screenshot taken there is an entry in the returned list. Each entry is a mapping from a tested
	 * dimension to a boolean representing whether or not all browsers with that dimension returned an equal
	 * screenshot.
	 * 
	 * @return The list
	 */
	public synchronized List<Map<Dimension, Boolean>> compareLastScreenshots() {
		List<Map<Dimension, Boolean>> result = new ArrayList<>();
		Iterator<BufferedImage> it;
		BufferedImage reference;
		Map<Dimension, Boolean> resultMap;
		boolean correct;
		for (Map<Dimension, Map<DesiredCapabilities, BufferedImage>> dimensionMap : screenshots) {
			resultMap = new HashMap<>();
			result.add(resultMap);
			for (Entry<Dimension, Map<DesiredCapabilities, BufferedImage>> dimensionEntry : dimensionMap.entrySet()) {
				correct = true;
				it = dimensionEntry.getValue().values().iterator();
				reference = it.next();
				while (it.hasNext()) {
					if (!screenshotComparator.equals(reference, it.next())) {
						correct = false;
						break;
					}
				}
				resultMap.put(dimensionEntry.getKey(), correct);
			}
		}
		return result;
	}

	/**
	 * Invoked by a {@link TestRunner} if it's test is complete.
	 * 
	 * @param dimension
	 *            The dimension of the browser during the completed test
	 * @param browser
	 *            The browser the test was ran against
	 * @param result
	 *            The result of the test
	 */
	synchronized void notifyTestComplete(Dimension dimension,
			DesiredCapabilities browser, ResultObject result) {
		results.add(new ResultTriple<>(result, browser, dimension));
	}
	
	/**
	 * Invoked by a {@link TestRunner} to notify a screenshot has been taken.
	 * This method adds the screenshot to the {@link MultiBrowserTester#screenshots} list.
	 * 
	 * @param dimension The dimension of the browser in this test
	 * @param browser The browser used for the test
	 * @param screenshot The screenshot that has been taken
	 */
	synchronized void notifyScreenshotTaken(Dimension dimension, DesiredCapabilities browser, BufferedImage screenshot) {
		Map<DesiredCapabilities, BufferedImage> addMap = null;
		for (Map<Dimension, Map<DesiredCapabilities, BufferedImage>> dimensionMap : screenshots) {
			if (!dimensionMap.containsKey(dimension)) {
				addMap = new HashMap<>();
				dimensionMap.put(dimension, addMap);
				break;
			} else if (!dimensionMap.get(dimension).containsKey(browser)) {
				addMap = dimensionMap.get(dimension);
			}
		}
		if (addMap == null) {
			screenshots.add(new HashMap<Dimension, Map<DesiredCapabilities, BufferedImage>>());
			addMap = new HashMap<>();
			screenshots.get(screenshots.size() - 1).put(dimension, addMap);
		}
		addMap.put(browser, screenshot);
	}

	/**
	 * Invoked by a {@link TestRunner} if there was a problem running the test.
	 * 
	 * @param e
	 *            The occurred exception
	 * @param dimension
	 *            The dimension of the browser during the completed test
	 * @param browser
	 *            The browser the test was ran against
	 */
	synchronized void notifyTestException(MultiBrowserTesterException e,
			Dimension dimension, DesiredCapabilities browser) {
		if (exception == null) {
			exception = e;
		}
	}

	/**
	 * Runs the specified {@link CrossBrowserTest} on all browsers (as specified
	 * by the {@link CapabilitiesProvider} with a given {@link Dimension}.
	 * 
	 * @param dimension The dimension the browsers need to have during the tests
	 * @return A list of test-thread that have been started
	 */
	private List<Thread> testDimensionAsync(Dimension dimension) {
		List<Thread> threadList = new ArrayList<>();
		for (DesiredCapabilities browser : browserProvider
				.getCapabilitiesList()) {
			threadList.add(testSpecificAsync(dimension, browser));
		}
		return threadList;
	}

	/**
	 * Runs the specified {@link CrossBrowserTest} on a given
	 * {@link DesiredCapabilities browser} with a given {@link Dimension}.
	 * 
	 * @param dimension
	 *            The dimension the browser should have
	 * @param browser
	 *            The browser to run the test against
	 * @return The test-thread that has been started
	 */
	private synchronized Thread testSpecificAsync(Dimension dimension,
			DesiredCapabilities browser) {
		TestRunner<ResultObject> runner;
		if (isSequentialBrowser(browser) && lastAddedTest.containsKey(browser)) {
			runner = new TestRunner<>(seleniumServer, browser, test, this,
					dimension, lastAddedTest.get(browser));
		} else {
			runner = new TestRunner<>(seleniumServer, browser, test, this,
					dimension, null);
		}
		Thread thread = new Thread(runner);
		lastAddedTest.put(browser, thread);
		thread.start();
		return thread;
	}

	/**
	 * Check whether or not the given browser needs to be run sequentially, or
	 * can have multiple instances running simultaneous.
	 * 
	 * @param browser
	 *            The browser to check for
	 * @return Whether or not the browser is sequential
	 */
	private synchronized boolean isSequentialBrowser(DesiredCapabilities browser) {
		for (DesiredCapabilities d : browserProvider
				.getSequentialCapabilities()) {
			if (browser.getBrowserName().equals(d.getBrowserName())) {
				return true;
			}
		}
		return false;
	}
}
