package com.arturborowy.androidtests.datastructures

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Collections.singletonList

class ListTest {

    @Test
    fun `listOf with no elements creates EmptyList object`() {
        assertEquals(emptyList<Int>(), listOf<Int>())
    }

    @Test
    fun `listOf with one element creates java singletonList object`() {
        assertEquals(singletonList(1), listOf(1))
    }

    private val collectionSizes = intArrayOf(10, 100, 1000, 10000, 100000, 1000000)

    @Test
    fun `insert is O(n)`() {
        repeat(5) { getTimeOfAddOperation(1000) }

        val insertTimeNanos = collectionSizes.map { getTimeOfAddOperation(it) }

        collectionSizes.indices.forEach { i ->
            println("size=${collectionSizes[i]} | insertTimeNanos=${insertTimeNanos[i]}")
        }

        val listGrowthRatio =
            insertTimeNanos.last().toDouble() / insertTimeNanos[insertTimeNanos.size - 2]
        println("List growth ratio for 10x size: ${listGrowthRatio}x")
        assert(listGrowthRatio > 7.0)
    }

    private fun getTimeOfAddOperation(listSize: Int): Long {
        val largeMap = MutableList(listSize) { it }

        val indexToAdd = listSize / 2
        val valueToAdd = 2137

        val timeBeforeAdd = System.nanoTime()
        largeMap.add(indexToAdd, valueToAdd)
        return System.nanoTime() - timeBeforeAdd
    }
}