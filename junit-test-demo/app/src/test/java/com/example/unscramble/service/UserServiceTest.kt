package com.example.unscramble.service

import com.example.unscramble.data.RegisterUserResponse
import junit.extension.MainScopeExtension
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


/**
 * 測試基于Coroutine的異步Case
 * Coroutine，依賴於kotlinx.coroutines.test，版本要1.6+
 * **/

/**
 * 要測試Coroutine相關，一定要用到MainCoroutineExtension
 * **/
@ExtendWith(MainScopeExtension::class)
class UserServiceTest {
    @Test
    fun callRegisterApiTest() {
        //如果使用Coroutine進行異步，要使用runTest
        runTest {
            var result : RegisterUserResponse? = null
            UserService.callRegisterApi("1234", "1234", object : ServiceCallback<RegisterUserResponse> {
                override fun onResult(data: RegisterUserResponse, e: Throwable?) {
                    result = data
                }
            })
            //等待所有Coroutine結束
            advanceUntilIdle()
            assertNotNull(result)
        }
    }
}