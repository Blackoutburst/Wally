package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.database.DatabaseManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.request.Hypixel;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.core.request.PracticeServer;
import com.blackoutburst.wally.utils.CommandUtils;
import com.blackoutburst.wally.utils.DatabaseUtils;
import com.blackoutburst.wally.utils.FilesUtils;
import com.blackoutburst.wally.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public class CommandAddAlt extends CommandExecutable{

    /**
     * Get all data and add the alt account
     * @param commandSender the command sender
     * @param args the command args
     * @param guild the server in which the command was used
     * @param channel the message channel
     * @param message the message
     */
    private void addAlt(@Nonnull WallyUser commandSender, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        final String discordID = CommandUtils.getDiscordIDFromTwoArgs(guild, args);
        if (discordID == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not an user")).queue();
            return;
        }

        if (!DatabaseUtils.isLinked(discordID)) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not linked").replace("%user%", "<@"+discordID+">")).queue();
            return;
        }

        final String playerName = CommandUtils.getNonDiscordIDArgFromTwoArgs(guild, args);
        if (playerName == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "could not get name")).queue();
            return;
        }

        final String playerUUID = Mojang.getPlayerUUID(playerName);
        if (playerUUID == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not in mojang").replace("%name%", playerName)).queue();
            return;
        }

        final String hypixelData = Hypixel.getPlayerStats(playerUUID);
        if (hypixelData == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not in hypixel").replace("%name%", playerName)).queue();
            return;
        }

        String practiceData = PracticeServer.getPlayerStats(playerUUID);
        if (practiceData == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "could not load practice data").replace("%name%", playerName)).queue();
            practiceData = "";
        }

        if (!DatabaseManager.getInstance().createAltData(discordID, playerUUID, hypixelData, practiceData)) {
            MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.getInstance().loadValue("replies", "add alt error"));
            return;
        }

        FilesUtils.excludeFromLb(playerUUID);
        channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "add alt success").replace("%name%", playerName)
                .replace("%user%", "<@"+discordID+">")).queue();
        Wally.getInstance().logger.log(LogLevel.INFO, playerName + " was added as alt to " + discordID);
    }

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 2)  {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"addalt [discordID] [Minecraft Name]")).queue();
            return;
        }

        addAlt(commandSender, args, guild, channel, message);
    }
}
