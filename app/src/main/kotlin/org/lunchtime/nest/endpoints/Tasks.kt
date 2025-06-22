package org.lunchtime.nest.endpoints

import io.github.oshai.kotlinlogging.KotlinLogging
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.lunchtime.nest.AppConfig
import org.lunchtime.nest.llm.gen



private val log = KotlinLogging.logger { "Tasks" }

data class Prompt(val prompt: String)

val promptRequestLens = Body.auto<Prompt>().toLens()

class TasksEndpoint(private val appConfig: AppConfig) : HttpHandler {
    override fun invoke(req: Request): Response {
        val p = promptRequestLens(req).prompt
        val a = gen(p, appConfig.gem)
        if (a == null) return Response(Status.OK)
        log.info { "tasks: $a" }
      return  Response(Status.OK).body(a)
    }
}
