package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.json.JSONObject;

public class API {

	/**
	 * Return player name
	 * @param json
	 * @return
	 */
	public static Object getPlayer(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.get("player"));
	}
	
	/**
	 * Return player name
	 * @param json
	 * @return
	 */
	public static String getName(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getJSONObject("player").getString("displayname"));
	}
	
	/**
	 * Return player UUID
	 * @param json
	 * @return
	 */
	public static String getUUID(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getJSONObject("player").getString("uuid"));
	}
	
	/**
	 * Return player wins
	 * @param json
	 * @return
	 */
	public static String getWins(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value =  obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("wins_hole_in_the_wall");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player walls cleared
	 * @param json
	 * @return
	 */
	public static String getWalls(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value = obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("rounds_hole_in_the_wall");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player qualification score
	 * @param json
	 * @return
	 */
	public static String getQualification(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value = obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("hitw_record_q");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player final score
	 * @param json
	 * @return
	 */
	public static String getFinals(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value = obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("hitw_record_f");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player total score
	 * @param json
	 * @return
	 */
	public static String getTotal(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int q = 0;
		int f = 0;
		
		try {
			q = obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("hitw_record_q");
			f = obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("hitw_record_f");
		} catch (Exception e) {}
		
		return (formatter.format(q + f));
	}
	
	/**
	 * Return player wins
	 * @param json
	 * @return
	 */
	public static int getWinsToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value =  obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("wins_hole_in_the_wall");
		} catch (Exception e) {}
		return (value);
	}
	
	/**
	 * Return player walls cleared
	 * @param json
	 * @return
	 */
	public static int getWallsToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value = obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("rounds_hole_in_the_wall");
		} catch (Exception e) {}
		return (value);
	}
	
	/**
	 * Return player qualification score
	 * @param json
	 * @return
	 */
	public static int getQualificationToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value = obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("hitw_record_q");
		} catch (Exception e) {}
		return (value);
	}
	
	/**
	 * Return player final score
	 * @param json
	 * @return
	 */
	public static int getFinalsToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value = obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("hitw_record_f");
		} catch (Exception e) {}
		return (value);
	}
	
	/**
	 * Return player total score
	 * @param json
	 * @return
	 */
	public static int getTotalToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int q = 0;
		int f = 0;
		
		try {
			q = obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("hitw_record_q");
			f = obj.getJSONObject("player").getJSONObject("stats").getJSONObject("Arcade").getInt("hitw_record_f");
		} catch (Exception e) {}
		
		return (q + f);
	}
}
