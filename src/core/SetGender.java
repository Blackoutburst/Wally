package core;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;

public class SetGender {
	
	public static void setRole(GuildMessageReactionAddEvent event) {
		Member user = event.getGuild().getMember(event.getUser());
		Role role = null;
		List<Role> roles = event.getGuild().getRoles();
		
		// She/Her
		if (event.getReactionEmote().toString().equals("RE:U+1f1f8")) {
			for (Role r : roles) {
				if (r.getName().contains("She/Her")) {
				role = r;
				}
			}
			event.getGuild().addRoleToMember(user, role).complete();
		}
		
		// He/Him
		if (event.getReactionEmote().toString().equals("RE:U+1f1ed")) {
			for (Role r : roles) {
				if (r.getName().contains("He/Him")) {
				role = r;
				}
			}
			event.getGuild().addRoleToMember(user, role).complete();
		}
		
		// They/Them
		if (event.getReactionEmote().toString().equals("RE:U+1f1f9")) {
			for (Role r : roles) {
				if (r.getName().contains("They/Them")) {
				role = r;
				}
			}
			event.getGuild().addRoleToMember(user, role).complete();
		}
	}
	
	public static void removeRole(GuildMessageReactionRemoveEvent event) {
		Member user = event.getGuild().getMember(event.getUser());
		Role role = null;
		List<Role> roles = event.getGuild().getRoles();
		
		// She/Her
		if (event.getReactionEmote().toString().equals("RE:U+1f1f8")) {
			for (Role r : roles) {
				if (r.getName().contains("She/Her")) {
				role = r;
				}
			}
			event.getGuild().removeRoleFromMember(user, role).complete();
		}
		
		// He/Him
		if (event.getReactionEmote().toString().equals("RE:U+1f1ed")) {
			for (Role r : roles) {
				if (r.getName().contains("He/Him")) {
				role = r;
				}
			}
			event.getGuild().removeRoleFromMember(user, role).complete();
		}
		
		// They/Them
		if (event.getReactionEmote().toString().equals("RE:U+1f1f9")) {
			for (Role r : roles) {
				if (r.getName().contains("They/Them")) {
				role = r;
				}
			}
			event.getGuild().removeRoleFromMember(user, role).complete();
		}
	}
}
