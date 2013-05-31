package nl.tue.multibrowsertest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * A test-runner capable of running a standard Selenium browser test given a
 * {@link DesiredCapabilities browser} to test against, a {@link Dimension} the
 * browser should have, a {@link CrossBrowserTest} to run and an {@link URL
 * location} of the host to run the test on.
 * 
 * The class is {@link Runnable} in order to have the ability to make the
 * cross-browser testing environment multi-threaded. Results will be reported
 * back to the master of this test-runner, a {@link MultiBrowserTester}. It is
 * possible to take screenshots in a consistend way using this class, and they
 * can be compared to tests with other browsers and dimensions in the
 * {@link MultiBrowserTester}.
 * 
 * After the {@link CrossBrowserTest} is finished it will return a ResultObject
 * that will be reported back to the {@link MultiBrowserTester master}.
 * 
 * @author Lasse Blaauwbroek
 * @param <ResultObject>
 *            The object returned by the test and reported back to the master
 */
public class TestRunner<ResultObject> implements Runnable {

	/**
	 * The browser the test should be run on.
	 */
	private DesiredCapabilities browser;

	/**
	 * The test that should be run.
	 */
	private CrossBrowserTest<ResultObject> test;

	/**
	 * The master to which results should be reported back.
	 */
	private MultiBrowserTester<ResultObject> master;

	/**
	 * The dimension the browser should have when the test is run.
	 */
	private Dimension browserDimension;

	/**
	 * The location of the Selenium host.
	 */
	private URL seleniumServer;

	/**
	 * A reference to the Selenium driver the test can use.
	 */
	private WebDriver driver;

	/**
	 * The thread the current thread needs to wait for. Might be null.
	 */
	private Thread waitFor;

	/**
	 * Constructs a new test-runner given a Selenium host server, a
	 * {@link DesiredCapabilities browser}, a {@link CrossBrowserTest test} to
	 * run, a {@link MultiBrowserTester master} to report back to and the
	 * {@link Dimension} the browser should have during the test.
	 * 
	 * @param seleniumServer
	 *            The Selenium host server
	 * @param browser
	 *            The browser the test should run on
	 * @param test
	 *            The test that should be run
	 * @param master
	 *            The master to report back to
	 * @param browserDimension
	 *            The dimension the browser should have
	 * @param waitFor
	 *            If given, the test will first wait for this thread to die
	 *            before actually executing the test.
	 */
	public TestRunner(URL seleniumServer, DesiredCapabilities browser,
			CrossBrowserTest<ResultObject> test,
			MultiBrowserTester<ResultObject> master,
			Dimension browserDimension, Thread waitFor) {
		if (seleniumServer == null) {
			throw new NullPointerException(
					"Argument seleniumServer cannot be null");
		}
		if (browser == null) {
			throw new NullPointerException("Argument browser cannot be null");
		}
		if (test == null) {
			throw new NullPointerException("Argument test cannot be null");
		}
		if (master == null) {
			throw new NullPointerException("Argument master cannot be null");
		}
		if (browserDimension == null) {
			throw new NullPointerException("Argument deviceType cannot be null");
		}
		this.seleniumServer = seleniumServer;
		this.master = master;
		this.browser = browser;
		this.test = test;
		this.browserDimension = browserDimension;
		this.waitFor = waitFor;
	}

	/**
	 * Runs the test by first creating a driver, then setting the view-port of
	 * the browser to the correct dimensions and then running the specified
	 * {@link CrossBrowserTest test}
	 * 
	 * All results and exceptions are reported back to the
	 * {@link MultiBrowserTester master}. The dimensions of the view-port is
	 * approximated by first resizing the window to this dimension, then
	 * measuring the view-port with some java-script and then compensating for
	 * the window decorations.
	 * 
	 * Dimensions are measured without scroll-bars.
	 */
	@Override
	public void run() {
		if (waitFor != null) {
			while (waitFor.isAlive()) {
				try {
					waitFor.join();
				} catch (InterruptedException e) {
					// Not used because we do not expect an exception
				}
			}
		}
		try {
			createDriver();
			setViewportDimension();
			System.out.println("Running browser " + browser.toString()
					+ " screen: " + browserDimension.toString());
			ResultObject result = test.test(driver, this);
			master.notifyTestComplete(browserDimension, browser, result);
		} catch (MultiBrowserTesterException e) {
			master.notifyTestException(e, browserDimension, browser);
		} finally {
			driver.close();
		}
	}

