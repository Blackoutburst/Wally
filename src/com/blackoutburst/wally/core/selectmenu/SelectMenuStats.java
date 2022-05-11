package com.blackoutburst.wally.core.selectmenu;

import com.blackoutburst.wally.utils.CommandUtils;
import com.blackoutburst.wally.utils.DatabaseUtils;
import com.blackoutburst.wally.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class SelectMenuStats extends SelectMenuExecutable {

    @Override
    protected void onChange(@NotNull SelectMenuInteractionEvent event) {
        final String uuid = event.getSelectedOptions().get(0).getValue();
        final String stats = CommandUtils.generateHypixelStatsMessage(uuid);

        event.editMessage(stats).queue();
    }
}
