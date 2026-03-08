package com.arturborowy.androidtests

import junit.framework.TestCase.assertEquals
import org.junit.Test

class InfixTest {

    @Test
    fun `infix to creates Pair`() {
        val pair = "key" to 42
        assertEquals("key", pair.first)
        assertEquals(42, pair.second)
    }

    @Test
    fun `infix and combines booleans`() {
        val result = true and false
        assertEquals(false, result)
    }

    @Test
    fun `infix or combines booleans`() {
        val result = false or true
        assertEquals(true, result)
    }

    data class Car(var location: OperatorTest.Point) {

        infix fun travelTo(newLocation: OperatorTest.Point) {
            location = newLocation
        }
    }

    private val car = Car(OperatorTest.Point(2f, 3f))

    @Test
    fun `infix travelTo sets newLocation on car object`() {
        val newLocation = OperatorTest.Point(10f, 20f)

        car travelTo newLocation

        assertEquals(newLocation, car.location)
    }
}