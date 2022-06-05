package com.blackoutburst.wally.core.canvas

import java.awt.*
import java.awt.image.BufferedImage
import java.awt.image.RescaleOp
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO


class Canvas {

    private var bufferedImage: BufferedImage? = null
    private var font: Font? = null
    private var g: Graphics2D? = null

    private var width = 0
    private var height = 0

    fun Canvas(width: Int, height: Int) {
        this.width = width
        this.height = height
        font = ResourcesLoader.font
        bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        g = bufferedImage!!.createGraphics()
        g!!.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g!!.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    }

    fun save(fileName: String) {
        val file = File(fileName)
        ImageIO.write(bufferedImage, "png", file)
        g!!.dispose()
    }

    fun drawImageFromMemory(image: BufferedImage?, x: Int, y: Int, width: Int, height: Int) {
        g!!.drawImage(image, x, y, width, height, null)
    }

    fun drawImage(file: String, x: Int, y: Int, width: Int, height: Int) {
        try {
            val image = ImageIO.read(File(file))
            g!!.drawImage(image, x, y, width, height, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun drawStringLeft(string: String, x: Int, y: Int, size: Float, color: Color?) {
        font = font!!.deriveFont(size)
        g!!.font = font
        g!!.color = color
        g!!.drawString(string, x, y)
    }

    fun drawStringCenter(string: String, x: Int, y: Int, size: Float, color: Color?) {
        font = font!!.deriveFont(size)
        g!!.font = font
        g!!.color = color
        g!!.drawString(string, x - g!!.fontMetrics.stringWidth(string) / 2, y)
    }

    fun drawStringRight(string: String, x: Int, y: Int, size: Float, color: Color?) {
        font = font!!.deriveFont(size)
        g!!.font = font
        g!!.color = color
        g!!.drawString(string, x - g!!.fontMetrics.stringWidth(string), y)
    }

    fun loadFont(file: String) {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, File(file))
        } catch (e: FontFormatException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}