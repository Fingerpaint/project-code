package lib.crossbrowsertest;

import org.openqa.selenium.WebDriver;

/**
 * This interface can be implemented by Selenium browser tests who need to be tested with various browsers.
 * 
 * A {@link MultiBrowserTester} can be used to instantiate the required {@link TestRunner TestRunner's}. 
 * Each TestRunner will represent a different browser, and will invoke the {@link CrossBrowserTest#test(WebDriver, TestRunner)} method.
 * 
 * In order to keep everything as generic as possible, is ResultObject can be specified, that will be returned by the test. In most
 * cases this will be a {@link Boolean}
 * 
 * @author Lasse Blaauwbroek
 * @param <ResultObject> The object that will be returned as the result of the test.
 */
public interface CrossBrowserTest<ResultObject> {
	
	/**
	 * This method will be called by a {@link TestRunner}. The method should run a test against the supplied {@link WebDriver}.
	 * The method must not resize the browser and should take care to close the driver before returning.
	 * 
	 * The tester parameter can be used to take screenshots using the {@link TestRunner#takeScreenshot()} 
	 * in order to compare visual results of the test ran on different drivers.
	 * The method must take care to take screenshots in a consistent manner over multiple invocations
	 * 
	 * @param driver Driver to run the test against
	 * @param tester Can be used to take screenshots and compare them with other test-runs
	 * @return The result of the test
	 */
	public ResultObject test(WebDriver driver, TestRunner<ResultObject> tester);
}
