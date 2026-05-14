package com.arturborowy.androidtests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.arturborowy.androidtests.animations.AnimatedContentExample
import com.arturborowy.androidtests.animations.AnimatedVisibilityExample
import kotlinx.serialization.Serializable

class AnimationExamplesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimationExamplesScreen()
        }
    }
}

@PreviewScreenSizes
@Composable
fun AnimationExamplesScreen() {
    val backStack = rememberNavBackStack(ScreenList)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<ScreenList> {
                ExamplesListScreen(backStack)
            }
            entry<SharedTransitionLayoutExample> {

            }
            entry<AnimationExamples> {

            }
        }
    )
}

@Composable
fun AnimationsExampleScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            AnimatedVisibilityExample()

            AnimatedContentExample()
        }
    }
}

@Serializable
data object ScreenList : NavKey

@Serializable
data object SharedTransitionLayoutExample : NavKey

@Serializable
data object AnimationExamples : NavKey
