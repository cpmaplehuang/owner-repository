package com.example.unscramble.utils

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ValidationUtilsTest {
    // 有效号码测试
    @ParameterizedTest
    @ValueSource(strings = ["51234567", "61234567", "71234567", "98765432"])
    fun `valid phone numbers should return true`(phoneNumber: String) {
        assertTrue(ValidationUtils.isValidPhoneNumber(phoneNumber))
    }

    // 无效首字符测试
    @ParameterizedTest
    @ValueSource(strings = ["41234567", "81234567", "01234567", "A1234567"])
    fun `invalid starting digit should return false`(phoneNumber: String) {
        assertFalse(ValidationUtils.isValidPhoneNumber(phoneNumber))
    }

    // 长度异常测试
    @ParameterizedTest
    @ValueSource(strings = ["1234567", "123456789", ""])
    fun `invalid length should return false`(phoneNumber: String) {
        assertFalse(ValidationUtils.isValidPhoneNumber(phoneNumber))
    }

    // 包含非数字字符测试
    @ParameterizedTest
    @ValueSource(strings = ["5123A567", "6-234567", "7 345678", "9８765432"]) // 注意包含全角数字
    fun `non-digit characters should return false`(phoneNumber: String) {
        assertFalse(ValidationUtils.isValidPhoneNumber(phoneNumber))
    }

    // 全重复数字测试（如果启用了补充逻辑）
    @Test
    fun `all repeated digits should return false`() {
        assertFalse(ValidationUtils.isValidPhoneNumber("55555555"))
    }

    // 特殊边界值测试
    @Test
    fun `minimum valid number`() {
        assertTrue(ValidationUtils.isValidPhoneNumber("50000000"))
    }

    @Test
    fun `maximum valid number`() {
        assertTrue(ValidationUtils.isValidPhoneNumber("99999998"))
    }

}