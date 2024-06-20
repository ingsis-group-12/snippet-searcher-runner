package ingsis.group12.snippetsearcherrunner.runner.util

import edu.austral.ingsis.gradle.interpreter.util.EnvReader
import edu.austral.ingsis.gradle.interpreter.util.KotlinEnvReader
import ingsis.group12.snippetsearcherrunner.runner.input.EnvironmentInput

class RunnerEnvReader(private val environment: List<EnvironmentInput>) : EnvReader {
    override fun readEnv(key: String): Any {
        val result = environment.find { it.key == key }?.value
        return when (result) {
            null -> KotlinEnvReader().readEnv(key)
            else -> result
        }
    }
}
