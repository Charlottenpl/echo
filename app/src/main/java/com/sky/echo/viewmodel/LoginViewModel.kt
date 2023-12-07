package com.sky.echo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sky.echo.Intent.LoginIntent
import com.sky.echo.model.LoginState

class LoginViewModel : ViewModel() {
    private val _state = MutableLiveData<LoginState>(LoginState())
    val state: LiveData<LoginState> = _state


    fun processIntent(intent: LoginIntent){
        when(intent){
            is LoginIntent.UpdateUsername -> updateUserName(intent.username)
            is LoginIntent.UpdatePassword -> updatePassword(intent.password)
            is LoginIntent.Login -> login()
            is LoginIntent.Logout -> logout()

        }
    }

    private fun logout() {
        _state.value = _state.value?.copy(username = "", loginState = false)
    }

    private fun updateUserName(username: String) {
        _state.value = _state.value?.copy(username = username)
    }


    private fun updatePassword(password: String) {
        _state.value = _state.value?.copy(password = password)
    }

    private fun login(username: String? = _state.value?.username, password: String? = _state.value?.password){
        //TODO 登录操作
        _state.value = _state.value?.copy(loginState = true)
        Log.e("login", "loginState: ${_state.value?.loginState}", )
    }




}