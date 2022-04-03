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
        register(new ButtonCancel(), "No");

        Wally.getInstance().logger.log(LogLevel.INFO, "Button registered!");
    }

    /**
     * Register new button
     *
     * @param executable the button executable
     * @param label the button label
     */
    private void register(@Nonnull ButtonExecutable executable, @Nonnull String label) {
        buttons.add(new Button(executable, label));
    }

    /**
     * Run the action associated with the button pressed
     * @param event the buttonInteraction event
     */
    public void runAction(@Nonnull ButtonInteractionEvent event) {
        final String label = event.getButton().getLabel();

        for (Button b : buttons) {
            if (label.startsWith(b.getLabel())) {
                b.getExecutable().onClick(event);
                Wally.getInstance().logger.log(LogLevel.INFO, event.getMember().getEffectiveName()+" clicked ["+label+"]");
            }
        }
    }
}
