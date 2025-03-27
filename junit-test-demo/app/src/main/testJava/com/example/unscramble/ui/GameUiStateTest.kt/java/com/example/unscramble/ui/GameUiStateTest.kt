package com.example.unscramble.ui

import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Unit
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

public class GameUiStateTest {
  private lateinit var testObject: GameUiState

  private lateinit var testObject2: GameUiState

  private lateinit var string: String

  private var int: Int = 1

  private lateinit var string2: String

  private var int2: Int = 1

  private var boolean: Boolean = false

  private var boolean2: Boolean = false

  @Before
  public fun setUp(): Unit {
    MockitoAnnotations.initMocks(this)
    testObject = GameUiState(
    )
    testObject2 = GameUiState(
    	string,
    	int,
    	string2,
    	int2,
    	boolean,
    	boolean2
    )
  }

  @Test
  public fun equals(): Unit {
    val testObjectObject: Any = Any(
    )
    val testExpectedResult: Boolean = false
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.equals(
    		testObjectObject
    	)
    )
  }

  @Test
  public fun toString(): Unit {
    val testExpectedResult: String = "arp7DRiI"
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.toString(
    	)
    )
  }

  @Test
  public fun hashCode(): Unit {
    val testExpectedResult: Int = 1
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.hashCode(
    	)
    )
  }

  @Test
  public fun copy(): Unit {
    val testStringObject: String = "lHyVUAMt"
    val testIntObject: Int = 1
    val testStringObject2: String = "J6FcPa0q"
    val testIntObject2: Int = 1
    val testBooleanObject: Boolean = false
    val testBooleanObject2: Boolean = false
    val testExpectedResult: GameUiState = GameUiState(
    )
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.copy(
    		testStringObject,
    		testIntObject,
    		testStringObject2,
    		testIntObject2,
    		testBooleanObject,
    		testBooleanObject2
    	)
    )
  }

  @Test
  public fun component1(): Unit {
    val testExpectedResult: String = "dFYNlvoZ"
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.component1(
    	)
    )
  }

  @Test
  public fun component2(): Unit {
    val testExpectedResult: Int = 1
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.component2(
    	)
    )
  }

  @Test
  public fun component3(): Unit {
    val testExpectedResult: String = "pA3KsqIB"
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.component3(
    	)
    )
  }

  @Test
  public fun component4(): Unit {
    val testExpectedResult: Int = 1
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.component4(
    	)
    )
  }

  @Test
  public fun component5(): Unit {
    val testExpectedResult: Boolean = false
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.component5(
    	)
    )
  }

  @Test
  public fun component6(): Unit {
    val testExpectedResult: Boolean = false
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.component6(
    	)
    )
  }

  @Test
  public fun getCurrentWordCount(): Unit {
    val testExpectedResult: Int = 1
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.getCurrentWordCount(
    	)
    )
  }

  @Test
  public fun getCurrentScrambledWord(): Unit {
    val testExpectedResult: String = "fCqHPczr"
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.getCurrentScrambledWord(
    	)
    )
  }

  @Test
  public fun getCurrentWord(): Unit {
    val testExpectedResult: String = "sVLtAWUB"
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.getCurrentWord(
    	)
    )
  }

  @Test
  public fun isGuessedWordWrong(): Unit {
    val testExpectedResult: Boolean = false
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.isGuessedWordWrong(
    	)
    )
  }

  @Test
  public fun getScore(): Unit {
    val testExpectedResult: Int = 1
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.getScore(
    	)
    )
  }

  @Test
  public fun isGameOver(): Unit {
    val testExpectedResult: Boolean = false
    Assert.assertEquals (
    	testExpectedResult,
    	testObject.isGameOver(
    	)
    )
  }
}
