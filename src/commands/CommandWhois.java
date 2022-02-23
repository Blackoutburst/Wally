package commands;

import java.awt.Color;

import core.AltExcluder;
import core.Bot;
import core.Command;
import core.CommandExecutable;
import core.Request;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import utils.API;
import utils.GeneralUtils;
import utils.MessageSender;

public class CommandWhois extends CommandExecutable {

	public CommandWhois(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage(this));
		
		String data = Request.getPlayerStats(command.getArgs()[0]);
		if (data == null) return (unknownPlayer(this, command.getArgs()[0]));
		if(!GeneralUtils.isValidJSON(data)) return (apiDead(this));
		if (API.getPlayer(data) == null) return (neverJoined(this, command.getArgs()[0]));
		String discordId = GeneralUtils.getDiscordfromIGN(API.getName(data));
		String discordNick = "N/A";
		if (discordId != null && GeneralUtils.isInsideTheServer(discordId)) {
			Member member = Bot.server.getMemberById(discordId);
			if (member != null) {
				discordNick = member.getEffectiveName() + "#" + member.getUser().getDiscriminator();
			}
		}
		discordNick = sanitizeName(discordNick);
		String uuid = API.getUUID(data);
		String IGN = API.getName(data);
		double lvl = 1.0 + -8750.0 / 2500.0 + Math.sqrt(-8750.0 / 2500.0 * -8750.0 / 2500.0 + 2.0 / 2500.0 * (double)API.getLevelToInt(data));
		int ap = API.getAPToInt(data);
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setAuthor("Information about : " + IGN, "https://namemc.com/profile/"+uuid, "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128");
		embed.setTitle(IGN);
		embed.setThumbnail("https://visage.surgeplay.com/face/128/" + uuid);
		embed.setColor(new Color(71, 165, 255));
		embed.addField("Discord", discordNick, false);
		embed.addField("UUID", uuid, false);
		embed.addField("Level", String.format("%.2f", lvl), true);
		embed.addField("Achievements", "" + ap, true);
		embed.addBlankField(true);
		embed.addField("Plancke", "[Link](https://plancke.io/hypixel/player/stats/" + uuid + ")", true);
		embed.addField("NameMC", "[Link](https://namemc.com/profile/" + uuid + ")", true);
		MessageSender.sendEmbeded(command, embed);
		
		AltExcluder.isAnAlt(uuid, this.command);
		
		return (true);
	}

	/**
	 * Get discord name
	 */
	private String sanitizeName(String name) {
		return name.replaceAll("@", "@ ");
	}
}