	/**
	 * Takes a screenshot of the current view-port of the browser, and reports
	 * it back to its {@link MultiBrowserTester master} in order to compare it
	 * to screenshots taken with other test-runners. If there is a scroll-bar
	 * present, it will be hidden temporarily using some java-script. Then a
	 * screenshot will be taken of the view-port. For browsers in which the
	 * whole page is captured, the resulting image is cropped to the correct
	 * dimensions.
	 */
	public void takeScreenshot() {
		String overflowVal = (String) ((JavascriptExecutor) driver).executeScript("return document.body.style.overflow;");
		((JavascriptExecutor) driver)
				.executeScript("document.body.style.overflow = \"hidden\";");
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage screenshot;
		try {
			screenshot = ImageIO.read(screenshotFile);
			screenshot = screenshot.getSubimage(0, 0, browserDimension.getWidth(), browserDimension.getHeight());
			master.notifyScreenshotTaken(browserDimension, browser, screenshot);
			((JavascriptExecutor) driver)
					.executeScript("document.body.style.overflow = \"" + overflowVal + "\";");
		} catch (IOException e) {
			// Should not occur
			throw new Error(e);
		}
	}

	/**
	 * Creates a driver for a given {@link DesiredCapabilities browser}.
	 * 
	 * The driver is augmented with screenshot support and if needed, browser
	 * specific adjustments to the driver are made.
	 */
	private void createDriver() {
		driver = new RemoteWebDriver(seleniumServer, browser);
		driver = new Augmenter().augment(driver);

		// This is done in order to hide the extensions add-on that
		// automatically pops up when firefox is started using the webdriver.
		if (browser.getBrowserName().equals("firefox")) {
			driver.switchTo().defaultContent();
			Actions myAction = new Actions(driver);
			myAction.sendKeys(Keys.CONTROL, Keys.DIVIDE).build().perform();
		}
	}

	/**
	 * Sets the {@link Dimension} of the view-port of the {@link WebDriver}.
	 * 
	 * This is done by first hiding, the scroll-bars of the view-port, then
	 * setting the body of the document to stretch over the whole view-port
	 * using java-script. Then the window is resized to the desired
	 * {@link Dimension}. Using java-script the dimensions of the view-port are
	 * measured, and the window is resized again to compensate for window
	 * decorations.
	 * 
	 * If the resizing of the window to the correct dimension fails, an
	 * exception is thrown. The most likely cause is that the screen of the
	 * Selenium host is of insufficient size.
	 * 
	 * @throws MultiBrowserTesterException If the driver was not able to set the dimension of the view-port correctly
	 */
	private void setViewportDimension() throws MultiBrowserTesterException {
		int viewportWidth;
		int viewportHeight;
		Dimension correctedDimension;
		driver.manage().window().setSize(browserDimension);
		((JavascriptExecutor) driver)
				.executeScript("document.body.style.overflow = \"hidden\";");
		((JavascriptExecutor) driver)
				.executeScript("document.body.style.width = \"100%\";");
		((JavascriptExecutor) driver)
				.executeScript("document.body.style.height = \"100%\";");
		viewportWidth = ((Long) ((JavascriptExecutor) driver)
				.executeScript("return window.innerWidth;")).intValue();
		viewportHeight = ((Long) ((JavascriptExecutor) driver)
				.executeScript("return window.innerHeight;")).intValue();
		System.out.println("current viewport: (" + viewportWidth + ", "
				+ viewportHeight + ")");
		correctedDimension = new Dimension(browserDimension.getWidth() * 2
				- viewportWidth, browserDimension.getHeight() * 2
				- viewportHeight);
		driver.manage().window().setSize(correctedDimension);
		viewportWidth = ((Long) ((JavascriptExecutor) driver)
				.executeScript("return window.innerWidth;")).intValue();
		viewportHeight = ((Long) ((JavascriptExecutor) driver)
				.executeScript("return window.innerHeight;")).intValue();
		System.out.println("corrected viewport: (" + viewportWidth + ", "
				+ viewportHeight + ")");
		if (browserDimension.getHeight() != viewportHeight
				|| browserDimension.getWidth() != viewportWidth) {
			throw new MultiBrowserTesterException(
					"The viewport size of driver "
							+ driver
							+ " could not be correctly altered to dimension "
							+ browserDimension
							+ ". Check the size of the display of the Selenium host.");
		}
	}

}
