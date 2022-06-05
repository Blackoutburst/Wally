package com.blackoutburst.wally.utils

import com.blackoutburst.wally.core.canvas.Canvas
import com.blackoutburst.wally.core.canvas.ResourcesLoader.background
import com.blackoutburst.wally.core.canvas.ResourcesLoader.hypixelHeader
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconChallenges
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconDev
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconFinals
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconOverall
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconPracticeFinals
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconPracticeLobby
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconPracticeOverall
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconPracticeQualification
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconPracticeTotal
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconPracticeWideFinals
import com.blackoutburst.wally.core.canvas.ResourcesLoader.iconPracticeWideQualification
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
import com.blackoutburst.wally.core.canvas.ResourcesLoader.practiceHeader
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.utils.Utils.downloadAvatar
import com.blackoutburst.wally.utils.Utils.formatLine
import com.blackoutburst.wally.utils.Utils.formatLineReverse
import com.blackoutburst.wally.utils.Utils.formatScoreCompareCanvas
import com.blackoutburst.wally.utils.Utils.getScoreCompareColor
import java.awt.Color
import java.io.File

object CanvasUtils {

    fun createPracticeCompareCanvas(data1: String, data2: String) {
        val canvas = Canvas(1000, 600)

        val player1 = PracticeStats(data1)
        val user1 = DatabaseUtils.getWallyUserFromUUID(player1.uuid)
        val fontColor1 = if (user1 == null) Color.WHITE else Color.decode(user1.fontColor)
        val color1 = Utils.getColorFromScore(Utils.getHighestScore(player1.scores))

        val player2 = PracticeStats(data2)
        val user2 = DatabaseUtils.getWallyUserFromUUID(player2.uuid)
        val fontColor2 = if (user2 == null) Color.WHITE else Color.decode(user2.fontColor)
        val color2 = Utils.getColorFromScore(Utils.getHighestScore(player2.scores))

        if (user1 != null && user1.hasFont()) {
            canvas.loadFont("database/linked users/${user1.member.id}/resources/font.ttf")
        }

        if (user1 != null && user1.hasBackground()) {
            canvas.drawImageLeftHalf("database/linked users/${user1.member.id}/resources/background.png", 0, 37, 500, 563)
        } else {
            canvas.drawImageLeftHalf(background, 0, 37, 500, 563)
        }

        canvas.drawImageLeftHalf(practiceHeader, 0, 0, 500, 37, color1)

        if (user1 != null && File("database/linked users/${user1.member.id}/accounts/${player1.uuid}/head.png").exists())
            canvas.drawImage("database/linked users/${user1.member.id}/accounts/${player1.uuid}/head.png", 20, 52, 64, 64)
        else if (File("database/leaderboards/hypixel/${player1.uuid}/head.png").exists())
            canvas.drawImage("database/leaderboards/hypixel/${player1.uuid}/head.png", 20, 52, 64, 64)
        else if (File("database/leaderboards/practice/${player1.uuid}/head.png").exists())
            canvas.drawImage("database/leaderboards/practice/${player1.uuid}/head.png", 20, 52, 64, 64)
        else
            canvas.drawImage(missingHead, 20, 52, 64, 64)

        canvas.drawStringLeft(player1.name, 100, 57, 36.0f, fontColor1)
        if (user1 != null)
            canvas.drawStringLeft(user1.subtitle, 100, 97, 30.0f, fontColor1)

        canvas.drawImage(line, 0, 130, 450, 5, color1)

        canvas.drawImage(iconPracticeQualification, 20, 150, 40, 40)
        canvas.drawStringLeft(formatLine("Qualification", player1.qualification, player1.qualificationPosition), 80, 160, 30.0f, getScoreCompareColor(player1.qualification, player2.qualification))
        canvas.drawImage(iconPracticeFinals, 20, 205, 40, 40)
        canvas.drawStringLeft(formatLine("Finals", player1.finals, player1.finalsPosition), 80, 215, 30.0f, getScoreCompareColor(player1.finals, player2.finals))
        canvas.drawImage(iconPracticeLobby, 20, 260, 40, 40)
        canvas.drawStringLeft(formatLine("Lobby", player1.lobby, player1.lobbyPosition), 80, 270, 30.0f, getScoreCompareColor(player1.lobby, player2.lobby))
        canvas.drawImage(iconPracticeWideQualification, 20, 315, 40, 40)
        canvas.drawStringLeft(formatLine("Wide Qualif", player1.wideQualification, player1.wideQualificationPosition), 80, 325, 30.0f, getScoreCompareColor(player1.wideQualification, player2.wideQualification))
        canvas.drawImage(iconPracticeWideFinals, 20, 370, 40, 40)
        canvas.drawStringLeft(formatLine("Wide Finals", player1.wideFinals, player1.wideFinalsPosition), 80, 380, 30.0f, getScoreCompareColor(player1.wideFinals, player2.wideFinals))
        canvas.drawImage(iconPracticeTotal, 20, 425, 40, 40)
        canvas.drawStringLeft(formatLine("Total Score", player1.total, player1.totalPosition), 80, 435, 30.0f, getScoreCompareColor(player1.total, player2.total))
        if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) {
            canvas.drawImage(iconPracticeOverall, 20, 480, 40, 40)
            canvas.drawStringLeft(formatLine("Overall Rank", player1.overall, player1.overallPosition), 80, 490, 30.0f, getScoreCompareColor(player1.overall, player2.overall))
        }

