package com.republic.services.test.spear

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import kotlinx.coroutines.withTimeout
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainCoroutineRule(val dispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

    suspend fun waitForState(timeout: Long = 5000, hasReachedState: () -> Boolean) {
        withTimeout(timeout) {
            while (!hasReachedState()) {
                dispatcher.scheduler.runCurrent()
                delay(1000)
            }
        }
    }
}