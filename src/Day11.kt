fun main() {
    fun part1(input: List<String>): Int {
        TODO()
    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    val input = readInput("Day11_test")
    println(part1(input))
    println(part2(input))
}

class Octopi(val input: List<String>) {
    private val width: Int
    private val height: Int
    private val map: List<List<Int>> = input.map { row -> row.map { it.toString().toInt() } }

    data class Position(val x: Int, val y: Int)

    init {
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
        this[index] = newValue
    }

    fun flash(pos: Position): Int {
        if (this[pos] != 9) {

        }
        this[pos] = 0
        return 0
    }

    // Returns the number of flashes in this step
    fun step(): Int {
        val flashes = 0

        val flashPositions = listOf<Position>()

        for ((cIdx, c) in map.withIndex()) {
            for ((rIdx, _) in c.withIndex()) {
                val pos = Position(rIdx, cIdx)
                when (this[pos]) {

                }
            }
        }

        return flashes
    }

    fun solve1(): Int {
        TODO()
    }

    fun solve2(): Int {
        TODO()
    }
}