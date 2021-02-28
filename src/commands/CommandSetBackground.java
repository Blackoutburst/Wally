package commands;

import java.io.File;

import org.json.JSONObject;

import core.Command;
import core.CommandExecutable;
import net.dv8tion.jda.api.entities.Message.Attachment;
import utils.MessageSender;
import utils.Utils;

public class CommandSetBackground extends CommandExecutable {

	public CommandSetBackground(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getEvent().getMessage().getAttachments().size() == 0) return (missingFile());
		
		Attachment file = command.getEvent().getMessage().getAttachments().get(0);
		
		if (file.getFileExtension().equals("png") || file.getFileExtension().equals("jpg") || file.getFileExtension().equals("jpeg")) {
			updateBackground(command.getSender().getId(), file);
		} else {
			return (wrongFileFormat(".png/.jpg/.jpeg"));
		}
		return (true);
	}
	
	/**
	 * Download background file
	 * @param discordid
	 * @param file
	 */
	private void updateBackground(String discordid, Attachment file) {
		File index = new File("linked player");
		String[]entries = index.list();
		
		for (String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			JSONObject obj = Utils.readJson(playerFolder + "/data.json");
			
			if (obj.getString("discordid").equals(discordid)) {
				file.downloadToFile(new File(playerFolder + "/background.png"));
				MessageSender.messageJSON(command, "background update");
				return;
			}
		}
		MessageSender.messageJSONMention(command, "not linked");
	}
 }
