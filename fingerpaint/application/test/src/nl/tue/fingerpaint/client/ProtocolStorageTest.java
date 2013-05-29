package nl.tue.fingerpaint.client;

import nl.tue.fingerpaint.client.json.ProtocolMap;
import nl.tue.fingerpaint.client.json.ProtocolStorage;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class ProtocolStorageTest extends GWTTestCase {

	@Test
	public void testJsonize() {
		ProtocolStorage protocol2 = new ProtocolStorage();
		
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
		
		protocol2.addProtocol("RECT",protocol);
		
		System.out.println(protocol2.jsonize());
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
