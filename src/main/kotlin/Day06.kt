fun main() {
    check(26 == lanternfishing(readInput("check_day_6"), 18))
    check(5934 == lanternfishing(readInput("check_day_6"), 80))
    println("Fishes after 80 days: ${lanternfishing(readInput("input_day_6"), 80)}")
}

fun lanternfishing(initialAges: List<String>, days: Int): Int {
    val ages = initialAges.get(0).split(",").map { it.toInt() }
    val groupedAges = ages.groupingBy { it }
    var ageMap = groupedAges.eachCount().toMutableMap()
    println("Initial Ages: $ages\nGrouped: $groupedAges\nAge Map: $ageMap")
    // Set Sentinel boundaries
    ageMap.getOrPut(-1){0}
    ageMap.getOrPut(0) {0}
    ageMap.getOrPut(7) {0}
    ageMap.getOrPut(8) {0}
    for (day in (1..days)) {
        val newAges = HashMap<Int, Int>()
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
//        println("Ages after day $day: $ageMap; New Borns: $newBorns; Total: ${ageMap.values.sum()}")
    }

    return ageMap.values.sum()
}
