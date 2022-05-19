package com.blackoutburst.wally.core.buttons;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.PermissionLevel;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.utils.DatabaseUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ButtonPermissionLevel extends ButtonExecutable {

    /**
     * Change someone permission
     *
     * @param event the button event
     * @param discordID the user discord id
     * @param level the new permission level
     * @return if the operation worked
     */
    private boolean updateUserPermissionLevel(@Nonnull ButtonInteractionEvent event, @Nonnull String discordID, @Nonnull PermissionLevel level) {
        final String file = "database/linked users/" + discordID + "/data.json";
        final UserOption data = DatabaseUtils.getWallyUserData(discordID);
        if (data == null) {
            event.reply(ConfigManager.getInstance().loadValue("replies", "not linked or corrupted")).setEphemeral(true).queue();
            return false;
        }
        data.setPermissionLevel(level);
        DatabaseUtils.generateWallyProfile(data, file);
        Wally.getInstance().loadUser();
        return true;
    }

    @Override
    protected void onClick(@Nonnull WallyUser buttonUser, @Nonnull ButtonInteractionEvent event) {
        final String discordID = event.getMember().getId();
        final String editedUserID = event.getButton().getId().split(":")[1];
        final String id = buttonUser.getMember().getId()+":"+editedUserID+":";
        final PermissionLevel level = PermissionLevel.valueOf(event.getButton().getLabel());

        final UserOption data = DatabaseUtils.getWallyUserData(editedUserID);
        if (data == null) {
            event.reply(ConfigManager.getInstance().loadValue("replies", "not linked or corrupted")).setEphemeral(true).queue();
            return;
        }

        event.getMessage().editMessage(ConfigManager.getInstance().loadValue("replies", "select display preference")).setActionRow(
                level.equals(PermissionLevel.MEMBER) ? Button.primary(id+ UUID.randomUUID(), "MEMBER") : Button.secondary(id+UUID.randomUUID(), "MEMBER"),
                level.equals(PermissionLevel.STAFF) ? Button.primary(id+UUID.randomUUID(), "STAFF") : Button.secondary(id+UUID.randomUUID(), "STAFF"),
                level.equals(PermissionLevel.ADMIN) ? Button.primary(id+UUID.randomUUID(), "ADMIN") : Button.secondary(id+UUID.randomUUID(), "ADMIN")
        ).queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));

        if (updateUserPermissionLevel(event, discordID, level)) {
            event.reply(ConfigManager.getInstance().loadValue("replies", "user permission updated")).setEphemeral(true).queue();
            Wally.getInstance().loadUser();
        }
    }
}
