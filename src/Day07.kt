import kotlin.math.abs
import kotlin.math.min

fun main() {
    fun part1(input: String): Int {
        val positions = input.split(",").map(String::toInt)
        val sorted = positions.sorted()
        val mid = sorted[positions.size/2]
        fun cost(pos: Int, mid: Int): Int {
            return abs(pos - mid)
        }
        return positions.sumOf { cost(it, mid) }
    }

    fun part2(input: String): Int {
        val positions = input.split(",").map(String::toInt)
        val sorted = positions.sorted()
        val mid = sorted[positions.size/2]

        fun cost(pos: Int, mid: Int): Int {
            fun triangle(num: Int): Int {
                return num * (num+1) / 2
            }
            return triangle(abs(pos - mid))
        }
        var triangles = sorted.sumOf { cost(it, mid) }

        for (m in (sorted.minOf { it } .. sorted.maxOf { it })) {
            triangles = min(triangles, sorted.sumOf { cost(it, m) })
        }
        return triangles
    }

    val input = readText("Day07")
    println(part1(input))
    println(part2(input))
}
