package com.sky.echo.model

/**
 * 存放Page中所有的变量和状态
 */
data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val loginState: Boolean = false
)
