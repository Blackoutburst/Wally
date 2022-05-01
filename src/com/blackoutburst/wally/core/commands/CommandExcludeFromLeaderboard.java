package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.utils.Utils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public class CommandExcludeFromLeaderboard extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 1)  {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"excludefromleaderboard [Minecraft Name]")).queue();
            return;
        }

        final String playerUUID = Mojang.getPlayerUUID(args[0]);
        if (playerUUID == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not in mojang").replace("%name%", args[0])).queue();
            return;
        }

        Utils.excludeFromLb(playerUUID);
        channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "exclude success").replace("%name%", args[0])).queue();
    }
}
