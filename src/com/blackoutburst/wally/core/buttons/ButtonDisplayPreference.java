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
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ButtonDisplayPreference extends ButtonExecutable {

    @Override
    protected void onClick(@Nonnull ButtonInteractionEvent event) {
        final String discordID = event.getMember().getId();
        final DisplayPreference displayPreference = DisplayPreference.valueOf(event.getButton().getLabel());

        DatabaseUtils.updateUserDisplayPreference(event.getTextChannel(), discordID, displayPreference);
        final String id = discordID+":"+event.getMessage().getId();

        event.reply(ConfigManager.getInstance().loadValue("replies", "user display preference updated")
                .replace("%preference%", displayPreference.toString().toLowerCase())).setEphemeral(true).queue();

        event.getMessage().editMessage(ConfigManager.getInstance().loadValue("replies", "select display preference")).setActionRow(
                displayPreference.equals(DisplayPreference.CANVAS) ? net.dv8tion.jda.api.interactions.components.buttons.Button.primary(id+ UUID.randomUUID(), "CANVAS") : Button.secondary(id+UUID.randomUUID(), "CANVAS"),
                displayPreference.equals(DisplayPreference.EMBED) ? net.dv8tion.jda.api.interactions.components.buttons.Button.primary(id+UUID.randomUUID(), "EMBED") : Button.secondary(id+UUID.randomUUID(), "EMBED"),
                displayPreference.equals(DisplayPreference.TEXT) ? net.dv8tion.jda.api.interactions.components.buttons.Button.primary(id+UUID.randomUUID(), "TEXT") : Button.secondary(id+UUID.randomUUID(), "TEXT")
        ).queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));
        Wally.getInstance().loadUser();
    }
}
