package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

public class Config {
	
	private static JSONObject obj;
	
	/**
	 * Create new JSON object from configuration file
	 * @param file
	 * @throws IOException
	 */
	public Config(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String json = "";
		String line = "";
		
		while ((line = reader.readLine()) != null) {
			json += line;
		}
		reader.close();
		obj = new JSONObject(json);
	}
	
	/**
	 * Get string value
	 * @param string
	 * @return
	 */
	public static String getString(String string) {
		return (obj.getString(string));
	}
	
	/**
	 * Get role id
	 * @param role
	 * @return
	 */
	public static String getRoleId(String role) {
		return (obj.getJSONObject("roles").getString(role));
	}
	
	/**
	 * Get message
	 * @param role
	 * @return
	 */
	public static String getMessage(String message) {
		return (obj.getJSONObject("message").getString(message));
	}
	
	/**
	 * Set string value
	 * @param string
	 * @param value
	 */
	public static void setString(String string, String value) {
		obj.put(string, value);

		try {
			PrintWriter writer = new PrintWriter("config.json");
			writer.write(obj.toString(4));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
