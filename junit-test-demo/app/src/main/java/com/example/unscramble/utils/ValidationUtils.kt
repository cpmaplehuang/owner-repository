package com.example.unscramble.utils

class ValidationUtils {
    companion object {
        fun isValidPhoneNumber(phoneNumber: String): Boolean {
            return phoneNumber.matches(Regex("^[5679]\\d{7}\$"))
                    && !phoneNumber.matches(Regex("^(.)\\1{7}\$"))
        }

    }
}