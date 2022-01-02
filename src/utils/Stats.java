package utils;

import org.json.JSONObject;

public class Stats {
	
	/**
	 * Return player discord id
	 */
	public static String getDiscordId(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("discordid"));
	}
	
	/**
	 * Return player UUID
	 */
	public static String getUUID(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("uuid"));
	}
	
	
	/**
	 * Return player UUID
	 */
	public static String getID(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("id"));
	}
	
	
	/**
	 * Return player subtitle
	 */
	public static String getSubTitle(String uuid) {
		if (!GeneralUtils.isLinkedUUID(uuid)) {
			return ("");
		}

		JSONObject obj = GeneralUtils.readJson("linked player/" + uuid + "/data.json");
		return (obj.getString("subtitle"));
	}
	
	/**
	 * Return player name
	 */
	public static String getName(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("name"));
	}
	
	/**
	 * Return player wins
	 */
	public static int getWinsToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value =  obj.getInt("wins");
		} catch (Exception ignored) {}
		return (value);
	}
	
	/**
	 * Return player walls cleared
	 */
	public static int getWallsToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value = obj.getInt("walls");
		} catch (Exception ignored) {}
		return (value);
	}
	
	/**
	 * Return player qualification score
	 */
	public static int getQualificationToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value = obj.getInt("qualification");
		} catch (Exception ignored) {}
		return (value);
	}
	
	/**
	 * Return player final score
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
	 * Return player total score
	 */
	public static int getTotalToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int q = 0;
		int f = 0;
		
		try {
			q = obj.getInt("qualification");
			f = obj.getInt("finals");
		} catch (Exception ignored) {}
		
		return (q + f);
	}
	
}
