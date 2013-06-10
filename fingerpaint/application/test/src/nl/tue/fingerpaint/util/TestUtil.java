package nl.tue.fingerpaint.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestUtil {
	
	private static String getExternalIP() {
		try {
			return ((InetAddress.getLocalHost().getHostAddress()).trim());
		} catch (UnknownHostException e) {
			throw new Error(e);
		}
	}
	
	public static void gotoLocalServer(WebDriver driver) {
		driver.get("http://" + getExternalIP() + ":8888/");
	}
	
	public static WebElement findCanvas(WebDriver driver) {
		return driver.findElement(By.tagName("canvas"));
	}
	
	public static int calculateRectangularFactor(WebDriver driver) {
		WebElement canvas = findCanvas(driver);
		return Math.max(
				1,
				(Math.min(
						(canvas.getSize().getHeight() - 60) / 240, 
						(canvas.getSize().getWidth() - 5) / 400)));
	}
	
	public static void waitForLoadingOverlay(WebDriver driver) {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.not(
				ExpectedConditions.presenceOfElementLocated(
						By.id("loading-overlay"))));
	}
	
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
	
	public static void selectSavedResultsForPerformance(
			WebDriver driver, int... results) {
		for (int result : results) {
			driver.findElement(By.cssSelector(
					"#compareSelectPopupCellList > div " +
					"> div:nth-child(" + result + ")")).click();
		}
	}
}
