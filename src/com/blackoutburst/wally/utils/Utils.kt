package com.blackoutburst.wally.utils

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Role
import java.awt.Color
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO
import kotlin.math.abs


object Utils {

    fun getGenderPrefix(discordId: String): String? {
        val hisRole = Wally.hitwDiscord!!.getRoleById(ConfigManager.loadValue("roles", "He/Him"))
        val herRole = Wally.hitwDiscord!!.getRoleById(ConfigManager.loadValue("roles", "She/Her"))
        val theirRole = Wally.hitwDiscord!!.getRoleById(ConfigManager.loadValue("roles", "They/Them"))
        val memberRoles = Wally.hitwDiscord!!.getMemberById(discordId)!!.roles

        val his = memberRoles.contains(hisRole)
        val her = memberRoles.contains(herRole)
        val their = memberRoles.contains(theirRole)

        if (his && !her && !their) return "his"
        return if (her && !his && !their) "her" else "their"
    }

    fun downloadAvatar(avatarUrl: String) : BufferedImage {
        val connection = URL(avatarUrl).openConnection()
        connection.setRequestProperty("User-Agent", "fuck you")

        return ImageIO.read(connection.getInputStream())
    }

    fun formatLine(label :String, score: Number, position: Int): String {
        return if (score is Int)
            "$label: " + Wally.numberFormat.format(score.toLong()) + " " + LeaderboardUtils.formatPosition(position)
        else
            "$label: " + Wally.decimalFormat.format(score.toDouble()) + " " + LeaderboardUtils.formatPosition(position)
    }

    fun formatLineReverse(label :String, score: Number, position: Int): String {
        return if (score is Int)
            LeaderboardUtils.formatPosition(position) + " " + Wally.numberFormat.format(score.toLong()) + " :$label"
        else
            LeaderboardUtils.formatPosition(position) + " " + Wally.decimalFormat.format(score.toDouble()) + " :$label"
    }

    fun formatLine(score: Number, position: Int): String {
        return if (score is Int)
            Wally.numberFormat.format(score.toLong()) + " " + LeaderboardUtils.formatPosition(position)
        else
            Wally.decimalFormat.format(score.toDouble()) + " " + LeaderboardUtils.formatPosition(position)
    }

    fun formatLineReverse(score: Number, position: Int): String {
        return if (score is Int)
             LeaderboardUtils.formatPosition(position) + " " + Wally.numberFormat.format(score.toLong())
        else
            LeaderboardUtils.formatPosition(position) + " " + Wally.decimalFormat.format(score.toDouble())
    }

    fun formatScoreCompare(score1: Number, score2: Number) : String {
        return String.format("%8s || %-8s",
            if (score1 is Int) Wally.numberFormat.format(score1.toLong() - score2.toLong()) else Wally.decimalFormat.format(score1.toDouble() - score2.toDouble()),
            if (score1 is Int) Wally.numberFormat.format(score2.toLong() - score1.toLong()) else Wally.decimalFormat.format(score2.toDouble() - score1.toDouble())
        )
    }

    fun getScoreCompareColor(score1: Number, score2: Number) : Color {
        if (score1 is Int) {
            if (score1.toLong() < score2.toLong())
                return Color.RED
            if (score1.toLong() > score2.toLong())
                return Color.GREEN
            return Color.WHITE
        } else {
            if (score1.toDouble() > score2.toDouble())
                return Color.RED
            if (score1.toDouble() < score2.toDouble())
                return Color.GREEN
            return Color.WHITE
        }
    }

    fun formatScoreCompareCanvas(score1: Number, score2: Number) : String {
        return if (score1 is Int)
            "[" + Wally.numberFormat.format(abs(score1.toLong() - score2.toLong())) + "]"
        else
            "[" + Wally.decimalFormat.format(abs(score1.toDouble() - score2.toDouble())) + "]"
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

        val lowRound = (if (score > 500) 500 else score) / 50 * 50
        val roles = Wally.hitwDiscord!!.roles
        val role = roles.stream().filter { r: Role -> r.name == "$lowRound+ Club" }.findFirst()

        return if (role.isPresent && role.get().color != null) role.get().color!! else Color.GRAY
    }
}