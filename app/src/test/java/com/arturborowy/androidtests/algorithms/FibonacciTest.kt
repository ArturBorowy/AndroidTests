package com.arturborowy.androidtests.algorithms

import junit.framework.TestCase.assertEquals
import org.junit.Test

// TODO add performance tests
class FibonacciTest {

    private val expectedFibonacciSequence = intArrayOf(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144)

    @Test
    fun `recursiveFibonacci returns expected values`() {
        expectedFibonacciSequence.forEachIndexed { i, expectedItem ->
            assertEquals(expectedItem, recursiveFibonacci(i))
        }
    }

    @Test
    fun `recursiveFibonacciWithMemory returns expected values`() {
        expectedFibonacciSequence.forEachIndexed { i, expectedItem ->
            assertEquals(expectedItem, recursiveFibonacciWithMemory(i))
        }
    }

    @Test
    fun `iterativeFibonacci returns expected values`() {
        expectedFibonacciSequence.forEachIndexed { i, expectedItem ->
            assertEquals(expectedItem, iterativeFibonacci(i))
        }
    }

    private val firstElement = 0
    private val secondElement = 1

    private fun iterativeFibonacci(i: Int): Int {
        when (i) {
            0 -> return firstElement
            1 -> return secondElement
        }

        var first = firstElement
        var second = secondElement
        var third = 0

        for (i in 0 until i - 1) {
            third = first + second
            first = second
            second = third
        }

        return third
    }

    private fun recursiveFibonacci(i: Int): Int {
        return when (i) {
            0 -> firstElement
            1 -> secondElement
            else -> recursiveFibonacci(i - 1) + recursiveFibonacci(i - 2)
        }
    }

    private fun recursiveFibonacciWithMemory(
        i: Int,
        memory: IntArray = IntArray(i + 1) { -1 }
    ): Int {
        return when {
            i == 0 -> firstElement
            i == 1 -> secondElement
            memory[i] != -1 -> memory[i]
            else -> {
                memory[i] = recursiveFibonacciWithMemory(i - 1, memory) +
                        recursiveFibonacciWithMemory(i - 2, memory)
                memory[i]
            }
        }
    }
}
