package nl.tue.fingerpaint.client.gui;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import nl.tue.fingerpaint.testenvironment.StandardCapabilitiesProvider;
import nl.tue.fingerpaint.testenvironment.TestUtil;
import nl.tue.multibrowsertest.CrossBrowserTest;
import nl.tue.multibrowsertest.MultiBrowserTester;
import nl.tue.multibrowsertest.MultiBrowserTesterException;
import nl.tue.multibrowsertest.TestRunner;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests the graph visualisation classes.
 * @author Group Fingerpaint
 */
public class GraphVisualisatorTest {

	/**
	 * Tests if the browsers correctly display a performance graph for a single mixing run
	 */
	@Test
	public void testSingleGraph() {
		CrossBrowserTest<Boolean> browsertester = new CrossBrowserTest<Boolean>(){

			@Override
			public Boolean test(WebDriver driver, TestRunner<Boolean> tester) {
				try{
					TestUtil.gotoLocalServer(driver);
					TestUtil.navigateCellBrowser(driver, 2, 4);
					TestUtil.drawRectangularCanvas(
							driver, 
							new Point(1, 1), new Point(100, 100), 
							new Point(0, 100), new Point(100, 0),  
							new Point(0, -100), new Point(-100, 0));
					driver.findElement(By.id("defineProtocolCheckBox-input")).click();
					driver.findElement(By.cssSelector("#nrStepsSpinner input")).sendKeys("0");
					TestUtil.moveRectangularWall(driver, 100, true, true);
					TestUtil.moveRectangularWall(driver, 100, true, false);
					TestUtil.moveRectangularWall(driver, 100, false, true);
					TestUtil.moveRectangularWall(driver, 100, false, false);
					driver.findElement(By.id("mixNowButton")).click();
					TestUtil.waitForLoadingOverlay(driver);
					(new WebDriverWait(driver, 30)).until(
							ExpectedConditions.elementToBeClickable(
									By.id("viewSingleGraph")));
					driver.findElement(By.id("viewSingleGraph")).click();
				//in case of failure
				}catch(Exception e){
					e.printStackTrace();
				return false;
				}
				tester.takeScreenshot();
				return true;
				}
			
		};
		try {
			MultiBrowserTester<Boolean> multitester = new MultiBrowserTester<Boolean>(
					browsertester, 
					new URL("http://fingerpaint.campus.tue.nl:4444/wd/hub"), 
					new StandardCapabilitiesProvider());
			multitester.testAll();
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
	 * Tests if the browsers 'correctly' display a performance graph of multiple results
	 */
	@Test
	public void testComparePerformance() {
		CrossBrowserTest<Boolean> browsertester = new CrossBrowserTest<Boolean>(){

			@Override
			public Boolean test(WebDriver driver, TestRunner<Boolean> tester) {
				try{
					TestUtil.gotoLocalServer(driver);
					TestUtil.navigateCellBrowser(driver, 2, 4);
					
					//draw six points on the canvas
					TestUtil.drawRectangularCanvas(
							driver, 
							new Point(1, 1), new Point(100, 100), 
							new Point(0, 100), new Point(100, 0),  
							new Point(0, -100), new Point(-100, 0));
					
					//define a mixing run of one mixing steps and #steps = 10
					driver.findElement(By.id("defineProtocolCheckBox-input")).click();
					//SHOULD result in #steps = 10 with the already-present '1'
					driver.findElement(By.cssSelector("#nrStepsSpinner input")).sendKeys("0");
					TestUtil.moveRectangularWall(driver, 100, true, true);
					
					driver.findElement(By.id("mixNowButton")).click();
					
					//wait till the mixing run is done
					TestUtil.waitForLoadingOverlay(driver);
					(new WebDriverWait(driver, 30)).until(
							ExpectedConditions.elementToBeClickable(
									By.id("saveResultsButton")));
					pause();
					
					//save the results of this first run
					driver.findElement(By.id("saveResultsButton")).click();
					driver.findElement(By.id("saveNameTextBox")).sendKeys("shortProt");
					driver.findElement(By.id("saveItemPanelButton")).click();
					
					//wait till the 'save was successful' popup dissapears
					
					//reset the canvas
					driver.findElement(By.id("resetDistButton")).click();
					
					//draw six points on the canvas
					TestUtil.drawRectangularCanvas(
							driver, 
							new Point(50, 0), new Point(100, 100), 
							new Point(0, 100), new Point(100, 0),  
							new Point(0, -100), new Point(-100, 0));
					
					//add a second step to the existing protocol, and mix again
					TestUtil.moveRectangularWall(driver, 100, false, false);
					
					driver.findElement(By.id("mixNowButton")).click();
					
					//wait for the mixing to finish
					(new WebDriverWait(driver, 30)).until(
							ExpectedConditions.elementToBeClickable(
									By.id("saveResultsButton")));
					pause();
					
					//save the second run
					driver.findElement(By.id("saveResultsButton")).click();
					driver.findElement(By.id("saveNameTextBox")).sendKeys("longProt");
					driver.findElement(By.id("saveItemPanelButton")).click();
					
					//wait for the save menu to close
					(new WebDriverWait(driver, 30)).until(
							ExpectedConditions.elementToBeClickable(
									By.id("comparePerformanceButton")));
					
					//compare the performance
					driver.findElement(By.id("comparePerformanceButton")).click();
					TestUtil.selectSavedResultsForPerformance(driver, 1, 2);
					driver.findElement(By.id("compareButton")).click();
				//if the test went wrong
				}catch(Exception e){
					e.printStackTrace();
				return false;
				}
				tester.takeScreenshot();
				return true;
			}
			
		};
		try {
			MultiBrowserTester<Boolean> multitester = new MultiBrowserTester<Boolean>(
					browsertester, 
					new URL("http://fingerpaint.campus.tue.nl:4444/wd/hub"), 
					new StandardCapabilitiesProvider());
			multitester.testAll();
			//ResultTriple<Boolean> results = multitester.testSpecific(new Dimension(1200, 800), DesiredCapabilities.firefox());
			
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
	 * helper function purely to wait
	 */
	private void pause(){
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
