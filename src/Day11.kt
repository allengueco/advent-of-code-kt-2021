fun main() {
    fun part1(input: List<String>): Int {
        return Octopi(input).solve1()
    }

    fun part2(input: List<String>): Int {
        return Octopi(input).solve2()
    }

    val input = readInput("Day11")
//    println(part1(input))
    println(part2(input))
}

class Octopi(input: List<String>) {
    private val width: Int
    private val height: Int
    private val map: MutableList<MutableList<Int>>

    data class Position(val x: Int, val y: Int) {
        override fun toString(): String {
            return "($x, $y)"
        }
    }

    init {
        map = input.map { row -> row.map { it.toString().toInt() }.toMutableList() }.toMutableList()
        width = map[0].size
        height = map.size

    }

    private fun isInBounds(pos: Position): Boolean {
        return (pos.x in 0 until width) and (pos.y in 0 until height)
    }

    private fun getNeighbors(pos: Position): List<Position> {

        return mutableListOf(
            //below
            Position(pos.x.inc(), pos.y),

            //above
            Position(pos.x.dec(), pos.y),

            //left
            Position(pos.x, pos.y.dec()),

            //right
            Position(pos.x, pos.y.inc()),


            //top-left
            Position(pos.x.dec(), pos.y.dec()),

            //top-right
            Position(pos.x.inc(), pos.y.dec()),

            //bottom-left
            Position(pos.x.dec(), pos.y.inc()),

            //bottom-right
            Position(pos.x.inc(), pos.y.inc())
        ).filter { isInBounds(it) } // only get in bound neighbors
    }

    operator fun get(index: Position): Int {
        return map[index.y][index.x]
    }

    operator fun set(index: Position, newValue: Int) {
        this.map[index.y][index.x] = newValue
    }

    // Returns the number of octopuses that flashes in this step
    private fun step(): Int {
        var totalFlashes = 0
        val flashPositions = mutableSetOf<Position>()

        // Part 1: increment everything. If it's 9, add it to positions to flash
        for ((cIdx, c) in map.withIndex()) {
            for ((rIdx, _) in c.withIndex()) {
                val pos = Position(rIdx, cIdx)
                this[pos] += 1
            }
        }

        // Part 2: Flash each position we found. This is the recursive step
        for ((cIdx, c) in map.withIndex()) {
            for ((rIdx, _) in c.withIndex()) {
                val pos = Position(rIdx, cIdx)
                if (this[pos] > 9 && pos !in flashPositions)
                    totalFlashes += flash(pos, flashPositions)
            }
        }

        // Part 3: Reset everything that was flashed
        resetFlashed(flashPositions)

        return totalFlashes
    }


    /**
     * Flash each octopus. Recursive : how many additional octopus flash did this flash cause?
     */
    private fun flash(from: Position, flashPositions: MutableSet<Position>): Int {
        var flashes = 1

        flashPositions.add(from)
        for (n in getNeighbors(from)) {
            this[n] += 1
            if (this[n] > 9 && n !in flashPositions) flashes += flash(n, flashPositions)

        }

        return flashes
    }



    private fun resetFlashed(flashPositions: MutableSet<Position>) {
        flashPositions.forEach { this[it] = 0 }
    }

    private fun steps(n: Int): Int {
        return (0 until n)
            .mapIndexed { i, _ -> step()
                .also { println("After step ${i + 1}:\n$this\n") } }
            .sum()
    }

    fun solve1(): Int {
        println("Before any changes:\n$this\n")
        return steps(100)
    }

    fun solve2(): Int {
        var c = 0
        while (true) {
            step()
            if (isSynchronized()) {
                break
            }
            c++
        }
        println(this)
        return c
    }

    private fun isSynchronized(): Boolean {
        return this.map.all { row -> row.all { it == 0 } }
    }

    override fun toString(): String {
        return map.joinToString("\n") { it.joinToString(" ") }
    }
}