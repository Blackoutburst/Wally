package core;

import java.nio.file.Files;
import java.nio.file.Paths;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class Utils {
	
	/**
	 * Get user staff from role
	 * @param Member
	 * @return if the user contain the specified role
	 * @author Blackoutburst
	 */
	public static boolean isStaff(Member member) {
		for (Role r : member.getRoles()) {
			if (r.getName().equals("Staff")) {
				return (true);
			}
		}
		return (false);
	}
	
	/**
	 * Get user gender from role
	 * @param Member
	 * @return if the user contain the specified role
	 * @author Blackoutburst
	 */
	public static boolean isHe(Member member) {
		for (Role r : member.getRoles()) {
			if (r.getName().equals("He/Him")) {
				return (true);
			}
		}
		return (false);
	}
	
	/**
	 * Get user gender from role
	 * @param Member
	 * @return if the user contain the specified role
	 * @author Blackoutburst
	 */
	public static boolean isShe(Member member) {
		for (Role r : member.getRoles()) {
			if (r.getName().equals("She/Her")) {
				return (true);
			}
		}
		return (false);
	}
	
	
	/**
	 * Get user gender from role
	 * @param Member
	 * @return if the user contain the specified role
	 * @author Blackoutburst
	 */
	public static boolean isBoth(Member member) {
		if (isShe(member) && isHe(member)) {
			return (true);
		}
		return (false);
	}
	
	/**
	 * Read file value
	 * @param file
	 * @return value read
	 * @author Blackoutburst
	 */
	public static String readValue(String file) {
		String str = "0";
		try {
			str = Files.readAllLines(Paths.get(file)).get(0);
		} catch (Exception e) {
			if (!file.equals("tracker")) {
				System.out.println("Fucked File : "+file);
				e.printStackTrace();
			}
		}
		return str;
	}
}
