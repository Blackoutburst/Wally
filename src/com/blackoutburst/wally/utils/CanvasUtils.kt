package com.blackoutburst.wally.utils

import com.blackoutburst.wally.core.canvas.Canvas
import com.blackoutburst.wally.core.canvas.ResourcesLoader.background
import com.blackoutburst.wally.core.canvas.ResourcesLoader.hypixelHeader
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconChallenges
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconDev
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconFinals
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconOverall
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconQualification
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconTier1
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconTier2
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconTier3
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconTotal
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconTotalApi
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconWalls
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconWins
import com.blackoutburst.wally.core.canvas.ResourcesLoader.line
import com.blackoutburst.wally.core.canvas.ResourcesLoader.missingHead
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.utils.Utils.downloadAvatar
import com.blackoutburst.wally.utils.Utils.formatLine
import java.awt.Color
import java.io.File
import java.net.URL
import javax.imageio.ImageIO

object CanvasUtils {

    fun createHypixelStatsCanvas(data: HypixelStats) {
        val user = DatabaseUtils.getWallyUserFromUUID(data.uuid)
        val canvas = Canvas(1000, 600)
        val fontColor = Color.decode(user!!.fontColor)
        val color = Utils.getColorFromScore(Utils.getHighestScore(data.scores))

        if (user.hasFont()) {
            canvas.loadFont("database/linked users/${user.member.id}/resources/font.ttf")
        }

        if (user.hasBackground()) {
            canvas.drawImage("database/linked users/${user.member.id}/resources/background.png", 0, 37, 1000, 563)
        } else {
            canvas.drawImage(background, 0, 37, 1000, 563)
        }

        canvas.drawImage(hypixelHeader, 0, 0, 1000, 37, color)

        if (!data.combined) {
            if (File("database/linked users/${user.member.id}/accounts/${data.uuid}/head.png").exists())
                canvas.drawImage("database/linked users/${user.member.id}/accounts/${data.uuid}/head.png", 20, 57, 128, 128)
            else if (File("database/leaderboards/hypixel/${data.uuid}/head.png").exists())
                canvas.drawImage("database/leaderboards/hypixel/${data.uuid}/head.png", 20, 57, 128, 128)
            else if (File("database/leaderboards/practice/${data.uuid}/head.png").exists())
                canvas.drawImage("database/leaderboards/practice/${data.uuid}/head.png", 20, 57, 128, 128)
            else
                canvas.drawImage(missingHead, 20, 57, 128, 128)
        } else {

            canvas.drawImage(downloadAvatar(user.member.effectiveAvatarUrl), 20, 57, 128, 128)
        }

        canvas.drawStringLeft(data.name, 158, 57, 36.0f, fontColor)
        canvas.drawStringLeft(user.subtitle, 158, 97, 30.0f, fontColor)

        var iconX = 158
        val increment = 55
        if (user.isDeveloper) {canvas.drawImage(iconDev, iconX, 135, 50, 50); iconX += increment}
        if (user.patreonTier >= 1) {canvas.drawImage(iconTier1, iconX, 135, 50, 50); iconX += increment}
        if (user.patreonTier >= 2) {canvas.drawImage(iconTier2, iconX, 135, 50, 50); iconX += increment}
        if (user.patreonTier >= 3) {canvas.drawImage(iconTier3, iconX, 135, 50, 50); iconX += increment}
        if (data.winsPosition in 1..10) {canvas.drawImage(iconWins, iconX, 135, 50, 50); iconX += increment}
        if (data.wallsPosition in 1..10) {canvas.drawImage(iconWalls, iconX, 135, 50, 50); iconX += increment}
        if (data.qualificationPosition in 1..10) {canvas.drawImage(iconQualification, iconX, 135, 50, 50); iconX += increment}
        if (data.finalsPosition in 1..10) {canvas.drawImage(iconFinals, iconX, 135, 50, 50); iconX += increment}
        if (data.totalPosition in 1..10) {canvas.drawImage(iconTotal, iconX, 135, 50, 50); iconX += increment}
        if (data.totalAPIPosition in 1..10) {canvas.drawImage(iconTotalApi, iconX, 135, 50, 50); iconX += increment}
        if (data.challengesPosition in 1..10) {canvas.drawImage(iconChallenges, iconX, 135, 50, 50); iconX += increment}
        if (data.overallPosition in 1..10) {canvas.drawImage(iconOverall, iconX, 135, 50, 50); iconX += increment}

        canvas.drawImage(line, 0, 205, 450, 5, color)

        canvas.drawImage(iconWins, 40, 270, 50, 50)
        canvas.drawStringLeft(formatLine("Wins", data.wins, data.winsPosition), 110, 285, 30.0f, fontColor)
        canvas.drawImage(iconWalls, 40, 340, 50, 50)
        canvas.drawStringLeft(formatLine("Walls", data.walls, data.wallsPosition), 110, 355, 30.0f, fontColor)
        canvas.drawImage(iconQualification, 40, 410, 50, 50)
        canvas.drawStringLeft(formatLine("Qualification", data.qualification, data.qualificationPosition), 110, 425, 30.0f, fontColor)
        canvas.drawImage(iconFinals, 40, 480, 50, 50)
        canvas.drawStringLeft(formatLine("Finals", data.finals, data.finalsPosition), 110, 495, 30.0f, fontColor)

        canvas.drawImage(iconTotal, 490, 270, 50, 50)
        canvas.drawStringLeft(formatLine("Total Score", data.total, data.totalPosition), 560, 285, 30.0f, fontColor)
        canvas.drawImage(iconTotalApi, 490, 340, 50, 50)
        canvas.drawStringLeft(formatLine("Total Score (API)", data.totalAPI, data.totalAPIPosition), 560, 355, 30.0f, fontColor)
        canvas.drawImage(iconChallenges, 490, 410, 50, 50)
        canvas.drawStringLeft(formatLine("Challenges", data.challenges, data.challengesPosition), 560, 425, 30.0f, fontColor)

        if (!data.combined) {
            canvas.drawImage(iconOverall, 490, 480, 50, 50)
            canvas.drawStringLeft(formatLine("Overall Rank", data.overall, data.overallPosition), 560, 495, 30.0f, fontColor)
        }

        canvas.save("stats.png")
    }
}