class Day06(
    inputType: String,
) : Day<Int, Int>() {
    private val map: Array<CharArray> = readInput(6, inputType).map { it.toCharArray() }.toTypedArray()

    private val initialPosition: Pair<Int, Int> =
        let {
            for (row in map.withIndex()) {
                for (cell in row.value.withIndex()) {
                    if (cell.value == '^') {
                        return@let cell.index to row.index
                    }
                }
            }

            error("there's always at least one guard")
        }

    private val route: Set<Pair<Int, Int>> =
        let {
            var pos = initialPosition
            var direction = 0 to -1

            val seen = mutableSetOf<Pair<Int, Int>>()
            while (inBounds(pos)) {
                seen.add(pos)

                var next = move(pos, direction)
                while (!canMoveTo(next)) {
                    direction = rotate(direction)
                    next = move(pos, direction)
                }

                pos = next
            }

            return@let seen
        }

    override fun precompute() {
        // no-op
    }

    private fun inBounds(p: Pair<Int, Int>): Boolean =
        p.let { (x, y) ->
            y >= 0 && y < map.size && x >= 0 && x < map[y].size
        }

    private fun move(
        p: Pair<Int, Int>,
        d: Pair<Int, Int>,
    ): Pair<Int, Int> {
        val (x, y) = p
        val (dx, dy) = d
        return x + dx to y + dy
    }

    private fun canMoveTo(p: Pair<Int, Int>): Boolean =
        p.let { (x, y) ->
            !inBounds(p) || map[y][x] != '#'
        }

    private fun rotate(d: Pair<Int, Int>): Pair<Int, Int> =
        d.let { (dx, dy) ->
            -dy to dx
        }

    override fun part1(): Int = route.size

    override fun part2(): Int =
        route.filter { it != initialPosition }.count { o ->
            var pos = initialPosition
            var direction = 0 to -1

            val visited = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
            while (inBounds(pos) && !visited.contains(direction to pos)) {
                visited.add(direction to pos)

                var next = move(pos, direction)
                while (next == o || !canMoveTo(next)) {
                    direction = rotate(direction)
                    next = move(pos, direction)
                }

                pos = next
            }

            inBounds(pos)
        }
}

fun main() {
    Day06("sample").test(41, 6)
    Day06("input").run()
}
