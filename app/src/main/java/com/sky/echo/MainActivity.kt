package com.sky.echo

import android.os.Bundle
import android.util.Log
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
import com.example.sms.manager.Mobile
import com.example.sms.manager.permission.PermissionManager
import com.example.sms.manager.sms.SmsManager
import com.example.sms.manager.util.Logger
import com.netease.nis.quicklogin.listener.QuickLoginTokenListener
import com.sky.echo.common.Route
import com.sky.echo.ui.page.home.HomePage
import com.sky.echo.ui.page.login.LoginPage
import com.sky.echo.ui.page.welcome.StartPagePreview
import com.sky.echo.ui.page.welcome.WelcomePage
import com.sky.echo.ui.theme.EchoTheme
import com.sky.echo.util.FileUtil
import com.sky.echo.viewmodel.LoginViewModel
import com.sky.quick_login.QuickLoginManager


class MainActivity : ComponentActivity() {


    /** ----------------------- 生命周期函数 ---------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(this)
        setContent {
            EchoTheme {
                AppNav()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Mobile.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }


    private fun init(activity: ComponentActivity){
        FileUtil.init(activity)
        Mobile.init(activity)
        Mobile.setSMSCallback {
            Logger.e("new sms: $it")
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


    NavHost(navController = navController, startDestination = Route.start){
        composable(Route.start){
            StartPagePreview(nav = navController)
        }

        composable(Route.welcome){
            WelcomePage(nav = navController, toLogin = {
                navController.navigate(Route.login)
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
