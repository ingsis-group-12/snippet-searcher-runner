package ingsis.group12.snippetsearcherrunner.runner.input

import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Serializable

@Serializable
data class LinterInput(
    @field:NotNull(message = "content type is missing")
    val content: String?,
    val language: String? = "printscript 1.1",
    @field:NotNull(message = "linting rules are missing")
    val rules: String?,
)
