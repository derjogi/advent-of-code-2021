fun main() {
    check(37 == crabPositions(readInput("check_day_7")))
    println("Min moves required: ${crabPositions(readInput("input_day_7"))}")
}

fun crabPositions(input: List<String>): Int {
    val horizontalPositions = input[0].split(",").map { it.toInt() }

    // Approach: take all values between the min and max.
    // Then count the difference between each to that value and take the sum,
    // then compare those sums.

    val min = horizontalPositions.minOf { it }
    val max = horizontalPositions.maxOf { it }

    val moves = HashMap<Int, Int>()
    var lowest = Int.MAX_VALUE
    var posForLowest = 0
    for (position in (min..max)) {
        var sum = 0
        horizontalPositions.forEach{ sum += Math.abs(it - position) }
        moves[position] = sum
        if (sum < lowest) {
            lowest = sum
            posForLowest = position
        }
    }

    println("All possible moves: $moves")

    println("Position $posForLowest had the lowest necessary moves of $lowest")
    return lowest
}
