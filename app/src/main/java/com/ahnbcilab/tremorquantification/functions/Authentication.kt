package com.ahnbcilab.tremorquantification.functions

import com.ahnbcilab.tremorquantification.data.CurrentUserData
import com.google.firebase.auth.FirebaseAuth

class Authentication {
    companion object {
        /*
        var uid: String? = null
        var name: String? = null
        var email: String? = null
        var failException: Exception? = null

        fun signIn(mAuth: FirebaseAuth, email: String, password: String) : Boolean {
            var isSuccess = false

            val task = mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    isSuccess = true
                    CurrentUserData.user = mAuth.currentUser
                } else {
                    isSuccess = false
                    failException = it.exception
                    CurrentUserData.user = null
                }
            }
            return isSuccess
        }

        fun signUp(mAuth: FirebaseAuth, email: String, password: String) : Boolean {
            var isSuccess = false
            val result = mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    CurrentUserData.user = mAuth.currentUser
                }
                else {
                    CurrentUserData.user = null
                    failException = it.exception
                }
            }.result
            isSuccess = (result != null)
            return isSuccess
        }

        */

        fun signOut(mAuth: FirebaseAuth) {
            mAuth.signOut()
            CurrentUserData.user = null
        }
    }
}