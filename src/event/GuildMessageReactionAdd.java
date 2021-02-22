package event;

import core.RoleAction;
import core.RolesManager;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import utils.Config;

public class GuildMessageReactionAdd {
	
	/**
	 * Execute the event
	 * @param event
	 */
	public void run(GuildMessageReactionAddEvent event) {
		if (event.getMember().getUser().isBot()) return;
		if (event.getMessageId().equals(Config.getString("tournamentMessage"))) {
			new RolesManager().setTournamentPlayer(RoleAction.ADD, event.getGuild(), event.getMember());
		}
		if (event.getMessageId().equals(Config.getString("genderMessage"))) {
			String roleName = getGender(event.getReactionEmote().toString());
			
			switch(event.getReactionEmote().toString()) {
				case "RE:U+1f1ed" : roleName = "He/Him"; break;
				case "RE:U+1f1f8" : roleName = "She/Her"; break;
				case "RE:U+1f1f9" : roleName = "They/Them"; break;
			}
			new RolesManager().setGender(RoleAction.ADD, event.getGuild(), event.getMember(), roleName);
		}
	}
	
	/**
	 * Get gender role from reaction
	 * @param emote
	 * @return
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
