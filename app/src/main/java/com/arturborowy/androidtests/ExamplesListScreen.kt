package com.arturborowy.androidtests

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
fun ExamplesListScreen(backStack: NavBackStack<NavKey>) {
    Column() {
        Button(onClick = { backStack.add(SharedTransitionLayoutExample) }) {
            Text("SharedTransitionLayoutExample")
        }
    }
}
