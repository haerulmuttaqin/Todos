package id.haeworks.core.util.extension

fun Boolean?.orFalse(): Boolean = this ?: false
fun Boolean?.isTrue() = this.orFalse()
fun Boolean?.isFalse() = this.isTrue().not()