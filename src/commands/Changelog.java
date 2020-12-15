package commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Changelog {
	
	/**
	 * Display changelogs
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		event.getChannel().sendMessage("Latest update : "+"15 december 2020\n\n"+"```diff\n"+"+ !convert command"+"\n```\nIf you find any issue with this update please repport them in <#711603045905072148> or here ||https://github.com/Blackoutburst/Wally/issues||, feel free to dm Blackoutburst as well").complete();
	}
}
