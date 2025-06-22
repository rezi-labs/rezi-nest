package org.lunchtime.nest.llm

import dev.langchain4j.model.chat.Capability
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.request.ResponseFormat
import dev.langchain4j.model.chat.request.ResponseFormatType
import dev.langchain4j.model.chat.request.json.JsonSchema
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel
import dev.langchain4j.service.output.JsonSchemas





class TaskList(
    val list: List<String>,
)

val jsonSchema: JsonSchema = JsonSchemas.jsonSchemaFrom(TaskList::class.java).get()

fun gen(prompt: String, gemKey: String?): String? {

    val gem: GoogleAiGeminiChatModel.GoogleAiGeminiChatModelBuilder? = gemKey?.let {
        GoogleAiGeminiChatModel
            .builder()
            .apiKey(it)
    }

    if (gem == null) return null

    val r = ResponseFormat.builder().jsonSchema(jsonSchema).type(ResponseFormatType.JSON).build()
    val gemini: ChatModel =
        gem
            .modelName("gemini-2.0-flash-lite")
            .supportedCapabilities(Capability.RESPONSE_FORMAT_JSON_SCHEMA)
            .responseFormat(r)
            .build()

    return gemini.chat(prompt)
}
