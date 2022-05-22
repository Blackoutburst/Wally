package com.blackoutburst.wally.core.selectmenu

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
import java.util.HashSet

object SelectMenuManager {

    private val selectMenus: MutableSet<SelectMenu> = HashSet()

    /**
     * Register Wally button
     */
    init {
        register(SelectMenuSkin(), "skin")
        register(SelectMenuStats(), "stats")
        register(SelectMenuPracticeStats(), "practice stats")
        register(SelectMenuLeaderboard(), "leaderboard")
        Wally.logger.log(LogLevel.INFO, "Select menu registered!")
    }

    /**
     * Register new button
     *
     * @param executable the button executable
     * @param labels the button label
     */
    private fun register(executable: SelectMenuExecutable, vararg labels: String) {
        selectMenus.add(SelectMenu(executable, labels))
        Wally.logger.log(LogLevel.INFO, "Menu [" + labels[0] + "] is registered")
    }

    /**
     * Run the action associated with the button pressed
     *
     * @param event the buttonInteraction event
     */
    fun runAction(menuUser: WallyUser?, event: SelectMenuInteractionEvent) {
        val label = event.selectMenu.id!!.split(":").toTypedArray()[1]
        val memberName = event.member!!.effectiveName

        for (b in selectMenus) {
            for (l in b.labels) {
                if (label.startsWith(l)) {
                    Wally.logger.log(LogLevel.INFO,"$memberName used menu [$label]")
                    b.executable.onChange(menuUser!!, event)
                }
            }
        }
    }
}