package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.database.DatabaseManager;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import javax.annotation.Nonnull;
import java.io.File;

public class CommandSkin extends CommandExecutable{

    private void createMenu(@Nonnull WallyUser commandSender, @Nonnull String uuid, @Nonnull Message message) {
        final boolean linked = DatabaseManager.getInstance().isLinkedUUID(uuid);
        final EmbedBuilder embed = Utils.generateSkin(uuid);

        if (!linked) {
            message.replyEmbeds(embed.build()).queue();
            return;
        }

        final WallyUser wallyUser = DatabaseManager.getInstance().getWallyUserFromUUID(uuid);

        SelectMenu.Builder menu = SelectMenu.create(commandSender.getMember().getId()+":skin")
                .setRequiredRange(1, 1)
                .setPlaceholder("Select an account");

        final File folder = new File("database/linked users/" + wallyUser.getMember().getId() + "/accounts/");
        final String[] entries = folder.list();

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
        } else if (DatabaseManager.getInstance().isLinked(discordID)) {
                final UserOption data = DatabaseManager.getInstance().getWallyUserData(discordID);
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
