package com.sky.echo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.netease.nis.quicklogin.listener.QuickLoginTokenListener
import com.sky.echo.common.Route
import com.sky.echo.ui.page.home.HomePage
import com.sky.echo.ui.page.login.LoginPage
import com.sky.echo.ui.page.welcome.WelcomePage
import com.sky.echo.ui.theme.EchoTheme
import com.sky.echo.util.FileUtil
import com.sky.echo.viewmodel.LoginViewModel
import com.sky.quick_login.QuickLoginManager


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(applicationContext);
        setContent {
            EchoTheme {
                AppNav()
            }
        }
    }


    fun init(context: Context){
        FileUtil.init(context)
    }
}


@Composable
private fun AppNav(){
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            Log.d("NavigationLog", "当前路径: ${destination.route}  当前参数: ${arguments.toString()}")
        }
        navController.addOnDestinationChangedListener(listener)

        // 当 Composable 被销毁时移除监听器
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }


    NavHost(navController = navController, startDestination = Route.welcome){
        composable(Route.welcome){
            WelcomePage(toLogin = {
//                navController.navigate(Route.login)
                QuickLoginManager.login(object : QuickLoginTokenListener() {
                    override fun onGetTokenSuccess(p0: String?, p1: String?) {
                        Log.e("QuickLogin", "onGetTokenSuccess: $p0 : $p1", )
                    }

                    override fun onGetTokenError(p0: String?, p1: String?) {
                        Log.e("QuickLogin", "onGetTokenError: $p0 : $p1", )
                    }

                })
            })
        }

        composable(Route.login){
            LoginPage(viewModel = loginViewModel) { user ->
                navController.navigate("${Route.home}/${if (user.isNullOrEmpty())"Guest" else user}") {
                    /** Route.welcome表示移除welcomePage以上的所有页面包括loginPage */
                    popUpTo(Route.welcome) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }


        composable("${Route.home}/{${Route.home_user}}", arguments = listOf(
            navArgument(Route.home_user){
                type = NavType.StringType
                defaultValue = "Guest"
            }
        )){ params ->
            HomePage(params.arguments?.getString(Route.home_user)!!)
        }

    }
}
