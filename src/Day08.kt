import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        fun isUniqueLength(text: String): Boolean {
            return listOf(2, 3, 4, 7).any { text.length == it }
        }

        fun countUniques(list: List<String>): Int {
            return list.count { isUniqueLength(it) }
        }

        return input.map { it.split("|")[1].trim().split(" ") }
            .sumOf {
                countUniques(it)
            }

    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day08")
    println(part1(input))
//    println(part2(input))
}

class Mapping {
    var digitMapping: MutableMap<Int, SortedSet<Char>> = mutableMapOf()
    var lengthMapping: MutableMap<String, Int> = mutableMapOf()
    var outputDigits = listOf<String>()

    companion object {
        fun fromString(list: String): Mapping {
            val mapping = Mapping()
            val input = list.split("|")

            mapping.outputDigits = input[1].trim().split(" ").map { it.trim() }
            mapping.addAll(input[0])

            mapping.determineDigitMapping()

            return mapping
        }
    }

    fun addAll(segments: String) {
        segments.trim().split(" ").forEach { put(it) }
    }

    private fun put(segment: String) {
        val key = filtering(segment)
        val value = key.length


        lengthMapping[key] = key.length
    }

    private fun filtering(segment: String) = segment.toSortedSet().joinToString("")

    /**
     * What actually solves part 2
     */
    private fun determineDigitMapping() {
        for ((digit, characters) in digitMapping) {
            // Obvious cases
            if (characters.size == 2) {
                digitMapping[1] = characters
            }
            if (characters.size == 3) {
                digitMapping[7] = characters
            }
            if (characters.size == 4) {
                digitMapping[4] = characters
            }
            if (characters.size == 7) {
                digitMapping[8] = characters
            }

            when (characters.size) {
                // This could be a 5, 2, or 3
                5 -> {
                    // It's a `3` if its characters contain all the characters in `1`
                    if (characters.containsAll(digitMapping[1]!!)) {
                        digitMapping[3] = characters
                    }

                }
                // This could be a 9, 6, 0
                6 -> {
                    // excluding 8, only `4` is contained in 9.
                    if (characters.containsAll(digitMapping[4]!!)) {
                        digitMapping[9] = characters
                    }

                    if (characters.containsAll(digitMapping[1]!!)) {
                        digitMapping[0] = characters
                    }
                    else {
                        digitMapping[6] = characters
                    }

                }
            }
        }
    }

    fun outputDigit() = outputDigits.map { this[it] }.joinToString("").toInt()

    operator fun get(segment: String): Int {
        TODO()
    }
}

class Day08(val input: List<String>) {
    fun solve2(): Int {
        return input.map { Mapping.fromString(it) }
            .sumOf { it.outputDigit() }
    }
}
