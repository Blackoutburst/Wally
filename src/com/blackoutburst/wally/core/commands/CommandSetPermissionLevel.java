package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.utils.CommandUtils;
import com.blackoutburst.wally.utils.DatabaseUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CommandSetPermissionLevel extends CommandExecutable {

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length < 1)  {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"setpermissionlevel [discordID]")).queue();
            return;
        }

        final String discordID = CommandUtils.getDiscordIDFromTwoArgs(guild, args);
        if (discordID == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not an user")).queue();
            return;
        }

        final Member member = guild.getMemberById(discordID);

        final UserOption data = DatabaseUtils.getWallyUserData(discordID);
        final String id = commandSender.getMember().getId()+":"+discordID+":";

        message.reply(ConfigManager.getInstance().loadValue("replies", "select permission level").replace("%user%", member.getEffectiveName())).setActionRow(
                data.getPermissionLevel().equals(PermissionLevel.MEMBER) ? Button.primary(id+ UUID.randomUUID(), "MEMBER") : Button.secondary(id+UUID.randomUUID(), "MEMBER"),
                data.getPermissionLevel().equals(PermissionLevel.STAFF) ? Button.primary(id+UUID.randomUUID(), "STAFF") : Button.secondary(id+UUID.randomUUID(), "STAFF"),
                data.getPermissionLevel().equals(PermissionLevel.ADMIN) ? Button.primary(id+UUID.randomUUID(), "ADMIN") : Button.secondary(id+UUID.randomUUID(), "ADMIN")
        ).queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));
    }
}
