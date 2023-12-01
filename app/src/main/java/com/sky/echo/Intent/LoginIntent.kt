package com.sky.echo.Intent


/**
 * 用户操作意图
 */
sealed class LoginIntent {
    data class UpdateUsername(val username: String) : LoginIntent()
    data class UpdatePassword(val password: String) : LoginIntent()
    object Login : LoginIntent()

    object Logout : LoginIntent()
}