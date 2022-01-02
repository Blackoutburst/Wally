package event;

import core.RoleAction;
import core.RolesManager;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import utils.ConfigManager;

import java.util.Objects;

public class GuildMessageReactionRemove {
	
	/**
	 * Execute the event
	 */
	public void run(GuildMessageReactionRemoveEvent event) {
		if (Objects.requireNonNull(event.getMember()).getUser().isBot()) return;
		if (event.getMessageId().equals(ConfigManager.getString("tournamentMessage"))) {
			new RolesManager().setTournamentPlayer(RoleAction.REMOVE, event.getGuild(), event.getMember());
		}
		if (event.getMessageId().equals(ConfigManager.getString("genderMessage"))) {
			String roleName = getGender(event.getReactionEmote().toString());
			
			new RolesManager().setGender(RoleAction.REMOVE, event.getGuild(), event.getMember(), roleName);
		}
	}
	
	/**
	 * Get gender role from reaction
	 */
	private String getGender(String emote) {
		switch(emote) {
			case "RE:U+1f1ed" : return ("He/Him");
			case "RE:U+1f1f8" : return ("She/Her");
			case "RE:U+1f1f9" : return ("They/Them");
			default: return ("unknown");
		}
	}
}
