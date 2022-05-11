package com.blackoutburst.wally.core.selectmenu;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.utils.CommandUtils;
import com.blackoutburst.wally.utils.DatabaseUtils;
import com.blackoutburst.wally.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SelectMenuStats extends SelectMenuExecutable {

    @Override
    protected void onChange(@NotNull SelectMenuInteractionEvent event) {
        try {
            final String uuid = event.getSelectedOptions().get(0).getValue();
            final WallyUser wallyUser = DatabaseUtils.getWallyUserFromUUID(uuid);
            final Path filePath = Paths.get("database/linked users/" + wallyUser.getMember().getId() + "/accounts/" + uuid + "/hypixel.json");
            final String data = new String(Files.readAllBytes(filePath));
            final String stats = CommandUtils.generateHypixelStatsMessage(data);

            event.editMessage(stats).queue();
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
    }
}
