package ingsis.group12.snippetsearcherrunner.runner.input

import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class LinterInput(
    @field:NotNull(message = "content type is missing")
    val content: String?,
    val version: String? = "1.1",
    @field:NotNull(message = "linting rules are missing")
    val rules: JsonObject?,
)
