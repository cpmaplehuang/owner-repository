package com.example.unscramble.ui

import kotlin.Any
import kotlin.String
import kotlin.Unit
import kotlinx.coroutines.flow.StateFlow
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

public class GameViewModelTest {
  private lateinit var testObject: GameViewModel

  @Before
  public fun setUp(): Unit {
    MockitoAnnotations.initMocks(this)
    testObject = GameViewModel(
    )
  }

  @Test
  public fun checkUserGuess(): Unit {
    val testExpectedResult: Any = Any()
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.checkUserGuess(
    	)
    )
  }

  @Test
  public fun updateUserGuess(): Unit {
    val testStringObject: String = "MoAh0UAu"
    val testExpectedResult: Any = Any()
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.updateUserGuess(
    		testStringObject
    	)
    )
  }

  @Test
  public fun getUserGuess(): Unit {
    val testExpectedResult: String = "Npo40xoZ"
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.getUserGuess(
    	)
    )
  }

  @Test
  public fun skipWord(): Unit {
    val testExpectedResult: Any = Any()
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.skipWord(
    	)
    )
  }

  @Test
  public fun getUiState(): Unit {
    lateinit var testExpectedResult: StateFlow
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.getUiState(
    	)
    )
  }

  @Test
  public fun resetGame(): Unit {
    val testExpectedResult: Any = Any()
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.resetGame(
    	)
    )
  }
}
