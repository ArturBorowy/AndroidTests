package com.arturborowy.androidtests.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SupervisorJobTest {

    private val scopeWithSupervisorJob = CoroutineScope(SupervisorJob())

    private val exceptionToThrow = RuntimeException()

    @Test
    fun `SupervisorJob's direct child coroutine is not cancelled when another direct child fails`() {
        var hasSecondChildSucceeded = false

        runBlocking {
            val throwingChild = scopeWithSupervisorJob.launch {
                throw exceptionToThrow
            }

            val successChild = scopeWithSupervisorJob.launch {
                delay(100)
                hasSecondChildSucceeded = true
            }

            joinAll(throwingChild, successChild)
        }

        assert(hasSecondChildSucceeded)
    }

    @Test
    fun `SupervisorJob's grandchild is cancelled when another grandchild fails`() {
        var hasSecondChildSucceeded = false

        runBlocking {
            scopeWithSupervisorJob.launch {
                val throwingChild = launch {
                    throw exceptionToThrow
                }

                val successChild = launch {
                    delay(100)
                    hasSecondChildSucceeded = true
                }

                joinAll(throwingChild, successChild)
            }.join()
        }

        assertFalse(hasSecondChildSucceeded)
    }
}