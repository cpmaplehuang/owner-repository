package com.example.unscramble.ui.test

import com.example.unscramble.data.RegisterUserResponse
import com.example.unscramble.data.UserData
import com.example.unscramble.data.UserInfo
import com.example.unscramble.service.ServiceCallback
import com.example.unscramble.service.UserService
import com.example.unscramble.ui.RegisterViewModel
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.unmockkObject
import junit.extension.jsonfilesource.JsonFileSource
import junit.extension.runTestWithCoroutine
import junit.extension.runTestWithHandler
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import tech.apter.junit.jupiter.robolectric.RobolectricExtension

@ExtendWith(RobolectricExtension::class)
class RegisterViewModelTest {


    @Test
    fun callRegisterApiTest() {
        runTestWithCoroutine {
            val viewModel = RegisterViewModel()
            viewModel.callRegisterApi("12345", "12345")
            advanceUntilIdle()
            Assertions.assertEquals("li", viewModel.userName.value)
        }
    }

    @Test
    fun callRegisterApiWithThreadTest() {
        runTestWithHandler {
            val viewModel = RegisterViewModel()
            viewModel.callRegisterApiWithThread("12345", "12345")
            //不能像Coroutine，能等所有的Coroutine執行完，所以需要延遲
            Thread.sleep(5000L)
            it.handleAssert {
                Assertions.assertEquals("12345", viewModel.userPhone.value)
            }
        }
    }

    //如果是混有Coroutine和Handler的Case，最好是拆分Function
    @Test
    fun callRegisterApiMixtureTest() {
        val viewModel = RegisterViewModel()
        runTestWithHandler {
            viewModel.callRegisterApiWithThread("12345", "12345")
            Thread.sleep(5000L)
            it.handleAssert {  }
        }

        runTestWithCoroutine {
            viewModel.callRegisterApi("12345", "12345")
            advanceUntilIdle()
        }

        Assertions.assertEquals("li", viewModel.userName.value)
        Assertions.assertEquals("12345", viewModel.userPhone.value)

    }


    @Test
    fun callRegisterApiWithMock() {

        mockkObject(UserService)
        val callbackMock = slot<ServiceCallback<RegisterUserResponse>>()

        every {
            UserService.callRegisterApi(any(), any(), capture(callbackMock))
        }  answers {
            callbackMock.captured.onResult(
                RegisterUserResponse(0,
                    UserData("12345", 0, UserInfo("liMock", "13922222"))
                ), null)
        }

        val viewModel = RegisterViewModel()
        viewModel.callRegisterApi("12345", "12345")
        Assertions.assertEquals("liMock", viewModel.userName.value)
        unmockkObject(UserService)
    }

    @ParameterizedTest
    @JsonFileSource(resources = ["/RegisterUserResponse.json"])
    fun callRegisterApiWithMockJson(response: RegisterUserResponse) {

        mockkObject(UserService)
        val callbackMock = slot<ServiceCallback<RegisterUserResponse>>()

        every {
            UserService.callRegisterApi(any(), any(), capture(callbackMock))
        }  answers {
            callbackMock.captured.onResult(
                response, null)
        }

        val viewModel = RegisterViewModel()
        viewModel.callRegisterApi("12345", "12345")
        Assertions.assertEquals("test user name", viewModel.userName.value)
        unmockkObject(UserService)
    }


}