package nl.tue.fingerpaint.testenvironment;

import nl.tue.multibrowsertest.CapabilitiesProvider;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Provides the standard set of {@link DesiredCapabilities browsers}
 * and {@link Dimension Dimension's} used in the test environment for the Fingerpaint project.
 * 
 * Currently the browsers in the standard testing environment are the newest versions of:
 * 
 * <ul>
 *  <li>Mozilla Firefox</li>
 *  <li>Microsoft Internet Explorer</li>
 *  <li>Opera Chrome</li>
 *  <li>Apple Safari</li>
 * </ul>
 * 
 * Of these browser, Microsoft Internet Explorer and Apple Safari are currently running in 
 * sequential mode because of problems during multi-threading.
 * 
 * The following dimensions are tested in the standard testing environment:
 * 
 * <ul>
 *  <li>Desktop: 1600x900</li>
 *  <li>Tablet horizontal: 1280x800</li>
 *  <li>Tablet vertical: 800x1280</li>
 *  <li>Phone horizontal: 960x540</li>
 *  <li>Phone vertical: 540x960</li>
 * </ul>
 *
 * @author Lasse Blaauwbroek
 *
 */
public class StandardCapabilitiesProvider implements CapabilitiesProvider {
	
	/**
	 * List of browsers currently used in the testing environment.
	 */
	private static final DesiredCapabilities[] BROWSERS = new DesiredCapabilities[] {
		DesiredCapabilities.firefox(),
		DesiredCapabilities.internetExplorer(),
		DesiredCapabilities.chrome(),
		DesiredCapabilities.safari()
	};
	
	/**
	 * List of browsers that currently need to run in sequential mode.
	 */
	private static final DesiredCapabilities[] SEQUENTIAL_BROWSERS = new DesiredCapabilities[] {
		DesiredCapabilities.firefox(),
		DesiredCapabilities.internetExplorer(),
		DesiredCapabilities.chrome(),
		DesiredCapabilities.safari()
	};
	
	/**
	 * List of dimensions currently using in the testing environment.
	 */
	private static final Dimension[] DIMENSIONS = new Dimension[] {
		new Dimension(1600, 900), 
		new Dimension(1280, 800), 
		new Dimension(800, 1280), 
		new Dimension(960, 540), 
		new Dimension(540, 960)
	};

	/**
	 * Returns the standard list of currently used browsers in the Fingerpaint testing environment:
	 * 
	 * <ul>
	 *  <li>Mozilla Firefox</li>
	 *  <li>Microsoft Internet Explorer</li>
	 *  <li>Opera Chrome</li>
	 *  <li>Apple Safari</li>
	 * </ul>
	 * 
	 * @return The list of browsers
	 */
	@Override
	public DesiredCapabilities[] getCapabilitiesList() {
		return BROWSERS;
	}

	/**
	 * Returns the list of browsers that currently needs to run in sequential mode because of problems with multi-threading.
	 * 
	 * <ul>
	 *  <li>Microsoft Internet Explorer</li>
	 *  <li>Apple Safari</li>
	 * </ul>
	 * 
	 * @return The list of sequential browsers
	 */
	@Override
	public DesiredCapabilities[] getSequentialCapabilities() {
		return SEQUENTIAL_BROWSERS;
	}

	/**
	 * Returns the list of dimensions that are currently tested in the Fingerpaint testing environment.
	 * 
	 * <ul>
	 *  <li>Desktop: 1600x900</li>
	 *  <li>Tablet horizontal: 1280x800</li>
	 *  <li>Tablet vertical: 800x1280</li>
	 *  <li>Phone horizontal: 960x540</li>
	 *  <li>Phone vertical: 540x960</li>
	 * </ul>
	 * 
	 * @return The list of dimensions
	 */
	@Override
	public Dimension[] getDimensions() {
		return DIMENSIONS;
	}
}
