package com.blackoutburst.wally.core.buttons;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ButtonLogLevel extends ButtonExecutable {

    @Override
    protected void onClick(@Nonnull WallyUser buttonUser, @Nonnull ButtonInteractionEvent event) {
        final String discordID = event.getMember().getId();
        final String id = discordID+":"+event.getMessage().getId();
        final LogLevel level = LogLevel.valueOf(event.getButton().getLabel());

        Wally.getInstance().logger.setInternalLogLevel(level);

        event.reply(ConfigManager.getInstance().loadValue("replies", "log level updated")
                .replace("%level%", level.toString())).setEphemeral(true).queue();

        event.getMessage().editMessage(ConfigManager.getInstance().loadValue("replies", "select display preference")).setActionRow(
                Wally.getInstance().logger.getInternalLogLevel().equals(LogLevel.INFO) ? Button.primary(id+UUID.randomUUID(), "INFO") : Button.secondary(id+UUID.randomUUID(), "INFO"),
                Wally.getInstance().logger.getInternalLogLevel().equals(LogLevel.WARN) ? Button.primary(id+UUID.randomUUID(), "WARN") : Button.secondary(id+UUID.randomUUID(), "WARN"),
                Wally.getInstance().logger.getInternalLogLevel().equals(LogLevel.ERROR) ? Button.primary(id+UUID.randomUUID(), "ERROR") : Button.secondary(id+UUID.randomUUID(), "ERROR"),
                Wally.getInstance().logger.getInternalLogLevel().equals(LogLevel.NONE) ? Button.primary(id+UUID.randomUUID(), "NONE") : Button.secondary(id+UUID.randomUUID(), "NONE")
        ).queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));


    }
}
