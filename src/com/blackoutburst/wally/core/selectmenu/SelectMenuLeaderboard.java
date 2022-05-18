package com.blackoutburst.wally.core.selectmenu;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.leaderboard.Leaderboard;
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager;
import com.blackoutburst.wally.utils.EmbedUtils;
import com.blackoutburst.wally.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;

public class SelectMenuLeaderboard extends SelectMenuExecutable {

    @Override
    protected void onChange(@Nonnull WallyUser buttonUser, @NonNull SelectMenuInteractionEvent event) {
        final int type = Integer.parseInt(event.getSelectedOptions().get(0).getValue().split(":")[0]);
        final int page = Integer.parseInt(event.getSelectedOptions().get(0).getValue().split(":")[1]);

        Leaderboard leaderboard;

        switch (type) {
            case 1: leaderboard = LeaderboardManager.getInstance().wallLeaderboardHypixel; break;
            case 2: leaderboard = LeaderboardManager.getInstance().qualificationLeaderboardHypixel; break;
            case 3: leaderboard = LeaderboardManager.getInstance().finalsLeaderboardHypixel; break;
            case 4: leaderboard = LeaderboardManager.getInstance().totalLeaderboardHypixel; break;
            case 5: leaderboard = LeaderboardManager.getInstance().totalApiLeaderboardHypixel; break;
            case 6: leaderboard = LeaderboardManager.getInstance().challengesLeaderboardHypixel; break;
            case 7: leaderboard = LeaderboardManager.getInstance().overallLeaderboardHypixel; break;
            case 8: leaderboard = LeaderboardManager.getInstance().qualificationLeaderboardPractice; break;
            case 9: leaderboard = LeaderboardManager.getInstance().finalsLeaderboardPractice; break;
            case 10: leaderboard = LeaderboardManager.getInstance().lobbyLeaderboardPractice; break;
            case 11: leaderboard = LeaderboardManager.getInstance().wideQualificationLeaderboardPractice; break;
            case 12: leaderboard = LeaderboardManager.getInstance().wideFinalsLeaderboardPractice; break;
            case 13: leaderboard = LeaderboardManager.getInstance().totalLeaderboardPractice; break;
            case 14: leaderboard = LeaderboardManager.getInstance().overallLeaderboardPractice; break;
            default: leaderboard = LeaderboardManager.getInstance().winsLeaderboardHypixel; break;
        }

        event.deferEdit().queue();
        MessageUtils.sendLeaderboard(buttonUser, event.getMessage(), leaderboard,  page,true);
    }
}
