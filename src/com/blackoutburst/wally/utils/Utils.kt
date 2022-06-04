package com.blackoutburst.wally.utils

import com.blackoutburst.wally.core.Wally
import net.dv8tion.jda.api.entities.Role
import java.awt.Color
import kotlin.math.abs

object Utils {

    fun formatLine(score: Number, position: Int): String {
        return if (score is Int)
            Wally.numberFormat.format(score.toLong()) + " " + LeaderboardUtils.formatPosition(position)
        else
            Wally.decimalFormat.format(score.toLong()) + " " + LeaderboardUtils.formatPosition(position)
    }

    fun formatLineReverse(score: Number, position: Int): String {
        return if (score is Int)
             LeaderboardUtils.formatPosition(position) + " " + Wally.numberFormat.format(score.toLong())
        else
            LeaderboardUtils.formatPosition(position) + " " + Wally.decimalFormat.format(score.toLong())
    }

    fun formatScoreCompare(score1: Number, position1: Number, score2: Number, position2: Number) : String {
        val line = StringBuilder()

        line.append(if (position1.toLong() > position2.toLong()) " +(" else if (position1.toLong() < position2.toLong()) " -(" else "")
        line.append(LeaderboardUtils.formatPosition(abs(position1.toInt() - position2.toInt())))
        line.append(if (score1.toLong() > score2.toLong()) ") | +(" else if (score1.toLong() < score2.toLong()) ") | -(" else "")
        line.append(if (score1 is Int) Wally.numberFormat.format(abs(score1.toLong() - score2.toLong())) else Wally.decimalFormat.format(abs(score1.toLong() - score2.toLong())))
        line.append(if (score1.toLong() > score2.toLong()) ")- | (" else if (score1.toLong() < score2.toLong()) ")+ | (" else "")
        line.append(LeaderboardUtils.formatPosition(abs(position1.toInt() - position2.toInt())))
        line.append(if (position1.toLong() > position2.toLong()) ")- " else if (position1.toLong() < position2.toLong()) ")+ " else "")

        return line.toString()
    }

    fun getHighestScore(scores: IntArray): Int {
        var highest = 0

        for (score in scores) if (score > highest) highest = score
        return highest
    }

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