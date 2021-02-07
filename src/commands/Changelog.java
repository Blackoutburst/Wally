package commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Changelog {
	
	/**
	 * Display changelogs
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		event.getChannel().sendMessage("Latest **Wally** update : "+"3 February 2021\n\n"+
				"```diff\n"+
				"* New improved personal best message (Fuby)\n"+
				"* New fresh Wally account due to unfortunate event\n"+
				"* Database go restored back to 26 october 2020 due to some issue\n"+
				"* Fixed small role glitch on link\n"+
				"* !convert F display now the correct value\n"+
				"* !stats, !compare and !profile use your own IGN if no arguments are provided\n"+
				"* Minor code upgrade\n"+
				"\n"+
				"```\n"+
				"Latest **Server** update : "+"7 February 2021\n\n"+
				"```diff\n"+
				"+ 'Old' flags, bring back old walls animation\n"+
				"+ 'Easy' flags, generate the easiest wall possible\n"+
				"\n"+
				"* Finals walls are no longer right sided\n"+
				"* Memtime now works with classic mode\n"+
				"* It's now possible to combine every play flags at the same time (you should not)\n"+
				"```\n"+
				"If you find any issue with this update please report them in <#711603045905072148> or here\n"+
				"Wally GitHub : ||https://github.com/Blackoutburst/Wally/issues||\n"+
				"Server Plugin GitHub : ||https://github.com/Blackoutburst/HitW-plugin/issues||\n"+
				"feel free to dm Blackoutburst as well").complete();
	}
}
