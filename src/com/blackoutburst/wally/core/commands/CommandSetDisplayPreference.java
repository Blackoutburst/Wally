package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.utils.CommandUtils;
import com.blackoutburst.wally.utils.DatabaseUtils;
import com.google.gson.Gson;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.io.FileReader;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CommandSetDisplayPreference extends CommandExecutable{

    /**
     * Change someone permission
     *
     * @param channel the message channel
     * @param discordID the user discord id
     * @param preference the new display preference
     * @return if the operation worked
     */
    private boolean updateUserDisplayPreference(@Nonnull MessageChannel channel, @Nonnull String discordID, @Nonnull DisplayPreference preference) {
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

        data.setDisplayPreference(preference);
        DatabaseUtils.generateWallyProfile(data, file);

        return true;
    }

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 2)  {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"setdisplaypreference [discordID] [TEXT/EMBED/CANVAS]")).queue();
            return;
        }

        final String discordID = CommandUtils.getDiscordIDFromTwoArgs(guild, args);
        if (discordID == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not an user")).queue();
            return;
        }

        final String displayPreference = CommandUtils.getNonDiscordIDArgFromTwoArgs(guild, args);
        if (displayPreference == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "display preference is null")).queue();
            return;
        }

        DisplayPreference preference;

        try {
            preference = DisplayPreference.valueOf(displayPreference.toUpperCase());
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
                message.reply(ConfigManager.getInstance().loadValue("replies", "not a display preference").replace("%preference%", displayPreference)).queue();
            return;
        }

        if (updateUserDisplayPreference(channel, discordID, preference)) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "user display preference updated")).queue();
            Wally.getInstance().loadUser();
        }
    }
}
