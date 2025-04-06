package com.example.unscramble.data

import com.example.unscramble.kover.ExcludeFromReport


class RegisterUserResponse(val status: Int, val userData: UserData)

/**
 * 這個個地方有Issue，在Kover Report 中 memberNo 一直沒辦法計算到覆蓋率，但是如果換成
 * data class UserData(val memberNo: String, var status: Int = 0, var userInfo: UserInfo? = null)
 * 這樣子的一行就能計算到覆蓋率
 */
data class UserData(val memberNo: String,
                    var status: Int = 0, var userInfo: UserInfo? = null) {

    fun getUserInfoSummary() : String? {
        return userInfo?.summary()
    }

}

data class UserInfo(val userName: String, val phoneNum: String) {

    fun summary() : String {
        return "${callUserName()} -- ${callPhoneNum()}"
    }

    private fun callUserName() : String {
        return userName
    }

    @ExcludeFromReport
    private fun callPhoneNum() : String {
        return phoneNum
    }

}