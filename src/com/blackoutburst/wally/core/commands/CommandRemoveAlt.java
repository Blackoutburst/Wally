package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.database.DatabaseManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.utils.Utils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.File;

public class CommandRemoveAlt extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 2) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"removealt [discordID]")).queue();
            return;
        }

        final String discordID = Utils.getDiscordIDFromTwoArgs(guild, args);
        if (discordID == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not an user")).queue();
            return;
        }

        if (!DatabaseManager.getInstance().isLinked(discordID)) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not linked").replace("%user%", "<@"+discordID+">")).queue();
            return;
        }

        final String playerName = Utils.getNonDiscordIDArgFromTwoArgs(guild, args);
        if (playerName == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "could not get name")).queue();
            return;
        }

        final String playerUUID = Mojang.getPlayerUUID(playerName);
        final File folder = new File("database/linked users/" + discordID + "accounts" + playerUUID);
        if (!folder.exists()) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not an alt account")
                    .replace("%name%", playerName)
                    .replace("%user%", "<@"+discordID+">")).queue();
            return;
        }

        if (!DatabaseManager.getInstance().cleanFolder(folder)) {
            Utils.sendLogButtonReply(commandSender, message, ConfigManager.getInstance().loadValue("replies", "removed alt error"));
            return;
        }

        channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "remove alt success")
                .replace("%name%", playerName)
                .replace("%user%", "<@"+discordID+">")).queue();
        Wally.getInstance().logger.log(LogLevel.INFO, playerName + " got unlinked");
    }
}
