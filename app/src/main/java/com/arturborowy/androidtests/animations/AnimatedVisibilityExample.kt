package com.arturborowy.androidtests.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.androidtests.ContentExampleA

@Composable
fun AnimatedVisibilityExample() {
    var isVisible by remember { mutableStateOf(true) }
    AnimatedVisibility(isVisible) {
        ContentExampleA()
    }

    Spacer(Modifier.height(16.dp))

    Button(onClick = { isVisible = isVisible.not() }) {
        Text("AnimatedVisibilityExample")
    }
}

@Preview
@Composable
private fun AnimatedVisibilityExamplePreview() {
    Column {
        AnimatedVisibilityExample()
    }
}
