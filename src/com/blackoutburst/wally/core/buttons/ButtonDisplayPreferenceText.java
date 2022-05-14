package com.blackoutburst.wally.core.buttons;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.commands.DisplayPreference;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.utils.DatabaseUtils;
import com.blackoutburst.wally.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class ButtonDisplayPreferenceText extends ButtonExecutable {

    @Override
    protected void onClick(@Nonnull ButtonInteractionEvent event) {
        final String discordID = event.getMember().getId();
        final DisplayPreference displayPreference = DatabaseUtils.getWallyUserData(discordID).getDisplayPreference();

        DatabaseUtils.updateUserDisplayPreference(event.getTextChannel(), discordID, displayPreference);
        final String id = discordID+":"+event.getMessage().getId();

        event.reply(ConfigManager.getInstance().loadValue("replies", "user display preference updated")).setEphemeral(true).queue();

        event.getMessage().editMessage(ConfigManager.getInstance().loadValue("replies", "select display preference")).setActionRow(
                displayPreference.equals(DisplayPreference.CANVAS) ? net.dv8tion.jda.api.interactions.components.buttons.Button.primary(id, "CANVAS") : net.dv8tion.jda.api.interactions.components.buttons.Button.secondary(id, "CANVAS"),
                displayPreference.equals(DisplayPreference.EMBED) ? net.dv8tion.jda.api.interactions.components.buttons.Button.primary(id, "EMBED") : net.dv8tion.jda.api.interactions.components.buttons.Button.secondary(id, "EMBED"),
                displayPreference.equals(DisplayPreference.TEXT) ? net.dv8tion.jda.api.interactions.components.buttons.Button.primary(id, "TEXT") : Button.secondary(id, "TEXT")
        ).queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));
        Wally.getInstance().loadUser();
    }
}
