package dev.ocpd.slf4k

import org.junit.jupiter.api.Test
import kotlin.reflect.jvm.jvmName
import kotlin.test.assertEquals

private object MyObject {
    val log by slf4j

    class MySubClass {
        val log by slf4j
    }
}

private class MyClass {
    val log by slf4j

    companion object {
        val log by slf4j
    }

    class MySubClass {
        val log by slf4j
    }
}

class DelegateProviderTest {

    @Test
    fun `basic cases`() {
        assertEquals(MyObject::class.jvmName, MyObject.log.name)
        assertEquals(MyClass::class.jvmName, MyClass().log.name)
    }

    @Test
    fun `in sub-classes`() {
        assertEquals(MyObject.MySubClass::class.jvmName, MyObject.MySubClass().log.name)
        assertEquals(MyClass.MySubClass::class.jvmName, MyClass.MySubClass().log.name)
    }

    @Test
    fun `in companions`() {
        assertEquals(MyClass::class.jvmName, MyClass.log.name)
    }
}
