package dev.ocpd.slf4k

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass

/**
 * Concise way to create loggers.
 *
 * Usage:
 * ```
 * class YourClass {
 *     private val log by slf4j
 * }
 * ```
 */
val slf4j = PropertyDelegateProvider<Any, ReadOnlyProperty<Any, Logger>> { thisRef, _ ->
    val logger = LoggerFactory.getLogger(thisRef::class.unwrapCompanion())
    ReadOnlyProperty { _, _ -> logger }
}

private fun <T : Any> KClass<T>.unwrapCompanion() = if (isCompanion) java.declaringClass else java

/**
 * Concise way to create loggers.
 * This alternative provides the ability to specify the class for the logger.
 *
 * Usage:
 * ```
 * class YourClass : BaseClass {
 *     private val log = slf4j<BaseClass>()
 * }
 * ```
 */
@JvmName("slf4jByClass")
inline fun <reified T : Any> slf4j(): Logger = LoggerFactory.getLogger(T::class.java)

/**
 * NOT RECOMMENDED! Create loggers by name.
 *
 * Usage:
 * ```
 * class YourClass {
 *     private val log = slf4j("my-logger")
 * }
 * ```
 */
@JvmName("slf4jByName")
fun slf4j(name: String): Logger = LoggerFactory.getLogger(name)
