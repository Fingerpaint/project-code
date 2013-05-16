package nl.tue.fingerpaint.client;

public class UserChoice {
	
	private GeometryNames geoChoice = null;
	private Mixer mixChoice = null;

	public void setGeometry(GeometryNames g) {
		geoChoice = g;
	}

	public void setMixer(Mixer m) {
		mixChoice = m;
	}

	public GeometryNames getGeometryChoice() {
		return geoChoice;
	}

	public Mixer getMixerChoice() {
		return mixChoice;
	}
}
