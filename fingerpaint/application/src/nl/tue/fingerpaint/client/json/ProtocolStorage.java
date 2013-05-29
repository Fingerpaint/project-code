package nl.tue.fingerpaint.client.json;

import java.util.HashMap;

import nl.tue.fingerpaint.client.json.ProtocolMap.ProtocolMapJsonizer;

import org.jsonmaker.gwt.client.JsonizerException;
import org.jsonmaker.gwt.client.base.Defaults;
import org.jsonmaker.gwt.client.base.HashMapJsonizer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

public class ProtocolStorage {
	private HashMap<String, ProtocolMap> protocols = new HashMap<String, ProtocolMap>();

	public HashMap<String, ProtocolMap> getProtocols() {
		return protocols;
	}
	
	public void addProtocol(String name, ProtocolMap protocol) {
		protocols.put(name, protocol);
	}

	public String jsonize() {
		ProtocolMapJsonizer spj = new ProtocolMapJsonizer() {
			
			@Override
			public Object asJavaObject(JavaScriptObject jsValue)
					throws JsonizerException {
				return GWT.create(ProtocolMapJsonizer.class);
			}
			
			@Override
			public String asString(Object javaValue) throws JsonizerException { 
				if (javaValue instanceof ProtocolMap) {
					return ((ProtocolMap) javaValue).jsonize();
				}
				
				throw new JsonizerException();
			}
		};
		
		HashMapJsonizer hj = new HashMapJsonizer(Defaults.STRING_JSONIZER, spj);
		
		return hj.asString(protocols);
	}
}
