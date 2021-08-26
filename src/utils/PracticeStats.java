package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.json.JSONObject;

public class PracticeStats {
	
	/**
	 * Return player UUID
	 * @param json
	 * @return
	 */
	public static String getUUID(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("uuid"));
	}

	/**
	 * Return player name
	 * @param json
	 * @return
	 */
	public static String getName(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("name"));
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
			value =  obj.getInt("qualification");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player finals score
	 * @param json
	 * @return
	 */
	public static String getFnals(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value = obj.getInt("finals");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player wide qualification score
	 * @param json
	 * @return
	 */
	public static String getWideQualification(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value = obj.getInt("wide_qualification");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player lobby score
	 * @param json
	 * @return
	 */
	public static String getLobby(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value = obj.getInt("lobby");
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
		int l = 0;
		int q = 0;
		int f = 0;
		
		try {
			l = obj.getInt("lobby");
			q = obj.getInt("qualification");
			f = obj.getInt("finals");
		} catch (Exception e) {}
		
		return (formatter.format(l + q + f));
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
			value =  obj.getInt("qualification");
		} catch (Exception e) {}
		return (value);
	}
	
	/**
	 * Return player finals score
	 * @param json
	 * @return
	 */
	public static int getFinalsToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value = obj.getInt("finals");
		} catch (Exception e) {}
		return (value);
	}
	
	/**
	 * Return player wide qualification score
	 * @param json
	 * @return
	 */
	public static int getWideQualificationToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value = obj.getInt("wide_qualification");
		} catch (Exception e) {}
		return (value);
	}
	
	/**
	 * Return player lobby score
	 * @param json
	 * @return
	 */
	public static int getLobbyToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value = obj.getInt("lobby");
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
		int l = 0;
		int q = 0;
		int f = 0;
		
		try {
			l = obj.getInt("lobby");
			q = obj.getInt("qualification");
			f = obj.getInt("finals");
		} catch (Exception e) {}
		
		return (l + q + f);
	}
	
}
