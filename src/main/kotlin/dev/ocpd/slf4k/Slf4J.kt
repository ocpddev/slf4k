package dev.ocpd.slf4k

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.invoke.MethodHandles
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty

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
    val logger = LoggerFactory.getLogger(thisRef.javaClass)
    ReadOnlyProperty { _, _ -> logger }
}

/**
 * Concise way to create loggers.
 * This alternative is safe to use in companion objects.
 *
 * Usage:
 * ```
 * class YourClass {
 *     companion object {
 *         private val log = slf4j() // will create logger for YourClass
 *     }
 * }
 * ```
 */
@JvmName("slf4jByLookup")
@Suppress("NOTHING_TO_INLINE") // force inline for not breaking lookup
inline fun slf4j(): Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

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
