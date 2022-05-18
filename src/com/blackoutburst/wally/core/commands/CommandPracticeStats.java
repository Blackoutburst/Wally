package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.core.request.PracticeServer;
import com.blackoutburst.wally.utils.DatabaseUtils;
import com.blackoutburst.wally.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public class CommandPracticeStats extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        final String discordID = commandSender.getMember().getId();
        String uuid;
        String name;

        if (args.length > 0) {
            uuid = Mojang.getPlayerUUID(args[0]);

        } else if (DatabaseUtils.isLinked(discordID)) {
                final UserOption data = DatabaseUtils.getWallyUserData(discordID);
                uuid = data.getMainAccountUUID();
        } else {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"practicestats [Minecraft name]")).queue();
            return;
        }
        if (uuid == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "unknown player")).queue();
            return;
        }
        name = Mojang.getPlayerName(uuid);
        if (name == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "unknown player")).queue();
            return;
        }

        String data = PracticeServer.getPlayerStats(uuid);
        if (data == null && DatabaseUtils.isLinkedUUID(uuid)) {
            data = DatabaseUtils.getBackupLocalData(uuid, DatabaseUtils.DataType.PRACTICE, channel);
        }

        if (data == null) {
            MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.getInstance().loadValue("replies", "command practice stats error"));
            return;
        }

        DatabaseUtils.updateLeaderboardStats(data, DatabaseUtils.DataType.PRACTICE, uuid);
        DatabaseUtils.updateLinkedPlayerStats(data, DatabaseUtils.DataType.PRACTICE, discordID, uuid);
        MessageUtils.sendPracticeStats(commandSender, message, data, uuid, false);
    }
}
