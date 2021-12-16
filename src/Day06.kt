fun main() {
    fun part1(input: String): Long {
        return Simulation(input).solve(80)
    }

    fun part2(input: String): Long {
        return Simulation(input).solve(256)
    }

    val input = readText("Day06")
    println(part1(input))
    println(part2(input))
}


class Simulation(input: String) {
    private var lanternFish: MutableList<Int> = mutableListOf()

    init {
        lanternFish.addAll(input.split(",").map(String::toInt))
    }



    // GAVE UP: copied from https://blog.sweller.de/posts/advent-of-code-2021-6
    fun solve(days: Int): Long {
        var timerCounts = lanternFish.groupBy { it }.mapValues { it.value.count().toLong() }
        repeat(days) {
            timerCounts = (0..7)
                .associateWith { timerCounts.getOrDefault(it + 1, 0) }
                .plus(6 to timerCounts.getOrDefault(7, 0) + timerCounts.getOrDefault(0, 0))
                .plus(8 to timerCounts.getOrDefault(0, 0))
        }
        return timerCounts.values.sum()
    }
}
