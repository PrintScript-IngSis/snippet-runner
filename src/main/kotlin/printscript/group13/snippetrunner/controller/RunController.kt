package printscript.group13.snippetrunner.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import printscript.group13.snippetrunner.input.FormatterInput
import printscript.group13.snippetrunner.input.InterpreterInput
import printscript.group13.snippetrunner.input.LinterInput
import printscript.group13.snippetrunner.output.FormatterOutput
import printscript.group13.snippetrunner.output.InterpreterOutput
import printscript.group13.snippetrunner.output.LinterOutput
import printscript.group13.snippetrunner.service.RunService

@RestController
@RequestMapping("/api/run")
class RunController(private val runService: RunService) {
    @PostMapping("/interpret")
    fun interpretCode(
        @Valid @RequestBody input: InterpreterInput,
    ): InterpreterOutput {
        return runService.interpretCode(input)
    }

    @PostMapping("/lint")
    fun lintCode(
        @RequestBody input: LinterInput,
    ): LinterOutput {
        return runService.lintCode(input)
    }

    @PostMapping("/format")
    fun formatCode(
        @RequestBody input: FormatterInput,
    ): FormatterOutput {
        return runService.formatCode(input)
    }
}
