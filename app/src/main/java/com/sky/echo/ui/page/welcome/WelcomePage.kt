package com.sky.echo.ui.page.welcome

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun WelcomePage(toLogin : ()->Unit){
    Button(onClick = toLogin) {
        Text("Go to Login")
    }
}