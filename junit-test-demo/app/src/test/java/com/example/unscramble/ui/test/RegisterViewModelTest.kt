package com.example.unscramble.ui.test

import com.example.unscramble.ui.RegisterViewModel
import junit.extension.runTestWithCoroutine
import junit.extension.runTestWithHandler
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
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

}