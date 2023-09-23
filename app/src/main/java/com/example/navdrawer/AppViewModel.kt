package com.example.navdrawer


import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    private var isLoggedIn = false

    fun setLoggedIn() {
        isLoggedIn = true
    }

    fun setLoggedOut() {
        isLoggedIn = false
    }

    fun isUserLoggedIn(): Boolean {
        return isLoggedIn
    }
}