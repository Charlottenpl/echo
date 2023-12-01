package com.sky.echo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sky.echo.common.Route
import com.sky.echo.ui.page.home.HomePage
import com.sky.echo.ui.page.login.LoginPage
import com.sky.echo.ui.page.welcome.WelcomePage
import com.sky.echo.ui.theme.EchoTheme
import com.sky.echo.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EchoTheme {
                AppNav()
            }
        }
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
            WelcomePage(toLogin = { navController.navigate(Route.login) })
        }

        composable(Route.login){
            LoginPage(viewModel = loginViewModel, toHome = {user ->
                navController.navigate("${Route.home}/$user"){
                    /** Route.welcome表示移除welcomePage以上的所有页面包括loginPage */
                    popUpTo(Route.welcome) { inclusive = true }
                    launchSingleTop = true
                }
            })
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
