package com.example.unscramble.service

import com.example.unscramble.data.RegisterUserResponse
import junit.extension.runTestWithCoroutine
import junit.extension.runTestWithHandler
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.apter.junit.jupiter.robolectric.RobolectricExtension
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExtendWith(RobolectricExtension::class)
class UserServiceTest {

    /**
     * 測試基于Coroutine的異步Case
     * **/
    @Test
    fun callRegisterApiTest() {
        runTestWithCoroutine {
            var result : RegisterUserResponse? = null
            UserService.callRegisterApi("1234", "1234", object : ServiceCallback<RegisterUserResponse> {
                override fun onResult(data: RegisterUserResponse, e: Throwable?) {
                    result = data
                }
            })

            var result2 : RegisterUserResponse? = null
            UserService.callRegisterApi("1234", "1234", object : ServiceCallback<RegisterUserResponse> {
                override fun onResult(data: RegisterUserResponse, e: Throwable?) {
                    result2 = data
                }
            })
            //等待所有Coroutine結束
            advanceUntilIdle()
            assertEquals("12345", result?.userData?.memberNo)
            assertEquals("12345", result2?.userData?.memberNo)
        }
    }


    /**
     * 測試基于MainHandler異步任务运行上下文
     * 依賴於Robolectric
     * **/
    @Test
    fun callRegisterApiWithThreadTest() {
        runTestWithHandler {
            var result : RegisterUserResponse? = null
            UserService.callRegisterApiWithThread("1234", "1234", object : ServiceCallback<RegisterUserResponse> {
                override fun onResult(data: RegisterUserResponse, e: Throwable?) {
                    result = data
                    it.handleAssert {
                        assertEquals("12345", result?.userData?.memberNo)
                    }
                }
            })
        }
    }


    /**
     * 測試基于MainHandler異步任务运行上下文
     * 依賴於Robolectric
     * **/
    @Test
    fun callRegisterApiWithThreadMultipleTest() {
        runTestWithHandler {
            val countDownLatch = CountDownLatch(2)
            var result : RegisterUserResponse? = null
            UserService.callRegisterApiWithThread("1234", "1234", object : ServiceCallback<RegisterUserResponse> {
                override fun onResult(data: RegisterUserResponse, e: Throwable?) {
                    result = data
                    countDownLatch.countDown()
                }
            })

            var result2 : RegisterUserResponse? = null
            UserService.callRegisterApiWithThread("1234", "1234", object : ServiceCallback<RegisterUserResponse> {
                override fun onResult(data: RegisterUserResponse, e: Throwable?) {
                    result2 = data
                    countDownLatch.countDown()
                }
            })

            countDownLatch.await(10, TimeUnit.SECONDS)
            it.handleAssert {
                assertEquals("12345", result?.userData?.memberNo)
                assertEquals("12345", result2?.userData?.memberNo)
            }
        }
    }
}