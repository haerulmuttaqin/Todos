package id.haeworks.core.util.extension

import id.haeworks.core.R

fun Int?.orZero(): Int = this ?: 0
fun Double?.orZero(): Double = this ?: 0.0
fun Float?.orZero(): Float = this ?: 0f
fun Long?.orZero(): Long = this ?: 0
fun Int.divideToPercent(divideTo: Int): Int {
    return if (divideTo == 0) 0
    else (this / divideTo.toFloat() * 100).toInt()
}
fun IntArray?.orEmpty(): IntArray = this ?: emptyArray<Int>().toIntArray()
fun Int?.orResourceStringEmpty(): Int = this ?: R.string.empty_string
fun Int?.ifNull(execute: () -> Int): Int {
    return this ?: execute.invoke()
}