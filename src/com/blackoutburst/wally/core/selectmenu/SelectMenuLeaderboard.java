package com.blackoutburst.wally.core.selectmenu;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.leaderboard.Leaderboard;
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager;
import com.blackoutburst.wally.utils.EmbedUtils;
import com.blackoutburst.wally.utils.LeaderboardUtils;
import com.blackoutburst.wally.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;

public class SelectMenuLeaderboard extends SelectMenuExecutable {

    @Override
    protected void onChange(@Nonnull WallyUser menuUser, @NonNull SelectMenuInteractionEvent event) {
        final int index = Integer.parseInt(event.getSelectedOptions().get(0).getValue().split(":")[0]);
        final int page = Integer.parseInt(event.getSelectedOptions().get(0).getValue().split(":")[1]);

        final Leaderboard leaderboard = LeaderboardUtils.getLeaderboardFromIndex(index);


        event.deferEdit().queue();
        MessageUtils.sendLeaderboard(menuUser, event.getMessage(), leaderboard,  page,true);
    }
}
