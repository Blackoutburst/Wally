package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager;
import com.blackoutburst.wally.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public class CommandLeaderboard extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        int page = 1;

        if (args.length > 0) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (Exception e) {
                Wally.getInstance().logger.logStackTrace(e.getStackTrace());
                message.reply(ConfigManager.getInstance().loadValue("replies", "invalid page").replace("%page%", args[0])).queue();
                return;
            }
        }
        if (page < 1) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid page").replace("%page%", args[0])).queue();
            return;
        }

        MessageUtils.sendLeaderboard(commandSender, message, LeaderboardManager.getInstance().winsLeaderboardHypixel, page, false);
    }
}
