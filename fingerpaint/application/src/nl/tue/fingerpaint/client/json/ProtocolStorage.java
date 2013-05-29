package nl.tue.fingerpaint.client.json;

import java.util.HashMap;

import nl.tue.fingerpaint.client.json.ProtocolMap.ProtocolMapJsonizer;

import org.jsonmaker.gwt.client.JsonizerException;
import org.jsonmaker.gwt.client.JsonizerParser;
import org.jsonmaker.gwt.client.base.Defaults;
import org.jsonmaker.gwt.client.base.HashMapJsonizer;

import com.google.gwt.core.client.JavaScriptObject;

public class ProtocolStorage {
	private HashMap<String, ProtocolMap> protocols = new HashMap<String, ProtocolMap>();
	private static ProtocolMapJsonizer q = new ProtocolMapJsonizer() {

		@Override
		public Object asJavaObject(JavaScriptObject jsValue)
				throws JsonizerException {
			return ProtocolMap.unJsonize(jsValue.toString());
		}
		
		@Override
		public String asString(Object javaValue) throws JsonizerException { 
			if (javaValue instanceof ProtocolMap) {
				return ((ProtocolMap) javaValue).jsonize();
			}
			
			throw new JsonizerException();
		}
	};

	public HashMap<String, ProtocolMap> getProtocols() {
		return protocols;
	}
	
	public void addProtocol(String name, ProtocolMap protocol) {
		protocols.put(name, protocol);
	}

	public String jsonize() {		
		HashMapJsonizer hj = new HashMapJsonizer(Defaults.STRING_JSONIZER, q);
		
		return hj.asString(protocols);
	}
	
	public static ProtocolStorage unJsonize(String jsonProtocols) {		
		HashMapJsonizer hj = new HashMapJsonizer(Defaults.STRING_JSONIZER, q);
		return (ProtocolStorage) JsonizerParser.parse(hj, jsonProtocols);
		// return (ProtocolMapJsonizer) hj.asJavaObject(jsonProtocols);
	}
}
