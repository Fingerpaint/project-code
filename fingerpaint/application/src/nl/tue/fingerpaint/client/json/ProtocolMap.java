package nl.tue.fingerpaint.client.json;

import java.util.ArrayList;
import java.util.HashMap;

import nl.tue.fingerpaint.client.MixingProtocol;
import nl.tue.fingerpaint.client.MixingProtocol.MixingProtocolJsonizer;

import org.jsonmaker.gwt.client.Jsonizer;
import org.jsonmaker.gwt.client.JsonizerException;
import org.jsonmaker.gwt.client.JsonizerParser;
import org.jsonmaker.gwt.client.base.Defaults;
import org.jsonmaker.gwt.client.base.HashMapJsonizer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

public class ProtocolMap {
	private HashMap<String, MixingProtocol> protocols = new HashMap<String, MixingProtocol>();

	public HashMap<String, MixingProtocol> getProtocols() {
		return protocols;
	}

	public void addProtocol(String name, MixingProtocol protocol) {
		protocols.put(name, protocol);
	}

	public String jsonize() {
		HashMapJsonizer hj = new HashMapJsonizer(Defaults.STRING_JSONIZER,
				(MixingProtocolJsonizer) GWT
						.create(MixingProtocolJsonizer.class));

		return hj.asString(protocols);
	}

	public static ProtocolMap unJsonize(String jsValue) {
		HashMapJsonizer hj = new HashMapJsonizer(Defaults.STRING_JSONIZER,
				(MixingProtocolJsonizer) GWT
						.create(MixingProtocolJsonizer.class));
		ProtocolMap result = (ProtocolMap) JsonizerParser.parse(hj, jsValue);

		return result;
	}

	public interface ProtocolMapJsonizer extends Jsonizer {
		@Override
		public String asString(Object javaValue) throws JsonizerException;

		@Override
		public Object asJavaObject(JavaScriptObject jsValue)
				throws JsonizerException;
	}
}
