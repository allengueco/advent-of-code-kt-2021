import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return Day05(input).solve1()
    }

    fun part2(input: List<String>): Int {
        return Day05(input).solve2()
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

enum class LineType {
    VERTICAL,
    HORIZONTAL,
    DIAGONAL,
    NONE
}

data class Coordinate(val x: Int, val y: Int) {
    companion object {
        fun fromString(input: String): Coordinate {
            val r = input.split(",").map { it.trim() }
            return Coordinate(r[0].toInt(), r[1].toInt())
        }

        fun fromString(inputs: List<String>): Coordinate {
            return Coordinate(inputs[0].toInt(), inputs[1].toInt())
        }
    }
}

data class Line(val start: Coordinate, val end: Coordinate) {
    var type: LineType
    private val deltaX: Int = abs(start.x - end.x)
    private val deltaY: Int = abs(start.y - end.y)

    init {
        type = if (deltaX == 0) {
            LineType.HORIZONTAL
        } else if (deltaY == 0) {
            LineType.VERTICAL
        } else if (deltaX == deltaY) {
            LineType.DIAGONAL
        } else {
            LineType.NONE
        }
    }

    fun pointsCovered(): List<Coordinate> {
        return when (type) {
            LineType.HORIZONTAL -> if (start.y < end.y) {
                (start.y..end.y).map { Coordinate(start.x, it) }
            } else {
                (end.y..start.y).map { Coordinate(start.x, it) }
            }

            LineType.VERTICAL -> if (start.x < end.x) {
                (start.x..end.x).map { Coordinate(it, start.y) }
            } else {
                (end.x..start.x).map { Coordinate(it, start.y) }
            }
            // If it's a diagonal, then we determine how we generate the
            // coords. This is similar to chess bishop move generation.
            // First we check for the difference from the start to the
            // end. The values determine which direction it goes.
            // Then we check the absolute difference, which tells us how
            // much to move in that direction
            // (+, +) = x+abs(diffX), y+abs(diffY)
            LineType.DIAGONAL -> {
                val diffX = start.x - end.x
                val diffY = start.y - end.y
                when {
                    // (+, +)
                    // both diffs negative
                    diffX < 0 && diffY < 0 -> {
                        (start.x..end.x)
                            .zip(start.y..end.y)
                            .map { Coordinate(it.first, it.second) }
                    }

                    // (-, +)
                    diffX > 0 && diffY < 0 -> {
                        (start.x downTo end.x)
                            .zip(start.y..end.y)
                            .map { Coordinate(it.first, it.second) }
                    }

                    // (+, -)
                    diffX < 0 && diffY > 0 -> {
                        (start.x..end.x)
                            .zip(start.y downTo end.y)
                            .map { Coordinate(it.first, it.second) }
                    }

                    // (-, -)
                    diffX > 0 && diffY > 0 -> {
                        (start.x downTo end.x)
                            .zip(start.y downTo end.y)
                            .map { Coordinate(it.first, it.second) }
                    }
                    else -> listOf()
                }
            }


            LineType.NONE -> listOf()
        }
    }

    companion object {
        fun fromString(input: String): Line {
            val spl = input.split("->")
            return Line(Coordinate.fromString(spl[0]), Coordinate.fromString(spl[1]))
        }

        fun fromCoordinates(coordinates: Pair<Coordinate, Coordinate>): Line {
            return Line(coordinates.first, coordinates.second)
        }
    }
}

class Vents {
    private val map: MutableList<MutableList<Int>> = MutableList(999) { MutableList(999) { 0 } }

    operator fun get(coord: Coordinate): Int = map[coord.y][coord.x]

    private fun mark(coord: Coordinate) {
        map[coord.y][coord.x] += 1
    }

    fun countDanger(threshold: Int): Int {
        return map.flatten().count { it >= threshold }
    }

    fun applyLineToMap(line: Line) {
        val points = line.pointsCovered()
        points.forEach {
            this.mark(it)
        }
    }
}

class Day05(input: List<String>) : BaseInput(input) {
    private val vents: Vents = Vents()
    private val lines = input.map(Line.Companion::fromString)
    override fun solve1(): Int {
        lines.filter { (it.type == LineType.HORIZONTAL) || (it.type == LineType.VERTICAL) }
            .forEach {
                vents.applyLineToMap(it)
            }
        return vents.countDanger(2)
    }

    override fun solve2(): Int {
        lines
            .filter {
                (it.type == LineType.HORIZONTAL) ||
                        (it.type == LineType.VERTICAL) ||
                        (it.type == LineType.DIAGONAL)
            }
            .forEach {
                vents.applyLineToMap(it)
            }
        return vents.countDanger(2)
    }
}