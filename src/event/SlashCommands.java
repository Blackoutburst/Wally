package event;

import commands.CommandManager;
import core.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class SlashCommands {
	
	/**
	 * Execute the event
	 * @param event
	 */
	public void run(SlashCommandEvent  event) {
		if (event.getGuild() == null) return;
		if (event.getMember().getUser().isBot()) return;
		if (event.getMember() == null) return;
		
		Member sender = event.getMember();
		String name = event.getName();
		event.getOptions();
		
		String[] args = new String[event.getOptions().size()];
		
		int i = 0;
		for (OptionMapping opt : event.getOptions()) {
			args[i++] = opt.getAsString();
		}
		event.reply("⠀").complete();
		new CommandManager(new Command(sender, name, args, event.getChannel(), null));
	}
}
