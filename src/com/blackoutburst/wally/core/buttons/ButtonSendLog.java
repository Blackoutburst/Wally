package com.blackoutburst.wally.core.buttons;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nonnull;
import java.io.File;

public class ButtonSendLog extends ButtonExecutable {

    @Override
    protected void onClick(@Nonnull WallyUser buttonUser, @Nonnull ButtonInteractionEvent event) {
        event.getChannel().sendFile(new File(Wally.getInstance().logger.currentLogFile)).queue();
        event.getMessage().delete().queue();
    }
}
