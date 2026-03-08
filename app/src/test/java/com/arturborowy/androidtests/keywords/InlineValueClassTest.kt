package com.arturborowy.androidtests.keywords

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import org.junit.Assert.assertThrows
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
        assert(a == b)
    }

    @Test
    fun `boxing via Any - referential inequality`() {
        val list1 = listOf(PasswordInline("x"))
        val list2 = listOf(PasswordInline("x"))
        val a: Any = list1[0]
        val b: Any = list2[0]
        assertFalse(a === b)
        assert(a == b)
    }

    @Test
    fun `boxing via nullable - referential inequality`() {
        val a: Any? = PasswordInline("x")
        val b: Any? = PasswordInline("x")
        assertFalse(a === b)
        assert(a == b)
    }

    @Test
    fun `underlying value is accessible`() {
        assertEquals("x", PasswordInline("x").value)
    }

    @JvmInline
    private value class NonEmptyPassword(val value: String) {
        init {
            require(value.isNotEmpty()) { "Value must not be empty" }
        }
    }

    @Test
    fun `init block throws on invalid value`() {
        assertThrows(IllegalArgumentException::class.java) {
            NonEmptyPassword("")
        }
    }

    @JvmInline
    private value class Email(val value: String) {
        fun domain(): String = value.substringAfter("@")
    }

    @Test
    fun `member function operates on underlying value`() {
        assertEquals("example.com", Email("user@example.com").domain())
    }
}