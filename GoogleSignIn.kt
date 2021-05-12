package com.lethtanahachi.googlesignin

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.lethtanahachi.googlesignin.ui.theme.GoogleSignInTheme
import com.lethtanahachi.googlesignin.ui.theme.Logo
import com.lethtanahachi.googlesignin.ui.theme.Shapes
import com.lethtanahachi.googlesignin.ui.theme.Teal200

class MainActivity : ComponentActivity() {

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                /** TODO */
            }
        }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        resultLauncher.launch(GoogleSignIn.getClient(this, gso).signInIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleSignInTheme() {
                RegisterScreen() {
                    signIn()
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        //Logo(Teal200)
        Button(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.75f),
            border = BorderStroke(4.dp, Color.Gray),
            shape = Shapes.small.copy(CornerSize(30)),
            colors = ButtonDefaults.buttonColors(Teal200),
            onClick = onClick
        ) { Text(text = "GO", color = Color.White, fontSize = 60.sp) }
    }
}
