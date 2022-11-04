package com.example.grocerydelivery.ui.login_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginFormViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is LoginForm Fragment"
    }
    val text: LiveData<String> = _text
}