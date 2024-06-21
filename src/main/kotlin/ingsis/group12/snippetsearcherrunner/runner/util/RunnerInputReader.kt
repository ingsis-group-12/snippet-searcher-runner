package ingsis.group12.snippetsearcherrunner.runner.util

import edu.austral.ingsis.gradle.interpreter.util.InputReader

class RunnerInputReader(
    private val input: List<String>,
) : InputReader {
    private val iterator: Iterator<String> = this.input.iterator()

    override fun read(message: String): Any {
        return when {
            iterator.hasNext() -> iterator.next()
            else -> throw Exception("Input $message is missing")
        }
    }
}
