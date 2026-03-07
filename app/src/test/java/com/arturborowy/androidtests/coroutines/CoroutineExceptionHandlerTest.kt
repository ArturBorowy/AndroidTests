package com.arturborowy.androidtests.coroutines

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class CoroutineExceptionHandlerTest {

    private val throwableHandlingDelegate = mockk<ThrowableHandlingDelegate>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwableHandlingDelegate.handle(throwable)
    }

    private val scope = CoroutineScope(coroutineExceptionHandler)

    private val exceptionToThrow = RuntimeException()

    @Before
    fun setUp() {
        every { throwableHandlingDelegate.handle(exceptionToThrow) } just runs
    }

    @Test
    fun `CoroutineExceptionHandler is called when exception is thrown from root coroutine launch`() {
        val job = scope.launch {
            throw exceptionToThrow
        }

        runBlocking { job.join() }

        verify { throwableHandlingDelegate.handle(exceptionToThrow) }
    }

    @Test
    fun `CoroutineExceptionHandler is called with original exception (not wrapped in CancellationException) when is thrown from child coroutine launch`() {
        val job = scope.launch {
            launch {
                throw exceptionToThrow
            }
        }

        runBlocking { job.join() }

        verify { throwableHandlingDelegate.handle(exceptionToThrow) }
    }

    @Test
    fun `CoroutineExceptionHandler does not recover coroutine`() {
        var executedAfterException = false

        val job = scope.launch {
            throw exceptionToThrow
            @Suppress("UNREACHABLE_CODE")
            executedAfterException = true

        }

        runBlocking { job.join() }

        assertFalse(job.isActive)
        assertFalse(executedAfterException)
    }

    @Test
    fun `expected exception is thrown when CoroutineExceptionHandler is passed to child coroutine of runTest`() {
        val exceptionThrown = assertThrows(RuntimeException::class.java) {
            runTest {
                val job = launch(coroutineExceptionHandler) {
                    throw exceptionToThrow
                }

                job.join()
            }
        }

        assertEquals(exceptionToThrow, exceptionThrown)

        verify(inverse = true) { throwableHandlingDelegate.handle(exceptionToThrow) }
    }

    @Test
    fun `CoroutineExceptionHandler silently ignores CancellationException`() {
        val cancellationExceptionToThrow = CancellationException()
        val job = scope.launch {
            cancel(cancellationExceptionToThrow)
            throw exceptionToThrow
        }

        runBlocking { job.join() }

        verify { throwableHandlingDelegate.handle(exceptionToThrow) }
        verify(inverse = true) { throwableHandlingDelegate.handle(cancellationExceptionToThrow) }
    }

    @Test
    fun `CoroutineExceptionHandler is NOT called when exception is thrown from root coroutine async`() {
        val deferred = scope.async(coroutineExceptionHandler) {
            throw exceptionToThrow
        }

        val exceptionThrown = assertThrows(RuntimeException::class.java) {
            runBlocking { deferred.await() }
        }

        assertEquals(RuntimeException::class.java, exceptionThrown.javaClass)
        verify(inverse = true) { throwableHandlingDelegate.handle(any()) }

    }

    @Test
    fun `CoroutineExceptionHandler is NOT called when the exception is catch`() {
        val tryCatchThrowableHandlingDelegate = mockk<ThrowableHandlingDelegate>()

        every { tryCatchThrowableHandlingDelegate.handle(exceptionToThrow) } just runs

        val job = scope.launch {
            try {
                throw exceptionToThrow
            } catch (runtimeException: RuntimeException) {
                tryCatchThrowableHandlingDelegate.handle(runtimeException)
            }
        }

        runBlocking { job.join() }

        verify(inverse = true) { throwableHandlingDelegate.handle(exceptionToThrow) }
        verify { tryCatchThrowableHandlingDelegate.handle(exceptionToThrow) }
    }

    @Test
    fun `CoroutineExceptionHandler is NOT called when CancellationException is thrown from root coroutine launch`() {
        val cancellationException = CancellationException()

        val job = scope.launch {
            throw cancellationException
        }

        runBlocking { job.join() }

        verify(inverse = true) { throwableHandlingDelegate.handle(cancellationException) }
    }
}