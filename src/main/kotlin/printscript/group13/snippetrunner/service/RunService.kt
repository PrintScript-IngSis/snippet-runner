package printscript.group13.snippetrunner.service

import org.springframework.stereotype.Service
import printscript.group13.snippetrunner.input.FormatterInput
import printscript.group13.snippetrunner.input.InterpreterInput
import printscript.group13.snippetrunner.input.LinterInput
import printscript.group13.snippetrunner.output.FormatterOutput
import printscript.group13.snippetrunner.output.InterpreterOutput
import printscript.group13.snippetrunner.output.LinterOutput

@Service
class RunService {
    fun interpretCode(input: InterpreterInput): InterpreterOutput {
        return RunResult("interpreting code: $code")
    }

    fun lintCode(input: LinterInput): LinterOutput {
        return RunResult("linting code: $code")
    }

    fun formatCode(input: FormatterInput): FormatterOutput {
        return RunResult("Formatting code: $code")
    }
}