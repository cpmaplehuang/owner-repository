package com.example.unscramble.data


class RegisterUserResponse(val status: Int, val userData: UserData)


data class UserData(val memberNo: String,
                    var status: Int = 0,
                    var userInfo: UserInfo) {

    fun getUserInfoSummary() : String {
        return userInfo.summary()
    }

}

data class UserInfo(val userName: String, val phoneNum: String) {

    fun summary() : String {
        return "${callUserName()} -- ${callPhoneNum()}"
    }

    private fun callUserName() : String {
        return userName
    }

    private fun callPhoneNum() : String {
        return phoneNum
    }

}