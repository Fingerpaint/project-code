package nl.tue.fingerpaint.client.gui.drawing;

import static org.junit.Assert.*;

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
import org.openqa.selenium.WebDriver;

/**
 * Class designed to test if the square and circle cursors function properly
 * Currently nonfunctional
 * 
 * @author Group Fingerpaint
 */
public class testCursor {

	/**
	 * Tests if the square cursor functions properly
	 * This method tests enlarging and drawing with the cursor
	 */
	@Test
	public void testSquare() {
		
		CrossBrowserTest<Boolean> browsertester = new CrossBrowserTest<Boolean>(){
			
			@Override
			public Boolean test(WebDriver driver, TestRunner<Boolean> tester) {
				navigate(driver);
				driver.findElement(By.id("cursorSizeSpinner")).clear();
				return true;
			}
		};
		
		testExecutor(browsertester);
	}
	
	/**
	 * Tests if the circular cursor functions properly
	 * This method tests enlarging and drawing with the cursor
	 */
	@Test
	public void testCircle() {

		CrossBrowserTest<Boolean> browsertester = new CrossBrowserTest<Boolean>(){
			
			@Override
			public Boolean test(WebDriver driver, TestRunner<Boolean> tester) {
				navigate(driver);
				return true;
			}
		};
		
		testExecutor(browsertester);
	}
	
	/**
	 * navigates to the cursor select window
	 * <pre> the cellBrowser is still open
	 * <post> The cursorSelectionMenu is opened
	 */
	private void navigate(WebDriver driver){
		TestUtil.navigateCellBrowser(driver, 2, 4);
		driver.findElement(By.id("toolSelector"));
	}
	
	/**
	 * Helper method that executes the tests stored in browserTester
	 * 
	 * @param browsertester the CrossBrowserTester that contains the desired tests
	 * @return true if and only if all tests in the CrossBrowserTester were successful
	 */
	private boolean testExecutor(CrossBrowserTest<Boolean> browsertester){
		boolean success = true; //variable to keep track of results
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
					success = success & result.get(dim);
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
			e.printStackTrace();
			return false;
		}
		return success;
	}

}
