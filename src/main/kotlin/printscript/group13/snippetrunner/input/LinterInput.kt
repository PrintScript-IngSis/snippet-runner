package printscript.group13.snippetrunner.input

import linter.LinterRules

data class LinterInput(
    val code: String,
    val version: String = "1.1",
    val rules: String = "src/main/resources/LinterRules.json" //path to rules file
)
