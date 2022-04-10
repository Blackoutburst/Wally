package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.database.DatabaseManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.request.Hypixel;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.core.request.PracticeServer;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public class CommandLink extends CommandExecutable{

    /**
     * Return the discord ID from the command args
     *
     * @param guild the guild used to run the command
     * @param args the command args
     * @return the discord id
     */
    private String getDiscordID(@Nonnull Guild guild, @Nonnull String[] args) {
        if (guild.getMemberById(args[0]) != null)
            return (args[0]);
        if (guild.getMemberById(args[1]) != null)
            return (args[1]);
        return (null);
    }

    /**
     * Get the player name from the command args
     *
     * @param guild the guild used to run the command
     * @param args the command args
     * @return the player name
     */
    private String getPlayerName(@Nonnull Guild guild, @Nonnull String[] args) {
        if (guild.getMemberById(args[0]) != null)
            return (args[1]);
        if (guild.getMemberById(args[1]) != null)
            return (args[0]);
        return (null);
    }

    @Override
    protected boolean onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 2) return false;

        final String discordID = getDiscordID(guild, args);
        if (discordID == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not an user")).complete();
            return false;
        }

        final String playerName = getPlayerName(guild, args);
        if (playerName == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "could not get name")).complete();
            return false;
        }

        final String playerUUID = Mojang.getPlayerUUID(playerName);
        if (playerUUID == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not in mojang").replace("%name%", playerName)).complete();
            return false;
        }

        final String hypixelData = Hypixel.getPlayerStats(playerUUID);
        if (hypixelData == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not in hypixel").replace("%name%", playerName)).complete();
            return false;
        }

        String practiceData = PracticeServer.getPlayerStats(playerUUID);
        if (practiceData == null) practiceData = "";

        if (!DatabaseManager.getInstance().createUserData(discordID, playerUUID, hypixelData, practiceData)) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "link error")).complete();
            return false;
        }

        channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "link success").replace("%name%", playerName)).complete();
        Wally.getInstance().logger.log(LogLevel.INFO, playerName + " was linked to " + discordID);

        return true;
    }
}
