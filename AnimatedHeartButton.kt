import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun AnimatedHeartButton(
) {
    var visibleRed by remember { mutableStateOf(false) }
    var visibleGray by remember { mutableStateOf(true) }

    val toBigSize by animateDpAsState(
        if (visibleGray) 200.dp else 0.dp,
        animationSpec = keyframes {
            durationMillis = 100
            300.dp at 0
        }
    )

    val toSmallSize by animateDpAsState(
        if (!visibleRed) 0.dp else 200.dp,
        animationSpec = keyframes {
            durationMillis = 100
            300.dp at 0
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(300.dp)
            .aspectRatio(1f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.heart_red),
            contentDescription = "Red Heart",
            modifier = Modifier
                .size(toBigSize)
                .clickable {
                    visibleGray = !visibleGray
                    visibleRed = !visibleRed
                }
        )
        Image(
            painter = painterResource(id = R.drawable.heart_gray),
            contentDescription = "Gray Heart",
            modifier = Modifier
                .size(toSmallSize)
                .clickable {
                    visibleGray = !visibleGray
                    visibleRed = !visibleRed
                }
        )
    }
}

@ExperimentalAnimationApi
@Composable
@Preview (showBackground = true)
fun HeartPreview() {
    AnimatedHeartButton()
}
