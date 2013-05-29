package nl.tue.fingerpaint.client.json;

import java.util.HashMap;
import java.util.Set;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * Convenience class that provides functions for creating and parsing JSON
 * strings.
 * 
 * @author Group Fingerpaint
 */
public class FingerpaintJsonizer {

	/**
	 * Create a hash map that is represented by the given JSON string.
	 * 
	 * @param jsonHashMap
	 *            A JSON string that represents an object and can be used as a
	 *            hash map in Java.
	 * @return The hash map that the given JSON string represents.
	 */
	public static HashMap<String, Object> fromString(JSONString jsonHashMap) {
		return FingerpaintJsonizer.fromString(jsonHashMap.stringValue());
	}

	/**
	 * Create a hash map that is represented by the given JSON string.
	 * 
	 * @param jsonHashMap
	 *            A JSON string that represents an object and can be used as a
	 *            hash map in Java.
	 * @return The hash map that the given JSON string represents.
	 */
	public static HashMap<String, Object> fromString(String jsonHashMap) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		JSONValue val;
		try {
			val = JSONParser.parseStrict(jsonHashMap);
		} catch (Exception e) {
			// When the value is null or empty, return an empty hash map
			return hm;
		}
		JSONObject valObj;

		if ((valObj = val.isObject()) != null) {
			for (String key : valObj.keySet()) {
				hm.put(key, FingerpaintJsonizer.toUnquotedString(valObj
						.get(key).isString()));
			}
		}

		return hm;
	}

	/**
	 * Convert a double array to JSON string representation.
	 * 
	 * @param array
	 *            The array to be JSONised.
	 * @return The JSON string that represents the given array.
	 */
	public static String toString(double[] array) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		long iPart;
		double fPart;
		for (int i = 0; i < array.length; i++) {
			iPart = (long) array[i];
			fPart = array[i] - iPart;
			// below assumes (by looking at the actual code) that the toString
			// of a double that is smaller than 1 starts with "0."
			sb.append(iPart + "." + Double.toString(fPart).substring(2));
			if (i < array.length - 1) {
				sb.append(",");
			}
		}

		sb.append("]");
		return sb.toString();
	}

	/**
	 * Creates a JSON string that is a representation of the given hash map.
	 * 
	 * @param hashMap
	 *            The hash map to be converted to a JSON string.
	 * @return The JSON string that represents the given hash map.
	 */
	public static String toString(HashMap<String, Object> hashMap) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");

		Set<String> keySet = hashMap.keySet();
		int added = 0;
		for (String key : keySet) {
			sb.append(JsonUtils.escapeValue(key));
			sb.append(":");
			sb.append(JsonUtils.escapeValue(hashMap.get(key).toString()));
			added++;
			if (added < keySet.size()) {
				sb.append(",");
			}
		}

		sb.append("}");
		return sb.toString();
	}

	/**
	 * Return the contents of the given JSONString, but without quotes as
	 * opposed to the standard {@link JSONString#toString()}.
	 * 
	 * @param jsonString The JSONString of which a String representation is needed.
	 * @return The unquoted value of the JSONString.
	 */
	public static String toUnquotedString(JSONString jsonString) {
		String str = jsonString.toString();
		return str.substring(1, str.length() - 1);
	}
}
