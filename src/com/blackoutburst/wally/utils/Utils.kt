package com.blackoutburst.wally.utils

import com.blackoutburst.wally.core.Wally
import net.dv8tion.jda.api.entities.Role
import java.awt.Color

object Utils {

    /**
     * @param score the score
     * @param position the position
     * @return a formatted line
     */
    fun formatLine(score: Number, position: Int): String {
        return if (score is Int)
            Wally.numberFormat.format(score.toLong()) + " " + LeaderboardUtils.formatPosition(position)
        else
            Wally.decimalFormat.format(score.toLong()) + " " + LeaderboardUtils.formatPosition(position)
    }

    /**
     * Input as many scores as you want, and it will return the highest one
     * @param scores the scores
     * @return the highest score
     */
    fun getHighestScore(scores: IntArray): Int {
        var highest = 0

        for (score in scores) if (score > highest) highest = score
        return highest
    }

    /**
     * Get a club role color based on score
     * The input number is rounded to the lower 50 div
     * @param score the player score
     * @return the role color
     */
    fun getColorFromScore(score: Int): Color {
        if (Wally.hitwDiscord == null) {
            return Color.GRAY
        }

        val lowRound = (if (score > 500) score else score + 49) / 50 * 50 - 50
        val roles = Wally.hitwDiscord!!.roles
        val role = roles.stream().filter { r: Role -> r.name == "$lowRound+ Club" }.findFirst()

        return if (role.isPresent && role.get().color != null) role.get().color!! else Color.GRAY
    }
}