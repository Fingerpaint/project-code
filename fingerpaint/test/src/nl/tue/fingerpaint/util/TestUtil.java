package nl.tue.fingerpaint.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utility class for testing with Selenium.
 * @author Group Fingerpaint
 */
public class TestUtil {
	
	/**
	 * Returns the current host address.
	 * @return the current host address.
	 */
	private static String getExternalIP() {
		try {
			return ((InetAddress.getLocalHost().getHostAddress()).trim());
		} catch (UnknownHostException e) {
			throw new Error(e);
		}
	}
	
	/**
	 * Navigates to the local server.
	 * @param driver The WebDriver to use.
	 */
	public static void gotoLocalServer(WebDriver driver) {
		driver.get("http://" + getExternalIP() + ":8888/");
	}
	
	/**
	 * Retrieves the canvas.
	 * @param driver The WebDriver to use.
	 * @return The canvas.
	 */
	public static WebElement findCanvas(WebDriver driver) {
		return driver.findElement(By.tagName("canvas"));
	}
	
	/**
	 * Calculates the factor for a rectangular canvas, indicating how much
	 * the canvas has been 'zoomed in'.
	 * @param driver The WebDriver to use.
	 * @return The zoom factor.
	 */
	public static int calculateRectangularFactor(WebDriver driver) {
		WebElement canvas = findCanvas(driver);
		return Math.max(
				1,
				(Math.min(
						(canvas.getSize().getHeight() - 60) / 240, 
						(canvas.getSize().getWidth() - 5) / 400)));
	}
	
	/**
	 * Waits for the loading overlay to appear.
	 * @param driver The WebDriver to use.
	 */
	public static void waitForLoadingOverlay(WebDriver driver) {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.not(
				ExpectedConditions.presenceOfElementLocated(
						By.id("loading-overlay"))));
	}
	
	/**
	 * Navigates through the CellBrowser.
	 * @param driver The WebDriver to use.
	 * @param firstTab The index of the element to select in the first level.
	 * @param secondTab The index of the element to select in the second level.
	 */
	public static void navigateCellBrowser(WebDriver driver, int firstTab, int secondTab) {
		
		//selecting the rectangle geometry in the cellBrowser
		driver.findElement(
				By.cssSelector("#cell > div:nth-child(3) " +
				"> div:first-child > div:first-child > div:first-child" +
				" > div:first-child > div:first-child > " +
				"div:nth-child(" + firstTab + ")")
		).click();
		
		//selecting the default mixer for the rectangle geometry in the cellBrowser
		driver.findElement(
				By.cssSelector("#cell > div:nth-child(5) " +
				"> div:first-child > div:first-child > div:first-child " +
				"> div:first-child > div:first-child > " +
				"div:nth-child(" + secondTab + ")")
		).click();
	}

	/**
	 * Draws on the distribution canvas.
	 * @param driver The WebDriver to use.
	 * @param start Starting coordinate.
	 * @param points Other coordinates.
	 */
	public static void drawRectangularCanvas(
			WebDriver driver, Point start, Point... points) {
		WebElement canvas = findCanvas(driver);
		int factor = calculateRectangularFactor(driver);
		
		Actions builder = new Actions(driver);

		builder = builder.moveToElement(canvas, factor * start.getX() + 6, factor * start.getY() + 31);
		builder = builder.clickAndHold();
		for (Point point : points) {
			builder = builder.moveByOffset(factor * point.getX(), factor * point.getY());
		}
		builder = builder.release();
		builder.build().perform();
	}
	
	/**
	 * Moves one of the walls of the rectangular geometry.
	 * @param driver The WebDriver to use.
	 * @param length How much the wall should move.
	 * @param left Whether to move the wall to the left.
	 * @param top Whether to move the top wall.
	 */
	public static void moveRectangularWall(WebDriver driver, int length, boolean left, boolean top) {
		WebElement canvas = findCanvas(driver);
		int factor = calculateRectangularFactor(driver);
		
		Actions builder = new Actions(driver);

		int startX = left ? 6 : factor * 400;
		
		int startY;
		if (top) {
			startY = 20;
		} else {
			startY = 40 + factor * 240;
		}
		builder = builder.moveToElement(canvas, startX, startY);
		builder = builder.clickAndHold();
		builder = builder.moveByOffset(left ? length : -length, 0);
		builder = builder.release();
		builder.build().perform();
	}
	
	/**
	 * Selects some saved results to show in the Compare Performance panel.
	 * @param driver The WebDriver to use.
	 * @param results Indices of the results to select.
	 */
	public static void selectSavedResultsForPerformance(
			WebDriver driver, int... results) {
		for (int result : results) {
			driver.findElement(By.cssSelector(
					"#compareSelectPopupCellList > div " +
					"> div:nth-child(" + result + ")")).click();
		}
	}
}
