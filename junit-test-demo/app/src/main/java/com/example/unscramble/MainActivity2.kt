
/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.unscramble

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.unscramble.kover.ExcludeFromReport
import com.example.unscramble.ui.GameViewModel
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

@ExcludeFromReport
public class MainActivity2 : AppCompatActivity() {
    private val gameViewModel: GameViewModel by viewModels()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ui)
        init()
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                gameViewModel.uiState.collect { state ->
                    if (state.isGameOver) {
                        FinalScoreDialog(state.score) {
                            gameViewModel.resetGame()

                            findViewById<TextView>(R.id.scrambled_word).text = gameViewModel.uiState.value.currentScrambledWord
                            findViewById<TextView>(R.id.current_word).text = gameViewModel.uiState.value.currentWord
                            findViewById<TextView>(R.id.word_count).text = String.format(getString(R.string.word_count),  gameViewModel.uiState.value.currentWordCount)
                        }
                    }
                }
            }
        }
    }

    private fun init() {
        findViewById<TextView>(R.id.scrambled_word).text = gameViewModel.uiState.value.currentScrambledWord
        findViewById<TextView>(R.id.current_word).text = gameViewModel.uiState.value.currentWord
        findViewById<TextView>(R.id.word_count).text = String.format(getString(R.string.word_count),  gameViewModel.uiState.value.currentWordCount)

        findViewById<EditText>(R.id.user_input).imeOptions = EditorInfo.IME_ACTION_DONE
        findViewById<EditText>(R.id.user_input).setOnEditorActionListener { tv, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                gameViewModel.checkUserGuess()
                findViewById<TextView>(R.id.word_count).text = String.format(getString(R.string.word_count),  gameViewModel.uiState.value.currentWordCount)
                true
            }
            false
        }

//        findViewById<Button>(R.id.submit_button).setOnClickListener {
//            gameViewModel.checkUserGuess()
//        }

        findViewById<Button>(R.id.skip_button).setOnClickListener {
            gameViewModel.skipWord()
            findViewById<TextView>(R.id.scrambled_word).text = gameViewModel.uiState.value.currentScrambledWord
            findViewById<TextView>(R.id.current_word).text = gameViewModel.uiState.value.currentWord
            findViewById<TextView>(R.id.word_count).text = String.format(getString(R.string.word_count),  gameViewModel.uiState.value.currentWordCount)

        }

        findViewById<EditText>(R.id.user_input).addTextChangedListener {
            val currentWord = findViewById<EditText>(R.id.user_input).text.toString()
            gameViewModel.updateUserGuess(currentWord)
        }

//        findViewById<EditText>(R.id.error_labeled).addTextChangedListener {
//            if(gameViewModel.uiState.value.isGuessedWordWrong){
//                findViewById<TextView>(R.id.error_labeled).visibility = View.VISIBLE
//            } else {
//                findViewById<TextView>(R.id.error_labeled).visibility = View.GONE
//            }
//        }

        findViewById<View>(R.id.submit_button).setOnClickListener {
            gameViewModel.checkUserGuess()
            findViewById<EditText>(R.id.user_input).text.clear()
            if(gameViewModel.uiState.value.isGuessedWordWrong){
                findViewById<TextView>(R.id.error_labeled).visibility = View.VISIBLE
            } else {
                findViewById<TextView>(R.id.error_labeled).visibility = View.GONE
                findViewById<TextView>(R.id.scrambled_word).text = gameViewModel.uiState.value.currentScrambledWord
                findViewById<TextView>(R.id.current_word).text = gameViewModel.uiState.value.currentWord
                findViewById<TextView>(R.id.word_count).text = String.format(getString(R.string.word_count),  gameViewModel.uiState.value.currentWordCount)
            }
        }
    }

    private fun FinalScoreDialog(score: Int,  onPlayAgain: () -> Unit) {
        val alertDialog: AlertDialog = AlertDialog.Builder(this@MainActivity2).create()
        alertDialog.setTitle(R.string.congratulations)
        alertDialog.setCancelable(false)
        alertDialog.setMessage(String.format(getString(R.string.you_scored), score))
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.play_again)
        ) { dialog, which ->
            onPlayAgain()
        }

        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.exit)
        ) { dialog, which ->
            finish()
        }

        alertDialog.show()
    }

}


