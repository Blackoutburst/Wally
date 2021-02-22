package commands;

import java.io.File;

import org.json.JSONObject;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;
import utils.Utils;

public class CommandRemoveBackground extends CommandExecutable {

	public CommandRemoveBackground(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage());
		
		File index = new File("linked player");
		String[]entries = index.list();
		
		for (String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			JSONObject obj = Utils.readJson(playerFolder + "/data.json");
			
			if (obj.getString("name").equalsIgnoreCase(command.getArgs()[0])) {
				if (new File(playerFolder + "/background.png").exists()) {
					new File(playerFolder + "/background.png").delete();
					MessageSender.messageJSON(command, "background remove");
					return (true);
				}
			}
		}
		MessageSender.messageJSONMention(command, "not linked");
		return (true);
	}
}
