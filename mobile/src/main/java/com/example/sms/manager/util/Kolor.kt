package com.example.sms.manager.util

import com.example.sms.manager.util.Kolor.ESCAPE

/**
 * 自定义颜色控制台文本
 *
 * 原理：ANSI 转义码
 * 不知道为啥不生效
 * 可能是因为windows不支持，需要安装插件
 * 参考blog：https://www.cnblogs.com/naiij/p/9772584.html
 */
object Kolor {
    internal const val ESCAPE = '\u001B'
    internal const val RESET = "$ESCAPE[0m"

    /**
     * Create a string that will be printed with the specified color as foreground
     * @param string The string to color
     * @param color The color to use
     * @return The colored string
     */
    fun foreground(string: String, color: Color) = Kolor.color(string, color.foreground)

    /**
     * Create a string that will be printed with the specified color as background
     * @param string The string to color
     * @param color The color to use
     * @return The colored string
     */
    fun background(string: String, color: Color) = Kolor.color(string, color.background)

    private fun color(string: String, ansiString: String) = "$ansiString$string$RESET"
}

/**
 * The amount of codes required in order to jump from a foreground code to a background code. Equal to 10. For example,
 * the foreground code for blue is "[33m", its respective background code is "[43m"
 */
private const val BG_JUMP = 10

/**
 * An enumeration of colors supported by most terminals. Can be applied to both foreground and background.
 */
enum class Color(baseCode: Int) {
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    MAGENTA(35),
    CYAN(36),
    LIGHT_GRAY(37),

    DARK_GRAY(90),
    LIGHT_RED(91),
    LIGHT_GREEN(92),
    LIGHT_YELLOW(93),
    LIGHT_BLUE(94),
    LIGHT_MAGENTA(95),
    LIGHT_CYAN(96),
    WHITE(97);

    /** ANSI modifier string to apply the color to the text itself */
    val foreground: String = "$ESCAPE[${baseCode}m"

    /** ANSI modifier string to apply the color the text's background */
    val background: String = "$ESCAPE[${baseCode + BG_JUMP}m"
}