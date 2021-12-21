import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        return Subsystem(input).solve1()
    }

    fun part2(input: List<String>): Long  {
        return Subsystem(input).solve2()
    }

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

val scoreMapping = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
)

val reverseMapping = mapOf(
    '(' to 1,
    '[' to 2,
    '{' to 3,
    '<' to 4
)


val opening = listOf('(', '[', '{', '<')
val closing = listOf(')', ']', '}', '>')
val bracesMapping = opening.zip(closing).toMap()

class Subsystem(val input: List<String>) {
    fun solve1(): Int {
        return input.sumOf { process(it) }
    }

    private fun process(line: String): Int {
        val s = Stack<Char>()

        for (char in line) {
            when (char) {
                in opening -> s.push(char)
                bracesMapping[s.peek()] -> s.pop()
                else -> {
                    return scoreMapping[char]!!
                }
            }
        }
        return 0
    }

    private fun incompleteStack(line: String): String {
        val s = Stack<Char>()

        for (char in line) {
            when (char) {
                in opening -> s.push(char)
                bracesMapping[s.peek()] -> s.pop()
            }
        }

        return s.joinToString("").reversed()
    }

    private fun computeIncompleteScore(stack: String): Long {
        return stack.map { reverseMapping[it]!! }.fold(0) { total, score -> total * 5 + score }
    }

    fun solve2(): Long {
        val incomplete = input.filter { process(it) == 0 }.map {
            computeIncompleteScore(incompleteStack(it))
        }
        val middleIdx = incomplete.size / 2
        println(incomplete)
        return incomplete.sorted()[middleIdx]
    }
}
