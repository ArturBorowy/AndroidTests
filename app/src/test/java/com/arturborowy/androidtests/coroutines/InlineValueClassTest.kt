package com.arturborowy.androidtests.coroutines

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InlineValueClassTest {

    interface Wrapper

    @JvmInline
    private value class PasswordInline(val value: String) : Wrapper

    private data class PasswordData(val value: String)

    @Test
    fun `data's and inline value class's toString() returns string created in the same pattern`() {
        assertEquals("PasswordData(value=x)", PasswordData("x").toString())
        assertEquals("PasswordInline(value=x)", PasswordInline("x").toString())
    }

    @Test
    fun `equals returns true when the same value is passed to constructors`() {
        assert(PasswordInline("x") == PasswordInline("x"))
    }

    @Test
    fun `equals returns false when different value is passed to constructors`() {
        assertFalse(PasswordInline("x") == PasswordInline("y"))
    }

    @Test
    fun `hashCode equals underlying value hashCode`() {
        assertEquals("x".hashCode(), PasswordInline("x").hashCode())
    }

    @Test
    fun `boxing via interface - referential inequality`() {
        val a: Wrapper = PasswordInline("x")
        val b: Wrapper = PasswordInline("x")
        assertFalse(a === b)
        assertTrue(a == b)
    }

    @Test
    fun `boxing via nullable - referential inequality`() {
        val a: Any = PasswordInline("x")
        val b: Any = PasswordInline("x")
        assertFalse(a === b)
        assertTrue(a == b)
    }
}
