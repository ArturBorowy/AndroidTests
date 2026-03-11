package com.arturborowy.androidtests.datastructures

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Collections.singletonMap

class MapTest {

    @Test
    fun `mapOf with no elements creates EmptyMap object`() {
        assertEquals(emptyMap<Int, Int>(), mapOf<Int, Int>())
    }

    @Test
    fun `mapOf with one element creates java singletonMap object`() {
        assertEquals(singletonMap(1, 2), mapOf(1 to 2))
    }

    private val collectionSizes = intArrayOf(10, 100, 1000, 10000, 100000, 1000000)

    @Test
    fun `insert is O(1)`() {
        repeat(5) { getTimeOfSetOperation(1000) }

        val insertTimeNanos = collectionSizes.map { getTimeOfSetOperation(it) }

        collectionSizes.indices.forEach { i ->
            println("size=${collectionSizes[i]} | insertTimeNanos=${insertTimeNanos[i]}")
        }

        val mapGrowthRatio =
            insertTimeNanos.last().toDouble() / insertTimeNanos[insertTimeNanos.size - 2]
        println("Map growth ratio for 10x size: ${mapGrowthRatio}x")
        assert(mapGrowthRatio < 3.0)
    }

    private fun getTimeOfSetOperation(mapSize: Int): Long {
        val largeMap = List(mapSize) { it }
            .associateWith { it }
            .toMutableMap()

        val indexToAdd = mapSize / 2
        val valueToAdd = 2137

        val timeBeforeAdd = System.nanoTime()
        largeMap[indexToAdd] = valueToAdd
        return System.nanoTime() - timeBeforeAdd
    }
}