package printscript.group13.snippetrunner.input

data class InterpreterInput(
    val code: String,
    val version: String,
    val input: String = "",
    val env: Map<String, Any> = emptyMap(),
)
