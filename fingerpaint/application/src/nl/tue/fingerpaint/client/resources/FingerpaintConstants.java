package nl.tue.fingerpaint.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

/**
 * The {@code FingerpaintConstants} are a set of localised constants. They can
 * be used in the GUI for I18N for example.
 * 
 * @author Group Fingerpaint
 */
public interface FingerpaintConstants extends Constants {

	/**
	 * An instance that can be used to obtain constants.
	 */
	public static final FingerpaintConstants INSTANCE = GWT
			.create(FingerpaintConstants.class);

	/**
	 * @return Localised string that indicates that the application is loading
	 *         geometries and mixers from the server.
	 */
	@DefaultStringValue("Loading geometries and mixers...")
	public String loadingGeometries();
	
	
	@DefaultStringValue("#steps")
	public String nrSteps();
}
