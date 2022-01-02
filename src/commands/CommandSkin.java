package commands;

import core.Command;
import core.CommandError;
import core.CommandExecutable;
import core.Request;
import utils.MessageSender;

public class CommandSkin extends CommandExecutable implements CommandError {

	public CommandSkin(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage(this));

		String uuid = Request.getPlayerUUID(command.getArgs()[0]);

		if (uuid == null) return (unknownPlayer(this, command.getArgs()[0]));

		final long time = System.currentTimeMillis();

		String url = "https://crafatar.com/renders/body/" + uuid + "?overlay&size=512&time=" + time;

		MessageSender.message(command, url);
		MessageSender.message(command, "NameMC link: <https://fr.namemc.com/profile/"+uuid+">");
		return (true);
	}
}
