package org.lunchtime.nest

private typealias Self = AppConfig

data class AppConfig(
    val apiKey: String,
    val gem: String?,
) {
    companion object {
        fun fromEnv(): Self {
            val gemKey: String? = System.getenv("GEMINI_AI_KEY")
            val apiKey = System.getenv("API_KEY") ?: error("setting an API_KEY is mandatory")
            return Self(
                apiKey,
                gemKey,
            )
        }
    }
}
