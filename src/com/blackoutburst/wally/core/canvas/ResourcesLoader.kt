package com.blackoutburst.wally.core.canvas

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object ResourcesLoader {

    val background = loadImage("res/background.png")
    val hypixelHeader = loadImage("res/hypixel_header.png")
    val iconChallenges = loadImage("res/icon_challenges.png")
    val iconDev = loadImage("res/icon_dev.png")
    val iconFinals = loadImage("res/icon_finals.png")
    val iconOverall = loadImage("res/icon_overall.png")
    val iconQualification = loadImage("res/icon_qualification.png")
    val iconTier1 = loadImage("res/icon_tier1.png")
    val iconTier2 = loadImage("res/icon_tier2.png")
    val iconTier3 = loadImage("res/icon_tier3.png")
    val iconTotal = loadImage("res/icon_total.png")
    val iconTotalApi = loadImage("res/icon_total_api.png")
    val iconWalls = loadImage("res/icon_walls.png")
    val iconWins = loadImage("res/icon_wins.png")
    val line = loadImage("res/line.png")
    val practiceHeader = loadImage("res/practice_header.png")
    val missingHead = loadImage("res/missing_head.png")

    val font = loadFont("res/font.ttf")

    private fun loadFont(filePath :String) :Font {
        Wally.logger.log(LogLevel.INFO, "Loaded resource [$filePath]")
        return Font.createFont(Font.TRUETYPE_FONT, File(filePath))
    }

    private fun loadImage(filePath :String) : BufferedImage {
        Wally.logger.log(LogLevel.INFO, "Loaded resource [$filePath]")
        return ImageIO.read(File(filePath))
    }

}