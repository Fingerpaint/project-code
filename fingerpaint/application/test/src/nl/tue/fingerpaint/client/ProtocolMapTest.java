package nl.tue.fingerpaint.client;

import nl.tue.fingerpaint.client.json.ProtocolMap;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class ProtocolMapTest extends GWTTestCase {

	@Test
	public void testJsonize() {
		ProtocolMap protocol = new ProtocolMap();
		MixingProtocol prot = new MixingProtocol();
		prot.addStep(new MixingStep(1.0, true, false));
		prot.addStep(new MixingStep(1.5, true, false));
		prot.addStep(new MixingStep(2.0, true, false));
		protocol.addProtocol("koetjes", prot);
		MixingProtocol prot2 = new MixingProtocol();
		prot2.addStep(new MixingStep(1.0, true, false));
		prot2.addStep(new MixingStep(1.5, true, false));
		prot2.addStep(new MixingStep(2.0, true, false));
		protocol.addProtocol("kalfkes", prot2);
		
		System.out.println(protocol.jsonize());
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
