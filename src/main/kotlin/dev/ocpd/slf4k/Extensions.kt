package dev.ocpd.slf4k

import org.slf4j.Logger

inline fun Logger.trace(lazyMessage: () -> String) {
    if (isTraceEnabled) trace(lazyMessage())
}

inline fun Logger.debug(lazyMessage: () -> String) {
    if (isDebugEnabled) debug(lazyMessage())
}

inline fun Logger.info(lazyMessage: () -> String) {
    if (isInfoEnabled) info(lazyMessage())
}

inline fun Logger.warn(lazyMessage: () -> String) {
    if (isWarnEnabled) warn(lazyMessage())
}

inline fun Logger.error(lazyMessage: () -> String) {
    if (isErrorEnabled) error(lazyMessage())
}

inline fun Logger.trace(throwable: Throwable?, lazyMessage: () -> String) {
    if (isTraceEnabled) trace(lazyMessage(), throwable)
}

inline fun Logger.debug(throwable: Throwable?, lazyMessage: () -> String) {
    if (isDebugEnabled) debug(lazyMessage(), throwable)
}

inline fun Logger.info(throwable: Throwable?, lazyMessage: () -> String) {
    if (isInfoEnabled) info(lazyMessage(), throwable)
}

inline fun Logger.warn(throwable: Throwable?, lazyMessage: () -> String) {
    if (isWarnEnabled) warn(lazyMessage(), throwable)
}

inline fun Logger.error(throwable: Throwable?, lazyMessage: () -> String) {
    if (isErrorEnabled) error(lazyMessage(), throwable)
}
