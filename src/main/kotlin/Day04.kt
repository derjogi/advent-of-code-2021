fun main() {

    val bingoResults = playBingo(readInput("check_day_4"))
    println("Results: $bingoResults")
    check(1924 == bingoResults)

    val input = readInput("input_day_4")
    println(playBingo(input))

}

fun playBingo(input: List<String>): Int {
    val (drawnNumbers, boards) = parseInput(input)
    val boardsNotWonYet = (1..boards.size).toMutableSet()
    drawnNumbers.forEach { number ->
        println("\nNewly drawn number: $number")
        boards.forEach { board ->
            val hasCompleteRowOrColumn = board.drawNumberAndCheck(number)
            if (hasCompleteRowOrColumn) {
                if (boardsNotWonYet.contains(board.boardIndex)) {
                    boardsNotWonYet.remove(board.boardIndex)
                }
                if (boardsNotWonYet.isEmpty()) {
                    val score = board.getWinningScore(number)
                    println("Aaaand we have a LOOSER!\n\nBoard:\n$board\nScore: $score")
                    return score
                }
            }
        }
    }
    return -1
}

private fun parseInput(input: List<String>): Pair<List<Int>, List<Board>> {
    val drawnNumbers = input[0].split(",").map { it.toInt() }

    var boardCounter = 0
    var board = Board(boardCounter)
    val boards = ArrayList<Board>()

    // split into 'boards', i.e. every newline marks the start of a new board
    input.drop(1) // the first line is drawnNumbers
        .forEachIndexed { index, line ->
            if (line.isEmpty()) {
                // start new 'board'
                board = Board(++boardCounter)
                boards.add(board)
            } else {
                board.addLine(line)
            }
        }

    return Pair(drawnNumbers, boards)
}

class Board(index: Int) {
    val boardIndex = index

    // has a matrix (2D array) of rows/columns, and an equivalent matrix of which numbers were drawn already.
    val drawnNumbers = ArrayList<LinkedHashMap<Int, Boolean>>()

    fun addLine(line: String) {
        val numbers: Map<Int, Boolean> = line.split(" ").filter { it.isNotBlank() }.associate { it.toInt() to false }
        drawnNumbers.add(numbers as LinkedHashMap<Int, Boolean>)
    }

    fun drawNumberAndCheck(number: Int): Boolean {
        var allValuesInColumnArefound = true
        var allValuesInRowArefound = true
        var col = 0
        drawnNumbers.forEach { row ->
            if (row[number] != null) { // drawn!
                row[number] = true
                // check whether either all values in the row are drawn, then all in the column.
                // First, find out which index was the number drawn so we know the column

                println("Found $number in Board $boardIndex: $this")

                var index = 0
                row.forEach { key, value ->
                    if (key == number) {
                        col = index
                    }
                    allValuesInRowArefound = allValuesInRowArefound && value
                    index++
                }
                if (allValuesInRowArefound) return allValuesInRowArefound
            }
        }

        // check column:
        drawnNumbers.forEach { row ->
            row.values.forEachIndexed {index, drawn ->
                if (index == col) {
                    allValuesInColumnArefound = allValuesInColumnArefound && drawn
                }
            }
        }
        return allValuesInColumnArefound
    }

    fun getWinningScore(number: Int): Int {
        var total = 0
        drawnNumbers.forEach { row ->
            val filterValues = row.filterValues { !it }
            total += filterValues.keys.sum()
        }
        return total * number
    }

    override fun toString(): String {
        var message = "\n*** Board $boardIndex: ***\n"
        drawnNumbers.forEach { row ->
            row.forEach{ entry -> message += "${entry.key} (${if (entry.value) "✓" else "x"})\t" }
            message += "\n"
        }
        return message+"\n"
    }
}
