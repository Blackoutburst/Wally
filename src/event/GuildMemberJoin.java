package event;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import utils.ConfigManager;

public class GuildMemberJoin {
	
	/**
	 * Execute the event
	 */
	public void run(GuildMemberJoinEvent event) {
		if (event.getMember().getUser().isBot()) return;
		
		Role role = event.getGuild().getRoleById(ConfigManager.getRoleId("Members"));
		
		if (role == null) return;
		
		event.getGuild().addRoleToMember(event.getMember(), role).complete();
	}
}
