package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.utils.CommandUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public class CommandLink extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 2)  {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"link [discordID] [Minecraft Name]")).queue();
            return;
        }

        final String discordID = CommandUtils.getDiscordIDFromTwoArgs(guild, args);
        if (discordID == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not an user")).queue();
            return;
        }

        CommandUtils.createUserData(false, guild, args, message, discordID, channel, commandSender);
    }
}
