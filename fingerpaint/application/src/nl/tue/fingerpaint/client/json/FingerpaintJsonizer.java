package nl.tue.fingerpaint.client.json;

import java.util.HashMap;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
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
	 * Create a double array that is represented by the given JSON string. When
	 * the given value does not represent an array, {@code null} is returned.
	 * When a value in the array is not a double, 1.0 is used as a default
	 * value.
	 * 
	 * @param jsonDoubleArray
	 *            A JSON string that represents an array and can be used as a
	 *            double array in Java.
	 * @return The double array that the given JSON string represents.
	 */
	public static double[] doubleArrayFromString(String jsonDoubleArray) {
		JSONValue val;
		try {
			val = JSONParser.parseStrict(jsonDoubleArray);
		} catch (Exception e) {
			// When the value is null or empty, return null
			return null;
		}
		JSONArray valArr;
		JSONNumber valNr;

		if ((valArr = val.isArray()) != null) {
			double[] result = new double[valArr.size()];
			for (int i = 0; i < valArr.size(); i++) {
				val = valArr.get(i);
				if ((valNr = val.isNumber()) != null) {
					result[i] = valNr.doubleValue();
				} else {
					result[i] = 1;
				}
			}
		}

		return null;
	}

	/**
	 * Create a hash map that is represented by the given JSON string.
	 * 
	 * @param jsonHashMap
	 *            A JSON string that represents an object and can be used as a
	 *            hash map in Java.
	 * @return The hash map that the given JSON string represents.
	 */
	public static HashMap<String, Object> hahsMapFromString(
			JSONString jsonHashMap) {
		return FingerpaintJsonizer.hashMapFromString(jsonHashMap.stringValue());
	}

	/**
	 * Create a hash map that is represented by the given JSON string.
	 * 
	 * @param jsonHashMap
	 *            A JSON string that represents an object and can be used as a
	 *            hash map in Java.
	 * @return The hash map that the given JSON string represents.
	 */
	public static HashMap<String, Object> hashMapFromString(String jsonHashMap) {	
		HashMap<String, Object> hm = new HashMap<String, Object>();
		JSONValue val;
		try {
			val = JSONParser.parseStrict(jsonHashMap);
		} catch (Exception e) {
			// When the value is null or empty, return an empty hash map
			return hm;
		}
		JSONObject valObj;
		JSONString tmpValStr;
		JSONObject tmpValObj;

		if ((valObj = val.isObject()) != null) {
			for (String key : valObj.keySet()) {
				val = valObj.get(key);
				GWT.log("HENK(" + key + ") " + (val == null ? "null" : "object/string"));
				if ((tmpValStr = val.isString()) != null) {
					hm.put(key, FingerpaintJsonizer.toUnquotedString(tmpValStr));
				} else if ((tmpValObj = val.isObject()) != null) {
					for (String tmpKey : tmpValObj.keySet()) {
						GWT.log("HENK  " + tmpKey);
					}
				}
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
			sb.append(hashMap.get(key).toString());
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
	 * @param jsonString
	 *            The JSONString of which a String representation is needed.
	 * @return The unquoted value of the JSONString.
	 */
	public static String toUnquotedString(JSONString jsonString) {
		String str = jsonString.toString();
		return str.substring(1, str.length() - 1);
	}
}
