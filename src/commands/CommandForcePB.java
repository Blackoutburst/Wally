package commands;

import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.API;
import utils.MessageSender;
import utils.Utils;

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
		if (!Utils.isLinkedIGN(ign)) return (notInDatabase(this));
		
		switch(type) {
			case Q : MessageSender.pbMessage(data, Utils.getDiscordfromIGN(ign), Request.getPlayerUUID(ign), 'q');break;
			case F : MessageSender.pbMessage(data, Utils.getDiscordfromIGN(ign), Request.getPlayerUUID(ign), 'f');break;
			default :
				MessageSender.pbMessage(data, Utils.getDiscordfromIGN(ign), Request.getPlayerUUID(ign), 'q');
				MessageSender.pbMessage(data, Utils.getDiscordfromIGN(ign), Request.getPlayerUUID(ign), 'f');
			break;
		}
		return (true);
	}
	
	/**
	 * Get player ign
	 * @return
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
	 * @return
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
