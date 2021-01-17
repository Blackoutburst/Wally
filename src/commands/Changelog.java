package commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Changelog {
	
	/**
	 * Display changelogs
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		event.getChannel().sendMessage("Latest **Wally** update : "+"17 January 2021\n\n"+
				"```diff\n"+
				"+ Convert F is now a thing\n"+
				"\n"+
				"- Removed some console output\n"+
				"\n"+
				"* Changelog now display practice server changelogs\n"+
				"* Stats commands now display global leaderboard position next to every stats\n"+
				"* Discord leaderboard now update properly when user leave the discord\n"+
				"* Changed Hypixel API request form (JS -> Java) increasing a bit the speed of command such as stats and compare\n"+
				"* Tracker speed increased might need some modification\n"+
				"```\n"+
				"Latest **Server** update : "+"17 January 2021\n\n"+
				"```diff\n"+
				"+ Server support now 1.8 to 1.16\n"+
				"\n"+
				"* Classic mode in co op is now working properly again\n"+
				"* Tournament in co op is now working properly\n"+
				"```\n"+
				"If you find any issue with this update please repport them in <#711603045905072148> or here\n"+
				"Wally GitHub : ||https://github.com/Blackoutburst/Wally/issues||\n"+
				"Server Plugin GitHub : ||https://github.com/Blackoutburst/HitW-plugin/issues||\n"+
				"feel free to dm Blackoutburst as well").complete();
	}
}
