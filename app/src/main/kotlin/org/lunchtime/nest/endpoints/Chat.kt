package org.lunchtime.nest.endpoints

import io.github.oshai.kotlinlogging.KotlinLogging
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.format.auto
import org.lunchtime.nest.AppConfig
import org.lunchtime.nest.llm.chat

private val log = KotlinLogging.logger { "Tasks" }

data class ChatResponse(
    val content: String,
)

val chatLens = Body.auto<ChatResponse>().toLens()

class ChatEndpoint(
    private val appConfig: AppConfig,
) : HttpHandler {
    override fun invoke(req: Request): Response {
        val p = promptRequestLens(req).prompt
        val a = chat(p, appConfig.gem)
        log.info { "chat: $a" }
        val res = ChatResponse(a ?: "can not provide an answer")

        return Response(Status.OK).with(chatLens of res)
    }
}
