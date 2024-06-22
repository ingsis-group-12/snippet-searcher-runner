package ingsis.group12.snippetsearcherrunner.runner.controller

import ingsis.group12.snippetsearcherrunner.runner.input.ExecutorInput
import ingsis.group12.snippetsearcherrunner.runner.input.FormatterInput
import ingsis.group12.snippetsearcherrunner.runner.input.LinterInput
import ingsis.group12.snippetsearcherrunner.runner.output.ExecutorOutput
import ingsis.group12.snippetsearcherrunner.runner.output.FormatterOutput
import ingsis.group12.snippetsearcherrunner.runner.output.LinterOutput
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/runner")
interface RunnerControllerSpec {
    @PostMapping("/interpret")
    @Operation(
        summary = "Interpret a snippet",
        requestBody = RequestBody(content = [Content(schema = Schema(implementation = ExecutorInput::class, required = true))]),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
                content = [Content(schema = Schema(implementation = ExecutorOutput::class))],
            ),
        ],
    )
    fun interpret(input: ExecutorInput): ExecutorOutput

    @PostMapping("/analyze")
    @Operation(
        summary = "Analyze a snippet",
        description =
            "On rules you should pass something like \"rules\": {\n" +
                "            \"enforce_literal_or_identifier_in_println_rule\": true,\n" +
                "            \"enforce_literal_or_identifier_in_read_input_rule\": true,\n" +
                "            \"identifier_rule\": \"snake_case\"\n" +
                "        } ",
        requestBody = RequestBody(content = [Content(schema = Schema(implementation = LinterInput::class, required = true))]),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
                content = [Content(schema = Schema(implementation = LinterOutput::class))],
            ),
        ],
    )
    fun analyze(input: LinterInput): LinterOutput

    @PostMapping("/format")
    @Operation(
        summary = "Format a snippet",
        requestBody = RequestBody(content = [Content(schema = Schema(implementation = FormatterInput::class, required = true))]),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
                content = [Content(schema = Schema(implementation = FormatterOutput::class))],
            ),
        ],
    )
    fun format(input: FormatterInput): FormatterOutput
}