        if (user2 != null && user2.hasFont()) {
            canvas.loadFont("database/linked users/${user2.member.id}/resources/font.ttf")
        }

        if (user2 != null && user2.hasBackground()) {
            canvas.drawImageRightHalf("database/linked users/${user2.member.id}/resources/background.png", 500, 37, 500, 563)
        } else {
            canvas.drawImageRightHalf(background, 500, 37, 500, 563)
        }

        canvas.drawImageRightHalf(hypixelHeader, 500, 0, 500, 37, color2)

        if (user2 != null && File("database/linked users/${user2.member.id}/accounts/${player2.uuid}/head.png").exists())
            canvas.drawImage("database/linked users/${user2.member.id}/accounts/${player2.uuid}/head.png", 916, 52, 64, 64)
        else if (File("database/leaderboards/hypixel/${player2.uuid}/head.png").exists())
            canvas.drawImage("database/leaderboards/hypixel/${player2.uuid}/head.png", 916, 52, 64, 64)
        else if (File("database/leaderboards/practice/${player2.uuid}/head.png").exists())
            canvas.drawImage("database/leaderboards/practice/${player2.uuid}/head.png", 916, 52, 64, 64)
        else
            canvas.drawImage(missingHead, 916, 52, 64, 64)

        canvas.drawStringRight(player2.name, 900, 57, 36.0f, fontColor2)
        if (user2 != null)
            canvas.drawStringRight(user2.subtitle, 900, 97, 30.0f, fontColor2)

        canvas.drawImage(line, 1000, 130, -450, 5, color2)

