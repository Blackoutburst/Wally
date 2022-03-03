package commands;

import core.Command;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class CommandManager {
	
	private static final boolean ADMIN = true;
	private static final boolean EVERYONE = false;
	
	public static void run(Command command) {
		switch (command.getName()) {
			case "skin": new CommandSkin(command, EVERYONE, "!skin [player]").run(); break;
			case "help": case "h": new CommandHelp(command, EVERYONE, "!help").run(); break;
			case "pack": new CommandPack(command, EVERYONE, "!pack").run(); break;
			case "stats": case "stat": case "s": new CommandStats(command, EVERYONE, "!stats [player]").run(); break;
			case "rawstats": case "rstat": case "rs": new CommandRawStats(command, EVERYONE, "!rawstats [player]").run(); break;
			case "pstats": case "pstat": case "ps": new CommandPStats(command, EVERYONE, "!pstats [player]").run(); break;
			case "rawpstats": case "rpstat": case "rps": new CommandRawPStats(command, EVERYONE, "!rawpstats [player]").run(); break;
			case "pgraph": new CommandPGraph(command, EVERYONE, "!pgraph [player] [Q/F/WQ/L]").run(); break;
			case "ping": new CommandPing(command, EVERYONE, "!ping").run(); break;
			case "compare": case "c": new CommandCompare(command, EVERYONE, "!compare [player] [player]").run(); break;
			case "pcompare": case "pc": new CommandPCompare(command, EVERYONE, "!pcompare [player] [player]").run(); break;
			case "crosscompare": case "cc": new CommandCrossCompare(command, EVERYONE, "!crosscompare [player] [player]").run(); break;
			case "getconfig": new CommandGetConfig(command, ADMIN, "!getconfig").run(); break;
			case "setconfig": new CommandSetConfig(command, ADMIN,"!setconfig (file required)").run(); break;
			case "settracker": new CommandSetTracker(command, ADMIN, "!settracker").run(); break;
			case "showtracker": new CommandShowTracker(command, ADMIN, "!showtracker").run(); break;
			case "say": new CommandSay(command, ADMIN, "!say [message]").run(); break;
			case "link": new CommandLink(command, ADMIN, "!link [discord ID] [IGN]").run(); break;
			case "unlink": new CommandUnlink(command, ADMIN, "!unlink [discordID/IGN]").run(); break;
			case "leaderboard": case "lead": case "lb": new CommandLeaderboard(command, EVERYONE, "!leaderboard [W/R/Q/F/T] [page] [\"discord\"]").run(); break;
			case "pleaderboard": case "plead": case "plb": new CommandPLeaderboard(command, EVERYONE, "!leaderboard [Q/F/WQ/L/T] [page]").run(); break;
			case "forcepb": new CommandForcePB(command, ADMIN, "!forcePB [IGN] [Q/F]").run(); break;
			case "forcetracker": new CommandForceTracker(command, ADMIN, "!forcetracker").run(); break;
			case "changelog": new CommandChangelog(command, ADMIN, "!changelog").run(); break;
			case "prechangelog": new CommandPreChangelog(command, ADMIN, "!prechangelog").run(); break;
			case "setchangelog": new CommandSetChangelog(command, ADMIN, "!setchangelog (file required)").run(); break;
			case "getchangelog": new CommandGetChangelog(command, ADMIN, "!getchangelog").run(); break;
			case "convert": new CommandConvert(command, EVERYONE, "!convert [Q/F] [score]").run(); break;
			case "poll": new CommandPoll(command, ADMIN, "!poll [message]").run(); break;
			case "search": new CommandSearch(command, EVERYONE, "!search [W/R/Q/F/T] [position]").run(); break;
			case "whois": case "who": new CommandWhois(command, EVERYONE, "!whois [IGN]").run(); break;
			case "setfontcolor": case "sfc": new CommandSetFontColor(command, EVERYONE, "!setfontcolor [color code (HEX)]").run(); break;
			case "resetfontcolor": case "rfc": new CommandResetFontColor(command, EVERYONE, "!resetfontcolor").run(); break;
			case "setbackground": case "sbg": case "setbg": new CommandSetBackground(command, EVERYONE, "!setbackground (file required format .JPG/.JPEG/.PNG Recommended size 1000x600)").run(); break;
			case "resetbackground": case "rbg": new CommandResetBackground(command, EVERYONE, "!resetbackground").run(); break;
			case "setfont": case "sf": new CommandSetFont(command, EVERYONE, "!setfont (file required format .TTF/.OTF)").run(); break;
			case "resetfont": case "rf": new CommandResetFont(command, EVERYONE, "!resetfont").run(); break;
			case "toggleheader": case "th": new CommandToggleHeader(command, EVERYONE, "!toggleheader").run(); break;
			case "suggest": new CommandSuggest(command, EVERYONE, "!suggest [Your idea or bug report]").run(); break;
			default :
		}
	}
	
	public static void registerSlashCommands(JDA jda) {
		CommandListUpdateAction commands = jda.updateCommands();
		
		//Staff
		commands.addCommands(new CommandData("getconfig", "Get Wally current configuration file (Requires permission)"));
		commands.addCommands(new CommandData("setconfig", "Update Wally configuration file (file required) (Requires permission)"));
		commands.addCommands(new CommandData("settracker", "Set tracker in the current channel (Requires permission)"));
		commands.addCommands(new CommandData("showtracker", "Show where the tracker is currently set (Requires permission)"));
		commands.addCommands(new CommandData("say", "Talk through wally (Requires permission)").addOptions(new OptionData(OptionType.STRING, "message", "The message Wally will say").setRequired(true)));
		commands.addCommands(new CommandData("link", "Link a player Discord and Minecraft account (Requires permission)").addOptions(new OptionData(OptionType.STRING, "discordid", "The discord account ID").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "ign", "The Minecraft username").setRequired(true)));
		commands.addCommands(new CommandData("unlink", "Unlink a player Discord and Minecraft account (Requires permission)").addOptions(new OptionData(OptionType.STRING, "who", "The discord account ID or The Minecraft username").setRequired(true)));
		commands.addCommands(new CommandData("forcepb", "Display a player personal best (no arguments mean both pb) (Requires permission)").addOptions(new OptionData(OptionType.STRING, "ign", "The Minecraft username").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "type", "Personal best type (Q/F)")));
		commands.addCommands(new CommandData("forcetracker", "Force the tracker to check everyone (should not be activated all time) (Requires permission)"));
		commands.addCommands(new CommandData("setchangelog", "Change Wally changelog (file required) (Requires permission)"));
		commands.addCommands(new CommandData("getchangelog", "Get wally Changelog (Requires permission)"));
		commands.addCommands(new CommandData("changelog", "Show latest Wally modifications (Requires permission)"));
		commands.addCommands(new CommandData("prechangelog", "Show latest Wally modifications (Requires permission)"));
		commands.addCommands(new CommandData("poll", "Make a poll (Requires permission)").addOptions(new OptionData(OptionType.STRING, "message", "The poll message Wally will say").setRequired(true)));
	
		//Users
		commands.addCommands(new CommandData("skin", "Show player skin").addOptions(new OptionData(OptionType.STRING, "player", "The player skin you want to check")));
		commands.addCommands(new CommandData("help", "Show this message"));
		commands.addCommands(new CommandData("pack", "Redirect you to the #resource-packs channel"));
		commands.addCommands(new CommandData("stats", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));
		commands.addCommands(new CommandData("stat", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));
		commands.addCommands(new CommandData("s", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));

		commands.addCommands(new CommandData("rawstats", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));
		commands.addCommands(new CommandData("rstat", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));
		commands.addCommands(new CommandData("rs", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));

		commands.addCommands(new CommandData("pstats", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));
		commands.addCommands(new CommandData("pstat", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));
		commands.addCommands(new CommandData("ps", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));

		commands.addCommands(new CommandData("rawpstats", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));
		commands.addCommands(new CommandData("rpstat", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));
		commands.addCommands(new CommandData("rps", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));

		commands.addCommands(new CommandData("pgraph", "Show player practice server score graph").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")).addOptions(new OptionData(OptionType.STRING, "type", "Graph type (Q/F/WQ/L)")));
		
		commands.addCommands(new CommandData("ping", "Show how much Wally is dying"));
		commands.addCommands(new CommandData("compare", "Compare two players Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player1", "The first player").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "player2", "The second player")));
		commands.addCommands(new CommandData("c", "Compare two players Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player1", "The first player").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "player2", "The second player")));
		
		commands.addCommands(new CommandData("pcompare", "Compare two players Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player1", "The first player").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "player2", "The second player")));
		commands.addCommands(new CommandData("pc", "Compare two players Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player1", "The first player").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "player2", "The second player")));

		commands.addCommands(new CommandData("crosscompare", "Compare a player Hypixel and Practice server Q and F scores").addOptions(new OptionData(OptionType.STRING, "player1", "The first player").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "player2", "The second player")));
		commands.addCommands(new CommandData("cc", "Compare a player Hypixel and Practice server Q and F scores").addOptions(new OptionData(OptionType.STRING, "player1", "The first player").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "player2", "The second player")));

		commands.addCommands(new CommandData("leaderboard", "Show Hole in the Wall leaderboard").addOptions(new OptionData(OptionType.STRING, "type", "Leaderboard type (W/R/Q/F/T/O)")).addOptions(new OptionData(OptionType.STRING, "page", "Leaderboard page")).addOptions(new OptionData(OptionType.STRING, "discord", "Show only discord members leaderboard")));
		commands.addCommands(new CommandData("lead", "Show Hole in the Wall leaderboard").addOptions(new OptionData(OptionType.STRING, "type", "Leaderboard type (W/R/Q/F/T/O)")).addOptions(new OptionData(OptionType.STRING, "page", "Leaderboard page")).addOptions(new OptionData(OptionType.STRING, "discord", "Show only discord members leaderboard")));
		commands.addCommands(new CommandData("lb", "Show Hole in the Wall leaderboard").addOptions(new OptionData(OptionType.STRING, "type", "Leaderboard type (W/R/Q/F/T/O)")).addOptions(new OptionData(OptionType.STRING, "page", "Leaderboard page")).addOptions(new OptionData(OptionType.STRING, "discord", "Show only discord members leaderboard")));
		
		commands.addCommands(new CommandData("pleaderboard", "Show Hole in the Wall practice server leaderboard").addOptions(new OptionData(OptionType.STRING, "type", "Leaderboard type (Q/F/WQ/L/T/O)")).addOptions(new OptionData(OptionType.STRING, "page", "Leaderboard page")));
		commands.addCommands(new CommandData("plead", "Show Hole in the Wall practice server leaderboard").addOptions(new OptionData(OptionType.STRING, "type", "Leaderboard type (Q/F/WQ/L/T/O)")).addOptions(new OptionData(OptionType.STRING, "page", "Leaderboard page")));
		commands.addCommands(new CommandData("plb", "Show Hole in the Wall practice server leaderboard").addOptions(new OptionData(OptionType.STRING, "type", "Leaderboard type (Q/F/WQ/L/T/O)")).addOptions(new OptionData(OptionType.STRING, "page", "Leaderboard page")));
		
		commands.addCommands(new CommandData("convert", "Convert a finals score to a qualification score and the other way around").addOptions(new OptionData(OptionType.STRING, "score", "Score").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "type", "Score type (Q/F)").setRequired(true)));
		commands.addCommands(new CommandData("search", "Search a player stats from their leaderboard position").addOptions(new OptionData(OptionType.STRING, "type", "Leaderboard type (W/R/Q/F/T)").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "position", "Leaderboard position").setRequired(true)));
		commands.addCommands(new CommandData("whois", "Tell information about a player").addOptions(new OptionData(OptionType.STRING, "ign", "The Minecraft name of player you want to check").setRequired(true)));
		commands.addCommands(new CommandData("who", "Tell information about a player").addOptions(new OptionData(OptionType.STRING, "ign", "The Minecraft name of player you want to check").setRequired(true)));
		
		commands.addCommands(new CommandData("setfontcolor", "Change your stats font color").addOptions(new OptionData(OptionType.STRING, "color", "The color code in hexadecimal").setRequired(true)));
		commands.addCommands(new CommandData("resetfontcolor", "Reset your stats font color"));
		
		commands.addCommands(new CommandData("sfc", "Change your stats font color").addOptions(new OptionData(OptionType.STRING, "color", "The color code in hexadecimal").setRequired(true)));
		commands.addCommands(new CommandData("rfc", "Reset your stats font color"));
		
		commands.addCommands(new CommandData("setbackground", "Set your stats background (file required format .JPG/.JPEG/.PNG Recommended size 1000x600)"));
		commands.addCommands(new CommandData("resetbackground", "Reset your stats background"));
		
		commands.addCommands(new CommandData("sbg", "Set your stats background (file required format .JPG/.JPEG/.PNG Recommended size 1000x600)"));
		commands.addCommands(new CommandData("setbg", "Set your stats background (file required format .JPG/.JPEG/.PNG Recommended size 1000x600)"));
		commands.addCommands(new CommandData("rbg", "Reset your stats background"));
		
		commands.addCommands(new CommandData("setfont", "Set your stats font (file required format .TTF/.OTF)"));
		commands.addCommands(new CommandData("resetfont", "Reset your stats font"));
		
		commands.addCommands(new CommandData("sf", "Set your stats font (file required format .TTF/.OTF)"));
		commands.addCommands(new CommandData("rf", "Reset your stats font"));

		commands.addCommands(new CommandData("toggleheader", "Toggle your stats header"));
		commands.addCommands(new CommandData("th", "Toggle your stats header"));

		commands.addCommands(new CommandData("suggest", "Create a new suggestion for Wally or the practice server").addOptions(new OptionData(OptionType.STRING, "message", "The suggestion").setRequired(true)));
		commands.queue();
	}
}
