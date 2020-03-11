package com.ahnbcilab.tremorquantification.data

import com.google.firebase.auth.FirebaseUser

class CurrentUserData {
    companion object {
        var user: FirebaseUser? = null

        val isLogin = { user != null }
    }
}