package commands;

import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.API;
import utils.MessageSender;
import utils.GeneralUtils;

public class CommandForcePB extends CommandExecutable {

	enum Type {
		Q,
		F,
		BOTH
	}
	
	public CommandForcePB(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage(this));
		
		Type type = getPBType();
		String ign = getIGN();
		
		String data = Request.getPlayerStats(ign);
		if (data == null) return (unknownPlayer(this, ign));
		if (API.getPlayer(data) == null) return (neverJoined(this, ign));
		if (!GeneralUtils.isLinkedUUID(API.getUUID(data))) return (notInDatabase(this));
		
		String discordId = GeneralUtils.getDiscordfromIGN(ign);
		String uuid = API.getUUID(data);
		String localData = GeneralUtils.readJsonToString("linked player/"+uuid+"/data.json");
		
		switch(type) {
			case Q : MessageSender.pbMessage(data, discordId, uuid, 'q');break;
			case F : MessageSender.pbMessage(data, discordId, uuid, 'f');break;
			default :
				MessageSender.pbMessage(data, discordId, uuid, 'q');
				MessageSender.pbMessage(data, discordId, uuid, 'f');
			break;
		}
		GeneralUtils.updateFile(data, localData, uuid, "linked player", false);
		GeneralUtils.updateFile(data, localData, uuid, "leaderboard", false);
		MessageSender.message(command, ign+" PB displayed");
		return (true);
	}
	
	/**
	 * Get player ign
	 */
	private String getIGN() {
		for (String arg : command.getArgs()) {
			if (arg.length() != 1) {
				return (arg);
			}
		}
		return ("");
	}
	
	/**
	 * Get personal best type
	 */
	private Type getPBType() {
		for (String arg : command.getArgs()) {
			if (arg.length() == 1) {
				switch(arg.toLowerCase().charAt(0)) {
					case 'q' : return (Type.Q);
					case 'f' : return (Type.F);
					default : return (Type.BOTH);
				}
			}
		}
		return (Type.BOTH);
	}
}
