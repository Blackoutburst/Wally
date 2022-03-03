package commands;

import core.Bot;
import core.Command;
import core.CommandExecutable;
import net.dv8tion.jda.api.EmbedBuilder;
import utils.ConfigManager;
import utils.MessageSender;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Objects;

public class CommandSuggest extends CommandExecutable {

	public CommandSuggest(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage(this));

		String fullString = String.join(" ", command.getArgs());
		String iconURL = command.getSender().getUser().getAvatarUrl();
		String wallyURL = "https://github.com/Blackoutburst/Wally";

		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor(command.getSender().getEffectiveName(), wallyURL, iconURL)
				.setTitle("New suggestion")
				.setColor(new Color(71, 165, 255))
				.setDescription(fullString);
		MessageSender.sendEmbeded(command, embed);

		Objects.requireNonNull(Bot.devServer.getTextChannelById(ConfigManager.getString("devServerSuggestionChannel"))).sendMessage(embed.build()).complete();
		return (true);
	}
}
