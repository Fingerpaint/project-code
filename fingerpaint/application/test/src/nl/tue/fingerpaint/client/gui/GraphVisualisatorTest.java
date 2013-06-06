package nl.tue.fingerpaint.client.gui;

import static org.junit.Assert.*;

import nl.tue.fingerpaint.testenvironment.StandardCapabilitiesProvider;
import nl.tue.fingerpaint.util.TestUtil;
import nl.tue.multibrowsertest.CrossBrowserTest;
import nl.tue.multibrowsertest.MultiBrowserTester;
import nl.tue.multibrowsertest.MultiBrowserTester.ResultTriple;
import nl.tue.multibrowsertest.MultiBrowserTesterException;
import nl.tue.multibrowsertest.TestRunner;
import nl.tue.screenshot.ScreenshotComparator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GraphVisualisatorTest {

	@Test
	public void test() {
		CrossBrowserTest<Boolean> browsertester = new CrossBrowserTest<Boolean>(){

			@Override
			public Boolean test(WebDriver driver, TestRunner<Boolean> tester) {
				TestUtil.gotoLocalServer(driver);
				TestUtil.navigateCellBrowser(driver, 2, 4);
				TestUtil.drawRectangularCanvas(
						driver, 
						new Point(1, 1), new Point(100, 100), 
						new Point(0, 100), new Point(100, 0),  
						new Point(0, -100), new Point(-100, 0));
				driver.findElement(By.id("gwt-debug-defineProtocolCheckbox-input")).click();
				driver.findElement(By.cssSelector("#gwt-debug-nrStepsSpinner input")).sendKeys("0");
				TestUtil.moveRectangularWall(driver, 100, true, true);
				TestUtil.moveRectangularWall(driver, 100, true, false);
				TestUtil.moveRectangularWall(driver, 100, false, true);
				TestUtil.moveRectangularWall(driver, 100, false, false);
				driver.findElement(By.id("gwt-debug-mixNowButton")).click();
				TestUtil.waitForLoadingOverlay(driver);
				(new WebDriverWait(driver, 30)).until(
						ExpectedConditions.elementToBeClickable(
								By.id("gwt-debug-viewGraph")));
				driver.findElement(By.id("gwt-debug-viewGraph")).click();
				tester.takeScreenshot();
				return true;
			}
			
		};
		try {
			MultiBrowserTester<Boolean> multitester = new MultiBrowserTester<Boolean>(
					browsertester, 
					new URL("http://fingerpaint.campus.tue.nl:4444/wd/hub"), 
					new StandardCapabilitiesProvider());
			List<ResultTriple<Boolean>> results = multitester.testAll();
			//ResultTriple<Boolean> results = multitester.testSpecific(new Dimension(800, 1280), DesiredCapabilities.firefox());
			
			List<Map<Dimension,Boolean>> screenieResults = multitester.compareLastScreenshots();
			
			for(Map<Dimension,Boolean> result : screenieResults){
				Collection<Dimension> dimensions = result.keySet();
				for(Dimension dim: dimensions){
					boolean success  = result.get(dim);
					assertTrue("Dimension: " + dim.toString() + " failed the test", success);
					System.out.println(dim.toString());
					System.out.println(success);
				}
			}
			
//			List<Map<Dimension, Map<DesiredCapabilities, BufferedImage>>> screenies = multitester.getLastScreenshots();
//			for(Map<Dimension, Map<DesiredCapabilities, BufferedImage>> screenie : screenies){
//				
//			}
		} catch (MalformedURLException | MultiBrowserTesterException e) {
			throw new Error(e);
		}
	}

	/**
	 * navigates the browser to the graph popup
	 * 
	 * @param the WebDriver that needs to be navigated
	 */
	protected void navigateToGraph(WebDriver driver) {
		//load the page
		driver.get("http://145.116.41.225:8888");
		
		//selecting the rectangle geometry in the cellBrowser
		driver.findElement(By.cssSelector("#gwt-debug-cell > div:nth-child(3) " +
				"> div:first-child > div:first-child > div:first-child" +
				" > div:first-child > div:first-child > div:nth-child(2)")).click();
//		driver.findElement(By.linkText("Rectangle 400x240")).click();
		
		//selecting the default mixer for the rectangle geometry in the cellBrowser
		driver.findElement(By.cssSelector("#gwt-debug-cell > div:nth-child(5) " +
				"> div:first-child > div:first-child > div:first-child" +
				" > div:first-child > div:first-child > div:nth-child(4)")).click();
//		driver.findElement(By.linkText("Default")).click();
		
		
		
		//clicking the View single graph button
		driver.findElement(By.cssSelector("#gwt-debug-viewGraph")).click();
	}

}
