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


    fun powerConsumption(input: List<String>): Int {
        val size = input.size
        val firstLine = input.get(0)
        val countsAtPosition = IntArray(firstLine.length) { 0 } // init everything to 0 (shorthand for {i -> 0}
        input.forEach{ line ->
            for (i in line.indices) {
                if (line.get(i) == '1') {
                    countsAtPosition[i] += 1
                }
            }
        }

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

    val input = readInput("input_day_3")
    println(part1(input))
}

