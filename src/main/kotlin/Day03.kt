import kotlin.math.pow

fun main() {

    fun convertToDecimal(rate: IntArray): Int {
        var result = 0.0
        rate.reversed().forEachIndexed { index, i ->
            if (i == 1) {
                result += 2.0.pow(index.toDouble())
                println("adding 2^$index = $result")
            }
        }
        return result.toInt()
    }

    fun convertToDecimal(binary: String): Int {
        var result = 0.0
        binary.reversed().forEachIndexed { index, c ->
            if (c == '1') {
                result += 2.0.pow(index.toDouble())
                println("adding 2^$index = $result")
            }
        }
        return result.toInt()
    }

    fun getBitCountsPerPosition(length: Int, input: List<String>): IntArray {
        val countsAtPosition = IntArray(length) { 0 } // init everything to 0 (shorthand for {i -> 0}
        input.forEach { line ->
            for (i in line.indices) {
                if (line.get(i) == '1') {
                    countsAtPosition[i] += 1
                }
            }
        }
        println("1's per position, for ${input.size} entries: \n" +
                countsAtPosition.joinToString(separator = " \t ")
        )
        return countsAtPosition
    }

    fun powerConsumption(input: List<String>): Int {
        val size = input.size
        val firstLine = input.get(0)
        val countsAtPosition = getBitCountsPerPosition(firstLine.length, input)

        println("Counted inputs: ${countsAtPosition.joinToString(separator = "")}")

        val gammaRate = IntArray(firstLine.length) { 0 } // init everything to 0 (shorthand for {i -> 0}
        val epsilonRate = IntArray(firstLine.length) { 0 } // init everything to 0 (shorthand for {i -> 0}
        countsAtPosition.forEachIndexed { index, i ->
            if (i > size/2) {
                gammaRate[index] = 1
            } else {
                epsilonRate[index] = 1
            }
        }

        println("Gamma Rate: ${gammaRate.joinToString(separator = "")}")
        println("Epsilon Rate: ${epsilonRate.joinToString(separator = "")}")

        val gamma = convertToDecimal(gammaRate)
        val epsilon = convertToDecimal(epsilonRate)

        return gamma * epsilon
    }

    fun part1(input: List<String>): Int {
        return powerConsumption(input)
    }

    fun getBitOccuringMostOrEqual(mutableInput: List<String>, index: Int): Char {
        val countsPerPosition = getBitCountsPerPosition(mutableInput[0].length, mutableInput)
        val more1s = countsPerPosition[index] * 2 >= mutableInput.size
        val bit = if(more1s) '1' else '0'
        println("Most bits in position $index are '$bit' (${countsPerPosition[index]} of ${mutableInput.size} are 1's)")
        return bit
    }

    fun getBinaryMatchingMostOrLeast(input: List<String>, keepMostCommon: Boolean): Int {
        val mutableInput = input.toMutableList()

        var index = 0
        while (mutableInput.size > 1 && index < input[0].length) {
            val mostCommonBit = getBitOccuringMostOrEqual(mutableInput, index)
            val leastCommonBit = if (mostCommonBit == '1') '0' else '1'
            // remove all lines that don't match the condition

            mutableInput.removeIf { line -> if (keepMostCommon) line[index] == leastCommonBit else line[index] == mostCommonBit}
            println(mutableInput.joinToString(separator = "\n"))
            index++
        }

        println("Last remaining: ${mutableInput[0]}")
        return convertToDecimal(mutableInput[0])
    }

    fun oxygenGeneratorRating(input: List<String>): Int {
        // keep most common in each position or 1 if equal
        return getBinaryMatchingMostOrLeast(input, true)
    }

    fun co2ScrubberRating(input: List<String>): Int {
        // keep least common, or 0 if equal
        return getBinaryMatchingMostOrLeast(input, false)
    }

    fun lifeSupportRating(input: List<String>) : Int {
        val oxygenGeneratorRating = oxygenGeneratorRating(input)
        val co2ScrubberRating = co2ScrubberRating(input)
        println("Oxygen: $oxygenGeneratorRating * CO2: $co2ScrubberRating")
        return oxygenGeneratorRating * co2ScrubberRating
    }

    check(230 == lifeSupportRating(readInput("check_day_3")))

    val input = readInput("input_day_3")
    println(lifeSupportRating(input))
}

