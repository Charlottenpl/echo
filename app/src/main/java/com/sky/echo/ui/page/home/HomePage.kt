package com.sky.echo.ui.page.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun HomePage(user: String = "1234"){
    
    Text(text = "Hello $user")

}