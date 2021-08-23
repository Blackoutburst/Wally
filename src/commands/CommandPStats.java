package commands;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import core.AltExcluder;
import core.Command;
import core.CommandExecutable;
import core.Request;
import main.Main;
import utils.Canvas;
import utils.GeneralUtils;
import utils.MessageSender;
import utils.StatsCanvas;

public class CommandPStats extends CommandExecutable {

	public CommandPStats(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		
		String uuid = null;
		String data = null;
		String playerName = null;
		
		if (command.getArgs().length == 0) {
			if (GeneralUtils.isLinkedDiscord(command.getSender().getId())) {
				uuid = GeneralUtils.getUUIDfromDiscord(command.getSender().getId());
				playerName = GeneralUtils.getIGNfromDiscord(command.getSender().getId());
			} else {
				return (badUsage(this));
			}
		} else {
			uuid = Request.getPlayerUUID(command.getArgs()[0]);
			playerName = command.getArgs()[0];
		}
		
		Socket socket;
		try {
			socket = new Socket(Main.PRACTICE_API_IP, Main.PRACTICE_API_PORT);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			OutputStream out = socket.getOutputStream();
			uuid += '\n';
			out.write(uuid.getBytes("UTF-8"));
			data = in.readLine();
			socket.close();
			in.close();
			out.close();
			
		} catch (Exception e) {}
		
		if (data.contains("Unknown player")) {
			return unknownPlayer(this, playerName);
		}
		
		Canvas image = new Canvas(1000, 400);
		
		StatsCanvas.createPStatsImage(image, uuid.replace("\n", ""), playerName, data.replace("\n", ""));
		MessageSender.sendFile(command, "pstats.png");
		AltExcluder.isAnAlt(uuid.replace("\n", ""), this.command);
		return (true);
	}
}
