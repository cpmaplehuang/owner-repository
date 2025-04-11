package com.example.unscramble.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.RegisterUserResponse
import com.example.unscramble.service.ServiceCallback
import com.example.unscramble.service.UserService

class RegisterViewModel : ViewModel() {


    var userName = MutableLiveData<String>()
    var userPhone = MutableLiveData<String>()


    fun callRegisterApi(phone: String, pwd: String) {
        UserService.callRegisterApi(phone, pwd, object : ServiceCallback<RegisterUserResponse> {
            override fun onResult(data: RegisterUserResponse, e: Throwable?) {
                if (data.status == 0) {
                    userName.value = data.userData.userInfo?.userName
                }
            }
        })
    }


    fun callRegisterApiWithThread(phone: String, pwd: String) {
        UserService.callRegisterApiWithThread(phone, pwd, object : ServiceCallback<RegisterUserResponse> {
            override fun onResult(data: RegisterUserResponse, e: Throwable?) {
                if (data.status == 0) {
                    userPhone.value = data.userData.userInfo?.phoneNum
                }
            }
        })
    }

}