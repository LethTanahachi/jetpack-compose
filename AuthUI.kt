import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private val resultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
            }
        }

    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build(),
        )
        resultContract.launch(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
        )
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        val firebaseUser = Firebase.auth.currentUser
        super.onCreate(savedInstanceState)
        if (firebaseUser != null) {
            setContent {
                Theme {
                    val navController = rememberNavController()
                    NavHost(navController, "StartDestination") {
                        composable("StartDestination") { StartDestination(navController) }
                        composable("LeftScreen") { LeftScreen(navController) }
                        composable("RightScreen") { RightScreen(navController) }
                        composable("UpScreen") { UpScreen(navController) }
                        composable("DownScreen") { DownScreen(navController) }
                    }
                }
            }
        } else
            setContent {
                Theme {
                    RegisterScreen() {
                        launchSignInFlow()
                    }
                }
            }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun RegisterScreen(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        //Logo(Teal200)
        Button(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.75f),
            border = BorderStroke(4.dp, Gray),
            shape = Shapes.small.copy(CornerSize(30)),
            colors = ButtonDefaults.buttonColors(Teal200),
            onClick = onClick
        ) { Text(text = "GO", color = White, fontSize = 60.sp) }
    }
}

@ExperimentalMaterialApi
@Composable
fun StartDestination(
    navController: NavController,
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow)
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                    when {
                        offsetX > 0 -> navController.navigate("LeftScreen")
                        offsetX < 0 -> navController.navigate("RightScreen")
                    }
                }
            )
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    offsetY += delta
                    when {
                        offsetY > 0 -> navController.navigate("UpScreen")
                        offsetY < 0 -> navController.navigate("DownScreen")
                    }
                }
            )
    ) {
        Text("StartDestination")
    }
}

@ExperimentalMaterialApi
@Composable
fun LeftScreen(navController: NavController) {
    var offsetX by remember { mutableStateOf(0f) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Magenta)
            .offset { IntOffset(offsetX.toInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                    when {
                        offsetX < 0 -> navController.navigate("StartDestination")
                    }
                }
            )
    ) {
        Text("LeftScreen")
    }
}

@ExperimentalMaterialApi
@Composable
fun RightScreen(navController: NavController) {
    var offsetX by remember { mutableStateOf(0f) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Green)
            .offset { IntOffset(offsetX.toInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                    when {
                        offsetX > 0 -> navController.navigate("StartDestination")
                    }
                }
            )
    ) {
        Text("RightScreen")
    }
}

@ExperimentalMaterialApi
@Composable
fun UpScreen(navController: NavController) {
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Red)
            .offset { IntOffset(0, offsetY.toInt()) }
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    offsetY += delta
                    when {
                        offsetY < 0 -> navController.navigate("StartDestination")
                    }
                }
            )
    ) {
        Text("UpScreen")
    }
}

@ExperimentalMaterialApi
@Composable
fun DownScreen(navController: NavController) {
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Orange)
            .offset { IntOffset(0, offsetY.toInt()) }
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    offsetY += delta
                    when {
                        offsetY > 0 -> navController.navigate("StartDestination")
                    }
                }
            )
    ) {
        Text("DownScreen")
    }
}
