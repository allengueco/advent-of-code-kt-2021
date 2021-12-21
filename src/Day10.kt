import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        return Subsystem(input).solve1()
    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    val input = readInput("Day10")
    println(part1(input))
//    println(part2(input))
}

val scoreMapping = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
)


val opening = listOf('(', '[', '{', '<')
val closing = listOf(')', ']', '}', '>')
val bracesMapping = opening.zip(closing).toMap()

class Subsystem(val input: List<String>) {
    fun solve1(): Int {
        return input.sumOf { process(it) ?: 0 }
    }
    private fun process(line: String): Int? {
        val s = Stack<Char>()

        var error: Char = 'a'
        for (char in line) {
            when (char) {
                in opening -> s.push(char)
                bracesMapping[s.peek()] -> s.pop()
                else -> {
                    error = char
                    break
                }
            }
        }
        return scoreMapping[error]
    }
}
