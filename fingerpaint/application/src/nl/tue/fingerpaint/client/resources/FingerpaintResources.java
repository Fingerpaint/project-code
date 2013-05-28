package nl.tue.fingerpaint.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * The {@code FingerpaintResources} are a {@link ClientBundle} with all
 * resources used in the Fingerpaint application.
 * 
 * @author Group Fingerpaint
 */
public interface FingerpaintResources extends ClientBundle {

	/**
	 * An instance that can be used to obtain resources.
	 */
	public static final FingerpaintResources INSTANCE = GWT
			.create(FingerpaintResources.class);

	/**
	 * @return The main CSS resource of the Fingerpaint application.
	 */
	@Source("fingerpaint.css")
	@CssResource.NotStrict
	public CssResource css();

	/**
	 * @return An endless animation that can be used to indicate that the
	 *         application is busy doing something.
	 */
	@Source("loading_animation.gif")
	public ImageResource loadImage();
}
