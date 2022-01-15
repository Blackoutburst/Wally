package commands;

import java.awt.Color;

import core.Command;
import core.CommandExecutable;
import net.dv8tion.jda.api.EmbedBuilder;
import utils.MessageSender;

public class CommandHelp extends CommandExecutable {

	public CommandHelp(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (senderIsStaff()) {
			sendStaffHelp();
		}
		sendHelp();
		return (true);
	}

	private void sendStaffHelp() {
		String iconURL = "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128";
		String wallyURL = "https://github.com/Blackoutburst/Wally";
		EmbedBuilder embed = new EmbedBuilder()
			.setAuthor("Wally help", wallyURL, iconURL)
			.setTitle("Staff commands")
			.setColor(new Color(71, 165, 255))
			.addField("!getconfig", "Get Wally current configuration file", false)
			.addField("!setconfig (file required)","Update Wally configuration file" ,false)
			.addField("!settracker", "Set tracker in the current channel", false)
			.addField("!showtracker", "Show where the tracker is currently set", false)
			.addField("!say [message]", "Talk through wally", false)
			.addField("!link [discord ID] [IGN]", "Link a player Discord and Minecraft account", false)
			.addField("!unlink [discordID/IGN]", "Unlink a player Discord and Minecraft account", false)
			.addField("!forcePB [IGN] [Q/F]", "Display a player personal best (no arguments mean both pb)", false)
			.addField("!forcetracker", "Force the tracker to check everyone (should not be activated all time)", false)
			.addField("!setchangelog (file required)", "Change Wally changelog", false)
			.addField("!getchangelog", "Get wally Changelog", false)
			.addField("!changelog", "Show latest Wally modifications", false)
			.addField("!poll [message]", "Make a poll", false);
		MessageSender.sendEmbeded(command, embed);
	}

	private void sendHelp() {
		String iconURL = "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128";
		String wallyURL = "https://github.com/Blackoutburst/Wally";
		EmbedBuilder embed = new EmbedBuilder()
			.setAuthor("Wally help", wallyURL, iconURL)
			.setTitle("Available commands")
			.setColor(new Color(71, 165, 255))
			.addField("!help/!h", "Show this message", false)
			.addField("!skin", "Show someone skin", false)
			.addField("!pack","Redirect you to the #resource-packs channel" ,false)
			.addField("!stats/!stat/!s [player]", "Show player Hole in the Wall stats", false)
			.addField("!rawstats/!rstat/!rs [player]", "Show player Hole in the Wall stats", false)
			.addField("!pstats/!pstat/!ps [player]", "Show player Hole in the Wall practice server stats", false)
			.addField("!rawpstats/!rpstat/!rps [player]", "Show player Hole in the Wall practice server stats", false)
			.addField("!ping", "Show how much Wally is dying", false)
			.addField("!pgraph [player] [Q/F/L/WQ] [games]", "Show player practice server score graph", false)
			.addField("!compare/!c [player1] [player2]", "Compare two players Hole in the Wall stats", false)
			.addField("!pcompare/!pc [player1] [player2]", "Compare two players Hole in the Wall practice server stats", false)
			.addField("!crosscompare/!cc [player1] [player2]", "Compare a player Hypixel and Practice server Q and F scores", false)
			.addField("!leaderboard/!lead/!lb [W/R/Q/F/T/O] [page] \"discord\"", "Show Hole in the Wall leaderboard", false)
			.addField("!pleaderboard/!plead/!plb [Q/F/WQ/L/T/O] [page]", "Show Hole in the Wall practice server leaderboard", false)
			.addField("!convert [Q/F] [score]", "Convert a finals score to a qualification score and the other way around", false)
			.addField("!search [W/R/Q/F/T] [position]", "Search a player stats from their leaderboard position", false)
			.addField("!whois/!who [IGN]", "Tell information about a player", false)
			.addField("!setfontcolor/!sfc [color code (HEX)]", "Change your stats font color", false)
			.addField("!resetfontcolor/!rfc", "Reset your stats font color", false)
			.addField("!setfont/!sf (file required format .TTF/.OTF)", "Change your stats font", false)
			.addField("!resetfont/!rf", "Reset your stats font", false)
			.addField("!setbackground/!setbg (file required format .JPG/.JPEG/.PNG Recommended size 1000x440)", "Change your stats background", false)
			.addField("!resetbackground/!rbg", "Reset your stats background", false);
		MessageSender.sendEmbeded(command, embed);
	}
}
