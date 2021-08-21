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
	
	public CommandManager(Command command) {
		switch (command.getName()) {
			case "help": case "h": new CommandHelp(command, EVERYONE, "!help").run(); break;
			case "pack": new CommandPack(command, EVERYONE, "!pack").run(); break;
			case "stats": case "stat": case "s": new CommandStats(command, EVERYONE, "!stats [player]").run(); break;
			case "ping": new CommandPing(command, EVERYONE, "!ping").run(); break;
			case "compare": case "c": new CommandCompare(command, EVERYONE, "!compare [player] [player]").run(); break;
			case "getconfig": new CommandGetConfig(command, ADMIN, "!getconfig").run(); break;
			case "setconfig": new CommandSetConfig(command, ADMIN,"!setconfig (file required)").run(); break;
			case "settracker": new CommandSetTracker(command, ADMIN, "!settracker").run(); break;
			case "showtracker": new CommandShowTracker(command, ADMIN, "!showtracker").run(); break;
			case "say": new CommandSay(command, ADMIN, "!say [message]").run(); break;
			case "link": new CommandLink(command, ADMIN, "!link [discord ID] [IGN]").run(); break;
			case "unlink": new CommandUnlink(command, ADMIN, "!unlink [discordID/IGN]").run(); break;
			case "leaderboard": case "lead": case "lb": new CommandLeaderboard(command, EVERYONE, "!leaderboard [W/R/Q/F/T] [page] [\"discord\"]").run(); break;
			case "forcepb": new CommandForcePB(command, ADMIN, "!forcePB [IGN] [Q/F]").run(); break;
			case "forcetracker": new CommandForceTracker(command, ADMIN, "!forcetracker").run(); break;
			case "changelog": new CommandChangelog(command, ADMIN, "!changelog").run(); break;
			case "setchangelog": new CommandSetChangelog(command, ADMIN, "!setchangelog (file required)").run(); break;
			case "getchangelog": new CommandGetChangelog(command, ADMIN, "!getchangelog").run(); break;
			case "convert": new CommandConvert(command, EVERYONE, "!convert [Q/F] [score]").run(); break;
			case "poll": new CommandPoll(command, ADMIN, "!poll [message]").run(); break;
			case "search": new CommandSearch(command, EVERYONE, "!search [W/R/Q/F/T] [position]").run(); break;
			case "whois": case "who": new CommandWhois(command, EVERYONE, "!whois [IGN]").run(); break;
			default : return;
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
		commands.addCommands(new CommandData("poll", "Make a poll (Requires permission)").addOptions(new OptionData(OptionType.STRING, "message", "The poll message Wally will say").setRequired(true)));
	
		//Users
		commands.addCommands(new CommandData("help", "Show this message"));
		commands.addCommands(new CommandData("pack", "Redirect you to the #resource-packs channel"));
		commands.addCommands(new CommandData("stats", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));
		commands.addCommands(new CommandData("stat", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));
		commands.addCommands(new CommandData("s", "Show player Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player", "The player you want to check")));
		commands.addCommands(new CommandData("ping", "Show how much Wally is dying"));
		commands.addCommands(new CommandData("compare", "Compare two players Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player1", "The first player").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "player2", "The second player")));
		commands.addCommands(new CommandData("c", "Compare two players Hole in the Wall stats").addOptions(new OptionData(OptionType.STRING, "player1", "The first player").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "player2", "The second player")));
		commands.addCommands(new CommandData("leaderboard", "Show Hole in the Wall leaderboard").addOptions(new OptionData(OptionType.STRING, "type", "Leaderboard type (W/R/Q/F/T)")).addOptions(new OptionData(OptionType.STRING, "page", "Leaderboard page")).addOptions(new OptionData(OptionType.STRING, "discord", "Show only discord members leaderboard")));
		commands.addCommands(new CommandData("lead", "Show Hole in the Wall leaderboard").addOptions(new OptionData(OptionType.STRING, "type", "Leaderboard type (W/R/Q/F/T)")).addOptions(new OptionData(OptionType.STRING, "page", "Leaderboard page")).addOptions(new OptionData(OptionType.STRING, "discord", "Show only discord members leaderboard")));
		commands.addCommands(new CommandData("lb", "Show Hole in the Wall leaderboard").addOptions(new OptionData(OptionType.STRING, "type", "Leaderboard type (W/R/Q/F/T)")).addOptions(new OptionData(OptionType.STRING, "page", "Leaderboard page")).addOptions(new OptionData(OptionType.STRING, "discord", "Show only discord members leaderboard")));
		commands.addCommands(new CommandData("convert", "Convert a finals score to a qualification score and the other way around").addOptions(new OptionData(OptionType.STRING, "score", "Score").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "type", "Score type (Q/F)").setRequired(true)));
		commands.addCommands(new CommandData("search", "Search a player stats from their leaderboard position").addOptions(new OptionData(OptionType.STRING, "type", "Leaderboard type (W/R/Q/F/T)").setRequired(true)).addOptions(new OptionData(OptionType.STRING, "position", "Leaderboard position").setRequired(true)));
		commands.addCommands(new CommandData("whois", "Tell information about a player").addOptions(new OptionData(OptionType.STRING, "ign", "The Minecraft name of player you want to check").setRequired(true)));
		commands.addCommands(new CommandData("who", "Tell information about a player").addOptions(new OptionData(OptionType.STRING, "ign", "The Minecraft name of player you want to check").setRequired(true)));
		
		commands.queue();
	}
}
