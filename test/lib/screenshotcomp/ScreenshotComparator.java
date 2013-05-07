package lib.screenshotcomp;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Class used to compare two screenshots.
 * 
 * @author Lasse Blaauwbroek
 *
 */
public class ScreenshotComparator {

	/**
	 * pixelsFuzzed * pixelsFuzzed is the size of one pixel block. The mean of all colors is taken as the color of the block.
	 */
	private int pixelsFuzzed;
	
	/**
	 * The threshold for the acceptable color distance of two blocks in the compared screenshots.
	 */
	private int distanceThreshold;
	
	/**
	 * The fraction of blocks that are allowed to exceed the threshold.
	 */
	private double wrongFraction;
	
	/**
	 * Constructs a new comparator with:
	 * 
	 * <ul>
	 * 	<li>{@code pixelsFuzzed}: {@code 3}</li>
	 *  <li>{@code distanceThreshold}: {@code 20}</li>
	 *  <li>{@code wrongFraction}: {@code 0.1}</li>
	 * </ul>
	 */
	public ScreenshotComparator() {
		this(3, 20, 0.1);
	}

	/**
	 * Constructs a new screenshot comparator.
	 * 
	 * {@code pixelsFuzzed * pixelsFuzzed} is the size of one pixel block. 
	 * The mean of all colors is taken as the color of the block.
	 * The {@code threshold} specifies the maximal acceptable color distance of two blocks in the compared screenshots.
	 * Parameter {@code wrongFraction} specifies the fraction of blocks that are allowed to exceed the threshold.
	 * 
	 * @param pixelsFuzzed Block size
	 * @param distanceThreshold Maximal acceptable color distance
	 * @param wrongFraction Fraction of blocks allowed to exceed the {@code distanceThreshold}
	 */
	public ScreenshotComparator(int pixelsFuzzed, int distanceThreshold,
			double wrongFraction) {
		if (pixelsFuzzed < 1) {
			throw new IllegalArgumentException(
					"Argument pixelsFuzzed cannot be smaller than one");
		}
		if (distanceThreshold < 0) {
			throw new IllegalArgumentException(
					"Argument distanceThreshold cannot be smaller than zero");
		}
		if (wrongFraction < 0 || wrongFraction > 1) {
			throw new IllegalArgumentException(
					"Argument wrongFraction must be between zero and one");
		}
		this.pixelsFuzzed = pixelsFuzzed;
		this.distanceThreshold = distanceThreshold;
		this.wrongFraction = wrongFraction;
	}
	
	/**
	 * Checks if two screenshots are equal.
	 * 
	 * The image is quantified in blocks of the specified {@code pixelsFuzzed} size. The color of this block
	 * is the mean of the color of the pixels in this block. The screenshots are equal if there are not more than
	 * {@code wrongFraction} pixel blocks more different than the {@code distanceThreshold} specifies.
	 * 
	 * @param a First screenshot
	 * @param b Second screenshot
	 * @return Whether or not the screenshots are equal
	 */
	public boolean equals(BufferedImage a, BufferedImage b) {
		if (a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
			return false;
		}
		Color color;
		int wrong = 0;
		int count = 0;
		long aRed, aGreen, aBlue, bRed, bGreen, bBlue;
		for (int x = 0; x < a.getWidth(); x += pixelsFuzzed) {
			for (int y = 0; y < a.getHeight(); y += pixelsFuzzed) {
				if (x + pixelsFuzzed <= a.getWidth()
						&& y + pixelsFuzzed <= a.getHeight()) {
					aRed = aGreen = aBlue = bRed = bGreen = bBlue = 0;
					for (int xs = 0; xs < pixelsFuzzed; xs++) {
						for (int ys = 0; ys < pixelsFuzzed; ys++) {
							color = new Color(a.getRGB(x + xs, y + ys));
							aRed += color.getRed();
							aGreen += color.getGreen();
							aBlue += color.getBlue();
							color = new Color(b.getRGB(x + xs, y + ys));
							bRed += color.getRed();
							bGreen += color.getGreen();
							bBlue += color.getBlue();
						}
					}
					count++;
					if (Math.abs(Math.sqrt((aRed * aRed + aGreen * aGreen + aBlue * aBlue))
							- Math.sqrt(bRed * bRed + bGreen * bGreen + bBlue * bBlue)) > 
							pixelsFuzzed * pixelsFuzzed * distanceThreshold) {
						wrong++;
					}
				}
			}
		}
		return wrong <= count * wrongFraction;
	}
}
