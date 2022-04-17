package com.blackoutburst.wally.core.buttons;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nonnull;
import java.io.File;

public class ButtonReloadUsers extends ButtonExecutable{

    @Override
    protected void onClick(@Nonnull ButtonInteractionEvent event) {
        final File rootFolder = new File("database/linked users/");
        if (!rootFolder.exists()) {
            event.getChannel().sendMessage(ConfigManager.getInstance().loadValue("replies", "no user db")).queue();
            return;
        }

        final String[] entries = rootFolder.list();
        if (entries == null) {
            event.getChannel().sendMessage(ConfigManager.getInstance().loadValue("replies", "no user db")).queue();
            return;
        }

        Wally.getInstance().loadUser();
        event.getChannel().sendMessage(ConfigManager.getInstance().loadValue("replies", "user reloaded")
                .replace("%users%", String.valueOf(Wally.getInstance().users.size()))
                .replace("%total%", String.valueOf(entries.length))).queue();
        event.getMessage().delete().queue();
    }
}
