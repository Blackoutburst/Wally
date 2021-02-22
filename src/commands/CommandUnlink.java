package commands;

import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.MessageSender;
import utils.Utils;

public class CommandUnlink extends CommandExecutable {

	public CommandUnlink(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage());
		
		String uuid = Utils.getUUIDfromDiscord(command.getArgs()[0]);
		if (uuid == null) uuid = Request.getPlayerUUID(command.getArgs()[0]);
		if (uuid == null) return (unlinkError());
		
		Utils.unlinkMember(uuid);
		
		MessageSender.messageJSON(command, "unlink");
		return (true);
	}
}
