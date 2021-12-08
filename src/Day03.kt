fun main() {
    fun part1(input: List<String>): Int {
        val length = input.size
        val map = readInputToMap(input)
        return Input(map, length, input).solvePart1()
    }

    fun part2(input: List<String>): Int {
        val length = input.size
        val map = readInputToMap(input)
        return Input(map, length, input).solvePart2()
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

/**
 * Returns a map which just maps occurrences of '1' in each position as key.
 */
fun readInputToMap(input: List<String>): Map<Int, Int> {
    val map: MutableMap<Int, Int> = (0 until input[0].length).associateWith { 0 }.toMutableMap()
    for (i in input) {
        for ((idx, c) in i.withIndex()) {
            if (c == '1') map[idx] = map[idx]!! + 1
        }
    }
    return map
}

data class Input(val map: Map<Int, Int>, val length: Int, val input: List<String>)

private fun Input.mostCommonBitAt(index: Int): Char {
    val ones = this.map[index]!!
    val zeroes = this.length - ones
    return if (ones >= zeroes) { '1' } else { '0' }
}

private fun Input.leastCommonBitAt(index: Int): Char {
    val ones = this.map[index]!!
    val zeroes = this.length - ones
    return if (zeroes <= ones) { '0' } else { '1' }
}

/**
 * If we know the length of the digit, we know which bit is more frequent
 */
fun Input.gamma(): Int {
    var res = ""
    this.map.forEach {
        res += if (it.value > this.length / 2) {
            "1" //adds a 1 at the end
        } else {
            "0" //adds a 0 at the end
        }
    }
    return res.toInt(2)
}

/**
 * Solving for epsilon is basically gamma.inv().
 * We have to mask it using 2**length-1 (in this example, length of input
 * is 12 so 0b1111_1111_1111)
 */
fun Input.epsilon(): Int {
    return this.gamma().inv() and (2 shl this.length) - 1
}

fun Input.oxygen(): Int {
    var current = this
    for (pos in 0 until this.input[0].length) {
        val inputs = current.input.filter { it[pos] == current.mostCommonBitAt(pos) }
        val map = readInputToMap(inputs)
        val length = inputs.size
        current = Input(map, length, inputs)
        if (current.input.size == 1) break
    }
    return current.input[0].toInt(2)
}

fun Input.co2(): Int {
    var current = this
    for (pos in 0 until this.input[0].length) {
        val inputs = current.input.filter { it[pos] == current.leastCommonBitAt(pos) }
        val map = readInputToMap(inputs)
        val length = inputs.size
        current = Input(map, length, inputs)
        if (current.input.size == 1) break
    }
    return current.input[0].toInt(2)
}

fun Input.solvePart1(): Int {
    return this.gamma() * this.epsilon()
}

fun Input.solvePart2(): Int {
    return this.oxygen() * this.co2()
}