        canvas.drawImage(iconPracticeQualification, 940, 150, 40, 40)
        canvas.drawStringRight(formatLineReverse("Qualification", player2.qualification, player2.qualificationPosition), 920, 160, 30.0f, getScoreCompareColor(player2.qualification, player1.qualification))
        canvas.drawImage(iconPracticeFinals, 940, 205, 40, 40)
        canvas.drawStringRight(formatLineReverse("Finals", player2.finals, player2.finalsPosition), 920, 215, 30.0f, getScoreCompareColor(player2.finals, player1.finals))
        canvas.drawImage(iconPracticeLobby, 940, 260, 40, 40)
        canvas.drawStringRight(formatLineReverse("Lobby", player2.lobby, player2.lobbyPosition), 920, 270, 30.0f, getScoreCompareColor(player2.lobby, player1.lobby))
        canvas.drawImage(iconPracticeWideQualification, 940, 315, 40, 40)
        canvas.drawStringRight(formatLineReverse("Wide Qualif", player2.wideQualification, player2.wideQualificationPosition), 920, 325, 30.0f, getScoreCompareColor(player2.wideQualification, player1.wideQualification))
        canvas.drawImage(iconPracticeWideFinals, 940, 370, 40, 40)
        canvas.drawStringRight(formatLineReverse("Wide Finals", player2.wideFinals, player2.wideFinalsPosition), 920, 380, 30.0f, getScoreCompareColor(player2.wideFinals, player1.wideFinals))
        canvas.drawImage(iconPracticeTotal, 940, 425, 40, 40)
        canvas.drawStringRight(formatLineReverse("Total Score", player2.total, player2.totalPosition), 920, 435, 30.0f, getScoreCompareColor(player2.total, player1.total))
        if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) {
            canvas.drawImage(iconPracticeOverall, 940, 480, 40, 40)
            canvas.drawStringRight(formatLineReverse("Overall Rank", player2.overall, player2.overallPosition), 920, 490, 30.0f, getScoreCompareColor(player2.overall, player1.overall))
        }

        canvas.drawStringCenter(formatScoreCompareCanvas(player1.qualification, player2.qualification), 500, 160, 30.0f, Color.WHITE)
        canvas.drawStringCenter(formatScoreCompareCanvas(player1.finals, player2.finals), 500, 215, 30.0f, Color.WHITE)
        canvas.drawStringCenter(formatScoreCompareCanvas(player1.lobby, player2.lobby), 500, 270, 30.0f, Color.WHITE)
        canvas.drawStringCenter(formatScoreCompareCanvas(player1.wideQualification, player2.wideQualification), 500, 325, 30.0f, Color.WHITE)
        canvas.drawStringCenter(formatScoreCompareCanvas(player1.wideFinals, player2.wideFinals), 500, 380, 30.0f, Color.WHITE)
        canvas.drawStringCenter(formatScoreCompareCanvas(player1.total, player2.total), 500, 435, 30.0f, Color.WHITE)
        if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0)
            canvas.drawStringCenter(formatScoreCompareCanvas(player1.overall, player2.overall), 500, 490, 30.0f, Color.WHITE)

        canvas.save("practice_compare.png")
    }

    fun createHypixelCompareCanvas(data1: String, data2: String) {
        val canvas = Canvas(1000, 600)

        val player1 = HypixelStats(data1)
        val user1 = DatabaseUtils.getWallyUserFromUUID(player1.uuid)
        val fontColor1 = if (user1 == null) Color.WHITE else Color.decode(user1.fontColor)
        val color1 = Utils.getColorFromScore(Utils.getHighestScore(player1.scores))

        val player2 = HypixelStats(data2)
        val user2 = DatabaseUtils.getWallyUserFromUUID(player2.uuid)
        val fontColor2 = if (user2 == null) Color.WHITE else Color.decode(user2.fontColor)
        val color2 = Utils.getColorFromScore(Utils.getHighestScore(player2.scores))

        if (user1 != null && user1.hasFont()) {
            canvas.loadFont("database/linked users/${user1.member.id}/resources/font.ttf")
        }

        if (user1 != null && user1.hasBackground()) {
            canvas.drawImageLeftHalf("database/linked users/${user1.member.id}/resources/background.png", 0, 37, 500, 563)
        } else {
            canvas.drawImageLeftHalf(background, 0, 37, 500, 563)
        }

        canvas.drawImageLeftHalf(hypixelHeader, 0, 0, 500, 37, color1)

        if (user1 != null && File("database/linked users/${user1.member.id}/accounts/${player1.uuid}/head.png").exists())
            canvas.drawImage("database/linked users/${user1.member.id}/accounts/${player1.uuid}/head.png", 20, 52, 64, 64)
        else if (File("database/leaderboards/hypixel/${player1.uuid}/head.png").exists())
            canvas.drawImage("database/leaderboards/hypixel/${player1.uuid}/head.png", 20, 52, 64, 64)
        else if (File("database/leaderboards/practice/${player1.uuid}/head.png").exists())
            canvas.drawImage("database/leaderboards/practice/${player1.uuid}/head.png", 20, 52, 64, 64)
        else
            canvas.drawImage(missingHead, 20, 52, 64, 64)

        canvas.drawStringLeft(player1.name, 100, 57, 36.0f, fontColor1)
        if (user1 != null)
            canvas.drawStringLeft(user1.subtitle, 100, 97, 30.0f, fontColor1)

        canvas.drawImage(line, 0, 130, 450, 5, color1)

        canvas.drawImage(iconWins, 20, 150, 40, 40)
        canvas.drawStringLeft(formatLine("Wins", player1.wins, player1.winsPosition), 80, 160, 30.0f, getScoreCompareColor(player1.wins, player2.wins))
        canvas.drawImage(iconWalls, 20, 205, 40, 40)
        canvas.drawStringLeft(formatLine("Walls", player1.walls, player1.wallsPosition), 80, 215, 30.0f, getScoreCompareColor(player1.walls, player2.walls))
        canvas.drawImage(iconQualification, 20, 260, 40, 40)
        canvas.drawStringLeft(formatLine("Qualification", player1.qualification, player1.qualificationPosition), 80, 270, 30.0f, getScoreCompareColor(player1.qualification, player2.qualification))
        canvas.drawImage(iconFinals, 20, 315, 40, 40)
        canvas.drawStringLeft(formatLine("Finals", player1.finals, player1.finalsPosition), 80, 325, 30.0f, getScoreCompareColor(player1.finals, player2.finals))
        canvas.drawImage(iconTotal, 20, 370, 40, 40)
        canvas.drawStringLeft(formatLine("Total Score", player1.total, player1.totalPosition), 80, 380, 30.0f, getScoreCompareColor(player1.total, player2.total))
        canvas.drawImage(iconTotalApi, 20, 425, 40, 40)
        canvas.drawStringLeft(formatLine("Total Score (API)", player1.totalAPI, player1.totalAPIPosition), 80, 435, 30.0f, getScoreCompareColor(player1.totalAPI, player2.totalAPI))
        canvas.drawImage(iconChallenges, 20, 480, 40, 40)
        canvas.drawStringLeft(formatLine("Challenges", player1.challenges, player1.challengesPosition), 80, 490, 30.0f, getScoreCompareColor(player1.challenges, player2.challenges))
        if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) {
            canvas.drawImage(iconOverall, 20, 535, 40, 40)
            canvas.drawStringLeft(formatLine("Overall Rank", player1.overall, player1.overallPosition), 80, 545, 30.0f, getScoreCompareColor(player1.overall, player2.overall))
        }

        if (user2 != null && user2.hasFont()) {
            canvas.loadFont("database/linked users/${user2.member.id}/resources/font.ttf")
        }

        if (user2 != null && user2.hasBackground()) {
            canvas.drawImageRightHalf("database/linked users/${user2.member.id}/resources/background.png", 500, 37, 500, 563)
        } else {
            canvas.drawImageRightHalf(background, 500, 37, 500, 563)
        }

        canvas.drawImageRightHalf(hypixelHeader, 500, 0, 500, 37, color2)

        if (user2 != null && File("database/linked users/${user2.member.id}/accounts/${player2.uuid}/head.png").exists())
            canvas.drawImage("database/linked users/${user2.member.id}/accounts/${player2.uuid}/head.png", 916, 52, 64, 64)
        else if (File("database/leaderboards/hypixel/${player2.uuid}/head.png").exists())
            canvas.drawImage("database/leaderboards/hypixel/${player2.uuid}/head.png", 916, 52, 64, 64)
        else if (File("database/leaderboards/practice/${player2.uuid}/head.png").exists())
            canvas.drawImage("database/leaderboards/practice/${player2.uuid}/head.png", 916, 52, 64, 64)
        else
            canvas.drawImage(missingHead, 916, 52, 64, 64)

        canvas.drawStringRight(player2.name, 900, 57, 36.0f, fontColor2)
        if (user2 != null)
            canvas.drawStringRight(user2.subtitle, 900, 97, 30.0f, fontColor2)

        canvas.drawImage(line, 1000, 130, -450, 5, color2)

        canvas.drawImage(iconWins, 940, 150, 40, 40)
        canvas.drawStringRight(formatLineReverse("Wins", player2.wins, player2.winsPosition), 920, 160, 30.0f, getScoreCompareColor(player2.wins, player1.wins))
        canvas.drawImage(iconWalls, 940, 205, 40, 40)
        canvas.drawStringRight(formatLineReverse("Walls", player2.walls, player2.wallsPosition), 920, 215, 30.0f, getScoreCompareColor(player2.walls, player1.walls))
        canvas.drawImage(iconQualification, 940, 260, 40, 40)
        canvas.drawStringRight(formatLineReverse("Qualification", player2.qualification, player2.qualificationPosition), 920, 270, 30.0f, getScoreCompareColor(player2.qualification, player1.qualification))
        canvas.drawImage(iconFinals, 940, 315, 40, 40)
        canvas.drawStringRight(formatLineReverse("Finals", player2.finals, player2.finalsPosition), 920, 325, 30.0f, getScoreCompareColor(player2.finals, player1.finals))
        canvas.drawImage(iconTotal, 940, 370, 40, 40)
        canvas.drawStringRight(formatLineReverse("Total Score", player2.total, player2.totalPosition), 920, 380, 30.0f, getScoreCompareColor(player2.total, player1.total))
        canvas.drawImage(iconTotalApi, 940, 425, 40, 40)
        canvas.drawStringRight(formatLineReverse("Total Score (API)", player2.totalAPI, player2.totalAPIPosition), 920, 435, 30.0f, getScoreCompareColor(player2.totalAPI, player1.totalAPI))
        canvas.drawImage(iconChallenges, 940, 480, 40, 40)
        canvas.drawStringRight(formatLineReverse("Challenges", player2.challenges, player2.challengesPosition), 920, 490, 30.0f, getScoreCompareColor(player2.challenges, player1.challenges))
        if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) {
            canvas.drawImage(iconOverall, 940, 535, 40, 40)
            canvas.drawStringRight(formatLineReverse("Overall Rank", player2.overall, player2.overallPosition), 920, 545, 30.0f, getScoreCompareColor(player2.overall, player1.overall))
        }

        canvas.drawStringCenter(formatScoreCompareCanvas(player1.wins, player2.wins), 500, 160, 30.0f, Color.WHITE)
        canvas.drawStringCenter(formatScoreCompareCanvas(player1.walls, player2.walls), 500, 215, 30.0f, Color.WHITE)
        canvas.drawStringCenter(formatScoreCompareCanvas(player1.qualification, player2.qualification), 500, 270, 30.0f, Color.WHITE)
        canvas.drawStringCenter(formatScoreCompareCanvas(player1.finals, player2.finals), 500, 325, 30.0f, Color.WHITE)
        canvas.drawStringCenter(formatScoreCompareCanvas(player1.total, player2.total), 500, 380, 30.0f, Color.WHITE)
        canvas.drawStringCenter(formatScoreCompareCanvas(player1.totalAPI, player2.totalAPI), 500, 435, 30.0f, Color.WHITE)
        canvas.drawStringCenter(formatScoreCompareCanvas(player1.challenges, player2.challenges), 500, 490, 30.0f, Color.WHITE)
        if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0)
            canvas.drawStringCenter(formatScoreCompareCanvas(player1.overall, player2.overall), 500, 545, 30.0f, Color.WHITE)

        canvas.save("compare.png")
    }

    fun createPracticeStatsCanvas(data: PracticeStats) {
        val user = DatabaseUtils.getWallyUserFromUUID(data.uuid)
        val canvas = Canvas(1000, 600)
        val fontColor = if (user == null) Color.WHITE else Color.decode(user.fontColor)
        val color = Utils.getColorFromScore(Utils.getHighestScore(data.scores))

        if (user != null && user.hasFont()) {
            canvas.loadFont("database/linked users/${user.member.id}/resources/font.ttf")
        }

        if (user != null && user.hasBackground()) {
            canvas.drawImage("database/linked users/${user.member.id}/resources/background.png", 0, 37, 1000, 563)
        } else {
            canvas.drawImage(background, 0, 37, 1000, 563)
        }

        canvas.drawImage(practiceHeader, 0, 0, 1000, 37, color)

        if (!data.combined) {
            if (user != null && File("database/linked users/${user.member.id}/accounts/${data.uuid}/head.png").exists())
                canvas.drawImage("database/linked users/${user.member.id}/accounts/${data.uuid}/head.png", 20, 57, 128, 128)
            else if (File("database/leaderboards/hypixel/${data.uuid}/head.png").exists())
                canvas.drawImage("database/leaderboards/hypixel/${data.uuid}/head.png", 20, 57, 128, 128)
            else if (File("database/leaderboards/practice/${data.uuid}/head.png").exists())
                canvas.drawImage("database/leaderboards/practice/${data.uuid}/head.png", 20, 57, 128, 128)
            else
                canvas.drawImage(missingHead, 20, 57, 128, 128)
        } else {
            canvas.drawImage(downloadAvatar(user!!.member.effectiveAvatarUrl), 20, 57, 128, 128)
        }

        canvas.drawStringLeft(data.name, 158, 57, 36.0f, fontColor)
        if (user != null )
            canvas.drawStringLeft(user.subtitle, 158, 97, 30.0f, fontColor)

        var iconX = 158
        val increment = 55
        if (user != null) {
            if (user.isDeveloper) {canvas.drawImage(iconDev, iconX, 135, 50, 50); iconX += increment}
            if (user.patreonTier >= 1) {canvas.drawImage(iconTier1, iconX, 135, 50, 50); iconX += increment}
            if (user.patreonTier >= 2) {canvas.drawImage(iconTier2, iconX, 135, 50, 50); iconX += increment}
            if (user.patreonTier >= 3) {canvas.drawImage(iconTier3, iconX, 135, 50, 50); iconX += increment}
        }
        if (data.qualificationPosition in 1..10) {canvas.drawImage(iconPracticeQualification, iconX, 135, 50, 50); iconX += increment}
        if (data.finalsPosition in 1..10) {canvas.drawImage(iconPracticeFinals, iconX, 135, 50, 50); iconX += increment}
        if (data.lobbyPosition in 1..10) {canvas.drawImage(iconPracticeLobby, iconX, 135, 50, 50); iconX += increment}
        if (data.wideQualificationPosition in 1..10) {canvas.drawImage(iconPracticeWideQualification, iconX, 135, 50, 50); iconX += increment}
        if (data.wideFinalsPosition in 1..10) {canvas.drawImage(iconPracticeWideFinals, iconX, 135, 50, 50); iconX += increment}
        if (data.totalPosition in 1..10) {canvas.drawImage(iconPracticeTotal, iconX, 135, 50, 50); iconX += increment}
        if (data.overallPosition in 1..10) {canvas.drawImage(iconPracticeOverall, iconX, 135, 50, 50); iconX += increment}

        canvas.drawImage(line, 0, 205, 450, 5, color)

        canvas.drawImage(iconPracticeQualification, 40, 270, 50, 50)
        canvas.drawStringLeft(formatLine("Qualification", data.qualification, data.qualificationPosition), 110, 285, 30.0f, fontColor)
        canvas.drawImage(iconPracticeFinals, 40, 340, 50, 50)
        canvas.drawStringLeft(formatLine("Finals", data.finals, data.finalsPosition), 110, 355, 30.0f, fontColor)
        canvas.drawImage(iconPracticeLobby, 40, 410, 50, 50)
        canvas.drawStringLeft(formatLine("Lobby", data.lobby, data.lobbyPosition), 110, 425, 30.0f, fontColor)
        canvas.drawImage(iconPracticeWideQualification, 40, 480, 50, 50)
        canvas.drawStringLeft(formatLine("Wide Qualification", data.wideQualification, data.wideQualificationPosition), 110, 495, 30.0f, fontColor)

        canvas.drawImage(iconPracticeWideFinals, 490, 270, 50, 50)
        canvas.drawStringLeft(formatLine("Wide Finals", data.wideFinals, data.wideFinalsPosition), 560, 285, 30.0f, fontColor)
        canvas.drawImage(iconPracticeTotal, 490, 340, 50, 50)
        canvas.drawStringLeft(formatLine("Total Score", data.total, data.totalPosition), 560, 355, 30.0f, fontColor)
        if (data.overall.toInt() != 0) {
            canvas.drawImage(iconPracticeOverall, 490, 410, 50, 50)
            canvas.drawStringLeft(formatLine("Overall Rank", data.overall, data.overallPosition), 560, 425, 30.0f, fontColor)
        }

        canvas.save("practice_stats.png")
    }

    fun createHypixelStatsCanvas(data: HypixelStats) {
        val user = DatabaseUtils.getWallyUserFromUUID(data.uuid)
        val canvas = Canvas(1000, 600)
        val fontColor = if (user == null) Color.WHITE else Color.decode(user.fontColor)
        val color = Utils.getColorFromScore(Utils.getHighestScore(data.scores))

        if (user != null && user.hasFont()) {
            canvas.loadFont("database/linked users/${user.member.id}/resources/font.ttf")
        }

        if (user != null && user.hasBackground()) {
            canvas.drawImage("database/linked users/${user.member.id}/resources/background.png", 0, 37, 1000, 563)
        } else {
            canvas.drawImage(background, 0, 37, 1000, 563)
        }

        canvas.drawImage(hypixelHeader, 0, 0, 1000, 37, color)

        if (!data.combined) {
            if (user != null && File("database/linked users/${user.member.id}/accounts/${data.uuid}/head.png").exists())
                canvas.drawImage("database/linked users/${user.member.id}/accounts/${data.uuid}/head.png", 20, 57, 128, 128)
            else if (File("database/leaderboards/hypixel/${data.uuid}/head.png").exists())
                canvas.drawImage("database/leaderboards/hypixel/${data.uuid}/head.png", 20, 57, 128, 128)
            else if (File("database/leaderboards/practice/${data.uuid}/head.png").exists())
                canvas.drawImage("database/leaderboards/practice/${data.uuid}/head.png", 20, 57, 128, 128)
            else
                canvas.drawImage(missingHead, 20, 57, 128, 128)
        } else {
            canvas.drawImage(downloadAvatar(user!!.member.effectiveAvatarUrl), 20, 57, 128, 128)
        }

        canvas.drawStringLeft(data.name, 158, 57, 36.0f, fontColor)
        if (user != null)
            canvas.drawStringLeft(user.subtitle, 158, 97, 30.0f, fontColor)

        var iconX = 158
        val increment = 55
        if (user != null) {
            if (user.isDeveloper) {canvas.drawImage(iconDev, iconX, 135, 50, 50); iconX += increment}
            if (user.patreonTier >= 1) {canvas.drawImage(iconTier1, iconX, 135, 50, 50); iconX += increment}
            if (user.patreonTier >= 2) {canvas.drawImage(iconTier2, iconX, 135, 50, 50); iconX += increment}
            if (user.patreonTier >= 3) {canvas.drawImage(iconTier3, iconX, 135, 50, 50); iconX += increment}
        }
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

        if (data.overall.toInt() != 0) {
            canvas.drawImage(iconOverall, 490, 480, 50, 50)
            canvas.drawStringLeft(formatLine("Overall Rank", data.overall, data.overallPosition), 560, 495, 30.0f, fontColor)
        }

        canvas.save("stats.png")
    }
}