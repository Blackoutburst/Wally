package com.blackoutburst.wally.core.selectmenu;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;

public class SelectMenuSkin extends SelectMenuExecutable {

    @Override
    protected void onChange(@Nonnull WallyUser menuUser, @NonNull SelectMenuInteractionEvent event) {
        final String uuid = event.getSelectedOptions().get(0).getValue();
        final String playerName = event.getSelectedOptions().get(0).getLabel();
        final EmbedBuilder embed = EmbedUtils.generateSkin(uuid, playerName);

        event.editMessageEmbeds(embed.build()).queue();
    }
}
