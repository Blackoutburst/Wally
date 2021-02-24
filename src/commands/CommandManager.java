package commands;

import core.Command;

public class CommandManager {
	
	public CommandManager(Command command) {
		switch (command.getName()) {
			case "help": case "h": new CommandHelp(command, false, "!help").run(); break;
			case "pack": new CommandPack(command, false, "!pack").run(); break;
			case "stats": case "stat": case "s": new CommandStats(command, false, "!stats [player]").run(); break;
			case "ping": new CommandPing(command, false, "!ping").run(); break;
			case "compare": case "c": new CommandCompare(command, false, "!compare [player] [player]").run(); break;
			case "getconfig": new CommandGetConfig(command, true, "!getconfig").run(); break;
			case "setconfig": new CommandSetConfig(command, true,"!setconfig (file required)").run(); break;
			case "settracker": new CommandSetTracker(command, true, "!settracker").run(); break;
			case "showtracker": new CommandShowTracker(command, true, "!showtracker").run(); break;
			case "say": new CommandSay(command, true, "!say [message]").run(); break;
			case "link": new CommandLink(command, true, "!link [discord ID] [IGN]").run(); break;
			case "unlink": new CommandUnlink(command, true, "!unlink [discordID/IGN]").run(); break;
			case "setbackground": case "setbg": new CommandSetBackground(command, false, "!setbackground (file required)").run(); break;
			case "resetbackground": case "rbg": new CommandResetBackground(command, false, "!resetbackground").run(); break;
			case "removebackground": new CommandRemoveBackground(command, true, "!removebackground [IGN]").run(); break;
			case "leaderboard": case "lead": case "lb": new CommandLeaderboard(command, false, "!leaderboard [W/R/Q/F/T] [page] [\"discord\"]").run(); break;
			case "forcepb": new CommandForcePB(command, true, "!forcePB [IGN] [Q/F]").run(); break;
			case "profile": case "p": new CommandProfile(command, false, "!profile [player]").run(); break;
			case "forcetracker": new CommandForceTracker(command, true, "!forcetracker").run(); break;
			case "changelog": new CommandChangelog(command, false, "!changelog").run(); break;
			case "setchangelog": new CommandSetChangelog(command, true, "!setchangelog (file required)").run(); break;
			case "getchangelog": new CommandGetChangelog(command, true, "!getchangelog").run(); break;
			case "convert": new CommandConvert(command, false, "!convert [Q/F] [score]").run(); break;
			case "poll": new CommandPoll(command, true, "!poll [message]").run(); break;
			default : return;
		}
	}
}
