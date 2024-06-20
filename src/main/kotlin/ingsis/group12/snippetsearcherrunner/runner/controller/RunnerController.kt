package ingsis.group12.snippetsearcherrunner.runner.controller

import ingsis.group12.snippetsearcherrunner.runner.input.ExecutorInput
import ingsis.group12.snippetsearcherrunner.runner.input.FormatterInput
import ingsis.group12.snippetsearcherrunner.runner.input.LinterInput
import ingsis.group12.snippetsearcherrunner.runner.output.ExecutorOutput
import ingsis.group12.snippetsearcherrunner.runner.output.FormatterOutput
import ingsis.group12.snippetsearcherrunner.runner.output.LinterOutput
import ingsis.group12.snippetsearcherrunner.runner.service.RunnerService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/runner")
class RunnerController(private val runnerService: RunnerService) {
    @PostMapping("/interpret")
    fun interpret(
        @Valid @RequestBody input: ExecutorInput,
    ): ExecutorOutput {
        return runnerService.interpret(input)
    }

    @PostMapping("/analyze")
    fun analyze(
        @Valid @RequestBody input: LinterInput,
    ): LinterOutput {
        return runnerService.analyze(input)
    }

    @PostMapping("/format")
    fun format(
        @Valid @RequestBody input: FormatterInput,
    ): FormatterOutput {
        return runnerService.format(input)
    }
}
