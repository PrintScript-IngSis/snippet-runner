package printscript.group13.snippetrunner.service

import lexer.director.LexerDirector
import org.springframework.stereotype.Service
import printscript.group13.snippetrunner.input.FormatterInput
import printscript.group13.snippetrunner.input.InterpreterInput
import printscript.group13.snippetrunner.input.LinterInput
import printscript.group13.snippetrunner.output.FormatterOutput
import printscript.group13.snippetrunner.output.InterpreterOutput
import printscript.group13.snippetrunner.output.LinterOutput
import java.io.BufferedReader
import java.io.InputStreamReader
import org.example.interpreter.InterpreterImpl

@Service
class RunService {
    fun interpretCode(input: InterpreterInput): InterpreterOutput {
        val lexer = LexerDirector().createLexer(input.version);
        val interpreter = InterpreterImpl();
        val reader = BufferedReader(InputStreamReader(input.code));

    }

    private fun InputStreamReader(code: String): InputStreamReader {
        return InputStreamReader(code.byteInputStream());
    }

    fun lintCode(input: LinterInput): LinterOutput {
        return RunResult("linting code: $code")
    }

    fun formatCode(input: FormatterInput): FormatterOutput {
        return RunResult("Formatting code: $code")
    }
}