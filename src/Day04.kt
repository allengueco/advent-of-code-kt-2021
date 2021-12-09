fun main() {
    fun part1(input: List<String>): Int {
        return Bingo(input).solve1()
    }

    fun part2(input: List<String>): Int {
        return Bingo(input).solve2()
    }

    val input = readInputWithSeparator("Day04", """\n\n""".toRegex())
    println(part1(input))
    println(part2(input))
}


class Bingo(input: List<String>) : BaseInput(input) {
    class Board(boardInput: String) {
        private val board: List<Int>
        private val marked: MutableList<Boolean>
        private val solved = listOf(true, true, true, true, true)
        var winningNumber: Int? = null

        init {
            board = boardInput.split("\n")
                .flatMap { it.split("  ") }
                .map { it.trim() }
                .flatMap { it.split(" ") }
                .map { it.toInt() }
            marked = MutableList(25) { false }
        }


        fun mark(number: Int) {
            val idx = this.board.indexOf(number)
            if (idx != -1) this.marked[idx] = true
        }

        fun hasWon(): Boolean {
            (0 until 5).forEach {
                if ((checkRow(it) != null) || (checkColumn(it) != null)) {
                    return true
                }
            }
            return false
        }

        fun getUnmarkedSum(): Int {
            return (0 until 25).sumOf { if (!this.marked[it]) this.board[it] else 0 }
        }

        fun sum(): Int {
            return this.board.sum()
        }

        /**
         * Returns the winning row, if there is one
         */
        private fun checkRow(rowIdx: Int): List<Int>? {
            val rowWithIndex = this.marked.windowed(5, 5)
                .withIndex()
                .find { (index, _) -> index == rowIdx }

            if (rowWithIndex != null) {
                val startIdx = rowWithIndex.index * 5;
                val endIdx = startIdx + 5;
                return if (rowWithIndex.value == solved) {

                    this.board.subList(startIdx, endIdx)
                } else {
                    null
                }
            }

            return null
        }

        /**
         * Returns the winning column, if there is one
         */
        private fun checkColumn(colIdx: Int): List<Int>? {
            val colWithIndex = this.marked.withIndex().windowed(5, 5)
                .map { it[colIdx].index }

            val selectedIdx = colWithIndex.map { this.marked[it] }
            return if (selectedIdx == solved) {
                colWithIndex.map { this.board[it] }
            } else {
                null
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Board

            if (board != other.board) return false

            return true
        }

        override fun hashCode(): Int {
            return board.hashCode()
        }

        override fun toString(): String {
            return "Board(board=$board)"
        }

    }

    private val sequence: List<Int>
    private val boards: List<Board>

    init {
        sequence = input[0].split(",").map { it.toInt() }
        boards = input.subList(1, input.size).map {
            Board(it)
        }
    }

    override fun solve1(): Int {
        var result = 0;
        var winningBoard: Board? = null;
        var winningNumber: Int? = null;
        sequence@ for (s in sequence) {
            for (b in boards) {
                b.mark(s)
                if (b.hasWon()) {
                    winningBoard = b
                    winningNumber = s
                    break@sequence
                }
            }
        }
        return if (winningBoard != null && winningNumber != null) {
            val unmarkedSum = winningBoard.getUnmarkedSum()
            unmarkedSum * winningNumber
        } else {
            0
        }
    }

    override fun solve2(): Int {
        var result = 0;
        val winningBoards = mutableSetOf<Board>()
        var winningNumber: Int = 0
        val boardSize = boards.size
        sequence@ for (s in sequence) {
            for (b in boards) {
                b.mark(s)
                if (b.hasWon()) {
                    if (!winningBoards.add(b)) {
                        println("Added\n$b")
                    }
                    if (winningBoards.size == boardSize) {
                        winningNumber = s
                        break@sequence
                    }
                }
            }
        }

        val lastWinner = winningBoards.last()
        println(lastWinner)
        println(lastWinner.getUnmarkedSum())
        return lastWinner.getUnmarkedSum() * winningNumber
    }

}
