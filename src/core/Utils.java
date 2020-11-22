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
}
