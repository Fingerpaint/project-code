package nl.tue.fingerpaint.client;

import java.util.ArrayList;

/**
 * 
 * @author Group Fingerpaint
 *
 */
public class Protocol {
	private ArrayList<Step> protocol = new ArrayList<Step>();
	
	public void addStep(Step step) {
		protocol.add(step);
	}
	
	public ArrayList<Step> getProtocol() {
		return protocol;
	}
	
	public Step getStep(int index) {
		return protocol.get(index);
	}

}
