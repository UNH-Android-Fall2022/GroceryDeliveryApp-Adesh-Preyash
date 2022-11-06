package com.example.grocerydelivery.ui.register_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterFormViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is RegisterForm Fragment"
    }
    val text: LiveData<String> = _text
}