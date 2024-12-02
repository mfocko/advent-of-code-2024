abstract class Day<Part1Answer, Part2Answer> {
    /**
     * Precomputes partial or final results that are used by both parts of the
     * challenge.
     *
     * This function is optional, it needs to be implemented, but empty override
     * is acceptable.
     */
    abstract fun precompute()

    /**
     * Tests the first part of the challenge.
     *
     * @param part1Answer Expected answer to the 1º part of the challenge
     */
    fun test(part1Answer: Part1Answer) {
        precompute()

        print("Checking part 1:\t")
        check(part1() == part1Answer)
        println("[OK]")
    }

    /**
     * Tests both parts of the challenge.
     *
     * @param part1Answer Expected answer to the 1º part of the challenge;
     * also can be `null`, in such case, the 1º part is not being tested
     * @param part2Answer Expected answer to the 2º part of the challenge
     */
    fun test(
        part1Answer: Part1Answer?,
        part2Answer: Part2Answer,
    ) {
        part1Answer?.let { test(it) }

        print("Checking part 2:\t")
        check(part2() == part2Answer) { "Given answer: ${part2()}" }
        println("[OK]")
    }

    /**
     * Runs the `precompute()` and then each part of the challenge.
     */
    fun run() {
        precompute()

        print("Part 1:\t")
        part1().println()

        print("Part 2:\t")
        part2().println()
    }

    /**
     * Computes the 1º part of the challenge.
     * @return Answer to the 1º part of the challenge
     */
    abstract fun part1(): Part1Answer

    /**
     * Computes the 2º part of the challenge.
     * @return Answer to the 2º part of the challenge
     */
    abstract fun part2(): Part2Answer
}
