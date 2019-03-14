package com.zhuanghongji.wan.base_common.manager

import android.content.Context
import android.util.DisplayMetrics

object DisplayManager {

    /** UI 图的宽度 */
    private const val STANDARD_WIDTH = 1080

    /** UI 图的高度 */
    private const val STANDARD_HEIGHT = 1920

    private var displayMetrics: DisplayMetrics? = null

    private var screenWidth: Int? = null

    private var screenHeight: Int? = null

    private var screenDpi: Int? = null

    fun init(context: Context) {
        displayMetrics = context.resources.displayMetrics
        screenWidth = displayMetrics?.widthPixels
        screenHeight = displayMetrics?.heightPixels
        screenDpi = displayMetrics?.densityDpi
    }


    fun getScreenWidth(): Int? {
        return screenWidth
    }

    fun getScreenHeight(): Int? {
        return screenHeight
    }


    /**
     * 传入 UI 图中问题的高度，单位像素
     *
     * @param size
     * @return
     */
    fun getPaintSize(size: Int): Int = getRealHeight(size)

    /**
     * 输入 UI 图的尺寸，输出实际的 px
     *
     * @param px ui图中的大小
     * @return
     */
    fun getRealWidth(px: Int): Int = getRealWidth(px, STANDARD_WIDTH.toFloat())

    /**
     * 输入 UI 图的尺寸，输出实际的 px，第二个参数是父布局
     *
     * @param px UI 图中的大小
     * @param parentWidth 父 View 在 UI 图中的高度
     * @return 实际的 px
     */
    fun getRealWidth(px: Int, parentWidth: Float): Int {
        return (px / parentWidth * getScreenWidth()!!).toInt()
    }

    /**
     * 输入 UI 图的尺寸，输出实际的px
     *
     * @param px UI 图中的大小
     * @return
     */
    fun getRealHeight(px: Int): Int = getRealHeight(px, STANDARD_HEIGHT.toFloat())

    /**
     * 输入 UI 图的尺寸，输出实际的 px，第二个参数是父布局
     *
     * @param px UI 图中的大小
     * @param parentHeight 父 View 在 UI 图中的高度
     * @return 出实际的 px
     */
    fun getRealHeight(px: Int, parentHeight: Float): Int {
        return (px / parentHeight * getScreenHeight()!!).toInt()
    }

    /**
     * dip 转 px
     * @param dipValue
     * @return int
     */
    fun dip2px(dipValue: Float): Int {
        val scale = displayMetrics?.density
        return (dipValue * scale!! + 0.5f).toInt()
    }
}