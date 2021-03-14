package commands;

import java.io.File;
import java.io.IOException;

import core.Command;
import core.CommandExecutable;
import net.dv8tion.jda.api.entities.Message.Attachment;
import utils.Config;
import utils.MessageSender;

public class CommandSetConfig extends CommandExecutable {

	public CommandSetConfig(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getEvent().getMessage().getAttachments().size() == 0) return (missingFile(this));
		
		Attachment file = command.getEvent().getMessage().getAttachments().get(0);
		
		if (file.getFileExtension().equals("json")) {
			file.downloadToFile(new File("config.json"));
			try {
				new Config("config.json");
				MessageSender.messageJSON(command, "config update");
			} catch (IOException e) {
				MessageSender.messageJSONMention(command, "config error");
				e.printStackTrace();
			}
		} else {
			return (wrongFileFormat(this, ".json"));
		}
		return (true);
	}
}
