package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.database.DatabaseManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.google.gson.Gson;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileReader;
import java.util.UUID;

public class CommandSetPermissionLevel extends CommandExecutable{

    /**
     * Return the discord ID from the command args
     *
     * @param guild the guild used to run the command
     * @param args the command args
     * @return the discord id
     */
    private String getDiscordID(@Nonnull Guild guild, @Nonnull String[] args) {
        try {
            if (guild.getMemberById(args[0]) != null)
                return (args[0]);
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.WARN, e.getMessage());
        }
        try {
            if (guild.getMemberById(args[1]) != null)
                return (args[1]);
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.WARN, e.getMessage());
        }
        return (null);
    }

    /**
     * Get the player name from the command args
     *
     * @param guild the guild used to run the command
     * @param args the command args
     * @return the player name
     */
    private String getPermissionLevel(@Nonnull Guild guild, @Nonnull String[] args) {
        try {
            if (guild.getMemberById(args[0]) != null)
                return (args[1]);
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.WARN, e.getMessage());
        }
        try {
            if (guild.getMemberById(args[1]) != null)
                return (args[0]);
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.WARN, e.getMessage());
        }
        return (null);
    }

    /**
     * Change someone permission
     *
     * @param channel the message channel
     * @param discordID the user discord id
     * @param level the new permission level
     * @return if the operation worked
     */
    private boolean updateUserPermissionLevel(@Nonnull MessageChannel channel, @Nonnull String discordID, @Nonnull PermissionLevel level) {
        final Gson gson = new Gson();
        final String file = "database/linked users/" + discordID + "/data.json";

        UserOption data;
        try {
            final FileReader reader = new FileReader(file);
            data = gson.fromJson(reader, UserOption.class);
            reader.close();
        } catch (Exception e) { data = null; }
        if (data == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not linked or corrupted")).queue();
            return false;
        }

        data.setPermissionLevel(level);
        DatabaseManager.getInstance().generateWallyProfile(data, file);

        return true;
    }

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 2)  {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"setpermissionlevel [discordID] [MEMBER/STAFF/ADMIN]")).queue();
            return;
        }

        final String discordID = getDiscordID(guild, args);
        if (discordID == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not an user")).queue();
            return;
        }

        final String permissionLevel = getPermissionLevel(guild, args);
        if (permissionLevel == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "permission level is null")).queue();
            return;
        }

        PermissionLevel level;

        try {
            level = PermissionLevel.valueOf(permissionLevel.toUpperCase());
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.WARN, e.getMessage());
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not a permission level").replace("%level%", permissionLevel)).queue();
            return;
        }

        if (updateUserPermissionLevel(channel, discordID, level)) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "user permission updated"))
                    .setActionRow(Button.primary(commandSender.getMember().getId()+":"+message.getId(), "Yes reload the users"),
                            Button.danger(commandSender.getMember().getId()+":"+ UUID.randomUUID(), "No"))
                    .queue();
        }
    }
}
