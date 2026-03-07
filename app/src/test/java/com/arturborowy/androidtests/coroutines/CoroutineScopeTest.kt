package com.arturborowy.androidtests.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineScopeTest {

    private val coroutineScope = CoroutineScope(Job())

    private val exceptionToThrow = RuntimeException()

    @Test
    fun `CoroutineScope's direct child coroutine is cancelled when another direct child fails`() {
        var hasSecondChildSucceeded = false

        runBlocking {
            val throwingChild = coroutineScope.launch {
                throw exceptionToThrow
            }

            val successChild = coroutineScope.launch {
                delay(100)
                hasSecondChildSucceeded = true
            }

            joinAll(throwingChild, successChild)
        }

        assertFalse(hasSecondChildSucceeded)
    }

    @Test
    fun `CoroutineScope's direct child coroutine is not cancelled when another direct child throws CancellationException`() {
        val cancellationException = CancellationException()

        var hasSecondChildSucceeded = false

        runBlocking {
            val throwingChild = coroutineScope.launch {
                throw cancellationException
            }

            val successChild = coroutineScope.launch {
                delay(100)
                hasSecondChildSucceeded = true
            }

            joinAll(throwingChild, successChild)
        }

        assert(hasSecondChildSucceeded)
    }
}