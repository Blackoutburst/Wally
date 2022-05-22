package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import java.util.HashSet

object ButtonManager {

    private val buttons: MutableSet<Button> = HashSet()
    
    init {
        register(ButtonCommand(), "Yes run")
        register(ButtonCancel(), "No", "Nevermind")
        register(ButtonReloadUsers(), "Yes reload the users")
        register(ButtonSendLog(), "Send me the log")
        register(ButtonDisplayPreference(), "TEXT", "EMBED", "CANVAS")
        register(ButtonLogLevel(), "INFO", "WARN", "ERROR", "NONE")
        register(ButtonPermissionLevel(), "MEMBER", "STAFF", "ADMIN")
        Wally.logger.log(LogLevel.INFO, "Button registered!")
    }

    private fun register(executable: ButtonExecutable, vararg labels: String) {
        buttons.add(Button(executable, labels))
        Wally.logger.log(LogLevel.INFO, "Button [" + labels[0] + "] is registered")
    }

    fun runAction(buttonUser: WallyUser?, event: ButtonInteractionEvent) {
        val label = event.button.label
        for (b in buttons) {
            for (l in b.labels) {
                if (label.startsWith(l)) {
                    Wally.logger.log(LogLevel.INFO,event.member!!.effectiveName + " clicked [" + label + "]")
                    b.executable.onClick(buttonUser!!, event)
                }
            }
        }
    }
}