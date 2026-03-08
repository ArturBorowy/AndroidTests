package com.arturborowy.androidtests.keywords

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import org.junit.Test
import kotlin.math.abs

class OperatorTest {

    data class Point(var x: Float, var y: Float) {

        operator fun plus(anotherPoint: Point) =
            Point(x + anotherPoint.x, y + anotherPoint.y)

        operator fun minus(anotherPoint: Point) =
            Point(x - anotherPoint.x, y - anotherPoint.y)

        operator fun times(anotherPoint: Point) =
            Point(x * anotherPoint.x, y * anotherPoint.y)

        operator fun div(anotherPoint: Point) =
            Point(x / anotherPoint.x, y / anotherPoint.y)

        operator fun unaryMinus() =
            Point(-x, -y)

        operator fun unaryPlus() =
            Point(+x, +y)

        operator fun rem(anotherPoint: Point) =
            Point(x % anotherPoint.x, y % anotherPoint.y)

        operator fun inc() =
            Point(x + 1, y + 1)

        operator fun dec() =
            Point(x - 1, y - 1)

        operator fun get(index: Int) =
            if (index == 0) x else y

        operator fun contains(value: Float) =
            x == value || y == value

        operator fun compareTo(anotherPoint: Point) =
            (abs(x) + abs(y)).compareTo(abs(anotherPoint.x) + abs(anotherPoint.y))

        operator fun invoke() =
            abs(x) + abs(y)


        operator fun set(index: Int, value: Float) {
            if (index == 0) x = value else y = value
        }
    }

    private val pointA = Point(2f, 3f)
    private val pointB = Point(4f, 5f)

    @Test
    fun `+ override returns sum of values`() {
        val sumPoint = pointA + pointB

        assertEquals(6f, sumPoint.x)
        assertEquals(8f, sumPoint.y)
    }

    @Test
    fun `- override returns difference of values`() {
        val differencePoint = pointA - pointB

        assertEquals(-2f, differencePoint.x)
        assertEquals(-2f, differencePoint.y)
    }

    @Test
    fun `times override returns multiplication of values`() {
        val timesPoint = pointA * pointB

        assertEquals(8f, timesPoint.x)
        assertEquals(15f, timesPoint.y)
    }

    @Test
    fun `div override returns multiplication of values`() {
        val divPoint = pointA / pointB

        assertEquals(0.5f, divPoint.x)
        assertEquals(0.6f, divPoint.y)
    }

    @Test
    fun `unaryMinus override returns negated values`() {
        val negated = -pointA

        assertEquals(-2f, negated.x)
        assertEquals(-3f, negated.y)
    }

    @Test
    fun `unaryPlus override returns same values`() {
        val same = +pointA

        assertEquals(2f, same.x)
        assertEquals(3f, same.y)
    }

    @Test
    fun `rem override returns remainder of values`() {
        val remPoint = pointA % pointB

        assertEquals(2f, remPoint.x)
        assertEquals(3f, remPoint.y)
    }

    @Test
    fun `inc override increments both values by 1`() {
        var v = pointA
        v++
        assertEquals(3f, v.x)
        assertEquals(4f, v.y)
    }

    @Test
    fun `dec override decrements both values by 1`() {
        var v = pointA
        v--
        assertEquals(1f, v.x)
        assertEquals(2f, v.y)
    }

    @Test
    fun `get override returns x for index 0 and y for index 1`() {
        assertEquals(2f, pointA[0])
        assertEquals(3f, pointA[1])
    }

    @Test
    fun `contains override returns true when value matches x or y`() {
        assert(2f in pointA)
        assert(3f in pointA)

        assertFalse(99f in pointA)
    }

    @Test
    fun `compareTo override compares Points by Manhattan distance`() {
        assert(pointA < pointB)
        assert(pointB > pointA)
    }

    @Test
    fun `invoke override returns Manhattan distance`() {
        assertEquals(5f, pointA())
        assertEquals(9f, pointB())
    }

    @Test
    fun `set override assigns x for index 0 and y for index 1`() {
        val p = Point(2f, 3f)
        p[0] = 10f
        p[1] = 20f
        assertEquals(10f, p.x)
        assertEquals(20f, p.y)
    }
}