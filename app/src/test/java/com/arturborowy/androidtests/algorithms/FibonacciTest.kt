package com.arturborowy.androidtests.algorithms

import junit.framework.TestCase.assertEquals
import org.junit.Test

class FibonacciTest {

    private val expectedFibonacciSequence = intArrayOf(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144)

    @Test
    fun `fibonacci returns expected values`() {
        expectedFibonacciSequence.forEachIndexed { i, expectedItem ->
            assertEquals(expectedItem, fibonacci(i))
        }
    }

    private val firstElement = 0
    private val secondElement = 1

    private fun fibonacci(i: Int): Int {
        return when (i) {
            0 -> firstElement
            1 -> secondElement
            else -> fibonacci(i - 1) + fibonacci(i - 2)
        }
    }
}