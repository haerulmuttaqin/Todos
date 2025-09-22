package id.haeworks.core.util.extension


fun String?.orStrip() = if (!this.isNullOrBlank()) this else "-"
fun String?.isStrip() = this == "-"
fun String?.orZero() = if (!this.isNullOrBlank()) this else "0"
inline fun String?.ifNullOrEmpty(execute: () -> String) =
    if (this.isNullOrBlank()) execute.invoke() else this
inline fun String?.ifNotNullOrEmpty(execute: (String) -> String) =
    if (!this.isNullOrBlank()) execute.invoke(this) else this