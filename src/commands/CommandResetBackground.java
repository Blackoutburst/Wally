package commands;

import java.io.File;

import org.json.JSONObject;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;
import utils.Utils;

public class CommandResetBackground extends CommandExecutable {

	public CommandResetBackground(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		File index = new File("linked player");
		String[]entries = index.list();
		
		for (String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			JSONObject obj = Utils.readJson(playerFolder + "/data.json");
			
			if (obj.getString("discordid").equals(command.getSender().getId())) {
				if (new File(playerFolder + "/background.png").exists()) {
					new File(playerFolder + "/background.png").delete();
					MessageSender.messageJSON(command, "background update");
					return (true);
				}
			}
		}
		MessageSender.messageJSONMention(command, "not linked");
		return (true);
	}
}
