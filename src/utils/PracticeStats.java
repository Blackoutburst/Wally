package utils;

import org.json.JSONObject;

public class PracticeStats {
	
	/**
	 * Return player UUID
	 */
	public static String getUUID(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("uuid"));
	}

	/**
	 * Return player name
	 */
	public static String getName(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("name"));
	}
	
	/**
	 * Return player qualification score
	 */
	public static int getQualificationToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value =  obj.getInt("qualification");
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
			value = obj.getInt("finals");
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
			value = obj.getInt("wide_qualification");
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
			value = obj.getInt("lobby");
		} catch (Exception ignored) {}
		return (value);
	}
	
	/**
	 * Return player total score
	 */
	public static int getTotalToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int l = 0;
		int q = 0;
		int f = 0;
		int wq = 0;
		
		try {
			l = obj.getInt("lobby");
			q = obj.getInt("qualification");
			f = obj.getInt("finals");
			wq = obj.getInt("wide_qualification");
		} catch (Exception ignored) {}
		
		return (l + q + f + wq);
	}
	
}
