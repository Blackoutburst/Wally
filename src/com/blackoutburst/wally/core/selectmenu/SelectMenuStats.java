package com.blackoutburst.wally.core.selectmenu;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.utils.CommandUtils;
import com.blackoutburst.wally.utils.DatabaseUtils;
import com.blackoutburst.wally.utils.MessageUtils;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SelectMenuStats extends SelectMenuExecutable {

    @Override
    protected void onChange(@Nonnull WallyUser buttonUser, @NonNull SelectMenuInteractionEvent event) {
        final String uuid = event.getSelectedOptions().get(0).getValue();
        final WallyUser wallyUser = DatabaseUtils.getWallyUserFromUUID(uuid);
        final Path filePath = Paths.get("database/linked users/" + wallyUser.getMember().getId() + "/accounts/" + uuid + "/hypixel.json");

        String data = "";
        try {
            data = new String(Files.readAllBytes(filePath));
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }

        event.deferEdit().queue();
        MessageUtils.sendHypixelStats(buttonUser, event.getMessage(), data, uuid, true);
    }
}
