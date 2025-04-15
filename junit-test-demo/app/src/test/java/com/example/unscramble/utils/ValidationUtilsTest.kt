package com.example.unscramble.utils

import com.example.unscramble.data.RegisterUserResponse
import com.example.unscramble.data.UserData
import com.example.unscramble.data.UserInfo
import com.example.unscramble.service.ServiceCallback
import com.example.unscramble.service.UserService
import com.example.unscramble.ui.RegisterViewModel
import io.mockk.every
import io.mockk.just
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.slot
import junit.extension.runTestWithCoroutine
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream


/**
 * 常用斷言 Assertions。
 * 無論哪種檢查，斷言方法都可以接受一個字符串作爲最後一個可選參數，它會在斷言失敗時提供必要的描述信息。
 * 如果提供出錯信息的過程比較複雜，它也可以被包裝在一個 lambda 表達式中，這樣，只有到真正失敗的時候，消息纔會真正被構造出來。
 *
 * assertEquals 斷言預期值和實際值相等◦
 * assertAll 分組斷言，執行其中包含的所有斷言◦
 * assertArrayEquals 斷言預期數組和實際數組相等◦
 * assertFalse 斷言條件爲假◦
 * assertNotNull 斷言不爲空◦
 * assertSame 斷言兩個對象相等◦
 * assertTimeout 斷言超時◦
 * assertTimeoutPreemptively 断言超时; 和assertTimeout的區別，在於assertTimeoutPreemptively一旦超時，就立即返回錯誤，assertTimeout會把 lambda 執行完
 * assertThrows 斷言異常，當 Lambda 表達式中代碼出現的異常會跟首個參數的異常類型進行比較，如果不屬於同一類異常，則失敗。
 * fail 使單元測試失敗。
 * Given-When-Then 模式
 * **/

/**
 * Todo @TestFactory
 * **/

class ValidationUtilsTest {

    companion object {
        /**
         * 表示使用了該註解的方法應該在當前類中所有測試方法之前執行（只執行一次），並且它必須是靜態方法。
         * **/
        @BeforeAll
        @JvmStatic
        fun initAll() {
            println("BeforeAll")
        }

        /**
         * 表示使用了該註解的方法應在當前類中所有測試方法之後執行（只執行一次），且它必須是靜態方法。
         * **/
        @AfterAll
        @JvmStatic
        fun tearDownAll() {
            println("AfterAll")
        }

        @JvmStatic
        fun invalidFirstCharacterProvider() : Stream<String> {
            return Stream.of("41234567", "81234567", "01234567", "A1234567")
        }

        @JvmStatic
        fun abnormalLengthStringProvider() : Stream<Arguments> {
            return Stream.of(
                Arguments.of(0, "1234567"),
                Arguments.of(1, "123456789"),
                Arguments.of(2, "")
            )
        }
    }


    /**
     * 表示使用了該註解的方法應該在當前類中每一個測試方法之前執行。
     * 可選 TestInfo，能夠知道正在執行的test信息
     * **/
    @BeforeEach
    fun initEach(testInfo: TestInfo) {
        println("BeforeEach ${testInfo.testMethod.get().name}")
    }


    /**
     * 表示使用了該註解的方法應該在當前類中每一個測試方法之前執行。
     * **/
    @AfterEach
    fun tearDown() {
        println("AfterEach")
    }


    /**
     * 表示該方法是一個測試方法。
     * **/
    @Test
    /**
     * 用於禁用一個測試類或測試方法。
     * **/
    @Disabled
    // 全重复数字测试
    fun `all repeated digits should return false`() {
        assertFalse(ValidationUtils.isValidPhoneNumber("55555555")) {
            "all repeated digits should return false is fail"
        }
    }

    /**
     * 重複執行測試
     * 可選 RepetitionInfo，能夠知道當前執行的次數
     * **/
    @RepeatedTest(2)
    fun `minimum valid number`(repetitionInfo : RepetitionInfo) {
        println("minimum valid number execute ${repetitionInfo.currentRepetition}")
        assertTrue(ValidationUtils.isValidPhoneNumber("50000000"))
    }

    //工作量
    //test case怎保证复盖率


    // 有效号码测试
    /**
     * 參數化測試，會把ValueSource裡面的值，都執行一次
     * **/
    @ParameterizedTest
    @ValueSource(strings = ["51234567", "61234567", "71234567", "98765432"])
    fun `valid phone numbers should return true`(phoneNumber: String) {
        println("valid phone numbers should return true")
        assertTrue(ValidationUtils.isValidPhoneNumber(phoneNumber))
    }

    // 包含非数字字符测试
    /**
     * 參數化測試，
     * ArgumentsSource，支持多個參數
     * **/
    @ParameterizedTest
    @ArgumentsSource(CustomArgumentsProvider::class) // 注意包含全角数字
    fun `non-digit characters should return false`(index: Int, phoneNumber: String) {
        println("non-digit characters $index $phoneNumber")
        assertFalse(ValidationUtils.isValidPhoneNumber(phoneNumber))
    }

    // 无效首字符测试
    /**
     * 參數化測試，提供一個Function(必須是Static)，使得輸入參數可編程
     * **/
    @ParameterizedTest
    @MethodSource("invalidFirstCharacterProvider")
    fun `invalid starting digit should return false`(phoneNumber: String) {
        assertFalse(ValidationUtils.isValidPhoneNumber(phoneNumber))
    }

    // 长度异常测试
    /**
     * 參數化測試，提供一個Function(必須是Static)
     * 另外一種支持多參數的方式
     * **/
    @ParameterizedTest
    @MethodSource("abnormalLengthStringProvider") //GIVEN
    fun `invalid length should return false`(index: Int, phoneNumber: String) {
        println("invalid length $index $phoneNumber")
        //WHEN
        val isCorrect = ValidationUtils.isValidPhoneNumber(phoneNumber)
        //THEN
        assertFalse(isCorrect)
    }

}

class CustomArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
        return Stream.of(
            Arguments.of(0, "5123A567"),
            Arguments.of(1, "6-234567"),
            Arguments.of(2, "7 345678"),
            Arguments.of(3, "9８765432") // 注意包含全角数字
        )
    }
}