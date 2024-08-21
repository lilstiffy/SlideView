package com.lilstiffy.slideview

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/**
 * A composable function that creates a slideable view with customizable text and colors.
 *
 * @param modifier The modifier to be applied to the SlideView.
 * @param text The text to be displayed inside the SlideView.
 * @param config The configuration for the colors of the SlideView.
 * @param completionDelay The delay in milliseconds before the onSlideDone callback is invoked after the slide is completed.
 * @param hapticsEnabled A boolean flag to enable or disable haptic feedback.
 * @param onSlideDone A callback function to be invoked when the slide is completed.
 *
 * @author lilstiffy
 */
@Composable
fun SlideView(
    modifier: Modifier = Modifier,
    text: String = "",
    config: SlideViewConfig = SlideViewConfig(),
    completionDelay: Long = 0L,
    hapticsEnabled: Boolean = true,
    onSlideDone: () -> Unit,
) {
    val view = LocalView.current
    val horizontalOffset = 16.dp.value
    val tolerance = 16.dp.value

    var offsetX by remember { mutableFloatStateOf(horizontalOffset) }
    var boxWidth by remember { mutableFloatStateOf(100f) }
    var thumbWidth by remember { mutableFloatStateOf(0f) }
    var isSliding by remember { mutableStateOf(false) }
    var slideCompleted by remember { mutableStateOf(false) }

    // Calculate the width available for sliding the thumb
    val slideableWidth = boxWidth - thumbWidth - horizontalOffset

    // Animate the horizontal offset of the thumb
    val animatedOffsetX by animateFloatAsState(
        targetValue = if (offsetX >= slideableWidth) slideableWidth else offsetX,
        animationSpec = tween(durationMillis = if (!isSliding) 350 else 0), label = "animation"
    )

    // State to control the opacity of the text
    var textOpacity by remember { mutableFloatStateOf(1f) }
    // Function to measure and update the text opacity based on the thumb's position
    fun measureTextOpacity() {
        textOpacity = (1 - (animatedOffsetX / (slideableWidth * 0.85f))).coerceIn(0f, 1f)
    }

    // Effect to measure text opacity and handle slide completion
    LaunchedEffect(animatedOffsetX) {
        measureTextOpacity()
        if (animatedOffsetX >= (slideableWidth - tolerance) && !slideCompleted) {
            slideCompleted = true
            if (hapticsEnabled) {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            }
            delay(completionDelay)
            onSlideDone()
        }
    }

    // Effect to measure text opacity when box or thumb width changes
    LaunchedEffect(boxWidth, thumbWidth) {
        measureTextOpacity()
    }

    // Main container for the slide view
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(config.backgroundColor)
            .onGloballyPositioned { boxWidth = it.size.width.toFloat() },
        contentAlignment = Alignment.CenterStart
    ) {
        // Text displayed inside the slide view
        Text(
            text = text,
            color = config.textColor.copy(alpha = textOpacity),
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        )

        // Thumb that can be dragged horizontally
        Box(
            modifier = Modifier
                .size(56.dp)
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .clip(CircleShape)
                .background(config.thumbColor)
                .draggable(
                    orientation = Orientation.Horizontal,
                    enabled = !slideCompleted,
                    onDragStarted = {
                        isSliding = true
                        if (hapticsEnabled) {
                            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS)
                        }
                    },
                    onDragStopped = {
                        isSliding = false
                        offsetX =
                            if (offsetX >= slideableWidth || slideCompleted) slideableWidth else horizontalOffset
                    },
                    state = rememberDraggableState { delta ->
                        if (offsetX + delta in 0.0..slideableWidth.toDouble()) {
                            offsetX += delta
                        }
                    }
                )
                .onGloballyPositioned { thumbWidth = it.size.width.toFloat() },
            contentAlignment = Alignment.Center
        ) {
            // Icon displayed inside the thumb
            Icon(
                imageVector = if (slideCompleted) Icons.Outlined.Check else Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = "thumb icon",
                tint = config.iconColor
            )
        }
    }
}