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
		if (command.getArgs().length == 0) {
			defaultHelp();
			return (true);
		}

		switch(command.getArgs()[0]) {
			case "hypixel":
				hypixelHelp();
			break;
			case "practice":
				practiceHelp();
			break;
			case "misc":
				miscHelp();
			break;
			case "staff":
				if (senderIsStaff()) {
					staffHelp();
				} else {
					MessageSender.message(command, command.getSender().getEffectiveName()+" took the L");
				}
			break;
			default: MessageSender.message(command, "I'm sorry try using ``!help`");
		}

		return (true);
	}

	private void miscHelp() {
		String iconURL = "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128";
		String wallyURL = "https://github.com/Blackoutburst/Wally";
		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor("Wally help", wallyURL, iconURL)
				.setTitle("Misc commands")
				.setColor(new Color(71, 165, 255))
				.addField("!skin", "Show someone skin", true)
				.addField("!pack","Redirect you to the #resource-packs channel" ,true)
				.addField("!ping", "Show how much Wally is dying", true)
				.addField("!convert [Q/F] [score]", "Convert a finals score to a qualification score and the other way around", true)
				.addField("!whois/!who [IGN]", "Tell information about a player", true)
				.addField("!setfontcolor/!sfc [color code (HEX)]", "Change your stats font color", true)
				.addField("!resetfontcolor/!rfc", "Reset your stats font color", true)
				.addField("!setfont/!sf (file required format .TTF/.OTF)", "Change your stats font", true)
				.addField("!resetfont/!rf", "Reset your stats font", true)
				.addField("!setbackground/!setbg (file required format .JPG/.JPEG/.PNG Recommended size 1000x440)", "Change your stats background", true)
				.addField("!resetbackground/!rbg", "Reset your stats background", true)
				.addField("!toggleheader/!th", "Toggle stats header visibility", true)
				.addField("!suggest [message]", "Create a new suggestion", true);
		MessageSender.sendEmbeded(command, embed);
	}

	private void practiceHelp() {
		String iconURL = "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128";
		String wallyURL = "https://github.com/Blackoutburst/Wally";
		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor("Wally help", wallyURL, iconURL)
				.setTitle("Practice server commands")
				.setColor(new Color(71, 165, 255))
				.addField("!pstats/!pstat/!ps [player]", "Show player Hole in the Wall practice server stats", true)
				.addField("!rawpstats/!rpstat/!rps [player]", "Show player Hole in the Wall practice server stats", true)
				.addField("!pgraph [player] [Q/F/L/WQ] [games]", "Show player practice server score graph", true)
				.addField("!pcompare/!pc [player1] [player2]", "Compare two players Hole in the Wall practice server stats", true)
				.addField("!crosscompare/!cc [player1] [player2]", "Compare a player Hypixel and Practice server Q and F scores", true)
				.addField("!pleaderboard/!plead/!plb [Q/F/WQ/L/T/O] [page]", "Show Hole in the Wall practice server leaderboard", true);
		MessageSender.sendEmbeded(command, embed);
	}

	private void hypixelHelp() {
		String iconURL = "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128";
		String wallyURL = "https://github.com/Blackoutburst/Wally";
		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor("Wally help", wallyURL, iconURL)
				.setTitle("Hypixel commands")
				.setColor(new Color(71, 165, 255))
				.addField("!stats/!stat/!s [player]", "Show player Hole in the Wall stats", true)
				.addField("!rawstats/!rstat/!rs [player]", "Show player Hole in the Wall stats", true)
				.addField("!compare/!c [player1] [player2]", "Compare two players Hole in the Wall stats", true)
				.addField("!crosscompare/!cc [player1] [player2]", "Compare a player Hypixel and Practice server Q and F scores", true)
				.addField("!leaderboard/!lead/!lb [W/R/Q/F/T/O] [page] \"discord\"", "Show Hole in the Wall leaderboard", true)
				.addField("!search [W/R/Q/F/T] [position]", "Search a player stats from their leaderboard position", true);
		MessageSender.sendEmbeded(command, embed);
	}

	private void defaultHelp() {
		String iconURL = "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128";
		String wallyURL = "https://github.com/Blackoutburst/Wally";
		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor("Wally help", wallyURL, iconURL)
				.setTitle("Try any of these command to get command help")
				.setColor(new Color(71, 165, 255))
				.addField("!help hypixel", "Display Hypixel stats related commands", true)
				.addField("!help practice","Display Practice server related commands" ,true)
				.addField("!help misc","Random commands" ,true)
				.addField("!help staff", "You took the L", true);
		MessageSender.sendEmbeded(command, embed);
	}

	private void staffHelp() {
		String iconURL = "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128";
		String wallyURL = "https://github.com/Blackoutburst/Wally";
		EmbedBuilder embed = new EmbedBuilder()
			.setAuthor("Wally help", wallyURL, iconURL)
			.setTitle("Staff commands")
			.setColor(new Color(71, 165, 255))
			.addField("!getconfig", "Get Wally current configuration file", true)
			.addField("!setconfig (file required)","Update Wally configuration file" ,true)
			.addField("!settracker", "Set tracker in the current channel", true)
			.addField("!showtracker", "Show where the tracker is currently set", true)
			.addField("!say [message]", "Talk through wally", true)
			.addField("!link [discord ID] [IGN]", "Link a player Discord and Minecraft account", true)
			.addField("!unlink [discordID/IGN]", "Unlink a player Discord and Minecraft account", true)
			.addField("!forcePB [IGN] [Q/F]", "Display a player personal best (no arguments mean both pb)", true)
			.addField("!forcetracker", "Force the tracker to check everyone (should not be activated all time)", true)
			.addField("!setchangelog (file required)", "Change Wally changelog", true)
			.addField("!getchangelog", "Get wally Changelog", true)
			.addField("!changelog", "Show latest Wally modifications", true)
			.addField("!prechangelog", "Show latest Wally modifications", true)
			.addField("!poll [message]", "Make a poll", true);
		MessageSender.sendEmbeded(command, embed);
	}
}
