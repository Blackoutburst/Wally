package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.utils.CommandUtils;
import com.blackoutburst.wally.utils.DatabaseUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public class CommandSetPermissionLevel extends CommandExecutable{

    /**
     * Change someone permission
     *
     * @param channel the message channel
     * @param discordID the user discord id
     * @param level the new permission level
     * @return if the operation worked
     */
    private boolean updateUserPermissionLevel(@Nonnull MessageChannel channel, @Nonnull String discordID, @Nonnull PermissionLevel level) {
        final String file = "database/linked users/" + discordID + "/data.json";
        final UserOption data = DatabaseUtils.getWallyUserData(discordID);
        if (data == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not linked or corrupted")).queue();
            return false;
        }
        data.setPermissionLevel(level);
        DatabaseUtils.generateWallyProfile(data, file);
        return true;
    }

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 2)  {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"setpermissionlevel [discordID] [MEMBER/STAFF/ADMIN]")).queue();
            return;
        }

        final String discordID = CommandUtils.getDiscordIDFromTwoArgs(guild, args);
        if (discordID == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not an user")).queue();
            return;
        }

        final String permissionLevel = CommandUtils.getNonDiscordIDArgFromTwoArgs(guild, args);
        if (permissionLevel == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "permission level is null")).queue();
            return;
        }

        PermissionLevel level;

        try {
            level = PermissionLevel.valueOf(permissionLevel.toUpperCase());
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
                message.reply(ConfigManager.getInstance().loadValue("replies", "not a permission level").replace("%level%", permissionLevel)).queue();
            return;
        }

        if (updateUserPermissionLevel(channel, discordID, level)) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "user permission updated")).queue();
            Wally.getInstance().loadUser();
        }
    }
}
