package com.blackoutburst.wally.core.buttons;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.leaderboard.Leaderboard;
import com.blackoutburst.wally.utils.LeaderboardUtils;
import com.blackoutburst.wally.utils.MessageUtils;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nonnull;

public class ButtonPreviousPage extends ButtonExecutable {

    @Override
    protected void onClick(@Nonnull WallyUser buttonUser, @Nonnull ButtonInteractionEvent event) {
        final int index = Integer.parseInt(event.getButton().getId().split(":")[2]);
        final Leaderboard leaderboard = LeaderboardUtils.getLeaderboardFromIndex(index);

        int page = Integer.parseInt(event.getButton().getId().split(":")[3]);
        if (page > 1) page--;

        event.deferEdit().queue();
        MessageUtils.sendLeaderboard(buttonUser, event.getMessage(), leaderboard,  page,true);
    }
}
