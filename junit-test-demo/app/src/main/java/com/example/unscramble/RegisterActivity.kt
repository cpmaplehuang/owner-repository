package com.example.unscramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import com.example.unscramble.databinding.ActivityRegisterBinding
import com.example.unscramble.kover.ExcludeFromReport

@ExcludeFromReport
class RegisterActivity : ComponentActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
    }
}