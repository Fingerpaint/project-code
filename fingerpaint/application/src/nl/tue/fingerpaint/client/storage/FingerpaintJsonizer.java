package nl.tue.fingerpaint.client.storage;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.tue.fingerpaint.client.model.MixingProtocol;
import nl.tue.fingerpaint.client.storage.ResultStorage.ResultStorageJsonizer;

import org.jsonmaker.gwt.client.JsonizerParser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
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
	 * Create an array that is represented by the given JSON array.
	 * 
	 * @param jsonArray
	 *            A JSON array that can be used as an array in Java.
	 * @return The array that the given JSON array represents.
	 */
	public static Object[] arrayFromJSONArray(JSONArray jsonArray) {
		Object[] result = new Object[jsonArray.size()];
		JSONValue val;
		JSONObject valObj;
		JSONArray valArr;
		JSONString valStr;
		JSONNumber valNr;
		JSONBoolean valBool;

		for (int i = 0; i < jsonArray.size(); i++) {
			val = jsonArray.get(i);
			if ((valNr = val.isNumber()) != null) {
				result[i] = valNr.doubleValue();
			} else if ((valStr = val.isString()) != null) {
				result[i] = valStr.stringValue();
			} else if ((valObj = val.isObject()) != null) {
				result[i] = FingerpaintJsonizer.hashMapFromJSONObject(valObj,
						true);
			} else if ((valArr = val.isArray()) != null) {
				result[i] = FingerpaintJsonizer.arrayFromJSONArray(valArr);
			} else if ((valBool = val.isBoolean()) != null) {
				result[i] = valBool.booleanValue();
			}
		}
		return result;
	}

	/**
	 * Create an integer array that is represented by the given JSON array. When a
	 * value in the array is not a number, 1.0 is used as a default value.
	 * 
	 * @param jsonArray
	 *            A JSON array that can be used as an integer array in Java.
	 * @return The integer array that the given JSON array represents.
	 */
	public static int[] intArrayFromJSONArray(JSONArray jsonArray) {
		int[] result = new int[jsonArray.size()];
		JSONValue val;
		JSONNumber valNr;

		for (int i = 0; i < jsonArray.size(); i++) {
			val = jsonArray.get(i);
			if ((valNr = val.isNumber()) != null) {
				result[i] = (int) valNr.doubleValue();
			} else {
				result[i] = 1;
			}
		}
		return result;
	}

	/**
	 * Create an integer array that is represented by the given JSON string. When
	 * the given value does not represent an array, {@code null} is returned.
	 * When a value in the array is not an integer, 1.0 is used as a default
	 * value.
	 * 
	 * @param jsonIntArray
	 *            A JSON string that represents an array and can be used as an
	 *            integer array in Java.
	 * @return The integer array that the given JSON string represents.
	 */
	public static int[] intArrayFromString(String jsonIntArray) {
		JSONValue val;
		try {
			val = JSONParser.parseStrict(jsonIntArray);
		} catch (Exception e) {
			// When the value is null or empty, return null
			return null;
		}
		JSONArray valArr;

		if ((valArr = val.isArray()) != null) {
			return intArrayFromJSONArray(valArr);
		}

		return null;
	}

	/**
	 * Create a hash map that is represented by the given JSON object.
	 * 
	 * @param jsonObj
	 *            A JSON object that can be used as a hash map in Java.
	 * @param deep
	 *            If the hash map should be constructed on all levels, or just
	 *            the top level.
	 * @return The hash map that the given JSON object represents.
	 */
	public static HashMap<String, Object> hashMapFromJSONObject(
			JSONObject jsonObj, boolean deep) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		JSONValue tmpVal;

		for (String key : jsonObj.keySet()) {
			tmpVal = jsonObj.get(key);

			if (deep) {
				unJsonize(hm, tmpVal, key);
			} else {
				hm.put(key, tmpVal);
			}
		}

		return hm;
	}

	private static void unJsonize(HashMap<String, Object> hm, JSONValue tmpVal,
			String key) {
		JSONObject tmpValObj;
		JSONArray tmpValArr;
		JSONString tmpValStr;
		JSONNumber tmpValNr;
		JSONBoolean tmpValBool;

		if ((tmpValStr = tmpVal.isString()) != null) {
			String s = tmpValStr.stringValue();
			if (s.charAt(0) == '#') {
				s = FingerpaintZipper.unzip(s);
				unJsonize(hm, JSONParser.parseStrict(s), key);
			} else {
				hm.put(key, s);
			}
		} else if ((tmpValObj = tmpVal.isObject()) != null) {
			hm.put(key, hashMapFromJSONObject(tmpValObj, true));
		} else if ((tmpValArr = tmpVal.isArray()) != null) {
			hm.put(key, FingerpaintJsonizer.arrayFromJSONArray(tmpValArr));
		} else if ((tmpValNr = tmpVal.isNumber()) != null) {
			hm.put(key, tmpValNr.doubleValue());
		} else if ((tmpValBool = tmpVal.isBoolean()) != null) {
			hm.put(key, tmpValBool.booleanValue());
		}
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
	 * @return The hash map that the given JSON string represents. If the string
	 *         is malformed or does not represent an object, {@code null} is
	 *         returned.
	 */
	public static HashMap<String, Object> hashMapFromString(String jsonHashMap) {
		return hashMapFromString(jsonHashMap, true);
	}

	/**
	 * Create a hash map that is represented by the given JSON string.
	 * 
	 * @param jsonHashMap
	 *            A JSON string that represents an object and can be used as a
	 *            hash map in Java.
	 * @param deep
	 *            If the hash map should be constructed on all levels, or just
	 *            the top level.
	 * @return The hash map that the given JSON string represents. If the string
	 *         is malformed or does not represent an object, {@code null} is
	 *         returned.
	 */
	public static HashMap<String, Object> hashMapFromString(String jsonHashMap,
			boolean deep) {
		JSONValue val;
		try {
			val = JSONParser.parseStrict(jsonHashMap);
		} catch (Exception e) {
			// When the value is null or empty, return an empty hash map
			Logger.getLogger("").log(Level.SEVERE,
					"[hashMapFromString] Could not parse value...");
			return null;
		}
		JSONObject valObj;

		if ((valObj = val.isObject()) != null) {
			return hashMapFromJSONObject(valObj, deep);
		}

		return null;
	}

	/**
	 * Create a ResultStorage object that is represented by the given JSON
	 * String
	 * 
	 * @param jsonString
	 *            A JSON string that represents an object and can be used as a
	 *            ResultStorage in Java.
	 * @return The ResultStorage object that the given JSON string represents.
	 *         If the string is malformed or does not represent an object,
	 *         {@code null} is returned.
	 */
	public static ResultStorage resultFromString(String jsonString) {
		ResultStorageJsonizer json = (ResultStorageJsonizer) GWT
				.create(ResultStorageJsonizer.class);
		try {
			return (ResultStorage) JsonizerParser.parse(json, jsonString);
		} catch (Exception e) {
			// When the value is null or empty, return an empty hash map
			Logger.getLogger("").log(Level.SEVERE,
					"[resultFromString] Could not parse value...");
			return null;
		}
	}

	/**
	 * Create a MixingProtocol object that is represented by the given JSON
	 * String
	 * 
	 * @param jsonString
	 *            A JSON string that represents an object and can be used as a
	 *            MixingProtocol in Java.
	 * @return The MixingProtocol object that the given JSON string represents.
	 *         If the string is malformed or does not represent an object,
	 *         {@code null} is returned.
	 */
	public static MixingProtocol protocolFromString(String jsonString) {
		return MixingProtocol.fromString(jsonString);
	}

	/**
	 * Convert an int array to JSON string representation.
	 * 
	 * @param array
	 *            The array to be JSONised.
	 * @return The JSON string that represents the given array.
	 */
	public static String toString(int[] array) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (int i = 0; i < array.length; i++) {
			String number = Integer.toString(array[i]);
			sb.append(number);
			if (i < array.length - 1) {
				sb.append(",");
			}
		}

		sb.append("]");
		return sb.toString();
	}

	/**
	 * Convert a MixingProtocol object to JSON string representation.
	 * 
	 * @param protocol
	 *            The MixingProtocol to be converted to a JSON string.
	 * @return The JSON string that represents the given mixing protocol.
	 */
	public static String toString(MixingProtocol protocol) {
		return protocol.toString();
	}

	/**
	 * Convert a StorageResult object to JSON string representation.
	 * 
	 * @param result
	 *            The StorageResult to be converted to a JSON string.
	 * @return The JSON string that represents the given mixing protocol.
	 */
	public static String toString(ResultStorage result) {
		ResultStorageJsonizer json = (ResultStorageJsonizer) GWT
				.create(ResultStorageJsonizer.class);
		return json.asString(result);

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
			sb.append(toString(hashMap.get(key)));
			added++;
			if (added < keySet.size()) {
				sb.append(",");
			}
		}

		sb.append("}");
		return sb.toString();
	}

	/**
	 * Creates a JSON string that is a representation of the given object, as
	 * good as possible, by looking at the type of the object.
	 * 
	 * @param object
	 *            The object to be converted to a JSON string.
	 * @return The JSON string that represents the given object.
	 */
	@SuppressWarnings("unchecked")
	public static String toString(Object object) {
		if (object instanceof int[]) {
			return toString((int[]) object);
		} else if (object instanceof int[]) {
			return toString((int[]) object);
		} else if (object instanceof JSONString) {
			return ((JSONString) object).toString();
		} else if (object instanceof HashMap) {
			return toString((HashMap<String, Object>) object);
		} else if (object instanceof MixingProtocol) {
			return toString((MixingProtocol) object);
		} else if (object instanceof Object[]) {
			return toString((Object[]) object);
		} else if (object instanceof String) {
			// Ugly check to see if a string is escaped already...
			String strObject = (String) object;
			return ((strObject.charAt(0) == '"' && strObject.charAt(strObject
					.length() - 1) == '"')
					|| (strObject.charAt(0) == '{' && strObject
							.charAt(strObject.length() - 1) == '}')
					|| (strObject.charAt(0) == '[' && strObject
							.charAt(strObject.length() - 1) == ']') ? strObject
					: JsonUtils.escapeValue(strObject));
		}

		return object.toString();
	}

	/**
	 * Creates a JSON string that is a representation of the given array of
	 * objects, as good as possible, by looking at the type of the objects.
	 * 
	 * @param objects
	 *            The array of objects to be converted to a JSON string.
	 * @return The JSON string that represents the given objects.
	 */
	public static String toString(Object[] objects) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (int i = 0; i < objects.length; i++) {
			sb.append(toString(objects[i]));
			if (i < objects.length - 1) {
				sb.append(",");
			}
		}

		sb.append("]");
		return sb.toString();
	}
}
