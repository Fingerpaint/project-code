package nl.tue.fingerpaint.client.storage;

import org.jsonmaker.gwt.client.Jsonizer;
import org.jsonmaker.gwt.client.annotation.Transient;

import nl.tue.fingerpaint.client.MixingProtocol;

public class ResultStorage {

	private String geometry;

	private String mixer;

	private double[] distribution;

	private String protocol;

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

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@Transient
	public MixingProtocol getMixingProtocol() {
		return MixingProtocol.fromString(protocol);
	}
	
	@Transient
	public void setMixingProtocol(MixingProtocol prot) {
		protocol = prot.toString();
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
