fun main() {
    fun part1(input: List<String>): Int {
        var init = Position(0, 0)
        for (i in input) {
            val ins = parseStringToInstruction(i)
            init = apply(init, ins)
        }
        return init.calculate()
    }

    fun part2(input: List<String>): Int {
        var init = Position2(0, 0, 0)
        for (i in input) {
            val ins = parseStringToInstruction(i)
            init = apply2(init, ins)
        }
        return init.calculate()
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun parseStringToInstruction(string: String): Instruction {
    val (ins, n) = string.split(" ")
    val instruction = when(ins) {
        "forward" -> InstructionType.FORWARD
        "up" -> InstructionType.UP
        "down" -> InstructionType.DOWN
        else -> throw IllegalArgumentException("Invalid instruction")
    }
    val number = n.toInt()

    return Instruction(instruction, number)
}

enum class InstructionType {
    FORWARD, DOWN, UP
}
data class Instruction(val type: InstructionType, val n: Int)

data class Position(var horizontal: Int, var depth: Int) {
    fun calculate(): Int {
        return this.horizontal * this.depth
    }
}
data class Position2(var horizontal: Int, var depth: Int, var aim: Int) {
    fun calculate(): Int {
        return this.horizontal * this.depth
    }
}

fun apply(position: Position, instruction: Instruction): Position {
    val (type, n) = instruction
    when (type) {
        InstructionType.FORWARD -> position.horizontal += n
        InstructionType.DOWN -> position.depth += n
        InstructionType.UP -> position.depth -= n
    }
    return position
}
fun apply2(position: Position2, instruction: Instruction): Position2 {
    val (type, n) = instruction
    when (type) {
        InstructionType.DOWN -> position.aim += n
        InstructionType.UP -> position.aim -= n
        InstructionType.FORWARD -> {
            position.horizontal += n
            position.depth += position.aim * n
        }
    }
    return position
}