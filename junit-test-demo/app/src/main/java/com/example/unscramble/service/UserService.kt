package com.example.unscramble.service

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.unscramble.data.RegisterUserResponse
import com.example.unscramble.data.UserData
import com.example.unscramble.data.UserInfo
import com.example.unscramble.kover.ExcludeFromReport
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


interface ServiceCallback<T> {
    fun onResult(data: T, e: Throwable?)
}

class UserService {

    companion object {

        fun callRegisterApi(phone: String, pwd: String, callback: ServiceCallback<RegisterUserResponse>) {
            MainScope().launch {
                delay(2000L)
                callback.onResult(
                    RegisterUserResponse(0,
                    UserData("12345", 0, UserInfo("li", phone))), null)
            }
        }


        fun callRegisterApiWithThread(phone: String, pwd: String, callback: ServiceCallback<RegisterUserResponse>) {
            val runnable = Runnable {
                Log.v("callRegisterApiWithThread", "callRegisterApiWithThread is Main: " + Looper.getMainLooper().isCurrentThread)
                Thread.sleep(3000L)
                Handler(Looper.getMainLooper()).post {
                    callback.onResult(
                        RegisterUserResponse(
                            0,
                            UserData("12345", 0, UserInfo("li", phone))
                        ), null
                    )
                }
            }

            Thread(runnable).start()
        }
    }

}