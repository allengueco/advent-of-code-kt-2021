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
        return Day08(input).solve2()
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

class Mapping {
    var digitMapping: MutableMap<SortedSet<Char>, Int?> = mutableMapOf()
    var outputDigits = listOf<SortedSet<Char>>()

    companion object {
        fun fromString(list: String): Mapping {
            val mapping = Mapping()
            val input = list.split("|")

            mapping.outputDigits = input[1].trim().split(" ").map { it.trim().toSortedSet() }

            mapping.determineDigitMapping(input[0].split(" "))

            return mapping
        }

        private fun containsSegment(s1: SortedSet<Char>, s2: SortedSet<Char>) = s1.containsAll(s2)
    }

    private fun getKeyByValue(value: Int): SortedSet<Char> {
        return digitMapping.filterValues { it == value }.keys.first()
    }


    /**
     * What actually solves part 2
     */
    private fun determineDigitMapping(segments: List<String>) {

        fun determineObvious() {
            val characters = segments.filter { s -> listOf(2, 3, 4, 7).any { it == s.length } }

            for (char in characters) {
                val s = char.toSortedSet()
                when (char.length) {
                    2 -> digitMapping[s] = 1
                    3 -> digitMapping[s] = 7
                    4 -> digitMapping[s] = 4
                    7 -> digitMapping[s] = 8
                }
            }
        }
        fun determineSixSegments() {
            val sixSegments = segments.filter { it.length == 6 }

            for (char in sixSegments) {
                val s = char.toSortedSet()
                when {
                    containsSegment(s, getKeyByValue(4)) -> digitMapping[s] = 9
                    containsSegment(s, getKeyByValue(7)) -> digitMapping[s] = 0
                    else -> digitMapping[s] = 6
                }
            }
        }
        fun determineFiveSegments() {
            val fiveSegments = segments.filter { it.length == 5 }
            for (char in fiveSegments) {
                val s = char.toSortedSet()
                when {
                    containsSegment(s, getKeyByValue(7)) -> digitMapping[s] = 3
                    containsSegment(getKeyByValue(6), s) -> digitMapping[s] = 5
                    else -> digitMapping[s] = 2
                }
            }
        }

        determineObvious()
        determineSixSegments()
        determineFiveSegments()
    }

    fun outputDigit() = outputDigits.map { this.digitMapping[it] }.joinToString("").toInt()
}

class Day08(val input: List<String>) {
    fun solve2(): Int {
        return input.map { Mapping.fromString(it) }
            .sumOf { it.outputDigit() }
    }
}
