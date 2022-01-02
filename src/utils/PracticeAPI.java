package utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class PracticeAPI {

	
	/**
	 * Return player name
	 */
	public static String getName(String json) {
		JSONObject obj = new JSONObject(json);
		String name = "";
		
		try {
			name =  obj.getString("name");
		} catch (Exception ignored) {}
		return (name);
	}

	/**
	 * Return player qualification score
	 */
	public static int getQualificationToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value =  obj.getJSONObject("scores").getInt("qualification");
		} catch (Exception ignored) {}
		return (value);
	}
	
	/**
	 * Return player finals score
	 */
	public static int getFinalsToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value =  obj.getJSONObject("scores").getInt("finals");
		} catch (Exception ignored) {}
		return (value);
	}
	
	/**
	 * Return player wide qualification score
	 */
	public static int getWideQualificationToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value =  obj.getJSONObject("scores").getInt("wide_qualification");
		} catch (Exception ignored) {}
		return (value);
	}
	
	/**
	 * Return player lobby score
	 */
	public static int getLobbyToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value =  obj.getJSONObject("scores").getInt("lobby");
		} catch (Exception ignored) {}
		return (value);
	}
	
	public static int[] getQualificationHistory(String json) {
		JSONObject obj = new JSONObject(json);
		JSONArray array = obj.optJSONArray("qualification_history");
		if (array == null) return (null);
		
		int[] values = new int[array.length()];
		for (int i = 0; i < array.length(); ++i) {
			values[i] = array.getInt(i);
		}
		return (values);
	}
	
	public static int[] getFinalsHistory(String json) {
		JSONObject obj = new JSONObject(json);
		JSONArray array = obj.optJSONArray("finals_history");

		if (array == null) return (null);

		int[] values = new int[array.length()];
		for (int i = 0; i < array.length(); ++i) {
			values[i] = array.getInt(i);
		}
		return (values);
	}
	
	public static int[] getWideQualificationHistory(String json) {
		JSONObject obj = new JSONObject(json);
		JSONArray array = obj.optJSONArray("wide_qualification_history");

		if (array == null) return (null);

		int[] values = new int[array.length()];
		for (int i = 0; i < array.length(); ++i) {
			values[i] = array.getInt(i);
		}
		return (values);
	}
	
	public static int[] getLobbyHistory(String json) {
		JSONObject obj = new JSONObject(json);
		JSONArray array = obj.optJSONArray("lobby_history");

		if (array == null) return (null);

		int[] values = new int[array.length()];
		for (int i = 0; i < array.length(); ++i) {
			values[i] = array.getInt(i);
		}
		return (values);
	}
}
