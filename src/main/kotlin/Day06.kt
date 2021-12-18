fun main() {
    check(26L == lanternfishing(readInput("check_day_6"), 18))
    check(5934L == lanternfishing(readInput("check_day_6"), 80))
    check(26984457539 == lanternfishing(readInput("check_day_6"), 256))
    println("Fishes after 80 days: ${lanternfishing(readInput("input_day_6"), 80)}")
    println("Fishes after 256 days: ${lanternfishing(readInput("input_day_6"), 256)}")
}

fun lanternfishing(initialAges: List<String>, days: Int): Long {
    val ages = initialAges.get(0).split(",").map { it.toInt() }
    var ageMap = ages.groupingBy { it }.eachCount().mapValues{ it.value.toLong() }.toMutableMap()
    println("Initial Ages: $ages\nAs Age Map: $ageMap")
    // Set Sentinel boundaries. Not sure whether that actually makes it much easier, but... 🤷‍
    ageMap.getOrPut(-1){0}
    ageMap.getOrPut(0) {0}
    ageMap.getOrPut(7) {0}
    ageMap.getOrPut(8) {0}
    for (day in (1..days)) {
        val newAges = HashMap<Int, Long>()
        ageMap.forEach { age, amount ->
            val newAge = Math.max(-1, age - 1)
//            println("$amount fishes were $age days (now $newAge) away from giving birth")
            newAges[newAge] = amount
        }
        val newBorns = newAges.getOrDefault(-1, 0)
        newAges.compute(6) { _, value -> if (value != null) value + newBorns else newBorns}
        newAges[8] = newBorns
        newAges.remove(-1)  // if I don't remove it it will continue making values -2 -3 -4 ...
        ageMap = newAges
        println("Ages after day $day: $ageMap; New Borns: $newBorns; Total: ${ageMap.values.sum()}")
    }

    val result = ageMap.values.sum()
    println("Total after $days days: $result")
    return result
}
