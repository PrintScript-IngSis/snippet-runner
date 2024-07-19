package printscript.group13.snippetrunner.service

import lexer.director.LexerDirector
import linter.LinterImpl
import org.example.ast.nodes.ProgramNode
import org.example.formatter.FormatterImpl
import org.example.interpreter.InterpreterImpl
import org.example.parser.ParserImpl
import org.example.token.Token
import org.springframework.stereotype.Service
import printscript.group13.snippetrunner.input.FormatterInput
import printscript.group13.snippetrunner.input.InterpreterInput
import printscript.group13.snippetrunner.input.LinterInput
import printscript.group13.snippetrunner.output.FormatterOutput
import printscript.group13.snippetrunner.output.InterpreterOutput
import printscript.group13.snippetrunner.output.LinterOutput
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@Service
class RunService {
    fun interpretCode(input: InterpreterInput): InterpreterOutput {
        try {
            val lexer = LexerDirector().createLexer(input.version)
            val lines = input.code.split("\n")
            val output = mutableListOf<String>()
            var index = 0
            while (index < lines.size) {
                var line = lines[index]
                if (line.contains("if")) {
                    line = handleIf(line, lines, index)
                    index = lines.indexOf(line)
                }
                val tokens: List<Token> = lexer.tokenize(line)
                val ast: ProgramNode = ParserImpl().parse(tokens)
                val baos = ByteArrayOutputStream()
                if (input.input.isNotEmpty()) {
                    output.addAll(checkInput(ast, baos,true, input.input))
                } else {
                    output.addAll(checkInput(ast, baos))
                }
                index++
            }
            return InterpreterOutput(output, "Interpreted correctly")
        } catch (e: Throwable) {
            return InterpreterOutput(mutableListOf(), e.message ?: "An error occurred")
        }
    }

    private fun handleIf(
        line: String,
        lines: List<String>,
        startIndex: Int,
    ): String {
        var lineMutable = line
        var index = startIndex
        while (!lineMutable.contains("}")) {
            index++
            if (index >= lines.size) break
            lineMutable += lines[index]
        }
        if (index + 1 < lines.size) {
            var newLine = lines[index + 1]
            if (newLine.contains("else")) {
                while (!newLine.contains("}")) {
                    index++
                    if (index >= lines.size) break
                    newLine += lines[index]
                }
            }
            lineMutable += " $newLine "
        }
        return lineMutable
    }

    private fun checkInput(
        ast: ProgramNode,
        baos: ByteArrayOutputStream,
        isInput: Boolean = false,
        inputValue: String = "",
    ): MutableList<String> {
        val ps = PrintStream(baos)
        val oldOut = System.out
        System.setOut(ps)
        val interpreter = InterpreterImpl()
        interpreter.interpret(ast, isInput, inputValue)
        System.setOut(oldOut)
        val response = baos.toString().replace("\r", "").trim()
        val resultList = mutableListOf<String>()
        if (response.isNotBlank()) {
            response.split("\n").forEach { resultList.add(it) }
        }
        return resultList
    }

    fun lintCode(input: LinterInput): LinterOutput {
        try {
            val lexer = LexerDirector().createLexer(input.version)
            val tokens: List<Token> = lexer.tokenize(input.code)
            val ast: ProgramNode = ParserImpl().parse(tokens)
            val linter = LinterImpl()
            val errors = linter.checkErrors(ast, input.rules)
            val output = StringBuilder()
            for (error in errors) {
                output.append(error.toString())
            }
            return LinterOutput(output.toString(), "Linted correctly")
        } catch (e: Throwable) {
            return LinterOutput("", e.message ?: "An error occurred")
        }
    }

    fun formatCode(input: FormatterInput): FormatterOutput {
        try {
            val lexer = LexerDirector().createLexer(input.version)
            val tokens: List<Token> = lexer.tokenize(input.code)
            val ast: ProgramNode = ParserImpl().parse(tokens)
            val formatter = FormatterImpl()
            val output = formatter.format(ast, input.rules)
            return FormatterOutput(output.trim(), "Formatted correctly")
        } catch (e: Throwable) {
            return FormatterOutput("", e.message ?: "An error occurred")
        }
    }
}
