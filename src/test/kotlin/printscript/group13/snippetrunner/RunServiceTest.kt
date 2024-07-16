package printscript.group13.snippetrunner

import org.junit.jupiter.api.Test
import printscript.group13.snippetrunner.input.FormatterInput
import printscript.group13.snippetrunner.input.InterpreterInput
import printscript.group13.snippetrunner.input.LinterInput
import printscript.group13.snippetrunner.service.RunService

class RunServiceTest {
    private val runService = RunService()

    @Test
    fun testInterpret() {
        val input = InterpreterInput("const booleanValue: boolean = false; if(booleanValue) { println('if statement is not working correctly'); } println('outside of conditional');", "1.1")
        val output = runService.interpretCode(input)
        assert(output.error == "Interpreted correctly")
        assert(output.interpretedCode[0] == "outside of conditional")
    }


    @Test
    fun testLint() {
        val input = LinterInput("let printfunction : number = 5;", "1.1")
        val output = runService.lintCode(input)
        assert(output.message == "Linted correctly")
        assert(output.errors == "java.lang.Error: Identifier printfunction is not in camelCase format in line 0 and column 4")
    }

    @Test
    fun testFormat(){
        val input = FormatterInput("println  (4.0) ;", "1.1")
        val output = runService.formatCode(input)
        assert(output.formattedCode == "println(4.0);")
        assert(output.error == "Formatted correctly")
    }

    @Test
    fun testInterpretError() {
        val input = InterpreterInput("const booleanValue: boolean = false", "1.1")
        val output = runService.interpretCode(input)
        assert(output.error == "Unfinished statement, try checking for () or ;")
    }

    @Test
    fun testLintError() {
        val input = LinterInput("let printfunction : number = 5", "1.1")
        val output = runService.lintCode(input)
        assert(output.message == "Unfinished statement, try checking for () or ;")
    }

    @Test
    fun testFormatError(){
        val input = FormatterInput("println  (4.0)", "1.1")
        val output = runService.formatCode(input)
        assert(output.error == "Unfinished statement, try checking for () or ;")
    }
}