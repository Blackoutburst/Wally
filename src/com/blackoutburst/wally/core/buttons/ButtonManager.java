package com.blackoutburst.wally.core.buttons;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class ButtonManager {

    private static ButtonManager instance = null;

    private static final Set<Button> buttons = new HashSet<>();

    /**
     * Get the button manager instance
     *
     * @return the button manager instance
     */
    public static ButtonManager getInstance() {
        if (instance == null)
            instance = new ButtonManager();

        return instance;
    }

    /**
     * Register Wally button
     */
    private ButtonManager() {
        register(new ButtonCommand(), "Yes run");
        register(new ButtonCancel(), "No", "Nevermind");
        register(new ButtonReloadUsers(), "Yes reload the users");
        register(new ButtonSendLog(), "Send me the log");
        register(new ButtonDisplayPreference(), "TEXT", "EMBED", "CANVAS");

        Wally.getInstance().logger.log(LogLevel.INFO, "Button registered!");
    }

    /**
     * Register new button
     *
     * @param executable the button executable
     * @param labels the button label
     */
    private void register(@Nonnull ButtonExecutable executable, @Nonnull String... labels) {
        buttons.add(new Button(executable, labels));
        Wally.getInstance().logger.log(LogLevel.INFO, "Button ["+labels[0]+"] is registered");
    }

    /**
     * Run the action associated with the button pressed
     * @param event the buttonInteraction event
     */
    public void runAction(@Nonnull ButtonInteractionEvent event) {
        final String label = event.getButton().getLabel();

        for (Button b : buttons) {
            for (String l : b.getLabels()) {
                if (label.startsWith(l)) {
                    Wally.getInstance().logger.log(LogLevel.INFO, event.getMember().getEffectiveName() + " clicked [" + label + "]");
                    b.getExecutable().onClick(event);
                }
            }
        }
    }
}
