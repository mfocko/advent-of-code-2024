private typealias Vector = Pair<Int, Int>

class Day06(
    inputType: String,
) : Day<Int, Int>() {
    private val map: Array<CharArray> = readInput(6, inputType).map { it.toCharArray() }.toTypedArray()

    private val initialPosition: Vector =
        map
            .withIndex()
            .flatMap { row ->
                row.value.withIndex().map { cell ->
                    (cell.index to row.index) to cell.value
                }
            }.first { (_, cell) -> cell == '^' }
            .let { (pos, _) -> pos }

    private val route: Set<Vector> =
        guard(null).let { (visited, _) ->
            visited.map { (_, p) -> p }.toSet()
        }

    override fun precompute() {
        // no-op
    }

    private fun inBounds(p: Vector): Boolean =
        p.let { (x, y) ->
            y >= 0 && y < map.size && x >= 0 && x < map[y].size
        }

    private fun move(
        p: Vector,
        d: Vector,
    ): Vector {
        val (x, y) = p
        val (dx, dy) = d
        return x + dx to y + dy
    }

    private fun canMoveTo(p: Vector): Boolean =
        p.let { (x, y) ->
            !inBounds(p) || map[y][x] != '#'
        }

    private fun rotate(d: Vector): Vector =
        d.let { (dx, dy) ->
            -dy to dx
        }

    private fun guard(obstacle: Vector?): Pair<Set<Pair<Vector, Vector>>, Vector> {
        var pos = initialPosition
        var direction = 0 to -1

        val visited = mutableSetOf<Pair<Vector, Vector>>()
        while (inBounds(pos) && !visited.contains(direction to pos)) {
            visited.add(direction to pos)

            var next = move(pos, direction)
            while (next == obstacle || !canMoveTo(next)) {
                direction = rotate(direction)
                next = move(pos, direction)
            }

            pos = next
        }

        return visited to pos
    }

    override fun part1(): Int = route.size

    override fun part2(): Int =
        route.filter { it != initialPosition }.count {
            guard(it).let { (_, pos) -> inBounds(pos) }
        }
}

fun main() {
    Day06("sample").test(41, 6)
    Day06("input").run()
}
