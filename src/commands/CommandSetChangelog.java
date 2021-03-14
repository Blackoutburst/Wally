package commands;

import java.io.File;

import core.Command;
import core.CommandExecutable;
import net.dv8tion.jda.api.entities.Message.Attachment;
import utils.MessageSender;

public class CommandSetChangelog extends CommandExecutable {

	public CommandSetChangelog(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getEvent().getMessage().getAttachments().size() == 0) return (missingFile(this));
		
		Attachment file = command.getEvent().getMessage().getAttachments().get(0);
		
		if (file.getFileExtension().equals("txt")) {
			file.downloadToFile(new File("changelog.txt"));
			MessageSender.messageJSON(command, "changelog update");
		} else {
			return (wrongFileFormat(this, ".txt"));
		}
		return (true);
	}
}
