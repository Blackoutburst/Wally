package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.request.Hypixel;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.utils.DatabaseUtils;
import com.blackoutburst.wally.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import javax.annotation.Nonnull;

public class CommandStats extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        final String discordID = commandSender.getMember().getId();
        String uuid;

        if (args.length > 0) {
            uuid = Mojang.getPlayerUUID(args[0]);
        } else if (DatabaseUtils.isLinked(discordID)) {
                final UserOption data = DatabaseUtils.getWallyUserData(discordID);
                uuid = data.getMainAccountUUID();
        } else {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"stats [Minecraft name]")).queue();
            return;
        }
        if (uuid == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "unknown player")).queue();
            return;
        }

        String data = Hypixel.getPlayerStats(uuid);
        if (data == null && DatabaseUtils.isLinkedUUID(uuid)) {
            data = DatabaseUtils.getBackupLocalData(uuid, DatabaseUtils.DataType.HYPIXEL, channel);
        }
        if (data == null) {
            MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.getInstance().loadValue("replies", "command stats error"));
            return;
        }

        DatabaseUtils.updateStats(data, DatabaseUtils.DataType.HYPIXEL, discordID, uuid);

        final SelectMenu menu = MessageUtils.createAccountMenu(commandSender, uuid, "stats");

        switch (commandSender.getDisplayPreference()) {
            case TEXT: message.reply(MessageUtils.generateHypixelStatsMessage(data)).setActionRow(menu).queue(); break;
            case EMBED: message.replyEmbeds(MessageUtils.generateHypixelStatsEmbed(data)).setActionRow(menu).queue(); break;
            default: message.reply("").setActionRow(menu).queue(); break;
        }
    }
}
