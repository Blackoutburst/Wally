package commands;


import core.Command;
import core.CommandExecutable;
import core.Request;
import net.dv8tion.jda.api.EmbedBuilder;
import utils.*;
import utils.Canvas;

import java.awt.*;
import java.io.File;
import java.util.Objects;

public class CommandPGraph extends CommandExecutable {

	public CommandPGraph(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		
		String uuid;
		String data;
		String playerName;
		int games;
		
		if (command.getArgs().length == 0 || (command.getArgs().length == 1 && command.getArgs()[0].length() <= 2) || (command.getArgs().length == 2 && command.getArgs()[0].length() <= 2)) {
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
		
		data = Request.getPracticeServerScore(uuid);
		if (data != null && data.contains("Unknown player")) {
			return unknownPlayer(this, playerName);
		}

		playerName = PracticeStats.getName(data);

		games = getGames();
		
		if (games <= 1) {
			games = 2;
			MessageSender.message(command, "At least two games are required for this to work");
		}
		
		if (games > 100) {
			games = 100;
			MessageSender.message(command, "Only the last 100 games are saved impossible to display more");
		}
		
		Canvas image = new Canvas(1000, 600);
		
		GraphCanvas.createPGraphImage(image, data, getType(), games);

		int average = 0;

		for (int i = 0; i < games; i++) {
			switch (getType()) {
				case "Qualification":
					average += Objects.requireNonNull(PracticeAPI.getQualificationHistory(data))[i];
				break;
				case "Finals":
					average += Objects.requireNonNull(PracticeAPI.getFinalsHistory(data))[i];
				break;
				case "Wide Qualification":
					average += Objects.requireNonNull(PracticeAPI.getWideQualificationHistory(data))[i];
				break;
				case "Lobby":
					average += Objects.requireNonNull(PracticeAPI.getLobbyHistory(data))[i];
				break;
				case "Wide Finals":
					average += Objects.requireNonNull(PracticeAPI.getWideFinalsHistory(data))[i];
				break;
			}
		}
		average = average / games;

		File file = new File("pgraph.png");

		EmbedBuilder embed = new EmbedBuilder();
		embed.setAuthor(playerName + " | "+ getType()+" - latest " + games + " games", "https://namemc.com/profile/"+uuid, "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128");
		embed.setTitle("Average Score: " + average);
		embed.setThumbnail("https://visage.surgeplay.com/face/128/" + uuid);
		embed.setColor(Canvas.getLineColor(average));
		embed.setImage("attachment://graph.png");

		command.getChannel().sendMessage(embed.build()).addFile(file, "graph.png").queue();

		return (true);
	}
	
	private int getGames() {
		for (int i = 1; i < command.getArgs().length; i++) {
			try {
				return Integer.parseInt(command.getArgs()[i]);
			} catch(Exception ignored) {}
		}
		return (100);
	}
	
	private String getType() {
		for (String arg : command.getArgs()) {
			if (arg.length() == 1 || arg.length() == 2) {
				switch(arg.toLowerCase()) {
					case "q": return ("Qualification");
					case "f": return ("Finals");
					case "wq": return ("Wide Qualification");
					case "l": return ("Lobby");
					case "wf": return ("Wide Finals");
				}
			}
		}
		return ("Qualification");
	}
}
