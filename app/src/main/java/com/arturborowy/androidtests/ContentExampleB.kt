package com.arturborowy.androidtests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ContentExampleB() {
    Column {
        Text(
            "ContentExampleB",
            Modifier
                .background(Color.Red, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .fillMaxWidth()
                .padding(16.dp)
        )
        Text(
            "Body Body Body Body Body Body Body Body Body Body Body Body Body Body Body Body Body" +
                    "Body Body Body Body Body Body Body Body Body Body Body Body Body Body Body" +
                    "Body Body Body Body Body Body Body Body Body Body Body Body Body Body Body",
            Modifier
                .background(
                    Color.White,
                    RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
