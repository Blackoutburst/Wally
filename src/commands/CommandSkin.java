package commands;

import core.Command;
import core.CommandError;
import core.CommandExecutable;
import core.Request;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import utils.GeneralUtils;
import utils.MessageSender;

import java.awt.*;

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

		final MessageEmbed embed = new EmbedBuilder()
				.setAuthor(command.getArgs()[0], "https://fr.namemc.com/profile/"+uuid, "https://visage.surgeplay.com/face/512/" + uuid + "?&time=" + time)
				.setTitle("NameMC Link", "https://fr.namemc.com/profile/"+uuid)
				.setColor(new Color(71, 165, 255))
				.setImage("https://visage.surgeplay.com/full/512/" + uuid + "?&time=" + time).build();

		command.getChannel().sendMessage(embed).complete();
		return (true);
	}
}
