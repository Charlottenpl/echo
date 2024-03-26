package com.sky.echo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sms.manager.util.Logger
import com.sky.echo.Intent.LoginIntent
import com.sky.echo.common.key_userId
import com.sky.echo.model.LoginState
import com.sky.echo.util.DataUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableLiveData<LoginState>(LoginState())
    val state: LiveData<LoginState> = _state


    fun processIntent(intent: LoginIntent){
        when(intent){
            is LoginIntent.UpdateUsername -> updateUserName(intent.username)
            is LoginIntent.UpdatePassword -> updatePassword(intent.password)
            is LoginIntent.Login -> login()
            is LoginIntent.Logout -> logout()
            is LoginIntent.CheckLoginStatus -> checkLoginStatus(intent.isLogin, intent.isNotLogin)
        }
    }

    private fun checkLoginStatus(isLogin: (name: String) -> Unit, isNotLogin: ()->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = DataUtil.getString(key_userId)
            Logger.i("userId save: $userId")
            viewModelScope.launch(Dispatchers.Main) {
                Logger.i("userId is empty ?: $userId")
                if (userId.isNotEmpty()){
                    _state.value = _state.value?.copy(loginState = true)
                    _state.value = _state.value?.copy(username = userId)
                    isLogin(userId)
                }else{
                    //todo 跳转到login
                    isNotLogin()
                }
            }
        }
    }

    private fun logout() {
        _state.value = _state.value?.copy(username = "", loginState = false)
        viewModelScope.launch(Dispatchers.IO) {
            DataUtil.putString(key_userId, "")
        }
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
        viewModelScope.launch {
            DataUtil.putString(key_userId, username?:"Guest")
            Logger.e("loginState: $username" )
        }
    }




}