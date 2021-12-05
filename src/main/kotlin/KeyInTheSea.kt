fun getNumberOfDepths(filename: String) : Int {
    val lines = readInput(filename)
    var previous = Int.MIN_VALUE;
    var increases = 0;
    lines.forEachIndexed { index, line ->
        val current = line.toInt()
        if (index == 0) {
            previous = line.toInt();
        } else {
            if (current > previous) {
                increases++;
            }
            previous = current;
        }
    }
    println("Counted: " + increases)
    return increases;
}

fun getNumberOfDepthTriplets(filename: String) : Int {
    val lines = readInput(filename)
    var previous = Int.MIN_VALUE;
    var increases = 0;
    var index = 0;
    while ((index+3) < lines.size) {
        val sum1 = lines.get(index).toInt() + lines.get(index+1).toInt() + lines.get(index+2).toInt()
        index++
        val sum2 = lines.get(index).toInt() + lines.get(index+1).toInt() + lines.get(index+2).toInt()
        if (sum1 < sum2) increases++
    }
    println("Counted: " + increases)
    return increases;
}

enum class Direction {
    forward, up, down
}

fun getCoordinates(filenam: String): Int {
    val lines = readInput(filenam)
    var index = 0;
    var x = 0;
    var y = 0
    var aim = 0;

    while(index < lines.size) {
        val line = lines.get(index)
        val directionAndValue = line.split(" ")

        val direction = directionAndValue.get(0)
        val value = directionAndValue.get(1).toInt()
        when(Direction.valueOf(direction)) {
            Direction.down -> aim+=value
            Direction.up -> aim-=value
            Direction.forward -> {
                x+=value
                y+=(aim*value)
            }
        }
        index++

        println("$line : \tFwd: $x, \tDepth: $y, \tAim: $aim")
    }

    return x*y
}
