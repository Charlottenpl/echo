package com.sky.echo.ui.page.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sky.echo.Intent.LoginIntent
import com.sky.echo.model.LoginState
import com.sky.echo.viewmodel.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(viewModel: LoginViewModel, toHome: (String) -> Unit) {
    val state by viewModel.state.observeAsState()


    Column {
        TextField(
            value = state?.username?:"",
            onValueChange = {
                Log.e("username", "LoginPage: $it")
                viewModel.processIntent(LoginIntent.UpdateUsername(it))
            },
            label = { Text("Username") }
        )
        TextField(
            value = state?.password?:"",
            onValueChange = { viewModel.processIntent(LoginIntent.UpdatePassword(it)) },
            label = { Text("Password") }
        )
        Button(onClick = { viewModel.processIntent(LoginIntent.Login) }) {
            Text("Login")
        }
    }

    if (state?.loginState == true) {
        LaunchedEffect(Unit) {
            toHome("charlotte")
        }
    }
}