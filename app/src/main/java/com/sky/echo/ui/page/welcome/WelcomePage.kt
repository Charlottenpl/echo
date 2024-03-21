package com.sky.echo.ui.page.welcome

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.netease.nis.quicklogin.listener.QuickLoginTokenListener
import com.sky.quick_login.QuickLoginManager


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun view(){
    WelcomePage(toLogin = { /*TODO*/ }, nav = rememberNavController())
}

@Composable
fun WelcomePage(toLogin : ()->Unit, nav: NavController){
    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ){
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            var btns = mapOf<String, ()->Unit>(
                "Guest Login" to toLogin,
                "Quick Login" to {
                    QuickLoginManager.login(object : QuickLoginTokenListener() {
                        override fun onGetTokenSuccess(p0: String?, p1: String?) {
                            Log.e("QuickLogin", "onGetTokenSuccess: $p0 : $p1", )
                        }

                        override fun onGetTokenError(p0: String?, p1: String?) {
                            Log.e("QuickLogin", "onGetTokenError: $p0 : $p1", )
                        }

                    })
                }
            )


            for (btn in btns){
                Button(
                    onClick = btn.value,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.7f)
                ) {
                    Text(btn.key)
                }
            }
        }

    }

}