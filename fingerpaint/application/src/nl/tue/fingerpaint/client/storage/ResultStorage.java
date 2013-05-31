package nl.tue.fingerpaint.client.storage;

import org.jsonmaker.gwt.client.Jsonizer;

import nl.tue.fingerpaint.client.MixingProtocol;

public class ResultStorage {

	private String geometry;

	private String mixer;

	private double[] distribution;

	private MixingProtocol protocol;

	private double[] segregation;
	
	private int nrSteps;

	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	public String getMixer() {
		return mixer;
	}

	public void setMixer(String mixer) {
		this.mixer = mixer;
	}

	public double[] getDistribution() {
		return distribution;
	}

	public void setDistribution(double[] distribution) {
		this.distribution = distribution;
	}

	public MixingProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(MixingProtocol protocol) {
		this.protocol = protocol;
	}

	public double[] getSegregation() {
		return segregation;
	}

	public void setSegregation(double[] segregation) {
		this.segregation = segregation;
	}

	public int getNrSteps() {
		return nrSteps;
	}

	public void setNrSteps(int nrSteps) {
		this.nrSteps = nrSteps;
	}
	
	public interface ResultStorageJsonizer extends Jsonizer {}

}
