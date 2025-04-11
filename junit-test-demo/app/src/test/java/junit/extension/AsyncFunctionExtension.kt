package junit.extension

import android.os.HandlerThread
import android.os.Looper
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


/**
 * 基于Coroutine異步任务运行上下文
 * 依賴於kotlinx.coroutines.test，版本要1.6+
 * **/
fun runTestWithCoroutine(dispatcher: CoroutineDispatcher = StandardTestDispatcher(),
                         context: CoroutineContext = EmptyCoroutineContext,
                         timeout: Duration = 60.seconds,
                         testBody: suspend TestScope.() -> Unit) {
    Dispatchers.setMain(dispatcher)
    runTest(context, timeout) {
        this.testBody()
        Dispatchers.resetMain()
    }
}


interface TestRunner {
    fun handleAssert(action : () -> Unit)
}

/**
 * 測試基于MainHandler異步任务运行上下文
 * 依賴於Robolectric
 * **/
fun runTestWithHandler(timeout: Duration = 60.seconds,
                       testBody: (testRunner: TestRunner) -> Unit) {
    val handlerThread = HandlerThread("test handler")
    handlerThread.start()
    val countDownLatch = CountDownLatch(1)
    mockkStatic(Looper::class)
    every { Looper.getMainLooper() } returns handlerThread.looper
    val testRunner = object : TestRunner {
        var localAction : (() -> Unit)? = null
        override fun handleAssert(action: () -> Unit) {
            localAction = action
            countDownLatch.countDown()
        }

    }
    testBody(testRunner)
    countDownLatch.await(timeout.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    unmockkStatic(Looper::class)
    handlerThread.quit()
    testRunner.localAction?.invoke()
}
