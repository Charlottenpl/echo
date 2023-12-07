package com.sky.echo.ui.page.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.sky.echo.Intent.LoginIntent
import com.sky.echo.viewmodel.LoginViewModel

class LoginViewModelDef : PreviewParameterProvider<LoginViewModel> {
    override val values: Sequence<LoginViewModel>
        get() = listOf<LoginViewModel>(LoginViewModel()).asSequence()
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun LoginPage(@PreviewParameter(LoginViewModelDef::class, 1) viewModel: LoginViewModel, toHome: ((String?) -> Unit) = {}) {
    val state by viewModel.state.observeAsState()


    Box (modifier = Modifier.fillMaxSize()){
        Column (
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(
                value = state?.username?:"",
                onValueChange = {
                    Log.e("username", "LoginPage: $it")
                    viewModel.processIntent(LoginIntent.UpdateUsername(it))
                },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(0.77f)
            )
            TextField(
                value = state?.password?:"",
                onValueChange = { viewModel.processIntent(LoginIntent.UpdatePassword(it)) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(0.77f)
            )
            Button(
                onClick = { viewModel.processIntent(LoginIntent.Login) },
                modifier = Modifier.fillMaxWidth(0.77f).padding(0.dp,10.dp,0.dp,0.dp)
            ) {
                Text("Login ${state?.loginState}")
            }


            Button(
                onClick = { /*TODO 注册逻辑*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // 设置按钮背景为透明
                    disabledContainerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = "or you want register")
            }
        }
    }

    // 当登录状态变为 true 时，只执行一次导航
    LaunchedEffect(state?.loginState) {
        if (state?.loginState == true) {
            toHome(state?.username)
        }
    }
}