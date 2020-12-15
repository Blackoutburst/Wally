package core;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class Utils {
	public static boolean isStaff(Member member) {
		for (Role r : member.getRoles()) {
			if (r.getName().equals("Staff")) {
				return (true);
			}
		}
		return (false);
	}
	
	public static boolean isHe(Member member) {
		for (Role r : member.getRoles()) {
			if (r.getName().equals("He/Him")) {
				return (true);
			}
		}
		return (false);
	}
	public static boolean isShe(Member member) {
		for (Role r : member.getRoles()) {
			if (r.getName().equals("She/Her")) {
				return (true);
			}
		}
		return (false);
	}
	
	public static boolean isBoth(Member member) {
		if (isShe(member) && isHe(member)) {
			return (true);
		}
		return (false);
	}
}
