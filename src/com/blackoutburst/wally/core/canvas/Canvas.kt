package com.blackoutburst.wally.core.canvas

import java.awt.*
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO


class Canvas(width: Int, height: Int) {

    private var bufferedImage: BufferedImage? = null
    private var font: Font? = null
    private var g: Graphics2D? = null

    init {
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

    private fun tint(r: Float, g: Float, b: Float, a: Float, image: BufferedImage): BufferedImage {
        val tintedImage = BufferedImage(image.width, image.height, BufferedImage.TRANSLUCENT)
        val graphics = tintedImage.createGraphics()
        graphics.drawImage(image, 0, 0, null)
        graphics.dispose()

        for (x in 0 until tintedImage.width) {
            for (y in 0 until tintedImage.height) {
                val ax = tintedImage.colorModel.getAlpha(tintedImage.raster.getDataElements(x, y, null)).toFloat() * a
                val rx = tintedImage.colorModel.getRed(tintedImage.raster.getDataElements(x, y, null)).toFloat() * r
                val gx = tintedImage.colorModel.getGreen(tintedImage.raster.getDataElements(x, y, null)).toFloat() * g
                val bx = tintedImage.colorModel.getBlue(tintedImage.raster.getDataElements(x, y, null)).toFloat() * b
                tintedImage.setRGB(x, y, ax.toInt() shl 24 or (rx.toInt() shl 16) or (gx.toInt() shl 8) or bx.toInt())
            }
        }
        return tintedImage
    }

    fun drawImage(image: BufferedImage, x: Int, y: Int, width: Int, height: Int, color :Color) {
        val tintedImage = tint(color.red / 255.0f, color.green / 255.0f, color.blue / 255.0f, color.alpha / 255.0f, image)

        g!!.drawImage(tintedImage, x, y, width, height, null)
    }

    fun drawImageLeftHalf(image: BufferedImage, x: Int, y: Int, width: Int, height: Int, color :Color) {
        val tintedImage = tint(color.red / 255.0f, color.green / 255.0f, color.blue / 255.0f, color.alpha / 255.0f, image)

        g!!.drawImage(tintedImage.getSubimage(0, 0, image.width/2, image.height), x, y, width, height, null)
    }

    fun drawImageRightHalf(image: BufferedImage, x: Int, y: Int, width: Int, height: Int, color :Color) {
        val tintedImage = tint(color.red / 255.0f, color.green / 255.0f, color.blue / 255.0f, color.alpha / 255.0f, image)

        g!!.drawImage(tintedImage.getSubimage(image.width/2, 0, image.width/2, image.height), x, y, width, height, null)
    }

    fun drawImage(image: BufferedImage, x: Int, y: Int, width: Int, height: Int) {
        g!!.drawImage(image, x, y, width, height, null)
    }

    fun drawImageLeftHalf(image: BufferedImage, x: Int, y: Int, width: Int, height: Int) {
        g!!.drawImage(image.getSubimage(0, 0, image.width/2, image.height), x, y, width, height, null)
    }

    fun drawImageRightHalf(image: BufferedImage, x: Int, y: Int, width: Int, height: Int) {
        g!!.drawImage(image.getSubimage(image.width/2, 0, image.width/2, image.height), x, y, width, height, null)
    }

    fun drawImage(file: String, x: Int, y: Int, width: Int, height: Int) {
        try {
            val image = ImageIO.read(File(file))
            g!!.drawImage(image, x, y, width, height, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun drawImageLeftHalf(file: String, x: Int, y: Int, width: Int, height: Int) {
        try {
            val image = ImageIO.read(File(file))

            g!!.drawImage(image.getSubimage(0, 0, image.width/2, image.height), x, y, width, height, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun drawImageRightHalf(file: String, x: Int, y: Int, width: Int, height: Int) {
        try {
            val image = ImageIO.read(File(file))

            g!!.drawImage(image.getSubimage(image.width/2, 0, image.width/2, image.height), x, y, width, height, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun drawStringLeft(string: String, x: Int, y: Int, size: Float, color: Color) {
        font = font!!.deriveFont(size)
        g!!.font = font
        g!!.color = color.darker().darker().darker()
        g!!.drawString(string, x + 2, y + g!!.fontMetrics.height / 2 + 2)
        g!!.color = color
        g!!.drawString(string, x, y + g!!.fontMetrics.height / 2)
    }

    fun drawStringCenter(string: String, x: Int, y: Int, size: Float, color: Color) {
        font = font!!.deriveFont(size)
        g!!.font = font
        g!!.color = color.darker().darker().darker()
        g!!.drawString(string, (x - g!!.fontMetrics.stringWidth(string) / 2) + 2, y + g!!.fontMetrics.height / 2 + 2)
        g!!.color = color
        g!!.drawString(string, x - g!!.fontMetrics.stringWidth(string) / 2, y + g!!.fontMetrics.height / 2)
    }

    fun drawStringRight(string: String, x: Int, y: Int, size: Float, color: Color) {
        font = font!!.deriveFont(size)
        g!!.font = font
        g!!.color = color.darker().darker().darker()
        g!!.drawString(string, (x - g!!.fontMetrics.stringWidth(string)) + 2, y + g!!.fontMetrics.height / 2 + 2)
        g!!.color = color
        g!!.drawString(string, x - g!!.fontMetrics.stringWidth(string), y + g!!.fontMetrics.height / 2)
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