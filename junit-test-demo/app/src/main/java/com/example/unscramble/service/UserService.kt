package com.example.unscramble.service

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
@ExcludeFromReport
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

    }

}