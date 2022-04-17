package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.database.DatabaseManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.request.Hypixel;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.core.request.PracticeServer;
import com.blackoutburst.wally.utils.Utils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public class CommandLink extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 2)  {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"link [discordID] [Minecraft UUID]")).queue();
            return;
        }

        final String discordID = Utils.getDiscordIDFromTwoArgs(guild, args);
        if (discordID == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not an user")).queue();
            return;
        }

        final String playerName = Utils.getNonDiscordIDArgFromTwoArgs(guild, args);
        if (playerName == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "could not get name")).queue();
            return;
        }

        final String playerUUID = Mojang.getPlayerUUID(playerName);
        if (playerUUID == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not in mojang").replace("%name%", playerName)).queue();
            return;
        }

        final String hypixelData = Hypixel.getPlayerStats(playerUUID);
        if (hypixelData == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not in hypixel").replace("%name%", playerName)).queue();
            return;
        }

        String practiceData = PracticeServer.getPlayerStats(playerUUID);
        if (practiceData == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "could not load practice data").replace("%name%", playerName)).queue();
            practiceData = "";
        }

        final UserOption wallyProfile = new UserOption(PermissionLevel.MEMBER, playerUUID, guild.getId());

        if (!DatabaseManager.getInstance().createUserData(discordID, playerUUID, hypixelData, practiceData, wallyProfile)) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "link error")).queue();
            return;
        }

        channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "link success").replace("%name%", playerName)).queue();
        Wally.getInstance().logger.log(LogLevel.INFO, playerName + " was linked to " + discordID);
    }
}
