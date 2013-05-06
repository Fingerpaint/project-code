package lib.crossbrowsertest;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Provides a list of {@link DesiredCapabilities} to an instance of {@link MultiBrowserTester}.
 * 
 * These capabilities are supposed to represent the different browser types present on a particular host machine.
 * 
 * @author Lasse Blaauwbroek
 *
 */
public interface CapabilitiesProvider {
	
	/**
	 * Returns the full list of different browsers supported.
	 * 
	 * @return Array of supported browsers
	 */
	public DesiredCapabilities[] getCapabilitiesList();
	
	/**
	 * Returns a list of browsers that must be called sequentially on the host.
	 * 
	 * @return Array of sequential browsers
	 */
	public DesiredCapabilities[] getSequentialCapabilities();
	
	/**
	 * Returns a list of {@link Dimension Dimension's} the supported browsers should be able to take on, and can be tested against.
	 * 
	 * @return A list of browser dimensions
	 */
	public Dimension[] getDimensions();
}
