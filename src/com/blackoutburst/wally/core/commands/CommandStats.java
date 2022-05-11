package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.utils.DatabaseUtils;
import com.blackoutburst.wally.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import javax.annotation.Nonnull;
import java.io.File;

public class CommandStats extends CommandExecutable{

    /**
     * Send the skin embed message without menu if the player isn't linked
     * If the player is linked and possess multiples account a drop menu
     * is added below to allow the user to switch between his skin
     * @param commandSender the command sender
     * @param uuid the player uuid
     * @param message the message
     */
    private void createMenu(@Nonnull WallyUser commandSender, @Nonnull String uuid, @Nonnull Message message) {
        final boolean linked = DatabaseUtils.isLinkedUUID(uuid);
        final String playerName = Mojang.getPlayerName(uuid);
        final EmbedBuilder embed = EmbedUtils.generateSkin(uuid, playerName);

        if (!linked) {
            message.replyEmbeds(embed.build()).queue();
            return;
        }

        final WallyUser wallyUser = DatabaseUtils.getWallyUserFromUUID(uuid);
        final File folder = new File("database/linked users/" + wallyUser.getMember().getId() + "/accounts/");
        final String[] entries = folder.list();
        if (entries.length == 1) {
            message.replyEmbeds(embed.build()).queue();
            return;
        }

        final SelectMenu.Builder menu = SelectMenu.create(commandSender.getMember().getId()+":skin")
                .setRequiredRange(1, 1)
                .setPlaceholder("Select an account");

        for (String s : entries) {
            String player = Mojang.getPlayerName(s);
            if (player == null) continue;
            menu.addOption(player, s);
        }

        message.replyEmbeds(embed.build()).setActionRow(menu.build()).queue();
    }

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
                    .replace("%usage%", CommandManager.getPrefix()+"skin [Minecraft name]")).queue();
            return;
        }

        if (uuid == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "unknown player")).queue();
            return;
        }

        createMenu(commandSender, uuid, message);
    }
}
