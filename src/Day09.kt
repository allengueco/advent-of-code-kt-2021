fun main() {
    fun part1(input: List<String>): Int {
        return CaveMap(input).solve1()
    }

    fun part2(input: List<String>): Int {
        return CaveMap(input).solve2()
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

class CaveMap(input: List<String>) {
    private val width: Int
    private val height: Int
    private val map: List<List<Int>>

    data class Position(val x: Int, val y: Int)

    init {
        map = input.map { row -> row.map { it.toString().toInt() } }
        width = map[0].size
        height = map.size
    }

    private fun isInBounds(pos: Position): Boolean {
        return (pos.x in 0 until width) and (pos.y in 0 until height)
    }

    private fun getNeighbors(pos: Position): List<Position> {
        val neighbors = mutableListOf<Position>(
            //below
            Position(pos.x.inc(), pos.y),

            //above
            Position(pos.x.dec(), pos.y),

            //left
            Position(pos.x, pos.y.dec()),

            //right
            Position(pos.x, pos.y.inc())
        ).filter { p ->
            isInBounds(p)
        }

        return neighbors
    }

    private fun getBasinSize(lowPoint: Position): Int {
        val frontier:MutableSet<Position> = mutableSetOf()
        fun traverseUp(from: Position, frontier: MutableSet<Position>): Int {
            val notVisited = !frontier.contains(from)
            val isValid = (this[from] != 9)

            if (notVisited && isValid) {
                frontier.add(from)
            } else return 0

            val nbhd = getNeighbors(from)
            for (n in nbhd) {
                if (frontier.contains(n)) continue else {
                    traverseUp(n, frontier)
                }
            }

            return frontier.size
        }
        return traverseUp(lowPoint, frontier)
    }

    operator fun get(index: Position): Int {
        return map[index.y][index.x]
    }

    private fun isLowPoint(pos: Position): Boolean {
        return getNeighbors(pos).all { neighbor ->
            this[neighbor] > this[pos]
        }
    }

    fun solve1(): Int {
        var total = 0

        for ((cIdx, c) in map.withIndex()) {
            for ((rIdx, _) in c.withIndex()) {
                val pos = Position(rIdx, cIdx)
                if (isLowPoint(pos)) total += this[pos] + 1
            }
        }
        return total
    }

    fun solve2(): Int {
        val total = mutableListOf<Int>()
        for ((cIdx, c) in map.withIndex()) {
            for ((rIdx, _) in c.withIndex()) {
                val pos = Position(rIdx, cIdx)
                if (isLowPoint(pos)) total.add(getBasinSize(pos))
            }
        }
        return total.sortedDescending().take(3).reduce { a, b -> a * b }
    }
}
