package com.blackoutburst.wally.core.buttons;

import com.blackoutburst.wally.core.WallyUser;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nonnull;

public abstract class ButtonExecutable {

    protected abstract void onClick(@Nonnull WallyUser buttonUser, @Nonnull ButtonInteractionEvent event);

}
