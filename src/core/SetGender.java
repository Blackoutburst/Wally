package core;

import java.util.List;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;

public class SetGender {
	
	/**
	 * Set user role on message reaction
	 * @param event
	 * @author Blackoutburst
	 */
	public static void addRole(GuildMessageReactionAddEvent event) {
		switch(event.getReactionEmote().toString()) {
			case "RE:U+1f1ed" : add("He/Him", event); break;
			case "RE:U+1f1f8" : add("She/Her", event); break;
			case "RE:U+1f1f9" : add("They/Them", event); break;
		}
	}
	
	/**
	 * Add specified role
	 * @param roleName
	 * @param event
	 * @author Blackoutburst
	 */
	private static void add(String roleName, GuildMessageReactionAddEvent event) {
		List<Role> roles = event.getGuild().getRoles();
		
		for (Role r : roles) {
			if (r.getName().equals(roleName)) {
				event.getGuild().addRoleToMember(event.getMember(), r).complete();
				break;
			}
		}
	}
	
	/**
	 * Remove user role when user remove message reaction
	 * @param event
	 * @author Blackoutburst
	 */
	public static void removeRole(GuildMessageReactionRemoveEvent event) {
		switch(event.getReactionEmote().toString()) {
			case "RE:U+1f1ed" : remove("He/Him", event); break;
			case "RE:U+1f1f8" : remove("She/Her", event); break;
			case "RE:U+1f1f9" : remove("They/Them", event); break;
		}
	}
	
	/**
	 * Remove specified role
	 * @param roleName
	 * @param event
	 * @author Blackoutburst
	 */
	private static void remove(String roleName, GuildMessageReactionRemoveEvent event) {
		List<Role> roles = event.getGuild().getRoles();
		
		for (Role r : roles) {
			if (r.getName().equals(roleName)) {
				event.getGuild().removeRoleFromMember(event.getMember(), r).complete();
				break;
			}
		}
	}
}
