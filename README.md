# SlideView

SlideView is a highly customizable UI component for Jetpack Compose that allows you to create slide-to-trigger actions in your Android apps. It's perfect for implementing features like slide-to-unlock or slide-to-confirm actions.

<p align="center">
  <img src="./demo_video.gif" alt="Demo">
</p>

### All support is greatly appreciated
https://buymeacoffee.com/lilstiffy

## Features

- **Customizable:** Easily modify the appearance and behavior.
- **Jetpack Compose:** Built with modern Android UI toolkit.
- **Lightweight:** Minimal dependencies for easy integration.

## Installation

Add the following to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.lilstiffy:slideview:1.0.0")
}
```

## Usage
Here's a basic example:
```kotlin
SlideView(
    onSlideComplete = { /* Your action here */ },
    modifier = Modifier.fillMaxWidth()
)
```
Here's how to customise the theming of the component:
```kotlin
SlideView(
    text = "Slide to confirm",
    onSlideDone = {
        Log.d("Fancy log", "Slide completed!")
    },
    config = SlideViewConfig().copy(backgroundColor = Color.Cyan, thumbColor = Color.Black),
    modifier = Modifier.padding(horizontal = 32.dp)
)
```
The theming is held within the SlideViewConfig object:
```kotlin
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
```

## License
This project is licensed under the MIT License - see the LICENSE file for details.
