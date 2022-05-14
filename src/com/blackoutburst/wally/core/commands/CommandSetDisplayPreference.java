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
     * Change someone display preference
     *
     * @param channel the message channel
     * @param discordID the user discord id
     * @param preference the new display preference
     * @return if the operation worked
     */
    private boolean updateUserDisplayPreference(@Nonnull MessageChannel channel, @Nonnull String discordID, @Nonnull DisplayPreference preference) {
        final String file = "database/linked users/" + discordID + "/data.json";
        final UserOption data = DatabaseUtils.getWallyUserData(discordID);
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
        final String discordID = commandSender.getMember().getId();
        if (!DatabaseUtils.isLinked(discordID)) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "sorry not linked")).queue();
            return;
        }

        final UserOption option = DatabaseUtils.getWallyUserData(discordID);
        final String id = commandSender.getMember().getId()+":"+message.getId();

        message.reply(ConfigManager.getInstance().loadValue("replies", "select display preference")).setActionRow(
                option.getDisplayPreference().equals(DisplayPreference.CANVAS) ? Button.primary(id, "CANVAS") : Button.secondary(id, "CANVAS"),
                option.getDisplayPreference().equals(DisplayPreference.EMBED) ? Button.primary(id, "EMBED") : Button.secondary(id, "EMBED"),
                option.getDisplayPreference().equals(DisplayPreference.TEXT) ? Button.primary(id, "TEXT") : Button.secondary(id, "TEXT")
        ).queue();

        /*
        if (updateUserDisplayPreference(channel, discordID, preference)) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "user display preference updated")).queue();
            Wally.getInstance().loadUser();
        }*/
    }
}
