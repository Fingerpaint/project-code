package nl.tue.fingerpaint.client;

import java.util.ArrayList;

import nl.tue.fingerpaint.client.MixingProtocol.MixingProtocolJsonizer;
import nl.tue.fingerpaint.client.MixingStep.MixingStepJsonizer;

import org.jsonmaker.gwt.client.Jsonizer;
import org.jsonmaker.gwt.client.JsonizerParser;
import org.jsonmaker.gwt.client.base.ArrayJsonizer;
import org.jsonmaker.gwt.client.base.ArrayListJsonizer;
import org.jsonmaker.gwt.client.base.Defaults;

import com.google.gwt.core.client.GWT;

/**
 * Class that keeps track of the Geometry and Mixer the user has selected. Used
 * by the cellBrowser widget in Fingerpaint.java to store chosen variables.
 * 
 * @author Group Fingerpaint
 */
public class ApplicationState {

	/**
	 * The chosen geometry.
	 */
	private String geoChoice = null;
	/**
	 * The chosen matrix.
	 */
	private String mixChoice = null;

	// the current mixing protocol
	private MixingProtocol protocol = new MixingProtocol();
	
	/**
	 * Stores the initial distribution, once set.
	 */
	private Distribution initialDistribution = null;

	/** Rectangular geometry to draw on */
	private Geometry geom;

	/*
	 * The number of times (#steps) that the defined protocol will be applied.
	 * Initially set to 0, to indicate that the spinner has not been loaded yet.
	 */
	private int nrSteps = 0;

	/**
	 * Stores the current value for the Step size spinner.
	 */
	private double stepsize;

	/**
	 * Returns the current value of number of steps.
	 * 
	 * @return The current value of number of steps.
	 */
	public int getNrSteps() {
		return nrSteps;
	}

	/**
	 * Sets the value for the number of steps. It accepts a double, as the
	 * (default)value of the numberspinner is a double; it can immediately be
	 * converted to an integer, as the numberspinner for this variable
	 * guarantees that correct rounding has been performed, when this method is
	 * called.
	 * 
	 * @param nrSteps
	 *            The new value for number of steps.
	 * 
	 *            <pre>
	 * {@param steps} is valid, according to the settings for the numberspinner in class Fingerpaint.
	 * @post The current number of steps is set to @param{nrSteps}.
	 */
	public void setNrSteps(double steps) {
		nrSteps = (int) steps;
	}

	/**
	 * Change the chosen geometry. Note that it should be compatible with the
	 * chosen mixer!
	 * 
	 * @param g
	 *            The value to be set
	 */
	public void setGeometry(String g) {
		geoChoice = g;
	}

	/**
	 * Change the chosen mixer. Note that it should be compatible with the
	 * chosen geometry!
	 * 
	 * @param m
	 *            The value to be set
	 */
	public void setMixer(String m) {
		mixChoice = m;
	}

	/**
	 * Returns the name of the chosen geometry.
	 * 
	 * @return the name of the chosen geometry
	 */
	public String getGeometryChoice() {
		return geoChoice;
	}

	/**
	 * Returns the name of the chosen mixer.
	 * 
	 * @return the name of the chosen mixer
	 */
	public String getMixerChoice() {
		return mixChoice;
	}

	public MixingProtocol getProtocol() {
		return protocol;
	}

	/**
	 * sets the current mixing protocol
	 * 
	 * @param mixingProtocol
	 *            , the new mixing protocol
	 * @throws NullPointerException
	 *             if mixingProtocol == null
	 */
	public void setProtocol(MixingProtocol mixingProtocol) {
		if (mixingProtocol == null) {
			throw new NullPointerException();
		}
		protocol = mixingProtocol;
	}

	/**
	 * Updates the current mixing step with a new value
	 * 
	 * @param value
	 *            the new StepSize for the current mixing step
	 */
	public void editStepSize(double value) {
		stepsize = value;
	}

	/**
	 * Add a step to the mixing protocol.
	 * 
	 * @param step
	 *            {@code Step} to be added.
	 */
	public void addMixingStep(MixingStep step) {
		protocol.addStep(step);
	}

	public double getStepSize() {
		return stepsize;
	}

	public void setInitialDistribution(Distribution distribution) {
		this.initialDistribution = distribution;
	}

	public Distribution getInitialDistribution() {
		return initialDistribution;
	}

	public String getMixChoice() {
		return mixChoice;
	}

	public double getStepsize() {
		return stepsize;
	}

	/**
	 * Encapsulates the entire ApplicationState into a JSON object, i.e. a
	 * string representing all its variables.
	 * The returned String has the following format:
	 * geoChoice|mixChoice|protocol|distribution|nrSteps.
	 * If either one of these objects have not been set yet (null or 0), the
	 * empty String is returned.
	 * 
	 * @return JSON representation of {@code this} or the empty String, if
	 * either of the components of {@code this} have not been set yet.
	 */
	public String jsonize() {
		String jsonObject = ""; // The resulting object
		
		if(geoChoice != null && mixChoice != null && protocol.getProgram() != null && 
				initialDistribution != null && nrSteps != 0){
			// Save the chosen geometry
			jsonObject += geoChoice + "@";
			
			// Save the chosen matrix/mixer
			jsonObject += mixChoice + "@";
			
			// Save the protocol
			ArrayListJsonizer aj = new ArrayListJsonizer(
					(MixingStepJsonizer) GWT.create(MixingStepJsonizer.class));
			jsonObject += aj.asString(protocol.getProgram()) + "@";

			// Save the distribution
			ArrayJsonizer dj_sonizer = getDoubleJsonizer();
			double[] distribution = initialDistribution.getDistribution();
			Double[] temp = new Double[distribution.length];
			for (int i = 0; i < distribution.length; i++) {
				temp[i] = distribution[i];
			}
			jsonObject += dj_sonizer.asString(temp) + "@";
			
			// Save the number of steps
			jsonObject += nrSteps;
		}
		
		// TODO: Remove output
		System.out.println(jsonObject);

		return jsonObject;
	}
	
	/**
	 * Unjsonizes a JSON object and sets variables 
	 * @param jsonObject
	 */
	public void unJsonize(String jsonObject) {
		String[] objects = jsonObject.split("@");
		
		geoChoice = objects[0];
		mixChoice = objects[1];
		
		ArrayListJsonizer aj = new ArrayListJsonizer((MixingStepJsonizer) GWT.create(MixingStepJsonizer.class));
		ArrayList<MixingStep> mixingList = (ArrayList<MixingStep>) JsonizerParser.parse(aj, objects[2]);
		protocol.setProgram(mixingList);
		
		ArrayListJsonizer dj_sonizer = new ArrayListJsonizer(Defaults.DOUBLE_JSONIZER);
		ArrayList<Double> djList = (ArrayList<Double>) JsonizerParser.parse(dj_sonizer, objects[3]);
		
		double[] initDistribution = new double[djList.size()];
		for (int i = 0; i < djList.size(); i++) {
			initDistribution[i] = djList.get(i);
		}
		geom.drawDistribution(initDistribution);
		
		nrSteps = Integer.parseInt(objects[4]);
	}
	
	private ArrayJsonizer getDoubleJsonizer() {
		return new ArrayJsonizer(Defaults.DOUBLE_JSONIZER) {
			@Override
			protected Object[] createArray(int size) {
				return new Double[size];
			}
		};
	}

	public interface ApplicationStateJsonizer extends Jsonizer {
	}

	public void setGegeom(Geometry geometry) {
		geom = geometry;
	}
	
	public Geometry getGeometry() {
		return geom;
	}
}
