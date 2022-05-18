package com.blackoutburst.wally.core.selectmenu;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class SelectMenuManager {

    private static SelectMenuManager instance = null;

    private static final Set<SelectMenu> selectMenus = new HashSet<>();

    /**
     * Get the button manager instance
     *
     * @return the button manager instance
     */
    public static SelectMenuManager getInstance() {
        if (instance == null)
            instance = new SelectMenuManager();

        return instance;
    }

    /**
     * Register Wally button
     */
    private SelectMenuManager() {
        register(new SelectMenuSkin(), "skin");
        register(new SelectMenuStats(), "stats");
        register(new SelectMenuPracticeStats(), "practice stats");
        register(new SelectMenuLeaderboard(), "leaderboard");

        Wally.getInstance().logger.log(LogLevel.INFO, "Select menu registered!");
    }

    /**
     * Register new button
     *
     * @param executable the button executable
     * @param labels the button label
     */
    private void register(@Nonnull SelectMenuExecutable executable, @Nonnull String... labels) {
        selectMenus.add(new SelectMenu(executable, labels));
        Wally.getInstance().logger.log(LogLevel.INFO, "Menu ["+labels[0]+"] is registered");
    }

    /**
     * Run the action associated with the button pressed
     *
     * @param event the buttonInteraction event
     */
    public void runAction(@Nonnull WallyUser buttonUser, @Nonnull SelectMenuInteractionEvent event) {
        final String label = event.getSelectMenu().getId().split(":")[1];

        for (SelectMenu b : selectMenus) {
            for (String l : b.getLabels()) {
                if (label.startsWith(l)) {
                    Wally.getInstance().logger.log(LogLevel.INFO, event.getMember().getEffectiveName() + " used menu [" + label + "]");
                    b.getExecutable().onChange(buttonUser, event);
                }
            }
        }
    }
}
