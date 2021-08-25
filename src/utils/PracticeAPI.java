package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.json.JSONObject;

public class PracticeAPI {

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
			value =  obj.getJSONObject("scores").getInt("qualification");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player finals score
	 * @param json
	 * @return
	 */
	public static String getFinals(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value =  obj.getJSONObject("scores").getInt("finals");
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
			value =  obj.getJSONObject("scores").getInt("wide_qualification");
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
			value =  obj.getJSONObject("scores").getInt("lobby");
		} catch (Exception e) {}
		return (formatter.format(value));
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
			value =  obj.getJSONObject("scores").getInt("qualification");
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
			value =  obj.getJSONObject("scores").getInt("finals");
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
			value =  obj.getJSONObject("scores").getInt("wide_qualification");
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
			value =  obj.getJSONObject("scores").getInt("lobby");
		} catch (Exception e) {}
		return (value);
	}
}
