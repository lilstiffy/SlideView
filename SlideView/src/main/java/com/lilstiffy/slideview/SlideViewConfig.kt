package com.lilstiffy.slideview

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Check
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Configuration for the colors of the SwipeView.
 *
 * @param backgroundColor The background color of the SwipeView.
 * @param thumbColor The color of the thumb icon.
 * @param iconColor The color of the icon.
 * @param textColor The color of the text.
 * @param thumbIcon The icon to be displayed on the thumb.
 * @param thumbIconDone The icon to be displayed on the thumb when the swipe is done.
 *
 * @author lilstiffy
 */
data class SlideViewConfig(
    val backgroundColor: Color = Color.Black,
    val thumbColor: Color = Color.White,
    val iconColor: Color = Color.Black,
    val textColor: Color = Color.White,

    val thumbIcon: ImageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
    val thumbIconDone:  ImageVector = Icons.Outlined.Check,
)
