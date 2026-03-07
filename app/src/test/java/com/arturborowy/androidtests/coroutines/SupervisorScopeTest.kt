package com.arturborowy.androidtests.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SupervisorScopeTest {

    private val exceptionToThrow = RuntimeException()

    @Test
    fun `with supervisorScope as a child root scope's grandchild is NOT cancelled when another grandchild fails`() {
        var hasSecondChildSucceeded = false

        runBlocking {
            launch {
                supervisorScope {
                    val throwingChild = launch {
                        throw exceptionToThrow
                    }

                    val successChild = launch {
                        delay(100)
                        hasSecondChildSucceeded = true
                    }

                    joinAll(throwingChild, successChild)
                }
            }.join()
        }

        assert(hasSecondChildSucceeded)
    }
}